package com.xiaomi.smarthome.core.entity;

import com.xiaomi.micolauncher.skills.music.model.PlayerEvent;
import kotlin.Metadata;

/* compiled from: SmartHomeBeans.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\b\n\u0002\b\t\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000b¨\u0006\f"}, d2 = {"Lcom/xiaomi/smarthome/core/entity/MediaType;", "", "type", "", "(Ljava/lang/String;II)V", "getType", "()I", "NONE", "LOCAL", "MIPLAY", PlayerEvent.MUSIC_SOURCE_BT, "RELAY", "smarthome_release"}, k = 1, mv = {1, 4, 2})
/* loaded from: classes4.dex */
public enum MediaType {
    NONE(0),
    LOCAL(1),
    MIPLAY(2),
    BT(3),
    RELAY(4);
    
    private final int type;

    MediaType(int i) {
        this.type = i;
    }

    public final int getType() {
        return this.type;
    }
}
