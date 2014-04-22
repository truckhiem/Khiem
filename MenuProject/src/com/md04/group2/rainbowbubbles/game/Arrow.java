package com.md04.group2.rainbowbubbles.game;
import java.io.IOException;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;


public class Arrow {
	private Image imgArrow;
	public Sprite arrowLayer;
	private int frameArrow;
	private boolean nextFrame;
	private boolean arrowFly;
	private int centerX = 50;
	private int centerY = 320;
	private int arrowPositionX,arrowPositionY;
	
	public Arrow(){
		frameArrow = 13;
		nextFrame = true;
		arrowFly = false;		
		initImage();
		createArrow();
	}
	
	public Arrow(int frameArrow){
		this.frameArrow = frameArrow;
		arrowPositionX = centerX;
		centerY = 250;
		arrowPositionY = centerY;
		initImage();
		createArrow();
		arrowLayer.setFrame(frameArrow);
	}
	
	private void createArrow() {
		arrowLayer = new Sprite(imgArrow,160,80);
		arrowLayer.setFrame(frameArrow);
		arrowLayer.setPosition(centerX, centerY);
	}
	
	private void initImage(){
		try {
			imgArrow = Image.createImage("/arrowLayer3.png");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int getArrowFrame(){
		return frameArrow;
	}
	
	public void setArrowFly(boolean arrowFly){
		this.arrowFly = arrowFly;
	}
	
	public boolean getArrowFly(){
		return arrowFly;
	}
	
	public int getCenterX(){
		return centerX;
	}
	
	public int getCenterY(){
		return centerY;
	}
	
	public int getArrowPositionX(){
		return arrowPositionX;
	}
	
	public int getArrowPositionY(){
		return arrowPositionY;
	}
	
	public void updateArrow(){		
		if (frameArrow == 0){
			nextFrame = true;
		}
		if (frameArrow == 26)
			nextFrame = false;
		if (nextFrame)
			frameArrow++;
		else 
			frameArrow--;
		arrowLayer.setFrame(frameArrow);
	}
	
	public void flyArrow(){
		int angleRoad = (frameArrow * 5) +25;
		double a, b;		
		if (angleRoad != 90){
			a = Math.tan(Math.toRadians(angleRoad));
			b = centerY - (a*centerX);			
			arrowPositionY-=20;	
			arrowPositionX = (int) ((arrowPositionY-b)/a);
		}else{
			arrowPositionY-=20;
		}
		
	}
	
	public boolean arrowApear(){
		if (centerY >=250){
			arrowLayer.setPosition(centerX, centerY--);
			return false;
		}else
			return true;
	}
	
	public boolean arrowDisapear(){
		if ((arrowPositionX+75>=240)||(arrowPositionX+70<=0)||(arrowPositionY+80<=0)){
			return true;
		}
		return false;			
	}
	
	public boolean checkColision(Sprite bubble){
		if (arrowLayer.collidesWith(bubble, true)){
			return true;
		}
		return false;
	}
}
