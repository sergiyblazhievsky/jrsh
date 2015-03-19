package com.jaspersoft.jasperserver.shell.completion.misc;

import lombok.Data;

import java.util.Map;

/**
 * @author Aaron Meir
 */
@Data
public class Response {
    private boolean success;
    private Map<String, Boolean> lookups;
}
