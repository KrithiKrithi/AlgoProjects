package algo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class ShiftManager {
	private  ArrayList<Shift> availableShiftList = new ArrayList<Shift>();
	// requiredDrivers : Stores required number of drivers at each hourly interval; interval is represented in minutes
	private  HashMap<Double,Double> originalRequiredDrivers = new HashMap<Double, Double>();
	private  HashMap<Double,Double> curRequiredDrivers = new HashMap<Double, Double>();
	
	public ShiftManager()
	{
		Initialize();
	}
	
	private void Initialize()
	{
		loadAvailableShifts();
		loadRequirements();
	}
	
	private void loadAvailableShifts()
	{
		availableShiftList.add(new Shift(525.0,660.0));
		availableShiftList.add(new Shift(645.0,720.0));
		availableShiftList.add(new Shift(645.0,780.0));
		availableShiftList.add(new Shift(645.0,840.0));
		availableShiftList.add(new Shift(660.0,780.0));
		availableShiftList.add(new Shift(660.0,840.0));
		availableShiftList.add(new Shift(660.0,900.0));
		availableShiftList.add(new Shift(660.0,960.0));
		availableShiftList.add(new Shift(720.0,900.0));
		availableShiftList.add(new Shift(720.0,1020.0));
		availableShiftList.add(new Shift(780.0,960.0));
		availableShiftList.add(new Shift(780.0,1080.0));
		availableShiftList.add(new Shift(840.0,1020.0));
		availableShiftList.add(new Shift(840.0,1140.0));
		availableShiftList.add(new Shift(900.0,1080.0));
		availableShiftList.add(new Shift(900.0,1200.0));
		availableShiftList.add(new Shift(960.0,1140.0));
		availableShiftList.add(new Shift(960.0,1260.0));
		availableShiftList.add(new Shift(1020.0,1200.0));
		availableShiftList.add(new Shift(1020.0,1320.0));
		availableShiftList.add(new Shift(1080.0,1200.0));
		availableShiftList.add(new Shift(1080.0,1260.0));
		availableShiftList.add(new Shift(1080.0,1380.0));
		availableShiftList.add(new Shift(1140.0,1260.0));
		availableShiftList.add(new Shift(1140.0,1320.0));
		availableShiftList.add(new Shift(1140.0,1440.0));
		availableShiftList.add(new Shift(1200.0,1380.0));
		availableShiftList.add(new Shift(1200.0,1500.0));
		availableShiftList.add(new Shift(1260.0,1440.0));
		availableShiftList.add(new Shift(1260.0,1560.0));
		availableShiftList.add(new Shift(1320.0,1500.0));
		availableShiftList.add(new Shift(1320.0,1620.0));
		availableShiftList.add(new Shift(1380.0,1560.0));
		availableShiftList.add(new Shift(1380.0,1680.0));
		availableShiftList.add(new Shift(1425.0,1620.0));
		availableShiftList.add(new Shift(1500.0,1680.0));
		availableShiftList.add(new Shift(1560.0,1740.0));
		
		Collections.shuffle(availableShiftList);
	}

	private  void loadRequirements()
	{
		originalRequiredDrivers.put(600.0,2.0);
		originalRequiredDrivers.put(660.0,8.0);
		originalRequiredDrivers.put(720.0,11.0);
		originalRequiredDrivers.put(780.0,10.0);
		originalRequiredDrivers.put(840.0,10.0);
		originalRequiredDrivers.put(900.0,11.0);
		originalRequiredDrivers.put(960.0,12.0);
		originalRequiredDrivers.put(1020.0,16.0);
		originalRequiredDrivers.put(1080.0,20.0);
		originalRequiredDrivers.put(1140.0,18.0);
		originalRequiredDrivers.put(1200.0,17.0);
		originalRequiredDrivers.put(1260.0,13.0);
		originalRequiredDrivers.put(1320.0,10.0);
		originalRequiredDrivers.put(1380.0,11.0);
		originalRequiredDrivers.put(1440.0,9.0);
		originalRequiredDrivers.put(1500.0,9.0);
		originalRequiredDrivers.put(1560.0,7.0);
		originalRequiredDrivers.put(1620.0,0.0);
		originalRequiredDrivers.put(1680.0,0.0);
		originalRequiredDrivers.put(1740.0,0.0);
		
		resetDriverRequirements();
	}
	
	public void resetDriverRequirements()
	{
		curRequiredDrivers = (HashMap<Double,Double>) originalRequiredDrivers.clone();
	}
	
	public Shift getShiftAt(int index)
	{
		return availableShiftList.get(index);
	}
	
	public double getDriversRequiredAtFromOriginalList(double interval)
	{
		if(!originalRequiredDrivers.containsKey(interval))
			return 0.0;
		return originalRequiredDrivers.get(interval);
	}
	
	public double getTotalDriversRequiredAtFromOriginalList(double sInterval, double eInterval)
	{
		
		double requiredDrivers = 0.0;
		for(double interval = sInterval; interval<=eInterval; interval += (15)) // checks for Drivers requirement at every 15 minute interval
		{
			if(!originalRequiredDrivers.containsKey(interval))
				continue;
			requiredDrivers += originalRequiredDrivers.get(interval);
			
		}
		return requiredDrivers;
	}
	
	public double getDriversRequiredAt(double interval)
	{
		if(!curRequiredDrivers.containsKey(interval))
			return 0.0;
		return curRequiredDrivers.get(interval);
	}
	
	public void removeDriversRequiredAt(double interval)
	{
		if(curRequiredDrivers.containsKey(interval))
			curRequiredDrivers.remove(interval);
	}
	
	public double geMaxtDriversRequiredInThisWindow(double sInterval, double eInterval)
	{
		double maxRequiredDrivers = -1.0;
		double requiredDrivers = 0.0;
		for(double interval = sInterval; interval<=eInterval; interval += (15)) // checks for Drivers requirement at every 15 minute interval
		{
			if(!curRequiredDrivers.containsKey(interval))
				continue;
			
			requiredDrivers = curRequiredDrivers.get(interval);
			if(maxRequiredDrivers >=0)
			{
				if(requiredDrivers > maxRequiredDrivers)
					maxRequiredDrivers = requiredDrivers;
			}
			else
				maxRequiredDrivers = requiredDrivers;
		}
		return maxRequiredDrivers;
	}
	
	public void removeDriversRequiredInThisWindow(double sInterval, double eInterval)
	{
		for(double interval = sInterval; interval<=eInterval; interval += (15)) // drivers requirement at every 15 minute interval
		{
			removeDriversRequiredAt(interval);	
		}
	}
	
	
	public double getTotalDriversRequiredInThisWindow(double sInterval, double eInterval)
	{
		
		double requiredDrivers = 0.0;
		for(double interval = sInterval; interval<=eInterval; interval += (15)) // checks for Drivers requirement at every 15 minute interval
		{
			if(!curRequiredDrivers.containsKey(interval))
				continue;
			requiredDrivers += curRequiredDrivers.get(interval);
			
		}
		return requiredDrivers;
	}
	
	
	public int getAvailableShiftSize()
	{
		return availableShiftList.size();
	}

}
