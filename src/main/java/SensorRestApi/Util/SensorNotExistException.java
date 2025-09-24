package SensorRestApi.Util;

public class SensorNotExistException extends RuntimeException{
    public SensorNotExistException(String string) {
        super(string);
    }
}
