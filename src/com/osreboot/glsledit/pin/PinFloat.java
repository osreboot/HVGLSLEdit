package com.osreboot.glsledit.pin;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.*;

import java.util.ArrayList;

import org.newdawn.slick.Color;

import com.osreboot.glsledit.Node;
import com.osreboot.glsledit.Pin;

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
			hvlDrawLine(getX(), getY(), p.getX(), p.getY(), Color.green);
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

}
