package com.xiaomi.micolauncher.api;

import android.content.Context;
import android.text.TextUtils;
import androidx.annotation.Nullable;
import com.allenliu.versionchecklib.v2.AllenVersionChecker;
import com.allenliu.versionchecklib.v2.builder.UIData;
import com.allenliu.versionchecklib.v2.callback.RequestVersionListener;
import com.elvishew.xlog.Logger;
import com.google.gson.Gson;
import com.xiaomi.ai.api.common.APIUtils;
import com.xiaomi.mico.base.utils.VersionUtils;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.common.Gsons;
import com.xiaomi.mico.common.MibrainConfig;
import com.xiaomi.mico.token.TokenManager;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.api.model.MIBrain;
import com.xiaomi.micolauncher.api.model.ThirdParty;
import com.xiaomi.micolauncher.application.Constants;
import com.xiaomi.micolauncher.application.MicoApplication;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import com.xiaomi.micolauncher.common.speech.SpeechManager;
import com.xiaomi.micolauncher.common.speech.utils.SpeechEngineHelper;
import com.xiaomi.micolauncher.module.intercom.BaseIntercomResponse;
import com.xiaomi.micolauncher.module.intercom.DeviceHomeInfo;
import com.xiaomi.micolauncher.module.intercom.IntercomSettings;
import com.xiaomi.micolauncher.skills.music.model.api.Music;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes3.dex */
public class ApiHelper {
    public static final String CP_RESOURCE_MUSIC = "music";
    public static final String CP_RESOURCE_RADIO = "radio";
    private static Gson a = Gsons.getGson();

    public static Observable<String> getAuthorizationValue() {
        return Observable.create($$Lambda$ApiHelper$35hTq9gVM7cek8iGYFaQf3RT2pc.INSTANCE);
    }

    public static /* synthetic */ void a(ObservableEmitter observableEmitter) throws Exception {
        if (!ThreadUtil.isMainThread()) {
            String authorizationValueBlocked = SpeechManager.getInstance().getAuthorizationValueBlocked();
            if (!TextUtils.isEmpty(authorizationValueBlocked)) {
                observableEmitter.onNext(authorizationValueBlocked);
                observableEmitter.onComplete();
                return;
            }
            observableEmitter.onError(new Throwable("load auth value error"));
            return;
        }
        throw new IllegalStateException("do auth in main thread");
    }

    public static Observable<MIBrain.CpResource> getCpResource(final String str, final String str2, final String str3) {
        return getAuthorizationValue().flatMap(new Function() { // from class: com.xiaomi.micolauncher.api.-$$Lambda$ApiHelper$QHbplWdvZVk_Sn6BYPH4WvVI_gM
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                ObservableSource a2;
                a2 = ApiHelper.a((String) obj, str, str2, str3);
                return a2;
            }
        });
    }

    public static Observable<MIBrain.CpResource> a(String str, String str2, String str3, String str4) {
        return ApiManager.miBrainService.getCpResource(str, str2, str3, str4, TokenManager.getInstance().getUserId(), Constants.XM_APP_ID, SystemSetting.getDeviceID());
    }

    public static Observable<MIBrain.CpResource> getCpVideoResource(final String str, final String str2, final int i) {
        return getAuthorizationValue().flatMap(new Function() { // from class: com.xiaomi.micolauncher.api.-$$Lambda$ApiHelper$VA0YAyd3fkg6C8dCh423wCLXPiU
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                ObservableSource a2;
                a2 = ApiHelper.a((String) obj, str, str2, i);
                return a2;
            }
        });
    }

    public static Observable<MIBrain.CpResource> a(String str, String str2, String str3, int i) {
        return ApiManager.miBrainService.getCpVideoResource(str, str2, str3, TokenManager.getInstance().getUserId(), Constants.XM_APP_ID, SystemSetting.getDeviceID(), i);
    }

    public static Observable<MIBrain.MVResource> getCpMVResource(final String str, final String str2, final String str3) {
        return getAuthorizationValue().flatMap(new Function() { // from class: com.xiaomi.micolauncher.api.-$$Lambda$ApiHelper$o6t2X9uRu6g5vgEcsxvZFp7Q0q4
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                ObservableSource b;
                b = ApiHelper.b((String) obj, str, str2, str3);
                return b;
            }
        });
    }

    public static Observable<MIBrain.MVResource> b(String str, String str2, String str3, String str4) {
        return ApiManager.miBrainService.getCpMVResource(str, str2, str3, TokenManager.getInstance().getUserId(), Constants.XM_APP_ID, SystemSetting.getDeviceID(), str4);
    }

    public static Observable<MIBrain.MiTvResource> getMiTvResource(final String str, final int i, final String str2) {
        return getAuthorizationValue().flatMap(new Function() { // from class: com.xiaomi.micolauncher.api.-$$Lambda$ApiHelper$-ACG3Enj0uYwaj2h2XOY0WDQPRQ
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                ObservableSource a2;
                a2 = ApiHelper.a((String) obj, "mitv_v2", str, i, "video", str2);
                return a2;
            }
        });
    }

    public static Observable<MIBrain.MiTvResource> a(String str, String str2, String str3, int i, String str4, String str5) {
        return ApiManager.miBrainService.getMiTvResource(str, str2, str3, i, TokenManager.getInstance().getUserId(), SystemSetting.getDeviceID(), str4, 1, str5);
    }

    public static Observable<List<DeviceHomeInfo>> getHomeMembers(final String str) {
        return getAuthorizationValue().flatMap(new Function<String, ObservableSource<List<DeviceHomeInfo>>>() { // from class: com.xiaomi.micolauncher.api.ApiHelper.1
            /* renamed from: a */
            public ObservableSource<List<DeviceHomeInfo>> apply(@NotNull String str2) throws Exception {
                return ApiManager.aiMiService.getHomeMembers(str2, str);
            }
        });
    }

    public static Observable<BaseIntercomResponse<IntercomSettings>> getIntercomSettings(final String str) {
        return getAuthorizationValue().flatMap(new Function() { // from class: com.xiaomi.micolauncher.api.-$$Lambda$ApiHelper$RgTJLQBWDoGPTbeDihJlG6Q3UgQ
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                ObservableSource a2;
                a2 = ApiHelper.a(str, (String) obj);
                return a2;
            }
        });
    }

    public static /* synthetic */ ObservableSource a(String str, String str2) throws Exception {
        return ApiManager.aiMiService.getIntercomSettings(str2, SpeechEngineHelper.getAiDeviceId(), str, "SCREENSOUNDBOX");
    }

    public static Observable<BaseIntercomResponse<IntercomSettings>> uploadIntercomSettings(final String str, final String str2) {
        return getAuthorizationValue().flatMap(new Function<String, ObservableSource<BaseIntercomResponse<IntercomSettings>>>() { // from class: com.xiaomi.micolauncher.api.ApiHelper.2
            /* renamed from: a */
            public ObservableSource<BaseIntercomResponse<IntercomSettings>> apply(@NotNull String str3) throws Exception {
                return ApiManager.aiMiService.uploadIntercomSettings(str3, str, str2, "SCREENSOUNDBOX");
            }
        });
    }

    public static Observable<ResponseBody> uploadIntercomMsgStatus(final String str, final String str2) {
        return getAuthorizationValue().flatMap(new Function() { // from class: com.xiaomi.micolauncher.api.-$$Lambda$ApiHelper$WIkzEPvYWZsvz6dRKTDA9fNF9XA
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                ObservableSource a2;
                a2 = ApiHelper.a(str, str2, (String) obj);
                return a2;
            }
        });
    }

    public static /* synthetic */ ObservableSource a(String str, String str2, String str3) throws Exception {
        return ApiManager.aiMiService.uploadIntercomMsgStatus(str3, TokenManager.getInstance().getUserId(), SpeechEngineHelper.getAiDeviceId(), str, str2);
    }

    public static Observable<ResponseBody> uploadIntercomAudio(final File file, final String str, final String str2, final String str3, final String str4, final String str5, final int i) {
        return getAuthorizationValue().flatMap(new Function() { // from class: com.xiaomi.micolauncher.api.-$$Lambda$ApiHelper$XbIuw_yCYDP5xSRnRf2Oa3CPU5o
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                ObservableSource a2;
                a2 = ApiHelper.a(i, file, str, str2, str3, str4, str5, (String) obj);
                return a2;
            }
        });
    }

    public static /* synthetic */ ObservableSource a(int i, File file, String str, String str2, String str3, String str4, String str5, String str6) throws Exception {
        String randomRequestId = APIUtils.randomRequestId(false);
        L.api.i("ApiHelper uploadIntercomAudio requestId=%s, duration_in_ms=%s", randomRequestId, Integer.valueOf(i));
        return ApiManager.aiMiService.uploadIntercomAudio(str6, new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("audio_file", file.getName(), RequestBody.create(file, MediaType.parse("application/octet-stream"))).addFormDataPart("requestId", randomRequestId).addFormDataPart("intercom_message", str).addFormDataPart("to_device_id", str2).addFormDataPart("to_home_id", str3).addFormDataPart("caller_home_id", str4).addFormDataPart("caller_device_id", str5).addFormDataPart("duration_in_ms", String.valueOf(i)).build());
    }

    public static void getLauncherGreyUpgradeInfoDebug(String str) {
        AllenVersionChecker.getInstance().requestVersion().setRequestUrl(str).request(new RequestVersionListener() { // from class: com.xiaomi.micolauncher.api.ApiHelper.3
            @Override // com.allenliu.versionchecklib.v2.callback.RequestVersionListener
            public void onRequestVersionFailure(String str2) {
            }

            @Override // com.allenliu.versionchecklib.v2.callback.RequestVersionListener
            @Nullable
            public UIData onRequestVersionSuccess(String str2) {
                ThirdParty.GrayUpdateResponse grayUpdateResponse = (ThirdParty.GrayUpdateResponse) Gsons.getGson().fromJson(str2, (Class<Object>) ThirdParty.GrayUpdateResponse.class);
                if (grayUpdateResponse.latestVersionCode > VersionUtils.getVersionCode(MicoApplication.getGlobalContext())) {
                    return UIData.create().setDownloadUrl(grayUpdateResponse.url).setTitle("发现Launcher更新").setContent(TextUtils.join(", ", grayUpdateResponse.releaseNotes));
                }
                return null;
            }
        }).setShowDownloadFailDialog(true).executeMission(MicoApplication.getGlobalContext());
    }

    public static Observable<String> like(int i, long j) {
        if (i == 4) {
            return ApiManager.minaService.stationLike(j);
        }
        return ApiManager.minaService.like(i, j);
    }

    public static Observable<String> unlike(int i, long j) {
        if (i == 4) {
            return ApiManager.minaService.stationUnlike(j);
        }
        return ApiManager.minaService.unlike(i, j);
    }

    public static Observable<String> stationLike(long j) {
        return ApiManager.minaService.stationLike(j);
    }

    public static Observable<String> stationUnlike(long j) {
        return ApiManager.minaService.stationUnlike(j);
    }

    public static Observable<Boolean> addSongs(long j, List<Music.Song> list) {
        return ApiManager.minaService.addSongs(j, a(list));
    }

    private static String a(List<Music.Song> list) {
        ArrayList arrayList = new ArrayList(list.size());
        for (Music.Song song : list) {
            arrayList.add(song.toSimple());
        }
        return a.toJson(arrayList);
    }

    public static Observable<Boolean> deleteSongs(long j, List<Music.Song> list) {
        return ApiManager.minaService.deleteSongs(j, a(list));
    }

    public static void uploadMiotAccessToken(String str, String str2) {
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
            ApiManager.minaService.uploadMiotAccessToken(SystemSetting.getDeviceID(), str, str2).subscribe(new DefaultObserver<String>() { // from class: com.xiaomi.micolauncher.api.ApiHelper.4
                /* renamed from: a */
                public void onSuccess(String str3) {
                }
            });
        }
    }

    public static void reportMusicActionLog(Context context, String str, String str2) {
        ApiManager.minaService.reportmusicActionLog(SystemSetting.getDeviceID(), MibrainConfig.getMibrainConfig(context).clientId, str, str2, "", "{}").subscribe(new DefaultObserver<String>() { // from class: com.xiaomi.micolauncher.api.ApiHelper.5
            /* renamed from: a */
            public void onSuccess(String str3) {
            }

            @Override // com.xiaomi.micolauncher.api.DefaultObserver
            public void onFail(ApiError apiError) {
                Logger logger = L.api;
                logger.e("reportMusicActionLog failed! " + apiError.toString());
            }
        });
    }

    public static Observable<Music.AudioInfo> requestAudioInfo(final String str) {
        return ApiManager.minaService.getAudioInfoV3(String.format("[%s]", str)).map($$Lambda$ApiHelper$KgTQwFAEmsJzoy0qiLfp3mkKkOY.INSTANCE).flatMap(new Function() { // from class: com.xiaomi.micolauncher.api.-$$Lambda$ApiHelper$JEfbOVHNX5iz5H9CVncp18xEcmI
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                ObservableSource a2;
                a2 = ApiHelper.a(str, (List) obj);
                return a2;
            }
        });
    }

    public static /* synthetic */ List c(MinaResponse minaResponse) throws Exception {
        return (List) minaResponse.data;
    }

    public static /* synthetic */ ObservableSource a(String str, List list) throws Exception {
        if (ContainerUtil.hasData(list)) {
            return Observable.just(list.get(0));
        }
        return Observable.error(new Throwable(String.format("get audio info by aivs3/audio/info/v2 with audioId=%s return null or empty", str)));
    }

    public static Observable<Boolean> addCollectV3(String str, String str2) {
        return ApiManager.minaService.addCollectV3(str, str2).map($$Lambda$ApiHelper$F2g1w7PWkZbjN4wStQMYNatjRUs.INSTANCE);
    }

    public static /* synthetic */ Boolean b(MinaResponse minaResponse) throws Exception {
        return (Boolean) minaResponse.data;
    }

    public static Observable<Boolean> delCollectV3(String str, String str2) {
        return ApiManager.minaService.delCollectV3(str, str2).map($$Lambda$ApiHelper$DlHO194UQfbKeij_OgXXCLIo3tk.INSTANCE);
    }

    public static /* synthetic */ Boolean a(MinaResponse minaResponse) throws Exception {
        return (Boolean) minaResponse.data;
    }
}
