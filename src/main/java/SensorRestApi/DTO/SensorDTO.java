package SensorRestApi.DTO;


import jakarta.validation.constraints.NotEmpty;

public class SensorDTO {
    @NotEmpty
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
