package ar.edu.utn.frc.tup.lciii.service;

import ar.edu.utn.frc.tup.lciii.dtos.common.CountryDTO;
import ar.edu.utn.frc.tup.lciii.model.Country;
import ar.edu.utn.frc.tup.lciii.repository.CountryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class CountryServiceTest {
    @MockBean
    private CountryRepository countryRepository;

    @Autowired
    private RestTemplate restTemplate;

    @InjectMocks
    private CountryService countryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void getCountries() {
        List<CountryDTO> result = countryService.getCountries();
        assertNotNull(result);
    }

    @Test
    void getCountriesByName() {
        List<CountryDTO> result = countryService.getCountriesByName("Argentina");
        assertNotNull(result);
        assertEquals(result.get(0).getName(), "Argentina");
    }

    @Test
    void getCountriesByCode() {
        List<CountryDTO> result = countryService.getCountriesByCode("ARG");
        assertNotNull(result);
        assertEquals(result.get(0).getCode(), "ARG");
    }


    @Test
    void getCountryMostBorders() {
        CountryDTO result = countryService.getCountryMostBorders();
        assertNotNull(result);
        assertEquals(result.getName(), "China");
        assertEquals(result.getCode(), "CHN");
    }

}