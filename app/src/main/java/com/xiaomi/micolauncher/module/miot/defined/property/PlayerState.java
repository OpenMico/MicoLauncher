package com.xiaomi.micolauncher.module.miot.defined.property;

import com.xiaomi.micolauncher.api.model.Remote;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.module.miot.defined.SpeakerDefined;
import com.xiaomi.micolauncher.skills.music.PlayerApi;
import com.xiaomi.miot.typedef.data.DataType;
import com.xiaomi.miot.typedef.data.value.Vint;
import com.xiaomi.miot.typedef.property.AccessType;
import com.xiaomi.miot.typedef.property.PropertyDefinition;
import com.xiaomi.miot.typedef.urn.PropertyType;

/* loaded from: classes3.dex */
public class PlayerState extends MicoPropertyOperable<Vint> {
    public static PropertyType TYPE = SpeakerDefined.Property.PlayerState.toPropertyType();
    private static AccessType a = AccessType.valueOf(2);
    private static DataType b = DataType.INT;

    @Override // com.xiaomi.micolauncher.module.miot.defined.property.MicoPropertyOperable
    protected int instanceID() {
        return 1;
    }

    @Override // com.xiaomi.micolauncher.module.miot.defined.property.MicoPropertyOperable
    protected int siid() {
        return 3;
    }

    public PlayerState() {
        super(new PropertyDefinition(TYPE, a, b));
    }

    public int getPlayerStatus() {
        Remote.Response.PlayerStatus playerStatus = PlayerApi.getPlayerStatus();
        L.miot.d("Get PlayerState status: %d", Integer.valueOf(playerStatus.status));
        return playerStatus.status;
    }
}
