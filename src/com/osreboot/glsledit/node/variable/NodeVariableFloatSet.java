package com.osreboot.glsledit.node.variable;

import java.util.ArrayList;
import java.util.Arrays;

import com.osreboot.glsledit.Node;
import com.osreboot.glsledit.Pin;
import com.osreboot.glsledit.pin.PinExecute;
import com.osreboot.glsledit.pin.PinFloat;

public class NodeVariableFloatSet extends Node{

	private PinExecute previous, next;
	private String path;
	private PinFloat input;
	
	public NodeVariableFloatSet(float x, float y, String pathArg){
		super("set " + pathArg, x, y, Node.WIDTH_NORMAL, Node.COLOR_VARIABLE);
		previous = new PinExecute(this);
		path = pathArg;
		input = new PinFloat(this, "in");
		setInputs(new ArrayList<Pin>(Arrays.asList(previous, input)));
		next = new PinExecute(this);
		setOutputs(new ArrayList<Pin>(Arrays.asList(next)));
	}

	public String getPath(){
		return path;
	}

	public void setPath(String pathArg){
		path = pathArg;
		setName("set " + pathArg);
	}
	
	@Override
	public ArrayList<String> getContent(){
		return new ArrayList<String>(Arrays.asList(path + " = " + Pin.getConnectionOutput(input, "0") + ";"));
	}

	@Override
	public Node getNext(){
		return next.getConnection();
	}

}