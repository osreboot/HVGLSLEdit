package com.osreboot.glsledit.node;

import java.util.ArrayList;

import org.newdawn.slick.Color;

import com.osreboot.glsledit.Node;
import com.osreboot.glsledit.Pin;
import com.osreboot.glsledit.pin.PinExecute;

public class NodeBasicTestAdd extends Node{

	private PinExecute previous, next;

	public NodeBasicTestAdd(){
		super("add 0.1", 512, 512, Color.pink);
		ArrayList<Pin> inputs = new ArrayList<>();
		previous = new PinExecute(this);
		inputs.add(previous);
		setInputs(inputs);
		ArrayList<Pin> outputs = new ArrayList<>();
		next = new PinExecute(this);
		outputs.add(next);
		setOutputs(outputs);
	}

	@Override
	public ArrayList<String> getContent(){
		ArrayList<String> list = new ArrayList<>();
		list.add("color = vec4(color.r + 0.1, color.g + 0.1, color.b + 0.1, color.a);");
		return list;
	}

	@Override
	public Node getNext(){
		return next.getConnection();
	}

}
