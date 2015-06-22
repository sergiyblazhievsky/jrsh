package com.jaspersoft.jasperserver.jrsh.core.common.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class MetadataScannerConfig {
    @Setter
    private List<String> classes;
    private List<String> packagesToScan;

    public void setPackages(List<String> packages) {
        this.packagesToScan = packages;
    }
}
