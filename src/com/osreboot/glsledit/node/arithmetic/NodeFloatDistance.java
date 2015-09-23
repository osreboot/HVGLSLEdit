package com.osreboot.glsledit.node.arithmetic;

import java.util.ArrayList;
import java.util.Arrays;

import com.osreboot.glsledit.Node;
import com.osreboot.glsledit.Pin;
import com.osreboot.glsledit.pin.PinFloat;

public class NodeFloatDistance extends Node{

	private PinFloat input1, input2, input3, input4, output;

	public NodeFloatDistance(float x, float y){
		super("float distance", x, y, Node.WIDTH_NORMAL, Node.COLOR_MATH_OPERATOR);
		input1 = new PinFloat(this, "x 1");
		input2 = new PinFloat(this, "y 1");
		input3 = new PinFloat(this, "x 2");
		input4 = new PinFloat(this, "y 2");
		setInputs(new ArrayList<Pin>(Arrays.asList(input1, input2, input3, input4)));
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
		return new ArrayList<String>(Arrays.asList("distance(vec2(" + Pin.getConnectionOutput(input1, "0") + ", " + Pin.getConnectionOutput(input2, "0") + "), vec2(" + Pin.getConnectionOutput(input3, "0") + ", " + Pin.getConnectionOutput(input4, "0") + "))"));
	}

	@Override
	public Node getNext(){
		return null;
	}
	
}
