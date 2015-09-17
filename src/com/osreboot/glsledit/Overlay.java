package com.osreboot.glsledit;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.*;

import java.util.ArrayList;

import org.newdawn.slick.Color;

import com.osreboot.ridhvl.HvlCoord;

public class Overlay {

	private static ArrayList<HvlCoord> startValues = new ArrayList<>();
	private static ArrayList<HvlCoord> endValues = new ArrayList<>();
	private static ArrayList<Color> colorValues = new ArrayList<>();
	
	public static void drawWires(float delta){
		for(HvlCoord start : startValues){
			HvlCoord end = endValues.get(startValues.indexOf(start));
			hvlDrawLine(start.x, start.y, end.x, end.y, colorValues.get(startValues.indexOf(start)));
		}
		startValues.clear();
		endValues.clear();
		colorValues.clear();
	}
	
	public static void addWireConnection(HvlCoord arg1, HvlCoord arg2, Color colorArg){
		startValues.add(arg1);
		endValues.add(arg2);
		colorValues.add(colorArg);
	}
	
}
