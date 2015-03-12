package com.time.path.svs.testnew.Game_Logic;

/**
 * Created by santiago on 3/12/15.
 */
public class DeadException extends Exception {

    public DeadException() { super("You have died!!"); }
    public DeadException(String message) { super(message); }
    public DeadException(String message, Throwable cause) { super(message, cause); }
    public DeadException(Throwable cause) { super(cause); }
}
