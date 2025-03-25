package com.hmsapp.service;

import com.hmsapp.dto.PropertyDto;
import java.util.List;

public interface PropertyServiceInterface {

    public PropertyDto saveProperty(PropertyDto propertyDto);

    public void deleteProperty(Long id);

    public PropertyDto updateProperty(Long id, PropertyDto propertyDto);

    public List<PropertyDto> getAllProperty();


}
