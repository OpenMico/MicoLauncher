package com.efs.sdk.base.protocol.record;

import com.efs.sdk.base.Constants;
import com.efs.sdk.base.a.c.b;
import com.efs.sdk.base.a.d.a;
import com.efs.sdk.base.a.h.d;
import java.util.Map;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class EfsJSONLog extends AbsRecordLog {
    public EfsJSONLog(String str) {
        super(str);
        put("type", str);
    }

    @Override // com.efs.sdk.base.protocol.ILogProtocol
    public void insertGlobal(b bVar) {
        this.dataMap.putAll(bVar.a());
        this.dataMap.putAll(a.a().a());
    }

    @Override // com.efs.sdk.base.protocol.ILogProtocol
    public byte[] generate() {
        String generateString = generateString();
        if (a.a().g) {
            d.a("efs.base", generateString);
        }
        return generateString.getBytes();
    }

    @Override // com.efs.sdk.base.protocol.ILogProtocol
    public String generateString() {
        return new JSONObject((Map) this.dataMap).toString();
    }

    @Override // com.efs.sdk.base.protocol.ILogProtocol
    public String getLinkKey() {
        if (this.dataMap.containsKey(Constants.LOG_KEY_LINK_KEY)) {
            return String.valueOf(this.dataMap.get(Constants.LOG_KEY_LINK_KEY));
        }
        return null;
    }

    @Override // com.efs.sdk.base.protocol.ILogProtocol
    public String getLinkId() {
        if (this.dataMap.containsKey(Constants.LOG_KEY_LINK_ID)) {
            return String.valueOf(this.dataMap.get(Constants.LOG_KEY_LINK_ID));
        }
        return null;
    }
}
