package com.jaspersoft.jasperserver.jrsh.core.operation.impl;

import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import com.jaspersoft.jasperserver.jrsh.core.i18n.Messages;
import com.jaspersoft.jasperserver.jrsh.core.operation.Operation;
import com.jaspersoft.jasperserver.jrsh.core.operation.OperationResult;
import com.jaspersoft.jasperserver.jrsh.core.operation.annotation.Master;
import com.jaspersoft.jasperserver.jrsh.core.operation.annotation.Parameter;
import com.jaspersoft.jasperserver.jrsh.core.operation.annotation.Prefix;
import com.jaspersoft.jasperserver.jrsh.core.operation.annotation.Value;
import lombok.Data;

import java.util.Locale;

import static com.jaspersoft.jasperserver.jrsh.core.operation.OperationResult.ResultCode.SUCCESS;

@Data
@Master(name = "set")
public class SetLocaleOperation implements Operation {

    private Messages messages = new Messages("i18n/set");

    @Prefix("locale")
    @Parameter(mandatory = true, dependsOn = "set", values =
    @Value(tokenAlias = "LC", tail = true))
    private String locale;

    @Override
    public OperationResult eval(Session ignored) {
        //
        // Get Messages
        //
        String ok = messages.getMessage("messages.success");
        String failed = messages.getMessage("messages.failed");
        //
        // Change shared locale
        //
        Messages.locale = new Locale(locale);
        return new OperationResult(ok, SUCCESS, this, null);
    }

}
