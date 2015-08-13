package com.osreboot.glsledit.node;

import java.util.ArrayList;
import java.util.Arrays;

import com.osreboot.glsledit.Node;
import com.osreboot.glsledit.Pin;
import com.osreboot.glsledit.pin.PinExecute;

public class NodeBasicStart extends Node{

	private PinExecute next;
	
	public NodeBasicStart(float x, float y){
		super("start", x, y, Node.COLOR_START);
		next = new PinExecute(this);
		setOutputs(new ArrayList<Pin>(Arrays.asList(next)));
	}

	@Override
	public ArrayList<String> getContent(){//TODO make sampling manual
		return new ArrayList<String>(Arrays.asList("uniform sampler2D texture1;", "void main(){", "vec4 color = texture2D(texture1, gl_TexCoord[0].st);"));
	}

	@Override
	public Node getNext(){
		return next.getConnection();
	}
	
}
