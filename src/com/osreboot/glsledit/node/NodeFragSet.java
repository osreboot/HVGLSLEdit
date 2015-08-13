package com.osreboot.glsledit.node;

import java.util.ArrayList;
import java.util.Arrays;

import com.osreboot.glsledit.Node;
import com.osreboot.glsledit.Pin;
import com.osreboot.glsledit.pin.PinColor;
import com.osreboot.glsledit.pin.PinExecute;

public class NodeFragSet extends Node{

	private PinExecute previous, next;
	private PinColor input;
	
	public NodeFragSet(float x, float y){
		super("fragment color", x, y, Node.COLOR_VARIABLE);
		previous = new PinExecute(this);
		input = new PinColor(this, "in");
		setInputs(new ArrayList<Pin>(Arrays.asList(previous, input)));
		next = new PinExecute(this);
		setOutputs(new ArrayList<Pin>(Arrays.asList(next)));
	}

	@Override
	public ArrayList<String> getContent(){
		return new ArrayList<String>(Arrays.asList("gl_FragColor = " + Pin.getConnectionOutput(input, Node.DEFAULT_COLOR) + ";"));
	}

	@Override
	public Node getNext(){
		return next.getConnection();
	}

}
