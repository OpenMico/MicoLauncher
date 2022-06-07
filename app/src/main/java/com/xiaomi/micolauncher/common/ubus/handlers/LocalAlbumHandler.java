package com.xiaomi.micolauncher.common.ubus.handlers;

import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.ubus.handlers.AbstractGsonUbusHandler;
import com.xiaomi.micolauncher.common.ubus.helpers.LocalAlbumHandleHelper;
import com.xiaomi.micolauncher.common.ubus.storage.LocalAlbumStorage;

/* loaded from: classes3.dex */
public class LocalAlbumHandler extends AbstractGsonUbusHandler {
    private static final int CODE_DOWNLOAD = 2;
    private static final int CODE_GET_SPACE = 1;
    private static final String METHOD_DOWNLOAD_URLS = "downloadUrl";
    private static final String METHOD_STORAGE_SPACE_STATE = "storageSpaceState";
    public static final String PATH = "localAlbumAgent";
    private LocalAlbumHandleHelper localAlbumHandler = new LocalAlbumHandleHelper();

    public LocalAlbumHandler() {
        super(0);
    }

    @Override // com.xiaomi.micolauncher.common.ubus.handlers.AbstractGsonUbusHandler, com.xiaomi.micolauncher.common.ubus.UBus.UbusHandler
    public boolean canHandle(Context context, String str, String str2) {
        return TextUtils.equals(str, PATH);
    }

    @Override // com.xiaomi.micolauncher.common.ubus.handlers.AbstractGsonUbusHandler
    public Object handleProto(Context context, String str, String str2, String str3, AbstractGsonUbusHandler.HandleResultCodeOnError handleResultCodeOnError) {
        LocalAlbumStorage.AlbumStorageQuota albumStorageQuota;
        if (canHandle(context, str, str2)) {
            int i = 2;
            L.localalbum.d("received method %s , params %s", str2, str3);
            char c = 65535;
            int hashCode = str2.hashCode();
            if (hashCode != -1211148345) {
                if (hashCode == -941352954 && str2.equals(METHOD_STORAGE_SPACE_STATE)) {
                    c = 1;
                }
            } else if (str2.equals(METHOD_DOWNLOAD_URLS)) {
                c = 0;
            }
            switch (c) {
                case 0:
                    albumStorageQuota = this.localAlbumHandler.handleDownloadUrls(context, str3);
                    break;
                case 1:
                    albumStorageQuota = LocalAlbumStorage.getInstance().getStorageQuota(context);
                    i = 1;
                    break;
                default:
                    L.ubus.w("UBUS LocalAlbumHandler : not recognized %s", str2);
                    i = -2;
                    albumStorageQuota = null;
                    break;
            }
            if (albumStorageQuota != null) {
                return albumStorageQuota;
            }
            handleResultCodeOnError.errorCode = i;
            return null;
        }
        throw new IllegalStateException("Not expected occasion");
    }
}
