package com.osreboot.glsledit.pin;

import java.util.ArrayList;

import com.osreboot.glsledit.Node;
import com.osreboot.glsledit.Overlay;
import com.osreboot.glsledit.Pin;
import com.osreboot.ridhvl.HvlCoord;

public class PinFloat extends Pin{

	private ArrayList<Pin> connections = new ArrayList<>();

	public PinFloat(Node parentArg, String argName){
		super(parentArg, "float " + argName);
	}

	@Override
	public void setConnection(Pin connectionArg){
		if(connectionArg instanceof PinFloat){
			connections.add(connectionArg);
		}
	}

	@Override
	public void drawConnections(float deltaArg){
		for(Pin p : connections){
			Overlay.addWireConnection(new HvlCoord(getX(), getY()), new HvlCoord(p.getX(), p.getY()));
		}
	}

	@Override
	public void resetConnections(){
		connections.clear();
	}

	@Override
	public boolean hasConnection(Pin pArg){
		return connections.contains(pArg);
	}

	@Override
	public void removeConnection(Pin pArg){
		connections.remove(pArg);
	}

}
