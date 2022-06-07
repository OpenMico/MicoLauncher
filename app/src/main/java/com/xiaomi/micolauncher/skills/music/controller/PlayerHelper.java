package com.xiaomi.micolauncher.skills.music.controller;

import android.text.TextUtils;
import android.util.LruCache;
import android.util.Pair;
import com.elvishew.xlog.Logger;
import com.xiaomi.accountsdk.request.SimpleRequestForAccount;
import com.xiaomi.ai.api.AudioPlayer;
import com.xiaomi.common.Optional;
import com.xiaomi.mico.base.utils.NetworkUtil;
import com.xiaomi.micolauncher.api.ApiHelper;
import com.xiaomi.micolauncher.api.model.Directive;
import com.xiaomi.micolauncher.api.model.MIBrain;
import com.xiaomi.micolauncher.api.model.PlayV3Pact;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import com.xiaomi.micolauncher.common.speech.SpeechManager;
import com.xiaomi.micolauncher.common.webview.CommonWebActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Function;
import java.io.IOException;
import java.util.ArrayList;
import okhttp3.Request;
import okhttp3.Response;

/* loaded from: classes3.dex */
public class PlayerHelper {
    private static LruCache<String, MIBrain.CpResource> a = new LruCache<>(100);

    public static Observable<MIBrain.CpResource> getCpResource(String str, String str2, String str3) {
        return ApiHelper.getCpResource(str, str2, str3);
    }

    private static String a(String str, String str2) {
        return String.format("%s_%s", str, str2);
    }

    public static String cpResourceType(Directive.Resource resource) {
        return (resource == null || resource.extend == null || !"radio".equals(resource.extend.resourceType)) ? "" : "radio";
    }

    public static Observable<MIBrain.CpResource> cacheResource(final String str, final String str2, String str3) {
        return ApiHelper.getCpResource(str, str2, str3).map(new Function() { // from class: com.xiaomi.micolauncher.skills.music.controller.-$$Lambda$PlayerHelper$APlRPkzsQGDPv3pSTCf5rMq3pGQ
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                MIBrain.CpResource a2;
                a2 = PlayerHelper.a(str, str2, (MIBrain.CpResource) obj);
                return a2;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ MIBrain.CpResource a(String str, String str2, MIBrain.CpResource cpResource) throws Exception {
        cpResource.setLoadTimeStamp();
        a.put(a(str, str2), cpResource);
        return cpResource;
    }

    public static Observable<MIBrain.CpResource> loadCpResource(String str, String str2, String str3) {
        MIBrain.CpResource cpResource = a.get(a(str, str2));
        if (cpResource == null || !cpResource.isNotExpired()) {
            return ApiHelper.getCpResource(str, str2, str3);
        }
        L.base.i("play cache resource cp=%s,id=%s,type=%s,", str, str2, str3);
        return Observable.just(cpResource);
    }

    public static MIBrain.CpResource loadCacheResource(String str, String str2, String str3) {
        MIBrain.CpResource cpResource = a.get(a(str, str2));
        if (cpResource == null || !cpResource.isNotExpired()) {
            return null;
        }
        return cpResource;
    }

    public static Observable<String> processAudioUrl(final AudioPlayer.AudioItemV1 audioItemV1, final boolean z) {
        return Observable.create(new ObservableOnSubscribe() { // from class: com.xiaomi.micolauncher.skills.music.controller.-$$Lambda$PlayerHelper$o8GT_KX9E6iTNMSeBkF1E9sK1tc
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                PlayerHelper.a(AudioPlayer.AudioItemV1.this, z, observableEmitter);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(AudioPlayer.AudioItemV1 audioItemV1, boolean z, ObservableEmitter observableEmitter) throws Exception {
        String url = audioItemV1.getStream().getUrl();
        if (audioItemV1.getStream().isAuthentication()) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(new Pair(CommonWebActivity.COMMON_AUTH, SpeechManager.getInstance().getAuthorizationValueBlocked()));
            if (audioItemV1.getStream().getToken().isPresent()) {
                arrayList.add(new Pair("token", audioItemV1.getStream().getToken().get()));
            }
            arrayList.add(new Pair(SimpleRequestForAccount.COOKIE_NAME_DEVICE_ID, SystemSetting.getDeviceID()));
            url = NetworkUtil.appendUrl(url, arrayList);
        }
        Optional<Boolean> isRedirect = audioItemV1.getStream().isRedirect();
        if (!TextUtils.isEmpty(url) && (z || (isRedirect.isPresent() && isRedirect.get().booleanValue()))) {
            try {
                Response execute = OkHttpUtils.getInstance().getOkHttpClient().newCall(new Request.Builder().get().url(url).build()).execute();
                url = execute.request().url().toString();
                Logger logger = L.player;
                logger.d("redirect url = " + url);
                if (execute != null) {
                    execute.close();
                }
            } catch (IOException e) {
                L.player.e("redirect url error", e);
            }
        }
        observableEmitter.onNext(url);
        observableEmitter.onComplete();
    }

    public static Observable<String> processAudioUrl(final PlayV3Pact.AudioItem audioItem, final boolean z) {
        return Observable.create(new ObservableOnSubscribe() { // from class: com.xiaomi.micolauncher.skills.music.controller.-$$Lambda$PlayerHelper$OkTuGXcBtOGYtsmqRdU4qrHG88k
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                PlayerHelper.a(PlayV3Pact.AudioItem.this, z, observableEmitter);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Type inference failed for: r1v2, types: [boolean] */
    /* JADX WARN: Unknown variable types count: 1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static /* synthetic */ void a(com.xiaomi.micolauncher.api.model.PlayV3Pact.AudioItem r7, boolean r8, io.reactivex.ObservableEmitter r9) throws java.lang.Exception {
        /*
            com.xiaomi.micolauncher.api.model.PlayV3Pact$Stream r0 = r7.stream
            java.lang.String r0 = r0.url
            com.xiaomi.micolauncher.api.model.PlayV3Pact$Stream r1 = r7.stream
            boolean r1 = r1.authentication
            if (r1 == 0) goto L_0x0033
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            android.util.Pair r2 = new android.util.Pair
            java.lang.String r3 = "auth"
            com.xiaomi.micolauncher.common.speech.SpeechManager r4 = com.xiaomi.micolauncher.common.speech.SpeechManager.getInstance()
            java.lang.String r4 = r4.getAuthorizationValueBlocked()
            r2.<init>(r3, r4)
            r1.add(r2)
            android.util.Pair r2 = new android.util.Pair
            java.lang.String r3 = "deviceId"
            java.lang.String r4 = com.xiaomi.micolauncher.common.setting.SystemSetting.getDeviceID()
            r2.<init>(r3, r4)
            r1.add(r2)
            java.lang.String r0 = com.xiaomi.mico.base.utils.NetworkUtil.appendUrl(r0, r1)
        L_0x0033:
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            if (r1 != 0) goto L_0x00ae
            if (r8 != 0) goto L_0x0041
            com.xiaomi.micolauncher.api.model.PlayV3Pact$Stream r7 = r7.stream
            boolean r7 = r7.redirect
            if (r7 == 0) goto L_0x00ae
        L_0x0041:
            com.zhy.http.okhttp.OkHttpUtils r7 = com.zhy.http.okhttp.OkHttpUtils.getInstance()     // Catch: IOException -> 0x00a6
            okhttp3.OkHttpClient r7 = r7.getOkHttpClient()     // Catch: IOException -> 0x00a6
            okhttp3.Request$Builder r8 = new okhttp3.Request$Builder     // Catch: IOException -> 0x00a6
            r8.<init>()     // Catch: IOException -> 0x00a6
            okhttp3.Request$Builder r8 = r8.get()     // Catch: IOException -> 0x00a6
            okhttp3.Request$Builder r8 = r8.url(r0)     // Catch: IOException -> 0x00a6
            okhttp3.Request r8 = r8.build()     // Catch: IOException -> 0x00a6
            okhttp3.Call r7 = r7.newCall(r8)     // Catch: IOException -> 0x00a6
            okhttp3.Response r7 = r7.execute()     // Catch: IOException -> 0x00a6
            r8 = 0
            okhttp3.Request r1 = r7.request()     // Catch: Throwable -> 0x0090, all -> 0x008b
            okhttp3.HttpUrl r1 = r1.url()     // Catch: Throwable -> 0x0090, all -> 0x008b
            java.lang.String r1 = r1.toString()     // Catch: Throwable -> 0x0090, all -> 0x008b
            com.elvishew.xlog.Logger r2 = com.xiaomi.micolauncher.common.L.base     // Catch: Throwable -> 0x0088, all -> 0x0086
            java.lang.String r3 = "redirect url, old=%s new=%s"
            r4 = 2
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch: Throwable -> 0x0088, all -> 0x0086
            r5 = 0
            r4[r5] = r0     // Catch: Throwable -> 0x0088, all -> 0x0086
            r0 = 1
            r4[r0] = r1     // Catch: Throwable -> 0x0088, all -> 0x0086
            r2.d(r3, r4)     // Catch: Throwable -> 0x0088, all -> 0x0086
            if (r7 == 0) goto L_0x0084
            r7.close()     // Catch: IOException -> 0x00a3
        L_0x0084:
            r0 = r1
            goto L_0x00ae
        L_0x0086:
            r0 = move-exception
            goto L_0x0092
        L_0x0088:
            r8 = move-exception
            r0 = r1
            goto L_0x0091
        L_0x008b:
            r1 = move-exception
            r6 = r1
            r1 = r0
            r0 = r6
            goto L_0x0092
        L_0x0090:
            r8 = move-exception
        L_0x0091:
            throw r8     // Catch: all -> 0x008b
        L_0x0092:
            if (r7 == 0) goto L_0x00a2
            if (r8 == 0) goto L_0x009f
            r7.close()     // Catch: Throwable -> 0x009a, IOException -> 0x00a3
            goto L_0x00a2
        L_0x009a:
            r7 = move-exception
            r8.addSuppressed(r7)     // Catch: IOException -> 0x00a3
            goto L_0x00a2
        L_0x009f:
            r7.close()     // Catch: IOException -> 0x00a3
        L_0x00a2:
            throw r0     // Catch: IOException -> 0x00a3
        L_0x00a3:
            r7 = move-exception
            r0 = r1
            goto L_0x00a7
        L_0x00a6:
            r7 = move-exception
        L_0x00a7:
            com.elvishew.xlog.Logger r8 = com.xiaomi.micolauncher.common.L.player
            java.lang.String r1 = "redirect url error"
            r8.e(r1, r7)
        L_0x00ae:
            r9.onNext(r0)
            r9.onComplete()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.micolauncher.skills.music.controller.PlayerHelper.a(com.xiaomi.micolauncher.api.model.PlayV3Pact$AudioItem, boolean, io.reactivex.ObservableEmitter):void");
    }
}
