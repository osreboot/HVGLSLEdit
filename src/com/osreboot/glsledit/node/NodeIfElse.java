package com.osreboot.glsledit.node;

import java.util.ArrayList;
import java.util.Arrays;

import com.osreboot.glsledit.Node;
import com.osreboot.glsledit.Pin;
import com.osreboot.glsledit.pin.PinExecute;

public class NodeIfElse extends Node{

	private PinExecute previous, next;
	
	public NodeIfElse(float x, float y){
		super("else", x, y, Node.WIDTH_EXTRASHORT, Node.COLOR_BLOCK);
		previous = new PinExecute(this);
		setInputs(new ArrayList<Pin>(Arrays.asList(previous)));
		next = new PinExecute(this);
		setOutputs(new ArrayList<Pin>(Arrays.asList(next)));
	}

	@Override
	public ArrayList<String> getContent(){
		return new ArrayList<String>(Arrays.asList("}else{"));
	}

	@Override
	public Node getNext(){
		return next.getConnection();
	}

}