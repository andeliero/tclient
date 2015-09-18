package it.tclient.model;

public class InvalidContentException extends Throwable {
    String invalidContentType;
    public InvalidContentException(String error) {invalidContentType=error;}
    public String getError() {
        return invalidContentType;
    }
}
