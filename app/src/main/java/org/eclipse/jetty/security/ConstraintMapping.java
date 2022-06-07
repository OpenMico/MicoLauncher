package org.eclipse.jetty.security;

import org.eclipse.jetty.util.security.Constraint;

/* loaded from: classes5.dex */
public class ConstraintMapping {
    Constraint _constraint;
    String _method;
    String[] _methodOmissions;
    String _pathSpec;

    public Constraint getConstraint() {
        return this._constraint;
    }

    public void setConstraint(Constraint constraint) {
        this._constraint = constraint;
    }

    public String getMethod() {
        return this._method;
    }

    public void setMethod(String str) {
        this._method = str;
    }

    public String getPathSpec() {
        return this._pathSpec;
    }

    public void setPathSpec(String str) {
        this._pathSpec = str;
    }

    public void setMethodOmissions(String[] strArr) {
        this._methodOmissions = strArr;
    }

    public String[] getMethodOmissions() {
        return this._methodOmissions;
    }
}
