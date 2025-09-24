package SensorRestApi.Util.Mappers;

import SensorRestApi.DTO.SensorDTO;
import SensorRestApi.Models.Sensor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SensorMapper {
    Sensor toEntity(SensorDTO sensorDTO);
    SensorDTO toDto(Sensor sensor);
}
