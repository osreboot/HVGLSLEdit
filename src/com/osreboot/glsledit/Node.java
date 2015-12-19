package com.osreboot.glsledit;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.swing.JColorChooser;
import javax.swing.JOptionPane;

import org.newdawn.slick.Color;

import com.osreboot.glsledit.node.NodeArbitraryColor;
import com.osreboot.glsledit.node.NodeArbitraryFloat;
import com.osreboot.glsledit.node.NodeClose;
import com.osreboot.glsledit.node.NodeColorSample;
import com.osreboot.glsledit.node.NodeColorToFloat;
import com.osreboot.glsledit.node.NodeEnd;
import com.osreboot.glsledit.node.NodeEndFragSet;
import com.osreboot.glsledit.node.NodeFloatToColor;
import com.osreboot.glsledit.node.NodeFor;
import com.osreboot.glsledit.node.NodeFragLocationGet;
import com.osreboot.glsledit.node.NodeFragSet;
import com.osreboot.glsledit.node.NodeIf;
import com.osreboot.glsledit.node.NodeIfElse;
import com.osreboot.glsledit.node.NodeSingleFloatToColor;
import com.osreboot.glsledit.node.arithmetic.NodeColorAdd;
import com.osreboot.glsledit.node.arithmetic.NodeColorDivide;
import com.osreboot.glsledit.node.arithmetic.NodeColorMultiply;
import com.osreboot.glsledit.node.arithmetic.NodeColorSubtract;
import com.osreboot.glsledit.node.arithmetic.NodeFloatAbsolute;
import com.osreboot.glsledit.node.arithmetic.NodeFloatAdd;
import com.osreboot.glsledit.node.arithmetic.NodeFloatDistance;
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
import com.osreboot.glsledit.node.booleans.NodeBooleanNot;
import com.osreboot.glsledit.node.booleans.NodeBooleanOr;
import com.osreboot.glsledit.node.organization.NodeOrganizationConnector;
import com.osreboot.glsledit.node.organization.NodeOrganizationLabel;
import com.osreboot.glsledit.node.variable.NodeVariableColorDefine;
import com.osreboot.glsledit.node.variable.NodeVariableColorGet;
import com.osreboot.glsledit.node.variable.NodeVariableColorSet;
import com.osreboot.glsledit.node.variable.NodeVariableFloatDefine;
import com.osreboot.glsledit.node.variable.NodeVariableFloatGet;
import com.osreboot.glsledit.node.variable.NodeVariableFloatSet;
import com.osreboot.ridhvl.action.HvlAction0;
import com.osreboot.ridhvl.action.HvlAction1;
import com.osreboot.ridhvl.painter.HvlCamera;

public abstract class Node {

	//Node TODOS:
	/*
	 * NodeBooleanEqual
	 * NodeBooleanGreaterOrEqual
	 * NodeBooleanLessOrEqual
	 * NodeBooleanInRange
	 * 
	 * NodeDisplayDimensions
	 * 
	 * NodeTime
	 * 
	 * NodeColorValueSet
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
	COLOR_BLOCK = new Color(1f, 0.5f, 0),
	COLOR_BOOLEAN = new Color(0, 0.6f, 0),
	COLOR_ORGANIZATION = new Color(0.1f, 0.1f, 0.1f);

	public static final String DEFAULT_COLOR = "vec4(0, 0, 0, 1)";

	private static LinkedHashMap<String, HvlAction0> registry = new LinkedHashMap<>();
	private static LinkedHashMap<String, ArrayList<String>> registrySubList = new LinkedHashMap<>();;

	public static LinkedHashMap<String, HvlAction0> getRegistry(){
		return registry;
	}
	
	public static HashMap<String, ArrayList<String>> getSubRegistry(){
		return registrySubList;
	}

	public static void initialize(){
		ArrayList<String> categoryManagement = new ArrayList<>();
		ArrayList<String> categoryFloat = new ArrayList<>();
		ArrayList<String> categoryColor = new ArrayList<>();
		ArrayList<String> categoryBoolean = new ArrayList<>();
		ArrayList<String> categoryCasting = new ArrayList<>();
		ArrayList<String> categoryFragment = new ArrayList<>();
		ArrayList<String> categoryOrganization = new ArrayList<>();
		
		categoryFloat.add("float");
		registry.put("float", new HvlAction0(){
			@Override
			public void run(){
				try{
					NodeArbitraryFloat node = new NodeArbitraryFloat(getUserFloat(), HvlCamera.getX(), HvlCamera.getY());
					node.setOnDialogueClick(new HvlAction1<Node>(){
						@Override
						public void run(Node node){
							try{
								((NodeArbitraryFloat)node).setValue(getUserFloat());
							}catch(Exception e){}
						}
					});
				}catch(Exception e){}
			}
		});
		categoryColor.add("color");
		registry.put("color", new HvlAction0(){
			@Override
			public void run(){
				try{
					NodeArbitraryColor node = new NodeArbitraryColor(getUserColor(), HvlCamera.getX(), HvlCamera.getY());
					node.setOnDialogueClick(new HvlAction1<Node>(){
						@Override
						public void run(Node node){
							try{
								((NodeArbitraryColor)node).setValue(getUserColor());
							}catch(Exception e){}
						}
					});
				}catch(Exception e){}
			}
		});
		categoryManagement.add("if");
		registry.put("if", new HvlAction0(){
			@Override
			public void run(){
				new NodeIf(HvlCamera.getX(), HvlCamera.getY());
			}
		});
		categoryManagement.add("else");
		registry.put("else", new HvlAction0(){
			@Override
			public void run(){
				new NodeIfElse(HvlCamera.getX(), HvlCamera.getY());
			}
		});
		categoryManagement.add("for");
		registry.put("for", new HvlAction0(){
			@Override
			public void run(){
				try{
					NodeFor node = new NodeFor(getUserString(), HvlCamera.getX(), HvlCamera.getY());
					node.setOnDialogueClick(new HvlAction1<Node>(){
						@Override
						public void run(Node node){
							try{
								((NodeFor)node).setVar(getUserString());
							}catch(Exception e){}
						}
					});
				}catch(Exception e){}
			}
		});
		categoryManagement.add("close");
		registry.put("close", new HvlAction0(){
			@Override
			public void run(){
				new NodeClose(HvlCamera.getX(), HvlCamera.getY());
			}
		});
		categoryBoolean.add("b and");
		registry.put("b and", new HvlAction0(){
			@Override
			public void run(){
				new NodeBooleanAnd(HvlCamera.getX(), HvlCamera.getY());
			}
		});
		categoryBoolean.add("b or");
		registry.put("b or", new HvlAction0(){
			@Override
			public void run(){
				new NodeBooleanOr(HvlCamera.getX(), HvlCamera.getY());
			}
		});
		categoryBoolean.add("b not");
		registry.put("b not", new HvlAction0(){
			@Override
			public void run(){
				new NodeBooleanNot(HvlCamera.getX(), HvlCamera.getY());
			}
		});
		categoryBoolean.add("b f grtr");
		registry.put("b f grtr", new HvlAction0(){
			@Override
			public void run(){
				new NodeBooleanFloatGreater(HvlCamera.getX(), HvlCamera.getY());
			}
		});
		categoryBoolean.add("b f less");
		registry.put("b f less", new HvlAction0(){
			@Override
			public void run(){
				new NodeBooleanFloatLess(HvlCamera.getX(), HvlCamera.getY());
			}
		});
		categoryFloat.add("f add");
		registry.put("f add", new HvlAction0(){
			@Override
			public void run(){
				new NodeFloatAdd(HvlCamera.getX(), HvlCamera.getY());
			}
		});
		categoryFloat.add("f sub");
		registry.put("f sub", new HvlAction0(){
			@Override
			public void run(){
				new NodeFloatSubtract(HvlCamera.getX(), HvlCamera.getY());
			}
		});
		categoryFloat.add("f mlt");
		registry.put("f mlt", new HvlAction0(){
			@Override
			public void run(){
				new NodeFloatMultiply(HvlCamera.getX(), HvlCamera.getY());
			}
		});
		categoryFloat.add("f div");
		registry.put("f div", new HvlAction0(){
			@Override
			public void run(){
				new NodeFloatDivide(HvlCamera.getX(), HvlCamera.getY());
			}
		});
		categoryFloat.add("f mod");
		registry.put("f mod", new HvlAction0(){
			@Override
			public void run(){
				new NodeFloatMod(HvlCamera.getX(), HvlCamera.getY());
			}
		});
		categoryFloat.add("f pow");
		registry.put("f pow", new HvlAction0(){
			@Override
			public void run(){
				new NodeFloatPower(HvlCamera.getX(), HvlCamera.getY());
			}
		});
		categoryFloat.add("f sqrt");
		registry.put("f sqrt", new HvlAction0(){
			@Override
			public void run(){
				new NodeFloatSqrt(HvlCamera.getX(), HvlCamera.getY());
			}
		});
		categoryFloat.add("f lerp");
		registry.put("f lerp", new HvlAction0(){
			@Override
			public void run(){
				new NodeFloatLerp(HvlCamera.getX(), HvlCamera.getY());
			}
		});
		categoryFloat.add("f abs");
		registry.put("f abs", new HvlAction0(){
			@Override
			public void run(){
				new NodeFloatAbsolute(HvlCamera.getX(), HvlCamera.getY());
			}
		});
		categoryFloat.add("f min");
		registry.put("f min", new HvlAction0(){
			@Override
			public void run(){
				new NodeFloatMin(HvlCamera.getX(), HvlCamera.getY());
			}
		});
		categoryFloat.add("f max");
		registry.put("f max", new HvlAction0(){
			@Override
			public void run(){
				new NodeFloatMax(HvlCamera.getX(), HvlCamera.getY());
			}
		});
		categoryFloat.add("f dist");
		registry.put("f dist", new HvlAction0(){
			@Override
			public void run(){
				new NodeFloatDistance(HvlCamera.getX(), HvlCamera.getY());
			}
		});
		categoryFloat.add("f var def");
		registry.put("f var def", new HvlAction0(){
			@Override
			public void run(){
				try{
					NodeVariableFloatDefine node = new NodeVariableFloatDefine(getUserString(), HvlCamera.getX(), HvlCamera.getY());
					node.setOnDialogueClick(new HvlAction1<Node>(){
						@Override
						public void run(Node node){
							try{
								((NodeVariableFloatDefine)node).setPath(getUserString());
							}catch(Exception e){}
						}
					});
				}catch(Exception e){}
			}
		});
		categoryFloat.add("f var get");
		registry.put("f var get", new HvlAction0(){
			@Override
			public void run(){
				try{
					NodeVariableFloatGet node = new NodeVariableFloatGet(getUserString(), HvlCamera.getX(), HvlCamera.getY());
					node.setOnDialogueClick(new HvlAction1<Node>(){
						@Override
						public void run(Node node){
							try{
								((NodeVariableFloatGet)node).setPath(getUserString());
							}catch(Exception e){}
						}
					});
				}catch(Exception e){}
			}
		});
		categoryFloat.add("f var set");
		registry.put("f var set", new HvlAction0(){
			@Override
			public void run(){
				try{
					NodeVariableFloatSet node = new NodeVariableFloatSet(getUserString(), HvlCamera.getX(), HvlCamera.getY());
					node.setOnDialogueClick(new HvlAction1<Node>(){
						@Override
						public void run(Node node){
							try{
								((NodeVariableFloatSet)node).setPath(getUserString());
							}catch(Exception e){}
						}
					});
				}catch(Exception e){}
			}
		});
		categoryColor.add("c add");
		registry.put("c add", new HvlAction0(){
			@Override
			public void run(){
				new NodeColorAdd(HvlCamera.getX(), HvlCamera.getY());
			}
		});
		categoryColor.add("c sub");
		registry.put("c sub", new HvlAction0(){
			@Override
			public void run(){
				new NodeColorSubtract(HvlCamera.getX(), HvlCamera.getY());
			}
		});
		categoryColor.add("c mlt");
		registry.put("c mlt", new HvlAction0(){
			@Override
			public void run(){
				new NodeColorMultiply(HvlCamera.getX(), HvlCamera.getY());
			}
		});
		categoryColor.add("c div");
		registry.put("c div", new HvlAction0(){
			@Override
			public void run(){
				new NodeColorDivide(HvlCamera.getX(), HvlCamera.getY());
			}
		});
		categoryColor.add("c var def");
		registry.put("c var def", new HvlAction0(){
			@Override
			public void run(){
				try{
					NodeVariableColorDefine node = new NodeVariableColorDefine(getUserString(), HvlCamera.getX(), HvlCamera.getY());
					node.setOnDialogueClick(new HvlAction1<Node>(){
						@Override
						public void run(Node node){
							try{
								((NodeVariableColorDefine)node).setPath(getUserString());
							}catch(Exception e){}
						}
					});
				}catch(Exception e){}
			}
		});
		categoryColor.add("c var get");
		registry.put("c var get", new HvlAction0(){
			@Override
			public void run(){
				try{
					NodeVariableColorGet node = new NodeVariableColorGet(getUserString(), HvlCamera.getX(), HvlCamera.getY());
					node.setOnDialogueClick(new HvlAction1<Node>(){
						@Override
						public void run(Node node){
							try{
								((NodeVariableColorGet)node).setPath(getUserString());
							}catch(Exception e){}
						}
					});
				}catch(Exception e){}
			}
		});
		categoryColor.add("c var set");
		registry.put("c var set", new HvlAction0(){
			@Override
			public void run(){
				try{
					NodeVariableColorSet node = new NodeVariableColorSet(getUserString(), HvlCamera.getX(), HvlCamera.getY());
					node.setOnDialogueClick(new HvlAction1<Node>(){
						@Override
						public void run(Node node){
							try{
								((NodeVariableColorSet)node).setPath(getUserString());
							}catch(Exception e){}
						}
					});
				}catch(Exception e){}
			}
		});
		categoryFragment.add("c sample");
		registry.put("c sample", new HvlAction0(){
			@Override
			public void run(){
				new NodeColorSample(HvlCamera.getX(), HvlCamera.getY());
			}
		});
		categoryCasting.add("f to c");
		registry.put("f to c", new HvlAction0(){
			@Override
			public void run(){
				new NodeFloatToColor(HvlCamera.getX(), HvlCamera.getY());
			}
		});
		categoryCasting.add("sf to c");
		registry.put("sf to c", new HvlAction0(){
			@Override
			public void run(){
				new NodeSingleFloatToColor(HvlCamera.getX(), HvlCamera.getY());
			}
		});
		categoryCasting.add("c to f");
		registry.put("c to f", new HvlAction0(){
			@Override
			public void run(){
				new NodeColorToFloat(HvlCamera.getX(), HvlCamera.getY());
			}
		});
		categoryFragment.add("frag loc get");
		registry.put("frag loc get", new HvlAction0(){
			@Override
			public void run(){
				new NodeFragLocationGet(HvlCamera.getX(), HvlCamera.getY());
			}
		});
		categoryFragment.add("frag set");
		registry.put("frag set", new HvlAction0(){
			@Override
			public void run(){
				new NodeFragSet(HvlCamera.getX(), HvlCamera.getY());
			}
		});
		categoryOrganization.add("org ctr");
		registry.put("org ctr", new HvlAction0(){
			@Override
			public void run(){
				new NodeOrganizationConnector(HvlCamera.getX(), HvlCamera.getY());
			}
		});
		categoryOrganization.add("org lbl");
		registry.put("org lbl", new HvlAction0(){
			@Override
			public void run(){
				try{
					NodeOrganizationLabel node = new NodeOrganizationLabel(getUserString(), HvlCamera.getX(), HvlCamera.getY());
					node.setOnDialogueClick(new HvlAction1<Node>(){
						@Override
						public void run(Node node){
							try{
								((NodeOrganizationLabel)node).setLabel(getUserString());
							}catch(Exception e){}
						}
					});
				}catch(Exception e){}
			}
		});
		categoryManagement.add("end frag");
		registry.put("end frag", new HvlAction0(){
			@Override
			public void run(){
				new NodeEndFragSet(HvlCamera.getX(), HvlCamera.getY());
			}
		});
		categoryManagement.add("end");
		registry.put("end", new HvlAction0(){
			@Override
			public void run(){
				new NodeEnd(HvlCamera.getX(), HvlCamera.getY());
			}
		});
		
		registrySubList.put("code", categoryManagement);
		registrySubList.put("float", categoryFloat);
		registrySubList.put("color", categoryColor);
		registrySubList.put("boolean", categoryBoolean);
		registrySubList.put("cast", categoryCasting);
		registrySubList.put("frag", categoryFragment);
		registrySubList.put("org", categoryOrganization);
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

	public static final float WIDTH_ORGCNCTR = 32, WIDTH_MEGASHORT = 64, WIDTH_EXTRASHORT = 128, WIDTH_SHORT = 192, WIDTH_MEDSHORT = 224, WIDTH_NORMAL = 256, WIDTH_LARGE = 288;
	
	private ArrayList<Pin> inputs = new ArrayList<>(), outputs = new ArrayList<>();

	private float x, y, width;
	private Color color;
	private String name;

	private HvlAction1<Node> onDialogueClick;

	public Node(String nameArg, float xArg, float yArg, float widthArg, Color colorArg){
		name = nameArg;
		x = xArg;
		y = yArg;
		width = widthArg;
		color = colorArg;
		nodes.add(this);
	}

	public String getName(){
		return name;
	}

	public void setName(String nameArg){
		name = nameArg;
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

	public void setWidth(float width){
		this.width = width;
	}

	public float getWidth(){
		return width;
	}

	public Color getColor(){
		return color;
	}

	public abstract ArrayList<String> getContent();

	public abstract Node getNext();

	public void draw(float delta){
		hvlDrawQuad(x, y, width, 32 + (Math.max(inputs.size(), outputs.size()) * 32), color);
		float value = Interactor.mouseNode == this ? 0.2f : -0.2f;
		hvlDrawQuad(x, y, 16, 32, new Color(color.r + value, color.g + value, color.b + value));
		Main.font.drawWord(name, x + (width/2) - (Main.font.getLineWidth(name)/20), y + 6, 0.1f, Color.white);
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

	public HvlAction1<Node> getOnDialogueClick() {
		return onDialogueClick;
	}

	public void setOnDialogueClick(HvlAction1<Node> onDialogueClickArg) {
		onDialogueClick = onDialogueClickArg;
	}

}
