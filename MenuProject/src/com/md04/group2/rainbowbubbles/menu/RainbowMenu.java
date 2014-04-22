package com.md04.group2.rainbowbubbles.menu;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class RainbowMenu extends Menu{
	public static final int RESUME = 0;
    public static final int NEWGAME = 1;
    public static final int SOUNDS = 2;
    public static final int HELP = 3;
    public static final int ABOUT = 4;
    public static final int EXIT = 5;
    private final Image background;
    private final ToggleMenuItem sounds;
    private int width;
    private int height;

    public  RainbowMenu(int w, int h, Listener l) {
        super(6, w, h, l);
        background = loadImage("bg_menu.jpg");
        setItem(RESUME, new MenuItems(loadSprite("resume.png", 2)));
        setItem(NEWGAME, new MenuItems(loadSprite("new_game.png", 2)));
        sounds = new ToggleMenuItem(loadSprite("sounds.png", 4));
        setItem(SOUNDS, sounds);
        setItem(HELP, new MenuItems(loadSprite("help.png", 2)));
        setItem(ABOUT, new MenuItems(loadSprite("about.png", 2)));
        setItem(EXIT, new MenuItems(loadSprite("exit.png", 2)));
        setSize(w, h);
    }

    /**
     * Set menu size to fit the screen.
     * @param w width of the menu
     * @param h height of the menu
     */
    public final void setSize(int w, int h) {
        width = w;
        height = h;
        final int bgW = background.getWidth();
        final int bgH = background.getHeight();
        final int leftOffset = (w - bgW) / 2;
        final int topOffset = (h - bgH) / 2;
        final int menuW = Math.min(w, bgW);
        final int menuH = Math.min(h, bgH);
        int x = leftOffset + menuW / 2;
        int y = topOffset + menuH / 4;
        for (int i = 0; i < getSize(); i++) {
            MenuItems item = getItem(i);
            item.setCenter(x, y);
            y += item.getHeight();
        }
    }

    protected void paint(Graphics g) {
        g.setColor(0x00000000);
        g.fillRect(0, 0, width, height);
        g.drawImage(background, width / 2, height / 2,
            Graphics.HCENTER | Graphics.VCENTER);
        super.paint(g);
    }

    public void setSounds(boolean on) {
        sounds.setOn(on);
    }

    public boolean toggleSounds() {
        sounds.setOn(!sounds.isOn());
        return sounds.isOn();
    }
}

