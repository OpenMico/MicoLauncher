package com.xiaomi.smarthome.core.entity;

import com.xiaomi.onetrack.api.b;
import com.xiaomi.smarthome.library.apache.http.protocol.HTTP;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

/* compiled from: SmartHomeBeans.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u000b\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u001f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\bj\u0002\b\u000bj\u0002\b\fj\u0002\b\r¨\u0006\u000e"}, d2 = {"Lcom/xiaomi/smarthome/core/entity/CurtainOperation;", "", b.p, "", "description", "format", "(Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getDescription", "()Ljava/lang/String;", "getFormat", "getValue", "PAUSE", "OPEN", "CLOSE", "smarthome_release"}, k = 1, mv = {1, 4, 2})
/* loaded from: classes4.dex */
public enum CurtainOperation {
    PAUSE("0", "Pause", "int"),
    OPEN("1", "Open", "int"),
    CLOSE("2", HTTP.CONN_CLOSE, "int");
    
    @NotNull
    private final String description;
    @NotNull
    private final String format;
    @NotNull
    private final String value;

    CurtainOperation(String str, String str2, String str3) {
        this.value = str;
        this.description = str2;
        this.format = str3;
    }

    @NotNull
    public final String getDescription() {
        return this.description;
    }

    @NotNull
    public final String getFormat() {
        return this.format;
    }

    @NotNull
    public final String getValue() {
        return this.value;
    }
}
