package com.helluys.parsers.llone;

import java.util.Collections;
import java.util.List;
import java.util.Stack;

import com.helluys.parsers.llone.tokens.Symbol;

public final class Parser {

	public final boolean parse(final String string) {
		final Stack<Symbol> stack = new Stack<>();
		stack.push(Symbol.START_SYMBOL);

		try {
			for (int i = 0; i < string.length();) {
				final char c = string.charAt(i);
				if (Character.isWhitespace(c)) {
					i++;
					continue;
				}

				final Symbol symbol = stack.pop();
				if (symbol.isTerminal()) {
					symbol.match(Character.valueOf(c));
					i++;
				} else {
					final List<Symbol> tokens = symbol.match(Character.valueOf(c));
					Collections.reverse(tokens);
					for (final Symbol t : tokens) {
						stack.push(t);
					}
				}
			}

			while (!stack.isEmpty()) {
				final Symbol symbol = stack.pop();
				symbol.match(null);
			}
		} catch (final ParseException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}
}
