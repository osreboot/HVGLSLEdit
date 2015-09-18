package com.osreboot.glsledit.node.arithmetic;

import java.util.ArrayList;
import java.util.Arrays;

import com.osreboot.glsledit.Node;
import com.osreboot.glsledit.Pin;
import com.osreboot.glsledit.pin.PinFloat;

public class NodeFloatSqrt extends Node{

	private PinFloat input1, output;

	public NodeFloatSqrt(float x, float y){
		super("float sqrt", x, y, Node.WIDTH_NORMAL, Node.COLOR_MATH_OPERATOR_ADV);
		input1 = new PinFloat(this, "1");
		setInputs(new ArrayList<Pin>(Arrays.asList(input1)));
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
		return new ArrayList<String>(Arrays.asList("sqrt(" + Pin.getConnectionOutput(input1, "0") + ")"));
	}

	@Override
	public Node getNext(){
		return null;
	}

}
