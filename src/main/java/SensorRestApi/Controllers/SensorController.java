package SensorRestApi.Controllers;


import SensorRestApi.DTO.SensorDTO;
import SensorRestApi.Services.MeasureService;
import SensorRestApi.Services.SensorService;
import SensorRestApi.Util.SensorAlreadyExistException;
import SensorRestApi.Util.CastomResponses.SensorErrorResponse;
import SensorRestApi.Util.SensorValidException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sensors")
public class SensorController {
    private final SensorService sensorService;
    private final MeasureService measureService;
    private Logger logger = LoggerFactory.getLogger(SensorController.class);

    @Autowired
    public SensorController(SensorService sensorService, MeasureService measureService) {
        this.sensorService = sensorService;
        this.measureService = measureService;
    }

    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestBody @Valid SensorDTO sensorDTO){
        logger.info("Sensor registration post-request" + sensorDTO.getName());
        sensorService.registrate(sensorDTO);
        return ResponseEntity.ok().build();
    }
}

