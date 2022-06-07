package com.xiaomi.micolauncher.module.miot.mesh;

/* loaded from: classes3.dex */
public class MiotMeshCmdResponseFailed {
    private int a;
    private ErrorBean b;

    public int getId() {
        return this.a;
    }

    public void setId(int i) {
        this.a = i;
    }

    public ErrorBean getError() {
        return this.b;
    }

    public void setError(ErrorBean errorBean) {
        this.b = errorBean;
    }

    /* loaded from: classes3.dex */
    public static class ErrorBean {
        private String a;
        private int b;

        public String getMessage() {
            return this.a;
        }

        public void setMessage(String str) {
            this.a = str;
        }

        public int getCode() {
            return this.b;
        }

        public void setCode(int i) {
            this.b = i;
        }
    }
}
