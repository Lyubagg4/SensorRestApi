package SensorRestApi.Util.Mappers;


import SensorRestApi.DTO.MeasurementsDTO;
import SensorRestApi.Models.Measurements;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MeasureMapper {
    Measurements toEntity(MeasurementsDTO measurementsDTO);
    MeasurementsDTO toDto(Measurements measurements);
}