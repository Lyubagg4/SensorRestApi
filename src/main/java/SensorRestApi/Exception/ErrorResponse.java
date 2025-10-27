package SensorRestApi.Exception;

import java.time.LocalDateTime;
import java.util.Map;

public class ErrorResponse {
    private String message;
    private LocalDateTime timestamp;
    private Map<String, String> errors;


    public ErrorResponse(String message, Map<String, String> errors, LocalDateTime timestamp) {
        this.message = message;
        this.timestamp = LocalDateTime.now();
        this.errors = errors;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
