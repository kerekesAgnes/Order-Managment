package bll;

/**
 * BLLException is an Exception subclass
 * for business logic layer
 *
 * @author Kerekes Agnes
 * @version 1.0
 * @since 2021-06-30
 */

public class BLLException extends Exception {
    public BLLException(String message){
        super(message);
    }
}
