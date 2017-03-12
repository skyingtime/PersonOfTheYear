import com.cache.NomineeStore;
import com.controller.Controller;
import com.util.FileParser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * Created by yangliu on 19/12/2016.
 */

public class ControllerTest {
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        NomineeStore.createNomineeMap(FileParser.parseFile(this.getClass().getClassLoader().getResource("people.txt").getPath()));
        mockMvc = standaloneSetup(new Controller()).build();
    }

    @Test
    public void getAllNomineesTest() throws Exception {
        String jsonResponse = "[{\"dateOfBirth\":\"1955-05-19\",\"firstName\":\"James\",\"middleName\":\"Arthur\",\"lastName\":\"Gosling\"}," +
                "{\"dateOfBirth\":\"1984-05-12\",\"firstName\":\"Sarah\",\"middleName\":\"\",\"lastName\":\"Connor\"}," +
                "{\"dateOfBirth\":\"1942-01-17\",\"firstName\":\"Cassius\",\"middleName\":\"Marcellus\",\"lastName\":\"Clay\"}," +
                "{\"dateOfBirth\":\"1976-02-29\",\"firstName\":\"Mohammed\",\"middleName\":\"\",\"lastName\":\"Ali\"}," +
                "{\"dateOfBirth\":\"1941-09-09\",\"firstName\":\"Dennis\",\"middleName\":\"\",\"lastName\":\"Ritchie\"}]";
        mockMvc.perform(get("/api/nominees"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string(jsonResponse));
    }

    @Test
    public void getSpecificNomineeTest() throws Exception {
        String jsonResponse = "{\"dateOfBirth\":\"1955-05-19\",\"firstName\":\"James\",\"middleName\":\"Arthur\",\"lastName\":\"Gosling\"}";
        mockMvc.perform(get("/api/nominee/{key}", "1955-05-19|GoslingTest"))
                .andExpect(status().isNotFound());
        mockMvc.perform(get("/api/nominee/{key}", "1955-05-19|Gosling"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string(jsonResponse));
    }

    @Test
    public void addNomineeTest() throws Exception {
        String requestBody = "{\"dateOfBirth\":\"1941-09-09\",\"firstName\":\"Dennis\",\"middleName\":\"\",\"lastName\":\"Ritchie\"}";
        mockMvc.perform(post("/api/nominee").contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isConflict());
        requestBody = "{\"dateOfBirth\":\"1987-02-25\",\"firstName\":\"Yang\",\"middleName\":\"\",\"lastName\":\"Liu\"}";
        String key = "1987-02-25|Liu";
        mockMvc.perform(post("/api/nominee").contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isCreated());
        Assert.assertEquals("1987-02-25", NomineeStore.getNominee(key).getDateOfBirth());
        Assert.assertEquals("Liu", NomineeStore.getNominee(key).getLastName());
    }

    @Test
    public void modifyNomineeTest() throws Exception {
        String requestBody = "{\"dateOfBirth\":\"1987-02-25\",\"firstName\":\"James\",\"middleName\":\"Test\",\"lastName\":\"Gosling\"}";
        mockMvc.perform(put("/api/nominee/{key}", "1987-02-25|Gosling").contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isNotFound());
        requestBody = "{\"dateOfBirth\":\"1984-05-12\",\"firstName\":\"Sarah\",\"middleName\":\"Test\",\"lastName\":\"Connor\"}";
        String key = "1984-05-12|Connor";
        mockMvc.perform(put("/api/nominee/{key}", key).contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isOk());
        Assert.assertEquals("Test", NomineeStore.getNominee(key).getMiddleName());
    }

    @Test
    public void deleteNomineeTest() throws Exception {
        int size = NomineeStore.getAllNominees().size();
        mockMvc.perform(delete("/api/nominee/{key}", "1987-02-25|Gosling"))
                .andExpect(status().isNotFound());
        mockMvc.perform(delete("/api/nominee/{key}", "1955-05-19|Gosling"))
                .andExpect(status().isNoContent());
        Assert.assertEquals(size-1, NomineeStore.getAllNominees().size());
    }
}
