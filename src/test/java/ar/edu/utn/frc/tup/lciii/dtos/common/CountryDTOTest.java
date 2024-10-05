package ar.edu.utn.frc.tup.lciii.dtos.common;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CountryDTOTest {
    private CountryDTO countryDTO;

    @BeforeEach
     void setUp() {
        countryDTO = new CountryDTO();
        countryDTO.setCode("TST");
        countryDTO.setName("Test");
    }

    @Test
    void getCode() {
        assertEquals("TST", countryDTO.getCode());
    }

    @Test
    void getName() {
        assertEquals("Test", countryDTO.getName());
    }

    @Test
    void setCode() {
        countryDTO.setCode("CHC");
        assertEquals("CHC", countryDTO.getCode());
    }

    @Test
    void setName() {
        countryDTO.setName("Test2");
        assertEquals("Test2", countryDTO.getName());
    }

    @Test
    void testEquals() {
        CountryDTO countryDTO2 = new CountryDTO();
        countryDTO2.setCode("TST");
        countryDTO2.setName("Test");
        assertEquals(countryDTO, countryDTO2);
    }
}