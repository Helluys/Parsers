package com.helluys.parsers.llone.tokens;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.helluys.parsers.llone.ParseException;

final class Ep extends NonTerminal {
	@Override
	public List<Symbol> match(final Character c) throws ParseException {
		if (c == null || c == '=' || c == ')') {
			return Collections.emptyList();
		} else if (c == '+') {
			return Arrays.asList(new Terminal('+'), new E());
		} else if (c == '-') {
			return Arrays.asList(new Terminal('-'), new E());
		}

		throw new ParseException("Expected addition, substraction or end: " + c);
	}
}
