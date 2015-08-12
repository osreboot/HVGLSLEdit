package com.osreboot.glsledit.node;

import java.util.ArrayList;

import org.newdawn.slick.Color;

import com.osreboot.glsledit.Node;
import com.osreboot.glsledit.Pin;
import com.osreboot.glsledit.pin.PinFloat;

public class NodeArbitraryFloat extends Node{

	private PinFloat next;

	private float value;
	
	public NodeArbitraryFloat(float valueArg, float x, float y){
		super("float " + valueArg, x, y, Color.orange);
		value = valueArg;
		ArrayList<Pin> outputs = new ArrayList<>();
		next = new PinFloat(this, "output"){
			@Override
			public String getOutput(){
				return value + "";
			}
		};
		outputs.add(next);
		setOutputs(outputs);
	}

	@Override
	public ArrayList<String> getContent(){
		return null;
	}

	@Override
	public Node getNext(){
		return null;
	}

}
