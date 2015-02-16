package com.jaspersoft.jasperserver.shell.profile.representer;

import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.nodes.NodeTuple;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Representer;

/**
 * @author Alexander Krasnyanskiy
 */
public class SkipEmptyRepresenter extends Representer {
    @Override
    protected NodeTuple representJavaBeanProperty(Object bean, Property property, Object propertyValue, Tag tag) {
        return propertyValue == null ? null : super.representJavaBeanProperty(bean, property, propertyValue, tag);
    }
}


