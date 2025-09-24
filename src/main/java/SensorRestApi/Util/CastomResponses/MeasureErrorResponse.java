package SensorRestApi.Util.CastomResponses;

public class MeasureErrorResponse {
    private String message;
    private long timaspamp;

    public MeasureErrorResponse(String message, long timaspamp) {
        this.message = message;
        this.timaspamp = timaspamp;
    }

    public MeasureErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public long getTimaspamp() {
        return timaspamp;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTimaspamp(long timaspamp) {
        this.timaspamp = timaspamp;
    }
}
