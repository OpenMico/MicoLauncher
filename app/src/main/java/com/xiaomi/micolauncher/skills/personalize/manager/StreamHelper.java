package com.xiaomi.micolauncher.skills.personalize.manager;

import android.net.Uri;
import android.text.TextUtils;
import com.xiaomi.accountsdk.request.SimpleRequestForAccount;
import com.xiaomi.ai.api.AudioPlayer;
import com.xiaomi.common.Optional;
import com.xiaomi.mico.token.TokenManager;
import com.xiaomi.micolauncher.api.ApiHelper;
import com.xiaomi.micolauncher.api.ApiManager;
import com.xiaomi.micolauncher.api.model.Directive;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.model.ImageSource;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import com.xiaomi.micolauncher.common.speech.SpeechManager;
import com.xiaomi.micolauncher.common.webview.CommonWebActivity;
import com.xiaomi.micolauncher.skills.personalize.model.TokenResult;
import com.xiaomi.micolauncher.skills.video.model.StreamInfo;
import com.zhy.http.okhttp.OkHttpUtils;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Function;
import java.io.IOException;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes3.dex */
public class StreamHelper {
    public static Observable<String> buildUrl(final ImageSource imageSource) {
        if (!imageSource.authentication) {
            return Observable.just(imageSource.url);
        }
        return ApiHelper.getAuthorizationValue().map(new Function() { // from class: com.xiaomi.micolauncher.skills.personalize.manager.-$$Lambda$StreamHelper$HJRukkNYfwbCmlApQ5c53c4GV3I
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                String a;
                a = StreamHelper.a(ImageSource.this, (String) obj);
                return a;
            }
        });
    }

    public static /* synthetic */ String a(ImageSource imageSource, String str) throws Exception {
        return buildUri(imageSource.url, str, imageSource.token);
    }

    public static Observable<String> buildUrl(AudioPlayer.Stream stream) {
        return processAudioUrl(stream, false);
    }

    public static Observable<String> processAudioUrl(@NotNull final AudioPlayer.Stream stream, final boolean z) {
        if (z || stream.isAuthentication()) {
            return ApiHelper.getAuthorizationValue().map(new Function() { // from class: com.xiaomi.micolauncher.skills.personalize.manager.-$$Lambda$StreamHelper$OXIa92GuTRtWSjoLKRWN1CT1G4w
                @Override // io.reactivex.functions.Function
                public final Object apply(Object obj) {
                    String a;
                    a = StreamHelper.a((String) obj, AudioPlayer.Stream.this);
                    return a;
                }
            }).map(new Function() { // from class: com.xiaomi.micolauncher.skills.personalize.manager.-$$Lambda$StreamHelper$gFyZewZ1Ma40sX0CfqEnBuvZIco
                @Override // io.reactivex.functions.Function
                public final Object apply(Object obj) {
                    String a;
                    a = StreamHelper.a(AudioPlayer.Stream.this, z, (String) obj);
                    return a;
                }
            });
        }
        return Observable.just(stream.getUrl());
    }

    public static /* synthetic */ String a(@NotNull AudioPlayer.Stream stream, boolean z, String str) throws Exception {
        Optional<Boolean> isRedirect = stream.isRedirect();
        return (z || (isRedirect.isPresent() && isRedirect.get().booleanValue())) ? a(str) : str;
    }

    public static Observable<String> buildUrl(final StreamInfo streamInfo) {
        Observable<String> authorizationValue = ApiHelper.getAuthorizationValue();
        streamInfo.getClass();
        return authorizationValue.map(new Function() { // from class: com.xiaomi.micolauncher.skills.personalize.manager.-$$Lambda$X85xqvIOfDOCMyro_B5CtfqYkXE
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                return StreamInfo.this.getStreamUrl((String) obj);
            }
        }).map(new Function() { // from class: com.xiaomi.micolauncher.skills.personalize.manager.-$$Lambda$StreamHelper$BfiS2aaxzFUzww7sZ03q56J0x1s
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                String a;
                a = StreamHelper.a(StreamInfo.this, (String) obj);
                return a;
            }
        });
    }

    public static /* synthetic */ String a(StreamInfo streamInfo, String str) throws Exception {
        return streamInfo.redirect ? a(str) : str;
    }

    public static Observable<String> buildUrl(boolean z, final boolean z2, final String str, final String str2) {
        if (z2 || z) {
            return ApiHelper.getAuthorizationValue().map(new Function() { // from class: com.xiaomi.micolauncher.skills.personalize.manager.-$$Lambda$StreamHelper$h3FW6whDA1G36qFjpMnELFFqr2Y
                @Override // io.reactivex.functions.Function
                public final Object apply(Object obj) {
                    String buildUri;
                    buildUri = StreamHelper.buildUri(str, (String) obj, str2);
                    return buildUri;
                }
            }).map(new Function() { // from class: com.xiaomi.micolauncher.skills.personalize.manager.-$$Lambda$StreamHelper$czBuk2R9ikQw0ROMxfyUk_rfgNc
                @Override // io.reactivex.functions.Function
                public final Object apply(Object obj) {
                    String a;
                    a = StreamHelper.a(z2, (String) obj);
                    return a;
                }
            });
        }
        return Observable.just(str);
    }

    public static /* synthetic */ String a(boolean z, String str) throws Exception {
        return z ? a(str) : str;
    }

    private static String a(String str) throws IOException {
        Response execute = OkHttpUtils.getInstance().getOkHttpClient().newCall(new Request.Builder().get().url(str).build()).execute();
        String httpUrl = execute.request().url().toString();
        execute.close();
        L.base.d("redirect url, old=%s new=%s", str, httpUrl);
        return httpUrl;
    }

    public static String a(String str, AudioPlayer.Stream stream) {
        return buildUri(stream.getUrl(), str, stream.getToken().isPresent() ? stream.getToken().get() : "");
    }

    public static String buildUri(String str, String str2, String str3) {
        L.base.d("buildUri url=%s,auth=%s,token=%s", str, str2, str3);
        Uri.Builder buildUpon = Uri.parse(str).buildUpon();
        if (!TextUtils.isEmpty(str2)) {
            buildUpon.appendQueryParameter(CommonWebActivity.COMMON_AUTH, str2);
        }
        if (!TextUtils.isEmpty(str3)) {
            buildUpon.appendQueryParameter("token", str3);
        }
        buildUpon.appendQueryParameter(SimpleRequestForAccount.COOKIE_NAME_DEVICE_ID, SystemSetting.getDeviceID());
        return buildUpon.toString();
    }

    public static Observable<String> buildUrl(final Directive.Stream stream) {
        if (stream == null || !"private".equals(stream.authentication_method)) {
            return Observable.create(new ObservableOnSubscribe() { // from class: com.xiaomi.micolauncher.skills.personalize.manager.-$$Lambda$StreamHelper$ujLgLbHKuJdYiep9hv3e8rEruMU
                @Override // io.reactivex.ObservableOnSubscribe
                public final void subscribe(ObservableEmitter observableEmitter) {
                    StreamHelper.a(Directive.Stream.this, observableEmitter);
                }
            });
        }
        return ApiManager.minaService.getToken(SystemSetting.getDeviceID()).map(new Function() { // from class: com.xiaomi.micolauncher.skills.personalize.manager.-$$Lambda$StreamHelper$SuZdd1xvc0w9fOux-upQ4_TW98I
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                String a;
                a = StreamHelper.a(Directive.Stream.this, (TokenResult.TokenData) obj);
                return a;
            }
        });
    }

    public static /* synthetic */ void a(Directive.Stream stream, ObservableEmitter observableEmitter) throws Exception {
        if (stream == null) {
            observableEmitter.onError(new Exception("audio item is empty"));
        } else if (TextUtils.isEmpty(stream.url)) {
            observableEmitter.onError(new Exception("audio item stream url is empty"));
        } else {
            observableEmitter.onNext(stream.url);
        }
        observableEmitter.onComplete();
    }

    public static /* synthetic */ String a(Directive.Stream stream, TokenResult.TokenData tokenData) throws Exception {
        return stream.url + "?token=" + stream.token + "&userId=" + TokenManager.getInstance().getUserId() + "&authToken=" + tokenData.token;
    }

    public static String buildUrlSync(AudioPlayer.Stream stream) {
        if (!stream.isAuthentication()) {
            return stream.getUrl();
        }
        return buildUri(stream.getUrl(), SpeechManager.getInstance().getAuthorizationValueBlocked(), stream.getToken().isPresent() ? stream.getToken().get() : "");
    }
}
