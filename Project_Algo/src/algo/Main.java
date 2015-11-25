package algo;

import java.util.ArrayList;
import java.util.HashMap;


public class Main {
	
	public static void main(String[] args)
	{
		System.out.println("Solver for Shift Assignment problem \n");
		Main m = new Main();
		m.findOptimalShiftAssignments();
	}
	
	public Main()
	{
		
	}
	
	private void findOptimalShiftAssignments()
	{
		ShiftAssignment currentShiftAssignment = new ShiftAssignment(); 
		
		long start = System.currentTimeMillis();
		currentShiftAssignment.createInitialPlan();
		long end = System.currentTimeMillis();		
		
		ArrayList<Shift> curShifts = currentShiftAssignment.getShiftAssignment();
		
		double currentScore = currentShiftAssignment.getScore();	
		System.out.println("Time(in miliseconds) to create initial plan " + (end-start) + '\n');
		//System.out.println("Score after initial plan: " + currentScore + '\n');
		System.out.println("Shift assignments after initial plan: \n" + currentShiftAssignment.toString());
		
		ShiftAssignment optimalShiftAssignment = new ShiftAssignment();
		long start1 = System.currentTimeMillis();
		optimalShiftAssignment.search(currentShiftAssignment);
		long end1 = System.currentTimeMillis();	
		
		double optimalScore = optimalShiftAssignment.getScore();	
		System.out.println("Time(in miliseconds) for search " + (end1-start1) + '\n');
		//System.out.println("Score after search: " + optimalScore + '\n');
		System.out.println("Shift assignments after search: \n" + optimalShiftAssignment.toString());
		
	}
	
	

}
