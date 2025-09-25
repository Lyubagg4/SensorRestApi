package SensorRestApi.Services;

import SensorRestApi.DTO.MeasurementsDTO;
import SensorRestApi.DTO.SensorDTO;
import SensorRestApi.Models.Measurements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Random;

@Service
public class MeasureClientService {
    private final RestTemplate restTemplate;
    private Random random = new Random();

    @Autowired
    public MeasureClientService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public void send100Measurements(){
        String urlForNewMeasurement = "http://localhost:8080/measurements/add";
        int i = 0;
        while(i<1000){
            MeasurementsDTO measurementsDTO = createRandomMeasurement();
            ResponseEntity<MeasurementsDTO> response = restTemplate.postForEntity(urlForNewMeasurement, measurementsDTO, MeasurementsDTO.class);
            if(response.getStatusCode()==HttpStatus.OK){
                System.out.println("произошло добавление"+i+"записи");
                i++;
            }
        }
    }
    public MeasurementsDTO createRandomMeasurement(){
        MeasurementsDTO measurementsDTO = new MeasurementsDTO();
        measurementsDTO.setValue(random.nextDouble());
        measurementsDTO.setRaining(random.nextBoolean());

        String urlForNewSensor = "http://localhost:8080/sensors/registration";
        SensorDTO sensorDTO = new SensorDTO();
        sensorDTO.setName("test"+random.nextInt());
        ResponseEntity<SensorDTO> response = restTemplate.postForEntity(urlForNewSensor, sensorDTO, SensorDTO.class);
        while (response.getStatusCode()==HttpStatus.BAD_REQUEST){
            sensorDTO.setName("test"+random.nextInt());
            response = restTemplate.postForEntity(urlForNewSensor, sensorDTO, SensorDTO.class);
        }
        System.out.println("добавили новый сенсор");

        measurementsDTO.setSensor(sensorDTO);
        return measurementsDTO;

    }
}
