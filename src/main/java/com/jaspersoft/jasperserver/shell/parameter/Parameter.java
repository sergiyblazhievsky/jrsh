package com.jaspersoft.jasperserver.shell.parameter;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@Deprecated
@EqualsAndHashCode(exclude = {"key", "values"})
@ToString(exclude = {"key", "values"})
@Accessors(chain = true)
public class Parameter {
    private String name;
    private String key;
    private boolean optional;
    private boolean multiple;
    private boolean available;
    private List<String> values = new ArrayList<>();
}
