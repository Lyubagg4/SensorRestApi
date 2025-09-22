package SensorRestApi.Controllers;


import SensorRestApi.DTO.SensorDTO;
import SensorRestApi.Services.MeasureService;
import SensorRestApi.Services.SensorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static org.springframework.http.ResponseEntity.badRequest;

@Controller
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
                sb.append(errors+" "+error.getDefaultMessage()).append(";");
            }
            return ResponseEntity.badRequest().body(sb.toString());// TODO

        }
        sensorService.registrate(sensor);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
