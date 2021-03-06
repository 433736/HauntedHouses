package cz.muni.fi.pa165.hauntedhouses;

import cz.muni.fi.pa165.hauntedhouses.dto.HouseCreateDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.HouseDTO;
import cz.muni.fi.pa165.hauntedhouses.facade.HouseFacade;
import cz.muni.fi.pa165.hauntedhouses.rest.controllers.ExceptionController;
import cz.muni.fi.pa165.hauntedhouses.rest.controllers.HouseController;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Petr Vitovsky
 */
@WebAppConfiguration
@ContextConfiguration(classes = {RootWebContext.class})
public class HouseControllerTest extends AbstractTestNGSpringContextTests {

    @Mock
    private HouseFacade houseFacade;

    @Autowired
    @InjectMocks
    private HouseController houseController;

    private MockMvc mockMvc;

    private HouseDTO houseOne;
    private HouseDTO houseTwo;
    private List<HouseDTO> houses;

    @BeforeClass
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(houseController).setMessageConverters(new MappingJackson2HttpMessageConverter())
                .setControllerAdvice(new ExceptionController()).build();
    }

    @BeforeMethod
    public void init() {
        houseOne = new HouseDTO();
        houseOne.setId(1L);
        houseOne.setAddress("addressOne");
        houseOne.setClue("clueOne");
        houseOne.setName("nameOne");

        houseTwo = new HouseDTO();
        houseTwo.setId(2L);
        houseTwo.setAddress("addressTwo");
        houseTwo.setClue("clueTwo");
        houseTwo.setName("nameTwo");

        houses = new ArrayList<>();
        houses.add(houseOne);
        houses.add(houseTwo);
    }

    @Test
    public void getAllHouses() throws Exception {

        doReturn(Collections.unmodifiableList(houses)).when(
                houseFacade).getAllHouses();

        mockMvc.perform(get("/api/v1/house"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.[?(@.id==1)].name").value("nameOne"))
                .andExpect(jsonPath("$.[?(@.id==1)].address").value("addressOne"))
                .andExpect(jsonPath("$.[?(@.id==1)].clue").value("clueOne"))
                .andExpect(jsonPath("$.[?(@.id==2)].name").value("nameTwo"))
                .andExpect(jsonPath("$.[?(@.id==2)].address").value("addressTwo"))
                .andExpect(jsonPath("$.[?(@.id==2)].clue").value("clueTwo"));
    }

    @Test
    public void getHouse() throws Exception {
        doReturn(houseOne).when(houseFacade).getHouseById(1L);
        doReturn(houseTwo).when(houseFacade).getHouseById(2L);

        mockMvc.perform(get("/api/v1/house/1"))
                .andExpect(status().isOk())
                .andExpect(
                        content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.name").value("nameOne"))
                .andExpect(jsonPath("$.address").value("addressOne"))
                .andExpect(jsonPath("$.clue").value("clueOne"));

        mockMvc.perform(get("/api/v1/house/2"))
                .andExpect(status().isOk())
                .andExpect(
                        content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.name").value("nameTwo"))
                .andExpect(jsonPath("$.address").value("addressTwo"))
                .andExpect(jsonPath("$.clue").value("clueTwo"));
    }

    @Test
    public void getNonexistingHouse() throws Exception {
        doReturn(null).when(houseFacade).getHouseById(1L);

        mockMvc.perform(get("/api/v1/house/1")).andExpect(
                status().is4xxClientError());
    }

    @Test
    public void createHouse() throws Exception {
        HouseCreateDTO houseCreateDTO = new HouseCreateDTO();
        houseCreateDTO.setAddress("addressOne");
        houseCreateDTO.setClue("clueOne");
        houseCreateDTO.setName("nameOne");

        doReturn(1L).when(houseFacade).createHouse(
                any(HouseCreateDTO.class));

        String json = this.convertObjectToJsonBytes(houseCreateDTO);

        mockMvc.perform(
                post("/api/v1/house").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());

        verify(houseFacade).createHouse(houseCreateDTO);
    }

    @Test
    public void createInvalidHouse() throws Exception {
        HouseCreateDTO houseCreateDTO = new HouseCreateDTO();
        houseCreateDTO.setName("name");
        houseCreateDTO.setClue("clue");

        doReturn(1L).when(houseFacade).createHouse(
                any(HouseCreateDTO.class));

        String json = this.convertObjectToJsonBytes(houseCreateDTO);

        mockMvc.perform(post("/api/v1/house").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void updateHouse() throws Exception {
        HouseDTO houseDTO = new HouseDTO();
        houseDTO.setId(1L);
        houseDTO.setAddress("addressOne");
        houseDTO.setClue("clueOne");
        houseDTO.setName("nameOne");

        String json = this.convertObjectToJsonBytes(houseDTO);

        mockMvc.perform(
                put("/api/v1/house").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());

        verify(houseFacade).updateHouse(houseDTO);
    }

    @Test
    public void updateInvalidHouse() throws Exception {
        HouseDTO houseDTO = new HouseDTO();
        houseDTO.setId(1L);
        houseDTO.setAddress(null);
        houseDTO.setClue("clueOne");
        houseDTO.setName("nameOne");

        String json = this.convertObjectToJsonBytes(houseDTO);

        doThrow(new RuntimeException("invalid parameter")).when(houseFacade).updateHouse(houseDTO);

        mockMvc.perform(put("/api/v1/house").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void deleteHouse() throws Exception {
        mockMvc.perform(delete("/api/v1/house/1"))
                .andExpect(status().isOk());

        verify(houseFacade).deleteHouse(1L);
    }

    @Test
    public void deleteNonexistingHouse() throws Exception {
        doThrow(new IllegalArgumentException("the house does not exist")).when(houseFacade).deleteHouse(1L);

        mockMvc.perform(delete("/api/v1/house/1"))
                .andExpect(status().is4xxClientError());
    }

    private static String convertObjectToJsonBytes(Object object)
            throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsString(object);
    }
}
