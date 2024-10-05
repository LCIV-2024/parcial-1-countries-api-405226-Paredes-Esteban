package ar.edu.utn.frc.tup.lciii.controllers;

import ar.edu.utn.frc.tup.lciii.dtos.common.CountryDTO;
import ar.edu.utn.frc.tup.lciii.model.Country;
import ar.edu.utn.frc.tup.lciii.service.CountryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.boot.test.context.assertj.AssertableWebApplicationContext.get;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CountryControllerTest {
    @MockBean
    private CountryService countryService;

    private CountryController countryController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        countryController = new CountryController(countryService);
    }

    @Test
    void getAllCountries() {
        CountryDTO country = new CountryDTO();
        country.setName("TestDTO");
        Country testCountry = new Country();
        testCountry.setName("TestCountry");
        when(countryService.getCountries()).thenReturn(List.of(country));
        when(countryService.getAllCountries()).thenReturn(List.of(testCountry));

        ResponseEntity<List<CountryDTO>> response = countryController.getAllCountries(null,null);

        verify(countryService, times(1)).getCountries();
        assertEquals(List.of(country), response.getBody());
        assertEquals(response.getBody().get(0).getName(), "TestDTO");
    }

    @Test
    void getAllCountriesByName(){
        CountryDTO country = new CountryDTO();
        country.setName("Test");
        when(countryService.getCountriesByName("Test")).thenReturn(List.of(country));
        ResponseEntity<List<CountryDTO>> response = countryController.getAllCountries("Test",null);
        assertEquals(List.of(country), response.getBody());
        verify(countryService, times(1)).getCountriesByName("Test");
        assertEquals(response.getBody().get(0).getName(), "Test");
    }

    @Test
    void getAllCountriesByCode(){
        CountryDTO country = new CountryDTO();
        country.setCode("TST");
        when(countryService.getCountriesByCode(anyString())).thenReturn(List.of(country));
        ResponseEntity<List<CountryDTO>> response = countryController.getAllCountries(null,"TST");
        assertEquals(List.of(country), response.getBody());
        verify(countryService, times(1)).getCountriesByCode("TST");
        assertEquals(response.getBody().get(0).getCode(), "TST");
    }

    @Test
    void createCountry() {
        CountryDTO country = new CountryDTO();
        country.setName("Test");
        when(countryService.saveCountries(1L)).thenReturn(List.of(country));

        ResponseEntity<List<CountryDTO>> response = countryController.createCountry(1L);

        assertEquals(country.getName(), response.getBody().get(0).getName());
        verify(countryService, times(1)).saveCountries(1L);
    }

    @Test
    void getCountrysByContinent() {
        CountryDTO country = new CountryDTO();
        country.setName("Test");
        when(countryService.getCountriesByContinent("test")).thenReturn(List.of(country));

        ResponseEntity<List<CountryDTO>> response = countryController.getCountrysByContinent("test");

        verify(countryService, times(1)).getCountriesByContinent("test");
        assertEquals(List.of(country), response.getBody());
        assertEquals(response.getBody().get(0).getName(), "Test");
    }

    @Test
    void getCountrysByLanguage() {
        CountryDTO country = new CountryDTO();
        country.setName("Test");
        when(countryService.getCountriesByLanguage("testing")).thenReturn(List.of(country));
        ResponseEntity<List<CountryDTO>> response = countryController.getCountrysByLanguage("testing");

        assertEquals(List.of(country), response.getBody());
        assertEquals(response.getBody().get(0).getName(), "Test");
    }

    @Test
    void getCountryWithMostBorders() {
        CountryDTO country = new CountryDTO();
        country.setName("Test");
        when(countryService.getCountryMostBorders()).thenReturn(country);
        ResponseEntity<CountryDTO> response = countryController.getCountryWithMostBorders();

        assertEquals(country, response.getBody());
        assertEquals(response.getBody().getName(), "Test");
    }
}