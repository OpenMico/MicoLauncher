package javax.servlet;

import javax.servlet.annotation.ServletSecurity;

/* loaded from: classes5.dex */
public class HttpConstraintElement {
    private ServletSecurity.EmptyRoleSemantic a;
    private ServletSecurity.TransportGuarantee b;
    private String[] c;

    public HttpConstraintElement() {
        this(ServletSecurity.EmptyRoleSemantic.PERMIT);
    }

    public HttpConstraintElement(ServletSecurity.EmptyRoleSemantic emptyRoleSemantic) {
        this(emptyRoleSemantic, ServletSecurity.TransportGuarantee.NONE, new String[0]);
    }

    public HttpConstraintElement(ServletSecurity.TransportGuarantee transportGuarantee, String... strArr) {
        this(ServletSecurity.EmptyRoleSemantic.PERMIT, transportGuarantee, strArr);
    }

    public HttpConstraintElement(ServletSecurity.EmptyRoleSemantic emptyRoleSemantic, ServletSecurity.TransportGuarantee transportGuarantee, String... strArr) {
        if (emptyRoleSemantic != ServletSecurity.EmptyRoleSemantic.DENY || strArr.length <= 0) {
            this.a = emptyRoleSemantic;
            this.b = transportGuarantee;
            this.c = strArr;
            return;
        }
        throw new IllegalArgumentException("Deny semantic with rolesAllowed");
    }

    public ServletSecurity.EmptyRoleSemantic getEmptyRoleSemantic() {
        return this.a;
    }

    public ServletSecurity.TransportGuarantee getTransportGuarantee() {
        return this.b;
    }

    public String[] getRolesAllowed() {
        return this.c;
    }
}
