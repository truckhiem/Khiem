package com.md04.group2.rainbowbubbles.game;

import java.io.IOException;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

public class BubbleBroken {
	private Image img;
	private int positionX, positionY;
	private Sprite bubbleBrokenLayer;
	
	public BubbleBroken(int positionX, int positionY){
		initImage();
		this.positionX = positionX;
		this.positionY = positionY;
		createBubbleBroken();
	}

	private void createBubbleBroken() {
		bubbleBrokenLayer = new Sprite(img);
		bubbleBrokenLayer.setPosition(positionX, positionY);		
	}
	
	public Sprite getBubbleBrokenLayer(){
		return bubbleBrokenLayer;
	}

	private void initImage() {
		try {
			img = Image.createImage("/red.png");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
