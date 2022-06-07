package com.google.android.material.textfield;

import androidx.annotation.NonNull;

/* compiled from: CustomEndIconDelegate.java */
/* loaded from: classes2.dex */
class b extends e {
    /* JADX INFO: Access modifiers changed from: package-private */
    public b(@NonNull TextInputLayout textInputLayout) {
        super(textInputLayout);
    }

    @Override // com.google.android.material.textfield.e
    void a() {
        this.a.setEndIconOnClickListener(null);
        this.a.setEndIconOnLongClickListener(null);
    }
}
