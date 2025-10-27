package SensorRestApi.Controllers;

import SensorRestApi.DTO.MeasurementsDTO;
import SensorRestApi.Services.MeasureService;
import SensorRestApi.Util.MeasureValidException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> addMeasure(@RequestBody @Valid MeasurementsDTO measurementsDTO){
        measureService.addMeasure(measurementsDTO);
        return ResponseEntity.ok().build();
    }

}
