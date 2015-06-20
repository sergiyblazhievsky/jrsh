package com.jaspersoft.jasperserver.jrsh.core.common.config;

import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
public class ClientConnectionConfig {
    private Timeout timeout;

    public Timeout getTimeout() {
        return timeout;
    }
}
