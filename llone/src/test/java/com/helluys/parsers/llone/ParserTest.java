package com.helluys.parsers.llone;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class ParserTest {
	@Parameters
	public static Collection<Object[]> validStrings() {
		return Arrays.asList(new Object[][] { { "1 = 1" } , { "1 + 2 = 3 * 1" }, { "1 + ( 3 * 5 + 5) * 2 = 100 / 2 - 9" } });
	}

	private final String string;

	public ParserTest(final String string) {
		this.string = string;
	}

	@Test
	public void testParse() {
		assertTrue(new Parser().parse(string));
	}
}
