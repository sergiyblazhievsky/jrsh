package com.jaspersoft.jasperserver.jrsh.core.common;

import java.util.List;

public class MetadataScannerConfig {

    private List<String> classes;
    private List<String> packagesToScan;

    public void setPackages(List<String> packages) {
        this.packagesToScan = packages;
    }

    public void setClasses(List<String> classes) {
        this.classes = classes;
    }

    public List<String> getClasses() {
        return classes;
    }

    public List<String> getPackagesToScan() {
        return packagesToScan;
    }
}
