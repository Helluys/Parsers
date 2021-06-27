package com.helluys.parsers.llone.tokens;

import java.util.Arrays;
import java.util.List;

import com.helluys.parsers.llone.ParseException;

final class S extends NonTerminal {
	@Override
	public List<Symbol> match(final Character c) throws ParseException {
		if (c >= '0' && c <= '9') {
			return Arrays.asList(new E(), new Terminal('='), new E());
		} else if (c >= 'a' && c <= 'z') {
			return Arrays.asList(new E(), new Terminal('='), new E());
		} else if (c == '(') {
			return Arrays.asList(new E(), new Terminal('='), new E());
		}

		throw new ParseException("Expected digit, lowercase letter or opening parenthesis: " + c);
	}
}
