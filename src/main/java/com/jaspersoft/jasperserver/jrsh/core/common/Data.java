package com.jaspersoft.jasperserver.jrsh.core.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * @author Alexander Krasnyanskiy
 */
@AllArgsConstructor
public class Data {
    @Getter
    private List<String> source;
}
