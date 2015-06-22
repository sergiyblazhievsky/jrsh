package com.jaspersoft.jasperserver.jrsh.core.common.config;

import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
public class Timeout {
    private Integer connection;
    private Integer read;

    public Integer getConnectionTimeout(){
        return connection;
    }

    public Integer getReadTimeout(){
        return read;
    }
}
