package com.xiaomi.micolauncher.common.ubus.helpers;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import com.google.gson.annotations.SerializedName;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.common.Gsons;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.ubus.storage.LocalAlbumStorage;
import java.util.List;

/* loaded from: classes3.dex */
public class LocalAlbumHandleHelper {

    /* loaded from: classes3.dex */
    public static class OneUrlInfo {
        @SerializedName("objectName")
        public String objectName;
        @SerializedName("url")
        public String url;
    }

    public LocalAlbumStorage.AlbumStorageQuota handleDownloadUrls(Context context, String str) {
        L.localalbum.d("handle download urls %s", str);
        if (TextUtils.isEmpty(str)) {
            L.localalbum.w("empty urls");
            return LocalAlbumStorage.getInstance().getStorageQuota(context);
        }
        List<OneUrlInfo> list = ((a) Gsons.getGson().fromJson(str, (Class<Object>) a.class)).a;
        if (ContainerUtil.isEmpty(list)) {
            L.localalbum.e("no urls in message");
            return LocalAlbumStorage.getInstance().getStorageQuota(context);
        } else if (!TextUtils.equals(Environment.getExternalStorageState(), "mounted")) {
            L.localalbum.e("before download image : external storage not ready %s", Environment.getExternalStorageState());
            return LocalAlbumStorage.getInstance().getStorageQuota(context);
        } else if (context.checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") == 0) {
            return LocalAlbumStorage.getInstance().downloadFiles(context, list);
        } else {
            L.localalbum.e("write external storage permission required");
            return LocalAlbumStorage.getInstance().getStorageQuota(context);
        }
    }

    /* loaded from: classes3.dex */
    private static class a {
        @SerializedName("objectList")
        List<OneUrlInfo> a;

        private a() {
        }
    }
}
