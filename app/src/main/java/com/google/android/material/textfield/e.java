package com.google.android.material.textfield;

import android.content.Context;
import androidx.annotation.NonNull;
import com.google.android.material.internal.CheckableImageButton;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: EndIconDelegate.java */
/* loaded from: classes2.dex */
public abstract class e {
    TextInputLayout a;
    Context b;
    CheckableImageButton c;

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract void a();

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(boolean z) {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean a(int i) {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean b() {
        return false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public e(@NonNull TextInputLayout textInputLayout) {
        this.a = textInputLayout;
        this.b = textInputLayout.getContext();
        this.c = textInputLayout.getEndIconView();
    }
}
