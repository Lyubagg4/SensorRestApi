package SensorRestApi.Controllers;


import SensorRestApi.DTO.SensorDTO;
import SensorRestApi.Services.MeasureService;
import SensorRestApi.Services.SensorService;
import SensorRestApi.Util.SensorAlreadyExistException;
import SensorRestApi.Util.CastomResponses.SensorErrorResponse;
import SensorRestApi.Util.SensorValidException;
import jakarta.validation.Valid;
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

    @Autowired
    public SensorController(SensorService sensorService, MeasureService measureService) {
        this.sensorService = sensorService;
        this.measureService = measureService;
    }

    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestBody @Valid SensorDTO sensor, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            StringBuilder sb = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for(FieldError error: errors){
                sb.append(error.getField()).append(": ").append(error.getDefaultMessage()).append("; ");
            }
            throw new SensorValidException(sb.toString());

        }
        sensorService.registrate(sensor);
        return ResponseEntity.ok(HttpStatus.OK);
    }



    @ExceptionHandler()
    public ResponseEntity<SensorErrorResponse> handleExceprion(SensorAlreadyExistException ex) {
        SensorErrorResponse response = new SensorErrorResponse(
                ex.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler()
    public ResponseEntity<SensorErrorResponse> handleExceprion(SensorValidException ex) {
        SensorErrorResponse response = new SensorErrorResponse(
                ex.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}

