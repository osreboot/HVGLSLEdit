package com.osreboot.glsledit.node.organization;

import java.util.ArrayList;
import java.util.Arrays;

import com.osreboot.glsledit.Main;
import com.osreboot.glsledit.Node;
import com.osreboot.glsledit.Pin;
import com.osreboot.glsledit.pin.PinOrganization;

public class NodeOrganizationLabel extends Node{

	private PinOrganization previous;
	private PinOrganization next;

	private static float getAdjustedWidth(String inputArg){
		return (Main.font.getLineWidth(inputArg)/10) + 64;
	}
	
	public NodeOrganizationLabel(String labelArg, float x, float y){
		super(labelArg, x, y, getAdjustedWidth(labelArg), Node.COLOR_ORGANIZATION);
		previous = new PinOrganization(this);
		setInputs(new ArrayList<Pin>(Arrays.asList(previous)));
		next = new PinOrganization(this);
		setOutputs(new ArrayList<Pin>(Arrays.asList(next)));
	}

	public void setLabel(String labelArg){
		setName(labelArg);
		setWidth(getAdjustedWidth(labelArg));
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
