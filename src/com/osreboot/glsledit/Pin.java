package com.osreboot.glsledit;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.*;

import org.newdawn.slick.Color;

public abstract class Pin {

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
		Main.font.drawWord(name, parent.getX() + (input ? 16 : 240) - (input ? 0 : Main.font.getLineWidth(name)/10), getY(), 0.1f, Color.white);
		if(!input) drawConnections(delta);
	}

	public float getX(){
		return parent.getX()  + (parent.getInputs().contains(this) ? 0 : 256);
	}
	
	public float getY(){
		return parent.getY() + (((parent.getInputs().contains(this) ? parent.getInputs().indexOf(this) : parent.getOutputs().indexOf(this)) + 0.625f) * 64) + 8;
	}
	
	public Node getParent(){
		return parent;
	}
	
	public abstract void setConnection(Pin connectionArg);
	public abstract void resetConnections();
	public abstract void drawConnections(float delta);

	public String getName(){
		return name;
	}
	
}
