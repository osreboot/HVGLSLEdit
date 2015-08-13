package com.osreboot.glsledit.node;

import java.util.ArrayList;
import java.util.Arrays;

import com.osreboot.glsledit.Node;
import com.osreboot.glsledit.Pin;
import com.osreboot.glsledit.pin.PinColor;

public class NodeVariableColorGet extends Node{

	private String path;
	private PinColor output;
	
	public NodeVariableColorGet(String pathArg, float x, float y){
		super("color variable get", x, y, Node.COLOR_VARIABLE);
		path = pathArg;
		output = new PinColor(this, "out"){
			@Override
			public String getOutput(){
				return path;
			}
		};
		setOutputs(new ArrayList<Pin>(Arrays.asList(output)));
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
