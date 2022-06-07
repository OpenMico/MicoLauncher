package com.xiaomi.smarthome.ui.widgets;

import com.xiaomi.micolauncher.common.schema.SchemaActivity;
import kotlin.Metadata;

/* compiled from: VerticalSeekView.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0010\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0018\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u0004\u001a\u00020\u0005H&¨\u0006\n"}, d2 = {"Lcom/xiaomi/smarthome/ui/widgets/OnVolumeChangeListener;", "", "onVolumeChanged", "", SchemaActivity.KEY_VOLUME, "", "onVolumeChangedBegin", "onVolumeChangedEnd", "flag", "", "smarthome_release"}, k = 1, mv = {1, 4, 2})
/* loaded from: classes4.dex */
public interface OnVolumeChangeListener {
    void onVolumeChanged(double d);

    void onVolumeChangedBegin(double d);

    void onVolumeChangedEnd(boolean z, double d);
}
