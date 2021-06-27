package com.helluys.parsers.llone.tokens;

import java.util.Collections;
import java.util.List;

import com.helluys.parsers.llone.ParseException;

final class Terminal implements Symbol {
	private final Character value;

	public Terminal(final Character value) {
		this.value = value;
	}

	public Character getValue() {
		return value;
	}

	@Override
	public boolean isTerminal() {
		return true;
	}

	@Override
	public List<Symbol> match(final Character c) throws ParseException {
		System.out.println("Terminal " + c);
		if (!value.equals(c)) {
			throw new ParseException("Expected " + value + ": " + c);
		}

		return Collections.emptyList();
	}
}
