package com.osreboot.glsledit.node.arithmetic;

import java.util.ArrayList;
import java.util.Arrays;

import com.osreboot.glsledit.Node;
import com.osreboot.glsledit.Pin;
import com.osreboot.glsledit.pin.PinFloat;

public class NodeFloatAbsolute extends Node{

	private PinFloat input1, output;

	public NodeFloatAbsolute(float x, float y){
		super("float absolute", x, y, Node.COLOR_MATH_OPERATOR_ADV);
		input1 = new PinFloat(this, "in");
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
		return new ArrayList<String>(Arrays.asList("abs(" + Pin.getConnectionOutput(input1, "0") + ")"));
	}

	@Override
	public Node getNext(){
		return null;
	}

}
