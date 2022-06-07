package com.bumptech.glide.load.engine;

import com.bumptech.glide.load.Key;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: EngineJobListener.java */
/* loaded from: classes.dex */
public interface i {
    void onEngineJobCancelled(h<?> hVar, Key key);

    void onEngineJobComplete(h<?> hVar, Key key, l<?> lVar);
}
