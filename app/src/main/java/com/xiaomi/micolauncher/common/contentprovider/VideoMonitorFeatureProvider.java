package com.xiaomi.micolauncher.common.contentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.xiaomi.mico.utils.Threads;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.miot.host.manager.MiotHostManager;
import com.xiaomi.miot.typedef.error.MiotError;
import com.xiaomi.miot.typedef.exception.MiotException;
import com.xiaomi.miot.typedef.listener.CompletedListener;
import com.xiaomi.miot.typedef.listener.TokenListener;

/* loaded from: classes3.dex */
public class VideoMonitorFeatureProvider extends ContentProvider {
    @Override // android.content.ContentProvider
    public int delete(@NonNull Uri uri, @Nullable String str, @Nullable String[] strArr) {
        return 0;
    }

    @Override // android.content.ContentProvider
    @Nullable
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Override // android.content.ContentProvider
    @Nullable
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    @Override // android.content.ContentProvider
    public boolean onCreate() {
        return true;
    }

    @Override // android.content.ContentProvider
    @Nullable
    public Cursor query(@NonNull Uri uri, @Nullable String[] strArr, @Nullable String str, @Nullable String[] strArr2, @Nullable String str2) {
        return null;
    }

    @Override // android.content.ContentProvider
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String str, @Nullable String[] strArr) {
        return 0;
    }

    @Override // android.content.ContentProvider
    @Nullable
    public Bundle call(@NonNull String str, @Nullable String str2, @Nullable Bundle bundle) {
        boolean z;
        final Bundle bundle2 = new Bundle();
        char c = 65535;
        bundle2.putInt("code", -1);
        try {
            z = MiotHostManager.getInstance().isMiotConnected();
        } catch (MiotException e) {
            L.ot.w("VideoMonitor", e);
            z = false;
        }
        if (!z) {
            return bundle2;
        }
        if (Threads.isMainThread()) {
            L.ot.e("call in main thread");
            return bundle2;
        }
        int hashCode = str.hashCode();
        if (hashCode != 910823748) {
            if (hashCode == 2008353926 && str.equals("miot_send")) {
                c = 1;
            }
        } else if (str.equals("get_miot_token")) {
            c = 0;
        }
        switch (c) {
            case 0:
                final Object obj = new Object();
                try {
                    MiotHostManager.getInstance().getToken(new TokenListener() { // from class: com.xiaomi.micolauncher.common.contentprovider.VideoMonitorFeatureProvider.1
                        @Override // com.xiaomi.miot.typedef.listener.TokenListener
                        public void onSucceed(String str3) {
                            bundle2.putInt("code", 0);
                            bundle2.putString("content", str3);
                            VideoMonitorFeatureProvider.this.a(obj);
                        }

                        @Override // com.xiaomi.miot.typedef.listener.TokenListener
                        public void onFailed(MiotError miotError) {
                            if (miotError != null) {
                                bundle2.putInt("code", miotError.getCode());
                            }
                            VideoMonitorFeatureProvider.this.a(obj);
                        }
                    });
                } catch (MiotException e2) {
                    L.ot.w("VideoMonitor", e2);
                }
                a(bundle2, obj);
                break;
            case 1:
                String string = bundle.getString("method_name");
                if (!TextUtils.isEmpty(string)) {
                    final Object obj2 = new Object();
                    try {
                        MiotHostManager.getInstance().send(string, str2, new CompletedListener() { // from class: com.xiaomi.micolauncher.common.contentprovider.VideoMonitorFeatureProvider.2
                            @Override // com.xiaomi.miot.typedef.listener.CompletedListener
                            public void onSucceed(String str3) {
                                bundle2.putInt("code", 0);
                                bundle2.putString("content", str3);
                                VideoMonitorFeatureProvider.this.a(obj2);
                            }

                            @Override // com.xiaomi.miot.typedef.listener.CompletedListener
                            public void onFailed(MiotError miotError) {
                                if (miotError != null) {
                                    bundle2.putInt("code", miotError.getCode());
                                }
                                VideoMonitorFeatureProvider.this.a(obj2);
                            }
                        });
                    } catch (MiotException e3) {
                        L.ot.w("VideoMonitor", e3);
                    }
                    a(bundle2, obj2);
                    break;
                }
                break;
        }
        return bundle2;
    }

    private void a(Bundle bundle, Object obj) {
        try {
            if (-1 == bundle.getInt("code")) {
                synchronized (obj) {
                    obj.wait(20000L);
                }
            }
        } catch (InterruptedException e) {
            L.ot.w("VideoMonitor", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Object obj) {
        synchronized (obj) {
            obj.notify();
        }
    }
}
