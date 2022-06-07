package com.xiaomi.smarthome.ui.media;

import com.xiaomi.smarthome.core.entity.MicoMediaData;
import java.util.List;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: RelayMediaListener.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H&J\u0010\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\tH&J\u0012\u0010\n\u001a\u00020\u00032\b\u0010\u000b\u001a\u0004\u0018\u00010\u0006H&¨\u0006\f"}, d2 = {"Lcom/xiaomi/smarthome/ui/media/RelayMediaListener;", "", "onDataChanged", "", "list", "", "Lcom/xiaomi/smarthome/core/entity/MicoMediaData;", "onDataSizeChange", "size", "", "onFirstDataChanged", "data", "smarthome_release"}, k = 1, mv = {1, 4, 2})
/* loaded from: classes4.dex */
public interface RelayMediaListener {
    void onDataChanged(@NotNull List<MicoMediaData> list);

    void onDataSizeChange(int i);

    void onFirstDataChanged(@Nullable MicoMediaData micoMediaData);
}
