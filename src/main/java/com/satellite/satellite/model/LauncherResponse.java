package com.satellite.satellite.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LauncherResponse {
    @JsonProperty("launchers")
    private Launcher[] launchers;

    public Launcher[] getLaunchers() {
        return launchers;
    }

    public void setLaunchers(Launcher[] launchers) {
        this.launchers = launchers;
    }
}
