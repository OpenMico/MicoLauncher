package javax.servlet;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import javax.servlet.annotation.HttpMethodConstraint;
import javax.servlet.annotation.ServletSecurity;

/* loaded from: classes5.dex */
public class ServletSecurityElement extends HttpConstraintElement {
    private Collection<String> a;
    private Collection<HttpMethodConstraintElement> b;

    public ServletSecurityElement() {
        this.b = new HashSet();
        this.a = Collections.emptySet();
    }

    public ServletSecurityElement(HttpConstraintElement httpConstraintElement) {
        super(httpConstraintElement.getEmptyRoleSemantic(), httpConstraintElement.getTransportGuarantee(), httpConstraintElement.getRolesAllowed());
        this.b = new HashSet();
        this.a = Collections.emptySet();
    }

    public ServletSecurityElement(Collection<HttpMethodConstraintElement> collection) {
        this.b = collection == null ? new HashSet<>() : collection;
        this.a = a(this.b);
    }

    public ServletSecurityElement(HttpConstraintElement httpConstraintElement, Collection<HttpMethodConstraintElement> collection) {
        super(httpConstraintElement.getEmptyRoleSemantic(), httpConstraintElement.getTransportGuarantee(), httpConstraintElement.getRolesAllowed());
        this.b = collection == null ? new HashSet<>() : collection;
        this.a = a(this.b);
    }

    public ServletSecurityElement(ServletSecurity servletSecurity) {
        super(servletSecurity.value().value(), servletSecurity.value().transportGuarantee(), servletSecurity.value().rolesAllowed());
        this.b = new HashSet();
        HttpMethodConstraint[] httpMethodConstraints = servletSecurity.httpMethodConstraints();
        for (HttpMethodConstraint httpMethodConstraint : httpMethodConstraints) {
            this.b.add(new HttpMethodConstraintElement(httpMethodConstraint.value(), new HttpConstraintElement(httpMethodConstraint.emptyRoleSemantic(), httpMethodConstraint.transportGuarantee(), httpMethodConstraint.rolesAllowed())));
        }
        this.a = a(this.b);
    }

    public Collection<HttpMethodConstraintElement> getHttpMethodConstraints() {
        return Collections.unmodifiableCollection(this.b);
    }

    public Collection<String> getMethodNames() {
        return Collections.unmodifiableCollection(this.a);
    }

    private Collection<String> a(Collection<HttpMethodConstraintElement> collection) {
        HashSet hashSet = new HashSet();
        for (HttpMethodConstraintElement httpMethodConstraintElement : collection) {
            String methodName = httpMethodConstraintElement.getMethodName();
            if (!hashSet.add(methodName)) {
                throw new IllegalArgumentException("Duplicate HTTP method name: " + methodName);
            }
        }
        return hashSet;
    }
}
