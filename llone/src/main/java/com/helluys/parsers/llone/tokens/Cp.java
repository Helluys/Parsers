package com.helluys.parsers.llone.tokens;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.helluys.parsers.llone.ParseException;

final class Cp extends NonTerminal {
	@Override
	public List<Symbol> match(final Character c) throws ParseException {
		if (c == null || c == '=' || c == ')' || c == '+' || c == '-' || c == '*' || c == '/') {
			return Collections.emptyList();
		} else if (c >= '0' && c <= '9') {
			return Arrays.asList(new Terminal(c), new Cp());
		}

		throw new ParseException("Expected digit, operator or end: " + c);
	}
}
