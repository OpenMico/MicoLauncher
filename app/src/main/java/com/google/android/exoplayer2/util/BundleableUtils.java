package com.google.android.exoplayer2.util;

import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Bundleable;
import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public final class BundleableUtils {
    @Nullable
    public static Bundle toNullableBundle(@Nullable Bundleable bundleable) {
        if (bundleable == null) {
            return null;
        }
        return bundleable.toBundle();
    }

    @Nullable
    public static <T extends Bundleable> T fromNullableBundle(Bundleable.Creator<T> creator, @Nullable Bundle bundle) {
        if (bundle == null) {
            return null;
        }
        return creator.fromBundle(bundle);
    }

    public static <T extends Bundleable> T fromNullableBundle(Bundleable.Creator<T> creator, @Nullable Bundle bundle, T t) {
        return bundle == null ? t : creator.fromBundle(bundle);
    }

    public static <T extends Bundleable> ImmutableList<Bundle> toBundleList(List<T> list) {
        ImmutableList.Builder builder = ImmutableList.builder();
        for (int i = 0; i < list.size(); i++) {
            builder.add((ImmutableList.Builder) list.get(i).toBundle());
        }
        return builder.build();
    }

    public static <T extends Bundleable> ImmutableList<T> fromBundleList(Bundleable.Creator<T> creator, List<Bundle> list) {
        ImmutableList.Builder builder = ImmutableList.builder();
        for (int i = 0; i < list.size(); i++) {
            builder.add((ImmutableList.Builder) creator.fromBundle(list.get(i)));
        }
        return builder.build();
    }

    public static <T extends Bundleable> ArrayList<Bundle> toBundleArrayList(List<T> list) {
        ArrayList<Bundle> arrayList = new ArrayList<>(list.size());
        for (int i = 0; i < list.size(); i++) {
            arrayList.add(list.get(i).toBundle());
        }
        return arrayList;
    }

    private BundleableUtils() {
    }
}
