package algo;

public class Shift {
	
	// Shift start/ end times are represented in minutes
	private double shiftStartTime;
	private double shiftEndTime;
	private double shiftLength;
	private int numOfDrivers;
	
	public Shift()
	{
		shiftStartTime = 0.0;
		shiftEndTime = 0.0;
		shiftLength = shiftStartTime - shiftEndTime;
		numOfDrivers = 0;
	}

	public Shift(double starttime, double endtime)
	{
		shiftStartTime = starttime;
		shiftEndTime = endtime;
		shiftLength = shiftEndTime-shiftStartTime;
	}
	
	public double getShiftLength()
	{
		return shiftLength;
	}
	
	public double getShiftStartTime()
	{
		return shiftStartTime;
	}
	
	public double getShiftEndTime()
	{
		return shiftEndTime;
	}
	
	public void setShiftStartTime(double startTime)
	{
		shiftStartTime = startTime;
	}
	
	public void setShiftEndTime(double endTime)
	{
		shiftEndTime = endTime;
	}
	
	public double getNumOfDrivers()
	{
		return numOfDrivers;
	}
	
	public void setNumOfDrivers(int nDrivers)
	{
		numOfDrivers = nDrivers;
	}

	public String toString()
	{
		return ("Start time: " + shiftStartTime + "\t End time: " + shiftEndTime + "\t No. of drivers: " + numOfDrivers);
	}
	
}
