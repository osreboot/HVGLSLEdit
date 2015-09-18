package com.osreboot.glsledit.node;

import java.util.ArrayList;
import java.util.Arrays;

import com.osreboot.glsledit.Node;
import com.osreboot.glsledit.Pin;
import com.osreboot.glsledit.pin.PinColor;
import com.osreboot.glsledit.pin.PinFloat;

public class NodeColorSample extends Node{//TODO add a system to get frame height/width

	private PinFloat inputX, inputY;
	private PinColor output;
	
	public NodeColorSample(float x, float y){
		super("color sample", x, y, Node.WIDTH_NORMAL, Node.COLOR_SAMPLE);
		inputX = new PinFloat(this, "x");
		inputY = new PinFloat(this, "y");
		setInputs(new ArrayList<Pin>(Arrays.asList(inputX, inputY)));
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
		return new ArrayList<String>(Arrays.asList("texture2D(texture1, vec2(" + Pin.getConnectionOutput(inputX, "0") + "," + Pin.getConnectionOutput(inputY, "0") + "))"));
	}

	@Override
	public Node getNext(){
		return null;
	}

}
