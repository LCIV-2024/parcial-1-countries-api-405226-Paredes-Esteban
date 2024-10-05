package ar.edu.utn.frc.tup.lciii.controllers;
import ar.edu.utn.frc.tup.lciii.dtos.common.CountryDTO;
import ar.edu.utn.frc.tup.lciii.exception.CountryNotFoundException;
import ar.edu.utn.frc.tup.lciii.model.Country;
import ar.edu.utn.frc.tup.lciii.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController("/api")
@RequiredArgsConstructor
public class CountryController {
    private final CountryService countryService;

    @GetMapping("/countries")
    public ResponseEntity<List<CountryDTO>> getAllCountries(@RequestParam(required = false) String name, @RequestParam(required = false) String code){
        List<CountryDTO> list = new ArrayList<>();
        if(name==null&&code==null){
            list = countryService.getCountries();
        }
        else if(name!=null){
            list = countryService.getCountriesByName(name);
        }
        else {
            list = countryService.getCountriesByCode(code);
        }
        if(list.isEmpty()){
            throw new CountryNotFoundException("No se encontraron los paises");
        }
        return ResponseEntity.ok().body(list);
    }

    @PostMapping("/countries")
    public ResponseEntity<List<CountryDTO>> createCountry(@RequestBody Long amountOfCountryToSave){
        if(amountOfCountryToSave==null||amountOfCountryToSave<=0){
            throw new IllegalArgumentException("Debe ingresar una cantidad valida");
        }
        List<CountryDTO> countries= countryService.saveCountries(amountOfCountryToSave);
        if(!countries.isEmpty()){
            return ResponseEntity.ok().body(countries);
        }
        return ResponseEntity.internalServerError().build();
    }

    @GetMapping("/countries/{continent}/continent")
    public ResponseEntity<List<CountryDTO>> getCountrysByContinent(@PathVariable(required = true) String continent){
        List<CountryDTO> auxReturn = countryService.getCountriesByContinent(continent);
        if(auxReturn.isEmpty()){
            throw new CountryNotFoundException("No se encontraron paises para ese continente");
        }
        return ResponseEntity.ok().body(auxReturn);
    }

    @GetMapping("/countries/{language}/language")
    public ResponseEntity<List<CountryDTO>> getCountrysByLanguage(@PathVariable(required = true) String language){
        List<CountryDTO> auxReturn = countryService.getCountriesByLanguage(language);
        if (auxReturn.isEmpty()){
            throw new CountryNotFoundException("No se encontraron paises con ese lenguaje");
        }
        return ResponseEntity.ok().body(auxReturn);
    }

    @GetMapping("/countries/most-borders")
    public ResponseEntity<CountryDTO> getCountryWithMostBorders(){
        return ResponseEntity.ok().body(countryService.getCountryMostBorders());
    }

}