package com.xiaomi.micolauncher.module.miot.defined.property;

import android.util.Log;
import com.xiaomi.micolauncher.module.miot.defined.SpeakerDefined;
import com.xiaomi.miot.typedef.data.DataType;
import com.xiaomi.miot.typedef.data.value.Vint;
import com.xiaomi.miot.typedef.data.value.Vstring;
import com.xiaomi.miot.typedef.property.AccessType;
import com.xiaomi.miot.typedef.property.PropertyDefinition;
import com.xiaomi.miot.typedef.urn.PropertyType;

/* loaded from: classes3.dex */
public class TtsReply extends MicoPropertyOperable<Vstring> {
    public static PropertyType TYPE = SpeakerDefined.Property.TtsReply.toPropertyType();
    public static AccessType PERMISSIONS = AccessType.valueOf(3);
    private static DataType a = DataType.INT;

    @Override // com.xiaomi.micolauncher.module.miot.defined.property.MicoPropertyOperable
    protected int instanceID() {
        return 2;
    }

    @Override // com.xiaomi.micolauncher.module.miot.defined.property.MicoPropertyOperable
    protected int siid() {
        return 3;
    }

    public TtsReply() {
        super(new PropertyDefinition(TYPE, PERMISSIONS, a));
    }

    public int getValue() {
        Log.d("[TtsReply]: ", "getValue !!");
        return ((Vint) super.getCurrentValue()).getValue();
    }

    public void setValue(int i) {
        Log.d("[TtsReply]: ", "setValue !!");
        super.setDataValue(new Vint(i));
    }
}
