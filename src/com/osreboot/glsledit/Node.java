package com.osreboot.glsledit;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.newdawn.slick.Color;

import com.osreboot.glsledit.node.NodeArbitraryFloat;
import com.osreboot.glsledit.node.NodeBasicEnd;
import com.osreboot.glsledit.node.NodeBasicTestAdd;
import com.osreboot.glsledit.node.NodeBasicTestSubtract;
import com.osreboot.glsledit.node.NodeColorToFloat;
import com.osreboot.glsledit.node.NodeEnd;
import com.osreboot.glsledit.node.NodeFloatAdd;
import com.osreboot.glsledit.node.NodeFloatDivide;
import com.osreboot.glsledit.node.NodeFloatMultiply;
import com.osreboot.glsledit.node.NodeFloatSubtract;
import com.osreboot.glsledit.node.NodeFloatToColor;
import com.osreboot.glsledit.node.NodeFragSet;
import com.osreboot.glsledit.node.NodeVariableColorGet;
import com.osreboot.glsledit.node.NodeVariableColorSet;
import com.osreboot.ridhvl.action.HvlAction0;
import com.osreboot.ridhvl.painter.HvlCamera;

public abstract class Node {
	
	public static final Color COLOR_ADD = new Color(1, 0.5f, 0), COLOR_SUB = new Color(1, 0, 0.5f),
			COLOR_MLT = new Color(0.75f, 0, 0), COLOR_DIV = new Color(0, 0.5f, 1f), COLOR_CAST = new Color(1f, 0.5f, 0.5f),
			COLOR_VARIABLE = new Color(0.5f, 0.5f, 1f);
	public static final String DEFAULT_COLOR = "vec4(0, 0, 0, 1)";
	
	private static LinkedHashMap<String, HvlAction0> registry = new LinkedHashMap<>();

	public static LinkedHashMap<String, HvlAction0> getRegistry(){
		return registry;
	}
	
	public static void initialize(){
		registry.put("bsc end", new HvlAction0(){
			@Override
			public void run(){
				new NodeBasicEnd(HvlCamera.getX(), HvlCamera.getY());
			}
		});
		registry.put("end", new HvlAction0(){
			@Override
			public void run(){
				new NodeEnd(HvlCamera.getX(), HvlCamera.getY());
			}
		});
		registry.put("float", new HvlAction0(){
			@Override
			public void run(){
				new NodeArbitraryFloat(0.2f, HvlCamera.getX(), HvlCamera.getY());//TODO input number here
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
		registry.put("f add", new HvlAction0(){
			@Override
			public void run(){
				new NodeFloatAdd(HvlCamera.getX(), HvlCamera.getY());
			}
		});
		registry.put("f sub", new HvlAction0(){
			@Override
			public void run(){
				new NodeFloatSubtract(HvlCamera.getX(), HvlCamera.getY());
			}
		});
		registry.put("f mlt", new HvlAction0(){
			@Override
			public void run(){
				new NodeFloatMultiply(HvlCamera.getX(), HvlCamera.getY());
			}
		});
		registry.put("f div", new HvlAction0(){
			@Override
			public void run(){
				new NodeFloatDivide(HvlCamera.getX(), HvlCamera.getY());
			}
		});
		registry.put("f to c", new HvlAction0(){
			@Override
			public void run(){
				new NodeFloatToColor(HvlCamera.getX(), HvlCamera.getY());
			}
		});
		registry.put("c to f", new HvlAction0(){
			@Override
			public void run(){
				new NodeColorToFloat(HvlCamera.getX(), HvlCamera.getY());
			}
		});
		registry.put("c var get", new HvlAction0(){
			@Override
			public void run(){
				new NodeVariableColorGet("color", HvlCamera.getX(), HvlCamera.getY());//TODO input string here
			}
		});
		registry.put("frag set", new HvlAction0(){
			@Override
			public void run(){
				new NodeFragSet(HvlCamera.getX(), HvlCamera.getY());
			}
		});
		registry.put("c var set", new HvlAction0(){
			@Override
			public void run(){
				new NodeVariableColorSet("color", HvlCamera.getX(), HvlCamera.getY());//TODO input string here
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
