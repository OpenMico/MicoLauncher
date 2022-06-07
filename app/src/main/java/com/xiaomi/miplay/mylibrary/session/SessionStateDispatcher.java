package com.xiaomi.miplay.mylibrary.session;

import android.os.Handler;
import android.os.HandlerThread;
import androidx.annotation.NonNull;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public final class SessionStateDispatcher {
    private static final String a = "SessionStateDispatcher";
    private static volatile SessionStateDispatcher b;
    private final List<PlaybackStateCallback> c = new ArrayList();
    private final HandlerThread d = new HandlerThread(a);
    private final Handler e = new Handler(this.d.getLooper());
    private Reference<ActiveSessionRecordStack> f;

    /* loaded from: classes4.dex */
    public interface PlaybackStateCallback {
        void onPlaybackStateChanged(int i);
    }

    public static SessionStateDispatcher getInstance() {
        if (b == null) {
            synchronized (SessionStateDispatcher.class) {
                if (b == null) {
                    b = new SessionStateDispatcher();
                }
            }
        }
        return b;
    }

    private SessionStateDispatcher() {
        this.d.start();
    }

    public final void dispatchOnPlaybackStateChanged(final int i) {
        this.e.post(new Runnable() { // from class: com.xiaomi.miplay.mylibrary.session.SessionStateDispatcher.1
            @Override // java.lang.Runnable
            public void run() {
                for (int size = SessionStateDispatcher.this.c.size() - 1; size >= 0; size--) {
                    ((PlaybackStateCallback) SessionStateDispatcher.this.c.get(size)).onPlaybackStateChanged(i);
                }
            }
        });
    }

    public final void dispatchOnActiveSessionChanged(@NonNull ActiveSessionRecordStack activeSessionRecordStack) {
        this.f = new WeakReference(activeSessionRecordStack);
    }

    public void addPlaybackStateCallback(@NonNull final PlaybackStateCallback playbackStateCallback) {
        this.e.post(new Runnable() { // from class: com.xiaomi.miplay.mylibrary.session.SessionStateDispatcher.2
            @Override // java.lang.Runnable
            public void run() {
                if (!SessionStateDispatcher.this.c.contains(playbackStateCallback)) {
                    SessionStateDispatcher.this.c.add(playbackStateCallback);
                }
            }
        });
    }

    public void removePlaybackStateCallback(@NonNull final PlaybackStateCallback playbackStateCallback) {
        this.e.post(new Runnable() { // from class: com.xiaomi.miplay.mylibrary.session.SessionStateDispatcher.3
            @Override // java.lang.Runnable
            public void run() {
                if (SessionStateDispatcher.this.c.contains(playbackStateCallback)) {
                    SessionStateDispatcher.this.c.remove(playbackStateCallback);
                }
            }
        });
    }

    private ActiveSessionRecord a() {
        ActiveSessionRecordStack activeSessionRecordStack;
        Reference<ActiveSessionRecordStack> reference = this.f;
        if (reference == null || (activeSessionRecordStack = reference.get()) == null) {
            return null;
        }
        return activeSessionRecordStack.getTopAudioSession();
    }

    public boolean hasActiveAudioSession() {
        return a() != null;
    }

    public int getPlayState() {
        ActiveSessionRecord a2 = a();
        if (a2 == null) {
            return 0;
        }
        return a2.getPlaybackState();
    }
}
