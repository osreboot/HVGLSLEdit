package com.osreboot.glsledit.node;

import java.util.ArrayList;
import java.util.Arrays;

import com.osreboot.glsledit.Node;
import com.osreboot.glsledit.Pin;
import com.osreboot.glsledit.pin.PinFloat;

public class NodeFloatAdd extends Node{

	private PinFloat input1, input2, output;

	public NodeFloatAdd(float x, float y){
		super("float add", x, y, Node.COLOR_ADD);
		input1 = new PinFloat(this, "1");
		input2 = new PinFloat(this, "2");
		setInputs(new ArrayList<Pin>(Arrays.asList(input1, input2)));
		output = new PinFloat(this, "out"){
			@Override
			public String getOutput(){
				return getContent().get(0);
			}
		};
		setOutputs(new ArrayList<Pin>(Arrays.asList(output)));
	}

	@Override
	public ArrayList<String> getContent(){
		String value = "";
		Pin connection = Pin.findOutputConnection(input1);
		if(connection != null) value = connection.getOutput(); else value = "0";
		
		String value2 = "";
		Pin connection2 = Pin.findOutputConnection(input2);
		if(connection2 != null) value2 = connection2.getOutput(); else value2 = "0";
		
		return new ArrayList<String>(Arrays.asList("(" + value + " + " + value2 + ")"));
	}

	@Override
	public Node getNext(){
		return null;
	}

}
