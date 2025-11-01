package SensorRestApi.Controllers;

import SensorRestApi.DTO.MeasurementsDTO;
import SensorRestApi.Services.MeasureService;
import SensorRestApi.Util.MeasureValidException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("/measurements")
public class MeasurementsController {
    private final MeasureService measureService;
    private Logger logger = LoggerFactory.getLogger(MeasurementsController.class);

    @Autowired
    public MeasurementsController(MeasureService measureService) {
        this.measureService = measureService;
    }

    @GetMapping()
    public List<MeasurementsDTO> getMeasure(){
        logger.info("Getting measurement get-request");
        return measureService.findAllMeasure();
    }

    @GetMapping("/rainyDaysCount")
    public int countOfRainyDays(){
        logger.info("Counting rainy days get-request");
        return measureService.findAllMeasureWhereRainyDay();
    }

    @PostMapping("/add")
    public ResponseEntity<?> addMeasure(@RequestBody @Valid MeasurementsDTO measurementsDTO){
        logger.info("Adding measurement post-request for sensor"+measurementsDTO.getSensor().getName());
        measureService.addMeasure(measurementsDTO);
        return ResponseEntity.ok().build();
    }
}