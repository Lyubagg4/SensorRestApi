package SensorRestApi.Services;

import SensorRestApi.DTO.SensorDTO;
import SensorRestApi.Models.Sensor;
import SensorRestApi.Repositories.SensorRepository;
import SensorRestApi.Util.SensorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SensorService {
    private final SensorMapper sensorMapper;
    private final SensorRepository sensorRepository;

    @Autowired
    public SensorService(SensorMapper sensorMapper, SensorRepository sensorRepository) {
        this.sensorMapper = sensorMapper;
        this.sensorRepository = sensorRepository;
    }

    public void registrate(SensorDTO sensorDTO){
        sensorRepository.save(convertToEntity(sensorDTO));
    }
    public Sensor convertToEntity(SensorDTO sensorDTO){
        return sensorMapper.toEntity(sensorDTO);
    }
}
