package com.helluys.parsers.functional;

@FunctionalInterface
public interface Matcher {
	/**
	 * Attempts to match the input {@code string} given the {@code context}.<br>
	 * The matching applies starting from {@link Context#index()} and may parse any
	 * number of characters. The characters are {@link Context#consume consumed} if
	 * and only if the parsing resulted in a match. In case of a non match, the
	 * {@link Context#index()} is untouched and the {@link Result#valid()} is
	 * {@code false}.
	 *
	 * @param character
	 * @param context
	 * @return
	 */
	Result match(final String text, final int index);
}