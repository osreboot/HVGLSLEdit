package com.osreboot.glsledit;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.newdawn.slick.Color;

import com.osreboot.glsledit.node.NodeArbitraryFloat;
import com.osreboot.glsledit.node.NodeBasicEnd;
import com.osreboot.glsledit.node.NodeBasicTestAdd;
import com.osreboot.glsledit.node.NodeBasicTestSubtract;
import com.osreboot.ridhvl.action.HvlAction0;
import com.osreboot.ridhvl.painter.HvlCamera;

public abstract class Node {
	
	private static LinkedHashMap<String, HvlAction0> registry = new LinkedHashMap<>();

	public static LinkedHashMap<String, HvlAction0> getRegistry(){
		return registry;
	}
	
	public static void initialize(){
		registry.put("end", new HvlAction0(){
			@Override
			public void run(){
				new NodeBasicEnd(HvlCamera.getX(), HvlCamera.getY());
			}
		});
		registry.put("float", new HvlAction0(){
			@Override
			public void run(){
				new NodeArbitraryFloat(0.1f, HvlCamera.getX(), HvlCamera.getY());//TODO input number here
			}
		});
		registry.put("c l add", new HvlAction0(){
			@Override
			public void run(){
				new NodeBasicTestAdd(HvlCamera.getX(), HvlCamera.getY());
			}
		});
		registry.put("c l sub", new HvlAction0(){
			@Override
			public void run(){
				new NodeBasicTestSubtract(HvlCamera.getX(), HvlCamera.getY());
			}
		});
	}
	
	private static ArrayList<Node> nodes = new ArrayList<>();

	public static ArrayList<Node> getNodes(){
		return nodes;
	}
	
	public static void removeNode(Node node){
		for(Pin p : node.getAllPins()){
			p.resetConnections();
			Pin connection = Pin.findOutputConnection(p);
			if(connection != null) connection.removeConnection(p);
		}
		
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
