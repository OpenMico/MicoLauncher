package com.xiaomi.micolauncher.module.homepage.event;

import com.xiaomi.micolauncher.api.model.Station;
import java.util.List;

/* loaded from: classes3.dex */
public class LoadHistoryEvent {
    public List<Station.Item> items;

    public LoadHistoryEvent(List<Station.Item> list) {
        this.items = list;
    }
}
