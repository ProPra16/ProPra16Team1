// MIT License, check LICENSE.txt in the src folder for full text

package junittest;

import static org.junit.Assert.*;

import org.junit.Test;

import logic.Timer;

public class TimerTest {

		
	@Test
	public void testTime() {
		assertEquals("1 Minute und 0 Sekunde", Timer.time(60));
		assertEquals("1 Minute und 23 Sekunde",Timer.time(83));
		assertEquals("2 Sekunde", Timer.time(2));
		assertEquals("2 Minuten und 43 Sekunde", Timer.time(163));
	}

}
