package com.osreboot.glsledit.node;

import java.util.ArrayList;

import org.newdawn.slick.Color;

import com.osreboot.glsledit.Node;
import com.osreboot.glsledit.Pin;
import com.osreboot.glsledit.pin.PinExecute;
import com.osreboot.glsledit.pin.PinFloat;

public class NodeBasicTestSubtract extends Node{

	private PinExecute previous, next;
	private PinFloat input;

	public NodeBasicTestSubtract(float x, float y){
		super("lightness subtract", x, y, new Color(1, 0, 0.5f));
		ArrayList<Pin> inputs = new ArrayList<>();
		previous = new PinExecute(this);
		inputs.add(previous);
		input = new PinFloat(this, "lightness");
		inputs.add(input);
		setInputs(inputs);
		ArrayList<Pin> outputs = new ArrayList<>();
		next = new PinExecute(this);
		outputs.add(next);
		setOutputs(outputs);
	}

	@Override
	public ArrayList<String> getContent(){
		String value = "";
		Pin connection = Pin.findOutputConnection(input);
		if(connection != null) value = connection.getOutput(); else value = "0";
		ArrayList<String> list = new ArrayList<>();
		list.add("color = vec4(color.r - " + value + ", color.g - " + value + ", color.b - " + value + ", color.a);");
		return list;
	}

	@Override
	public Node getNext(){
		return next.getConnection();
	}

}
