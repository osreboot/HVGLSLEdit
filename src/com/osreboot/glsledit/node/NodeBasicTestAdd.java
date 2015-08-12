package com.osreboot.glsledit.node;

import java.util.ArrayList;

import org.newdawn.slick.Color;

import com.osreboot.glsledit.Compiler;
import com.osreboot.glsledit.Node;
import com.osreboot.glsledit.Pin;
import com.osreboot.glsledit.pin.PinExecute;
import com.osreboot.glsledit.pin.PinFloat;

public class NodeBasicTestAdd extends Node{

	private PinExecute previous, next;
	private PinFloat input;

	public NodeBasicTestAdd(){
		super("lightness add", 512, 512, new Color(1, 0.5f, 0));
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
		if(connection != null) value = connection.getParent().getContent().get(0); else value = "0";
		ArrayList<String> list = new ArrayList<>();
		list.add("color = vec4(color.r + " + value + ", color.g + " + value + ", color.b + " + value + ", color.a);");
		return list;
	}

	@Override
	public Node getNext(){
		return next.getConnection();
	}

}
