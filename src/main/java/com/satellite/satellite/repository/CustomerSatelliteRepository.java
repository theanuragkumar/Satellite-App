package com.satellite.satellite.repository;

import com.satellite.satellite.model.CustomerSatellite;

import jakarta.persistence.Id;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerSatelliteRepository extends MongoRepository<CustomerSatellite, String> {
    @Query("{$or :[{id: ?0}, {country: ?1}, {launch_date: ?2}, {launcher: ?3}, {mass: {$exists: ?4, $eq: ?5}}]}")
    List<CustomerSatellite> getAllSatelliteByFilter(String id, String country, String launch_date, String launcher, Boolean hasMass, Double mass);





}
