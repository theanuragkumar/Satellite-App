package com.satellite.satellite.model;


import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class APIResponse<T> {
    private int status;
    private String message;
    private T data;

}
