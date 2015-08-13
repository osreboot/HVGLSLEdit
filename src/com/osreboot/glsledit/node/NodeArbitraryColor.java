package com.osreboot.glsledit.node;

import java.util.ArrayList;
import java.util.Arrays;

import org.newdawn.slick.Color;

import com.osreboot.glsledit.Node;
import com.osreboot.glsledit.Pin;
import com.osreboot.glsledit.pin.PinColor;

public class NodeArbitraryColor extends Node{

	private PinColor next;

	private Color value;
	
	public NodeArbitraryColor(Color valueArg, float x, float y){
		super(round(valueArg.r) + "r " + round(valueArg.g) + "g " + round(valueArg.b) + "b " + round(valueArg.a) + "a ", x, y, Node.COLOR_ARBITRARY);
		value = valueArg;
		next = new PinColor(this, "out"){
			@Override
			public String getOutput(){
				return "vec4(" + value.r + "," + value.g + "," + value.b + "," + value.a + ")";
			}
		};
		setOutputs(new ArrayList<Pin>(Arrays.asList(next)));
	}

	@Override
	public ArrayList<String> getContent(){
		return null;
	}

	@Override
	public Node getNext(){
		return null;
	}
	
	private static float round(float arg){
		return (float)Math.round(arg*100)/100;
	}

}