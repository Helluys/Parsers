package com.helluys.parsers.llone.tokens;

abstract class NonTerminal implements Symbol {
	@Override
	public boolean isTerminal() {
		return false;
	}
}
