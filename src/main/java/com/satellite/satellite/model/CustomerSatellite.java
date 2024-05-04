package com.satellite.satellite.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;




@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "customerSatellite")
public class CustomerSatellite {
    @Id
    private String id;
    private String country;
    private String launch_date;
    private String launcher;
    private double mass;
}
