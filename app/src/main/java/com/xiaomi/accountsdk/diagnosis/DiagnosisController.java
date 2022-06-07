package com.xiaomi.accountsdk.diagnosis;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import com.xiaomi.accountsdk.diagnosis.a.a;
import java.io.IOException;
import java.util.regex.Pattern;

/* loaded from: classes2.dex */
public class DiagnosisController {
    private static volatile Context a;
    private static String b;
    private String c;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public class CheckDiagnosisEnabledTask extends AsyncTask<Void, Void, Boolean> {
        private final c b;

        private CheckDiagnosisEnabledTask(c cVar) {
            DiagnosisController.this = r1;
            this.b = cVar;
        }

        public Boolean doInBackground(Void... voidArr) {
            return Boolean.valueOf(!TextUtils.isEmpty(DiagnosisController.this.getDiagnosisDomain()));
        }

        public void onPostExecute(Boolean bool) {
            if (this.b != null) {
                if (bool != null && bool.booleanValue()) {
                    this.b.onLaunch();
                } else {
                    this.b.onError();
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class Holder {
        static DiagnosisController a = new DiagnosisController();

        private Holder() {
        }
    }

    private DiagnosisController() {
    }

    private static void a() {
        if (a == null) {
            throw new RuntimeException("please call DiagnosisController.init() first!");
        }
    }

    private static void a(String str) {
        if (b(str)) {
            b = str;
            return;
        }
        throw new IllegalArgumentException("name must be ^[A-Za-z]{0,10}$");
    }

    private String b() {
        try {
            a a2 = e.a();
            if (a2 == null) {
                return null;
            }
            this.c = a2.b;
            return this.c;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static boolean b(String str) {
        return Pattern.compile("^[A-Za-z]{0,10}$").matcher(str).matches();
    }

    public static DiagnosisController get() {
        a();
        return Holder.a;
    }

    public static Context getApplicationContext() {
        a();
        return a;
    }

    public static String getLogName() {
        return b;
    }

    public static void init(Context context, String str) {
        a = context.getApplicationContext();
        DiagnosisLog.set(new a(a));
        a(str);
    }

    public void checkStart(c cVar) {
        new CheckDiagnosisEnabledTask(cVar).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
    }

    public String getDiagnosisDomain() {
        return !TextUtils.isEmpty(this.c) ? this.c : b();
    }
}
