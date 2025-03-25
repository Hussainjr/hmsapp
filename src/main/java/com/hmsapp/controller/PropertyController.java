package com.hmsapp.controller;
import com.hmsapp.dto.PropertyDto;
import com.hmsapp.entity.Property;
import com.hmsapp.entity.PropertyImage;
import com.hmsapp.repository.PropertyImageRepository;
import com.hmsapp.repository.PropertyRepository;
import com.hmsapp.service.BucketService;
import com.hmsapp.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/property")
public class PropertyController {

    @Autowired private PropertyRepository propertyRepository;
    @Autowired private PropertyService propertyService;
    @Autowired private BucketService bucketService;
    @Autowired private PropertyImageRepository propertyImageRepository;


    @PostMapping("/createProperty")
    public ResponseEntity<PropertyDto> createProperty(@RequestBody PropertyDto propertyDto){
        PropertyDto dto = propertyService.saveProperty(propertyDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteProperty(@RequestParam Long id){
        propertyService.deleteProperty(id);
        return new ResponseEntity<>("deleted", HttpStatus.OK);
    }

    @PostMapping("/updatingProperty")
    public ResponseEntity<?> updatingProperty(@RequestParam Long id,@RequestBody PropertyDto propertyDto){
        PropertyDto dto = propertyService.updateProperty(id, propertyDto);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/gettingAllProperty")
    public ResponseEntity<List<PropertyDto>> gettingAllProperty() {
        List<PropertyDto> allProperty = propertyService.getAllProperty();
        return new ResponseEntity<>(allProperty, HttpStatus.OK);
    }

    //http://localhost:8080/api/v1/property/{searchParam}
    @GetMapping("/{searchParam}")
    public ResponseEntity<List<Property>> searchProperty(@PathVariable String searchParam){
        List<Property> properties = propertyRepository.searchProperty(searchParam);

        return new ResponseEntity<>( properties ,HttpStatus.OK);
    }

    @PostMapping("/")
    public String addProperty(){


        return "added";
    }

    //http://localhost:8080/api/v1/property/upload/file/{bucketName}/property/{propertyId}
    @PostMapping("/upload/file/{bucketName}/property/{propertyId}")
    public String uploadPropertyPhotos(
            @RequestParam MultipartFile file,
            @PathVariable String bucketName,
            @PathVariable long propertyId)
        {
            String imageUrl = bucketService.uploadFile(file, bucketName);
            PropertyImage propertyImage = new PropertyImage();
            propertyImage.setUrl(imageUrl);
            Optional<Property> byId = propertyRepository.findById(propertyId);
            if(byId.isEmpty()){
                throw new IllegalStateException("property id "+propertyId+ "not found");
            }
            Property property = byId.get();
            propertyImage.setProperty(property);
            propertyImageRepository.save(propertyImage);

            return "images is uploaded";
    }

    @GetMapping("/get/property/images")
    public List<PropertyImage> getPropertyImages(@RequestParam long id){
        Property property = propertyRepository.findById(id).get();
        return propertyImageRepository.findByProperty(property);
    }


}
