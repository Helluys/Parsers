package com.helluys.parsers.llone.tokens;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.helluys.parsers.llone.ParseException;

final class Tp extends NonTerminal {
	@Override
	public List<Symbol> match(final Character c) throws ParseException {
		if (c == null || c == '=' || c == ')' || c == '+' || c == '-') {
			return Collections.emptyList();
		} else if (c == '*') {
			return Arrays.asList(new Terminal('*'), new T());
		} else if (c == '/') {
			return Arrays.asList(new Terminal('/'), new T());
		}

		throw new ParseException("Expected operator or end: " + c);
	}
}
