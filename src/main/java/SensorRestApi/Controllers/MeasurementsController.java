package SensorRestApi.Controllers;

import SensorRestApi.DTO.MeasurementsDTO;
import SensorRestApi.Services.MeasureService;
import SensorRestApi.Util.CastomResponses.MeasureErrorResponse;
import SensorRestApi.Util.MeasureValidException;
import SensorRestApi.Util.SensorNotExistException;
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
@RequestMapping("/measurements")
public class MeasurementsController {
    private final MeasureService measureService;

    @Autowired
    public MeasurementsController(MeasureService measureService) {
        this.measureService = measureService;
    }

    @GetMapping()
    public List<MeasurementsDTO> getMeasure(){
        return measureService.findAllMeasure();
    }

    @GetMapping("/rainyDaysCount")
    public int countOfRainyDays(){
        return measureService.findAllMeasureWhereRainyDay();
    }

    @PostMapping("/add")
    public ResponseEntity<?> addMeasure(@RequestBody @Valid MeasurementsDTO measurementsDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            StringBuilder sb = new StringBuilder();
            for(FieldError error: bindingResult.getFieldErrors()){
                sb.append(error.getField()).append(":").append(error.getDefaultMessage()).append(";");
            }
            throw new MeasureValidException(sb.toString());
        }
        measureService.addMeasure(measurementsDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    public ResponseEntity<MeasureErrorResponse> handleException(MeasureValidException ex){
        MeasureErrorResponse response = new MeasureErrorResponse(ex.getMessage(),System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    public ResponseEntity<MeasureErrorResponse> hendleException(SensorNotExistException ex) {
        MeasureErrorResponse response = new MeasureErrorResponse(ex.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
