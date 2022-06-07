package androidx.transition;

import android.util.SparseArray;
import android.view.View;
import androidx.collection.ArrayMap;
import androidx.collection.LongSparseArray;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: TransitionValuesMaps.java */
/* loaded from: classes.dex */
public class q {
    final ArrayMap<View, TransitionValues> a = new ArrayMap<>();
    final SparseArray<View> b = new SparseArray<>();
    final LongSparseArray<View> c = new LongSparseArray<>();
    final ArrayMap<String, View> d = new ArrayMap<>();
}
