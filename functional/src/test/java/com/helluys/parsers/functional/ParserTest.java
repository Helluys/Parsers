package com.helluys.parsers.functional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ParserTest {

	private static final Parser DIGIT_PARSER = Parser.IS('0').or(Parser.IS('1')).or(Parser.IS('2')).or(Parser.IS('3'))
			.or(Parser.IS('4')).or(Parser.IS('5')).or(Parser.IS('6')).or(Parser.IS('7')).or(Parser.IS('8'))
			.or(Parser.IS('9'));

	private static final Parser EXACT_PARSER = Parser.IS("1234");
	private static final Parser NATURAL_PARSER = DIGIT_PARSER.some();
	private static final Parser THREE_DIGIT_PARSER = DIGIT_PARSER.times(3);
	private static final Parser RELATIVE_PARSER = Parser.IS('-').maybe().then(NATURAL_PARSER);

	private static final Parser VARIABLE_PARSER = Parser.ANY("abcdefghijklmnopqrstuvwxyz").some();

	private static final Parser EXPR_PARSER = Parser.EOS(); // Placeholder to break recursion
	private static final Parser PAR_EXPR_PARSER = Parser.IS('(').then(EXPR_PARSER).then(Parser.IS(')'));
	private static final Parser FACTOR_PARSER = NATURAL_PARSER.or(VARIABLE_PARSER).or(PAR_EXPR_PARSER);

	private static final Parser MULT_OR_DIV = Parser.IS('*').or(Parser.IS('/'));
	private static final Parser TERM_PARSER = FACTOR_PARSER.then(MULT_OR_DIV.then(FACTOR_PARSER).some().maybe());

	private static final Parser EQUALITY_PARSER = EXPR_PARSER.then(Parser.IS('=')).then(EXPR_PARSER);

	private static final Parser PLUS_OR_MINUS = Parser.IS('+').or(Parser.IS('-'));
	{
		// Redefine EXPR_PARSER after having defined TERM_PARSER
		EXPR_PARSER.redefine(TERM_PARSER.then(PLUS_OR_MINUS.then(TERM_PARSER).some().maybe()));
	}

	@Test
	public void testDigitValid() {
		assertTrue(DIGIT_PARSER.parse("0").valid());
		assertTrue(DIGIT_PARSER.parse("1").valid());
		assertTrue(DIGIT_PARSER.parse("2").valid());
		assertTrue(DIGIT_PARSER.parse("3").valid());
		assertTrue(DIGIT_PARSER.parse("4").valid());
		assertTrue(DIGIT_PARSER.parse("5").valid());
		assertTrue(DIGIT_PARSER.parse("6").valid());
		assertTrue(DIGIT_PARSER.parse("7").valid());
		assertTrue(DIGIT_PARSER.parse("8").valid());
		assertTrue(DIGIT_PARSER.parse("9").valid());
		assertFalse(DIGIT_PARSER.parse("a").valid());
	}

	@Test
	public void testDigitConsumed() {
		assertEquals(1, DIGIT_PARSER.parse("0").consumed());
		assertEquals(1, DIGIT_PARSER.parse("1").consumed());
		assertEquals(1, DIGIT_PARSER.parse("2").consumed());
		assertEquals(1, DIGIT_PARSER.parse("3").consumed());
		assertEquals(1, DIGIT_PARSER.parse("4").consumed());
		assertEquals(1, DIGIT_PARSER.parse("5").consumed());
		assertEquals(1, DIGIT_PARSER.parse("6").consumed());
		assertEquals(1, DIGIT_PARSER.parse("7").consumed());
		assertEquals(1, DIGIT_PARSER.parse("8").consumed());
		assertEquals(1, DIGIT_PARSER.parse("9").consumed());
		assertEquals(0, DIGIT_PARSER.parse("a").consumed());
	}

	@Test
	public void testExactValid() {
		assertTrue(EXACT_PARSER.parse("1234").valid());
		assertTrue(EXACT_PARSER.parse("12345").valid());
		assertFalse(EXACT_PARSER.parse("1").valid());
		assertFalse(EXACT_PARSER.parse("1235").valid());
	}

	@Test
	public void testExactConsumed() {
		assertEquals(4, EXACT_PARSER.parse("1234").consumed());
		assertEquals(4, EXACT_PARSER.parse("12345").consumed());
		assertEquals(0, EXACT_PARSER.parse("1").consumed());
		assertEquals(0, EXACT_PARSER.parse("1235").consumed());
	}

	@Test
	public void testNaturalValid() {
		assertTrue(NATURAL_PARSER.parse("0").valid());
		assertTrue(NATURAL_PARSER.parse("10").valid());
		assertTrue(NATURAL_PARSER.parse("652").valid());
		assertTrue(NATURAL_PARSER.parse("3541").valid());
		assertTrue(NATURAL_PARSER.parse("45613abc").valid());
		assertFalse(NATURAL_PARSER.parse("-3541").valid());
		assertFalse(NATURAL_PARSER.parse("absdb").valid());
	}

	@Test
	public void testNaturalConsumed() {
		assertEquals(1, NATURAL_PARSER.parse("0").consumed());
		assertEquals(2, NATURAL_PARSER.parse("10").consumed());
		assertEquals(3, NATURAL_PARSER.parse("652").consumed());
		assertEquals(4, NATURAL_PARSER.parse("3541").consumed());
		assertEquals(5, NATURAL_PARSER.parse("45613abc").consumed());
		assertEquals(0, NATURAL_PARSER.parse("-3541").consumed());
		assertEquals(0, NATURAL_PARSER.parse("absdb").consumed());
	}

	@Test
	public void testThreeDigitValid() {
		assertFalse(THREE_DIGIT_PARSER.parse("0").valid());
		assertFalse(THREE_DIGIT_PARSER.parse("10").valid());
		assertTrue(THREE_DIGIT_PARSER.parse("652").valid());
		assertTrue(THREE_DIGIT_PARSER.parse("3541").valid());
		assertTrue(THREE_DIGIT_PARSER.parse("456abc").valid());
		assertTrue(THREE_DIGIT_PARSER.parse("45613abc").valid());
		assertFalse(THREE_DIGIT_PARSER.parse("-3541").valid());
		assertFalse(THREE_DIGIT_PARSER.parse("absdb").valid());
	}

	@Test
	public void testThreeDigitConsumed() {
		assertEquals(0, THREE_DIGIT_PARSER.parse("0").consumed());
		assertEquals(0, THREE_DIGIT_PARSER.parse("10").consumed());
		assertEquals(3, THREE_DIGIT_PARSER.parse("652").consumed());
		assertEquals(3, THREE_DIGIT_PARSER.parse("3541").consumed());
		assertEquals(3, THREE_DIGIT_PARSER.parse("456abc").consumed());
		assertEquals(3, THREE_DIGIT_PARSER.parse("45613abc").consumed());
		assertEquals(0, THREE_DIGIT_PARSER.parse("-3541").consumed());
		assertEquals(0, THREE_DIGIT_PARSER.parse("absdb123").consumed());
	}

	@Test
	public void testRelativeValid() {
		assertTrue(RELATIVE_PARSER.parse("0").valid());
		assertTrue(RELATIVE_PARSER.parse("10").valid());
		assertTrue(RELATIVE_PARSER.parse("652").valid());
		assertTrue(RELATIVE_PARSER.parse("3541").valid());
		assertTrue(RELATIVE_PARSER.parse("45613abc").valid());
		assertTrue(RELATIVE_PARSER.parse("-3541").valid());
		assertFalse(RELATIVE_PARSER.parse("absdb123").valid());
	}

	@Test
	public void testRelativeConsumed() {
		assertEquals(1, RELATIVE_PARSER.parse("0").consumed());
		assertEquals(2, RELATIVE_PARSER.parse("10").consumed());
		assertEquals(3, RELATIVE_PARSER.parse("652").consumed());
		assertEquals(4, RELATIVE_PARSER.parse("3541").consumed());
		assertEquals(5, RELATIVE_PARSER.parse("45613abc").consumed());
		assertEquals(5, RELATIVE_PARSER.parse("-3541").consumed());
		assertEquals(0, RELATIVE_PARSER.parse("absdb").consumed());
	}

	@Test
	public void testEqualityParser() {
		assertTrue(EQUALITY_PARSER.parse("1+2*3=x*(3+y)").valid());
	}
}
