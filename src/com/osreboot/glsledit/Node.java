package com.osreboot.glsledit;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.swing.JColorChooser;
import javax.swing.JOptionPane;

import org.newdawn.slick.Color;

import com.osreboot.glsledit.node.NodeArbitraryColor;
import com.osreboot.glsledit.node.NodeArbitraryFloat;
import com.osreboot.glsledit.node.NodeColorSample;
import com.osreboot.glsledit.node.NodeColorToFloat;
import com.osreboot.glsledit.node.NodeEnd;
import com.osreboot.glsledit.node.NodeEndFragSet;
import com.osreboot.glsledit.node.NodeFloatToColor;
import com.osreboot.glsledit.node.NodeFragLocationGet;
import com.osreboot.glsledit.node.NodeFragSet;
import com.osreboot.glsledit.node.NodeIf;
import com.osreboot.glsledit.node.NodeIfEnd;
import com.osreboot.glsledit.node.NodeVariableColorDefine;
import com.osreboot.glsledit.node.NodeVariableColorGet;
import com.osreboot.glsledit.node.NodeVariableColorSet;
import com.osreboot.glsledit.node.arithmetic.NodeFloatAbsolute;
import com.osreboot.glsledit.node.arithmetic.NodeFloatAdd;
import com.osreboot.glsledit.node.arithmetic.NodeFloatDivide;
import com.osreboot.glsledit.node.arithmetic.NodeFloatLerp;
import com.osreboot.glsledit.node.arithmetic.NodeFloatMax;
import com.osreboot.glsledit.node.arithmetic.NodeFloatMin;
import com.osreboot.glsledit.node.arithmetic.NodeFloatMod;
import com.osreboot.glsledit.node.arithmetic.NodeFloatMultiply;
import com.osreboot.glsledit.node.arithmetic.NodeFloatPower;
import com.osreboot.glsledit.node.arithmetic.NodeFloatSqrt;
import com.osreboot.glsledit.node.arithmetic.NodeFloatSubtract;
import com.osreboot.glsledit.node.booleans.NodeBooleanAnd;
import com.osreboot.glsledit.node.booleans.NodeBooleanFloatGreater;
import com.osreboot.glsledit.node.booleans.NodeBooleanFloatLess;
import com.osreboot.glsledit.node.booleans.NodeBooleanOr;
import com.osreboot.ridhvl.action.HvlAction0;
import com.osreboot.ridhvl.painter.HvlCamera;

public abstract class Node {

	//Node TODOS:
	/*
	 * NodeBooleanGreater
	 * NodeBooleanEqual
	 * NodeBooleanLess
	 * NodeBooleanGreaterOrEqual
	 * NodeBooleanLessOrEqual
	 * NodeBooleanInRange
	 * 
	 * NodeBooleanOr
	 * NodeBooleanAnd
	 * 
	 * 
	 * NodeIf add else functions
	 * 
	 * Convert pins to using a abstract PinType
	 */
	
	@SuppressWarnings("serial")
	public static class InputInvalidException extends Exception{}

	public static final Color 
	COLOR_START = Color.blue,
	COLOR_END = Color.red,
	COLOR_MATH_OPERATOR = new Color(0.7f, 0, 0),
	COLOR_MATH_OPERATOR_ADV = new Color(0.4f, 0, 0),
	COLOR_ARBITRARY = new Color(0.2f, 0.75f, 0.2f),
	COLOR_FRAGMENT = new Color(0.8f, 0, 0.8f),
	COLOR_SAMPLE = new Color(0, 0.6f, 0.6f),
	COLOR_CAST = new Color(1f, 0.5f, 0.5f),
	COLOR_VARIABLE = new Color(0, 0, 0.6f),
	COLOR_IF = new Color(1f, 0.5f, 0),
	COLOR_BOOLEAN = new Color(0, 0.6f, 0);

	public static final String DEFAULT_COLOR = "vec4(0, 0, 0, 1)";

	private static LinkedHashMap<String, HvlAction0> registry = new LinkedHashMap<>();

	public static LinkedHashMap<String, HvlAction0> getRegistry(){
		return registry;
	}

	public static void initialize(){
		registry.put("float", new HvlAction0(){
			@Override
			public void run(){
				try{
					new NodeArbitraryFloat(getUserFloat(), HvlCamera.getX(), HvlCamera.getY());
				}catch(Exception e){}
			}
		});
		registry.put("color", new HvlAction0(){
			@Override
			public void run(){
				try{
					new NodeArbitraryColor(getUserColor(), HvlCamera.getX(), HvlCamera.getY());
				}catch(Exception e){}
			}
		});
		registry.put("if", new HvlAction0(){
			@Override
			public void run(){
				new NodeIf(HvlCamera.getX(), HvlCamera.getY());
			}
		});
		registry.put("if end", new HvlAction0(){
			@Override
			public void run(){
				new NodeIfEnd(HvlCamera.getX(), HvlCamera.getY());
			}
		});
		registry.put("b and", new HvlAction0(){
			@Override
			public void run(){
				new NodeBooleanAnd(HvlCamera.getX(), HvlCamera.getY());
			}
		});
		registry.put("b or", new HvlAction0(){
			@Override
			public void run(){
				new NodeBooleanOr(HvlCamera.getX(), HvlCamera.getY());
			}
		});
		registry.put("b f grtr", new HvlAction0(){
			@Override
			public void run(){
				new NodeBooleanFloatGreater(HvlCamera.getX(), HvlCamera.getY());
			}
		});
		registry.put("b f less", new HvlAction0(){
			@Override
			public void run(){
				new NodeBooleanFloatLess(HvlCamera.getX(), HvlCamera.getY());
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
		registry.put("f mod", new HvlAction0(){
			@Override
			public void run(){
				new NodeFloatMod(HvlCamera.getX(), HvlCamera.getY());
			}
		});
		registry.put("f pow", new HvlAction0(){
			@Override
			public void run(){
				new NodeFloatPower(HvlCamera.getX(), HvlCamera.getY());
			}
		});
		registry.put("f sqrt", new HvlAction0(){
			@Override
			public void run(){
				new NodeFloatSqrt(HvlCamera.getX(), HvlCamera.getY());
			}
		});
		registry.put("f lerp", new HvlAction0(){
			@Override
			public void run(){
				new NodeFloatLerp(HvlCamera.getX(), HvlCamera.getY());
			}
		});
		registry.put("f abs", new HvlAction0(){
			@Override
			public void run(){
				new NodeFloatAbsolute(HvlCamera.getX(), HvlCamera.getY());
			}
		});
		registry.put("f min", new HvlAction0(){
			@Override
			public void run(){
				new NodeFloatMin(HvlCamera.getX(), HvlCamera.getY());
			}
		});
		registry.put("f max", new HvlAction0(){
			@Override
			public void run(){
				new NodeFloatMax(HvlCamera.getX(), HvlCamera.getY());
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
		registry.put("c var def", new HvlAction0(){
			@Override
			public void run(){
				try{
					new NodeVariableColorDefine(getUserString(), HvlCamera.getX(), HvlCamera.getY());
				}catch(Exception e){}
			}
		});
		registry.put("c var get", new HvlAction0(){
			@Override
			public void run(){
				try{
					new NodeVariableColorGet(getUserString(), HvlCamera.getX(), HvlCamera.getY());
				}catch(Exception e){}
			}
		});
		registry.put("c var set", new HvlAction0(){
			@Override
			public void run(){
				try{
					new NodeVariableColorSet(getUserString(), HvlCamera.getX(), HvlCamera.getY());
				}catch(Exception e){}
			}
		});
		registry.put("c sample", new HvlAction0(){
			@Override
			public void run(){
				new NodeColorSample(HvlCamera.getX(), HvlCamera.getY());
			}
		});
		registry.put("frag loc get", new HvlAction0(){
			@Override
			public void run(){
				new NodeFragLocationGet(HvlCamera.getX(), HvlCamera.getY());
			}
		});
		registry.put("frag set", new HvlAction0(){
			@Override
			public void run(){
				new NodeFragSet(HvlCamera.getX(), HvlCamera.getY());
			}
		});
		registry.put("end frag", new HvlAction0(){
			@Override
			public void run(){
				new NodeEndFragSet(HvlCamera.getX(), HvlCamera.getY());
			}
		});
		registry.put("end", new HvlAction0(){
			@Override
			public void run(){
				new NodeEnd(HvlCamera.getX(), HvlCamera.getY());
			}
		});
	}

	public static float getUserFloat() throws InputInvalidException{
		boolean valid = false;
		float value = 0;
		do{
			String input = JOptionPane.showInputDialog("Input a float value.");
			if (input == null) throw new InputInvalidException();
			try{
				value = Float.parseFloat(input);
			}catch(Exception e){
				JOptionPane.showMessageDialog(null, "Invalid float.");
				continue;
			}
			valid = true;
		}while(!valid);

		return value;
	}

	public static String getUserString() throws InputInvalidException{
		boolean valid = false;
		String value = "";
		do{
			String input = JOptionPane.showInputDialog("Input a string value.");
			if (input == null) throw new InputInvalidException();
			try{
				value = input;
			}catch(Exception e){
				JOptionPane.showMessageDialog(null, "Invalid string.");
				continue;
			}
			valid = true;
		}while(!valid);

		return value;
	}

	public static Color getUserColor() throws InputInvalidException{
		boolean valid = false;
		Color value = new Color(0, 0, 0, 1);
		do{
			java.awt.Color input = JColorChooser.showDialog(null, "Input a color value.", java.awt.Color.WHITE);
			if (input == null) throw new InputInvalidException();
			value = new Color(((float)input.getRed())/255f, ((float)input.getGreen())/255f, ((float)input.getBlue())/255f, ((float)input.getAlpha())/255f);
			valid = true;
		}while(!valid);

		return value;
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
