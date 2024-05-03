package com.satellite.satellite.service;

import com.satellite.satellite.model.APIResponse;
import com.satellite.satellite.model.Launcher;
import com.satellite.satellite.model.Launcher;
import com.satellite.satellite.model.LauncherResponse;
import com.satellite.satellite.repository.LauncherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class LauncherService {

    @Autowired
    private LauncherRepository launcherRepository;

    public ResponseEntity<APIResponse<Void>> fetchLaunchersFromAPI() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<LauncherResponse> response = restTemplate.getForEntity("https://isro.vercel.app/api/launchers", LauncherResponse.class);

        APIResponse<Void> apiResponse = new APIResponse<>();

        try{
            if (response.getStatusCode() == HttpStatus.OK) {
                LauncherResponse launchersResponse = response.getBody();
                for (Launcher l  : launchersResponse.getLaunchers()) {
                    System.out.println(l);
                    Launcher launcher = new Launcher();
                    launcher.setId(l.getId());

                    launcherRepository.save(launcher);
                }

                apiResponse = APIResponse.<Void>builder().status(HttpStatus.OK.value())
                        .message("Launchers fetched and saved successfully.")
                        .build();

            } else {
                apiResponse = APIResponse.<Void>builder().status(response.getStatusCodeValue())
                        .message("Failed to fetch launchers from API.")
                        .build();
            }
        }catch (Exception e){
            apiResponse.setStatus(500);
            apiResponse.setMessage(e.getMessage());
        }

        return ResponseEntity.status(response.getStatusCode()).body(apiResponse);
    }

    // Create operation
    public ResponseEntity<APIResponse<Launcher>> createLauncher(Launcher launcher) {
        try {
            Launcher createdLauncher = launcherRepository.save(launcher);
            APIResponse<Launcher> apiResponse =  APIResponse.<Launcher>builder()
                    .status(HttpStatus.CREATED.value())
                    .message("Launcher created successfully.")
                    .data(createdLauncher)
                    .build();
            return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        }catch (Exception e) {
            APIResponse<Launcher> apiResponse = APIResponse.<Launcher>builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("Failed to create satellite: " + e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }

    }

    // Read operation
    public ResponseEntity<APIResponse<List<Launcher>>> getAllLaunchers() {
        try{
            List<Launcher> satellites = launcherRepository.findAll();
            APIResponse<List<Launcher>> apiResponse = APIResponse.<List<Launcher>>builder()
                    .status(HttpStatus.OK.value())
                    .message("Launchers retrieved successfully.")
                    .data(satellites)
                    .build();
            return ResponseEntity.ok(apiResponse);
        }catch (Exception e) {
            APIResponse<List<Launcher>> apiResponse = APIResponse.<List<Launcher>>builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("Failed to retrieve satellites: " + e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }

    }

    public ResponseEntity<APIResponse<Launcher>> getLauncherById(String id) {
        try {
            Optional<Launcher> satelliteOptional = launcherRepository.findById(id);
            if (satelliteOptional.isPresent()) {
                APIResponse<Launcher> apiResponse =  APIResponse.<Launcher>builder()
                        .status(HttpStatus.OK.value())
                        .message("Launcher retrieved successfully.")
                        .data(satelliteOptional.get())
                        .build();
                return ResponseEntity.ok(apiResponse);
            } else {
                APIResponse<Launcher> apiResponse =  APIResponse.<Launcher>builder()
                        .status(HttpStatus.NOT_FOUND.value())
                        .message("Launcher not found.")
                        .build();
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
            }
        } catch (Exception e) {
            APIResponse<Launcher> apiResponse = APIResponse.<Launcher>builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("Failed to retrieve satellite: " + e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }

    }

    // Update operation
    public ResponseEntity<APIResponse<Launcher>> updateLauncher(Launcher satellite) {
        try {
            if (!launcherRepository.existsById(satellite.getId())) {
                APIResponse<Launcher> apiResponse = APIResponse.<Launcher>builder()
                        .status(HttpStatus.NOT_FOUND.value())
                        .message("Launcher not found.")
                        .build();
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
            }
            satellite.setId(satellite.getId());
            Launcher updatedLauncher = launcherRepository.save(satellite);
            APIResponse<Launcher> apiResponse =  APIResponse.<Launcher>builder()
                    .status(HttpStatus.OK.value())
                    .message("Launcher updated successfully.")
                    .data(updatedLauncher)
                    .build();
            return ResponseEntity.ok(apiResponse);
        }catch (Exception e) {
            APIResponse<Launcher> apiResponse = APIResponse.<Launcher>builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("Failed to update satellite: " + e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
    }

    // Delete operation
    public ResponseEntity<APIResponse<Void>> deleteLauncher(String id) {
        try{
            if (!launcherRepository.existsById(id)) {
                APIResponse<Void> apiResponse =  APIResponse.<Void>builder()
                        .status(HttpStatus.NOT_FOUND.value())
                        .message("Launcher not found.")
                        .build();
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
            }
            launcherRepository.deleteById(id);
            APIResponse<Void> apiResponse = APIResponse.<Void>builder()
                    .status(HttpStatus.OK.value())
                    .message("Launcher deleted successfully.")
                    .build();
            return ResponseEntity.ok(apiResponse);
        }catch (Exception e) {
            APIResponse<Void> apiResponse = APIResponse.<Void>builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("Failed to delete satellite: " + e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }

    }
}
