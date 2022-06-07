package com.xiaomi.miot.host.lan.impl.codec.operation;

import android.text.TextUtils;
import com.xiaomi.micolauncher.common.schema.SchemaActivity;
import com.xiaomi.miot.host.lan.impl.codec.MiotMethod;
import com.xiaomi.miot.host.lan.impl.codec.MiotRequest;
import com.xiaomi.miot.typedef.device.Event;
import com.xiaomi.miot.typedef.property.Property;
import com.xiaomi.onetrack.api.b;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class MiotSpecV2EventNotify extends MiotRequest {
    public static final String MIOT_SPEC_V2_REQUEST_METHOD = "event_occured";
    private String did;
    private Event event;
    private String partnerId;
    private List<Property> properties;

    public MiotSpecV2EventNotify(String str, String str2, Event event) {
        this.properties = new ArrayList();
        this.did = str;
        this.partnerId = str2;
        this.event = event;
        if (event != null) {
            this.properties = event.getArguments();
        }
    }

    @Override // com.xiaomi.miot.host.lan.impl.codec.MiotRequest
    public MiotMethod getMethod() {
        return MiotMethod.MIOT_SPEC_V2_NOTIFY_EVENT;
    }

    @Override // com.xiaomi.miot.host.lan.impl.codec.MiotRequest
    public byte[] encodeRequest() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("id", getId());
            jSONObject.put(SchemaActivity.KEY_METHOD, MIOT_SPEC_V2_REQUEST_METHOD);
            if (!TextUtils.isEmpty(this.partnerId)) {
                jSONObject.put("partner_id", this.partnerId);
            }
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("did", this.did);
            jSONObject2.put("siid", this.event.getServiceInstanceID());
            jSONObject2.put("eiid", this.event.getInstanceID());
            JSONArray jSONArray = new JSONArray();
            if (this.properties != null) {
                for (Property property : this.properties) {
                    JSONObject jSONObject3 = new JSONObject();
                    jSONObject3.put("piid", property.getInstanceID());
                    jSONObject3.put(b.p, property.getCurrentValue().getObjectValue());
                    jSONArray.put(jSONObject3);
                }
            }
            jSONObject2.put("arguments", jSONArray);
            jSONObject.put("params", jSONObject2);
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
