package com.jaspersoft.jasperserver.jrsh.core.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * @author Alexander Krasnyanskiy
 */
@AllArgsConstructor
public class Script {
    @Getter private List<String> source;
}
