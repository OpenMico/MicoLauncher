package com.google.android.material.datepicker;

import androidx.fragment.app.Fragment;
import java.util.LinkedHashSet;

/* compiled from: PickerFragment.java */
/* loaded from: classes2.dex */
abstract class h<S> extends Fragment {
    protected final LinkedHashSet<OnSelectionChangedListener<S>> onSelectionChangedListeners = new LinkedHashSet<>();

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean addOnSelectionChangedListener(OnSelectionChangedListener<S> onSelectionChangedListener) {
        return this.onSelectionChangedListeners.add(onSelectionChangedListener);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void f() {
        this.onSelectionChangedListeners.clear();
    }
}
