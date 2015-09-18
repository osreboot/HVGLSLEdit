package com.osreboot.glsledit.node.booleans;

import java.util.ArrayList;
import java.util.Arrays;

import com.osreboot.glsledit.Node;
import com.osreboot.glsledit.Pin;
import com.osreboot.glsledit.pin.PinBoolean;

public class NodeBooleanNot extends Node{

	private PinBoolean input1, output;

	public NodeBooleanNot(float x, float y){
		super("not", x, y, Node.WIDTH_NORMAL, Node.COLOR_BOOLEAN);
		input1 = new PinBoolean(this, "in");
		setInputs(new ArrayList<Pin>(Arrays.asList(input1)));
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
		return new ArrayList<String>(Arrays.asList("!(" + Pin.getConnectionOutput(input1, "true") + ")"));
	}

	@Override
	public Node getNext(){
		return null;
	}

}
