package com.helluys.parsers.llone.tokens;

import java.util.List;

import com.helluys.parsers.llone.ParseException;

public interface Symbol {
	public static final Symbol START_SYMBOL = new S();

	boolean isTerminal();
	List<Symbol> match(final Character c) throws ParseException;
}
