package com.md04.group2.rainbowbubbles.menu;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class AboutMenu extends Menu {

    public static final int BACK = 0;
    private int width;
    private int height;
    private final Font fontBold = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_BOLD,
        Font.SIZE_SMALL);
    private final Font font = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN,
        Font.SIZE_SMALL);
    
    private  Image bgAbout;

    public AboutMenu(int w, int h, Listener l) {
        super(1, w, h, l);
        bgAbout = loadImage("1111.jpg");
        setItem(BACK, new MenuItems(loadSprite("back.png", 2)));
        setSize(w, h);
    }

    public final void setSize(int w, int h) {
        width = w;
        height = h;

        int x = width / 2;
        int y = 11 * height / 13;
        for (int i = 0; i < getSize(); i++) {
            MenuItems item = getItem(i);
            item.setCenter(x, y);
            y += item.getHeight();
        }
    }

    protected void paint(Graphics g) {
        g.setColor(0x00000000);
        g.fillRect(0, 0, width, height);
        g.setColor(0x00ffffff);
        int x = width / 2;
        int y = height / 9;
        int a = Graphics.BASELINE | Graphics.HCENTER;
        
        g.drawImage(bgAbout, width / 2, height / 2,
                Graphics.HCENTER | Graphics.VCENTER);
        g.setFont(fontBold);

        super.paint(g);
    }
}

