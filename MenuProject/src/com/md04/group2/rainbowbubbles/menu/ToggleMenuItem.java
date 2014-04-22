package com.md04.group2.rainbowbubbles.menu;

import javax.microedition.lcdui.game.Sprite;

public class ToggleMenuItem 
	 extends MenuItems {

		    private volatile boolean on = false;

		    public ToggleMenuItem(Sprite sprite) {
		        super(sprite);
		    }

		    public void setOn(boolean on) {
		        this.on = on;
		        sprite.setFrame(getFrame());
		    }

		    protected int getFrame() {
		        return super.getFrame() + (on ? 0 : 2);
		    }

		    public boolean isOn() {
		        return on;
		    }
		}

