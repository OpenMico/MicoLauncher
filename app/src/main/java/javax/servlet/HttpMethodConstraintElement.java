package javax.servlet;

/* loaded from: classes5.dex */
public class HttpMethodConstraintElement extends HttpConstraintElement {
    private String a;

    public HttpMethodConstraintElement(String str) {
        if (str == null || str.length() == 0) {
            throw new IllegalArgumentException("invalid HTTP method name");
        }
        this.a = str;
    }

    public HttpMethodConstraintElement(String str, HttpConstraintElement httpConstraintElement) {
        super(httpConstraintElement.getEmptyRoleSemantic(), httpConstraintElement.getTransportGuarantee(), httpConstraintElement.getRolesAllowed());
        if (str == null || str.length() == 0) {
            throw new IllegalArgumentException("invalid HTTP method name");
        }
        this.a = str;
    }

    public String getMethodName() {
        return this.a;
    }
}
