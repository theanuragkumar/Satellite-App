package com.satellite.satellite.repository;

import com.satellite.satellite.model.Launcher;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LauncherRepository extends MongoRepository<Launcher, String> {
}
