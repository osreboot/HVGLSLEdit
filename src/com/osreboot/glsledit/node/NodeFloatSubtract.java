package com.osreboot.glsledit.node;

import java.util.ArrayList;

import com.osreboot.glsledit.Node;
import com.osreboot.glsledit.Pin;
import com.osreboot.glsledit.pin.PinFloat;

public class NodeFloatSubtract extends Node{

	private PinFloat input1, input2, output;

	public NodeFloatSubtract(float x, float y){
		super("float subtract", x, y, Node.COLOR_SUB);
		ArrayList<Pin> inputs = new ArrayList<>();
		input1 = new PinFloat(this, "1");
		inputs.add(input1);
		input2 = new PinFloat(this, "2");
		inputs.add(input2);
		setInputs(inputs);
		ArrayList<Pin> outputs = new ArrayList<>();
		output = new PinFloat(this, "out"){
			@Override
			public String getOutput(){
				return getContent().get(0);
			}
		};
		outputs.add(output);
		setOutputs(outputs);
	}

	@Override
	public ArrayList<String> getContent(){
		String value = "";
		Pin connection = Pin.findOutputConnection(input1);
		if(connection != null) value = connection.getOutput(); else value = "0";
		
		String value2 = "";
		Pin connection2 = Pin.findOutputConnection(input2);
		if(connection2 != null) value2 = connection2.getOutput(); else value2 = "0";
		
		ArrayList<String> list = new ArrayList<>();
		list.add("(" + value + " - " + value2 + ")");
		return list;
	}

	@Override
	public Node getNext(){
		return null;
	}

}
