package com.xiaomi.micolauncher.skills.music.model.cache;

import android.text.TextUtils;
import android.util.LruCache;
import com.xiaomi.mico.common.Gsons;
import com.xiaomi.micolauncher.api.ApiManager;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.skills.music.model.MusicHelper;
import com.xiaomi.micolauncher.skills.music.model.api.Music;
import com.xiaomi.micolauncher.skills.music.model.lrc.LrcParseImpl;
import com.xiaomi.micolauncher.skills.music.model.lrc.LrcRow;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import java.io.ByteArrayInputStream;
import java.util.List;
import okhttp3.ResponseBody;

/* loaded from: classes3.dex */
public class LrcCache {
    private static LruCache<String, String> a = new LruCache<>(50);

    public static Observable<List<LrcRow>> getLrcRow(String str) {
        return AudioInfoCache.getAudioInfo(str).flatMap($$Lambda$LrcCache$aUoj21CfJGoTxr3FLi6pdl4BPJs.INSTANCE).map($$Lambda$LrcCache$UFN5dBvNRYNbB7UZubEajetTO4.INSTANCE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ ObservableSource c(Music.AudioInfo audioInfo) throws Exception {
        if (MusicHelper.QQ_MUSIC_CP.equalsIgnoreCase(audioInfo.cpName)) {
            return a(audioInfo);
        }
        return b(audioInfo);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ List a(String str) throws Exception {
        return new LrcParseImpl().getLrcRows(str);
    }

    private static Observable<String> a(final Music.AudioInfo audioInfo) {
        return ApiManager.minaService.getQQMusicLyric(audioInfo.cpID).doOnNext(new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.model.cache.-$$Lambda$LrcCache$XvJQb6CBbJM4aWbBl1SmO3jbahY
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                LrcCache.a(Music.AudioInfo.this, (String) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Music.AudioInfo audioInfo, String str) throws Exception {
        a.put(audioInfo.audioID, str);
    }

    private static Observable<String> b(Music.AudioInfo audioInfo) {
        if (!TextUtils.isEmpty(audioInfo.lyricURL)) {
            return ApiManager.rawService.getMusicLyric(audioInfo.lyricURL).map($$Lambda$LrcCache$AEOO4e3zWFXQ_37B7HfYkMJDe68.INSTANCE);
        }
        L.player.e("lrc url is empty: %s", Gsons.getGson().toJson(audioInfo));
        return Observable.just("");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ String a(ResponseBody responseBody) throws Exception {
        byte[] bytes = responseBody.bytes();
        return new String(bytes, a(new ByteArrayInputStream(bytes)));
    }

    /* JADX WARN: Code restructure failed: missing block: B:51:0x0080, code lost:
        r7 = r3.read();
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x0084, code lost:
        if (128 > r7) goto L_0x0092;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x0086, code lost:
        if (r7 > 191) goto L_0x0092;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x0088, code lost:
        r7 = r3.read();
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x008c, code lost:
        if (128 > r7) goto L_0x0092;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x008e, code lost:
        if (r7 > 191) goto L_0x0092;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x0090, code lost:
        r0 = "UTF-8";
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static java.lang.String a(java.io.InputStream r7) {
        /*
            java.lang.String r0 = "GBK"
            r1 = 3
            byte[] r2 = new byte[r1]
            java.io.BufferedInputStream r3 = new java.io.BufferedInputStream
            r3.<init>(r7)
            r7 = 4
            r3.mark(r7)     // Catch: Exception -> 0x009d, all -> 0x009b
            r7 = 0
            int r1 = r3.read(r2, r7, r1)     // Catch: Exception -> 0x009d, all -> 0x009b
            r4 = -1
            if (r1 != r4) goto L_0x001f
            r3.close()     // Catch: IOException -> 0x001a
            goto L_0x001e
        L_0x001a:
            r7 = move-exception
            r7.printStackTrace()
        L_0x001e:
            return r0
        L_0x001f:
            byte r1 = r2[r7]     // Catch: Exception -> 0x009d, all -> 0x009b
            r5 = -2
            r6 = 1
            if (r1 != r4) goto L_0x002c
            byte r1 = r2[r6]     // Catch: Exception -> 0x009d, all -> 0x009b
            if (r1 != r5) goto L_0x002c
            java.lang.String r0 = "UTF-16LE"
            goto L_0x004e
        L_0x002c:
            byte r1 = r2[r7]     // Catch: Exception -> 0x009d, all -> 0x009b
            if (r1 != r5) goto L_0x0037
            byte r1 = r2[r6]     // Catch: Exception -> 0x009d, all -> 0x009b
            if (r1 != r4) goto L_0x0037
            java.lang.String r0 = "UTF-16BE"
            goto L_0x004e
        L_0x0037:
            byte r1 = r2[r7]     // Catch: Exception -> 0x009d, all -> 0x009b
            r5 = -17
            if (r1 != r5) goto L_0x004d
            byte r1 = r2[r6]     // Catch: Exception -> 0x009d, all -> 0x009b
            r5 = -69
            if (r1 != r5) goto L_0x004d
            r1 = 2
            byte r1 = r2[r1]     // Catch: Exception -> 0x009d, all -> 0x009b
            r2 = -65
            if (r1 != r2) goto L_0x004d
            java.lang.String r0 = "UTF-8"
            goto L_0x004e
        L_0x004d:
            r6 = r7
        L_0x004e:
            r3.reset()     // Catch: Exception -> 0x009d, all -> 0x009b
            if (r6 != 0) goto L_0x0092
        L_0x0053:
            int r7 = r3.read()     // Catch: Exception -> 0x009d, all -> 0x009b
            if (r7 == r4) goto L_0x0092
            r1 = 240(0xf0, float:3.36E-43)
            if (r7 < r1) goto L_0x005e
            goto L_0x0092
        L_0x005e:
            r1 = 191(0xbf, float:2.68E-43)
            r2 = 128(0x80, float:1.794E-43)
            if (r2 > r7) goto L_0x0067
            if (r7 > r1) goto L_0x0067
            goto L_0x0092
        L_0x0067:
            r5 = 192(0xc0, float:2.69E-43)
            if (r5 > r7) goto L_0x0078
            r5 = 223(0xdf, float:3.12E-43)
            if (r7 > r5) goto L_0x0078
            int r7 = r3.read()     // Catch: Exception -> 0x009d, all -> 0x009b
            if (r2 > r7) goto L_0x0092
            if (r7 > r1) goto L_0x0092
            goto L_0x0053
        L_0x0078:
            r5 = 224(0xe0, float:3.14E-43)
            if (r5 > r7) goto L_0x0053
            r5 = 239(0xef, float:3.35E-43)
            if (r7 > r5) goto L_0x0053
            int r7 = r3.read()     // Catch: Exception -> 0x009d, all -> 0x009b
            if (r2 > r7) goto L_0x0092
            if (r7 > r1) goto L_0x0092
            int r7 = r3.read()     // Catch: Exception -> 0x009d, all -> 0x009b
            if (r2 > r7) goto L_0x0092
            if (r7 > r1) goto L_0x0092
            java.lang.String r0 = "UTF-8"
        L_0x0092:
            r3.close()     // Catch: IOException -> 0x0096
            goto L_0x00a4
        L_0x0096:
            r7 = move-exception
            r7.printStackTrace()
            goto L_0x00a4
        L_0x009b:
            r7 = move-exception
            goto L_0x00a5
        L_0x009d:
            r7 = move-exception
            r7.printStackTrace()     // Catch: all -> 0x009b
            r3.close()     // Catch: IOException -> 0x0096
        L_0x00a4:
            return r0
        L_0x00a5:
            r3.close()     // Catch: IOException -> 0x00a9
            goto L_0x00ad
        L_0x00a9:
            r0 = move-exception
            r0.printStackTrace()
        L_0x00ad:
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.micolauncher.skills.music.model.cache.LrcCache.a(java.io.InputStream):java.lang.String");
    }
}
