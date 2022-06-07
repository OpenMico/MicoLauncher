package com.efs.sdk.base.a.h.b;

import androidx.annotation.Nullable;
import com.efs.sdk.base.a.h.a.c;
import com.efs.sdk.base.a.h.d;
import com.efs.sdk.base.http.HttpEnv;
import com.efs.sdk.base.http.HttpResponse;
import com.xiaomi.micolauncher.module.setting.bluetooth.BluetoothConstants;
import com.xiaomi.micolauncher.skills.music.model.lrc.LrcRow;
import java.io.File;
import java.util.Map;

/* loaded from: classes.dex */
public final class b implements c<HttpResponse> {
    String a;
    Map<String, String> b;
    public byte[] c;
    public File d;
    public String e;
    public Map<String, String> f;
    public boolean g = false;

    @Override // com.efs.sdk.base.a.h.a.c
    @Nullable
    public final /* synthetic */ HttpResponse a() {
        char c;
        String str = this.e;
        int hashCode = str.hashCode();
        if (hashCode != 102230) {
            if (hashCode == 3446944 && str.equals("post")) {
                c = 1;
            }
            c = 65535;
        } else {
            if (str.equals(BluetoothConstants.GET)) {
                c = 0;
            }
            c = 65535;
        }
        switch (c) {
            case 0:
                return HttpEnv.getInstance().getHttpUtil().get(this.a, this.b);
            case 1:
                byte[] bArr = this.c;
                if (bArr == null || bArr.length <= 0) {
                    return HttpEnv.getInstance().getHttpUtil().post(this.a, this.b, this.d);
                }
                if (this.g) {
                    return HttpEnv.getInstance().getHttpUtil().postAsFile(this.a, this.b, this.c);
                }
                return HttpEnv.getInstance().getHttpUtil().post(this.a, this.b, this.c);
            default:
                d.b("efs.util.http", "request not support method '" + this.e + LrcRow.SINGLE_QUOTE, null);
                return null;
        }
    }
}
