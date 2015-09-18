package com.osreboot.glsledit.node;

import java.util.ArrayList;
import java.util.Arrays;

import com.osreboot.glsledit.Node;
import com.osreboot.glsledit.Pin;
import com.osreboot.glsledit.pin.PinExecute;

public class NodeEndFragSet extends Node{

	private PinExecute previous;
	
	public NodeEndFragSet(float x, float y){
		super("end var color", x, y, Node.WIDTH_SHORT, Node.COLOR_END);
		previous = new PinExecute(this);
		setInputs(new ArrayList<Pin>(Arrays.asList(previous)));
	}

	@Override
	public ArrayList<String> getContent(){
		return new ArrayList<String>(Arrays.asList("gl_FragColor = color;", "}"));
	}

	@Override
	public Node getNext(){
		return null;
	}
	
}