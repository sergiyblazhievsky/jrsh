package com.jaspersoft.jasperserver.shell.profile;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Alexander Krasnyanskiy
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DefaultProfileConfiguration extends ProfileConfiguration {
    private Profile defaultProfile;
}
