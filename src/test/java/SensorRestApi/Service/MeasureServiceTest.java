package SensorRestApi.Service;

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
import SensorRestApi.Util.SensorNotExistException;
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
import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class MeasureServiceTest {
    @Autowired
    private MockMvc mockMvc;
    @InjectMocks
    private MeasureService measureService;
    @InjectMocks
    private SensorService sensorService;
    @Mock
    private SensorRepository sensorRepository;
    @Mock
    private MeasureRepository measureRepository;
    @Mock
    private SensorMapper sensorMapper;
    @Mock
    private MeasureMapper measureMapper;


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
    public void addMeasureSuccess(){
        Mockito.when(sensorRepository.existsByName("Ivan")).thenReturn(true);
        Mockito.when(sensorRepository.findByName(sensorDTO.getName())).thenReturn(sensor);
        Mockito.when(measureMapper.toEntity(measurementsDTO)).thenReturn(measurements);
        measureService.addMeasure(measurementsDTO);
        Mockito.verify(measureRepository).save(measurements);
    }
    @Test
    public void addMeasureFail(){
        Mockito.when(sensorRepository.existsByName("Ivan")).thenReturn(false);
        Assertions.assertThrows(SensorNotExistException.class, () -> measureService.addMeasure(measurementsDTO));
    }
    @Test
    public void findAllMeasurementNotEmpty(){
        Mockito.when(measureRepository.findAll()).thenReturn(List.of(measurements));
        Mockito.when(measureMapper.toDto(measurements)).thenReturn(measurementsDTO);
        measureService.findAllMeasure();
    }
    @Test
    public void findAllMeasurementmpty(){
        Mockito.when(measureRepository.findAll()).thenReturn(Collections.emptyList());
        measureService.findAllMeasure();
    }
    @Test
    public void findAllMeasureWhereRainyDay(){
        Mockito.when(measureRepository.findAll()).thenReturn(List.of(measurements));
        Mockito.when(measureMapper.toDto(measurements)).thenReturn(measurementsDTO);
        int count = measureService.findAllMeasureWhereRainyDay();
        Assertions.assertEquals(1, count);
    }


}
