package com.osreboot.glsledit.node.variable;

import java.util.ArrayList;
import java.util.Arrays;

import com.osreboot.glsledit.Node;
import com.osreboot.glsledit.Pin;
import com.osreboot.glsledit.pin.PinExecute;
import com.osreboot.glsledit.pin.PinFloat;

public class NodeVariableFloatDefine extends Node{

	private PinExecute previous, next;
	private String path;
	private PinFloat input;
	
	public NodeVariableFloatDefine(String pathArg, float x, float y){
		super("def " + pathArg, x, y, Node.COLOR_VARIABLE);
		previous = new PinExecute(this);
		path = pathArg;
		input = new PinFloat(this, "in");
		setInputs(new ArrayList<Pin>(Arrays.asList(previous, input)));
		next = new PinExecute(this);
		setOutputs(new ArrayList<Pin>(Arrays.asList(next)));
	}

	@Override
	public ArrayList<String> getContent(){
		return new ArrayList<String>(Arrays.asList("float " + path + " = " + Pin.getConnectionOutput(input, "0") + ";"));
	}

	@Override
	public Node getNext(){
		return next.getConnection();
	}

}