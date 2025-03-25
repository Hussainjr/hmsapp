package com.hmsapp.service;

import com.hmsapp.dto.CityDto;

import java.util.List;

public interface CityServiceInterface {

    public CityDto saveCity(CityDto dto);

    public void deleteCity(Long id);

    public CityDto updateCity(Long id, CityDto dto);

    public List<CityDto> getAllCity();

}
