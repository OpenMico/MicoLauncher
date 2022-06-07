package com.efs.sdk.base.a.i;

import com.efs.sdk.base.a.a.a;
import com.efs.sdk.base.a.h.d;
import com.efs.sdk.base.protocol.record.AbsRecordLog;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

/* loaded from: classes.dex */
public final class b extends AbsRecordLog {
    private String a;
    private String b;
    private String c;
    private String d;

    @Override // com.efs.sdk.base.protocol.ILogProtocol
    public final String getLinkId() {
        return "";
    }

    @Override // com.efs.sdk.base.protocol.ILogProtocol
    public final String getLinkKey() {
        return "";
    }

    public b(String str, String str2, String str3) {
        super("wa");
        this.a = str;
        this.b = str2;
        this.d = str3;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS", Locale.CHINA);
        a.a();
        this.c = simpleDateFormat.format(new Date(a.b()));
    }

    @Override // com.efs.sdk.base.protocol.ILogProtocol
    public final void insertGlobal(com.efs.sdk.base.a.c.b bVar) {
        this.dataMap.putAll(bVar.a());
        this.dataMap.putAll(com.efs.sdk.base.a.d.a.a().a());
    }

    @Override // com.efs.sdk.base.protocol.ILogProtocol
    public final byte[] generate() {
        String generateString = generateString();
        if (com.efs.sdk.base.a.d.a.a().g) {
            d.a("efs.base", generateString);
        }
        return generateString.getBytes();
    }

    @Override // com.efs.sdk.base.protocol.ILogProtocol
    public final String generateString() {
        StringBuilder sb = new StringBuilder();
        sb.append("lt=event`");
        sb.append("ev_ct=");
        sb.append(this.a);
        sb.append("`");
        sb.append("ev_ac=");
        sb.append(this.b);
        sb.append("`");
        sb.append("tm=");
        sb.append(this.c);
        sb.append("`");
        sb.append("dn=");
        sb.append(this.d);
        sb.append("`");
        for (Map.Entry entry : this.dataMap.entrySet()) {
            sb.append((String) entry.getKey());
            sb.append("=");
            sb.append(entry.getValue());
            sb.append("`");
        }
        return sb.subSequence(0, sb.length() - 1).toString();
    }
}
