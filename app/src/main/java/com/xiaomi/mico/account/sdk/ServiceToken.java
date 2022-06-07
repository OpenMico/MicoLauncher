package com.xiaomi.mico.account.sdk;

import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.xiaomi.mipush.sdk.Constants;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/* loaded from: classes3.dex */
public class ServiceToken implements Serializable {
    private static final long serialVersionUID = -1387759219905842987L;
    private String authToken;
    private String cUserId;
    private long expireTimestamp;
    private String ph;
    private String serviceToken;
    private String sid;
    private String slh;
    private String ssecurity;

    /* JADX INFO: Access modifiers changed from: package-private */
    @Nullable
    public static ServiceToken a(@NonNull String str, @NonNull String str2) {
        ExtendedAuthToken b = b(str, str2);
        if (b == null) {
            return null;
        }
        return new ServiceToken(str, str2, b.authToken, b.security, 86400000 + System.currentTimeMillis());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(@NonNull String str, @NonNull String str2, @NonNull String str3) {
        this.cUserId = str;
        this.slh = str2;
        this.ph = str3;
    }

    private ServiceToken(String str, String str2, String str3, String str4, long j) {
        this.sid = str;
        this.authToken = str2;
        this.serviceToken = str3;
        this.ssecurity = str4;
        this.expireTimestamp = j;
    }

    private static ExtendedAuthToken b(@NonNull String str, @NonNull String str2) {
        String str3;
        String[] split = str2.split(Constants.ACCEPT_TIME_SEPARATOR_SP);
        String str4 = null;
        if (str.startsWith("weblogin:")) {
            str3 = split[0];
            if (TextUtils.isEmpty(str3)) {
                return null;
            }
        } else if (split.length != 2 || TextUtils.isEmpty(split[0]) || TextUtils.isEmpty(split[1])) {
            return null;
        } else {
            String str5 = split[0];
            str4 = split[1];
            str3 = str5;
        }
        return ExtendedAuthToken.build(str3, str4);
    }

    public String getAuthToken() {
        return this.authToken;
    }

    public String getServiceToken() {
        return this.serviceToken;
    }

    public String getSid() {
        return this.sid;
    }

    public String getSsecurity() {
        return this.ssecurity;
    }

    @Nullable
    public String getCUserId() {
        return this.cUserId;
    }

    @Nullable
    public String getSlh() {
        return this.slh;
    }

    @Nullable
    public String getPh() {
        return this.ph;
    }

    public boolean isServiceTokenValid() {
        return !TextUtils.isEmpty(this.serviceToken) && this.expireTimestamp - System.currentTimeMillis() >= 14400000;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.writeObject(this.sid);
        objectOutputStream.writeObject(this.authToken);
        objectOutputStream.writeObject(this.serviceToken);
        objectOutputStream.writeObject(this.ssecurity);
        objectOutputStream.writeLong(this.expireTimestamp);
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        this.sid = (String) objectInputStream.readObject();
        this.authToken = (String) objectInputStream.readObject();
        this.serviceToken = (String) objectInputStream.readObject();
        this.ssecurity = (String) objectInputStream.readObject();
        try {
            this.expireTimestamp = objectInputStream.readLong();
        } catch (IOException unused) {
            this.expireTimestamp = 0L;
        }
    }
}
