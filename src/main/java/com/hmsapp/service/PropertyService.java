package com.hmsapp.service;

import com.hmsapp.dto.PropertyDto;
import com.hmsapp.entity.City;
import com.hmsapp.entity.Country;
import com.hmsapp.entity.Property;
import com.hmsapp.repository.CityRepository;
import com.hmsapp.repository.CountryRepository;
import com.hmsapp.repository.PropertyRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PropertyService implements PropertyServiceInterface{

    @Autowired private PropertyRepository propertyRepository;
    @Autowired private ModelMapper modelMapper;
    @Autowired private CityRepository cityRepository;
    @Autowired private CountryRepository countryRepository;

    @Override
    public PropertyDto saveProperty(PropertyDto propertyDto) {
        City city = cityRepository.findById(propertyDto.getId()).orElseThrow(() -> new RuntimeException("city not found"));
        Country country = countryRepository.findById(propertyDto.getId()).orElseThrow(() -> new RuntimeException("country not found"));
        Property property = modelMapper.map(propertyDto, Property.class);
        property.setCity(city);
        property.setCountry(country);
        Property savedProperty = propertyRepository.save(property);
        PropertyDto dto = modelMapper.map(savedProperty, PropertyDto.class);
        return dto;
    }

    @Override
    public void deleteProperty(Long id) {
        propertyRepository.deleteById(id);
    }

    @Override
    public PropertyDto updateProperty(Long id, PropertyDto propertyDto) {
        Optional<Property> byId = propertyRepository.findById(id);
        if(byId.isEmpty()){
            throw new RuntimeException("property id "+id+ "not found");
        }
        Property property = byId.get();
        property.setName(propertyDto.getName());
        property.setNoOfBathroom(propertyDto.getNoOfBathroom());
        property.setNoOfGuest(propertyDto.getNoOfGuest());
        property.setNoOfBedroom(propertyDto.getNoOfBedroom());
        Property saved = propertyRepository.save(property);
        PropertyDto mappedDto = modelMapper.map(saved, PropertyDto.class);
        return mappedDto;
    }

    @Override
    public List<PropertyDto> getAllProperty() {
        List<Property> all = propertyRepository.findAll();
        List<PropertyDto> dtoList = all.stream().map(i -> modelMapper.map(i, PropertyDto.class)).toList();
        return dtoList;
    }

}
