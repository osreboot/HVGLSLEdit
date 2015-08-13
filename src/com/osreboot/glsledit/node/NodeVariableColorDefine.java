package com.osreboot.glsledit.node;

import java.util.ArrayList;
import java.util.Arrays;

import com.osreboot.glsledit.Node;
import com.osreboot.glsledit.Pin;
import com.osreboot.glsledit.pin.PinColor;
import com.osreboot.glsledit.pin.PinExecute;

public class NodeVariableColorDefine extends Node{

	private PinExecute previous, next;
	private String path;
	private PinColor input;
	
	public NodeVariableColorDefine(String pathArg, float x, float y){
		super("def " + pathArg, x, y, Node.COLOR_VARIABLE);
		previous = new PinExecute(this);
		path = pathArg;
		input = new PinColor(this, "in");
		setInputs(new ArrayList<Pin>(Arrays.asList(previous, input)));
		next = new PinExecute(this);
		setOutputs(new ArrayList<Pin>(Arrays.asList(next)));
	}

	@Override
	public ArrayList<String> getContent(){
		return new ArrayList<String>(Arrays.asList("vec4 " + path + " = " + Pin.getConnectionOutput(input, Node.DEFAULT_COLOR) + ";"));
	}

	@Override
	public Node getNext(){
		return next.getConnection();
	}

}