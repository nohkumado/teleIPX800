package com.nohkumado.teleIPX800;

import java.util.*;

public class LedStatusList
{
	protected ArrayList<Boolean> outputstate = new ArrayList<Boolean>();
	protected ArrayList<Boolean> inputstate = new ArrayList<Boolean>();
	protected ArrayList<String> labelList = new ArrayList<String>();

	public LedStatusList()
	{
		outputstate = new ArrayList<Boolean>();
		inputstate = new ArrayList<Boolean>();
		labelList = new ArrayList<String>();
	}

	public LedStatusList(int capacity)
	{
		outputstate = new ArrayList<Boolean>(capacity);
		inputstate = new ArrayList<Boolean>(capacity);
		labelList = new ArrayList<String>(capacity);
	}

	public int size()
	{
		return outputstate.size();
	}

	public ArrayList<String> getLabelList()
	{ return labelList;}
	public ArrayList<Boolean> getOutPutList()
	{ return outputstate;}
	public ArrayList<Boolean> getInPutList()
	{ return inputstate;}
	
	public boolean add(String label, boolean instate, boolean outstate)
	{
		boolean result = true;
		result &= labelList.add(label);
		result &= inputstate.add(instate);
		result &= outputstate.add(outstate);
		return(result);
	}

	public void add(int index, String label, boolean instate, boolean outstate)
	{
		labelList.add(index, label);
		outputstate.add(index, outstate);
		inputstate.add(index,instate);
	}
	public void clear() 
	{
		labelList.clear();
		outputstate.clear();
		inputstate.clear();
	}
	
	public String getLabel(int index) { return labelList.get(index);}
	public boolean getOutState(int index) { return outputstate.get(index);}
	public boolean getInState(int index) { return inputstate.get(index);}
	public void setOut(int index, boolean p1)
	{
		outputstate.set(index,p1);
	}
	public void setIn(int index, boolean p1)
	{
		inputstate.set(index,p1);
	}

}
