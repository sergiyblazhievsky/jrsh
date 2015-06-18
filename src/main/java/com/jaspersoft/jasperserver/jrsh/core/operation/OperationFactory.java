package com.jaspersoft.jasperserver.jrsh.core.operation;

import com.jaspersoft.jasperserver.jrsh.core.operation.annotation.Master;
import com.jaspersoft.jasperserver.jrsh.core.operation.parser.exception.OperationNotFoundException;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.FilterBuilder;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Alexander Krasnyanskiy
 */
@Log4j
@SuppressWarnings("unchecked")
public class OperationFactory {
    private static final Map<String, Class<? extends Operation>> AVAILABLE_OPERATIONS;

    static {
        AVAILABLE_OPERATIONS = new HashMap<String, Class<? extends Operation>>();
        Set<Class<? extends Operation>> types = getOperationTypes();
        for (Class<? extends Operation> operationType : types) {
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
        } catch (InstantiationException e) {
            throw new CouldNotCreateOperationInstance();
        } catch (IllegalAccessException e) {
            throw new CouldNotCreateOperationInstance();
        }
        return instance;
    }

    protected static Set<Class<? extends Operation>> getOperationTypes() {
        Set<Class<? extends Operation>> operationTypes = new HashSet<Class<? extends Operation>>();
        //
        // Read YAML config to get package info
        //
        Map<String, Object> config = getConfig();
        List<String> packages = (List<String>) config.get("packages-to-scan");
        List<String> classes = (List<String>) config.get("classes");
        //
        // Use default package to limit the search area and to speed up
        // scanning ClassPath
        //
        FilterBuilder filter = new FilterBuilder().includePackage("com.jaspersoft.jasperserver.jrsh.core.operation.impl");
        //
        // Add specific package names to filter
        //
        if (packages != null) {
            for (String aPackage : packages) {
                aPackage = StringUtils.chomp(aPackage, ".*");
                filter.includePackage(aPackage);
            }
        }

        if (classes != null) {
            for (String aClass : classes) {
                try {
                    Class clz = Class.forName(aClass);
                    if (!Modifier.isAbstract(clz.getModifiers())
                            && Operation.class.isAssignableFrom(clz)) {
                        operationTypes.add(clz);
                    }
                } catch (ClassNotFoundException ignored) {
                    // NOP
                }
            }
        }
        //
        // Scan CP and retrieve operation types
        //
        Reflections ref = new Reflections(new SubTypesScanner(), filter);
        for (Class<? extends Operation> subType : ref.getSubTypesOf(Operation.class)) {
            if (!Modifier.isAbstract(subType.getModifiers())) {
                operationTypes.add(subType);
            }
        }
        return operationTypes;
    }

    protected static Map<String, Object> getConfig() {
        Yaml yaml = new Yaml();
        Map<String, Object> config = new HashMap<String, Object>();
        try {
            InputStream file = OperationFactory.class.getClassLoader().getResourceAsStream("config.yml");
            try {
                config.putAll((Map<String, Object>) yaml.load(file));
            } finally {
                file.close();
            }
        } catch (IOException ignored) {
            // NOP
        }
        return config;
    }

}
