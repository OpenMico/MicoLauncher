package com.xiaomi.micolauncher.module.miot.defined.property;

import android.util.Log;
import com.xiaomi.micolauncher.module.miot.defined.SpeakerDefined;
import com.xiaomi.micolauncher.module.setting.utils.SystemVolume;
import com.xiaomi.miot.typedef.data.DataType;
import com.xiaomi.miot.typedef.data.value.Vstring;
import com.xiaomi.miot.typedef.property.AccessType;
import com.xiaomi.miot.typedef.property.PropertyDefinition;
import com.xiaomi.miot.typedef.urn.PropertyType;

/* loaded from: classes3.dex */
public class SpeakerVolume extends MicoPropertyOperable<Vstring> {
    public static PropertyType TYPE = SpeakerDefined.Property.SpeakerVolume.toPropertyType();
    public static AccessType PERMISSIONS = AccessType.valueOf(3);
    private static DataType a = DataType.INT;

    @Override // com.xiaomi.micolauncher.module.miot.defined.property.MicoPropertyOperable
    protected int instanceID() {
        return 1;
    }

    @Override // com.xiaomi.micolauncher.module.miot.defined.property.MicoPropertyOperable
    protected int siid() {
        return 2;
    }

    public SpeakerVolume() {
        super(new PropertyDefinition(TYPE, PERMISSIONS, a));
    }

    public int getValue() {
        int volume = SystemVolume.getInstance().getVolume();
        Log.d("[SpeakerVolume]: ", "Get SpeakerVolume value: " + volume);
        return volume;
    }

    public void setValue(int i) {
        Log.i("[SpeakerVolume]: ", "Set SpeakerVolume value: " + i);
        SystemVolume.getInstance().setVolume(i);
    }
}
