package com.md04.group2.rainbowbubbles.game;
import java.io.IOException;
import java.util.Random;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

public class Bubbles{	
	private int color;
	private int positionX;
	private int positionY;
	private final int Width = 240;
	private final int Height = 260;
	private final int bubbleRadius = 15;
	private final int imageWidth = 34;
	private final int imageHeight = 34;
	private int bubbleCenterX, bubbleCenterY;
	private Image img;
	private Random rd = new Random() ;
	public Sprite bubblesLayer;
//	private TiledLayer bubblesLayer;
	private int bubblesDirectionX,bubblesDirectionY ;
	private int[] bubblesMoveDistance = {1,2};
	private int bubblesMoveDistanceX,bubblesMoveDistanceY;
	
	public Bubbles() {
		color = rd.nextInt(7);
		initImage();
		initInstance();		
		createBubbles();		
	}
	
	public Bubbles(int color) {
		this.color = color;
		initImage();
		initInstance();		
		createBubbles();		
	}
	
	private void initInstance() {
		positionX = 200;
		positionY = Height;
		bubblesDirectionX = 0;
		bubblesDirectionY = 1;		
	}

	private void createBubbles() {
		bubblesLayer = new Sprite(img,imageWidth, imageHeight);
		bubblesLayer.setFrame(color);
		bubblesLayer.setPosition(positionX, positionY);	

	}

	private void initImage() {
		try {
			img = Image.createImage("/bubbles.png");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public int getColor(){
		return color;
	}
	
	public int getPositionX(){
		return positionX;
	}
	
	public int getPositionY(){
		return positionY;
	}
	
	public int getBubbleRadius(){
		return bubbleRadius;
	}
	
	public int getBubbleCenterX(){
		bubbleCenterX = positionX+bubbleRadius;
		return bubbleCenterX;
	}
	
	public int getBubbleCenterY(){
		bubbleCenterY = positionY+bubbleRadius;
		return bubbleCenterY;
	}
	
	public void setBubbleDirectionX(int DirectionX){
		bubblesDirectionX = DirectionX;
		bubblesMoveDistanceX = rd.nextInt(3);
	}
	
	public void setBubbleDirectionY(int DirectionY){
		bubblesDirectionY = DirectionY;
		bubblesMoveDistanceY = rd.nextInt(3);
	}
	
	public int getBubbleDirectionX(){
		return bubblesDirectionX;
	}
	
	public int getBubbleDirectionY(){
		return bubblesDirectionY;
	}
	
	public void updatePositionApear(){
		checkColisionWithWall();
		if (bubblesDirectionX==0)
			positionX= positionX + bubblesMoveDistance[bubblesMoveDistanceX]+1;	
		else if (bubblesDirectionX==1)
			positionX= positionX - bubblesMoveDistance[bubblesMoveDistanceX]-1;			
		
		if (bubblesDirectionY==0)
			positionY= positionY + bubblesMoveDistance[bubblesMoveDistanceY]+1;	
		else if (bubblesDirectionY==1)
			positionY= positionY - bubblesMoveDistance[bubblesMoveDistanceY]-1;		
	}
	
	public void updatePosition(){
		checkColisionWithWall();
		if (bubblesDirectionX==0)
			positionX= positionX + bubblesMoveDistance[bubblesMoveDistanceX];	
		else if (bubblesDirectionX==1)
			positionX= positionX - bubblesMoveDistance[bubblesMoveDistanceX];			
		
		if (bubblesDirectionY==0)
			positionY= positionY + bubblesMoveDistance[bubblesMoveDistanceY];	
		else if (bubblesDirectionY==1)
			positionY= positionY - bubblesMoveDistance[bubblesMoveDistanceY];			
	}
	
	public void checkColisionWithWall(){
		if (positionX <= 0){
			bubblesDirectionX = 0;
			bubblesMoveDistanceX = rd.nextInt(2);
		}
		if (positionX >= Width-(2*bubbleRadius)){
			bubblesDirectionX = 1;		
			bubblesMoveDistanceX = rd.nextInt(2);
		}
		if (positionY <= 0){
			bubblesDirectionY = 0;
			bubblesMoveDistanceY = rd.nextInt(2);
		}
		if (positionY >= Height-(2*bubbleRadius)){
			bubblesDirectionY = 1;
			bubblesMoveDistanceY = rd.nextInt(2);
		}
	}
	
	public boolean checkColisionTogether(Sprite anotherBubble){
		if (bubblesLayer.collidesWith(anotherBubble, true))
			return true;
		return false;
	}
	
	public void solveColision(int intersectPointX, int intersectPointY){
		if (positionX < intersectPointX){
			bubblesDirectionX = 1;
		}
		if (positionX >= intersectPointX-bubbleRadius){
			bubblesDirectionX = 0;		
		}
		if (positionY < intersectPointY){
			bubblesDirectionY = 1;
		}
		if (positionY >= intersectPointY-bubbleRadius){
			bubblesDirectionY = 0;
		}
	}
}







