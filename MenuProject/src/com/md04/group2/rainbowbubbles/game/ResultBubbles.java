package com.md04.group2.rainbowbubbles.game;
import java.io.IOException;

import javax.microedition.lcdui.Image;
//import javax.microedition.lcdui.game.Sprite;

import javax.microedition.lcdui.game.TiledLayer;

public class ResultBubbles {
	private Image img;
//	private Sprite bubbleResultLayer;
	private TiledLayer bubbleResultLayer;
	private final int positionX = 10;
	private final int positionY = 286;
	private final int imageWidth = 34;
	private final int imageHeight = 34;
	
	public ResultBubbles(){
		initImage();
		createResult();
	}

	public void setResultBubbles(int numOfShotBubbles, int color){
		color++;
		bubbleResultLayer.setCell(numOfShotBubbles, 0, color);
	}
	
	private void createResult() {
		bubbleResultLayer = new TiledLayer(3, 1, img, imageWidth, imageHeight);		
		bubbleResultLayer.setPosition(positionX, positionY);		
	}
	
	public TiledLayer getResultBubbles(){
		return bubbleResultLayer;
	}
	
	public void setVisible(boolean visible){
		bubbleResultLayer.setVisible(visible);
	}
	
	public void removeResultBubbles(){
		bubbleResultLayer = null;
		createResult();
	}
	
	private void initImage() {
		try {
			img = Image.createImage("/bubbles.png");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	
	
}
