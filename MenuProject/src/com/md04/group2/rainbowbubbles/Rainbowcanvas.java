package com.md04.group2.rainbowbubbles;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.rms.RecordEnumeration;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;

import com.md04.group2.rainbowbubbles.game.Bubbles;
import com.md04.group2.rainbowbubbles.game.BubblesMove;
import com.md04.group2.rainbowbubbles.menu.AboutMenu;
import com.md04.group2.rainbowbubbles.menu.HelpMenu;
import com.md04.group2.rainbowbubbles.menu.Menu;
import com.md04.group2.rainbowbubbles.menu.RainbowMenu;

public class Rainbowcanvas extends GameCanvas 
	implements GameThread.Listener, CommandListener {

	
	
	
	private static final boolean HW_BACK_KEY_EXISTS;
    private static final int MAX_RENDERING_FPS = 10;
    private static final int LEFT_SOFTKEY = -6;
    private static final int RIGHT_SOFTKEY = -7;
    private volatile int pointerKeyState = 0;
    private Main main;
    // menus
    private RainbowMenu menu;
    private HelpMenu helpMenu;
    private AboutMenu aboutMenu;
    
    // current visible menu
    private Menu visibleMenu;
    //private Game game;
    
    // touch handler
    private PointerEventHandler pointerEventHandler;
    // the game loop that is run MAX_RENDERING_FPS timer per second
    private GameThread gameLoop;
    private Graphics graphics;

    private Command backCommand;
    //Khiem create Game
    private BubblesMove bubblesMove;
    //Over
    
    static {
        HW_BACK_KEY_EXISTS = System.getProperty("com.nokia.keyboard.type").equalsIgnoreCase("OnekeyBack");
    }

    /**
     * Initializes the canvas
     *
     * @param main Main midlet
     */
    public Rainbowcanvas(Main main) {
        super(false);
        setFullScreenMode(true);
        this.main = main;

        // create menus
        createMenu();
        createGame();
        createHelpMenu();
        createAboutMenu();

        createPointerEventHandler();
        
        if (HW_BACK_KEY_EXISTS) {
            backCommand = new Command("Back", Command.BACK, 0);
            this.addCommand(backCommand);
            this.setCommandListener(this);
        }

    }

    /**
     * Gets the states of the physical game keys.
     *
     * @return An integer containing the key state information (one bit per
     * key), or 0 if the GameCanvas is not currently shown.
     */
    public int getKeyStates() {
        int keyStates = super.getKeyStates();
        if (keyStates != 0) {
            pointerKeyState = 0;
        }
        else {
            keyStates = pointerKeyState;
            if (pointerKeyState == FIRE_PRESSED) {
                pointerKeyState = 0;
            }
        }
        return keyStates;
    }

    /**
     * Shows menu view.
     */
    public void showMenu() {
        if (visibleMenu == menu) {
            return;
        }
        visibleMenu = menu;
        //menu.setSounds(game.soundsEnabled);
        menu.selectItem(hasPointerEvents() ? -1 : 0);
    }

    /**
     * Hides current menu view.
     */
    public void hideCurrentMenu() {
//        if (visibleMenu == menu && AudioManager.areSoundsEnabled()
//            != game.soundsEnabled) {
//            AudioManager.setSoundsEnabled(game.soundsEnabled);
//        }
        visibleMenu = null;
    }

    /**
     * Saves the current state of the game to RecordStore
     */
//    public void saveGame() {
//        if (game == null) {
//            return;
//        }
//        try {
//            RecordStore gameState = RecordStore.openRecordStore("GameState",
//                true);
//            if (gameState.getNumRecords() == 0) {
//                gameState.addRecord(null, 0, 0);
//            }
//            byte[] data = game.getState();
//            gameState.setRecord(getRecordId(gameState), data, 0, data.length);
//            gameState.closeRecordStore();
//        }
//        catch (Exception e) {
//            try {
//                RecordStore.deleteRecordStore("GameState");
//            }
//            catch (RecordStoreException rse) {
//                // Nothing to do here.
//            }
//        }
//    }

    /**
     * Shows Help view.
     */
    public void showHelpMenu() {
        showMenu();
        helpMenu.selectItem(hasPointerEvents() ? -1 : 0);
        visibleMenu = helpMenu;
    }

    /**
     * Shows About view.
     */
    public void showAboutMenu() {
        showMenu();
        aboutMenu.selectItem(hasPointerEvents() ? -1 : 0);
        visibleMenu = aboutMenu;
    }

    /**
     * Handle key press events.
     * @see javax.microedition.lcdui.Canvas#keyPressed(int)
     * @param key the pressed key
     */
    protected void keyPressed(int key) {
        // delegate key event to proper menu instance
        if (visibleMenu != null) {
            switch (getGameAction(key)) {
                case UP:
                    visibleMenu.selectPrev();
                    break;
                case DOWN:
                    visibleMenu.selectNext();
                    break;
                case FIRE:
                    visibleMenu.clickSelected();
                    break;
            }
        }
        switch (key) {
            case LEFT_SOFTKEY:
                leftSoftkey();
                break;
            case RIGHT_SOFTKEY:
                rightSoftkey();
                break;
        }
    }

    /**
     * delegate pointer events to proper menu
     * @see javax.microedition.lcdui.Canvas#pointerPressed(int, int)
     * @param x coordinate of press
     * @param y coordinate of press
     */
    protected void pointerPressed(int x, int y) {
        if (visibleMenu != null) {
            visibleMenu.pointerEvent(Menu.POINTER_PRESSED, x, y);
        }
        else {
        	bubblesMove.pointerPressed();
//            pointerEventHandler.pointerPressed(x, y);
        }
    }

    /**
     * delegate pointer drag events to proper menu
     * @see javax.microedition.lcdui.Canvas#pointerDragged(int, int)
     * @param x coordinate
     * @param y coordinate
     */
    protected void pointerDragged(int x, int y) {
        if (visibleMenu != null) {
            visibleMenu.pointerEvent(Menu.POINTER_DRAGGED, x, y);
        }
        else {
            pointerEventHandler.pointerDragged(x, y);
        }
    }

    /**
     * delegate pointer release events to proper menu
     * @see javax.microedition.lcdui.Canvas#pointerReleased(int, int)
     * @param x coordinate
     * @param y coordinate
     */
    protected void pointerReleased(int x, int y) {
        if (visibleMenu != null) {
            visibleMenu.pointerEvent(Menu.POINTER_RELEASED, x, y);
        }
        else {
            pointerEventHandler.pointerReleased(x, y);
        }
    }

    /**
     * Called when this canvas is shown.
     * @see javax.microedition.lcdui.Canvas#showNotify()
     */
    protected void showNotify() {
        graphics = getGraphics();
        startGameLoop();
        // show menu view first
        showMenu();
    }

    /**
     * Called when this canvas is hidden.
     * @see javax.microedition.lcdui.Canvas#hideNotify()
     */
    protected void hideNotify() {
        //AudioManager.disableSounds();
        stopGameLoop();
        graphics = null;
    }

    /**
     * Handles command events.
     * @see javax.microedition.lcdui.CommandListener#commandAction(javax.microedition.lcdui.Command, javax.microedition.lcdui.Displayable)
     * @param c Command received
     * @param d Displayable that sent the command
     */
    public void commandAction(Command c, Displayable d) {
        if (c == backCommand) {
            if (visibleMenu == menu) {
                main.close();
            } else {
                hideCurrentMenu();
                showMenu();
            }
        }
    }

    /**
     * Called when the drawable area of the Canvas has been changed.
     * @see javax.microedition.lcdui.Canvas#sizeChanged(int, int)
     * @param w the new width in pixels of the drawable area of the Canvas
     * @param h the new height in pixels of the drawable area of the Canvas
     */
    protected void sizeChanged(int w, int h) {
        if (menu != null) {
            menu.setSize(w, h);
        }
        if (helpMenu != null) {
            helpMenu.setSize(w, h);
        }
        if (aboutMenu != null) {
            aboutMenu.setSize(w, h);
        }
//        if (game != null) {
//            game.setViewportSize(w, h);
//        }
        if (pointerEventHandler != null) {
            pointerEventHandler.setSize(w, h);
        }
    }

    private void leftSoftkey() {
//        if (visibleMenu != menu && game != null) {
//            stopGameLoop();
//            try {
//                game.leftSoftkeyPressed();
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
//
//            startGameLoop();
//        }
    }

    private void rightSoftkey() {
        if (visibleMenu != null) {
            hideCurrentMenu();
        }
        else {
            showMenu();
        }
    }

    private void startGameLoop() {
        stopGameLoop();
        gameLoop = new GameThread(this, MAX_RENDERING_FPS);
        gameLoop.start();
    }

    private void createHelpMenu() {
        helpMenu = new HelpMenu(getWidth(), getHeight(), hasPointerEvents(),
            new Menu.Listener() {

                public void itemClicked(int item) {
                    switch (item) {
                        case HelpMenu.BACK:
                            hideCurrentMenu();
                            showMenu();
                            break;
                    }
                }
            });
    }

    private void createAboutMenu() {
        aboutMenu = new AboutMenu(getWidth(), getHeight(), new Menu.Listener() {

            public void itemClicked(int item) {
                switch (item) {
                    case AboutMenu.BACK:
                        hideCurrentMenu();
                        showMenu();
                        break;
                }
            }
        });
    }

    /**
     * Create game. If there is a saved game in the RecordStore,
     * load the previous game state. Otherwise create a new game.
     */
    private void createGame() {
    	bubblesMove = new BubblesMove(getWidth(),getHeight());
//        game = new Game(getWidth(), getHeight());
//        try {
//            RecordStore gameState = RecordStore.openRecordStore("GameState",
//                true);
//            if (gameState.getNumRecords() == 0
//                || !game.load(gameState.getRecord(getRecordId(gameState)))) {
//                newGame();
//            }
//            gameState.closeRecordStore();
//        }
//        catch (RecordStoreException e) {
//            newGame();
//        }
    }

    private void newGame() {
//        try {
//            game.newGame();
//        }
//        catch (IOException e) {
//            // should not happen as there should be at least one trial level
//            throw new RuntimeException("No levels.");
//        }
    }

    private int getRecordId(RecordStore store)
        throws RecordStoreException {
        RecordEnumeration e = store.enumerateRecords(null, null, false);
        try {
            return e.nextRecordId();
        }
        finally {
            e.destroy();
        }
    }

    private void createPointerEventHandler() {
        pointerEventHandler = new PointerEventHandler(getWidth(), getHeight(),
            new PointerEventHandler.Listener() {

                public void onMoveLeft() {
                    pointerKeyState = LEFT_PRESSED;
                }

                public void onMoveRight() {
                    pointerKeyState = RIGHT_PRESSED;
                }

                public void onMoveUp() {
                    pointerKeyState = UP_PRESSED;
                }

                public void onMoveDown() {
                    pointerKeyState = DOWN_PRESSED;
                }

                public void onFire() {
                    pointerKeyState = FIRE_PRESSED;
                }

                public void onLeftSoftKey() {
                    leftSoftkey();
                }

                public void onRightSoftKey() {
                    rightSoftkey();
                }
            });
    }

    private void stopGameLoop() {
        if (gameLoop != null) {
            gameLoop.cancel();
        }
    }

    private void createMenu() {
        menu = new RainbowMenu(getWidth(), getHeight(), new Menu.Listener() {

            public void itemClicked(int item) {
                switch (item) {
                    case RainbowMenu.RESUME:
                        hideCurrentMenu();
                        break;
                    case RainbowMenu.NEWGAME:
                        newGame();
                        hideCurrentMenu();
                        break;
                    case RainbowMenu.SOUNDS:
                        //game.soundsEnabled = menu.toggleSounds();
                        break;
                    case RainbowMenu.HELP:
                        showHelpMenu();
                        break;
                    case RainbowMenu.ABOUT:
                        showAboutMenu();
                        break;
                    case RainbowMenu.EXIT:
                        main.close();
                        break;
                }
            }
        });
    }

    public void runGameLoop() {
        if (visibleMenu != null) {
            visibleMenu.render(graphics);
        }
        else {
//            game.update(getKeyStates());
            bubblesMove.render(graphics);
        }

        flushGraphics();
    }

}


