package com.osreboot.glsledit;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.*;

import java.util.ArrayList;

import org.newdawn.slick.Color;

public abstract class Node {

	private static ArrayList<Node> nodes = new ArrayList<>();

	public static ArrayList<Node> getNodes(){
		return nodes;
	}
	
	public static void removeNode(Node node){
		nodes.remove(node);
		node = null;
	}
	
	private ArrayList<Pin> inputs = new ArrayList<>(), outputs = new ArrayList<>();

	private float x, y;
	private Color color;
	private String name;
	
	public Node(String nameArg, float xArg, float yArg, Color colorArg){
		name = nameArg;
		x = xArg;
		y = yArg;
		color = colorArg;
		nodes.add(this);
	}
	
	public String getName(){
		return name;
	}

	public float getX(){
		return x;
	}

	public void setX(float xArg){
		x = xArg;
	}

	public float getY(){
		return y;
	}

	public void setY(float yArg){
		y = yArg;
	}

	public Color getColor(){
		return color;
	}

	public abstract ArrayList<String> getContent();
	
	public abstract Node getNext();
	
	public void draw(float delta){
		hvlDrawQuad(x, y, 256, 32 + (Math.max(inputs.size(), outputs.size()) * 32), color);
		float value = Interactor.mouseNode == this ? 0.2f : -0.2f;
		hvlDrawQuad(x, y, 16, 32, new Color(color.r + value, color.g + value, color.b + value));
		Main.font.drawWord(name, x + 128 - (Main.font.getLineWidth(name)/20), y + 6, 0.1f, Color.white);
		for(Pin p : inputs) p.draw(true, delta);
		for(Pin p : outputs) p.draw(false, delta);
	}
	
	public ArrayList<Pin> getInputs(){
		return inputs;
	}

	public void setInputs(ArrayList<Pin> inputsArg){
		inputs = inputsArg;
	}

	public void setOutputs(ArrayList<Pin> outputsArg){
		outputs = outputsArg;
	}
	
	public ArrayList<Pin> getAllPins(){
		ArrayList<Pin> pins = new ArrayList<>();
		pins.addAll(inputs);
		pins.addAll(outputs);
		return pins;
	}

	public ArrayList<Pin> getOutputs(){
		return outputs;
	}
	
}
