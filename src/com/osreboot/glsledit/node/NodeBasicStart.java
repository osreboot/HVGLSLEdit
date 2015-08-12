package com.osreboot.glsledit.node;

import java.util.ArrayList;

import org.newdawn.slick.Color;

import com.osreboot.glsledit.Node;
import com.osreboot.glsledit.Pin;
import com.osreboot.glsledit.pin.PinExecute;

public class NodeBasicStart extends Node{

	private PinExecute next;
	
	public NodeBasicStart(){
		super("start", 512, 512, Color.blue);
		ArrayList<Pin> outputs = new ArrayList<>();
		next = new PinExecute(this);
		outputs.add(next);
		setOutputs(outputs);
	}

	@Override
	public ArrayList<String> getContent(){
		ArrayList<String> list = new ArrayList<>();
		list.add("uniform sampler2D texture1; void main(){ vec4 color = texture2D(texture1, gl_TexCoord[0].st);");
		return list;
	}

	@Override
	public Node getNext(){
		return next.getConnection();
	}
	
}
