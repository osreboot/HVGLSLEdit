package com.osreboot.glsledit.node;

import java.util.ArrayList;
import java.util.Arrays;

import com.osreboot.glsledit.Node;
import com.osreboot.glsledit.Pin;
import com.osreboot.glsledit.pin.PinExecute;
import com.osreboot.glsledit.pin.PinFloat;

public class NodeFor extends Node{

	private PinExecute previous, next;
	private String var;
	private PinFloat count;
	
	public NodeFor(String varArg, float x, float y){
		super("for " + varArg, x, y, Node.WIDTH_SHORT, Node.COLOR_BLOCK);
		previous = new PinExecute(this);
		var = varArg;
		count = new PinFloat(this, "count");
		setInputs(new ArrayList<Pin>(Arrays.asList(previous, count)));
		next = new PinExecute(this);
		setOutputs(new ArrayList<Pin>(Arrays.asList(next)));
	}

	public String getVar(){
		return var;
	}

	public void setVar(String varArg){
		var = varArg;
		setName("for " + varArg);
	}
	
	@Override
	public ArrayList<String> getContent(){
		return new ArrayList<String>(Arrays.asList("for(float " + var + " = 0; " + var + " < " + Pin.getConnectionOutput(count, "0") + "; " + var + "++){"));
	}

	@Override
	public Node getNext(){
		return next.getConnection();
	}

}
