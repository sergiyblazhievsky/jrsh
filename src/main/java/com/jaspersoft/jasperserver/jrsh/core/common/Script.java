package com.jaspersoft.jasperserver.jrsh.core.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * Used to hold stringified operations. Each line
 * should contain only one operation.
 *
 * @author Alexander Krasnyanskiy
 * @version 2.0
 */
@AllArgsConstructor
public class Script {
    @Getter private List<String> source;
}
