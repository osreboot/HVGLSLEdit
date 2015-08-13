package com.osreboot.glsledit.node;

import java.util.ArrayList;
import java.util.Arrays;

import org.newdawn.slick.Color;

import com.osreboot.glsledit.Node;
import com.osreboot.glsledit.Pin;
import com.osreboot.glsledit.pin.PinExecute;

public class NodeEnd extends Node{

	private PinExecute previous;
	
	public NodeEnd(float x, float y){
		super("end", x, y, Color.red);
		previous = new PinExecute(this);
		setInputs(new ArrayList<Pin>(Arrays.asList(previous)));
	}

	@Override
	public ArrayList<String> getContent(){
		return new ArrayList<String>(Arrays.asList("}"));
	}

	@Override
	public Node getNext(){
		return null;
	}
	
}
