package com.xiaomi.micolauncher.module.miot.defined.property;

import android.util.Log;
import com.xiaomi.micolauncher.api.model.Remote;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.stat.StatPoints;
import com.xiaomi.micolauncher.module.miot.defined.SpeakerDefined;
import com.xiaomi.micolauncher.skills.music.PlayerApi;
import com.xiaomi.miot.typedef.data.DataType;
import com.xiaomi.miot.typedef.data.value.Vstring;
import com.xiaomi.miot.typedef.property.AccessType;
import com.xiaomi.miot.typedef.property.PropertyDefinition;
import com.xiaomi.miot.typedef.urn.PropertyType;

@Deprecated
/* loaded from: classes3.dex */
public class SpeakerRate extends MicoPropertyOperable<Vstring> {
    public static PropertyType TYPE = SpeakerDefined.Property.SpeakerRate.toPropertyType();
    public static AccessType PERMISSIONS = AccessType.valueOf(3);
    private static DataType a = DataType.INT;

    @Override // com.xiaomi.micolauncher.module.miot.defined.property.MicoPropertyOperable
    protected int instanceID() {
        return 3;
    }

    @Override // com.xiaomi.micolauncher.module.miot.defined.property.MicoPropertyOperable
    protected int siid() {
        return 2;
    }

    public SpeakerRate() {
        super(new PropertyDefinition(TYPE, PERMISSIONS, a));
    }

    public int getValue() {
        Remote.Response.PlayerStatus playerStatus = PlayerApi.getPlayerStatus();
        Log.d("[SpeakerRate]: ", "Get SpeakerRate value: " + playerStatus.status);
        return playerStatus.status == 1 ? 1 : 0;
    }

    public void setValue(int i) {
        L.miot.d("%s Set SpeakerRate value=%s", "[SpeakerRate]: ", Integer.valueOf(i));
        if (i == 1) {
            PlayerApi.playOrPlayRecommend();
            StatPoints.recordPoint(StatPoints.Event.miio, StatPoints.MiotPluginAutoMation.play_music, "1");
        } else if (i == 0) {
            PlayerApi.pause();
            StatPoints.recordPoint(StatPoints.Event.miio, StatPoints.MiotPluginAutoMation.play_pause, "1");
        } else {
            Log.e("[SpeakerRate]: ", "Not support this play_state: " + i);
        }
    }
}
