package com.osreboot.glsledit;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.*;

import org.newdawn.slick.Color;

public abstract class Pin {
	
	public static Pin findOutputConnection(Pin p){
		for(Node n : Node.getNodes()){
			for(Pin pin : n.getOutputs()) if(pin.hasConnection(p)) return pin;
		}
		return null;
	}
	
	public static int getOutputConnectionCount(Pin p){
		int total = 0;
		for(Node n : Node.getNodes()){
			for(Pin pin : n.getOutputs()) if(pin.hasConnection(p)) total++;
		}
		return total;
	}
	
	public static String getConnectionOutput(Pin pin, String defaultOutput){
		Pin connection = Pin.findOutputConnection(pin);
		if(connection != null) return connection.getOutput(); else return defaultOutput;
	}
	
	private Node parent;

	private String name;
	
	public Pin(Node parentArg, String nameArg){
		parent = parentArg;
		name = nameArg;
	}
	
	public void draw(boolean input, float delta){
		hvlDrawQuad(getX() - 8, getY() - 8, 16, 16, 
				input ? new Color(parent.getColor().r + 0.2f, parent.getColor().g + 0.2f, parent.getColor().b + 0.2f, 1) :
					new Color(parent.getColor().r - 0.2f, parent.getColor().g - 0.2f, parent.getColor().b - 0.2f, 1));
		Main.font.drawWord(name, parent.getX() + (input ? 16 : parent.getWidth() - 16) - (input ? 0 : Main.font.getLineWidth(name)/10), getY() - 8, 0.1f, Color.white);
		if(!input) drawConnections(delta);
	}

	public float getX(){
		return parent.getX()  + (parent.getInputs().contains(this) ? 0 : parent.getWidth());
	}
	
	public float getY(){
		return parent.getY() + (((parent.getInputs().contains(this) ? parent.getInputs().indexOf(this) : parent.getOutputs().indexOf(this)) + 1.25f) * 32) + 8;
	}
	
	public Node getParent(){
		return parent;
	}
	
	public String getOutput(){
		return "";
	}
	
	public abstract void setConnection(Pin connectionArg);
	public abstract void resetConnections();
	public abstract void removeConnection(Pin p);
	public abstract boolean hasConnection(Pin p);
	public abstract void drawConnections(float delta);

	public String getName(){
		return name;
	}
	
}
