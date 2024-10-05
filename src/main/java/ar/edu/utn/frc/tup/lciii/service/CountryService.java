package ar.edu.utn.frc.tup.lciii.service;

import ar.edu.utn.frc.tup.lciii.dtos.common.CountryDTO;
import ar.edu.utn.frc.tup.lciii.entities.CountryEntity;
import ar.edu.utn.frc.tup.lciii.model.Country;
import ar.edu.utn.frc.tup.lciii.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CountryService {
        private final CountryRepository countryRepository;

        private final RestTemplate restTemplate;

        public List<Country> getAllCountries() {
                String url = "https://restcountries.com/v3.1/all";
                List<Map<String, Object>> response = restTemplate.getForObject(url, List.class);
                return response.stream().map(this::mapToCountry).collect(Collectors.toList());
        }

        /**
         * Agregar mapeo de campo cca3 (String) --Listo
         * Agregar mapeo campos borders ((List<String>)) --Listo
         */
        private Country mapToCountry(Map<String, Object> countryData) {
                Map<String, Object> nameData = (Map<String, Object>) countryData.get("name");
                return Country.builder()
                        .name((String) nameData.get("common"))
                        .code((String) countryData.get("cca3"))
                        .population(((Number) countryData.get("population")).longValue())
                        .area(((Number) countryData.get("area")).doubleValue())
                        .region((String) countryData.get("region"))
                        .borders((List<String>) countryData.get("borders"))
                        .languages((Map<String, String>) countryData.get("languages"))
                        .build();
        }

        private CountryEntity mapToEntity(Country c){
                CountryEntity auxReturn = new CountryEntity();
                auxReturn.setName(c.getName());
                auxReturn.setCode(c.getCode());
                auxReturn.setPopulation(c.getPopulation());
                auxReturn.setArea(c.getArea());
                return auxReturn;
        }

        private CountryDTO mapToDTO(Country country) {
                return new CountryDTO(country.getCode(), country.getName());
        }

        public List<CountryDTO> getCountries(){
                List<Country> countries = this.getAllCountries();
                List<CountryDTO> auxReturn = new ArrayList<>();
                for(Country country : countries){
                        auxReturn.add(mapToDTO(country));
                }
                return auxReturn;
        }

        public List<CountryDTO> getCountriesByName(String name){
                List<Country> countries = this.getAllCountries();
                List<CountryDTO> auxReturn = new ArrayList<>();
                for(Country c : countries){
                        if(c.getName().toUpperCase().contains(name.toUpperCase()))
                                auxReturn.add(mapToDTO(c));
                }
                return auxReturn;
        }

        public List<CountryDTO> getCountriesByCode(String code){
                List<Country> countries = this.getAllCountries();
                List<CountryDTO> auxReturn = new ArrayList<>();
                for(Country c : countries){
                        if(c.getCode().toUpperCase().contains(code.toUpperCase()))
                                auxReturn.add(mapToDTO(c));
                }
                return auxReturn;
        }

        public List<CountryDTO> getCountriesByContinent(String continent){
                List<Country> countries = this.getAllCountries();
                List<CountryDTO> auxReturn = new ArrayList<>();
                for(Country c: countries){
                        if(c.getRegion().toUpperCase().contains(continent.toUpperCase()))
                                auxReturn.add(mapToDTO(c));
                }
                return auxReturn;
        }

        public List<CountryDTO> getCountriesByLanguage(String lang){
                List<Country> countries = this.getAllCountries();
                List<CountryDTO> auxReturn = new ArrayList<>();
                for(Country c: countries){
                        if(c.getLanguages()!=null&&(c.getLanguages().containsValue(lang)||c.getLanguages().containsKey(lang))){
                                auxReturn.add(mapToDTO(c));
                        }
                }
                return auxReturn;
        }

        public CountryDTO getCountryMostBorders(){
                List<Country> countries = this.getAllCountries();
                CountryDTO mostBorders = new CountryDTO();
                int auxBorders= 0;
                for(Country c : countries){
                        if(c.getBorders()!=null&&c.getBorders().size()>auxBorders){
                                auxBorders = c.getBorders().size();
                                mostBorders = mapToDTO(c);
                        }
                }
                return mostBorders;
        }

        public List<CountryDTO> saveCountries(Long quantity){
                List<Country> countries = this.getAllCountries();
                List<CountryDTO> auxReturn = new ArrayList<>();
                Random rand = new Random();
                List<CountryEntity> countriesAGuardar = new ArrayList<>();
                for (int i = 0; i < quantity; i++) {
                        countriesAGuardar.add(mapToEntity(countries.get(rand.nextInt(countries.size()))));
                }
                List<CountryEntity> guardados = this.countryRepository.saveAll(countriesAGuardar);
                for(CountryEntity e : guardados){
                        auxReturn.add(new CountryDTO(e.getCode(), e.getName()));
                }
                return auxReturn;
        }
}