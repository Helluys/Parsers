package com.helluys.parsers.llone.tokens;

import java.util.Arrays;
import java.util.List;

import com.helluys.parsers.llone.ParseException;

final class C extends NonTerminal {
	@Override
	public List<Symbol> match(final Character c) throws ParseException {
		if (c >= '0' && c <= '9') {
			return Arrays.asList(new Terminal(c), new Cp());
		}

		throw new ParseException("Expected digit or end: " + c);
	}
}
