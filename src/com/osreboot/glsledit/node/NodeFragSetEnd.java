package com.osreboot.glsledit.node;

import java.util.ArrayList;
import java.util.Arrays;

import com.osreboot.glsledit.Node;
import com.osreboot.glsledit.Pin;
import com.osreboot.glsledit.pin.PinExecute;

public class NodeFragSetEnd extends Node{

	private PinExecute previous;
	
	public NodeFragSetEnd(float x, float y){
		super("var color end", x, y, Node.COLOR_END);
		previous = new PinExecute(this);
		setInputs(new ArrayList<Pin>(Arrays.asList(previous)));
	}

	@Override
	public ArrayList<String> getContent(){
		return new ArrayList<String>(Arrays.asList("gl_FragColor = color; }"));
	}

	@Override
	public Node getNext(){
		return null;
	}
	
}