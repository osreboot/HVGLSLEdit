package com.osreboot.glsledit.node.variable;

import java.util.ArrayList;
import java.util.Arrays;

import com.osreboot.glsledit.Node;
import com.osreboot.glsledit.Pin;
import com.osreboot.glsledit.pin.PinFloat;

public class NodeVariableFloatGet extends Node{

	private String path;
	private PinFloat output;
	
	public NodeVariableFloatGet(String pathArg, float x, float y){
		super("get " + pathArg, x, y, Node.COLOR_VARIABLE);
		path = pathArg;
		output = new PinFloat(this, "out"){
			@Override
			public String getOutput(){
				return path;
			}
		};
		setOutputs(new ArrayList<Pin>(Arrays.asList(output)));
	}
	
	public String getPath(){
		return path;
	}

	public void setPath(String pathArg){
		path = pathArg;
		setName("get" + pathArg);
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
