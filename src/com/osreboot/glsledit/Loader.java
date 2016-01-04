package com.osreboot.glsledit;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.newdawn.slick.Color;

import com.osreboot.ridhvl.HvlReflectionUtil;

public class Loader {

	public static void loadProgram(File fileArg) {
		Node.getNodes().clear();
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fileArg));
			
			boolean inNode = false;
			int nodeId = -1;
			String nodeClassName = "";
			float nodeX = 0f, nodeY = 0f;
			
			Map<Integer, Map<String, String>> pinConnections = new HashMap<>();
			
			Map<String, String> pins = null;
			Map<String, String> vars = null;
			
			String line;
			
			while ((line = reader.readLine()) != null) {
				if (line.startsWith("Node:")) {
					// Construct previous node
					if (inNode) {
						createNode(nodeId, nodeClassName, nodeX, nodeY, vars);
						pinConnections.put(nodeId, pins);
					}
					inNode = true;
					
					String modLine = line.replaceFirst("Node:", "");
					
					String[] parts = modLine.split(",");
					
					nodeId = Integer.parseInt(parts[0]);
					nodeClassName = parts[1];
					nodeX = Float.parseFloat(parts[2]);
					nodeY = Float.parseFloat(parts[3]);
					pins = new HashMap<>();
					vars = new HashMap<>();
				}
				if (line.startsWith("Pin:")) {
					String modLine = line.replaceFirst("Pin:", "");
					
					String[] parts = modLine.split(":");
					
					if (parts.length > 1) {
						pins.put(parts[0], parts[1]);
					}
				}
				if (line.startsWith("Var:")) {
					String modLine = line.replaceFirst("Var:", "");
					
					String[] parts = modLine.split("=");
					
					vars.put(parts[0], parts[1]);
				}
			}
			
			if (inNode) {
				createNode(nodeId, nodeClassName, nodeX, nodeY, vars);
				pinConnections.put(nodeId, pins);
			}
			
			reader.close();
			
			for (Map.Entry<Integer, Map<String, String>> entry : pinConnections.entrySet()) {
				Node base = Node.getNodeWithId(entry.getKey());
				
				for (Map.Entry<String, String> pinEntry : entry.getValue().entrySet()) {
					try {
						Field basePinField = base.getClass().getDeclaredField(pinEntry.getKey());
						basePinField.setAccessible(true);
						Pin basePin = (Pin) basePinField.get(base);
						
						String[] targets = pinEntry.getValue().split(",");
						
						for (String target : targets) {
							String[] targetParts = target.split("\\.");
							
							Integer targetNodeId = Integer.parseInt(targetParts[0]);
							String targetPinName = targetParts[1];
							
							Node targetNode = Node.getNodeWithId(targetNodeId);
							Field targetPinField = targetNode.getClass().getDeclaredField(targetPinName);
							targetPinField.setAccessible(true);
							Pin targetPin = (Pin) targetPinField.get(targetNode);
							
							basePin.setConnection(targetPin);
						}
					} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void saveProgram(String pathArg) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File(pathArg)));

			for (Node n : Node.getNodes()) {
				writer.write("Node:" + n.getId() + "," + n.getClass().getName() + "," + n.getX() + "," + n.getY());
				writer.newLine();

				for (Pin p : n.getOutputs()) {
					writer.write("Pin:" + getPinVar(p, n).getName() + ":");
					
					List<Pin> connections = p.getConnections();
					
					for (Pin conn : connections) {
						writer.write("" + conn.getParent().getId());
						writer.write(".");
						writer.write(getPinVar(conn, conn.getParent()).getName() + ",");
					}
					
					writer.newLine();
				}
				
				for (Field f : n.getClass().getDeclaredFields()) {
					if (Pin.class.isAssignableFrom(f.getType()))
						continue;
					
					writer.write("Var:" + f.getName() + "=");
					
					f.setAccessible(true);
					
					try {
						String toWrite = f.get(n).toString();
						
						if (Color.class.isAssignableFrom(f.getType())) {
							Color color = (Color) f.get(n);
							
							toWrite = color.r + "," + color.g + "," + color.b + "," + color.a;
						}
						
						writer.write(toWrite + ",");
						writer.newLine();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}

			writer.close();
		} catch (IOException | IllegalArgumentException e) {
			e.printStackTrace();
		}
	}
	
	private static void createNode(int nodeId, String nodeClassName, float nodeX, float nodeY, Map<String, String> vars) {
		try {
			Class<?> cl = Class.forName(nodeClassName);
			
			Constructor<?> constr = cl.getDeclaredConstructors()[0];
			
			NodeConstructorArg[] argAnnotations = constr.getAnnotationsByType(NodeConstructorArg.class);
			
			String[] argVars = new String[argAnnotations.length];
			
			for (int i = 0; i < argVars.length; i++) {
				argVars[i] = argAnnotations[i].value();
			}
			
			Object[] args = new Object[constr.getParameterTypes().length];
			
			args[0] = nodeX;
			args[1] = nodeY;
			
			for (int i = 0; i < argVars.length; i++) {
				args[i + 2] = getValue(cl.getField(argVars[i]), vars.get(argVars[i]));
			}
			
			Node toAdd = (Node) constr.newInstance(args);
			toAdd.setId(nodeId);
			
			for (Map.Entry<String, String> varInfo : vars.entrySet()) {
				cl.getField(varInfo.getKey()).set(toAdd, getValue(cl.getField(varInfo.getKey()), varInfo.getValue()));
			}
		} catch (ClassNotFoundException | SecurityException | NoSuchFieldException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	private static Object getValue(Field f, String in) {
		if (Color.class.isAssignableFrom(f.getType())) {
			String[] parts = in.split(",");
			return new Color(Float.parseFloat(parts[0]), Float.parseFloat(parts[1]), Float.parseFloat(parts[2]), Float.parseFloat(parts[3]));
		}
		
		return HvlReflectionUtil.genericParse(f.getType(), in);
	}
	
	private static Field getPinVar(Pin p, Node toSearch) {
		for (Field f : toSearch.getClass().getDeclaredFields()) {
			if (!Pin.class.isAssignableFrom(f.getType()))
				continue;
			
			f.setAccessible(true);
			
			try {
				Pin pin = (Pin) f.get(toSearch);
				
				if (pin.equals(p))
					return f;
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}
}
