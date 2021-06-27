package com.helluys.parsers.functional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Result {
	private final boolean _isValid;
	private final List<Result> children = new ArrayList<>();
	private final int consumed;

	public static Result of(final List<Result> children, final int consumed) {
		return new Result(true, children, consumed);
	}

	public static Result failure(final int consumed) {
		return new Result(false, Collections.emptyList(), consumed);
	}

	private Result(final boolean _isValid, final List<Result> children, final int consumed) {
		this._isValid = _isValid;
		this.children.addAll(children);
		this.consumed = consumed;
	}

	public boolean valid() {
		return _isValid;
	}

	public List<Result> children() {
		return children;
	}

	public int consumed() {
		return this.consumed;
	}

	@Override
	public String toString() {
		return "Result [_isValid=" + _isValid + ", children=" + children + ", consumed=" + consumed + "]";
	}

}
