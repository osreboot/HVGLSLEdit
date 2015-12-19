package com.osreboot.glsledit;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import com.osreboot.ridhvl.painter.HvlRenderFrame;
import com.osreboot.ridhvl.painter.HvlShader;
import com.osreboot.ridhvl.template.HvlTemplate;
import com.osreboot.ridhvl.template.HvlTemplateInteg2D;

public class Compiler {

	public static final String SHADER_ERROR = "SHADER ERROR";
	
	private static HvlRenderFrame frame;
	private static HvlShader shader;
	private static boolean failed = false;

	public static void initialize(){
		frame = new HvlRenderFrame(512, 512);
	}

	public static void compile(){
		failed = false;
		Main.font.drawWord("compiling...", (Display.getWidth()/2) - (Main.font.getLineWidth("compiling...")/4), (Display.getHeight()/2) - 28, 0.5f, Color.green);

		String program = "";
		Node current = Main.getStarterNode();
		while(current != null){
			for(String s : current.getContent()){
				if(s.equals(SHADER_ERROR)) failed = true;
				program += s;
				program += System.lineSeparator();
			}
			current = current.getNext();
		}
		try{
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File("shader\\Compile.hvlfg")));
			writer.write(program);
			writer.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		shader = new HvlShader(HvlShader.PATH_SHADER_DEFAULT + "Compile" + HvlShader.SUFFIX_FRAGMENT);
	}

	public static void draw(float delta){
		frame.setX(Display.getWidth() - 512);
		frame.setY((Display.getHeight()/2) - 256);
		HvlRenderFrame.setCurrentRenderFrame(frame);
		hvlRotate(Display.getWidth() - 256, (Display.getHeight()/2), HvlTemplate.getNewestInstance().getTimer().getTotalTime()*50 % 360);
		hvlDrawQuad(Display.getWidth() - 512 + 128, (Display.getHeight()/2) - 128, 256, 256, HvlTemplateInteg2D.getTexture(0));
		hvlResetRotation();
		HvlRenderFrame.setCurrentRenderFrame(null);
		if(shader != null && !failed){
			HvlShader.setCurrentShader(shader);
			hvlDrawQuad(Display.getWidth() - 512, (Display.getHeight()/2) - 256, 512, 512, frame);
			HvlShader.setCurrentShader(null);
		}else{
			Main.font.drawWord("shader error", Display.getWidth() - 256 - (Main.font.getLineWidth("shader error")/8), (Display.getHeight()/2) - 14, 0.25f, Color.red);
		}

	}

}
