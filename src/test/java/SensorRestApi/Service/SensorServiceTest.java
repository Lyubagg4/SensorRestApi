package SensorRestApi.Service;

import SensorRestApi.Controllers.MeasurementsController;
import SensorRestApi.DTO.MeasurementsDTO;
import SensorRestApi.DTO.SensorDTO;
import SensorRestApi.Models.Measurements;
import SensorRestApi.Models.Sensor;
import SensorRestApi.Repositories.MeasureRepository;
import SensorRestApi.Repositories.SensorRepository;
import SensorRestApi.Services.MeasureService;
import SensorRestApi.Services.SensorService;
import SensorRestApi.Util.Mappers.MeasureMapper;
import SensorRestApi.Util.Mappers.SensorMapper;
import SensorRestApi.Util.SensorAlreadyExistException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class SensorServiceTest {
    @Autowired
    private MockMvc mockMvc;
    @Mock
    private MeasureRepository measureRepository;
    @Mock
    private SensorRepository sensorRepository;
    @Mock
    private MeasureMapper measureMapper;
    @Mock
    private SensorMapper sensorMapper;
    @InjectMocks
    private MeasureService measureService;
    @InjectMocks
    private SensorService sensorService;

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
    public void registrationSuccess(){
        Mockito.when(sensorRepository.existsByName(sensorDTO.getName())).thenReturn(false);
        Mockito.when(sensorMapper.toEntity(sensorDTO)).thenReturn(sensor);
        sensorService.registrate(sensorDTO);
        Mockito.verify(sensorRepository).save(sensor);
    }
    @Test
    public void registrationFail(){
        Mockito.when(sensorRepository.existsByName(sensorDTO.getName())).thenReturn(true);
        Assertions.assertThrows(SensorAlreadyExistException.class, () -> sensorService.registrate(sensorDTO));
    }



}
