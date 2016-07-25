// MIT License, check LICENSE.txt in the src folder for full text

package test.java.junittest;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import main.java.logic.Aufgabe;

public class AufgabeTestClass {
	
	Aufgabe tester = new Aufgabe("Name","Beschreibung");

	@Test
	public void testGetName() {
		assertEquals("Name", tester.getName());
	}
	
	@Test
	public void testGetBeschreibung() {
		assertEquals("Beschreibung", tester.getBeschreibung());
	}
	
	// Tests generateKlasse() and getKlasse()
	@Test
	public void testGetKlasse() {
		ArrayList<String> testKlasse = new ArrayList<String>();
		testKlasse.add("Klasse");
		tester.generateKlasse("Klasse");
		assertEquals(testKlasse, tester.getKlasse());
	}
	
	// Tests generateTest() and getTest()
	@Test
	public void testGetTest() {
		ArrayList<String> testTest = new ArrayList<String>();
		testTest.add("Test");
		tester.generateTest("Test");
		assertEquals(testTest, tester.getTest());
	}
	
	// Tests saveClassName() and ClassName()
	@Test
	public void testSaveClassName() {
		tester.saveClassName("Class1");
		assertEquals("Class1", tester.className());
	}
	
	// Tests saveTestName() and TestName()
	@Test
	public void testSaveTestName() {
		tester.saveTestName("Test1");
		assertEquals("Test1", tester.testName());
	}
	
	@Test
	public void testGetTestContructor() {
		tester.generateTest("Test");
		assertEquals("Test", tester.getTestConstructor(0));
	}
	
	@Test
	public void testGetClasstContructor() {
		tester.generateKlasse("Klasse");
		assertEquals("Klasse", tester.getClassConstructor(0));
	}
}
