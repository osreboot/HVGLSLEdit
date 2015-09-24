package com.osreboot.glsledit;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawLine;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import com.osreboot.glsledit.pin.PinOrganization;
import com.osreboot.ridhvl.HvlCoord;
import com.osreboot.ridhvl.painter.HvlCamera;
import com.osreboot.ridhvl.painter.HvlCursor;

public class Interactor {

	public static Node mouseNode = null;
	public static Pin mousePin = null;

	private static HvlCoord displacement;

	private static boolean clicked = false;

	public static void update(float delta){
		if(Mouse.isButtonDown(0)){
			if(!clicked){
				if(HvlCursor.getCursorX() > 128) onClick();
				clicked = true;
			}

			if(mouseNode != null){
				mouseNode.setX(mouseNode.getX() - displacement.x + HvlCursor.getCursorX());
				mouseNode.setY(mouseNode.getY() - displacement.y + HvlCursor.getCursorY());
			}

			if(mousePin != null){
				hvlDrawLine(mousePin.getX(), mousePin.getY(), (float)HvlCursor.getCursorX() + HvlCamera.getX() - (Display.getWidth()/2), (float)HvlCursor.getCursorY() + HvlCamera.getY() - (Display.getHeight()/2), Overlay.WIRE_TEMPORARY, Overlay.WIDTH_THIN);
			}

		}else{
			clicked = false;
			onReleaseClick();
			mouseNode = null;
			mousePin = null;
		}
		if(Mouse.isButtonDown(2)){
			HvlCamera.setPosition(HvlCamera.getX() + displacement.x - HvlCursor.getCursorX(), HvlCamera.getY() + displacement.y - HvlCursor.getCursorY());
		}
		displacement = new HvlCoord(HvlCursor.getCursorX(), HvlCursor.getCursorY());
	}

	private static void onClick(){
		if(mouseNode == null && mousePin == null){
			for(Node n : Node.getNodes()){
				float x = n.getX() - HvlCamera.getX() + (Display.getWidth()/2);
				float y = n.getY() - HvlCamera.getY() + (Display.getHeight()/2);
				if(HvlCursor.getCursorX() > x && HvlCursor.getCursorX() < x + 16 && 
						HvlCursor.getCursorY() > y && HvlCursor.getCursorY() < y + 32 && mouseNode == null){
					n.setX(n.getX() - displacement.x + HvlCursor.getCursorX());
					n.setY(n.getY() - displacement.y + HvlCursor.getCursorY());
					mouseNode = n;
				}
			}
		}

		if(mouseNode == null && mousePin == null){
			for(Node n : Node.getNodes()){
				for(Pin p : n.getAllPins()){
					if(p instanceof PinOrganization || n.getOutputs().contains(p)){
						float x = p.getX() - HvlCamera.getX() + (Display.getWidth()/2);
						float y = p.getY() - HvlCamera.getY() + (Display.getHeight()/2);
						if(HvlCursor.getCursorX() > x - 8 && HvlCursor.getCursorX() < x + 8 && 
								HvlCursor.getCursorY() > y - 8 && HvlCursor.getCursorY() < y + 8 && mousePin == null){
							mousePin = p;
						}
					}
				}
			}
		}

		if(mouseNode == null && mousePin == null){
			for(Node n : Node.getNodes()){
				float x = n.getX() - HvlCamera.getX() + (Display.getWidth()/2);
				float y = n.getY() - HvlCamera.getY() + (Display.getHeight()/2);
				if(HvlCursor.getCursorX() > x + 32 && HvlCursor.getCursorX() < x + n.getWidth() &&
						HvlCursor.getCursorY() > y && HvlCursor.getCursorY() < y + 32) if(n.getOnDialogueClick() != null) n.getOnDialogueClick().run(n);
			}
		}
	}

	private static void onReleaseClick(){
		if(isHoveringRemove()) Node.removeNode(mouseNode);
		if(mousePin != null){
			boolean set = false;
			for(Node n : Node.getNodes()){
				for(Pin p : n.getAllPins()){
					if(p instanceof PinOrganization || n.getInputs().contains(p)){
						float x = p.getX() - HvlCamera.getX() + (Display.getWidth()/2);
						float y = p.getY() - HvlCamera.getY() + (Display.getHeight()/2);
						if(HvlCursor.getCursorX() > x - 8 && HvlCursor.getCursorX() < x + 8 && 
								HvlCursor.getCursorY() > y - 8 && HvlCursor.getCursorY() < y + 8 && mousePin != p){
							Pin connection = Pin.findOutputConnection(p);
							if(connection != null && !(mousePin instanceof PinOrganization)) connection.removeConnection(p);
							mousePin.setConnection(p);
							set = true;
						}
					}
				}
			}
			if(!set) mousePin.resetConnections();
		}
	}

	public static boolean isHoveringRemove(){
		return HvlCursor.getCursorX() > Main.getRemoveButton().getX() && HvlCursor.getCursorX() < Main.getRemoveButton().getX() + Main.getRemoveButton().getWidth() && 
				HvlCursor.getCursorY() > Main.getRemoveButton().getY() && HvlCursor.getCursorY() < Main.getRemoveButton().getY() + Main.getRemoveButton().getHeight()&& mouseNode != null && mouseNode != Main.getStarterNode();
	}

}
