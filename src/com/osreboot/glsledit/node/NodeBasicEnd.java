package com.osreboot.glsledit.node;

import java.util.ArrayList;

import org.newdawn.slick.Color;

import com.osreboot.glsledit.Node;
import com.osreboot.glsledit.Pin;
import com.osreboot.glsledit.pin.PinExecute;

public class NodeBasicEnd extends Node{

	private PinExecute previous;
	
	public NodeBasicEnd(float x, float y){
		super("end", x, y, Color.red);
		ArrayList<Pin> inputs = new ArrayList<>();
		previous = new PinExecute(this);
		inputs.add(previous);
		setInputs(inputs);
	}

	@Override
	public ArrayList<String> getContent(){
		ArrayList<String> list = new ArrayList<>();
		list.add("gl_FragColor = color; }");
		return list;
	}

	@Override
	public Node getNext(){
		return null;
	}
	
}