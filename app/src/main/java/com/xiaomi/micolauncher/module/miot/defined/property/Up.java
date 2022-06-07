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
public class Up extends MicoPropertyOperable<Vstring> {
    public static PropertyType TYPE = SpeakerDefined.Property.Up.toPropertyType();
    public static AccessType PERMISSIONS = AccessType.valueOf(3);
    private static DataType a = DataType.STRING;

    @Override // com.xiaomi.micolauncher.module.miot.defined.property.MicoPropertyOperable
    protected int instanceID() {
        return 4;
    }

    @Override // com.xiaomi.micolauncher.module.miot.defined.property.MicoPropertyOperable
    protected int siid() {
        return 2;
    }

    public Up() {
        super(new PropertyDefinition(TYPE, PERMISSIONS, a));
    }

    public String getValue() {
        Log.d("[Up]: ", "! getvalue() !");
        return ((Vstring) super.getCurrentValue()).getValue();
    }

    public void setValue(String str) {
        if (TextUtils.isEmpty(str)) {
            Log.e("[Up]: ", "Set Up value is Null !!");
        } else {
            SystemVolume.getInstance().upVolume();
        }
    }
}
