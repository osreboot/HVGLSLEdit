package com.osreboot.glsledit.node.booleans;

import java.util.ArrayList;
import java.util.Arrays;

import com.osreboot.glsledit.Node;
import com.osreboot.glsledit.Pin;
import com.osreboot.glsledit.pin.PinBoolean;

public class NodeBooleanAnd extends Node{

	private PinBoolean input1, input2, output;

	public NodeBooleanAnd(float x, float y){
		super("and", x, y, Node.WIDTH_NORMAL, Node.COLOR_BOOLEAN);
		input1 = new PinBoolean(this, "1");
		input2 = new PinBoolean(this, "2");
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
		return new ArrayList<String>(Arrays.asList("(" + Pin.getConnectionOutput(input1, "true") + " && " + Pin.getConnectionOutput(input2, "true") + ")"));
	}

	@Override
	public Node getNext(){
		return null;
	}

}