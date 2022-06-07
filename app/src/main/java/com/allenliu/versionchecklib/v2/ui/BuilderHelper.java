package com.allenliu.versionchecklib.v2.ui;

import android.content.Context;
import com.allenliu.versionchecklib.R;
import com.allenliu.versionchecklib.core.DownloadManager;
import com.allenliu.versionchecklib.utils.ALog;
import com.allenliu.versionchecklib.v2.AllenVersionChecker;
import com.allenliu.versionchecklib.v2.builder.DownloadBuilder;
import java.io.File;

/* loaded from: classes.dex */
public class BuilderHelper {
    private DownloadBuilder a;
    private Context b;

    public BuilderHelper(Context context, DownloadBuilder downloadBuilder) {
        this.b = context;
        this.a = downloadBuilder;
    }

    public void checkAndDeleteAPK() {
        try {
            String str = this.a.getDownloadAPKPath() + this.b.getString(R.string.versionchecklib_download_apkname, this.b.getPackageName());
            if (!DownloadManager.checkAPKIsExists(this.b, str)) {
                ALog.e("删除本地apk");
                new File(str).delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void checkForceUpdate() {
        if (this.a.getForceUpdateListener() != null) {
            this.a.getForceUpdateListener().onShouldForceUpdate();
            AllenVersionChecker.getInstance().cancelAllMission(this.b);
        }
    }
}
