package com.xiaomi.account.auth;

import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.text.TextUtils;
import android.util.Log;
import com.xiaomi.account.IXiaomiAuthResponse;
import com.xiaomi.account.XiaomiOAuthResponse;
import com.xiaomi.account.http.HttpClient;
import com.xiaomi.account.http.Request;
import com.xiaomi.account.http.Response;
import com.xiaomi.account.http.UrlConnHttpFactory;
import com.xiaomi.account.openauth.AccountAuth;
import com.xiaomi.account.openauth.AuthorizeActivityBase;
import com.xiaomi.account.openauth.BuildConfig;
import com.xiaomi.account.openauth.XMAuthericationException;
import com.xiaomi.account.openauth.XiaomiOAuthConstants;
import com.xiaomi.account.openauth.XiaomiOAuthResults;
import com.xiaomi.account.utils.OAuthUrlPaser;
import com.xiaomi.accountsdk.account.data.BaseConstants;
import com.xiaomi.accountsdk.diagnosis.DiagnosisLog;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

/* loaded from: classes2.dex */
public class WebViewOauth implements XiaomiOAuth {
    private static final String AUTHORIZE_PATH = XiaomiOAuthConstants.OAUTH2_HOST + "/oauth2/authorize";
    private static final String LOCALE_KEY_IN_URL = "_locale";
    private static final String TAG = "WebViewOauth";
    private String mAppId;
    private Context mContext;
    private String mRedirectUrl;

    public WebViewOauth(Context context, String str, String str2) {
        this.mContext = context;
        this.mAppId = str;
        this.mRedirectUrl = str2;
    }

    @Override // com.xiaomi.account.auth.XiaomiOAuth
    public XiaomiOAuthResults startOAuth(Activity activity, OAuthConfig oAuthConfig) throws AuthenticatorException, IOException, XMAuthericationException, InterruptedException, OperationCanceledException {
        WeakReference weakReference = new WeakReference(activity);
        try {
            return quietOAuth(oAuthConfig);
        } catch (AuthenticatorException unused) {
            Log.e(TAG, "quietOAuth failed");
            Activity activity2 = (Activity) weakReference.get();
            if (activity2 == null || activity2.isFinishing()) {
                Log.e(TAG, "activity is null");
                Bundle bundle = new Bundle();
                bundle.putInt(XiaomiOAuthConstants.EXTRA_ERROR_CODE, XiaomiOAuthConstants.ERROR_NEED_AUTHORIZE);
                bundle.putString(XiaomiOAuthConstants.EXTRA_ERROR_DESCRIPTION, "activity is null");
                return XiaomiOAuthResults.parseBundle(bundle);
            }
            final CountDownLatch countDownLatch = new CountDownLatch(1);
            final ArrayList arrayList = new ArrayList();
            activity2.startActivity(getIntent(activity2, oAuthConfig, new IXiaomiAuthResponse.Stub() { // from class: com.xiaomi.account.auth.WebViewOauth.1
                @Override // com.xiaomi.account.IXiaomiAuthResponse
                public void onResult(Bundle bundle2) throws RemoteException {
                    arrayList.add(XiaomiOAuthResults.parseBundle(bundle2));
                    countDownLatch.countDown();
                }

                @Override // com.xiaomi.account.IXiaomiAuthResponse
                public void onCancel() throws RemoteException {
                    countDownLatch.countDown();
                }
            }));
            countDownLatch.await();
            if (arrayList.size() > 0) {
                return (XiaomiOAuthResults) arrayList.get(0);
            }
            throw new OperationCanceledException();
        }
    }

    private XiaomiOAuthResults quietOAuth(OAuthConfig oAuthConfig) throws AuthenticatorException, IOException, XMAuthericationException {
        Log.i(TAG, "WebViewOauth quietOAuth start...");
        if (oAuthConfig.accountAuth != null) {
            AccountAuth accountAuth = oAuthConfig.accountAuth;
            for (int i = 0; i < 2; i++) {
                HttpClient createHttpClient = new UrlConnHttpFactory().createHttpClient();
                HashMap hashMap = new HashMap();
                HashMap hashMap2 = new HashMap();
                hashMap2.put(BaseConstants.EXTRA_USER_ID, accountAuth.getUserId());
                hashMap2.put(AuthorizeActivityBase.KEY_SERVICETOKEN, accountAuth.getServiceToken());
                hashMap.put("Cookie", joinMap(hashMap2, "; "));
                hashMap.put("User-Agent", (System.getProperty("http.agent") + " Passport/OAuthSDK/" + BuildConfig.VERSION_NAME) + " mi/OAuthSDK/VersionCode/90");
                Response excute = createHttpClient.excute(new Request.Builder().url(getUrl(oAuthConfig)).followRedirects(false).headers(hashMap).build());
                DiagnosisLog.get().log("quietOAuth.response.location=" + excute.location);
                if (excute.location == null) {
                    throw new AuthenticatorException();
                } else if (excute.location.startsWith(this.mRedirectUrl)) {
                    Bundle parse = OAuthUrlPaser.parse(excute.location);
                    if (parse != null) {
                        Log.i(TAG, "WebViewOauth.quietOAuth.sucess");
                        return XiaomiOAuthResults.parseBundle(parse);
                    }
                    Log.e(TAG, "location is null need user to Authorization");
                    throw new XMAuthericationException("parse url fail:" + excute.location);
                } else {
                    accountAuth.invalideServiceToken();
                }
            }
            throw new AuthenticatorException();
        }
        Log.i(TAG, "WebViewOauth..quietOAuth..accountAuth is null");
        throw new AuthenticatorException();
    }

    private Intent getIntent(Activity activity, OAuthConfig oAuthConfig, IXiaomiAuthResponse iXiaomiAuthResponse) {
        Intent intent = new Intent(activity, oAuthConfig.authorizeActivityClazz);
        intent.putExtra("url", getUrl(oAuthConfig));
        intent.putExtra(AuthorizeActivityBase.KEY_REDIRECT_URI, this.mRedirectUrl);
        intent.putExtra(AuthorizeActivityBase.KEY_KEEP_COOKIES, oAuthConfig.keepCookies);
        intent.putExtra("extra_response", new XiaomiOAuthResponse(iXiaomiAuthResponse));
        AccountAuth accountAuth = oAuthConfig.accountAuth;
        if (accountAuth != null) {
            HashMap hashMap = new HashMap();
            hashMap.put(BaseConstants.EXTRA_USER_ID, accountAuth.getUserId());
            hashMap.put(AuthorizeActivityBase.KEY_SERVICETOKEN, accountAuth.getServiceToken());
            intent.putExtra(AuthorizeActivityBase.KEY_USERID, "userId=" + accountAuth.getUserId());
            intent.putExtra(AuthorizeActivityBase.KEY_SERVICETOKEN, "serviceToken=" + accountAuth.getServiceToken());
        }
        PhoneInfo phoneInfo = oAuthConfig.phoneInfo;
        if (phoneInfo != null) {
            intent.putExtras(phoneInfo.blokingGetPhoneInfo(getDefaultSmsSlotId()));
        }
        return intent;
    }

    private int getDefaultSmsSlotId() {
        if (Build.VERSION.SDK_INT < 24) {
            return 0;
        }
        try {
            int defaultDataSubscriptionId = SubscriptionManager.getDefaultDataSubscriptionId();
            List<SubscriptionInfo> activeSubscriptionInfoList = SubscriptionManager.from(this.mContext).getActiveSubscriptionInfoList();
            if (activeSubscriptionInfoList == null) {
                return 0;
            }
            for (SubscriptionInfo subscriptionInfo : activeSubscriptionInfoList) {
                if (subscriptionInfo.getSubscriptionId() == defaultDataSubscriptionId) {
                    return subscriptionInfo.getSimSlotIndex();
                }
            }
            return 0;
        } catch (SecurityException e) {
            Log.e(TAG, e.toString());
            return 0;
        }
    }

    private String getUrl(OAuthConfig oAuthConfig) {
        Bundle bundle = new Bundle();
        bundle.putString("client_id", this.mAppId);
        bundle.putString(AuthorizeActivityBase.KEY_REDIRECT_URI, this.mRedirectUrl);
        bundle.putString("response_type", oAuthConfig.responseType);
        bundle.putString("scope", oAuthConfig.scopes);
        bundle.putString(XiaomiOAuthConstants.EXTRA_STATE_2, oAuthConfig.state);
        if (oAuthConfig.skipConfirm != null) {
            bundle.putString("skip_confirm", String.valueOf(oAuthConfig.skipConfirm));
        }
        if (oAuthConfig.loginType != null) {
            bundle.putString(XiaomiOAuthConstants.EXTRA_LOGIN_TYPE, oAuthConfig.loginType);
        }
        if (oAuthConfig.hideSwitch != null) {
            bundle.putString("_hideSwitch", "true");
        }
        bundle.putString("pt", "" + oAuthConfig.platform);
        bundle.putString("device_id", oAuthConfig.deviceID);
        bundle.putString("display", oAuthConfig.display);
        addLocaleIfNeeded(bundle);
        return AUTHORIZE_PATH + "?" + parseBundle(bundle);
    }

    private void addLocaleIfNeeded(Bundle bundle) {
        if (bundle != null && !bundle.containsKey(LOCALE_KEY_IN_URL)) {
            String localeString = getLocaleString(Locale.getDefault());
            if (!TextUtils.isEmpty(localeString)) {
                bundle.putString(LOCALE_KEY_IN_URL, localeString);
            }
        }
    }

    private static String getLocaleString(Locale locale) {
        if (locale == null) {
            return null;
        }
        String language = locale.getLanguage();
        String country = locale.getCountry();
        return TextUtils.isEmpty(country) ? language : String.format("%s_%s", language, country);
    }

    private String parseBundle(Bundle bundle) {
        if (bundle == null) {
            return "";
        }
        ArrayList arrayList = new ArrayList();
        for (String str : bundle.keySet()) {
            String string = bundle.getString(str);
            if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(string)) {
                arrayList.add(new BasicNameValuePair(str, string));
            }
        }
        return URLEncodedUtils.format(arrayList, "UTF-8");
    }

    private String getCookie(String str, String str2) {
        return str + "=" + str2;
    }

    protected static String joinMap(Map<String, String> map, String str) {
        if (map == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (i > 0) {
                sb.append(str);
            }
            sb.append(entry.getKey());
            sb.append("=");
            sb.append(entry.getValue());
            i++;
        }
        return sb.toString();
    }
}
