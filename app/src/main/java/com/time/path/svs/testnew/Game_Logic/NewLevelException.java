package com.time.path.svs.testnew.Game_Logic;

/**
 * Created by santiago on 3/12/15.
 */
public class NewLevelException extends Exception {

    public NewLevelException(int level) {
        super("You have reached a new level "+level);
    }

    public NewLevelException(String message) {
        super(message);
    }

    public NewLevelException(String message, Throwable cause) {
        super(message, cause);
    }

    public NewLevelException(Throwable cause) {
        super(cause);
    }


}