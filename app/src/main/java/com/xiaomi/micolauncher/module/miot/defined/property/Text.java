package com.xiaomi.micolauncher.module.miot.defined.property;

import com.xiaomi.micolauncher.module.miot.defined.SpeakerDefined;
import com.xiaomi.miot.typedef.data.DataType;
import com.xiaomi.miot.typedef.data.value.Vstring;
import com.xiaomi.miot.typedef.property.AccessType;
import com.xiaomi.miot.typedef.property.PropertyDefinition;
import com.xiaomi.miot.typedef.urn.PropertyType;

/* loaded from: classes3.dex */
public class Text extends MicoPropertyOperable<Vstring> {
    public static PropertyType TYPE = SpeakerDefined.Property.Text.toPropertyType();
    public static AccessType PERMISSIONS = AccessType.valueOf(0);
    private static DataType a = DataType.STRING;

    @Override // com.xiaomi.micolauncher.module.miot.defined.property.MicoPropertyOperable
    protected int instanceID() {
        return 1;
    }

    @Override // com.xiaomi.micolauncher.module.miot.defined.property.MicoPropertyOperable
    protected int siid() {
        return 7;
    }

    public Text() {
        super(new PropertyDefinition(TYPE, PERMISSIONS, a));
    }

    public String getValue() {
        return ((Vstring) super.getCurrentValue()).getValue();
    }

    public void setValue(String str) {
        super.setDataValue(new Vstring(str));
    }
}
