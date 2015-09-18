package com.osreboot.glsledit.pin;

import com.osreboot.glsledit.Node;
import com.osreboot.glsledit.Overlay;
import com.osreboot.glsledit.Pin;
import com.osreboot.ridhvl.HvlCoord;

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
		return connection.getParent();//TODO null catch here with public static final node error
	}

	@Override
	public void drawConnections(float deltaArg){
		if(connection != null) Overlay.addWireConnection(new HvlCoord(getX(), getY()), new HvlCoord(connection.getX(), connection.getY()), Overlay.WIRE_EXECUTE);
	}

	@Override
	public void resetConnections(){
		connection = null;
	}

	@Override
	public boolean hasConnection(Pin pArg){
		return connection == pArg;
	}
	
	@Override
	public void removeConnection(Pin pArg){
		if(connection == pArg) connection = null;
	}

}
