package com.xiaomi.micolauncher.module.miot.defined.property;

import com.xiaomi.micolauncher.module.miot.defined.SpeakerDefined;
import com.xiaomi.miot.typedef.data.DataType;
import com.xiaomi.miot.typedef.data.value.Vbool;
import com.xiaomi.miot.typedef.property.AccessType;
import com.xiaomi.miot.typedef.property.PropertyDefinition;
import com.xiaomi.miot.typedef.urn.PropertyType;

/* loaded from: classes3.dex */
public class SilentExecution extends MicoPropertyOperable<Vbool> {
    public static PropertyType TYPE = SpeakerDefined.Property.SilentExecution.toPropertyType();
    public static AccessType PERMISSIONS = AccessType.valueOf(3);
    private static DataType a = DataType.BOOL;

    @Override // com.xiaomi.micolauncher.module.miot.defined.property.MicoPropertyOperable
    protected int instanceID() {
        return 2;
    }

    @Override // com.xiaomi.micolauncher.module.miot.defined.property.MicoPropertyOperable
    protected int siid() {
        return 7;
    }

    public SilentExecution() {
        super(new PropertyDefinition(TYPE, PERMISSIONS, a));
    }

    public boolean getValue() {
        return ((Vbool) super.getCurrentValue()).getValue();
    }

    public void setValue(boolean z) {
        super.setDataValue(new Vbool(z));
    }
}
