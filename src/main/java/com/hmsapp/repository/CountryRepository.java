package com.hmsapp.repository;

import com.hmsapp.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
        Optional<Country> findByName(String name);
        Optional<Country> findById(Long id);
}