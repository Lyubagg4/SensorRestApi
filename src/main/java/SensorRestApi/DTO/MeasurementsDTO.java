package SensorRestApi.DTO;

import SensorRestApi.Models.Sensor;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;


public class MeasurementsDTO {
    @Size(min = -100, max = 100)
    private double value;

    private boolean raining;

    @NotEmpty
    private Sensor sensor;

    public double getValue() {
        return value;
    }

    public boolean isRaining() {
        return raining;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setRaining(boolean raining) {
        this.raining = raining;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }
}
