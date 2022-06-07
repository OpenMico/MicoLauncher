package com.xiaomi.micolauncher.skills.personalize.manager;

import android.text.TextUtils;
import com.xiaomi.mico.token.TokenManager;
import com.xiaomi.micolauncher.api.ApiManager;
import com.xiaomi.micolauncher.api.model.Directive;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import com.xiaomi.micolauncher.skills.personalize.model.TokenResult;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Function;

/* loaded from: classes3.dex */
public class StreamManager {
    public static Observable<String> buildUrl(final Directive.Stream stream) {
        if (stream == null || !"private".equals(stream.authentication_method)) {
            return Observable.create(new ObservableOnSubscribe() { // from class: com.xiaomi.micolauncher.skills.personalize.manager.-$$Lambda$StreamManager$NA8mJV5Py-YCte77D5oNtItrZxI
                @Override // io.reactivex.ObservableOnSubscribe
                public final void subscribe(ObservableEmitter observableEmitter) {
                    StreamManager.a(Directive.Stream.this, observableEmitter);
                }
            });
        }
        return ApiManager.minaService.getToken(SystemSetting.getDeviceID()).map(new Function() { // from class: com.xiaomi.micolauncher.skills.personalize.manager.-$$Lambda$StreamManager$ijXl-zK2UOJD6bJ-RV1h7e2afmU
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                String a;
                a = StreamManager.a(Directive.Stream.this, (TokenResult.TokenData) obj);
                return a;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
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

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ String a(Directive.Stream stream, TokenResult.TokenData tokenData) throws Exception {
        return stream.url + "?token=" + stream.token + "&userId=" + TokenManager.getInstance().getUserId() + "&authToken=" + tokenData.token;
    }
}
