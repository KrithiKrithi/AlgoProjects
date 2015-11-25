package algo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class ShiftAssignment {
	
	private ArrayList<Shift> shifts = new ArrayList<Shift>();
	private double distanceToGoalScore = 0.0;
	
	
	public ShiftAssignment()
	{
		
	}
	
	
	
	public ArrayList<Shift> getShiftAssignment()
	{
		return shifts;
	}
	
	public int getSize()
	{
		return shifts.size();
	}
	
	public double getScore()
	{
		return distanceToGoalScore(shifts);
	}
	
	private double distanceToGoalScore(ArrayList<Shift> shiftList)
	{
		ShiftManager shiftManager = new ShiftManager();
		for(int i =0; i< shiftList.size(); i++)
		{
			Shift curShift = shiftList.get(i);
			double requiredDrivers = 0.0;
			double allocatedDrivers = curShift.getNumOfDrivers();
			for(double interval = curShift.getShiftStartTime(); interval<=curShift.getShiftEndTime(); interval +=15)
			{
				requiredDrivers = shiftManager.getDriversRequiredAtFromOriginalList(interval);
				distanceToGoalScore += Math.abs(allocatedDrivers - requiredDrivers); 
			}
		}
		return distanceToGoalScore;
	}
	
	private double updateScore(ArrayList<Shift>shiftList)
	{
		return distanceToGoalScore(shiftList);
	}
	
	public void createInitialPlan()
	{
		ShiftManager shiftManager = new ShiftManager();
		
		// For now: Using Random shift selection to create initial plan
		// TODO: We could get creative and come up with a reasonable initial plan
		// Pick shifts at random and ramp up to meet requirement
		
		for(int i = 0; i< shiftManager.getAvailableShiftSize(); i++)
		{
			Shift curShift = shiftManager.getShiftAt(i);
			double startInterval = curShift.getShiftStartTime();
			double endInterval = curShift.getShiftEndTime();
			//double curNumDrivers = shiftManager.geMaxtDriversRequiredInThisWindow(startInterval,endInterval);
			// Average driver requirement Note: shift length is in minutes
			double curNumDrivers = (shiftManager.getTotalDriversRequiredInThisWindow(startInterval,endInterval))/(curShift.getShiftLength()/60);
			if(curNumDrivers > 0)
			{
				curShift.setNumOfDrivers((int)Math.round(curNumDrivers));	
				shifts.add(curShift);				
				
				// update score
				updateScore(shifts);

				// remove as requirement is already met
				shiftManager.removeDriversRequiredInThisWindow(startInterval,endInterval);
			}
			
		}
		
	}
	
	public void search(ShiftAssignment curShiftAssignment)
	{
		ShiftManager shiftManager = new ShiftManager();
		ArrayList<Shift> bestShifts = curShiftAssignment.getShiftAssignment();
		double bestScore = curShiftAssignment.getScore();
		
		System.out.println("Score at the beginning of the search : " + bestScore);
		
		ArrayList<Shift> curShifts = curShiftAssignment.getShiftAssignment();
		

		double percentageTolerance = 0.10; // 10%
		int depthPercentage = 100;
		while (depthPercentage > 0)
		{
			ArrayList<Shift> newShifts = curShifts;
			double curScore = updateScore(curShifts);
			shiftManager.resetDriverRequirements();
			
			
			for(int i = 0; i < (newShifts.size() * (depthPercentage/100)); i++ )
			{
				// remove a shift
				newShifts.remove(i);
				
				// add a random shift from available shifts
				int index = (int)(shiftManager.getAvailableShiftSize() * Math.random());
				Shift newShift = shiftManager.getShiftAt(index);
				if(newShifts.contains(newShift))
					continue;
				
				// add drivers to shift
				double startInterval = newShift.getShiftStartTime();
				double endInterval = newShift.getShiftEndTime();
				
				//double newNumDrivers = shiftManager.geMaxtDriversRequiredInThisWindow(startInterval,endInterval);
				// Average driver requirement Note: shift length is in minutes
				double newNumDrivers = (shiftManager.getTotalDriversRequiredInThisWindow(startInterval,endInterval))/(newShift.getShiftLength()/60);
				if(newNumDrivers > 0)
				{
					newShift.setNumOfDrivers((int)Math.round(newNumDrivers));	
					newShifts.add(newShift);	
				
					
					// update score
					double newScore = updateScore(newShifts);

					// remove as requirement is already met
					shiftManager.removeDriversRequiredInThisWindow(startInterval,endInterval);
				}
				
				// update score
				double newScore = updateScore(newShifts);
				// if good, accept and save best
				if(newScore < curScore)
				{
					curShifts = newShifts;
					bestShifts = newShifts;
				}
				// temporarily accept and continue
				else if(newScore - curScore > (percentageTolerance * curScore))
				{
					curShifts = newShifts;
				}
				else
					newShifts = curShifts;					
			}
			depthPercentage -= 10;			
		}
		
		shifts = bestShifts;
		System.out.println("Score at the end of the search : " + getScore());
				
	}
	
	
	
	public String toString()
	{
		String string = "";
		for(int i =0; i< shifts.size(); i++)
		{
			string += shifts.get(i).toString();
			string += '\n';
		}
		return string;
	}
	

}
