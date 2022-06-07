package com.xiaomi.miot.lib.impl;

import android.app.Application;
import android.text.TextUtils;
import com.xiaomi.miot.lib.MiotCallback;
import com.xiaomi.miot.lib.MiotConfig;
import com.xiaomi.smarthome.core.entity.net.KeyValuePair;
import com.xiaomi.smarthome.core.entity.net.NetError;
import com.xiaomi.smarthome.core.entity.net.NetRequest;
import com.xiaomi.smarthome.core.entity.net.NetResult;
import com.xiaomi.smarthome.core.server.internal.NetCallback;
import com.xiaomi.smarthome.core.server.internal.account.AccountCallBack;
import com.xiaomi.smarthome.core.server.internal.account.AccountManager;
import com.xiaomi.smarthome.core.server.internal.account.NetApiConfig;
import com.xiaomi.smarthome.core.server.internal.api.SmartHomeRc4Api;
import com.xiaomi.smarthome.core.server.internal.util.UserAgentUtil;
import com.xiaomi.smarthome.library.http.Error;
import com.xiaomi.smarthome.library.http.HttpApi;
import com.xiaomi.smarthome.library.http.Request;
import com.xiaomi.smarthome.library.http.async.FileAsyncHandler;
import com.xiaomi.smarthome.setting.LoginSetting;
import com.xiaomi.smarthome.setting.ServerSetting;
import com.xiaomi.youpin.login.entity.account.MiServiceTokenInfo;
import java.io.File;
import java.net.CookieManager;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import okhttp3.Dispatcher;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.internal.Util;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public abstract class MiotManagerBase {
    protected MiotConfig config = null;
    protected boolean isInited = false;
    private OkHttpClient mOkHttpClient = null;

    protected abstract boolean afterIntialized();

    protected abstract void beforeInitialize();

    public final String getServerID() {
        return "xiaomiio";
    }

    public final String getServerDomain() {
        return LoginSetting.getDomainBySid(getServerID());
    }

    public boolean isInitialized() {
        return this.isInited;
    }

    private static final Long getServerTimeDiff(String str, Long l) {
        String str2 = "https://api.io.mi.com/app";
        if (!TextUtils.isEmpty(str) && !str.equals(ServerSetting.SERVER_CN)) {
            str2 = "https://" + str + ".api.io.mi.com/app";
        }
        try {
            URLConnection openConnection = new URL(str2).openConnection();
            openConnection.setConnectTimeout(10000);
            try {
                openConnection.connect();
                return Long.valueOf(openConnection.getDate() - System.currentTimeMillis());
            } catch (Exception e) {
                e.printStackTrace();
                return l;
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            return l;
        }
    }

    protected synchronized boolean doInitialize(final MiotConfig miotConfig) {
        if (!isInitialized() && miotConfig != null && this.config == null && miotConfig.getApplication() != null) {
            this.config = miotConfig;
            String serverName = miotConfig.getServerName();
            if (TextUtils.isEmpty(serverName)) {
                serverName = ServerSetting.SERVER_CN;
            }
            Locale locale = miotConfig.getLocale();
            Long serverTimeDiff = miotConfig.getServerTimeDiff();
            if (serverTimeDiff == null) {
                serverTimeDiff = getServerTimeDiff(serverName, 0L);
            }
            beforeInitialize();
            NetApiConfig.Builder serverEnv = new NetApiConfig.Builder().init(miotConfig.getApplication()).setIsDebug(miotConfig.isDebug()).setServer(serverName).setServerEnv(miotConfig.isPreview() ? "preview" : "release");
            if (locale == null) {
                locale = Locale.getDefault();
            }
            AccountManager.init(serverEnv.setLocal(locale).build());
            AccountManager.getInstance().setMiServiceToken(new MiServiceTokenInfo(miotConfig.getMiId(), getServerID(), miotConfig.getEncryptedUserId(), miotConfig.getServiceToken(), miotConfig.getServiceSecurity(), getServerDomain(), serverTimeDiff.longValue()));
            AccountManager.getInstance().setAccountCallBack(new AccountCallBack() { // from class: com.xiaomi.miot.lib.impl.MiotManagerBase.1
                @Override // com.xiaomi.smarthome.core.server.internal.account.AccountCallBack
                public void doUnAuthorized(int i) {
                    miotConfig.onAuthorizationError(i);
                }
            });
            return afterIntialized();
        }
        return false;
    }

    public Application getApplication() {
        MiotConfig miotConfig = this.config;
        if (miotConfig != null) {
            return miotConfig.getApplication();
        }
        return null;
    }

    protected boolean sendRequestExec(String str, JSONObject jSONObject, final MiotCallback<JSONObject> miotCallback) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new KeyValuePair("data", jSONObject == null ? "" : jSONObject.toString()));
        new ArrayList().add(new KeyValuePair("User-Agent", UserAgentUtil.getUserAgent(UserAgentUtil.FOR_APP)));
        SmartHomeRc4Api.getInstance().sendRequest(new NetRequest.Builder().method("POST").path(str).addQueries(arrayList).build(), new NetCallback<NetResult, NetError>() { // from class: com.xiaomi.miot.lib.impl.MiotManagerBase.2
            public void onCache(NetResult netResult) {
            }

            public void onSuccess(NetResult netResult) {
                try {
                    miotCallback.onSuccess(new JSONObject(netResult.mResponse));
                } catch (Exception e) {
                    e.printStackTrace();
                    miotCallback.onFailure(1, e.getMessage());
                }
            }

            public void onFailure(NetError netError) {
                miotCallback.onFailure(netError.getCode(), netError.getDetail());
            }
        });
        return true;
    }

    private OkHttpClient getHttpClient() {
        if (this.mOkHttpClient == null) {
            this.mOkHttpClient = new OkHttpClient.Builder().dispatcher(new Dispatcher(new ThreadPoolExecutor(6, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue(), Util.threadFactory("OkHttp Dispatcher", false)))).connectTimeout(20L, TimeUnit.SECONDS).readTimeout(30L, TimeUnit.SECONDS).writeTimeout(30L, TimeUnit.SECONDS).cookieJar(new JavaNetCookieJar(new CookieManager())).build();
        }
        return this.mOkHttpClient;
    }

    protected void downloadFileExec(String str, String str2, final MiotCallback<File> miotCallback) {
        File createFileIfNotExists = ZipFileUtils.createFileIfNotExists(str2);
        HttpApi.sendRequest(getHttpClient(), new Request.Builder().method("GET").url(str).build(), new FileAsyncHandler(createFileIfNotExists) { // from class: com.xiaomi.miot.lib.impl.MiotManagerBase.3
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.xiaomi.smarthome.library.http.async.FileAsyncHandler
            public void onSuccess(File file, Response response) {
                if (file == null || !file.exists()) {
                    miotCallback.onFailure(1, response == null ? "failed to load" : response.message());
                } else {
                    miotCallback.onSuccess(file);
                }
            }

            @Override // com.xiaomi.smarthome.library.http.async.FileAsyncHandler, com.xiaomi.smarthome.library.http.async.AsyncHandler
            public void onFailure(Error error, Exception exc, Response response) {
                if (exc != null) {
                    exc.printStackTrace();
                }
                miotCallback.onFailure(2, response == null ? exc == null ? "error" : exc.getMessage() : response.message());
            }
        });
    }
}
