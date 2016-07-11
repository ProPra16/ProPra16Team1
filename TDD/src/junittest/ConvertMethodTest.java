// MIT License, check LICENSE.txt in the src folder for full text

package junittest;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import logic.Timer;

public class ConvertMethodTest {

	
	@Test
	public void testConvertMethod() {
		
		
		String case1 = "3 Minuten und 0 Sekunde";
		String case2 = "2 Minuten und 0 Sekunde";
		String case3 = "2 Minuten und 3 Sekunde";
		String case4 = "1 Minute und 59 Sekunde";
		String case5 = "59 Sekunde";
		String case6 = "10 Sekunde";
		
		
		//testing...
		//180 sec = 3 Min
		assertEquals(case1, Timer.time(180)); 
		//120 sec = 2 Min
		assertEquals(case2, Timer.time(120)); 
		//123 sec = 2 Min , 3 sec
		assertEquals(case3, Timer.time(123)); 
		//119 sec = 1 Min , 59 sec
		assertEquals(case4, Timer.time(119)); 
		//59 sec
		assertEquals(case5, Timer.time(59)); 
		//10 sec
		assertEquals(case6, Timer.time(10)); 
		
	}

}
