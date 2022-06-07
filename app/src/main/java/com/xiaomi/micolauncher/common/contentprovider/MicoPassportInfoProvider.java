package com.xiaomi.micolauncher.common.contentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.xiaomi.account.openauth.AuthorizeActivityBase;
import com.xiaomi.accountsdk.account.data.BaseConstants;
import com.xiaomi.accountsdk.request.SimpleRequestForAccount;
import com.xiaomi.mico.token.TokenManager;
import com.xiaomi.micolauncher.api.model.ThirdPartyResponse;
import com.xiaomi.micolauncher.application.setup.afterlogin.QuickAppSetup;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import com.xiaomi.passport.servicetoken.ServiceTokenResult;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class MicoPassportInfoProvider extends ContentProvider {
    public static final int GET_ACCOUNT_CODE = 2;
    public static final int GET_DEVICEINFO_CODE = 3;
    public static final int GET_SERVICE_TOKEN_CODE = 1;
    private static String[] a = {"id", "info"};
    private static final UriMatcher b = new UriMatcher(-1);

    @Override // android.content.ContentProvider
    public int delete(@NonNull Uri uri, @Nullable String str, @Nullable String[] strArr) {
        return 0;
    }

    @Override // android.content.ContentProvider
    @Nullable
    public String getType(Uri uri) {
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
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String str, @Nullable String[] strArr) {
        return 0;
    }

    static {
        b.addURI("com.xiaomi.micolauncher", "common.contentprovider.MicoPassportInfoProvider/getServiceToken/*", 1);
        b.addURI("com.xiaomi.micolauncher", "common.contentprovider.MicoPassportInfoProvider/getAccount", 2);
        b.addURI("com.xiaomi.micolauncher", "common.contentprovider.MicoPassportInfoProvider/getDeviceInfo", 3);
    }

    @Override // android.content.ContentProvider
    @Nullable
    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        String callingPackage = getCallingPackage();
        if (!callingPackage.equals(QuickAppSetup.QUICK_PKG_NAME) && !callingPackage.equals("com.xiaomi.myservicetokenresultprovidertest")) {
            return null;
        }
        switch (b.match(uri)) {
            case 1:
                return a(uri.getLastPathSegment());
            case 2:
                return c();
            case 3:
                return b();
            default:
                return null;
        }
    }

    private MatrixCursor b() {
        L.contentprovider.i("MicoPassportInfoProvider getDeviceInfo");
        MatrixCursor matrixCursor = new MatrixCursor(a);
        matrixCursor.addRow(new Object[]{"0", a()});
        return matrixCursor;
    }

    String a() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(SimpleRequestForAccount.COOKIE_NAME_DEVICE_ID, SystemSetting.getDeviceID());
            jSONObject.put("deviceName", SystemSetting.getDeviceName());
            jSONObject.put("deviceLocation", SystemSetting.getDeviceLocation());
        } catch (JSONException e) {
            L.contentprovider.e("getDeviceInfoString json put failed", e);
        }
        return jSONObject.toString();
    }

    private MatrixCursor c() {
        L.contentprovider.i("MicoPassportInfoProvider getAccountInfo");
        MatrixCursor matrixCursor = new MatrixCursor(a);
        matrixCursor.addRow(new Object[]{"0", d()});
        return matrixCursor;
    }

    private String d() {
        String str = "";
        JSONObject jSONObject = new JSONObject();
        ThirdPartyResponse.UserCard blockingSingle = SystemSetting.getUserProfile().getUserCardObservable().subscribeOn(MicoSchedulers.mainThread()).blockingSingle();
        if (blockingSingle.miliaoIcon != null) {
            str = blockingSingle.miliaoIcon;
        }
        try {
            jSONObject.put("userName", TokenManager.getInstance().getUserId());
            jSONObject.put("orgType", "com.xiaomi");
            jSONObject.put("iconUrl", str);
        } catch (JSONException e) {
            L.contentprovider.e("getAccountInfoString json put failed", e);
        }
        String jSONObject2 = jSONObject.toString();
        L.contentprovider.i("MicoPassportInfoProvider getAccountInfoString return %s", jSONObject2);
        return jSONObject2;
    }

    private MatrixCursor a(String str) {
        L.contentprovider.i("MicoPassportInfoProvider getServiceTokenInfo %s", str);
        MatrixCursor matrixCursor = new MatrixCursor(a);
        matrixCursor.addRow(new Object[]{"0", b(str)});
        return matrixCursor;
    }

    private String b(String str) {
        JSONObject jSONObject = new JSONObject();
        L.contentprovider.i("MicoPassportInfoProvider getServiceTokenInfoString: sid=%s", str);
        ServiceTokenResult blockGetServiceToken = TokenManager.getInstance().blockGetServiceToken(str);
        if (blockGetServiceToken != null) {
            try {
                jSONObject.put(AuthorizeActivityBase.KEY_SERVICETOKEN, blockGetServiceToken.serviceToken);
                jSONObject.put("security", blockGetServiceToken.security);
                jSONObject.put(BaseConstants.EXTRA_USER_ID, blockGetServiceToken.userId);
                jSONObject.put("cUserId", blockGetServiceToken.cUserId);
                jSONObject.put("ph", blockGetServiceToken.ph);
                jSONObject.put("slh", blockGetServiceToken.slh);
            } catch (Exception e) {
                L.contentprovider.e("MicoPassportInfoProvider getServiceTokenInfoString json put failed", e);
            }
        } else {
            L.contentprovider.i("MicoPassportInfoProvidergetServiceTokenInfoString failed");
        }
        return jSONObject.toString();
    }

    public static byte[] getPackageSignDigest(String str, Context context) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance(MessageDigestAlgorithms.SHA_256);
            for (Signature signature : context.getPackageManager().getPackageInfo(str, 64).signatures) {
                messageDigest.update(signature.toByteArray());
            }
        } catch (PackageManager.NameNotFoundException e) {
            L.contentprovider.e("getPackageSignDigest not found package", e);
        } catch (NoSuchAlgorithmException e2) {
            L.contentprovider.e("getPackageSignDigest no such algorithm", e2);
        }
        return messageDigest.digest();
    }

    public static String getPackageSignHex(String str, Context context) {
        return bytes2hex(getPackageSignDigest(str, context));
    }

    public static String bytes2hex(byte[] bArr) {
        if (bArr == null) {
            return "input is null";
        }
        String str = "";
        for (byte b2 : bArr) {
            String hexString = Integer.toHexString(b2 & 255);
            str = hexString.length() == 1 ? str + "0" + hexString : str + hexString;
        }
        return str.toUpperCase();
    }
}
