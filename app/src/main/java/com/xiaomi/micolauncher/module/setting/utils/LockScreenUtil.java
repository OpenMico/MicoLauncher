package com.xiaomi.micolauncher.module.setting.utils;

import com.xiaomi.micolauncher.common.ubus.storage.LocalAlbumStorage;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import java.io.File;

/* loaded from: classes3.dex */
public class LockScreenUtil {
    public static Observable<File> getCoverFile() {
        return Observable.create($$Lambda$LockScreenUtil$UZ5yTCjGN8KqIkP2R0SvqayGR7A.INSTANCE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(ObservableEmitter observableEmitter) throws Exception {
        try {
            File coverFile = LocalAlbumStorage.getInstance().getCoverFile();
            if (coverFile == null || !coverFile.exists()) {
                observableEmitter.onError(new Exception("file is empty"));
                observableEmitter.onComplete();
            } else {
                observableEmitter.onNext(coverFile);
                observableEmitter.onComplete();
            }
        } catch (Exception e) {
            observableEmitter.onError(e);
            observableEmitter.onComplete();
        }
    }
}
