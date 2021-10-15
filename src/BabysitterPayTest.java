import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;

import org.junit.jupiter.api.Test;

class BabysitterPayTest {

	@Test
	void testMain() {

	}

	@Test
	void testCalculatePay() {
		//test all hours as 0
		assertEquals(0, BabysitterPay.CalculatePay(0, 0, 0));
		//test only base hours
		assertEquals(24, BabysitterPay.CalculatePay(2, 0, 0));
		//test base and evening
		assertEquals(32, BabysitterPay.CalculatePay(2, 1, 0));
		//test base and late, no evening
		assertEquals(104, BabysitterPay.CalculatePay(6, 0, 2));
		//test all three
		assertEquals(100, BabysitterPay.CalculatePay(3, 2, 3));
	}

	@Test
	void testReadIntegerFromUser() {
		//test integer input
		String input = "7";
		int intValue = 0;
		System.setIn(new ByteArrayInputStream(input.getBytes()));
		intValue = BabysitterPay.ReadIntegerFromUser();
		assertEquals(7, intValue);
		
		//test non-whole number - should print message and return 0
		//code loops when 0 is returned
		input = "7.5";
		intValue = 0;
		System.setIn(new ByteArrayInputStream(input.getBytes()));
		intValue = BabysitterPay.ReadIntegerFromUser();
		assertEquals(0, intValue);
		
		//test non-numeric input - should print message and return 0
		//code loops when 0 is returned
		input = "xyz";
		intValue = 0;
		System.setIn(new ByteArrayInputStream(input.getBytes()));
		intValue = BabysitterPay.ReadIntegerFromUser();
		assertEquals(0, intValue);
				
	}

	@Test
	void testConvertTime() {
		//test negative input
		assertEquals(-1, BabysitterPay.ConvertTime(-3));
		//test 0 input
		assertEquals(-1, BabysitterPay.ConvertTime(0));
		//test before midnight
		assertEquals(0, BabysitterPay.ConvertTime(5));
		//test after midnight
		assertEquals(11, BabysitterPay.ConvertTime(4));
	}

	@Test
	void testValidateTimeInputs() {
		//test one valid, two invalid inputs
		assertEquals(false, BabysitterPay.ValidateTimeInputs(-1, 0, 3));
		assertEquals(false, BabysitterPay.ValidateTimeInputs(0, -1, 3));
		assertEquals(false, BabysitterPay.ValidateTimeInputs(0, 2, -1));
		//test 3 invalid inputs
		assertEquals(false, BabysitterPay.ValidateTimeInputs(-1, -1, -1));
		//test 3 valid inputs
		assertEquals(true, BabysitterPay.ValidateTimeInputs(0, 5, 3));
	}

	@Test
	void testValidateStartTime() {
		//test negative input - invalid input from conversion
		assertEquals(false, BabysitterPay.ValidateStartTime(-1));
		//test 0 input (5:00)
		assertEquals(true, BabysitterPay.ValidateStartTime(0));
		//test 4am input - too late to start
		assertEquals(false, BabysitterPay.ValidateStartTime(11));
		//test midnight input - last possible start time
		assertEquals(true, BabysitterPay.ValidateStartTime(7));
	}

	@Test
	void testValidateEndTime() {
		//invalid start time input won't make it this far, so only testing valid start times
		//test negative input - invalid input from conversion
		assertEquals(false, BabysitterPay.ValidateEndTime(-1, 0));
		//test same as start time
		assertEquals(false, BabysitterPay.ValidateEndTime(2, 2));
		//test end time before start time
		assertEquals(false, BabysitterPay.ValidateEndTime(3, 5));
		//test end time after start time
		assertEquals(true, BabysitterPay.ValidateEndTime(6, 1));
	}

	@Test
	void testValidateBedtime() {
		//invalid start/end time input won't make it this far, so only testing valid times for those
		//test negative input - invalid input from conversion
		assertEquals(false, BabysitterPay.ValidateBedtime(-1, 0, 3));
		//test same as start time
		assertEquals(true, BabysitterPay.ValidateBedtime(0, 0, 3));
		//test same as end time
		assertEquals(true, BabysitterPay.ValidateBedtime(3, 0, 3));
		//test not between start and end times
		assertEquals(false, BabysitterPay.ValidateBedtime(6, 1, 3));
	}
	
	@Test
	void testCalculateBaseHours() {
		//test bedtime before midnight
		assertEquals(4, BabysitterPay.CalculateBaseHours(0, 4));
		//test bedtime at midnight
		assertEquals(5, BabysitterPay.CalculateBaseHours(2, 7));
		//test bedtime after midnight
		assertEquals(5, BabysitterPay.CalculateBaseHours(2, 8));		
	}
	
	@Test
	void testCalculateEveningHours() {
		//test bedtime before midnight, end after midnight
		assertEquals(3, BabysitterPay.CalculateEveningHours(4, 8));
		//test bedtime and end time before midnight
		assertEquals(2, BabysitterPay.CalculateEveningHours(4, 6));
		//test bedtime at midnight
		assertEquals(0, BabysitterPay.CalculateEveningHours(7, 8));
		//test bedtime after midnight
		assertEquals(0, BabysitterPay.CalculateEveningHours(8, 10));	
	}
	
	@Test
	void testCalculateLateHours() {
		//test end time before midnight
		assertEquals(0, BabysitterPay.CalculateLateHours(6));
		//test end time at midnight
		assertEquals(0, BabysitterPay.CalculateLateHours(7));
		//test end time after midnight
		assertEquals(1, BabysitterPay.CalculateLateHours(8));	
	}

}
