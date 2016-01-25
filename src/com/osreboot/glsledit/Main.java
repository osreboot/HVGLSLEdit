package com.osreboot.glsledit;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.*;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import com.osreboot.glsledit.node.NodeBasicStart;
import com.osreboot.ridhvl.HvlFontUtil;
import com.osreboot.ridhvl.action.HvlAction0;
import com.osreboot.ridhvl.action.HvlAction1;
import com.osreboot.ridhvl.action.HvlAction2;
import com.osreboot.ridhvl.action.HvlAction3;
import com.osreboot.ridhvl.display.collection.HvlDisplayModeResizable;
import com.osreboot.ridhvl.menu.HvlComponent;
import com.osreboot.ridhvl.menu.HvlComponentDefault;
import com.osreboot.ridhvl.menu.HvlMenu;
import com.osreboot.ridhvl.menu.component.*;
import com.osreboot.ridhvl.menu.component.HvlArrangerBox.ArrangementStyle;
import com.osreboot.ridhvl.menu.component.HvlSlider.Direction;
import com.osreboot.ridhvl.menu.component.collection.HvlLabeledButton;
import com.osreboot.ridhvl.painter.HvlCamera;
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
	private static HvlListBox nodeCategoryList, nodeSubList;

	public static HvlCamera camera;

	@Override
	public void initialize(){
		getTextureLoader().loadResource("Icon");
		getTextureLoader().loadResource("Font");

		font = new HvlFontPainter2D(getTextureLoader().getResource(1), HvlFontUtil.DEFAULT, 112, 144);

		Compiler.initialize();

		Node.initialize();

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

		HvlSlider sliderDefault = new HvlSlider(16, 256, Direction.VERTICAL, 16, 64, 0, new HvlComponentDrawable(){
			@Override
			public void draw(float delta, float x, float y, float width, float height){//slider handle
				hvlDrawQuad(x, y, width, height, Color.darkGray);
			}
		}, new HvlComponentDrawable(){
			@Override
			public void draw(float delta, float x, float y, float width, float height){//slider background
				hvlDrawQuad(x, y, width, height, Color.gray);
			}
		});
		HvlComponentDefault.setDefault(sliderDefault);

		HvlComponentDefault.setDefault(new HvlButton(16, 16, new HvlComponentDrawable(){
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
		}));

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
		sideBar.setxAlign(0.5f);
		sideBar.setyAlign(0.5f);
		sideBar.setBorderU(12);
		sideBar.setBorderD(12);
		main.add(sideBar);
		main.getFirstOfType(HvlArrangerBox.class).add(new HvlLabeledButton.Builder().setText("compile").setClickedCommand(new HvlAction1<HvlButton>(){
			@Override
			public void run(HvlButton button){
				Compiler.compile();
			}
		}).build());

		main.getFirstOfType(HvlArrangerBox.class).add(new HvlLabeledButton.Builder().setText("add node").setClickedCommand(new HvlAction1<HvlButton>(){
			@Override
			public void run(HvlButton button){
				if(nodeSubList.getSelectedIndex() != -1) Node.getRegistry().get(nodeSubList.getSelectedItem().toString()).run();
			}
		}).build());

		nodeSubList = new HvlListBox(96, 128, new HvlSlider(16, 256, Direction.VERTICAL, 16, 64, 0, new HvlComponentDrawable(){
			@Override
			public void draw(float delta, float x, float y, float width, float height){//slider handle
				hvlDrawQuad(x, y, width, height, Color.darkGray);
			}
		}, new HvlComponentDrawable(){
			@Override
			public void draw(float delta, float x, float y, float width, float height){//slider background
				hvlDrawQuad(x, y, width, height, Color.gray);
			}
		}), new HvlButton.Builder().build(), new HvlButton.Builder().build(), font, new HvlComponentDrawable(){
			@Override
			public void draw(float delta, float x, float y, float width, float height){//item on
				hvlDrawQuad(x, y, width, height, Color.gray);
			}
		}, new HvlComponentDrawable(){
			@Override
			public void draw(float delta, float x, float y, float width, float height){//item off
				hvlDrawQuad(x, y, width, height, Color.darkGray);
			}
		}, 16, 8);
		nodeSubList.setTextScale(0.1f);
		main.getFirstOfType(HvlArrangerBox.class).add(nodeSubList);

		nodeCategoryList = new HvlListBox(96, 128, new HvlSlider(16, 256, Direction.VERTICAL, 16, 64, 0, new HvlComponentDrawable(){
			@Override
			public void draw(float delta, float x, float y, float width, float height){//slider handle
				hvlDrawQuad(x, y, width, height, Color.darkGray);
			}
		}, new HvlComponentDrawable(){
			@Override
			public void draw(float delta, float x, float y, float width, float height){//slider background
				hvlDrawQuad(x, y, width, height, Color.gray);
			}
		}), new HvlButton.Builder().build(), new HvlButton.Builder().build(), font, new HvlComponentDrawable(){
			@Override
			public void draw(float delta, float x, float y, float width, float height){//item on
				hvlDrawQuad(x, y, width, height, Color.gray);
			}
		}, new HvlComponentDrawable(){
			@Override
			public void draw(float delta, float x, float y, float width, float height){//item off
				hvlDrawQuad(x, y, width, height, Color.darkGray);
			}
		}, 16, 8);
		for(String s : Node.getSubRegistry().keySet()) nodeCategoryList.addItem(s);
		nodeCategoryList.setTextScale(0.1f);
		nodeCategoryList.setSelectionChangedCommand(new HvlAction3<HvlListBox, Integer, Object>(){
			@Override
			public void run(HvlListBox l, Integer i, Object o){
				if(i != -1){
					nodeSubList.getItems().clear();
					for(String s : Node.getSubRegistry().get(l.getSelectedItem().toString())) nodeSubList.addItem(s);
					nodeSubList.setSelectedIndex(-1);
				}
			}
		});
		main.getFirstOfType(HvlArrangerBox.class).add(nodeCategoryList);

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
		main.getFirstOfType(HvlArrangerBox.class).add(removeButton);

		main.getFirstOfType(HvlArrangerBox.class).add(new HvlLabeledButton.Builder().setText("load").setClickedCommand(new HvlAction1<HvlButton>(){
			@Override
			public void run(HvlButton button){
				//Compiler.compile();
			}
		}).build());

		main.getFirstOfType(HvlArrangerBox.class).add(new HvlLabeledButton.Builder().setText("save").setClickedCommand(new HvlAction1<HvlButton>(){
			@Override
			public void run(HvlButton button){
				//Compiler.compile();
			}
		}).setDrawOverride(new HvlAction2<HvlComponent, Float>(){
			@Override
			public void run(HvlComponent a, Float delta){
				((HvlButton)a).setEnabled(Node.getNodes().size() > 0);
				a.draw(delta);
			}
		}).build());

		HvlMenu.setCurrent(main);

		starterNode = new NodeBasicStart(0, 0);
		camera = new HvlCamera(starterNode.getX(), starterNode.getY(), 0f, 1f, HvlCamera.ALIGNMENT_CENTER);
	}

	@Override
	public void update(final float delta){
		camera.doTransform(new HvlAction0(){
			@Override
			public void run(){
				for(Node n : Node.getNodes()) n.draw(delta);
				Overlay.drawWires(delta);
			}
		});

		Compiler.draw(delta);
		HvlMenu.updateMenus(delta);

		camera.doTransform(new HvlAction0(){
			@Override
			public void run(){
				Interactor.update(delta);
			}
		});
	}

	public static Node getStarterNode(){
		return starterNode;
	}

	public static HvlLabeledButton getRemoveButton(){
		return removeButton;
	}

}
