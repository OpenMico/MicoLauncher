package com.allenliu.versionchecklib.v2;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.Nullable;
import com.allenliu.versionchecklib.core.http.AllenHttp;
import com.allenliu.versionchecklib.v2.builder.DownloadBuilder;
import com.allenliu.versionchecklib.v2.builder.RequestVersionBuilder;
import com.allenliu.versionchecklib.v2.builder.UIData;
import com.allenliu.versionchecklib.v2.ui.VersionService;

/* loaded from: classes.dex */
public class AllenVersionChecker {

    /* loaded from: classes.dex */
    public static class a {
        public static final AllenVersionChecker a = new AllenVersionChecker();
    }

    private AllenVersionChecker() {
    }

    public static AllenVersionChecker getInstance() {
        return a.a;
    }

    public void cancelAllMission(Context context) {
        AllenHttp.getHttpClient().dispatcher().cancelAll();
        context.getApplicationContext().stopService(new Intent(context, VersionService.class));
    }

    public DownloadBuilder downloadOnly(@Nullable UIData uIData) {
        return new DownloadBuilder(null, uIData);
    }

    public RequestVersionBuilder requestVersion() {
        return new RequestVersionBuilder();
    }
}
