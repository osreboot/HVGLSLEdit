package com.osreboot.glsledit.node.arithmetic;

import java.util.ArrayList;
import java.util.Arrays;

import com.osreboot.glsledit.Node;
import com.osreboot.glsledit.Pin;
import com.osreboot.glsledit.pin.PinColor;

public class NodeColorSubtract extends Node{

	private PinColor input1, input2, output;

	public NodeColorSubtract(float x, float y){
		super("color subtract", x, y, Node.COLOR_MATH_OPERATOR);
		input1 = new PinColor(this, "1");
		input2 = new PinColor(this, "2");
		setInputs(new ArrayList<Pin>(Arrays.asList(input1, input2)));
		output = new PinColor(this, "out"){
			@Override
			public String getOutput(){
				return getContent().get(0);
			}
		};
		setOutputs(new ArrayList<Pin>(Arrays.asList(output)));
	}

	@Override
	public ArrayList<String> getContent(){
		return new ArrayList<String>(Arrays.asList("(" + Pin.getConnectionOutput(input1, Node.DEFAULT_COLOR) + " - " + Pin.getConnectionOutput(input2, Node.DEFAULT_COLOR) + ")"));
	}

	@Override
	public Node getNext(){
		return null;
	}

}