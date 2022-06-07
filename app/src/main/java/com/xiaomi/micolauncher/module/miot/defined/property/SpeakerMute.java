package com.xiaomi.micolauncher.module.miot.defined.property;

import android.util.Log;
import com.xiaomi.micolauncher.api.model.Remote;
import com.xiaomi.micolauncher.module.miot.defined.SpeakerDefined;
import com.xiaomi.micolauncher.skills.music.PlayerApi;
import com.xiaomi.miot.typedef.data.DataType;
import com.xiaomi.miot.typedef.data.value.Vstring;
import com.xiaomi.miot.typedef.property.AccessType;
import com.xiaomi.miot.typedef.property.PropertyDefinition;
import com.xiaomi.miot.typedef.urn.PropertyType;

/* loaded from: classes3.dex */
public class SpeakerMute extends MicoPropertyOperable<Vstring> {
    public static PropertyType TYPE = SpeakerDefined.Property.SpeakerMute.toPropertyType();
    public static AccessType PERMISSIONS = AccessType.valueOf(3);
    private static DataType a = DataType.BOOL;

    @Override // com.xiaomi.micolauncher.module.miot.defined.property.MicoPropertyOperable
    protected int instanceID() {
        return 2;
    }

    @Override // com.xiaomi.micolauncher.module.miot.defined.property.MicoPropertyOperable
    protected int siid() {
        return 2;
    }

    public SpeakerMute() {
        super(new PropertyDefinition(TYPE, PERMISSIONS, a));
    }

    public boolean getValue() {
        Remote.Response.PlayerStatus playerStatus = PlayerApi.getPlayerStatus();
        Log.d("[SpeakerMute]: ", "Get SpeakerMute value: " + playerStatus.status);
        return playerStatus.status == 1;
    }

    public void setValue(boolean z) {
        Log.i("[SpeakerMute]: ", "Set SpeakerMute value: " + z);
        if (z) {
            PlayerApi.play();
        } else {
            PlayerApi.pause();
        }
    }
}
