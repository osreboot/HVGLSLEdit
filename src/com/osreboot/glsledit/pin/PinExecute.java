package com.osreboot.glsledit.pin;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.*;

import org.newdawn.slick.Color;

import com.osreboot.glsledit.Node;
import com.osreboot.glsledit.Pin;

public class PinExecute extends Pin{

	private Pin connection;
	
	public PinExecute(Node parentArg){
		super(parentArg, "exe");
	}

	@Override
	public void setConnection(Pin connectionArg){
		if(connectionArg == null) connection = null;
		if(connectionArg instanceof PinExecute){
			connection = connectionArg;
		}
	}

	public Node getConnection(){
		return connection.getParent();
	}

	@Override
	public void drawConnections(float deltaArg){
		if(connection != null) hvlDrawLine(getX(), getY(), connection.getX(), connection.getY(), Color.green);
	}

	@Override
	public void resetConnections(){
		connection = null;
	}

	@Override
	public boolean hasConnection(Pin pArg){
		return connection == pArg;
	}

}
