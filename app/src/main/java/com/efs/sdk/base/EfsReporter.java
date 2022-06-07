package com.efs.sdk.base;

import android.app.Application;
import android.content.Context;
import android.os.Message;
import android.text.TextUtils;
import android.util.Pair;
import android.webkit.ValueCallback;
import androidx.annotation.NonNull;
import com.efs.sdk.base.a.b.a;
import com.efs.sdk.base.a.b.e;
import com.efs.sdk.base.a.c.a.c;
import com.efs.sdk.base.a.d.a;
import com.efs.sdk.base.a.e.d;
import com.efs.sdk.base.http.HttpResponse;
import com.efs.sdk.base.observer.IConfigCallback;
import com.efs.sdk.base.observer.IEfsReporterObserver;
import com.efs.sdk.base.processor.action.ILogEncryptAction;
import com.efs.sdk.base.protocol.ILogProtocol;
import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes.dex */
public class EfsReporter {
    private static a sControllerCenter;

    private EfsReporter(Builder builder) {
        sControllerCenter = new a(builder);
    }

    @NonNull
    public com.efs.sdk.base.a.c.a getGlobalEnvStruct() {
        return a.a();
    }

    public void send(ILogProtocol iLogProtocol) {
        sControllerCenter.a(iLogProtocol);
    }

    public HttpResponse sendSyncImediatelly(String str, int i, String str2, File file) {
        return sendSyncImediatelly(str, i, str2, true, file);
    }

    public HttpResponse sendSyncImediatelly(String str, int i, String str2, boolean z, File file) {
        return a.a(str, i, str2, z, file);
    }

    public void getAllSdkConfig(String[] strArr, IConfigCallback iConfigCallback) {
        c a = c.a();
        a.e.put(iConfigCallback, strArr);
        if (!a.d.e.isEmpty()) {
            a.d();
        }
    }

    public Map<String, String> getAllConfig() {
        return c.a().c();
    }

    public Map<String, Object> getAllSdkConfig() {
        return new HashMap(c.a().d.e);
    }

    public void refreshConfig(String str) {
        c.a().a(str);
    }

    public void setEnableRefreshConfigFromRemote(boolean z) {
        c.a().c = z;
    }

    public void addPublicParams(@NonNull Map<String, String> map) {
        if (map.size() > 0) {
            getGlobalEnvStruct().a(map);
        }
    }

    public void registerCallback(int i, ValueCallback<Pair<Message, Message>> valueCallback) {
        com.efs.sdk.base.a.c.a globalEnvStruct = getGlobalEnvStruct();
        if (valueCallback != null) {
            List<ValueCallback<Pair<Message, Message>>> list = globalEnvStruct.n.get(Integer.valueOf(i));
            if (list == null) {
                list = new LinkedList<>();
                globalEnvStruct.n.putIfAbsent(Integer.valueOf(i), list);
            }
            list.add(valueCallback);
        }
    }

    /* loaded from: classes.dex */
    public static class Builder {
        private static Map<String, EfsReporter> sInstanceMap = new ConcurrentHashMap();
        private static boolean sUseAppContext = true;
        private final String TAG;
        private com.efs.sdk.base.a.c.a mGlobalEnvStruct;

        /* loaded from: classes.dex */
        public interface IPublicParams {
            Map<String, String> getRecordHeaders();
        }

        public Builder(@NonNull Application application, @NonNull String str, @NonNull String str2) {
            this(application.getApplicationContext(), str, str2);
        }

        public Builder(@NonNull Context context, @NonNull String str, @NonNull String str2) {
            this.TAG = "efs.reporter.builder";
            Context checkContext = checkContext(context);
            if (TextUtils.isEmpty(str)) {
                throw new RuntimeException("EfsReporter init, appid is empty");
            } else if (!TextUtils.isEmpty(str2)) {
                this.mGlobalEnvStruct = new com.efs.sdk.base.a.c.a();
                com.efs.sdk.base.a.c.a aVar = this.mGlobalEnvStruct;
                aVar.c = checkContext;
                aVar.a = str;
                aVar.b = str2;
            } else {
                throw new RuntimeException("EfsReporter init, secret is empty");
            }
        }

        public Builder uid(String str) {
            this.mGlobalEnvStruct.h = str;
            return this;
        }

        public Builder debug(boolean z) {
            this.mGlobalEnvStruct.f = z;
            return this;
        }

        public Builder enableWaStat(boolean z) {
            this.mGlobalEnvStruct.d = z;
            return this;
        }

        public Builder printLogDetail(boolean z) {
            this.mGlobalEnvStruct.g = z;
            return this;
        }

        public Builder publicParams(@NonNull IPublicParams iPublicParams) {
            if (iPublicParams.getRecordHeaders() != null && iPublicParams.getRecordHeaders().size() > 0) {
                this.mGlobalEnvStruct.a(iPublicParams.getRecordHeaders());
            }
            return this;
        }

        public Builder publicParams(@NonNull Map<String, String> map) {
            if (map.size() > 0) {
                this.mGlobalEnvStruct.a(map);
            }
            return this;
        }

        public Builder logEncryptAction(ILogEncryptAction iLogEncryptAction) {
            this.mGlobalEnvStruct.m = iLogEncryptAction;
            return this;
        }

        public Builder configRefreshDelayMills(long j) {
            this.mGlobalEnvStruct.j = j;
            return this;
        }

        public Builder configRefreshAction(@NonNull IConfigRefreshAction iConfigRefreshAction) {
            c.a().b = iConfigRefreshAction;
            return this;
        }

        public Builder maxConcurrentUploadCnt(int i) {
            d.a().a = i;
            return this;
        }

        public Builder addEfsReporterObserver(IEfsReporterObserver iEfsReporterObserver) {
            com.efs.sdk.base.a.c.a aVar = this.mGlobalEnvStruct;
            if (!aVar.o.contains(iEfsReporterObserver)) {
                aVar.o.add(iEfsReporterObserver);
            }
            return this;
        }

        public Builder intl(boolean z) {
            this.mGlobalEnvStruct.i = z;
            return this;
        }

        public Builder efsDirRootName(String str) {
            com.efs.sdk.base.a.h.a.a(str);
            return this;
        }

        public Builder enableSendLog(boolean z) {
            this.mGlobalEnvStruct.e = z;
            return this;
        }

        public com.efs.sdk.base.a.c.a getGlobalEnvStruct() {
            return this.mGlobalEnvStruct;
        }

        public EfsReporter build() {
            String str = getGlobalEnvStruct().a;
            if (!sInstanceMap.containsKey(str)) {
                synchronized (EfsReporter.class) {
                    if (!sInstanceMap.containsKey(str)) {
                        EfsReporter efsReporter = new EfsReporter(this);
                        sInstanceMap.put(str, efsReporter);
                        return efsReporter;
                    }
                }
            }
            com.efs.sdk.base.a.h.d.a("efs.reporter.builder", "efs-core: duplicate init", null);
            checkParam(str);
            return sInstanceMap.get(str);
        }

        private void checkParam(String str) {
            com.efs.sdk.base.a.c.a globalEnvStruct = sInstanceMap.get(str).getGlobalEnvStruct();
            if (!globalEnvStruct.c.equals(getGlobalEnvStruct().c)) {
                throw new RuntimeException("efs-core: duplicate init, but application context is different");
            } else if (!TextUtils.isEmpty(globalEnvStruct.b) && !globalEnvStruct.b.equals(getGlobalEnvStruct().b)) {
                throw new RuntimeException("efs-core: duplicate init, but secret is different");
            } else if (globalEnvStruct.i == getGlobalEnvStruct().i) {
                if (!TextUtils.isEmpty(getGlobalEnvStruct().h) && !getGlobalEnvStruct().h.equals(globalEnvStruct.h)) {
                    com.efs.sdk.base.a.h.d.a("efs.reporter.builder", "efs-core: duplicate init, but  uid is different", null);
                }
                if (getGlobalEnvStruct().a() != null && getGlobalEnvStruct().a().size() > 0) {
                    globalEnvStruct.a(getGlobalEnvStruct().a());
                }
            } else {
                throw new RuntimeException("efs-core: duplicate init, but intl setting is different");
            }
        }

        private static Context checkContext(Context context) {
            if (context == null) {
                com.efs.sdk.base.a.h.d.b("efs.base", "context can not be null!", null);
                throw new NullPointerException();
            } else if (!sUseAppContext || (context instanceof Application) || ((context = context.getApplicationContext()) != null && (context instanceof Application))) {
                return context;
            } else {
                com.efs.sdk.base.a.h.d.b("efs.base", "Can not get Application context from given context!", null);
                throw new IllegalArgumentException("Can not get Application context from given context!");
            }
        }
    }

    public void flushRecordLogImmediately(String str) {
        com.efs.sdk.base.a.b.a aVar;
        aVar = a.b.a;
        e a = aVar.c.a((byte) 1);
        if (a != null) {
            a.a(str);
        }
    }
}
