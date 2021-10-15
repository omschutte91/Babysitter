import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author Olivia Schutte
 * Kata - Northwoods
 * @version 1.0
 */
public class BabysitterPay {

	/*
	 * Assumptions: 
	 * - all babysitting jobs have a bedtime
	 */

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int intStartTime = 0;
		int intEndTime = 0;
		int intBedtime = 0;
		boolean blnValid = false;
		int intBaseHours = 0;
		int intEveningHours = 0;
		int intLateHours = 0;
		int intPay = 0;

		//get start, end, and bed time and validate input
		do {			
			//get start time
			do {
				System.out.println("Enter start time as a whole number (for 7:00, enter 7)");
				System.out.println("Start time: ");
				intStartTime = ReadIntegerFromUser();
			} while (intStartTime == 0);
			//convert
			intStartTime = ConvertTime(intStartTime);
	
			//get end time
			do {
				System.out.println("Enter end time as a whole number (for 7:00, enter 7)");
				System.out.println("End time: ");
				intEndTime = ReadIntegerFromUser();	
			} while (intEndTime == 0);
			//convert
			intEndTime = ConvertTime(intEndTime);

			//get bedtime
			do {
				System.out.println("Enter bedtime as a whole number (for 7:00, enter 7)");
				System.out.println("Bedtime: ");
				intBedtime = ReadIntegerFromUser();
			} while (intBedtime == 0);
			//convert
			intBedtime = ConvertTime(intBedtime);
			
			//validate all times
			blnValid = ValidateTimeInputs(intStartTime, intEndTime, intBedtime);			
		} while (blnValid == false);
		
		//calculate regular/base hours (start to bedtime)
		intBaseHours = CalculateBaseHours(intStartTime, intBedtime);
		
		//calculate evening hours (bedtime to midnight)
		intEveningHours = CalculateEveningHours(intBedtime, intEndTime);
		
		//calculate late hours (midnight to end time)
		//this really could just be done in one line here but I'm breaking it out for consistency/testing
		intLateHours = CalculateLateHours(intEndTime);	
		
		//calculate pay
		intPay = CalculatePay(intBaseHours, intEveningHours, intLateHours);
		
		//output pay
		System.out.println("Total pay: $" + intPay);
		
	}
	
	
/**
	 * method: ReadIntegerFromUser
	 * @return intValue
	 */
	public static int ReadIntegerFromUser( )
	{
		int intValue = 0;

		try{
			String strBuffer = "";	
			// Input stream
			BufferedReader burInput = new BufferedReader( new InputStreamReader( System.in ) ) ;
			// Read a line from the user
			strBuffer = burInput.readLine( );	
			// Convert from string to integer
			intValue = Integer.parseInt( strBuffer );
		}
		//print instructions for correction rather than throwing error and halting program
		catch(NumberFormatException e){
			System.out.println("Please enter whole numbers only.");
		}
		catch( Exception excError ){
			System.out.println( excError.toString( ) );
		}
		// Return integer value
		return intValue;
	}

	/**
	 * Method: ConvertTime
	 * Abstract: Take user input for time and convert to 0-11 scale (makes it easier to deal with for calculations)
	 * @param intInput
	 * @return intNewTime
	 */
	public static int ConvertTime(int intInput)
	{
		int intNewTime = -1;
		//not checking for validity of input beyond non-negative and non-zero
		if(intInput > 0){
			//hours 5pm to midnight
			if(intInput > 4){
				//5:00 (5) is the start point, so we will set it to 0. This means 12:00 (12) will be 7.
				intNewTime = intInput - 5;
			}
			//hours 1am to 4am
			else{
				//hours after 12 will start at 8 (continuing after midnight)
				intNewTime = intInput + 7;
			}
		}
		//returns -1 if input is not a positive number, which will trigger an error later
		return intNewTime;
	}
	
	/**
	 * method: ValidateTimeInputs
	 * @param intStartTime
	 * @return blnValid
	 * ReadIntegerFromUser already checks for numeric input, so this just needs
	 * to check that the number makes sense with the others.
	 * Assumes number has already been converted to 0-11 scale.
	 */
	public static boolean ValidateTimeInputs(int intStartTime, int intEndTime, int intBedtime)
	{
		boolean blnValid = false;
		
		//check start time
		blnValid = ValidateStartTime(intStartTime);
		//if that's good, check end time
		if(blnValid == true) {
			//check end time
			blnValid = ValidateEndTime(intEndTime, intStartTime);
			//if that's good, check bedtime
			if(blnValid == true) {
				//check bedtime
				blnValid = ValidateBedtime(intBedtime, intStartTime, intEndTime);
			}
		}		
		return blnValid;
	}
	
	/**
	 * method: ValidateStartTime
	 * @param intStartTime
	 * @return blnValid
	 * ReadIntegerFromUser already checks for numeric input, so this just needs
	 * to check that the number makes sense with the others.
	 * Assumes number has already been converted to 0-11 scale.
	 */
	public static boolean ValidateStartTime(int intStartTime)
	{
		boolean blnValid = false;
		if (intStartTime >= 0){
			//end time is no later than 11 (4am), so start can be no later than 10 (3am).
			if(intStartTime <= 10){
				blnValid = true;
			}
			else{
				System.out.println("Start time must be between 5pm and 3am.");
			}
		}
		else{
			System.out.println("Start time must be between 5pm and 3am.");
		}
		
		return blnValid;
	}
	
	/**
	 * method: ValidateEndTime
	 * @param intEndTime
	 * @return blnValid
	 * ReadIntegerFromUser already checks for numeric input, so this just needs
	 * to check that the number makes sense with the others.
	 * Assumes number has already been converted to 0-11 scale.
	 */
	public static boolean ValidateEndTime(int intEndTime, int intStartTime)
	{
		boolean blnValid = false;
		
		//start time is not before 0 (5pm) so end cannot be before 1 (6pm)
		if (intEndTime >= 1)
		{
			//end time is no later than 11 (4am)
			if(intEndTime <= 11)
			{
				//end time must be after start time
				if(intEndTime > intStartTime)
				{
					blnValid = true;
				}
				else System.out.println("End time must be after start time.");
			}
			else System.out.println("End time must be between 6pm and 4am.");
		}	
		else System.out.println("End time must be between 6pm and 4am.");
		
		return blnValid;
	}
	
	/**
	 * method: ValidateBedtime
	 * @param intBedtime
	 * @return blnValid
	 * ReadIntegerFromUser already checks for numeric input, so this just needs
	 * to check that the number makes sense with the others.
	 * Assumes number has already been converted to 0-11 scale.
	 */
	public static boolean ValidateBedtime(int intBedtime, int intStartTime, int intEndTime)
	{
		boolean blnValid = false;
		
		//if they want to put the kid to bed at 5pm or 4am that's their decision
		if (intBedtime >= 0 && intBedtime <= 11)
		{
			//make sure bedtime is between start and end time
			if(intBedtime >= intStartTime && intBedtime <= intEndTime)
			{
				blnValid = true;
			}
			else
			{
				System.out.println("Bedtime must be between start and end times.");
			}
		}	
		else
		{
			System.out.println("Bedtime must be between start and end times.");
		}
		
		return blnValid;
	}
	
	
	/**
	 * method: CalculateBaseHours
	 * @param intStartTime
	 * @param intBedtime
	 * @return intBaseHours
	 */
	public static int CalculateBaseHours(int intStartTime, int intBedtime)
	{
		int intBaseHours = 0;

		//if bedtime is before or at midnight, standard rate behavior
		if (intBedtime <= 7) {
			intBaseHours = intBedtime - intStartTime;
		}
		//otherwise, midnight rates override bedtime rates, so base rate goes to midnight.
		else {
			intBaseHours = 7 - intStartTime;
		}
		
		return intBaseHours;
	}
	
	
	/**
	 * method: CalculateEveningHours
	 * @param intBedtime
	 * @param intEndTime
	 * @return intEveningHours
	 */
	public static int CalculateEveningHours(int intBedtime, int intEndTime)
	{
		int intEveningHours = 0;

		//end before midnight
		if(intEndTime <= 7) {
			intEveningHours = intEndTime - intBedtime;
		}
		//end after midnight
		else {
			//if bedtime is before or at midnight, standard rate behavior
			if (intBedtime <= 7) {
				//in conversion, midnight becomes 7
				intEveningHours = 7 - intBedtime;
			}
			//if bedtime is after midnight, no evening hours - variable can stay 0
		}
		
		return intEveningHours;
	}
	
	
	/**
	 * method: CalculateLateHours
	 * @param intEndTime
	 * @return intLateHours
	 */
	public static int CalculateLateHours(int intEndTime)
	{
		int intLateHours = 0;
		//check if end time is after midnight (7 after conversion)
		if(intEndTime > 7) {
			//even if bedtime is after midnight, late hours take over
			intLateHours = intEndTime - 7;
		}
		return intLateHours;
	}
	
	
	/**
	 * method: CalculatePay
	 * @param intBaseHours (start to bedtime)
	 * @param intEveningHours (bedtime to midnight)
	 * @param intLateHours (midnight to end time)
	 * @return intTotalPay
	 */
	public static int CalculatePay(int intBaseHours, int intEveningHours, int intLateHours)
	{
		int intTotalPay = 0;
		int intBaseRate = 12;
		int intEveningRate = 8;
		int intLateRate = 16;

		//get base pay
		intTotalPay += (intBaseHours * intBaseRate);
		//add evening pay
		intTotalPay += (intEveningHours * intEveningRate);
		//add late pay
		intTotalPay += (intLateHours * intLateRate);		
		
		return intTotalPay;
	}

}
