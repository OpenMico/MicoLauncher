package miuix.arch.component;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: StandardComponentEvent.java */
/* loaded from: classes5.dex */
public enum c implements a {
    APP_ATTACH_CONTEXT,
    BEFORE_APP_CREATED,
    APP_CREATE,
    AFTER_APP_CREATED,
    BEFORE_FIRST_ACT_CREATED,
    FIRST_ACT_CREATED,
    AFTER_FIRST_ACT_CREATED,
    MAIN_HANDLER_IDLE,
    ON_REMOVE,
    CONFIG_CHANGE,
    LOW_MEMORY,
    TRIM_MEMORY;
    
    private Bundle mData;

    @Override // miuix.arch.component.a
    @NonNull
    public final String a() {
        return name();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Nullable
    public final Bundle b() {
        return this.mData;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final c a(Bundle bundle) {
        this.mData = bundle;
        return this;
    }
}
