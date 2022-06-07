package com.xiaomi.infra.galaxy.fds.result;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
/* loaded from: classes3.dex */
public class ApplySecretResult {
    private String a;
    private String b;

    public ApplySecretResult() {
    }

    public ApplySecretResult(String str, String str2) {
        this.a = str;
        this.b = str2;
    }

    public String getKey() {
        return this.a;
    }

    public void setKey(String str) {
        this.a = str;
    }

    public String getSecret() {
        return this.b;
    }

    public void setSecret(String str) {
        this.b = str;
    }
}
