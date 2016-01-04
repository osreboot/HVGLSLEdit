package com.osreboot.glsledit.node.organization;

import java.util.ArrayList;
import java.util.Arrays;

import com.osreboot.glsledit.Main;
import com.osreboot.glsledit.Node;
import com.osreboot.glsledit.NodeConstructorArg;
import com.osreboot.glsledit.Pin;
import com.osreboot.glsledit.pin.PinOrganization;

public class NodeOrganizationLabel extends Node{

	private PinOrganization previous;
	private PinOrganization next;
	@SuppressWarnings("unused")
	private String label; // Only here for reflection purposes (need for constructor argument)

	private static float getAdjustedWidth(String inputArg){
		return (Main.font.getLineWidth(inputArg)/10) + 64;
	}
	
	@NodeConstructorArg("label")
	public NodeOrganizationLabel(float x, float y, String labelArg){
		super(labelArg, x, y, getAdjustedWidth(labelArg), Node.COLOR_ORGANIZATION);
		label = labelArg;
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
