package com.jaspersoft.jasperserver.jrsh.core.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * Operation data holder. It contains the operations
 * line by line in string format.
 *
 * @author Alex Krasnyanskiy
 */
@AllArgsConstructor
public class Script {
    @Getter private List<String> source;
}
