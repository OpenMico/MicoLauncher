package org.eclipse.jetty.security;

import java.util.List;
import java.util.Set;

/* loaded from: classes5.dex */
public interface ConstraintAware {
    void addConstraintMapping(ConstraintMapping constraintMapping);

    void addRole(String str);

    List<ConstraintMapping> getConstraintMappings();

    Set<String> getRoles();

    void setConstraintMappings(List<ConstraintMapping> list, Set<String> set);
}
