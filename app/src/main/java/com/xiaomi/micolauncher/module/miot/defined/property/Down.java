package com.xiaomi.micolauncher.module.miot.defined.property;

import android.text.TextUtils;
import android.util.Log;
import com.xiaomi.micolauncher.module.miot.defined.SpeakerDefined;
import com.xiaomi.micolauncher.module.setting.utils.SystemVolume;
import com.xiaomi.miot.typedef.data.DataType;
import com.xiaomi.miot.typedef.data.value.Vstring;
import com.xiaomi.miot.typedef.property.AccessType;
import com.xiaomi.miot.typedef.property.PropertyDefinition;
import com.xiaomi.miot.typedef.urn.PropertyType;

/* loaded from: classes3.dex */
public class Down extends MicoPropertyOperable<Vstring> {
    public static PropertyType TYPE = SpeakerDefined.Property.Down.toPropertyType();
    public static AccessType PERMISSIONS = AccessType.valueOf(3);
    private static DataType a = DataType.STRING;

    @Override // com.xiaomi.micolauncher.module.miot.defined.property.MicoPropertyOperable
    protected int instanceID() {
        return 5;
    }

    @Override // com.xiaomi.micolauncher.module.miot.defined.property.MicoPropertyOperable
    protected int siid() {
        return 2;
    }

    public Down() {
        super(new PropertyDefinition(TYPE, PERMISSIONS, a));
    }

    public String getValue() {
        Log.d("[Down]: ", "! getvalue() !");
        return ((Vstring) super.getCurrentValue()).getValue();
    }

    public void setValue(String str) {
        if (TextUtils.isEmpty(str)) {
            Log.e("[Down]: ", "Set Down value is Null !!");
        } else {
            SystemVolume.getInstance().downVolume();
        }
    }
}
