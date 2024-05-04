package com.satellite.satellite.service;

import com.satellite.satellite.model.APIResponse;
import com.satellite.satellite.model.CustomerSatellite;
import com.satellite.satellite.model.CustomerSatelliteResponse;

import com.satellite.satellite.repository.CustomerSatelliteRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;



import java.util.List;
import java.util.Optional;

@Service
public class CustomerSatelliteService {
    @Autowired
    private CustomerSatelliteRepository customerSatelliteRepository;

    // Fetch Satellite Data
    public ResponseEntity<APIResponse<Void>> fetchSatellitesFromAPI() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<CustomerSatelliteResponse> response = restTemplate
                .getForEntity("https://isro.vercel.app/api/customer_satellites", CustomerSatelliteResponse.class);

        APIResponse<Void> apiResponse = new APIResponse<>();
        try {
            if (response.getStatusCode() == HttpStatus.OK) {
                CustomerSatelliteResponse satellites = response.getBody();
                if (satellites != null) {
                    for (CustomerSatellite satellite : satellites.getCustomer_satellites()) {
                        // Assuming launcherId corresponds to id in Launcher entity

                        CustomerSatellite customerSatellite  = CustomerSatellite.
                                builder()
                                .id(satellite.getId())
                                .mass(satellite.getMass())
                                .country(satellite.getCountry())
                                .launcher(satellite
                                        .getLauncher())
                                .launch_date(satellite.getLaunch_date())
                                .build();

                        customerSatelliteRepository.save(satellite);
                    }
                    apiResponse.setStatus(HttpStatus.OK.value());
                    apiResponse.setMessage("Satellites fetched and saved successfully.");
                }
                apiResponse.setStatus(HttpStatus.OK.value());
                apiResponse.setMessage("No Satellite Found");

            } else {
                apiResponse.setStatus(response.getStatusCodeValue());
                apiResponse.setMessage("Failed to fetch satellites from API.");
            }
        }catch (Exception e){
            apiResponse.setStatus(500);
            apiResponse.setMessage(e.getMessage());
        }
        return ResponseEntity.status(response.getStatusCode()).body(apiResponse);
    }

    // Create operation
    public ResponseEntity<APIResponse<CustomerSatellite>> createCustomerSatellite(CustomerSatellite satellite) {
        try {
            CustomerSatellite createdSatellite = customerSatelliteRepository.save(satellite);
            APIResponse<CustomerSatellite> apiResponse =  APIResponse.<CustomerSatellite>builder()
                    .status(HttpStatus.CREATED.value())
                    .message("Satellite created successfully.")
                    .data(createdSatellite)
                    .build();
            return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        }catch (Exception e) {
        APIResponse<CustomerSatellite> apiResponse = APIResponse.<CustomerSatellite>builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("Failed to create satellite: " + e.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
    }

    }

    // Read operation
    public ResponseEntity<APIResponse<List<CustomerSatellite>>> getAllCustomerSatellites() {
        try{
            List<CustomerSatellite> satellites = customerSatelliteRepository.findAll();
            APIResponse<List<CustomerSatellite>> apiResponse = APIResponse.<List<CustomerSatellite>>builder()
                    .status(HttpStatus.OK.value())
                    .message("Satellites retrieved successfully.")
                    .data(satellites)
                    .build();
            return ResponseEntity.ok(apiResponse);
        }catch (Exception e) {
            APIResponse<List<CustomerSatellite>> apiResponse = APIResponse.<List<CustomerSatellite>>builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("Failed to retrieve satellites: " + e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }

    }

    public ResponseEntity<APIResponse<CustomerSatellite>> getCustomerSatelliteById(String id) {
        try {
            Optional<CustomerSatellite> satelliteOptional = customerSatelliteRepository.findById(id);
            if (satelliteOptional.isPresent()) {
                APIResponse<CustomerSatellite> apiResponse =  APIResponse.<CustomerSatellite>builder()
                        .status(HttpStatus.OK.value())
                        .message("Satellite retrieved successfully.")
                        .data(satelliteOptional.get())
                        .build();
                return ResponseEntity.ok(apiResponse);
            } else {
                APIResponse<CustomerSatellite> apiResponse =  APIResponse.<CustomerSatellite>builder()
                        .status(HttpStatus.NOT_FOUND.value())
                        .message("Satellite not found.")
                        .build();
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
            }
        } catch (Exception e) {
            APIResponse<CustomerSatellite> apiResponse = APIResponse.<CustomerSatellite>builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("Failed to retrieve satellite: " + e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }

    }

    // Update operation
    public ResponseEntity<APIResponse<CustomerSatellite>> updateCustomerSatellite(CustomerSatellite satellite) {
        try {
            if (!customerSatelliteRepository.existsById(satellite.getId())) {
                APIResponse<CustomerSatellite> apiResponse = APIResponse.<CustomerSatellite>builder()
                        .status(HttpStatus.NOT_FOUND.value())
                        .message("Satellite not found.")
                        .build();
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
            }
            satellite.setId(satellite.getId());
            CustomerSatellite updatedSatellite = customerSatelliteRepository.save(satellite);
            APIResponse<CustomerSatellite> apiResponse =  APIResponse.<CustomerSatellite>builder()
                    .status(HttpStatus.OK.value())
                    .message("Satellite updated successfully.")
                    .data(updatedSatellite)
                    .build();
            return ResponseEntity.ok(apiResponse);
        }catch (Exception e) {
            APIResponse<CustomerSatellite> apiResponse = APIResponse.<CustomerSatellite>builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("Failed to update satellite: " + e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
    }

    // Delete operation
    public ResponseEntity<APIResponse<Void>> deleteCustomerSatellite(String id) {
        try{
            if (!customerSatelliteRepository.existsById(id)) {
                APIResponse<Void> apiResponse =  APIResponse.<Void>builder()
                        .status(HttpStatus.NOT_FOUND.value())
                        .message("Satellite not found.")
                        .build();
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
            }
            customerSatelliteRepository.deleteById(id);
            APIResponse<Void> apiResponse = APIResponse.<Void>builder()
                    .status(HttpStatus.OK.value())
                    .message("Satellite deleted successfully.")
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

    public List<CustomerSatellite> searchSatellites(String id, String launchDate, String country, String launcherId, Double mass) {
        return customerSatelliteRepository.getAllSatelliteByFilter(id, country, launchDate, launcherId, mass != null, mass);
    }
}
