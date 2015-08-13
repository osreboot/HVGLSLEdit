package com.osreboot.glsledit.node;

import java.util.ArrayList;
import java.util.Arrays;

import com.osreboot.glsledit.Node;
import com.osreboot.glsledit.Pin;
import com.osreboot.glsledit.pin.PinColor;
import com.osreboot.glsledit.pin.PinFloat;

public class NodeColorToFloat extends Node{

	private PinColor input;
	private PinFloat red, green, blue, alpha;
	
	public NodeColorToFloat(float x, float y){
		super("color to float", x, y, Node.COLOR_CAST);
		input = new PinColor(this, "in");
		setInputs(new ArrayList<Pin>(Arrays.asList(input)));
		red = new PinFloat(this, "r"){
			@Override
			public String getOutput(){
				return Pin.getConnectionOutput(input, Node.DEFAULT_COLOR) + ".r";
			}
		};
		green = new PinFloat(this, "g"){
			@Override
			public String getOutput(){
				return Pin.getConnectionOutput(input, Node.DEFAULT_COLOR) + ".g";
			}
		};
		blue = new PinFloat(this, "b"){
			@Override
			public String getOutput(){
				return Pin.getConnectionOutput(input, Node.DEFAULT_COLOR) + ".b";
			}
		};
		alpha = new PinFloat(this, "a"){
			@Override
			public String getOutput(){
				return Pin.getConnectionOutput(input, Node.DEFAULT_COLOR) + ".a";
			}
		};
		setOutputs(new ArrayList<Pin>(Arrays.asList(red, green, blue, alpha)));
	}

	@Override
	public ArrayList<String> getContent(){
		return null;
	}

	@Override
	public Node getNext(){
		return null;
	}

}