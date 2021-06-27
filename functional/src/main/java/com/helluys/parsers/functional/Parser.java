package com.helluys.parsers.functional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class Parser {
	public static final Parser IS(final char character) {
		return new Parser((s, i) -> i < s.length()
				? s.charAt(i) == character ? Result.of(Collections.emptyList(), 1) : Result.failure(0)
						: Result.failure(0));
	}

	public static final Parser IS(final String s) {
		Parser p = IS(s.charAt(0));
		for (int i = 1; i < s.length(); i++) {
			p = p.then(IS(s.charAt(i)));
		}
		return p;
	}

	public static final Parser ANY(final String characters) {
		return new Parser((s, i) -> i < s.length()
				? characters.indexOf(s.codePointAt(i)) != -1 ? Result.of(Collections.emptyList(), 1) : Result.failure(0)
						: Result.failure(0));
	}

	public static final Parser EOS() {
		return new Parser((s, i) -> i < s.length() ? Result.of(Collections.emptyList(), 0) : Result.failure(0));
	}

	private Matcher matcher;

	public Parser(final Matcher matcher) {
		this.matcher = matcher;
	}

	public void redefine(final Parser parser) {
		this.matcher = parser.matcher;
	}

	public Result parse(final String input) {
		return matcher.match(input, 0);
	}

	public Result parse(final String input, final int index) {
		return matcher.match(input, index);
	}

	public Parser maybe() {
		return new Parser((s, i) -> {
			final Result res = parse(s, i);
			return res.valid() ? res : Result.of(Collections.emptyList(), 0);
		});
	}

	public Parser then(final Parser p) {
		return new Parser((s, i) -> {
			final Result res1 = parse(s, i);
			if (res1.valid()) {
				final Result res2 = p.parse(s, i + res1.consumed());
				return res2.valid() ? Result.of(Arrays.asList(res1, res2), res1.consumed() + res2.consumed()) : res2;
			} else {
				return res1;
			}
		});
	}

	public Parser or(final Parser p) {
		return new Parser((s, i) -> {
			final Result res = parse(s, i);
			return res.valid() ? res : p.parse(s, i);
		});
	}

	public Parser some() {
		return new Parser((s, i) -> {
			final List<Result> results = new ArrayList<>();
			int consumed = 0;
			Result tmpRes;
			do {
				tmpRes = parse(s, i + consumed);

				if (tmpRes.valid()) {
					results.add(tmpRes);
					consumed += tmpRes.consumed();
				}
			} while (tmpRes.valid() && i + consumed < s.length());
			return results.isEmpty() ? Result.failure(consumed) : Result.of(results, consumed);
		});
	}

	public Parser times(final int n) {
		Parser p = this;
		for (int k = 1; k < n; k++) {
			p = p.then(this);
		}
		return p;
	}
}
