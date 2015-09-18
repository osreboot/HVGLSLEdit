package com.osreboot.glsledit.node.booleans;

import java.util.ArrayList;
import java.util.Arrays;

import com.osreboot.glsledit.Node;
import com.osreboot.glsledit.Pin;
import com.osreboot.glsledit.pin.PinBoolean;
import com.osreboot.glsledit.pin.PinFloat;

public class NodeBooleanFloatGreater extends Node{

	private PinFloat input1, input2;
	private PinBoolean output;

	public NodeBooleanFloatGreater(float x, float y){
		super("is float greater than", x, y, Node.WIDTH_LARGE, Node.COLOR_BOOLEAN);
		input1 = new PinFloat(this, "1");
		input2 = new PinFloat(this, "2");
		setInputs(new ArrayList<Pin>(Arrays.asList(input1, input2)));
		output = new PinBoolean(this, "out"){
			@Override
			public String getOutput(){
				return getContent().get(0);
			}
		};
		setOutputs(new ArrayList<Pin>(Arrays.asList(output)));
	}

	@Override
	public ArrayList<String> getContent(){
		return new ArrayList<String>(Arrays.asList("(" + Pin.getConnectionOutput(input1, "0") + " > " + Pin.getConnectionOutput(input2, "0") + ")"));
	}

	@Override
	public Node getNext(){
		return null;
	}

}
