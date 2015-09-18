package com.osreboot.glsledit.node;

import java.util.ArrayList;
import java.util.Arrays;

import com.osreboot.glsledit.Node;
import com.osreboot.glsledit.Pin;
import com.osreboot.glsledit.pin.PinColor;
import com.osreboot.glsledit.pin.PinFloat;

public class NodeFloatToColor extends Node{

	private PinFloat red, green, blue, alpha;
	private PinColor output;
	
	public NodeFloatToColor(float x, float y){
		super("float to color", x, y, Node.WIDTH_NORMAL, Node.COLOR_CAST);
		red = new PinFloat(this, "r");
		green = new PinFloat(this, "g");
		blue = new PinFloat(this, "b");
		alpha = new PinFloat(this, "a");
		setInputs(new ArrayList<Pin>(Arrays.asList(red, green, blue, alpha)));
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
		return new ArrayList<String>(Arrays.asList("vec4(" + Pin.getConnectionOutput(red, "0") + "," + Pin.getConnectionOutput(green, "0") + "," + Pin.getConnectionOutput(blue, "0") + "," + Pin.getConnectionOutput(alpha, "1") + ")"));
	}

	@Override
	public Node getNext(){
		return null;
	}

}
