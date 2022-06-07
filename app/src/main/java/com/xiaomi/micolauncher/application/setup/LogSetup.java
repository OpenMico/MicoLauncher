package com.xiaomi.micolauncher.application.setup;

import android.content.Context;
import com.elvishew.xlog.LogConfiguration;
import com.elvishew.xlog.LogItem;
import com.elvishew.xlog.LogLevel;
import com.elvishew.xlog.XLog;
import com.elvishew.xlog.formatter.border.BorderFormatter;
import com.elvishew.xlog.interceptor.Interceptor;
import com.elvishew.xlog.internal.SystemCompat;
import com.elvishew.xlog.printer.AndroidPrinter;
import com.elvishew.xlog.printer.file.FilePrinter;
import com.elvishew.xlog.printer.file.backup.FileSizeBackupStrategy;
import com.elvishew.xlog.printer.file.naming.ChangelessFileNameGenerator;
import com.xiaomi.mico.base.utils.FileUtils;
import com.xiaomi.mico.common.ProcessChecker;
import com.xiaomi.micolauncher.application.Constants;
import com.xiaomi.micolauncher.common.DebugHelper;
import com.xiaomi.micolauncher.common.L;
import io.reactivex.exceptions.UndeliverableException;
import io.reactivex.plugins.RxJavaPlugins;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/* loaded from: classes3.dex */
public class LogSetup extends AbsSyncSetup {
    public static final String TAG = "MICO";
    private static LogSetup a = new LogSetup();
    private boolean c;
    private String d;
    private boolean b = false;
    private int e = 2;

    public static LogSetup getInstance() {
        return a;
    }

    private LogSetup() {
    }

    private void a(Context context) {
        LogConfiguration build = new LogConfiguration.Builder().threadFormatter($$Lambda$CvVr2J_Et4dqEcRFl4F2l7Oi3p0.INSTANCE).t().b().addInterceptor(new Interceptor() { // from class: com.xiaomi.micolauncher.application.setup.-$$Lambda$LogSetup$0hWwnPmE7AmiPtoEICt4HrjhauM
            public final LogItem intercept(LogItem logItem) {
                LogItem a2;
                a2 = LogSetup.this.a(logItem);
                return a2;
            }
        }).borderFormatter(new BorderFormatter() { // from class: com.xiaomi.micolauncher.application.setup.-$$Lambda$LogSetup$B_ZFSeeR4fWIpguCaJC6VibKCUg
            @Override // com.elvishew.xlog.formatter.Formatter
            public final String format(String[] strArr) {
                String a2;
                a2 = LogSetup.this.a(strArr);
                return a2;
            }
        }).logLevel(3).tag(TAG).build();
        String str = "mico.log";
        this.c = ProcessChecker.isMainProcess(context);
        if (!this.c) {
            this.d = ProcessChecker.getCurrentProcessName(context);
            str = String.format("%s.log", this.d);
        }
        XLog.init(build, new AndroidPrinter(), new FilePrinter.Builder(Constants.getLogDirectory(context)).fileNameGenerator(new ChangelessFileNameGenerator(str)).backupStrategy(new FileSizeBackupStrategy(DebugHelper.isDebugVersion() ? 20971520 : 5242880)).flattener($$Lambda$LogSetup$CrVZlTAWlpvx7QLxyW9NqJXQKp0.INSTANCE).build());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ LogItem a(LogItem logItem) {
        if (DebugHelper.isDebugVersion() || logItem.level >= this.e) {
            return logItem;
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ String a(String[] strArr) {
        String str;
        String str2;
        if (strArr == null || strArr.length != 3) {
            return null;
        }
        String str3 = strArr[0];
        if (!this.c) {
            str3 = this.d + "/" + str3;
        }
        String str4 = strArr[1];
        String str5 = strArr[2];
        StringBuilder sb = new StringBuilder();
        if (str3 != null) {
            str = "[" + str3 + "] ";
        } else {
            str = "";
        }
        sb.append(str);
        if (str4 != null) {
            str2 = str4 + SystemCompat.lineSeparator;
        } else {
            str2 = "";
        }
        sb.append(str2);
        sb.append(str5);
        return sb.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ CharSequence a(long j, int i, String str, String str2) {
        return new SimpleDateFormat("MM-dd HH:mm:ss SSS", Locale.CHINA).format(Long.valueOf(j)) + '|' + LogLevel.getLevelName(i) + '|' + str + '|' + str2;
    }

    private void a() {
        RxJavaPlugins.setErrorHandler($$Lambda$LogSetup$r4TTTiIc2Z3Svv4dA1hh7hGPdd8.INSTANCE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Throwable th) throws Exception {
        if (th instanceof UndeliverableException) {
            L.base.d("RxJava undeliverable exception received ", th);
        } else if ((th instanceof IOException) || (th instanceof InterruptedException)) {
        } else {
            if ((th instanceof NullPointerException) || (th instanceof IllegalArgumentException)) {
                Thread.currentThread().getUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), th);
            } else if (th instanceof IllegalStateException) {
                Thread.currentThread().getUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), th);
            }
        }
    }

    @Override // com.xiaomi.micolauncher.application.setup.ISetupRule
    public void setup(Context context) {
        if (!this.b) {
            a(context);
            try {
                FileUtils.deleteFile("/sdcard/mtklog");
            } catch (IOException unused) {
                L.base.e("Exception in deleting /sdcard/mtklog.");
            }
            a();
            this.b = true;
        }
    }

    public boolean isSetup() {
        return this.b;
    }

    public void setLogOutputLevel(int i) {
        this.e = i;
    }
}
