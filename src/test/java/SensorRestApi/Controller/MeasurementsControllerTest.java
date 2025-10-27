package SensorRestApi.Controller;

import SensorRestApi.Controllers.MeasurementsController;
import SensorRestApi.DTO.MeasurementsDTO;
import SensorRestApi.DTO.SensorDTO;
import SensorRestApi.Models.Measurements;
import SensorRestApi.Models.Sensor;
import SensorRestApi.Services.MeasureService;
import SensorRestApi.Util.MeasureValidException;
import jakarta.validation.Valid;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MeasurementsController.class)
@AutoConfigureMockMvc
public class MeasurementsControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private MeasureService measureService;

    private MeasurementsDTO measurementsDTO;
    private SensorDTO sensorDTO;
    private Sensor sensor;
    private Measurements measurements;

    @BeforeEach
    public void setUp(){
        sensorDTO = new SensorDTO("Ivan");
        sensor = new Sensor("Ivan");
        measurementsDTO = new MeasurementsDTO(12.3, true, sensorDTO);
        measurements = new Measurements();
        measurements.setId(1);
        measurements.setValue(12);
        measurements.setRaining(true);
        measurements.setSensor(sensor);
        measurements.setTimeOfMeasurement(LocalDateTime.now());
    }
    @Test
    public void getAddMeasurement() throws Exception{
        Mockito.when(measureService.findAllMeasure()).thenReturn(List.of(measurementsDTO));
        mockMvc.perform(get("/measurements"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$",hasSize(1)))
                .andExpect(jsonPath("$[0].value").value(12.3))
                .andExpect(jsonPath("$[0].raining").value(true))
                .andExpect(jsonPath("$[0].sensor.name").value("Ivan"));
    }
    @Test
    public void countOfRainyDays() throws Exception{
        Mockito.when(measureService.findAllMeasureWhereRainyDay()).thenReturn(1);
        mockMvc.perform(get("/measurements/rainyDaysCount"))
                .andExpect(status().isOk());

    }
    @Test
    public void addMeasurementSuccess() throws Exception{
        String measurementJson = """
        {
            "value": 25.5,
            "raining": true,
            "sensor": {
                "name": "TestSensor"
            }
        }
        """;
        mockMvc.perform(post("/measurements/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(measurementJson))
                .andExpect(status().isOk());
    }
    @Test
    public void addMeasurementFail() throws Exception {
        String measurementJson = """
    {
        "value": 50.0,
        "raining": true
    }
    """;

        mockMvc.perform(post("/measurements/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(measurementJson))
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    Assertions.assertNotNull(result.getResolvedException());
                    Assertions.assertTrue(result.getResolvedException() instanceof MeasureValidException);
                });
    }



}
