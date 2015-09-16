
package com.osreboot.glsledit.node;

import java.util.ArrayList;
import java.util.Arrays;

import com.osreboot.glsledit.Node;
import com.osreboot.glsledit.Pin;
import com.osreboot.glsledit.pin.PinColor;
import com.osreboot.glsledit.pin.PinFloat;

public class NodeSingleFloatToColor extends Node{

	private PinFloat input;
	private PinColor output;
	
	public NodeSingleFloatToColor(float x, float y){
		super("single float to color", x, y, Node.COLOR_CAST);
		input = new PinFloat(this, "in");
		setInputs(new ArrayList<Pin>(Arrays.asList(input)));
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
		return new ArrayList<String>(Arrays.asList("vec4(" + Pin.getConnectionOutput(input, "0") + "," + Pin.getConnectionOutput(input, "0") + "," + Pin.getConnectionOutput(input, "0") + "," + Pin.getConnectionOutput(input, "1") + ")"));
	}

	@Override
	public Node getNext(){
		return null;
	}

}