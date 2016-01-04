package com.osreboot.glsledit.node.variable;

import java.util.ArrayList;
import java.util.Arrays;

import com.osreboot.glsledit.Node;
import com.osreboot.glsledit.NodeConstructorArg;
import com.osreboot.glsledit.Pin;
import com.osreboot.glsledit.pin.PinColor;
import com.osreboot.glsledit.pin.PinExecute;

public class NodeVariableColorSet extends Node{

	private PinExecute previous, next;
	private String path;
	private PinColor input;

	@NodeConstructorArg("path")
	public NodeVariableColorSet(float x, float y, String pathArg){
		super("set " + pathArg, x, y, Node.WIDTH_NORMAL, Node.COLOR_VARIABLE);
		previous = new PinExecute(this);
		path = pathArg;
		input = new PinColor(this, "in");
		setInputs(new ArrayList<Pin>(Arrays.asList(previous, input)));
		next = new PinExecute(this);
		setOutputs(new ArrayList<Pin>(Arrays.asList(next)));
	}
	
	public String getPath(){
		return path;
	}

	public void setPath(String pathArg){
		path = pathArg;
		setName("set " + pathArg);
	}

	@Override
	public ArrayList<String> getContent(){
		return new ArrayList<String>(Arrays.asList(path + " = " + Pin.getConnectionOutput(input, Node.DEFAULT_COLOR) + ";"));
	}

	@Override
	public Node getNext(){
		return next.getConnection();
	}

}
