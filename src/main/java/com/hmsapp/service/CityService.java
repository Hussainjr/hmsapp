package com.hmsapp.service;

import com.hmsapp.dto.CityDto;
import com.hmsapp.entity.City;
import com.hmsapp.repository.CityRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CityService implements CityServiceInterface{

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CityRepository cityRepository;

    @Override
    public CityDto saveCity(CityDto dto) {
        City mapped = modelMapper.map(dto, City.class);
        City saved = cityRepository.save(mapped);
        CityDto cityDto = modelMapper.map(saved, CityDto.class);
        return cityDto;
    }

    @Override
    public void deleteCity(Long id) {
        cityRepository.deleteById(id);
    }

    @Override
    public CityDto updateCity(Long id, CityDto dto) {
        Optional<City> byId = cityRepository.findById(id);
        if(byId.isEmpty()){
            throw new RuntimeException("city id "+id+"not found");
        }
        City city = byId.get();
        city.setName(dto.getName());
        City saved = cityRepository.save(city);
        CityDto cityDto = modelMapper.map(saved, CityDto.class);
        return cityDto;
    }

    @Override
    public List<CityDto> getAllCity() {
        List<City> cityList = cityRepository.findAll();
        List<CityDto> cityDtoList = cityList.stream().map(i -> modelMapper.map(i, CityDto.class)).toList();
        return cityDtoList;
    }
}
