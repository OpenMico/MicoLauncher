package com.xiaomi.miot.host.lan.impl.codec.operation;

import android.text.TextUtils;
import com.xiaomi.micolauncher.common.schema.SchemaActivity;
import com.xiaomi.miot.host.lan.impl.codec.MiotMethod;
import com.xiaomi.miot.host.lan.impl.codec.MiotRequest;
import com.xiaomi.miot.host.lan.impl.codec.MiotSupportRpcType;
import com.xiaomi.miot.typedef.property.Property;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class MiotPropertyNotify extends MiotRequest {
    private String partnerId;
    private List<Property> properties;

    public MiotPropertyNotify(String str, List<Property> list) {
        this.properties = new ArrayList();
        this.partnerId = str;
        this.properties = list;
    }

    @Override // com.xiaomi.miot.host.lan.impl.codec.MiotRequest
    public MiotMethod getMethod() {
        return MiotMethod.NOTIFY_PROPERTIES;
    }

    @Override // com.xiaomi.miot.host.lan.impl.codec.MiotRequest
    public byte[] encodeRequest() {
        JSONObject jSONObject = new JSONObject();
        try {
            if (!TextUtils.isEmpty(this.partnerId)) {
                jSONObject.put("partner_id", this.partnerId);
            }
            jSONObject.put("id", getId());
            if (MiotSupportRpcType.isMitvType()) {
                Property property = this.properties.get(0);
                jSONObject.put(SchemaActivity.KEY_METHOD, "prop." + property.getDefinition().getType().getSubType());
                JSONArray jSONArray = new JSONArray();
                jSONArray.put(property.getCurrentValue().getObjectValue());
                jSONObject.put("params", jSONArray);
            } else {
                jSONObject.put(SchemaActivity.KEY_METHOD, "props");
                JSONObject jSONObject2 = new JSONObject();
                for (Property property2 : this.properties) {
                    jSONObject2.put(property2.getDefinition().getType().getSubType(), property2.getCurrentValue().getObjectValue());
                }
                jSONObject.put("params", jSONObject2);
            }
            return jSONObject.toString().getBytes();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override // com.xiaomi.miot.host.lan.impl.codec.MiotRequest
    public byte[] encodeResponse() {
        return new byte[0];
    }
}
