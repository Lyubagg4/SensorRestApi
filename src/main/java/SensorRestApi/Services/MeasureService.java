package SensorRestApi.Services;

import SensorRestApi.DTO.MeasurementsDTO;
import SensorRestApi.DTO.SensorDTO;
import SensorRestApi.Models.Measurements;
import SensorRestApi.Models.Sensor;
import SensorRestApi.Repositories.MeasureRepository;
import SensorRestApi.Repositories.SensorRepository;
import SensorRestApi.Util.Mappers.MeasureMapper;
import SensorRestApi.Util.SensorNotExistException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MeasureService {
    private final MeasureMapper measureMapper;
    private final SensorRepository sensorRepository;
    private final MeasureRepository measureRepository;
    private Logger logger = LoggerFactory.getLogger(MeasureService.class);

    @Autowired
    public MeasureService(MeasureMapper measureMapper, SensorRepository sensorRepository, MeasureRepository measureRepository) {
        this.measureMapper = measureMapper;
        this.sensorRepository = sensorRepository;
        this.measureRepository = measureRepository;
    }

    public void addMeasure(MeasurementsDTO measurementsDTO){
        logger.info("Adding measurement for sensor "+measurementsDTO.getSensor().getName());
        if(!sensorRepository.existsByName(measurementsDTO.getSensor().getName())){
            logger.error("Sensor with this name "+measurementsDTO.getSensor().getName() + "already exist");
            throw new SensorNotExistException("Сенсор с таким именем не зарегистрирован!");
        }
        Sensor toEntity = sensorRepository.findByName(measurementsDTO.getSensor().getName());
        measureRepository.save(convertToEntity(measurementsDTO, toEntity));
    }
    public List<MeasurementsDTO> findAllMeasure(){
        logger.info("Finding all measurements");
        return measureRepository.findAll().stream().map(this::convertToDto).toList();
    }
    public Measurements convertToEntity(MeasurementsDTO measurementsDTO, Sensor sensor){
        Measurements measurements = measureMapper.toEntity(measurementsDTO);
        measurements.setSensor(sensor);
        measurements.setTimeOfMeasurement(LocalDateTime.now());
        return measurements;
    }
    public int findAllMeasureWhereRainyDay(){
        logger.info("Finding all measurements with rainy days");
        int count = 0;
        for(MeasurementsDTO mdto: measureRepository.findAll().stream().map(this::convertToDto).toList()){
            if(mdto.isRaining()){
                count++;
            }
        }
        return count;
    }
    public MeasurementsDTO convertToDto(Measurements measurements){
        MeasurementsDTO measurementsDTO = measureMapper.toDto(measurements);
        SensorDTO sensor= new SensorDTO();
        sensor.setName(measurements.getSensor().getName());
        measurementsDTO.setSensor(sensor);
        return measurementsDTO;
    }

}
