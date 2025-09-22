package SensorRestApi.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Entity
@Table(name = "measurements")
public class Measurements {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Size(min = -100, max = 100)
    @Column(name = "value")
    private double value;

    @Column(name = "raining")
    private boolean raining;

    @Column(name = "timeOfMeasurement")
    private LocalDateTime timeOfMeasurement;

    @NotEmpty
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sensor", referencedColumnName = "id")
    private Sensor sensor;

    public int getId() {
        return id;
    }

    public double getValue() {
        return value;
    }

    public boolean isRaining() {
        return raining;
    }

    public LocalDateTime getTimeOfMeasurement() {
        return timeOfMeasurement;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setRaining(boolean raining) {
        this.raining = raining;
    }

    public void setTimeOfMeasurement(LocalDateTime timeOfMeasurement) {
        this.timeOfMeasurement = timeOfMeasurement;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }
}
