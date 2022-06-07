package com.xiaomi.micolauncher.skills.music.model.lrc;

import android.text.TextUtils;
import android.util.LruCache;
import com.elvishew.xlog.Logger;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.api.model.Remote;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.skills.music.model.cache.LrcCache;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.lang.ref.WeakReference;
import java.util.List;

/* loaded from: classes3.dex */
public class LrcManager {
    private static volatile LrcManager a;
    private LruCache<String, List<LrcRow>> b = new LruCache<>(3);
    private int c;
    private long d;
    private String e;
    private Disposable f;

    /* loaded from: classes3.dex */
    public interface OnLrcChangeListener {
        void onLrcRowsChange(String str, List<LrcRow> list);
    }

    private LrcManager() {
    }

    public static LrcManager getInstance() {
        if (a == null) {
            synchronized (LrcManager.class) {
                if (a == null) {
                    a = new LrcManager();
                }
            }
        }
        return a;
    }

    public List<LrcRow> getLrcRows(String str) {
        return this.b.get(str);
    }

    public void loadLrc(Remote.Response.PlayingData playingData, OnLrcChangeListener onLrcChangeListener) {
        if (playingData == null) {
            L.player.d("playingData in null");
            return;
        }
        final String simpleId = playingData.getSimpleId();
        if (playingData.screenExtend.mediaType == 21) {
            if (!TextUtils.isEmpty(playingData.lrc)) {
                onLrcChangeListener.onLrcRowsChange(simpleId, new LrcParseImpl().getLrcRows(playingData.lrc));
            } else {
                onLrcChangeListener.onLrcRowsChange(simpleId, null);
            }
        } else if (!TextUtils.isEmpty(simpleId)) {
            List<LrcRow> lrcRows = getLrcRows(simpleId);
            if (ContainerUtil.hasData(lrcRows)) {
                onLrcChangeListener.onLrcRowsChange(simpleId, lrcRows);
                return;
            }
            Disposable disposable = this.f;
            if (disposable != null && !disposable.isDisposed()) {
                this.f.dispose();
                this.f = null;
            }
            final WeakReference weakReference = new WeakReference(onLrcChangeListener);
            this.f = LrcCache.getLrcRow(simpleId).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.model.lrc.-$$Lambda$LrcManager$i8DVma1HXbhOuDMUqlenOnlqdAU
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    LrcManager.this.a(simpleId, weakReference, (List) obj);
                }
            }, new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.model.lrc.-$$Lambda$LrcManager$hf1rUGuY_5D0rWr8W_C0Q8Bu8X0
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    LrcManager.a(weakReference, simpleId, (Throwable) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(String str, WeakReference weakReference, List list) throws Exception {
        Logger logger = L.player;
        logger.d("load lyric success, musicId = " + str);
        if (ContainerUtil.hasData(list)) {
            this.b.put(str, list);
        }
        OnLrcChangeListener onLrcChangeListener = (OnLrcChangeListener) weakReference.get();
        if (onLrcChangeListener != null) {
            onLrcChangeListener.onLrcRowsChange(str, list);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(WeakReference weakReference, String str, Throwable th) throws Exception {
        L.player.e("load lyric error", th);
        OnLrcChangeListener onLrcChangeListener = (OnLrcChangeListener) weakReference.get();
        if (onLrcChangeListener != null) {
            onLrcChangeListener.onLrcRowsChange(str, null);
        }
    }

    public String loadLrcRow(String str, long j) {
        List<LrcRow> list = this.b.get(str);
        if (ContainerUtil.isEmpty(list)) {
            return null;
        }
        int i = 0;
        if (str.equals(this.e) && j > this.d) {
            i = this.c;
        }
        while (i < list.size()) {
            if (j >= list.get(i).getTimeMillis()) {
                if (this.c != i) {
                    this.e = str;
                    this.d = j;
                    this.c = i;
                }
                return list.get(i).getContent();
            }
            i++;
        }
        return "";
    }

    public void clearLrc() {
        L.player.i("LrcManager clearLrc");
        Disposable disposable = this.f;
        if (disposable != null && !disposable.isDisposed()) {
            this.f.dispose();
            this.f = null;
        }
    }
}
