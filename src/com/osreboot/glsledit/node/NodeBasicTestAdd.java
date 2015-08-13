package com.osreboot.glsledit.node;

import java.util.ArrayList;
import java.util.Arrays;

import com.osreboot.glsledit.Node;
import com.osreboot.glsledit.Pin;
import com.osreboot.glsledit.pin.PinExecute;
import com.osreboot.glsledit.pin.PinFloat;

public class NodeBasicTestAdd extends Node{

	private PinExecute previous, next;
	private PinFloat input;

	public NodeBasicTestAdd(float x, float y){
		super("lightness add", x, y, Node.COLOR_ADD);
		previous = new PinExecute(this);
		input = new PinFloat(this, "lightness");
		setInputs(new ArrayList<Pin>(Arrays.asList(previous, input)));
		next = new PinExecute(this);
		setOutputs(new ArrayList<Pin>(Arrays.asList(next)));
	}

	@Override
	public ArrayList<String> getContent(){
		String value = Pin.getConnectionOutput(input, "0");
		
		return new ArrayList<String>(Arrays.asList("color = vec4(color.r + " + value + ", color.g + " + value + ", color.b + " + value + ", color.a);"));
	}

	@Override
	public Node getNext(){
		return next.getConnection();
	}

}
