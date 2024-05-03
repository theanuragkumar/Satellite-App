package com.satellite.satellite.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class CustomerSatelliteResponse {

    @JsonProperty("customer_satellites")
    private CustomerSatellite[] customer_satellites;


}


