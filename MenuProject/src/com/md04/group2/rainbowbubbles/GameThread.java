package com.md04.group2.rainbowbubbles;

public class GameThread extends Thread {

    private boolean run = true;
    private final Listener listener;
    private final int fps;

    /**
     * Constructor
     * @param listener
     * @param fps listener.runGameLoop is called according to fps
     */
    public GameThread(Listener listener, int fps) {
        if (listener == null) {
            throw new IllegalArgumentException("listener cannot be null");
        }
        if (fps <= 0) {
            throw new IllegalArgumentException("fps must be bigger than 0");
        }
        this.listener = listener;
        this.fps = fps;
    }

    /**
     * @see Thread#run() 
     */
    public void run() {
//        long lastTime = 0;
        while (run) {
//            long now = System.currentTimeMillis();
//            long delay = (1000 / fps) + lastTime - now;
//            if (delay < 1) {
//                delay = 1;
//            }
//            // force-sleep at least 1 ms to ensure time for the UI thread
            try {
                sleep(fps);
            }
            catch (InterruptedException e) {
            }
            if (run) {
                runGameLoop();
            }
//            lastTime = now;
        }
    }

    private synchronized void runGameLoop() {
        listener.runGameLoop();
    }

    /**
     * Stop the game thread
     */
    public synchronized void cancel() {
        run = false;
    }

    /**
     * Listener for a game thread.
     */
    public interface Listener {

        /**
         * The game thread calls this method periodically 
         * until the thread is cancelled.
         */
        void runGameLoop();
    }
}



