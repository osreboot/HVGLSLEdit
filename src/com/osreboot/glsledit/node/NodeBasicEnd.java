package com.osreboot.glsledit.node;

import java.util.ArrayList;
import java.util.Arrays;

import org.newdawn.slick.Color;

import com.osreboot.glsledit.Node;
import com.osreboot.glsledit.Pin;
import com.osreboot.glsledit.pin.PinExecute;

public class NodeBasicEnd extends Node{

	private PinExecute previous;
	
	public NodeBasicEnd(float x, float y){
		super("var color end", x, y, Color.red);
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