package com.hmsapp.controller;

import com.hmsapp.dto.CityDto;
import com.hmsapp.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cityController")
public class CityController {

    @Autowired
    private CityService cityService;

    @PostMapping("/createCity")
    public ResponseEntity<CityDto> createCity(@RequestBody CityDto cityDto){
        CityDto dto = cityService.saveCity(cityDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<String> deletingCity(@RequestParam Long id){
        cityService.deleteCity(id);
        return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }

    @PostMapping("/updatingCity")
    public ResponseEntity<CityDto> updatingCity(@RequestParam Long id, @RequestBody CityDto cityDto){
        CityDto dto = cityService.updateCity(id, cityDto);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }


    @PostMapping("/gettingAllCity")
    public ResponseEntity<List<CityDto>> gettingAllCity(){
        List<CityDto> allCity = cityService.getAllCity();
        return new ResponseEntity<>(allCity,HttpStatus.OK);
    }




}
