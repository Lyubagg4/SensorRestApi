package SensorRestApi.DTO;

import SensorRestApi.Models.Sensor;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;


public class MeasurementsDTO {
    @DecimalMin(value = "-100.0", inclusive = true)
    @DecimalMax(value = "100.0", inclusive = true)
    private double value;

    private boolean raining;

    @NotNull
    private SensorDTO sensor;
    public MeasurementsDTO() {
    }

    public MeasurementsDTO(double value, boolean raining, SensorDTO sensor) {
        this.value = value;
        this.raining = raining;
        this.sensor = sensor;
    }



    public double getValue() {
        return value;
    }

    public boolean isRaining() {
        return raining;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setRaining(boolean raining) {
        this.raining = raining;
    }

    public SensorDTO getSensor() {
        return sensor;
    }

    public void setSensor(SensorDTO sensor) {
        this.sensor = sensor;
    }
}
