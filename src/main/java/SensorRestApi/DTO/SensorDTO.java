package SensorRestApi.DTO;


import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.UniqueElements;

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
