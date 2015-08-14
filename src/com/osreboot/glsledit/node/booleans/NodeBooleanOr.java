package com.osreboot.glsledit.node.booleans;

import java.util.ArrayList;
import java.util.Arrays;

import com.osreboot.glsledit.Node;
import com.osreboot.glsledit.Pin;
import com.osreboot.glsledit.pin.PinBoolean;

public class NodeBooleanOr extends Node{

	private PinBoolean input1, input2, output;

	public NodeBooleanOr(float x, float y){
		super("or", x, y, Node.COLOR_BOOLEAN);
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
		return new ArrayList<String>(Arrays.asList("(" + Pin.getConnectionOutput(input1, "false") + " || " + Pin.getConnectionOutput(input2, "false") + ")"));
	}

	@Override
	public Node getNext(){
		return null;
	}

}
