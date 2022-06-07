package com.xiaomi.micolauncher.module.miot.defined.property;

import com.xiaomi.micolauncher.module.miot.defined.SpeakerDefined;
import com.xiaomi.miot.typedef.data.DataType;
import com.xiaomi.miot.typedef.data.value.Vstring;
import com.xiaomi.miot.typedef.property.AccessType;
import com.xiaomi.miot.typedef.property.PropertyDefinition;
import com.xiaomi.miot.typedef.urn.PropertyType;

/* loaded from: classes3.dex */
public class DoorbellTimeStamp extends MicoPropertyOperable<Vstring> {
    public static PropertyType TYPE = SpeakerDefined.Property.DoorbellTimeStamp.toPropertyType();
    public static AccessType PERMISSIONS = AccessType.valueOf(3);
    private static DataType a = DataType.STRING;

    @Override // com.xiaomi.micolauncher.module.miot.defined.property.MicoPropertyOperable
    protected int instanceID() {
        return 3;
    }

    @Override // com.xiaomi.micolauncher.module.miot.defined.property.MicoPropertyOperable
    protected int siid() {
        return 1;
    }

    public DoorbellTimeStamp() {
        super(new PropertyDefinition(TYPE, PERMISSIONS, a));
    }

    public String getValue() {
        return ((Vstring) super.getCurrentValue()).getValue();
    }

    public void setValue(String str) {
        super.setDataValue(new Vstring(str));
    }
}
