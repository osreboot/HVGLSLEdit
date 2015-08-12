package com.osreboot.glsledit.node;

import java.util.ArrayList;

import org.newdawn.slick.Color;

import com.osreboot.glsledit.Node;
import com.osreboot.glsledit.Pin;
import com.osreboot.glsledit.pin.PinFloat;

public class NodeArbitraryFloat extends Node{

	private PinFloat next;

	private float value;
	
	public NodeArbitraryFloat(float valueArg){
		super("float " + valueArg, 512, 512, Color.orange);
		value = valueArg;
		ArrayList<Pin> outputs = new ArrayList<>();
		next = new PinFloat(this, "output");
		outputs.add(next);
		setOutputs(outputs);
	}

	@Override
	public ArrayList<String> getContent(){
		ArrayList<String> list = new ArrayList<>();
		list.add(value + "");
		return list;
	}

	@Override
	public Node getNext(){
		return null;
	}

}
