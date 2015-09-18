package com.osreboot.glsledit.node;

import java.util.ArrayList;
import java.util.Arrays;

import com.osreboot.glsledit.Node;
import com.osreboot.glsledit.Pin;
import com.osreboot.glsledit.pin.PinBoolean;
import com.osreboot.glsledit.pin.PinExecute;

public class NodeIf extends Node{

	private PinExecute previous, next;
	private PinBoolean input;
	
	public NodeIf(float x, float y){
		super("if", x, y, Node.WIDTH_EXTRASHORT, Node.COLOR_BLOCK);
		previous = new PinExecute(this);
		input = new PinBoolean(this, "in");
		setInputs(new ArrayList<Pin>(Arrays.asList(previous, input)));
		next = new PinExecute(this);
		setOutputs(new ArrayList<Pin>(Arrays.asList(next)));
	}

	@Override
	public ArrayList<String> getContent(){
		return new ArrayList<String>(Arrays.asList("if(" + Pin.getConnectionOutput(input, "true") + "){"));
	}

	@Override
	public Node getNext(){
		return next.getConnection();
	}

}
