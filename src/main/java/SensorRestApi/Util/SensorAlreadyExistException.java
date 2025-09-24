package SensorRestApi.Util;

public class SensorAlreadyExistException extends RuntimeException{
    public SensorAlreadyExistException(String msg) {
        super(msg);
    }
}
