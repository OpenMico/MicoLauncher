package com.xiaomi.miplay.mylibrary.session;

import android.content.Context;
import android.content.pm.PackageManager;
import android.media.MediaMetadata;
import android.media.session.MediaController;
import android.media.session.MediaSessionManager;
import android.media.session.PlaybackState;
import android.text.TextUtils;
import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.xiaomi.miplay.mylibrary.session.data.AppMetaData;
import com.xiaomi.miplay.mylibrary.session.data.MediaMetaData;
import com.xiaomi.miplay.mylibrary.session.utils.Logger;
import com.xiaomi.miplay.mylibrary.session.utils.MediaControllerDiffUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class ActiveAudioSessionManager {
    @NonNull
    private final MediaSessionManager a;
    @NonNull
    private final PackageManager b;
    private Context g;
    @NonNull
    private final Object d = new Object();
    @NonNull
    @GuardedBy("mLock")
    private final ActiveSessionRecordStack e = new ActiveSessionRecordStack();
    @NonNull
    @GuardedBy("mLock")
    private final List<ActiveAudioSessionCallback> f = new ArrayList();
    @NonNull
    private final a c = new a();

    /* loaded from: classes4.dex */
    public interface ActiveAudioSessionCallback {
        void dispatchMetaChange(MediaMetaData mediaMetaData, String str);

        void dispatchPlaybackStateChange(int i);

        default void onActiveSessionChange(@NonNull ActiveSessionRecordStack activeSessionRecordStack) {
        }

        default void onTopActiveMediaMetaChange(@NonNull MediaMetaData mediaMetaData) {
        }

        default void onTopActivePlaybackStateChange(int i) {
        }

        void onTopActiveSessionChange(@Nullable ActiveSessionRecord activeSessionRecord);
    }

    public ActiveAudioSessionManager(Context context, ActiveAudioSessionCallback activeAudioSessionCallback) {
        Logger.d("ActiveAudioSessionManager", "context:" + context, new Object[0]);
        this.g = context;
        this.b = context.getPackageManager();
        this.a = (MediaSessionManager) context.getSystemService("media_session");
        this.a.addOnActiveSessionsChangedListener(this.c, null);
        this.f.add(activeAudioSessionCallback);
        a(this.a.getActiveSessions(null));
    }

    public void release() {
        this.a.removeOnActiveSessionsChangedListener(this.c);
        synchronized (this.d) {
            this.f.clear();
        }
    }

    public void a(@Nullable List<MediaController> list) {
        boolean z = false;
        Logger.d("ActiveAudioSessionManager", "handleOnActiveSessionsChanged.", new Object[0]);
        ActiveSessionRecord topAudioSession = this.e.getTopAudioSession();
        String str = null;
        String packageName = topAudioSession == null ? null : topAudioSession.getPackageName();
        synchronized (this.d) {
            ArrayList<MediaController> arrayList = new ArrayList();
            ArrayList<MediaController> arrayList2 = new ArrayList();
            MediaControllerDiffUtils.diffExistLocked(list, a(), arrayList, arrayList2);
            if (!arrayList.isEmpty()) {
                for (MediaController mediaController : arrayList) {
                    ActiveSessionRecord findByMediaController = this.e.findByMediaController(mediaController);
                    if (findByMediaController != null) {
                        findByMediaController.release();
                        this.e.remove(findByMediaController);
                        Logger.d("ActiveAudioSessionManager", "-----remove----", new Object[0]);
                    }
                }
                z = true;
            }
            if (!arrayList2.isEmpty()) {
                for (MediaController mediaController2 : arrayList2) {
                    String packageName2 = mediaController2.getPackageName();
                    this.e.add(new ActiveSessionRecord(this, mediaController2, new AppMetaData(packageName2, a(packageName2)), this.g));
                }
                z = true;
            }
        }
        ActiveSessionRecord topAudioSession2 = this.e.getTopAudioSession();
        if (z) {
            if (topAudioSession2 != null) {
                str = topAudioSession2.getPackageName();
            }
            boolean z2 = !TextUtils.equals(packageName, str);
            synchronized (this.d) {
                b();
                if (z2) {
                    c();
                }
            }
        }
    }

    @Nullable
    public ActiveSessionRecord getTopActiveSessionRecord() {
        return this.e.getTopAudioSession();
    }

    public void a(@NonNull ActiveSessionRecord activeSessionRecord, @Nullable PlaybackState playbackState) {
        if (playbackState != null) {
            synchronized (this.d) {
                if (this.e.onPlaybackStateChange(activeSessionRecord, playbackState.getState())) {
                    c();
                }
                if (activeSessionRecord == this.e.getTopAudioSession()) {
                    Logger.d("ActiveAudioSessionManager", "TopAudioSession play change", new Object[0]);
                    a(playbackState.getState());
                }
            }
        }
    }

    public void onPlaybackStateChanged(int i) {
        for (int size = this.f.size() - 1; size >= 0; size--) {
            this.f.get(size).dispatchPlaybackStateChange(i);
        }
    }

    public void onMetadataChanged(MediaMetaData mediaMetaData, String str) {
        for (int size = this.f.size() - 1; size >= 0; size--) {
            this.f.get(size).dispatchMetaChange(mediaMetaData, str);
        }
    }

    public void a(@NonNull ActiveSessionRecord activeSessionRecord, MediaMetadata mediaMetadata) {
        synchronized (this.d) {
            if (this.e.getTopAudioSession() == activeSessionRecord) {
                c();
            }
        }
    }

    private int a(String str) {
        try {
            return this.b.getPackageUid(str, 0);
        } catch (PackageManager.NameNotFoundException e) {
            Logger.e("ActiveAudioSessionManager", "getUid, package: " + str, e);
            return -1;
        }
    }

    private List<MediaController> a() {
        ArrayList arrayList = new ArrayList();
        Iterator it = this.e.iterator();
        while (it.hasNext()) {
            arrayList.add(((ActiveSessionRecord) it.next()).getMediaController());
        }
        return arrayList;
    }

    private void b() {
        Logger.d("ActiveAudioSessionManager", "dispatchActiveSessionChangeCallbackLocked, size:" + this.f.size(), new Object[0]);
        for (int size = this.f.size() + (-1); size >= 0; size--) {
            this.f.get(size).onActiveSessionChange(this.e);
        }
    }

    private void a(int i) {
        for (int size = this.f.size() - 1; size >= 0; size--) {
            this.f.get(size).onTopActivePlaybackStateChange(i);
        }
    }

    private void c() {
        Logger.d("ActiveAudioSessionManager", "dispatchTopActiveSessionChangeCallbackLocked, size:" + this.f.size(), new Object[0]);
        for (int size = this.f.size() + (-1); size >= 0; size--) {
            this.f.get(size).onTopActiveSessionChange(this.e.getTopAudioSession());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public final class a implements MediaSessionManager.OnActiveSessionsChangedListener {
        private a() {
            ActiveAudioSessionManager.this = r1;
        }

        @Override // android.media.session.MediaSessionManager.OnActiveSessionsChangedListener
        public void onActiveSessionsChanged(@Nullable List<MediaController> list) {
            Logger.d("ActiveAudioSessionManager", "onActiveSessionsChanged.", new Object[0]);
            ActiveAudioSessionManager.this.a(list);
        }
    }
}
