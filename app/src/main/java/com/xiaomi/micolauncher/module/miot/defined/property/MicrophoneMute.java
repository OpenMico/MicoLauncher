package com.xiaomi.micolauncher.module.miot.defined.property;

import android.util.Log;
import com.elvishew.xlog.Logger;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.speech.SpeechManager;
import com.xiaomi.micolauncher.common.stat.StatPoints;
import com.xiaomi.micolauncher.module.miot.defined.SpeakerDefined;
import com.xiaomi.micolauncher.module.setting.utils.Mic;
import com.xiaomi.miot.typedef.data.DataType;
import com.xiaomi.miot.typedef.data.value.Vstring;
import com.xiaomi.miot.typedef.property.AccessType;
import com.xiaomi.miot.typedef.property.PropertyDefinition;
import com.xiaomi.miot.typedef.urn.PropertyType;

/* loaded from: classes3.dex */
public class MicrophoneMute extends MicoPropertyOperable<Vstring> {
    public static PropertyType TYPE = SpeakerDefined.Property.MicrophoneMute.toPropertyType();
    public static AccessType PERMISSIONS = AccessType.valueOf(3);
    private static DataType a = DataType.BOOL;

    @Override // com.xiaomi.micolauncher.module.miot.defined.property.MicoPropertyOperable
    protected int instanceID() {
        return 1;
    }

    @Override // com.xiaomi.micolauncher.module.miot.defined.property.MicoPropertyOperable
    protected int siid() {
        return 4;
    }

    public MicrophoneMute() {
        super(new PropertyDefinition(TYPE, PERMISSIONS, a));
    }

    public boolean getValue() {
        Log.d("[MicrophoneMute]: ", "get MicrophoneMute value: " + Mic.getInstance().isMicMute());
        return Mic.getInstance().isMicMute();
    }

    public void setValue(boolean z) {
        Log.i("[MicrophoneMute]: ", "Set MicrophoneMute value: " + z);
        if (z) {
            StatPoints.recordPoint(StatPoints.Event.miio, StatPoints.MiotPluginAutoMation.mic_off, "1");
        } else {
            StatPoints.recordPoint(StatPoints.Event.miio, StatPoints.MiotPluginAutoMation.mic_on, "1");
        }
        Logger logger = L.mic;
        logger.d("[MicrophoneMute]: setValue=" + z);
        Mic.getInstance().setMicMute(z);
        SpeechManager.getInstance().start();
    }
}
