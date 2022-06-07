package com.xiaomi.accountsdk.diagnosis.c;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Process;
import android.text.TextUtils;
import com.xiaomi.idm.api.IDMServer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/* loaded from: classes2.dex */
public class b extends AsyncTask<Void, Void, String> {
    private final a a;
    private final int b;
    @SuppressLint({"StaticFieldLeak"})
    private final Context c;

    /* loaded from: classes2.dex */
    public interface a {
        void onReadLog(String str);
    }

    public b(Context context, a aVar, int i) {
        this.c = context.getApplicationContext();
        this.a = aVar;
        this.b = i;
    }

    private ArrayList<String> a(String[] strArr) {
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("logcat -d -v time").getInputStream()));
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    break;
                }
                int length = strArr.length;
                int i = 0;
                while (true) {
                    if (i >= length) {
                        break;
                    } else if (readLine.contains(strArr[i])) {
                        arrayList.add(readLine);
                        break;
                    } else {
                        i++;
                    }
                }
            }
        } catch (IOException unused) {
        }
        return arrayList;
    }

    private String[] a() {
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses;
        String packageName = this.c.getPackageName();
        ActivityManager activityManager = (ActivityManager) this.c.getSystemService(IDMServer.PERSIST_TYPE_ACTIVITY);
        HashSet hashSet = new HashSet();
        if (!(activityManager == null || (runningAppProcesses = activityManager.getRunningAppProcesses()) == null)) {
            for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
                if (runningAppProcessInfo.processName != null && runningAppProcessInfo.processName.contains(packageName)) {
                    hashSet.add(String.valueOf(runningAppProcessInfo.pid));
                }
            }
        }
        hashSet.add(String.valueOf(Process.myPid()));
        return (String[]) hashSet.toArray(new String[0]);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* renamed from: a */
    public String doInBackground(Void... voidArr) {
        List a2 = a(a());
        if (a2 != null && a2.size() > this.b) {
            a2 = a2.subList(a2.size() - this.b, a2.size() - 1);
        }
        return TextUtils.join("\n", a2);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* renamed from: a */
    public void onPostExecute(String str) {
        this.a.onReadLog(str);
        super.onPostExecute(str);
    }
}
