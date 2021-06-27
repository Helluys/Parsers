package com.helluys.parsers.llone.tokens;

import java.util.Arrays;
import java.util.List;

import com.helluys.parsers.llone.ParseException;

final class V extends NonTerminal {
	@Override
	public List<Symbol> match(final Character c) throws ParseException {
		if (c >= 'a' && c <= 'z') {
			return Arrays.asList(new Terminal(c));
		}

		throw new ParseException("Expected lowercase letter: " + c);
	}
}
