package com.osreboot.glsledit.node;

import java.util.ArrayList;
import java.util.Arrays;

import com.osreboot.glsledit.Node;
import com.osreboot.glsledit.Pin;
import com.osreboot.glsledit.pin.PinFloat;

public class NodeFragLocationGet extends Node{
	
	private PinFloat outputX, outputY;
	
	public NodeFragLocationGet(float x, float y){
		super("frag loc get", x, y, Node.COLOR_VARIABLE);
		outputX = new PinFloat(this, "x"){
			@Override
			public String getOutput(){
				return "gl_TexCoord[0].x";
			}
		};
		outputY = new PinFloat(this, "y"){
			@Override
			public String getOutput(){
				return "gl_TexCoord[0].y";
			}
		};
		setOutputs(new ArrayList<Pin>(Arrays.asList(outputX, outputY)));
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
