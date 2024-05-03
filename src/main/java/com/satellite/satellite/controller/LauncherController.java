package com.satellite.satellite.controller;

import com.satellite.satellite.model.APIResponse;
import com.satellite.satellite.model.Launcher;
import com.satellite.satellite.service.LauncherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/launchers")
@CrossOrigin()
public class LauncherController {
    @Autowired
    private LauncherService launcherService;

    @PostMapping("/fetchLauncher")
    public void fetchLaunchersFromAPI() {
        launcherService.fetchLaunchersFromAPI();
    }

    @GetMapping("")
    public ResponseEntity<APIResponse<List<Launcher>>> getAllLaunchers() {
        return launcherService.getAllLaunchers();
    }

    @PostMapping("")
    public ResponseEntity<APIResponse<Launcher>> addLaunchers(@RequestBody Launcher launcher) {
        return launcherService.createLauncher(launcher);
    }

    @PutMapping("")
    public ResponseEntity<APIResponse<Launcher>> updateLaunchers(@RequestBody Launcher launcher) {
        return launcherService.updateLauncher(launcher);
    }

    @DeleteMapping("")
    public ResponseEntity<APIResponse<Void>> deleteLaunchers(@RequestParam("id") String id) {
        return launcherService.deleteLauncher(id);
    }

    @GetMapping("/id")
    public ResponseEntity<APIResponse<Launcher>> getLaunchersById(@RequestParam("id") String id) {
        return launcherService.getLauncherById(id);
    }
}
