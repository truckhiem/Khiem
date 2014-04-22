package com.md04.group2.rainbowbubbles.game;
import java.io.IOException;
import java.util.Vector;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.LayerManager;
import javax.microedition.lcdui.game.Sprite;
import javax.microedition.lcdui.game.TiledLayer;


public class BubblesMove {
	private Image imgBackground;
	private LayerManager layerManager;
	private TiledLayer backgroundLayer;
	private Vector vectorBubbles = new Vector();
	private ResultBubbles resultBubbles = new ResultBubbles();
//	private ResultBubbles[] resultBubbles = new ResultBubbles[3];
	private Bubbles bubbles;
	private Arrow rotateArrow, flyArrow;
	private BubbleBroken bubbleBroken;
	private int countBubble, timeToApearBubble, count;
	private final int numOfBubble = 10;
	private final int rainbowColor = 7;
	private boolean apearBubbles, arrowApear, bubbleBreak;
	private int numOfShotBubbles;
	private boolean getScore;
	private int timeToGetScore;
/*Hàm khởi tạo của lớp BubblesMove
 * 
 */
	public BubblesMove(int width, int height) {
		initInstance();
		initImage();			
		createRotateArrow();
		createResultBubbles();
		createBackGround();		
		createBubbles();
		
	}
/* Hàm khởi tạo giá trị của các biến ban đầu
 * 
 */
	private void initInstance() {
		layerManager = new LayerManager();
		countBubble = 1;
		timeToApearBubble = 1;
		arrowApear = false;
		apearBubbles = false;
		bubbleBreak = false;
		count = 0;		
		numOfShotBubbles = 0;
		getScore = false;
		timeToGetScore = 0;
	}
/*Hàm tạo mũi tên xoay theo cung tròn 
 * Khởi tạo biến rotateArrow bằng lớp Arrow
 * Add mũi tên vào layerManager
 */
	private void createRotateArrow() {
		rotateArrow = new Arrow();
		layerManager.append(rotateArrow.arrowLayer);
	}
/* Hàm khởi tạo background
 * Tạo background bằng TiledLayer
 * Add background vào layerManager
 */
	private void createBackGround() {
		backgroundLayer = new TiledLayer(1, 1, imgBackground, 240, 320);
		backgroundLayer.setCell(0, 0, 1);
		backgroundLayer.setPosition(0, 0);
		layerManager.append(backgroundLayer);
	}
/*Hàm tạo bong bóng
 * Khởi tạo biến bubbles từ lớp Bubbles
 * Add biến bubbles vào layerManager
 * Add biến bubbles vào lớp Vector(mảng)
 */
	private void createBubbles() {
		bubbles = new Bubbles();
		layerManager.insert(bubbles.bubblesLayer,0);
		vectorBubbles.addElement(bubbles);
	}
/*Hàm tạo bong bóng cầu vồng
* Khởi tạo biến bubbles từ lớp Bubbles với giá trị color của bong bóng cầu vồng
* Add biến bubbles vào layerManager
* Add biến bubbles vào lớp Vector(mảng)
*/
	private void createRainbowBubbles() {
		bubbles = new Bubbles(rainbowColor);
		layerManager.insert(bubbles.bubblesLayer,0);
		vectorBubbles.addElement(bubbles);
	}
/* Khởi tạo hình ảnh
 * 
 */
	private void initImage() {
		try {
			imgBackground = Image.createImage("/1111.jpg");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
/* Hàm render được gọi từ hàm main (giống như hàm Run)
 * 
 */
	public void render(Graphics g) {
			update();
			paintBubbles(g);
	}
/* Hàm vẽ bong bóng
 * Gọi hàm vẽ bong bóng trong lần đầu tiên
 * Gọi hàm vẽ bong bóng sau khi update vị trí
 * Gọi hàm mũi tên bắn ra
 * Vẽ layerManager
 */
	private void paintBubbles(Graphics g) {
		paintBubbleInFirstime();
		paintUpdateBubbles();
		paintFlyArrow();		
		layerManager.paint(g, 0, 0);		
	}
/*Hàm vẽ mũi tên bắn ra
 * Biến arrowFly trong rotateArrow true khi mũi tên được bắn ra và ngược lại
 * Nếu biến arrowFly vẫn còn true thì vẽ arrowFly ở vị trí đã được update
 */
	private void paintFlyArrow() {
		if (rotateArrow.getArrowFly()){
			flyArrow.arrowLayer.setPosition(flyArrow.getArrowPositionX(), flyArrow.getArrowPositionY());			
		}		
	}
/*Hàm vẽ bong bóng đã update
 * Cho vòng for chạy hết mảng Vector
 * Lấy từng giá trị bong bóng trong mảng Vector
 * Vẽ từng bong bóng theo vị trí đã update
 */
	private void paintUpdateBubbles() {
		for (int i = 0; i< vectorBubbles.size();i++){
			 bubbles = (Bubbles) vectorBubbles.elementAt(i);
			 bubbles.bubblesLayer.setPosition(bubbles.getPositionX(),bubbles.getPositionY());
		}		
	}
/*Hàm vẽ bong bóng ở lần đầu tiên chơi
 * timeToApearBubble là biến delay để xuất hiện bong bóng tiếp theo
 * countBubble là biến đếm bong bóng xuất hiện
 * numOfBubble là tổng số bong bóng xuất hiện trên màn hình
 * Vẽ bong bóng cho đến khi countBubble = numOfBubble
 */
	private void paintBubbleInFirstime() {
		if (countBubble<numOfBubble){
			if (timeToApearBubble%15 == 0){
				createBubbles();
				countBubble++;
				timeToApearBubble=1;
			}
			else timeToApearBubble++;
		}
		else {
			apearBubbles = true;
		}		
	}
/*Hàm update biến
 * Update mũi tên
 * Update va chạm giữa các bong bóng
 * Update bong bóng vỡ
 * Update điểm số 
 */
	private void update() {	
		updateArrowAndBubbles();		
		updateColisionBubbles();
		updateBubbleBroken();	
		updateScore();
	}
/*Hàm update điểm số
 * Khi biến getScore = true là lúc bắt đầu xét điểm
 * Làm cho 3 bong bóng result nhấp nháy trước khi biến mất và xuất hiện bong bóng cầu vồng
 */
	private void updateScore() {
		if (getScore){			
			if (timeToGetScore == 30){
				removeResultBubbles();
				timeToGetScore = 0;
				createRainbowBubbles();
				getScore = false;
			}else if(timeToGetScore%10 ==0){
				resultBubbles.setVisible(true);
				timeToGetScore++;				
			}else if(timeToGetScore%10 ==5){
				resultBubbles.setVisible(false);
				timeToGetScore++;
			}else
				timeToGetScore++;
		}		
	}
/* Hàm update mũi tên và bong bóng
 * Update bong bóng xuất hiện, sau đó mũi tên xuất hiện
 * Update vị trí bong bóng và mũi tên xoay
 * Kiểm tra sự va chạm giữa bong bóng và mũi tên
 */
	private void updateArrowAndBubbles() {
		if(!arrowApear){
			updateBubblesApear();
			if (apearBubbles)				
				arrowApear();
		}else{
			updateBubbles();
			if (!rotateArrow.getArrowFly()){
				rotateArrow.updateArrow();
			}
			else{
				flyArrow.flyArrow();
				checkColisionArrowAndBubbles();
			}
		}	
	}
/*Hàm update hình ảnh bong bóng vỡ
 * Delay hình ảnh bong bóng vỡ trong 5 đơn vị đếm
 * Sau đó remove hình ảnh đó
 */
	private void updateBubbleBroken() {
		if (bubbleBreak){
			if (count != 5)
				count ++;
			else{
				removeBubbleBroken();
				bubbleBreak = false;
				count = 0;
			}
		}		
	}
/*Hàm kiểm tra sự va chạm giữa các bong bóng
 * 
 */
	private void updateColisionBubbles() {
		for (int i = 0; i< vectorBubbles.size()-1;i++){
			bubbles = (Bubbles) vectorBubbles.elementAt(i);
			for(int j =i+1; j<vectorBubbles.size(); j++){
				 Bubbles anotherBubble = (Bubbles) vectorBubbles.elementAt(j);
				 if (bubbles.checkColisionTogether(anotherBubble.bubblesLayer)){
					 int intersectPointX = (bubbles.getBubbleCenterX()+anotherBubble.getBubbleCenterX())/2;
					 int intersectPointY = (bubbles.getBubbleCenterY()+anotherBubble.getBubbleCenterY())/2;
					 bubbles.solveColision(intersectPointX, intersectPointY);
					 anotherBubble.solveColision(intersectPointX, intersectPointY);
				 }
			}
		}
		
	}
/*Hàm xuất hiện mũi tên
 * Gọi hàm xuất hiện mũi tên được viết trong lớp Arrow
 * Sau khi mũi tên xuất hiện thì biến arrowApear = true
 */
	private void arrowApear(){
		if (rotateArrow.arrowApear())
			arrowApear = true;
	}
/*Hàm update những bong bóng vừa xuất hiện
 * Cho vòng for chạy hết mảng vector
 * Update tất cả bong bóng trong mảng vector bằng hàm update được viết trong lớp Bubbles
 */
	private void updateBubblesApear(){
		for (int i = 0; i< vectorBubbles.size();i++){
			 bubbles = (Bubbles) vectorBubbles.elementAt(i);
			 bubbles.updatePositionApear();
		}
	}

	private void updateBubbles(){
		for (int i = 0; i< vectorBubbles.size();i++){
			 bubbles = (Bubbles) vectorBubbles.elementAt(i);
			 bubbles.updatePosition();
		}
	}
	
	public void pointerPressed(){
		if ((!rotateArrow.getArrowFly())&&(arrowApear)){
			rotateArrow.setArrowFly(true);
			createFlyArrow();			
		}
	}
	
	private void createFlyArrow(){
		flyArrow = new Arrow(rotateArrow.getArrowFrame());
		layerManager.insert(flyArrow.arrowLayer,1);
	}
	
	private void checkColisionArrowAndBubbles(){
		for (int i = 0; i< vectorBubbles.size();i++){
			 bubbles = (Bubbles) vectorBubbles.elementAt(i);
			 if (flyArrow.checkColision(bubbles.bubblesLayer)){
				 rotateArrow.setArrowFly(false);
				 removeBubble(bubbles.bubblesLayer, flyArrow.arrowLayer);
				 vectorBubbles.removeElementAt(i);	
			// Visible Bubble Broken
				 createBubbleBroken(bubbles.getPositionX(),bubbles.getPositionY());
			// Create Result Bubbles
				 setResultBubbles(bubbles.getColor());
			// Check Score	 
				 checkScore();
				 break;
			 }
		}
		if (flyArrow.arrowDisapear()){
			rotateArrow.setArrowFly(false);
			layerManager.remove(flyArrow.arrowLayer);
		}
	}
	private void checkScore() {
//		System.out.println(numOfShotBubbles);
		if (numOfShotBubbles == 3){
			getScore = true;			
			numOfShotBubbles = 0;
		}else{
//		// Create New Bubble
			createBubbles();
		}
	}

	private void removeResultBubbles() {
		layerManager.remove(resultBubbles.getResultBubbles());
		resultBubbles.removeResultBubbles();
		layerManager.insert(resultBubbles.getResultBubbles(), 0);

	}

	private void createResultBubbles(){
		resultBubbles = new ResultBubbles();
		layerManager.append(resultBubbles.getResultBubbles());
	}
	
	private void setResultBubbles(int color){
		System.out.println(numOfShotBubbles);
		resultBubbles.setResultBubbles(numOfShotBubbles, color);
		numOfShotBubbles++;
	}

// Visible Bubble Broken
	private void createBubbleBroken(int positionX, int postionY) {
		bubbleBroken = new BubbleBroken(positionX, postionY);
		layerManager.insert(bubbleBroken.getBubbleBrokenLayer(),0);
		bubbleBreak = true;		
	}
	
	private void removeBubbleBroken(){
		layerManager.remove(bubbleBroken.getBubbleBrokenLayer());
		
	}

	private void removeBubble(Sprite bubble, Sprite flyArrow) {
		layerManager.remove(flyArrow);
		layerManager.remove(bubble);		
	}
	
}
	

