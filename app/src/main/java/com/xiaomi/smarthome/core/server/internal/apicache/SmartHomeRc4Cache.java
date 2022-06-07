package com.xiaomi.smarthome.core.server.internal.apicache;

import android.text.TextUtils;
import android.util.Pair;
import com.xiaomi.smarthome.core.entity.net.NetRequest;
import com.xiaomi.smarthome.globalsetting.GlobalSetting;
import com.xiaomi.smarthome.library.crypto.MD5Util;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* loaded from: classes4.dex */
public class SmartHomeRc4Cache {
    public static final int FILE_CACHE_MAX = 50;
    private static SmartHomeRc4Cache a;
    private static Object b = new Object();
    private Map<String, WeakReference<String>> d = new ConcurrentHashMap();
    private ExecutorService c = Executors.newCachedThreadPool();

    /* loaded from: classes4.dex */
    public interface CacheCallback {
        void onResult(String str);
    }

    private SmartHomeRc4Cache() {
    }

    public static SmartHomeRc4Cache getInstance() {
        if (a == null) {
            synchronized (b) {
                if (a == null) {
                    a = new SmartHomeRc4Cache();
                }
            }
        }
        return a;
    }

    public void getCache(final NetRequest netRequest, final CacheCallback cacheCallback) {
        this.c.submit(new Runnable() { // from class: com.xiaomi.smarthome.core.server.internal.apicache.SmartHomeRc4Cache.1
            @Override // java.lang.Runnable
            public void run() {
                String str;
                CacheCallback cacheCallback2;
                String a2 = SmartHomeRc4Cache.this.a(netRequest);
                if (SmartHomeRc4Cache.this.d.containsKey(a2)) {
                    str = (String) ((WeakReference) SmartHomeRc4Cache.this.d.get(a2)).get();
                    if (TextUtils.isEmpty(str)) {
                        SmartHomeRc4Cache.this.d.remove(a2);
                    }
                } else {
                    str = null;
                }
                if (TextUtils.isEmpty(str)) {
                    File file = new File(SmartHomeRc4Cache.this.a(a2));
                    StringBuffer stringBuffer = new StringBuffer("");
                    try {
                        InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file));
                        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                        for (String readLine = bufferedReader.readLine(); readLine != null; readLine = bufferedReader.readLine()) {
                            stringBuffer.append(readLine);
                        }
                        inputStreamReader.close();
                    } catch (Exception unused) {
                    }
                    str = stringBuffer.toString();
                }
                if (TextUtils.isEmpty(str) || (cacheCallback2 = cacheCallback) == null) {
                    SmartHomeRc4Cache.this.clearMemCacheEmptyItem();
                } else {
                    cacheCallback2.onResult(str);
                }
            }
        });
    }

    public void clearMemCacheEmptyItem() {
        Iterator<Map.Entry<String, WeakReference<String>>> it = this.d.entrySet().iterator();
        while (it.hasNext()) {
            if (TextUtils.isEmpty(it.next().getValue().get())) {
                it.remove();
            }
        }
    }

    public void setCache(NetRequest netRequest, String str) {
        String a2 = a(netRequest);
        this.d.put(a2, new WeakReference<>(str));
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(createFileIfNotExists(a(a2)));
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
            outputStreamWriter.append((CharSequence) str);
            outputStreamWriter.close();
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception unused) {
        }
    }

    public void clearFileCache() {
        File file = new File(a());
        ArrayList arrayList = new ArrayList();
        File[] listFiles = file.listFiles();
        if (listFiles != null) {
            if (listFiles.length > 50) {
                for (File file2 : listFiles) {
                    arrayList.add(new Pair(file2.getPath(), Long.valueOf(file2.lastModified())));
                }
                Collections.sort(arrayList, new Comparator<Pair<String, Long>>() { // from class: com.xiaomi.smarthome.core.server.internal.apicache.SmartHomeRc4Cache.2
                    /* renamed from: a */
                    public int compare(Pair<String, Long> pair, Pair<String, Long> pair2) {
                        if (((Long) pair.second).longValue() > ((Long) pair2.second).longValue()) {
                            return -1;
                        }
                        return ((Long) pair.second).longValue() < ((Long) pair2.second).longValue() ? 1 : 0;
                    }
                });
                int size = arrayList.size();
                for (int i = 50; i < size; i++) {
                    deleteFile((String) ((Pair) arrayList.get(i)).first);
                }
            }
        }
    }

    private String a() {
        return GlobalSetting.getAppContext().getCacheDir().getPath() + File.separator + "smrc4-cache";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String a(String str) {
        return a() + File.separator + str;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String a(NetRequest netRequest) {
        return MD5Util.getMd5Digest(netRequest.getMethod() + netRequest.getPath() + netRequest.getHeaders() + netRequest.getQueryParams());
    }

    public static File createFileIfNotExists(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        File file = new File(str);
        File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException unused) {
        }
        return file;
    }

    public static boolean deleteFile(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        File file = new File(str);
        return file.exists() && !file.isDirectory() && file.delete();
    }
}
