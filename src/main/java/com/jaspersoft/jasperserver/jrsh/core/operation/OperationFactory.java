package com.jaspersoft.jasperserver.jrsh.core.operation;

import com.jaspersoft.jasperserver.jrsh.core.common.config.MetadataScannerConfig;
import com.jaspersoft.jasperserver.jrsh.core.operation.annotation.Master;
import com.jaspersoft.jasperserver.jrsh.core.operation.parser.exception.OperationNotFoundException;
import lombok.extern.log4j.Log4j;
import lombok.val;
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

    public static final  String BASE_PACKAGE = "com.jaspersoft.jasperserver.jrsh.core.operation.impl";
    private static final Map<String, Class<? extends Operation>> AVAILABLE_OPERATIONS;

    static {
        AVAILABLE_OPERATIONS = new HashMap<String, Class<? extends Operation>>();
        val types = getOperationTypes();
        for (val operationType : types) {
            Master annotation = operationType.getAnnotation(Master.class);
            if (annotation != null) {
                String operationName = annotation.name();
                AVAILABLE_OPERATIONS.put(operationName, operationType);
            }
        }
    }

    public static Operation createOperationByName(String operationName) {
        val operationType = AVAILABLE_OPERATIONS.get(operationName);
        if (operationType == null) {
            throw new OperationNotFoundException();
        }
        return createInstance(operationType);
    }

    public static Set<Operation> createOperationsByAvailableTypes() {
        HashSet<Operation> set = new HashSet<Operation>();
        for (val type : AVAILABLE_OPERATIONS.values()) {
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
        val operationTypes = new HashSet<Class<? extends Operation>>();
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
                }
            }
        }
        Reflections ref = new Reflections(new SubTypesScanner(), filter);
        for (val subType : ref.getSubTypesOf(Operation.class)) {
            if (!Modifier.isAbstract(subType.getModifiers())) {
                operationTypes.add(subType);
            }
        }
        return operationTypes;
    }
}
