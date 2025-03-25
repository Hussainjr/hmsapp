package com.hmsapp.controller;

import com.hmsapp.dto.CountryDto;
import com.hmsapp.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/countryController")
public class    CountryController {

    @Autowired
    private CountryService countryService;

    @PostMapping("/createCountry")
    public ResponseEntity<?> createCountry(@RequestBody CountryDto dto){
        CountryDto countryDto = countryService.saveCountry(dto);
        return new ResponseEntity<>(countryDto, HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<String> deletingCountry(@RequestParam Long id, @RequestBody CountryDto countryDto){
        countryService.deleteCountry(id);
        return new ResponseEntity<>("deleted", HttpStatus.OK);
    }

    @PostMapping("/updatingCountry")
    public ResponseEntity<CountryDto> updatingCountry(@RequestParam Long id, @RequestBody CountryDto countryDto){
        CountryDto updateCountry = countryService.updateCountry(id, countryDto);
        return new ResponseEntity<>(updateCountry, HttpStatus.OK);
    }

    @GetMapping("/allCountry")
    public ResponseEntity<List<CountryDto>> gettingAllCountry(){
        List<CountryDto> allCountry = countryService.getAllCountry();
        return new ResponseEntity<>(allCountry, HttpStatus.OK);
    }

    @GetMapping("/by-name/{name}")
    public ResponseEntity<CountryDto> countryByName(@PathVariable String name){
        CountryDto countryNameDto = countryService.getCountryByName(name);
        return new ResponseEntity<>(countryNameDto,HttpStatus.OK);
    }

    @GetMapping("/countryById/{id}")
    public ResponseEntity<CountryDto> countryById(@RequestParam Long id){
        CountryDto countryById = countryService.getCountryById(id);
        return new ResponseEntity<>(countryById, HttpStatus.OK);
    }



}
