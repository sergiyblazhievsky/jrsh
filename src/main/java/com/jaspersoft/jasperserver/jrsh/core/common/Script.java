package com.jaspersoft.jasperserver.jrsh.core.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

// Do we really need this class?
@AllArgsConstructor
public class Script {
    @Getter private List<String> source;
}
