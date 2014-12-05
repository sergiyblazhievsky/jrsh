package com.jaspersoft.cli.tool.api.toolkit;

import org.apache.commons.cli.Option;

/**
 * @author Alexander Krasnyanskiy
 * @since 1.0
 */
public interface Toolkit {

    void run();

    void help();

    void importData(Option[] options);

    void profile();

    void server(Option[] options);

    void version();

    void reedStdin();

    void log(Exception e);
}
