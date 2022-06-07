package com.xiaomi.smarthome.ui.media;

import com.xiaomi.smarthome.core.entity.MicoMediaData;
import kotlin.Metadata;
import org.jetbrains.annotations.Nullable;

/* compiled from: RelayPlayCallback.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H&J\u0012\u0010\u0006\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H&¨\u0006\u0007"}, d2 = {"Lcom/xiaomi/smarthome/ui/media/RelayPlayCallback;", "", "onError", "", "data", "Lcom/xiaomi/smarthome/core/entity/MicoMediaData;", "onSuccess", "smarthome_release"}, k = 1, mv = {1, 4, 2})
/* loaded from: classes4.dex */
public interface RelayPlayCallback {
    void onError(@Nullable MicoMediaData micoMediaData);

    void onSuccess(@Nullable MicoMediaData micoMediaData);
}
