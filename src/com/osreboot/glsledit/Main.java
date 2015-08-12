package com.osreboot.glsledit;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.*;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import com.osreboot.glsledit.node.NodeArbitraryFloat;
import com.osreboot.glsledit.node.NodeBasicEnd;
import com.osreboot.glsledit.node.NodeBasicStart;
import com.osreboot.glsledit.node.NodeBasicTestAdd;
import com.osreboot.glsledit.node.NodeBasicTestSubtract;
import com.osreboot.ridhvl.HvlFontUtil;
import com.osreboot.ridhvl.action.HvlAction2;
import com.osreboot.ridhvl.display.collection.HvlDisplayModeResizable;
import com.osreboot.ridhvl.menu.HvlComponent;
import com.osreboot.ridhvl.menu.HvlComponentDefault;
import com.osreboot.ridhvl.menu.HvlMenu;
import com.osreboot.ridhvl.menu.component.HvlArrangerBox;
import com.osreboot.ridhvl.menu.component.HvlButton.OnClickedCommand;
import com.osreboot.ridhvl.menu.component.HvlComponentDrawable;
import com.osreboot.ridhvl.menu.component.HvlArrangerBox.ArrangementStyle;
import com.osreboot.ridhvl.menu.component.HvlButton;
import com.osreboot.ridhvl.menu.component.collection.HvlLabeledButton;
import com.osreboot.ridhvl.painter.HvlCamera;
import com.osreboot.ridhvl.painter.HvlCamera.HvlCameraAlignment;
import com.osreboot.ridhvl.painter.painter2d.HvlFontPainter2D;
import com.osreboot.ridhvl.template.HvlTemplateInteg2D;

public class Main extends HvlTemplateInteg2D{

	public static void main(String args[]){
		new Main();
	}
	
	public Main(){
		super(60, 1280, 720, "High Velocity GLSL Editor ", new HvlDisplayModeResizable());
	}

	public static HvlFontPainter2D font;
	
	public static HvlMenu main;
	private static HvlLabeledButton removeButton;
	
	private static Node starterNode;
	
	@Override
	public void initialize(){
		getTextureLoader().loadResource("Icon");
		getTextureLoader().loadResource("Font");
		
		font = new HvlFontPainter2D(getTextureLoader().getResource(1), HvlFontUtil.DEFAULT, 2048, 2048, 112, 144, 18);
		
		Compiler.initialize();
		
		HvlLabeledButton labeledButtonDefault = new HvlLabeledButton(64, 64, new HvlComponentDrawable(){
			@Override
			public void draw(float delta, float x, float y, float width, float height){
				hvlDrawQuad(x, y, width, height, Color.darkGray);
			}
		}, new HvlComponentDrawable(){
			@Override
			public void draw(float delta, float x, float y, float width, float height){
				hvlDrawQuad(x, y, width, height, Color.lightGray);
			}
		}, new HvlComponentDrawable(){
			@Override
			public void draw(float delta, float x, float y, float width, float height){
				hvlDrawQuad(x, y, width, height, Color.darkGray);
			}
		}, font, "", 0.075f, Color.white);
		labeledButtonDefault.setAlign(0.5f);
		HvlComponentDefault.setDefault(labeledButtonDefault);
		
		main = new HvlMenu();
		
		HvlArrangerBox sideBar = new HvlArrangerBox(0, 0, 128, Display.getHeight(), ArrangementStyle.VERTICAL);
		sideBar.setDrawOverride(new HvlAction2<HvlComponent, Float>(){
			@Override
			public void run(HvlComponent component, Float delta){
				hvlDrawQuad(component.getX(), component.getY(), component.getWidth(), component.getHeight(), Color.gray);
				component.setHeight(Display.getHeight());
				component.draw(delta);
			}
		});
		sideBar.setAlign(0.5f);
		sideBar.setBorderU(16);
		sideBar.setBorderD(16);
		main.add(sideBar);
		main.getFirstChildOfType(HvlArrangerBox.class).add(new HvlLabeledButton.Builder().setText("compile").setClickedCommand(new OnClickedCommand(){
			@Override
			public void run(HvlButton button){
				Compiler.compile();
			}
		}).build());
		
		final HvlComponentDrawable removeOnDrawable = new HvlComponentDrawable(){
			@Override
			public void draw(float delta, float x, float y, float width, float height){
				hvlDrawQuad(x, y, width, height, Color.lightGray);
			}
		};
		final HvlComponentDrawable removeOffDrawable = new HvlComponentDrawable(){
			@Override
			public void draw(float delta, float x, float y, float width, float height){
				hvlDrawQuad(x, y, width, height, Color.darkGray);
			}
		};
		removeButton = new HvlLabeledButton.Builder().setText("remove").setEnabled(false).setUpdateOverride(new HvlAction2<HvlComponent, Float>() {
			@Override
			public void run(HvlComponent component, Float delta){
				((HvlLabeledButton)(component)).setOffDrawable(Interactor.isHoveringRemove() ? removeOnDrawable : removeOffDrawable);
				component.update(delta);
			}
		}).build();
		main.getFirstChildOfType(HvlArrangerBox.class).add(removeButton);
		
		HvlMenu.setCurrent(main);
		
		HvlCamera.setAlignment(HvlCameraAlignment.CENTER);
		
		starterNode = new NodeBasicStart();
		HvlCamera.setPosition(starterNode.getX(), starterNode.getY());
		new NodeBasicEnd();
		new NodeBasicTestAdd();
		new NodeBasicTestAdd();
		new NodeArbitraryFloat(0.1f);
		new NodeArbitraryFloat(0.5f);
		new NodeArbitraryFloat(1f);
		new NodeBasicTestSubtract();
		new NodeBasicTestSubtract();
		
	}

	@Override
	public void update(float delta){
		HvlCamera.setAlignment(HvlCameraAlignment.CENTER);
		
		for(Node n : Node.getNodes()) n.draw(delta);
		
		HvlCamera.undoTransform();
		Compiler.draw(delta);
		HvlMenu.updateMenus(delta);
		HvlCamera.doTransform();
		
		Interactor.update(delta);
	}

	public static Node getStarterNode(){
		return starterNode;
	}

	public static HvlLabeledButton getRemoveButton(){
		return removeButton;
	}

}
