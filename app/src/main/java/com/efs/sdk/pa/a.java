package com.efs.sdk.pa;

/* loaded from: classes.dex */
public final class a implements PAANRListener {
    private PAFactory a;
    private PATraceListener b;

    public a(PAFactory pAFactory) {
        this.a = pAFactory;
        this.b = pAFactory.getTraceListener();
        boolean enableTracer = pAFactory.getConfigManager().enableTracer();
        if (enableTracer) {
            c.a(this.a, "patracepv", null);
        }
        PATraceListener pATraceListener = this.b;
        if (pATraceListener != null) {
            pATraceListener.onCheck(enableTracer);
        }
    }

    @Override // com.efs.sdk.pa.PAANRListener
    public final void anrStack(String str) {
        if (str != null && str.length() > 200) {
            c.a(this.a, "patrace", str);
            PATraceListener pATraceListener = this.b;
            if (pATraceListener != null) {
                pATraceListener.onAnrTrace();
            }
        }
    }

    @Override // com.efs.sdk.pa.PAANRListener
    public final void unexcept(Object obj) {
        PATraceListener pATraceListener = this.b;
        if (pATraceListener != null) {
            pATraceListener.onUnexcept(obj);
        }
    }
}
