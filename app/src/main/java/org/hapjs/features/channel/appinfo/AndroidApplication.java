package org.hapjs.features.channel.appinfo;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.text.TextUtils;
import org.hapjs.features.channel.Utils;

/* loaded from: classes5.dex */
public class AndroidApplication {
    private Context a;
    public String mPkgName;
    public String[] mSignatureList;

    public AndroidApplication(Context context, String str, String... strArr) {
        this.a = context;
        this.mPkgName = str;
        this.mSignatureList = strArr;
    }

    public boolean checkSignMatched() {
        String[] strArr = this.mSignatureList;
        if (strArr == null || strArr.length == 0) {
            return true;
        }
        PackageInfo a = a();
        if (a == null) {
            return false;
        }
        for (Signature signature : a.signatures) {
            String sha256 = Utils.getSha256(signature.toByteArray());
            for (String str : this.mSignatureList) {
                if (TextUtils.equals(str, sha256)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkInstalled() {
        return a() != null;
    }

    private PackageInfo a() {
        try {
            return this.a.getPackageManager().getPackageInfo(this.mPkgName, 64);
        } catch (PackageManager.NameNotFoundException unused) {
            return null;
        }
    }
}
