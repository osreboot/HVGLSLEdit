package com.osreboot.glsledit.node;

import java.util.ArrayList;
import java.util.Arrays;

import com.osreboot.glsledit.Node;
import com.osreboot.glsledit.Pin;
import com.osreboot.glsledit.pin.PinColor;
import com.osreboot.glsledit.pin.PinExecute;

public class NodeVariableColorSet extends Node{

	private PinExecute previous, next;
	private String path;
	private PinColor input;
	
	public NodeVariableColorSet(String pathArg, float x, float y){
		super("color variable set", x, y, Node.COLOR_VARIABLE);
		previous = new PinExecute(this);
		path = pathArg;
		input = new PinColor(this, "input");
		setInputs(new ArrayList<Pin>(Arrays.asList(previous, input)));
		next = new PinExecute(this);
		setOutputs(new ArrayList<Pin>(Arrays.asList(next)));
	}

	@Override
	public ArrayList<String> getContent(){
		return new ArrayList<String>(Arrays.asList(path + " = " + Pin.getConnectionOutput(input, Node.DEFAULT_COLOR) + ";"));
	}

	@Override
	public Node getNext(){
		return next.getConnection();
	}

}