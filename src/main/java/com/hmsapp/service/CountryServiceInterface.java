package com.hmsapp.service;

import com.hmsapp.dto.CountryDto;

import java.util.List;

public interface CountryServiceInterface {

    public CountryDto saveCountry(CountryDto dto);

    public void deleteCountry(Long id);

    public CountryDto updateCountry(Long id,CountryDto dto);

    public List<CountryDto> getAllCountry();


}
