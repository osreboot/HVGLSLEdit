package com.osreboot.glsledit;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

import org.newdawn.slick.Color;

public class Loader {

	public static void loadProgram(File fileArg) {
		Node.getNodes().clear();

	}

	public static void saveProgram(String pathArg) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File(pathArg)));

			for (Node n : Node.getNodes()) {
				writer.write("Node:" + n.getId() + "," + n.getClass().getName() + "," + n.getX() + "," + n.getY());
				writer.newLine();

				for (Pin p : n.getOutputs()) {
					writer.write("Pin:" + getPinVar(p, n).getName() + ",");
					
					List<Pin> connections = p.getConnections();
					
					for (Pin conn : connections) {
						writer.write("" + conn.getParent().getId());
						writer.write(".");
						writer.write(getPinVar(conn, conn.getParent()).getName());
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
						
						writer.write(toWrite);
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
	
	
	
	public static Field getPinVar(Pin p, Node toSearch) {
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
