package com.xiaomi.mico.token;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.text.TextUtils;
import com.xiaomi.account.openauth.AuthorizeActivityBase;
import com.xiaomi.accountsdk.account.data.BaseConstants;
import com.xiaomi.accountsdk.request.SimpleRequestForAccount;
import com.xiaomi.mico.account.sdk.LoginManager;
import com.xiaomi.mico.account.sdk.ServiceToken;
import com.xiaomi.mico.settingslib.core.AESUtils;
import com.xiaomi.micolauncher.application.Constants;
import com.xiaomi.micolauncher.common.DebugHelper;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.passport.accountmanager.MiAccountManager;
import com.xiaomi.passport.servicetoken.ServiceTokenResult;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/* loaded from: classes3.dex */
public class TokenManager {
    public static final String AI_SERVICE_SID = "ai-service";
    public static final String MICO_SID = "micoapi";
    public static final String XIAOMI_ACCOUNT_TYPE = "com.xiaomi";
    static final /* synthetic */ boolean a = !TokenManager.class.desiredAssertionStatus();
    private static volatile TokenManager b;
    private ICookieParams f;
    private Context g;
    private CookieJar d = new DefaultCookieJar();
    private HashMap<String, ServiceToken> e = new HashMap<>();
    private LoginManager c = LoginManager.get();

    /* loaded from: classes3.dex */
    public interface ICookieParams {
        String getDeviceId();

        String getMiBrainId();

        ArrayList<String> getUrls();
    }

    public static /* synthetic */ String c(String str) throws Exception {
        return str;
    }

    private TokenManager(Context context, ICookieParams iCookieParams) {
        this.g = context.getApplicationContext();
        LoginManager.init(context);
        this.f = iCookieParams;
        syncAccountInfo();
    }

    public static void init(Context context, ICookieParams iCookieParams) {
        if (b == null) {
            synchronized (TokenManager.class) {
                if (b == null) {
                    b = new TokenManager(context, iCookieParams);
                }
            }
        }
    }

    public static boolean isInited() {
        boolean z = true;
        if (b != null) {
            return true;
        }
        synchronized (TokenManager.class) {
            if (b == null) {
                z = false;
            }
        }
        return z;
    }

    public static TokenManager getInstance() {
        TokenManager tokenManager;
        if (b != null) {
            return b;
        }
        synchronized (TokenManager.class) {
            if (b != null) {
                tokenManager = b;
            } else {
                throw new IllegalStateException("call TokenManager.init first");
            }
        }
        return tokenManager;
    }

    public void migrateAccount(Context context) {
        if (!hasValidAccount()) {
            Account[] accountsByType = MiAccountManager.get(context).getAccountsByType("com.xiaomi");
            if (accountsByType.length > 0) {
                Account account = accountsByType[0];
                AccountManager.get(context).addAccountExplicitly(account, MiAccountManager.get(context).getPassword(account), null);
            }
        }
    }

    public boolean hasValidAccount() {
        return this.c.hasValidAccount();
    }

    public static boolean hasValidAccount(Context context) {
        return AccountManager.get(context).getAccountsByType("com.xiaomi").length > 0;
    }

    public String getUserId() {
        return this.c.getUserId();
    }

    public String getCUserId() {
        return this.c.getCUserId();
    }

    public void addOrUpdateCookies() {
        b("micoapi");
    }

    public synchronized void syncAccountInfo() {
        Schedulers.io().scheduleDirect(new Runnable() { // from class: com.xiaomi.mico.token.-$$Lambda$VLD1PFx1WOMoOTuCv6BRQ96aaxA
            @Override // java.lang.Runnable
            public final void run() {
                TokenManager.this.syncAccountInfoSync();
            }
        });
    }

    public synchronized void syncAccountInfoSync() {
        if (!hasValidAccount()) {
            L.login.e("syncAccountInfoSync No valid account found");
        }
        ServiceToken peekServiceToken = this.c.peekServiceToken("micoapi");
        if (peekServiceToken == null) {
            try {
                peekServiceToken = this.c.blockGetServiceToken("micoapi");
            } catch (LoginManager.NeedUserInteractionException unused) {
            }
        }
        if (peekServiceToken != null) {
            a("micoapi", peekServiceToken);
            b("micoapi");
        }
        try {
            ServiceToken blockGetServiceToken = this.c.blockGetServiceToken("ai-service");
            if (blockGetServiceToken != null) {
                a("ai-service", blockGetServiceToken);
            }
        } catch (LoginManager.NeedUserInteractionException unused2) {
        }
    }

    public ServiceTokenResult blockGetServiceToken(String str) {
        return a(str);
    }

    private synchronized ServiceTokenResult a(String str) {
        L.login.d("TokenManager innerBlockGetServiceToken sid=%s", str);
        if (Looper.myLooper() == Looper.getMainLooper()) {
            L.login.w("！！！ getServiceToken is running in main thread");
        }
        this.c.invalidateServiceToken(str);
        removeServiceInfo(str);
        try {
            ServiceToken blockGetServiceToken = this.c.blockGetServiceToken(str);
            if (blockGetServiceToken == null) {
                L.login.e("TokenManager get service token is null sid=%s", str);
                return null;
            }
            L.login.d("TokenManager refresh service token success sid=%s", str);
            a(str, blockGetServiceToken);
            if ("micoapi".equals(str)) {
                b(str);
            }
            return new ServiceTokenResult.Builder(str).userId(this.c.getUserId()).cUserId(blockGetServiceToken.getCUserId()).serviceToken(blockGetServiceToken.getServiceToken()).security(blockGetServiceToken.getSsecurity()).ph(blockGetServiceToken.getPh()).slh(blockGetServiceToken.getSlh()).build();
        } catch (LoginManager.NeedUserInteractionException e) {
            L.login.e("TokenManager get service token need user Interaction", e);
            Intent intent = e.getIntent();
            intent.setFlags(268435456);
            this.g.startActivity(intent);
            return new ServiceTokenResult.Builder(str).errorCode(ServiceTokenResult.ErrorCode.ERROR_USER_INTERACTION_NEEDED).intent(e.getIntent()).build();
        }
    }

    public Observable<String> refreshServiceToken(final String str) {
        L.login.i("try to refresh service token %s", str);
        return Observable.unsafeCreate(new ObservableSource() { // from class: com.xiaomi.mico.token.-$$Lambda$TokenManager$pKZZ3tq6iHObf9GAi0kNfSlDwg0
            @Override // io.reactivex.ObservableSource
            public final void subscribe(Observer observer) {
                TokenManager.this.a(str, observer);
            }
        }).subscribeOn(Schedulers.io());
    }

    public /* synthetic */ void a(String str, Observer observer) {
        ServiceTokenResult a2 = a(str);
        if (a2 == null || a2.errorCode != ServiceTokenResult.ErrorCode.ERROR_NONE) {
            observer.onError(new Exception("refresh service token failed"));
            return;
        }
        observer.onNext("");
        observer.onComplete();
    }

    private void b(String str) {
        ICookieParams iCookieParams;
        ArrayList<String> urls;
        if ("micoapi".equals(str) && (iCookieParams = this.f) != null && (urls = iCookieParams.getUrls()) != null) {
            Iterator<String> it = urls.iterator();
            while (it.hasNext()) {
                String next = it.next();
                if (!TextUtils.isEmpty(next)) {
                    HttpUrl parse = HttpUrl.parse(next);
                    if (a || parse != null) {
                        ArrayList arrayList = new ArrayList(2);
                        Cookie.Builder path = new Cookie.Builder().hostOnlyDomain(parse.host()).httpOnly().path("/");
                        if (parse.isHttps()) {
                            path.secure();
                        }
                        String miBrainId = this.f.getMiBrainId();
                        if (!TextUtils.isEmpty(miBrainId)) {
                            arrayList.add(path.name("shuidiAppId").value(miBrainId).build());
                        } else {
                            L.login.e("mi brain id is empty");
                        }
                        String deviceId = this.f.getDeviceId();
                        if (!TextUtils.isEmpty(deviceId)) {
                            arrayList.add(path.name(SimpleRequestForAccount.COOKIE_NAME_DEVICE_ID).value(deviceId).build());
                        } else {
                            L.login.e("device id is empty");
                        }
                        arrayList.add(path.name(BaseConstants.EXTRA_USER_ID).value(getUserId()).build());
                        arrayList.add(path.name("cUserId").value(getCUserId()).build());
                        arrayList.add(path.name(AuthorizeActivityBase.KEY_SERVICETOKEN).value(getServiceInfo(str).getServiceToken()).build());
                        this.d.saveFromResponse(parse, arrayList);
                    } else {
                        throw new AssertionError();
                    }
                }
            }
        }
    }

    public ServiceToken getServiceInfo(String str) {
        return this.e.get(str);
    }

    public ServiceToken removeServiceInfo(String str) {
        return this.e.remove(str);
    }

    private void a(String str, ServiceToken serviceToken) {
        L.login.d("update service token %s %s %s", str, serviceToken.getServiceToken(), serviceToken.getSsecurity());
        this.e.put(str, serviceToken);
    }

    public CookieJar getCookieJar() {
        return this.d;
    }

    public boolean hasValidServiceToken() {
        ServiceToken peekServiceToken = this.c.peekServiceToken("micoapi");
        return peekServiceToken != null && peekServiceToken.isServiceTokenValid();
    }

    public Observable<String> webLogin(final Activity activity, final String str) {
        if (TextUtils.isEmpty(str)) {
            return Observable.error(new Throwable(""));
        }
        return Observable.create(new ObservableOnSubscribe() { // from class: com.xiaomi.mico.token.-$$Lambda$TokenManager$WvXCJaZR3C6ebvbglTOhSkC9VDI
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                TokenManager.this.a(activity, str, observableEmitter);
            }
        }).subscribeOn(Schedulers.computation()).map($$Lambda$TokenManager$hNEnnO8mGK7mGTuOEeyfpgOGes.INSTANCE);
    }

    public /* synthetic */ void a(Activity activity, String str, ObservableEmitter observableEmitter) throws Exception {
        observableEmitter.onNext(this.c.blockWebLogin(activity, str));
        observableEmitter.onComplete();
    }

    public static void fetchToken() {
        getInstance().blockGetServiceToken(Constants.MICO_SID);
    }

    public String getEncryptedToken(String str) {
        String str2 = "";
        ServiceToken serviceToken = this.e.get(str);
        if (serviceToken != null) {
            String serviceToken2 = serviceToken.getServiceToken();
            try {
                str2 = AESUtils.encrypt(serviceToken2, com.xiaomi.mico.settingslib.core.Constants.ENCRYPT_KEY);
                if (DebugHelper.isDebugVersion()) {
                    L.encryption.d("original token is %s ,encrypted token is %s", serviceToken2, str2);
                }
            } catch (Exception e) {
                L.encryption.e("AESUtil.encrypt errors occurred", e);
            }
        } else {
            L.encryption.e("serviceToken is null");
        }
        return str2;
    }
}
