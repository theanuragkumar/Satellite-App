package com.satellite.satellite.controller;

import com.satellite.satellite.model.APIResponse;
import com.satellite.satellite.model.CustomerSatellite;
import com.satellite.satellite.service.CustomerSatelliteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/satellite")
public class CustomerSatelliteController {

    @Autowired
    CustomerSatelliteService customerSatelliteService;

    @PostMapping("/fetchSatellites")
    public void fetchSatellitesFromAPI() {
        customerSatelliteService.fetchSatellitesFromAPI();
    }

    @GetMapping("/search")
    public ResponseEntity<APIResponse<List<CustomerSatellite>>> searchSatellites(
            @RequestParam(name = "id", required = false) String id,
            @RequestParam(name = "launchDate", required = false) String launchDate,
            @RequestParam(name = "country", required = false) String country,
            @RequestParam(name = "launcherId", required = false) String launcherId,
            @RequestParam(name = "mass", required = false) Double mass) {

        List<CustomerSatellite> searchResults = customerSatelliteService.searchSatellites(id, launchDate, country, launcherId, mass);

        if (searchResults.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(APIResponse.<List<CustomerSatellite>>builder()
                    .status(HttpStatus.NOT_FOUND.value())
                    .message("No satellites found matching the search criteria.")
                    .build());
        } else {
            return ResponseEntity.ok(APIResponse.<List<CustomerSatellite>>builder()
                    .status(HttpStatus.OK.value())
                    .message("Satellites retrieved successfully.")
                    .data(searchResults)
                    .build());
        }
    }

    @GetMapping("")
    public ResponseEntity<APIResponse<List<CustomerSatellite>>> getAllCustomerSatellites() {
        return customerSatelliteService.getAllCustomerSatellites();
    }

    @PostMapping("")
    public ResponseEntity<APIResponse<CustomerSatellite>> addCustomerSatellites(@RequestBody CustomerSatellite customerSatellite) {
        return customerSatelliteService.createCustomerSatellite(customerSatellite);
    }

    @PutMapping("")
    public ResponseEntity<APIResponse<CustomerSatellite>> updateCustomerSatellites(@RequestBody CustomerSatellite customerSatellite) {
        return customerSatelliteService.updateCustomerSatellite(customerSatellite);
    }

    @DeleteMapping("")
    public ResponseEntity<APIResponse<Void>> deleteCustomerSatellites(@RequestParam("id") String id) {
        return customerSatelliteService.deleteCustomerSatellite(id);
    }

    @GetMapping("/id")
    public ResponseEntity<APIResponse<CustomerSatellite>> getCustomerSatellitesById(@RequestParam("id") String id) {
        return customerSatelliteService.getCustomerSatelliteById(id);
    }


}
