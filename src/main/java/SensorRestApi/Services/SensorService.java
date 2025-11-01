package SensorRestApi.Services;

import SensorRestApi.DTO.SensorDTO;
import SensorRestApi.Models.Sensor;
import SensorRestApi.Repositories.SensorRepository;
import SensorRestApi.Util.SensorAlreadyExistException;
import SensorRestApi.Util.Mappers.SensorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SensorService {
    private final SensorMapper sensorMapper;
    private final SensorRepository sensorRepository;
    private Logger logger = LoggerFactory.getLogger(SensorService.class);

    @Autowired
    public SensorService(SensorMapper sensorMapper, SensorRepository sensorRepository) {
        this.sensorMapper = sensorMapper;
        this.sensorRepository = sensorRepository;
    }

    public void registrate(SensorDTO sensorDTO){
        logger.info("Registration of the sensor "+sensorDTO.getName());
        if(sensorRepository.existsByName(sensorDTO.getName())){
            logger.error("Sensor with this name "+sensorDTO.getName() + "already exist");
            throw new SensorAlreadyExistException("сенсор с таким именем уже существует");
        }
        sensorRepository.save(convertToEntity(sensorDTO));
    }
    public Sensor convertToEntity(SensorDTO sensorDTO){
        return sensorMapper.toEntity(sensorDTO);
    }
}
