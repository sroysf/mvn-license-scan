package com.codechronicle.model;

import junit.framework.Assert;

import org.junit.Test;


public class MavenCoordinateTest {

	@Test(expected=AssertionError.class)
	public void testBadInput1() {
		MavenCoordinate mvcx = new MavenCoordinate(null, "beanshell", "2.0b4");
	}
	
	@Test(expected=AssertionError.class)
	public void testBadInput2() {
		MavenCoordinate mvcx = new MavenCoordinate("beanshell", "", "2.0b4");
	}
	
	@Test(expected=AssertionError.class)
	public void testBadInput3() {
		MavenCoordinate mvcx = new MavenCoordinate("beanshell", "beanshell", null);
	}
}
