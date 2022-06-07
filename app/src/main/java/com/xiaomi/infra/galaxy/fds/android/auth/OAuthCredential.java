package com.xiaomi.infra.galaxy.fds.android.auth;

import com.google.gson.Gson;
import com.xiaomi.infra.galaxy.fds.android.exception.GalaxyFDSClientException;
import com.xiaomi.infra.galaxy.fds.android.model.StorageAccessToken;
import com.xiaomi.micolauncher.module.music.MusicGroupListActivity;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import kotlin.text.Typography;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;

/* loaded from: classes3.dex */
public class OAuthCredential implements GalaxyFDSCredential {
    private final String a = "OAuth";
    private final String b;
    private final StorageAccessToken c;

    public OAuthCredential(String str, String str2, String str3, String str4, String str5, String str6, String str7) throws GalaxyFDSClientException {
        this.b = str2;
        this.c = a(str, str2, str3, str4, str5, str6, str7);
    }

    private StorageAccessToken a(String str, String str2, String str3, String str4, String str5, String str6, String str7) throws GalaxyFDSClientException {
        try {
            DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(str + "/?STORAGE_ACCESS_TOKEN" + MusicGroupListActivity.SPECIAL_SYMBOL + "APP_ID=" + str2 + MusicGroupListActivity.SPECIAL_SYMBOL + "OAUTH_APPID=" + str3 + MusicGroupListActivity.SPECIAL_SYMBOL + "OAUTH_ACCESS_TOKEN=" + str4 + MusicGroupListActivity.SPECIAL_SYMBOL + "OAUTH_PROVIDER=" + str5 + MusicGroupListActivity.SPECIAL_SYMBOL + "OAUTH_MAC_ALGORITHM=" + str7 + MusicGroupListActivity.SPECIAL_SYMBOL + "OAUTH_MAC_KEY=" + str6);
            httpGet.setHeader("Authorization", "OAuth");
            HttpResponse execute = defaultHttpClient.execute(httpGet);
            if (execute.getStatusLine().getStatusCode() == 200) {
                InputStream content = execute.getEntity().getContent();
                StorageAccessToken storageAccessToken = (StorageAccessToken) new Gson().fromJson((Reader) new InputStreamReader(content), (Class<Object>) StorageAccessToken.class);
                content.close();
                return storageAccessToken;
            }
            throw new GalaxyFDSClientException("Failed to get the storage access token from FDS server. URI:" + httpGet.getURI().toString() + ".Reason:" + execute.getStatusLine().toString());
        } catch (IOException e) {
            throw new GalaxyFDSClientException("Failed to get the storage access token", e);
        }
    }

    @Override // com.xiaomi.infra.galaxy.fds.android.auth.GalaxyFDSCredential
    public void addHeader(HttpRequestBase httpRequestBase) {
        httpRequestBase.setHeader("Authorization", "OAuth");
    }

    @Override // com.xiaomi.infra.galaxy.fds.android.auth.GalaxyFDSCredential
    public String addParam(String str) {
        StringBuilder sb = new StringBuilder(str);
        if (str.indexOf(63) == -1) {
            sb.append('?');
        } else {
            sb.append(Typography.amp);
        }
        sb.append("APP_ID");
        sb.append('=');
        sb.append(this.b);
        sb.append(Typography.amp);
        sb.append("STORAGE_ACCESS_TOKEN");
        sb.append('=');
        sb.append(this.c.getToken());
        return sb.toString();
    }
}
