package com.efs.sdk.base.protocol;

/* loaded from: classes.dex */
public abstract class AbsLog implements ILogProtocol {
    private String cp = "none";
    private byte de = 1;
    private String logType;

    public AbsLog(String str) {
        this.logType = str;
    }

    @Override // com.efs.sdk.base.protocol.ILogProtocol
    public String getLogType() {
        return this.logType;
    }

    public void setCp(String str) {
        this.cp = str;
    }

    public void setDe(byte b) {
        this.de = b;
    }

    public boolean isCp() {
        return !this.cp.equals("none");
    }

    public boolean isDe() {
        return this.de != 1;
    }
}
