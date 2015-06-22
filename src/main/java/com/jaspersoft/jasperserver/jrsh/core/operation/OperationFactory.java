package com.jaspersoft.jasperserver.jrsh.core.operation;

import com.jaspersoft.jasperserver.jrsh.core.common.config.MetadataScannerConfig;
import com.jaspersoft.jasperserver.jrsh.core.operation.annotation.Master;
import com.jaspersoft.jasperserver.jrsh.core.operation.parser.exception.OperationNotFoundException;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.FilterBuilder;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * @author Alexander Krasnyanskiy
 * @since 2.0
 */
@Log4j
public class OperationFactory {

    private static final Map<String, Class<? extends Operation>> AVAILABLE_OPERATIONS;
    public static final String BASE_PACKAGE = "com.jaspersoft.jasperserver.jrsh.core.operation.impl";

    static {
        AVAILABLE_OPERATIONS = new HashMap<String, Class<? extends Operation>>();
        for (Class<? extends Operation> operationType : getOperationTypes()) {
            Master annotation = operationType.getAnnotation(Master.class);
            if (annotation != null) {
                String operationName = annotation.name();
                AVAILABLE_OPERATIONS.put(operationName, operationType);
            }
        }
    }

    public static Operation createOperationByName(String operationName) {
        Class<? extends Operation> operationType = AVAILABLE_OPERATIONS.get(operationName);
        if (operationType == null) {
            throw new OperationNotFoundException();
        }
        return createInstance(operationType);
    }

    public static Set<Operation> createOperationsByAvailableTypes() {
        Set<Operation> set = new HashSet<Operation>();
        for (Class<? extends Operation> type : AVAILABLE_OPERATIONS.values()) {
            set.add(createInstance(type));
        }
        return set;
    }

    protected static Operation createInstance(Class<? extends Operation> operationType) {
        Operation instance;
        try {
            instance = operationType.newInstance();
        } catch (Exception e) {
            throw new CouldNotCreateOperationInstance();
        }
        return instance;
    }

    protected static Set<Class<? extends Operation>> getOperationTypes() {
        Set<Class<? extends Operation>> operationTypes = new HashSet<Class<? extends Operation>>();

        Yaml yml = new Yaml();
        InputStream file = OperationFactory.class.getClassLoader().getResourceAsStream("scanner.yml");
        MetadataScannerConfig config = yml.loadAs(file, MetadataScannerConfig.class);

        List<String> packagesToScan = config.getPackagesToScan();
        List<String> classes = config.getClasses();
        FilterBuilder filter = new FilterBuilder().includePackage(BASE_PACKAGE);

        if (packagesToScan != null) {
            for (String aPackage : packagesToScan) {
                aPackage = StringUtils.chomp(aPackage, ".*");
                filter.includePackage(aPackage);
            }
        }

        if (classes != null) {
            for (String aClass : classes) {
                try {
                    Class clz = Class.forName(aClass);
                    if (!Modifier.isAbstract(clz.getModifiers()) && Operation.class.isAssignableFrom(clz)) {
                        operationTypes.add(clz);
                    }
                } catch (ClassNotFoundException ignored) {
                    // NOP
                }
            }
        }
        //
        // Scan ClassPath to get operation types
        //
        Reflections ref = new Reflections(new SubTypesScanner(), filter);
        for (Class<? extends Operation> subType : ref.getSubTypesOf(Operation.class)) {
            if (!Modifier.isAbstract(subType.getModifiers())) {
                operationTypes.add(subType);
            }
        }
        return operationTypes;
    }
}
