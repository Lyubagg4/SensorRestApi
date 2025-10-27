package SensorRestApi.Controller;

import SensorRestApi.Controllers.MeasurementsController;
import SensorRestApi.Controllers.SensorController;
import SensorRestApi.Services.MeasureService;
import SensorRestApi.Services.SensorService;
import SensorRestApi.Util.MeasureValidException;
import SensorRestApi.Util.SensorAlreadyExistException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(SensorController.class)
@AutoConfigureMockMvc
public class SensorControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private SensorService sensorService;
    @MockitoBean
    private MeasureService measureService;

    @Test
    public void registrationSuccess() throws Exception {
        String sensorJson = """
        {
            "name": "Ivan"
        }
        """;

        mockMvc.perform(post("/sensors/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(sensorJson))
                .andExpect(status().isOk());
    }
    @Test
    public void registrationFail() throws Exception {

        doThrow(new SensorAlreadyExistException("сенсор с таким именем уже существует"))
                .when(sensorService).registrate(any());
        String sensorJson = """
        {
            "name": "ivan"
        }
        """;

        mockMvc.perform(post("/sensors/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(sensorJson))
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    Assertions.assertNotNull(result.getResolvedException());
                    Assertions.assertTrue(result.getResolvedException() instanceof SensorAlreadyExistException);
                });
    }

}
