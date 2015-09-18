package com.osreboot.glsledit;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.*;

import java.util.ArrayList;

import org.newdawn.slick.Color;

import com.osreboot.ridhvl.HvlCoord;

public class Overlay {

	public static final Color WIRE_BOOLEAN = new Color(0.1f, 1f, 0.1f), 
			WIRE_COLOR = new Color(1f, 0.1f, 0.1f), 
			WIRE_EXECUTE = new Color(1f, 0.1f, 1f), 
			WIRE_FLOAT = new Color(1f, 0.5f, 0.1f), 
			WIRE_TEMPORARY = Color.gray,
			WIRE_ORGANIZATION = new Color(1f, 1f, 0);
	
	public static final float WIRE_THIN = 1f, WIRE_MEDTHICK = 2f, WIRE_THICK = 3f;
	
	private static ArrayList<HvlCoord> startValues = new ArrayList<>();
	private static ArrayList<HvlCoord> endValues = new ArrayList<>();
	private static ArrayList<Color> colorValues = new ArrayList<>();
	private static ArrayList<Float> widthValues = new ArrayList<>();
	
	public static void drawWires(float delta){
		for(HvlCoord start : startValues){
			HvlCoord end = endValues.get(startValues.indexOf(start));
			hvlDrawLine(start.x, start.y, end.x, end.y, colorValues.get(startValues.indexOf(start)), widthValues.get(startValues.indexOf(start)));
		}
		startValues.clear();
		endValues.clear();
		colorValues.clear();
		widthValues.clear();
	}
	
	public static void addWireConnection(HvlCoord arg1, HvlCoord arg2, Color colorArg, float widthArg){
		startValues.add(arg1);
		endValues.add(arg2);
		colorValues.add(colorArg);
		widthValues.add(widthArg);
	}
	
}
