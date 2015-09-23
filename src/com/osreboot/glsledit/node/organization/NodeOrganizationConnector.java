package com.osreboot.glsledit.node.organization;

import java.util.ArrayList;
import java.util.Arrays;

import com.osreboot.glsledit.Node;
import com.osreboot.glsledit.Pin;
import com.osreboot.glsledit.pin.PinOrganization;

public class NodeOrganizationConnector extends Node{

	private PinOrganization previous;
	private PinOrganization next;

	public NodeOrganizationConnector(float x, float y){
		super("", x, y, Node.WIDTH_MEGASHORT, Node.COLOR_ORGANIZATION);
		previous = new PinOrganization(this);
		setInputs(new ArrayList<Pin>(Arrays.asList(previous)));
		next = new PinOrganization(this);
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

}
