package com.hmsapp.service;

import com.hmsapp.dto.CountryDto;
import com.hmsapp.entity.Country;
import com.hmsapp.repository.CountryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CountryService implements CountryServiceInterface{

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CountryRepository countryRepository;


    @Override
    public CountryDto saveCountry(CountryDto dto) {
        Country mapped = modelMapper.map(dto, Country.class);
        Country saved = countryRepository.save(mapped);
        CountryDto countryDto = modelMapper.map(saved, CountryDto.class);
        return countryDto;
    }

    @Override
    public void deleteCountry(Long id) {
        countryRepository.deleteById(id);
    }

    @Override
    public CountryDto updateCountry(Long id, CountryDto dto) {
        Optional<Country> byId = countryRepository.findById(id);
        if(byId.isEmpty()){
            throw new RuntimeException("Country id "+id+ "not found");
        }
        Country country = byId.get();
        country.setName(dto.getName());
        Country saved = countryRepository.save(country);
        CountryDto countryDto = modelMapper.map(saved, CountryDto.class);
        return countryDto;
    }

    @Override
    public List<CountryDto> getAllCountry() {
        List<Country> all = countryRepository.findAll();
        List<CountryDto> countryDtoList = all.stream().map(i -> modelMapper.map(i, CountryDto.class)).toList();
        return countryDtoList;
    }

    public CountryDto getCountryByName(String name){
        Optional<Country> byName = countryRepository.findByName(name);
        if(byName.isEmpty()){
            throw new RuntimeException("country name "+name+"not found");
        }
        Country country = byName.get();
        CountryDto countryDto = modelMapper.map(country, CountryDto.class);
        return countryDto;
    }

    public CountryDto getCountryById(Long id){
        Optional<Country> byId = countryRepository.findById(id);
        if(byId.isEmpty()){
            throw new RuntimeException("country id "+id+"not found.");
        }
        Country country = byId.get();
        CountryDto countryDto = modelMapper.map(country, CountryDto.class);
        return countryDto;
    }




}
