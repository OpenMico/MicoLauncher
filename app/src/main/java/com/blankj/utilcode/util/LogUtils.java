package com.blankj.utilcode.util;

import android.content.ClipData;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import androidx.annotation.IntRange;
import androidx.annotation.RequiresApi;
import androidx.collection.SimpleArrayMap;
import com.blankj.utilcode.util.b;
import com.google.android.exoplayer2.extractor.ts.PsExtractor;
import com.umeng.analytics.pro.ai;
import com.xiaomi.micolauncher.skills.voip.model.MicoVoipClient;
import com.xiaomi.mipush.sdk.Constants;
import io.netty.util.internal.StringUtil;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Formatter;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes.dex */
public final class LogUtils {
    public static final int A = 7;
    public static final int D = 3;
    public static final int E = 6;
    public static final int I = 4;
    public static final int V = 2;
    public static final int W = 5;
    private static SimpleDateFormat e;
    private static final char[] a = {'V', 'D', 'I', 'W', 'E', 'A'};
    private static final String b = System.getProperty("file.separator");
    private static final String c = System.getProperty("line.separator");
    private static final Config d = new Config();
    private static final ExecutorService f = Executors.newSingleThreadExecutor();
    private static final SimpleArrayMap<Class, IFormatter> g = new SimpleArrayMap<>();

    /* loaded from: classes.dex */
    public interface IFileWriter {
        void write(String str, String str2);
    }

    /* loaded from: classes.dex */
    public static abstract class IFormatter<T> {
        public abstract String format(T t);
    }

    /* loaded from: classes.dex */
    public interface OnConsoleOutputListener {
        void onConsoleOutput(int i, String str, String str2);
    }

    /* loaded from: classes.dex */
    public interface OnFileOutputListener {
        void onFileOutput(String str, String str2);
    }

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes.dex */
    public @interface TYPE {
    }

    private LogUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static Config getConfig() {
        return d;
    }

    public static void v(Object... objArr) {
        log(2, d.getGlobalTag(), objArr);
    }

    public static void vTag(String str, Object... objArr) {
        log(2, str, objArr);
    }

    public static void d(Object... objArr) {
        log(3, d.getGlobalTag(), objArr);
    }

    public static void dTag(String str, Object... objArr) {
        log(3, str, objArr);
    }

    public static void i(Object... objArr) {
        log(4, d.getGlobalTag(), objArr);
    }

    public static void iTag(String str, Object... objArr) {
        log(4, str, objArr);
    }

    public static void w(Object... objArr) {
        log(5, d.getGlobalTag(), objArr);
    }

    public static void wTag(String str, Object... objArr) {
        log(5, str, objArr);
    }

    public static void e(Object... objArr) {
        log(6, d.getGlobalTag(), objArr);
    }

    public static void eTag(String str, Object... objArr) {
        log(6, str, objArr);
    }

    public static void a(Object... objArr) {
        log(7, d.getGlobalTag(), objArr);
    }

    public static void aTag(String str, Object... objArr) {
        log(7, str, objArr);
    }

    public static void file(Object obj) {
        log(19, d.getGlobalTag(), obj);
    }

    public static void file(int i, Object obj) {
        log(i | 16, d.getGlobalTag(), obj);
    }

    public static void file(String str, Object obj) {
        log(19, str, obj);
    }

    public static void file(int i, String str, Object obj) {
        log(i | 16, str, obj);
    }

    public static void json(Object obj) {
        log(35, d.getGlobalTag(), obj);
    }

    public static void json(int i, Object obj) {
        log(i | 32, d.getGlobalTag(), obj);
    }

    public static void json(String str, Object obj) {
        log(35, str, obj);
    }

    public static void json(int i, String str, Object obj) {
        log(i | 32, str, obj);
    }

    public static void xml(String str) {
        log(51, d.getGlobalTag(), str);
    }

    public static void xml(int i, String str) {
        log(i | 48, d.getGlobalTag(), str);
    }

    public static void xml(String str, String str2) {
        log(51, str, str2);
    }

    public static void xml(int i, String str, String str2) {
        log(i | 48, str, str2);
    }

    public static void log(int i, String str, Object... objArr) {
        if (d.isLogSwitch()) {
            final int i2 = i & 15;
            int i3 = i & PsExtractor.VIDEO_STREAM_MASK;
            if (!d.isLog2ConsoleSwitch() && !d.isLog2FileSwitch() && i3 != 16) {
                return;
            }
            if (i2 >= d.m || i2 >= d.n) {
                final b b2 = b(str);
                final String a2 = a(i3, objArr);
                if (d.isLog2ConsoleSwitch() && i3 != 16 && i2 >= d.m) {
                    a(i2, b2.a, b2.b, a2);
                }
                if ((d.isLog2FileSwitch() || i3 == 16) && i2 >= d.n) {
                    f.execute(new Runnable() { // from class: com.blankj.utilcode.util.LogUtils.1
                        @Override // java.lang.Runnable
                        public void run() {
                            int i4 = i2;
                            String str2 = b2.a;
                            LogUtils.f(i4, str2, b2.c + a2);
                        }
                    });
                }
            }
        }
    }

    public static String getCurrentLogFilePath() {
        return a(new Date());
    }

    public static List<File> getLogFiles() {
        File file = new File(d.getDir());
        if (!file.exists()) {
            return new ArrayList();
        }
        File[] listFiles = file.listFiles(new FilenameFilter() { // from class: com.blankj.utilcode.util.LogUtils.2
            @Override // java.io.FilenameFilter
            public boolean accept(File file2, String str) {
                return LogUtils.c(str);
            }
        });
        ArrayList arrayList = new ArrayList();
        Collections.addAll(arrayList, listFiles);
        return arrayList;
    }

    private static b b(String str) {
        String str2;
        String str3;
        if (d.h || d.isLogHeadSwitch()) {
            StackTraceElement[] stackTrace = new Throwable().getStackTrace();
            int stackOffset = d.getStackOffset() + 3;
            if (stackOffset >= stackTrace.length) {
                String a2 = a(stackTrace[3]);
                if (!d.h || !b.o(str)) {
                    a2 = str;
                } else {
                    int indexOf = a2.indexOf(46);
                    if (indexOf != -1) {
                        a2 = a2.substring(0, indexOf);
                    }
                }
                return new b(a2, null, ": ");
            }
            StackTraceElement stackTraceElement = stackTrace[stackOffset];
            String a3 = a(stackTraceElement);
            if (!d.h || !b.o(str)) {
                str3 = str;
            } else {
                int indexOf2 = a3.indexOf(46);
                str3 = indexOf2 == -1 ? a3 : a3.substring(0, indexOf2);
            }
            if (d.isLogHeadSwitch()) {
                String name = Thread.currentThread().getName();
                String formatter = new Formatter().format("%s, %s.%s(%s:%d)", name, stackTraceElement.getClassName(), stackTraceElement.getMethodName(), a3, Integer.valueOf(stackTraceElement.getLineNumber())).toString();
                String str4 = " [" + formatter + "]: ";
                if (d.getStackDeep() <= 1) {
                    return new b(str3, new String[]{formatter}, str4);
                }
                String[] strArr = new String[Math.min(d.getStackDeep(), stackTrace.length - stackOffset)];
                strArr[0] = formatter;
                String formatter2 = new Formatter().format("%" + (name.length() + 2) + ai.az, "").toString();
                int length = strArr.length;
                for (int i = 1; i < length; i++) {
                    StackTraceElement stackTraceElement2 = stackTrace[i + stackOffset];
                    strArr[i] = new Formatter().format("%s%s.%s(%s:%d)", formatter2, stackTraceElement2.getClassName(), stackTraceElement2.getMethodName(), a(stackTraceElement2), Integer.valueOf(stackTraceElement2.getLineNumber())).toString();
                }
                return new b(str3, strArr, str4);
            }
            str2 = str3;
        } else {
            str2 = d.getGlobalTag();
        }
        return new b(str2, null, ": ");
    }

    private static String a(StackTraceElement stackTraceElement) {
        String fileName = stackTraceElement.getFileName();
        if (fileName != null) {
            return fileName;
        }
        String className = stackTraceElement.getClassName();
        String[] split = className.split("\\.");
        if (split.length > 0) {
            className = split[split.length - 1];
        }
        int indexOf = className.indexOf(36);
        if (indexOf != -1) {
            className = className.substring(0, indexOf);
        }
        return className + ".java";
    }

    private static String a(int i, Object... objArr) {
        String str = "null";
        if (objArr != null) {
            if (objArr.length == 1) {
                str = a(i, objArr[0]);
            } else {
                StringBuilder sb = new StringBuilder();
                int length = objArr.length;
                for (int i2 = 0; i2 < length; i2++) {
                    Object obj = objArr[i2];
                    sb.append("args");
                    sb.append("[");
                    sb.append(i2);
                    sb.append("]");
                    sb.append(" = ");
                    sb.append(b(obj));
                    sb.append(c);
                }
                str = sb.toString();
            }
        }
        return str.length() == 0 ? "log nothing" : str;
    }

    private static String a(int i, Object obj) {
        if (obj == null) {
            return "null";
        }
        if (i == 32) {
            return a.a(obj, 32);
        }
        if (i == 48) {
            return a.a(obj, 48);
        }
        return b(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String b(Object obj) {
        IFormatter iFormatter;
        if (obj == null) {
            return "null";
        }
        if (g.isEmpty() || (iFormatter = g.get(c(obj))) == null) {
            return a.a(obj);
        }
        return iFormatter.format(obj);
    }

    private static void a(int i, String str, String[] strArr, String str2) {
        if (d.isSingleTagSwitch()) {
            d(i, str, b(i, str, strArr, str2));
            return;
        }
        a(i, str, true);
        a(i, str, strArr);
        b(i, str, str2);
        a(i, str, false);
    }

    private static void a(int i, String str, boolean z) {
        if (d.isLogBorderSwitch()) {
            e(i, str, z ? "┌────────────────────────────────────────────────────────────────────────────────────────────────────────────────" : "└────────────────────────────────────────────────────────────────────────────────────────────────────────────────");
        }
    }

    private static void a(int i, String str, String[] strArr) {
        if (strArr != null) {
            for (String str2 : strArr) {
                if (d.isLogBorderSwitch()) {
                    str2 = "│ " + str2;
                }
                e(i, str, str2);
            }
            if (d.isLogBorderSwitch()) {
                e(i, str, "├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄");
            }
        }
    }

    private static void b(int i, String str, String str2) {
        int length = str2.length();
        int i2 = length / 1100;
        if (i2 > 0) {
            int i3 = 0;
            int i4 = 0;
            while (i3 < i2) {
                int i5 = i4 + 1100;
                c(i, str, str2.substring(i4, i5));
                i3++;
                i4 = i5;
            }
            if (i4 != length) {
                c(i, str, str2.substring(i4, length));
                return;
            }
            return;
        }
        c(i, str, str2);
    }

    private static void c(int i, String str, String str2) {
        if (!d.isLogBorderSwitch()) {
            e(i, str, str2);
            return;
        }
        new StringBuilder();
        String[] split = str2.split(c);
        for (String str3 : split) {
            e(i, str, "│ " + str3);
        }
    }

    private static String b(int i, String str, String[] strArr, String str2) {
        StringBuilder sb = new StringBuilder();
        int i2 = 0;
        if (d.isLogBorderSwitch()) {
            sb.append(StringUtils.SPACE);
            sb.append(c);
            sb.append("┌────────────────────────────────────────────────────────────────────────────────────────────────────────────────");
            sb.append(c);
            if (strArr != null) {
                for (String str3 : strArr) {
                    sb.append("│ ");
                    sb.append(str3);
                    sb.append(c);
                }
                sb.append("├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄");
                sb.append(c);
            }
            String[] split = str2.split(c);
            int length = split.length;
            while (i2 < length) {
                String str4 = split[i2];
                sb.append("│ ");
                sb.append(str4);
                sb.append(c);
                i2++;
            }
            sb.append("└────────────────────────────────────────────────────────────────────────────────────────────────────────────────");
        } else {
            if (strArr != null) {
                sb.append(StringUtils.SPACE);
                sb.append(c);
                int length2 = strArr.length;
                while (i2 < length2) {
                    sb.append(strArr[i2]);
                    sb.append(c);
                    i2++;
                }
            }
            sb.append(str2);
        }
        return sb.toString();
    }

    private static void d(int i, String str, String str2) {
        int length = str2.length();
        int i2 = 1100;
        int i3 = d.isLogBorderSwitch() ? (length - 113) / 1100 : length / 1100;
        if (i3 > 0) {
            int i4 = 1;
            if (d.isLogBorderSwitch()) {
                e(i, str, str2.substring(0, 1100) + c + "└────────────────────────────────────────────────────────────────────────────────────────────────────────────────");
                while (i4 < i3) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(StringUtils.SPACE);
                    sb.append(c);
                    sb.append("┌────────────────────────────────────────────────────────────────────────────────────────────────────────────────");
                    sb.append(c);
                    sb.append("│ ");
                    int i5 = i2 + 1100;
                    sb.append(str2.substring(i2, i5));
                    sb.append(c);
                    sb.append("└────────────────────────────────────────────────────────────────────────────────────────────────────────────────");
                    e(i, str, sb.toString());
                    i4++;
                    i2 = i5;
                }
                if (i2 != length - 113) {
                    e(i, str, StringUtils.SPACE + c + "┌────────────────────────────────────────────────────────────────────────────────────────────────────────────────" + c + "│ " + str2.substring(i2, length));
                    return;
                }
                return;
            }
            e(i, str, str2.substring(0, 1100));
            while (i4 < i3) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(StringUtils.SPACE);
                sb2.append(c);
                int i6 = i2 + 1100;
                sb2.append(str2.substring(i2, i6));
                e(i, str, sb2.toString());
                i4++;
                i2 = i6;
            }
            if (i2 != length) {
                e(i, str, StringUtils.SPACE + c + str2.substring(i2, length));
                return;
            }
            return;
        }
        e(i, str, str2);
    }

    private static void e(int i, String str, String str2) {
        Log.println(i, str, str2);
        if (d.t != null) {
            d.t.onConsoleOutput(i, str, str2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void f(int i, String str, String str2) {
        Date date = new Date();
        String format = e().format(date);
        String substring = format.substring(0, 10);
        String a2 = a(date);
        if (!a(a2, substring)) {
            Log.e("LogUtils", "create " + a2 + " failed!");
            return;
        }
        String substring2 = format.substring(11);
        d(a2, substring2 + a[i - 2] + "/" + str + str2 + c);
    }

    private static String a(Date date) {
        String substring = e().format(date).substring(0, 10);
        return d.getDir() + d.getFilePrefix() + "_" + substring + "_" + d.getProcessName() + d.getFileExtension();
    }

    private static SimpleDateFormat e() {
        if (e == null) {
            e = new SimpleDateFormat("yyyy_MM_dd HH:mm:ss.SSS ", Locale.getDefault());
        }
        return e;
    }

    private static boolean a(String str, String str2) {
        File file = new File(str);
        if (file.exists()) {
            return file.isFile();
        }
        if (!b.e(file.getParentFile())) {
            return false;
        }
        try {
            b(str, str2);
            boolean createNewFile = file.createNewFile();
            if (createNewFile) {
                c(str, str2);
            }
            return createNewFile;
        } catch (IOException e2) {
            e2.printStackTrace();
            return false;
        }
    }

    private static void b(String str, String str2) {
        File[] listFiles;
        if (d.getSaveDays() > 0 && (listFiles = new File(str).getParentFile().listFiles(new FilenameFilter() { // from class: com.blankj.utilcode.util.LogUtils.3
            @Override // java.io.FilenameFilter
            public boolean accept(File file, String str3) {
                return LogUtils.c(str3);
            }
        })) != null && listFiles.length > 0) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy_MM_dd", Locale.getDefault());
            try {
                long time = simpleDateFormat.parse(str2).getTime() - (d.getSaveDays() * 86400000);
                for (final File file : listFiles) {
                    String name = file.getName();
                    name.length();
                    if (simpleDateFormat.parse(d(name)).getTime() <= time) {
                        f.execute(new Runnable() { // from class: com.blankj.utilcode.util.LogUtils.4
                            @Override // java.lang.Runnable
                            public void run() {
                                if (!file.delete()) {
                                    Log.e("LogUtils", "delete " + file + " failed!");
                                }
                            }
                        });
                    }
                }
            } catch (ParseException e2) {
                e2.printStackTrace();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean c(String str) {
        return str.matches("^" + d.getFilePrefix() + "_[0-9]{4}_[0-9]{2}_[0-9]{2}_.*$");
    }

    private static String d(String str) {
        Matcher matcher = Pattern.compile("[0-9]{4}_[0-9]{2}_[0-9]{2}").matcher(str);
        return matcher.find() ? matcher.group() : "";
    }

    private static void c(String str, String str2) {
        d.v.a("Date of Log", str2);
        d(str, d.v.toString());
    }

    private static void d(String str, String str2) {
        if (d.s == null) {
            b.a(str, str2, true);
        } else {
            d.s.write(str, str2);
        }
        if (d.u != null) {
            d.u.onFileOutput(str, str2);
        }
    }

    /* loaded from: classes.dex */
    public static final class Config {
        private String a;
        private String b;
        private String c;
        private String d;
        private boolean e;
        private boolean f;
        private String g;
        private boolean h;
        private boolean i;
        private boolean j;
        private boolean k;
        private boolean l;
        private int m;
        private int n;
        private int o;
        private int p;
        private int q;
        private String r;
        private IFileWriter s;
        private OnConsoleOutputListener t;
        private OnFileOutputListener u;
        private b.a v;

        private Config() {
            this.c = "util";
            this.d = ".txt";
            this.e = true;
            this.f = true;
            this.g = "";
            this.h = true;
            this.i = true;
            this.j = false;
            this.k = true;
            this.l = true;
            this.m = 2;
            this.n = 2;
            this.o = 1;
            this.p = 0;
            this.q = -1;
            this.r = b.p();
            this.v = new b.a("Log");
            if (!b.s() || Utils.getApp().getExternalFilesDir(null) == null) {
                this.a = Utils.getApp().getFilesDir() + LogUtils.b + MicoVoipClient.MICOLAUNCHER_LOG + LogUtils.b;
                return;
            }
            this.a = Utils.getApp().getExternalFilesDir(null) + LogUtils.b + MicoVoipClient.MICOLAUNCHER_LOG + LogUtils.b;
        }

        public final Config setLogSwitch(boolean z) {
            this.e = z;
            return this;
        }

        public final Config setConsoleSwitch(boolean z) {
            this.f = z;
            return this;
        }

        public final Config setGlobalTag(String str) {
            if (b.o(str)) {
                this.g = "";
                this.h = true;
            } else {
                this.g = str;
                this.h = false;
            }
            return this;
        }

        public final Config setLogHeadSwitch(boolean z) {
            this.i = z;
            return this;
        }

        public final Config setLog2FileSwitch(boolean z) {
            this.j = z;
            return this;
        }

        public final Config setDir(String str) {
            if (b.o(str)) {
                this.b = null;
            } else {
                if (!str.endsWith(LogUtils.b)) {
                    str = str + LogUtils.b;
                }
                this.b = str;
            }
            return this;
        }

        public final Config setDir(File file) {
            String str;
            if (file == null) {
                str = null;
            } else {
                str = file.getAbsolutePath() + LogUtils.b;
            }
            this.b = str;
            return this;
        }

        public final Config setFilePrefix(String str) {
            if (b.o(str)) {
                this.c = "util";
            } else {
                this.c = str;
            }
            return this;
        }

        public final Config setFileExtension(String str) {
            if (b.o(str)) {
                this.d = ".txt";
            } else if (str.startsWith(".")) {
                this.d = str;
            } else {
                this.d = "." + str;
            }
            return this;
        }

        public final Config setBorderSwitch(boolean z) {
            this.k = z;
            return this;
        }

        public final Config setSingleTagSwitch(boolean z) {
            this.l = z;
            return this;
        }

        public final Config setConsoleFilter(int i) {
            this.m = i;
            return this;
        }

        public final Config setFileFilter(int i) {
            this.n = i;
            return this;
        }

        public final Config setStackDeep(@IntRange(from = 1) int i) {
            this.o = i;
            return this;
        }

        public final Config setStackOffset(@IntRange(from = 0) int i) {
            this.p = i;
            return this;
        }

        public final Config setSaveDays(@IntRange(from = 1) int i) {
            this.q = i;
            return this;
        }

        public final <T> Config addFormatter(IFormatter<T> iFormatter) {
            if (iFormatter != null) {
                LogUtils.g.put(LogUtils.b((IFormatter) iFormatter), iFormatter);
            }
            return this;
        }

        public final Config setFileWriter(IFileWriter iFileWriter) {
            this.s = iFileWriter;
            return this;
        }

        public final Config setOnConsoleOutputListener(OnConsoleOutputListener onConsoleOutputListener) {
            this.t = onConsoleOutputListener;
            return this;
        }

        public final Config setOnFileOutputListener(OnFileOutputListener onFileOutputListener) {
            this.u = onFileOutputListener;
            return this;
        }

        public final Config addFileExtraHead(Map<String, String> map) {
            this.v.a(map);
            return this;
        }

        public final Config addFileExtraHead(String str, String str2) {
            this.v.b(str, str2);
            return this;
        }

        public final String getProcessName() {
            String str = this.r;
            return str == null ? "" : str.replace(Constants.COLON_SEPARATOR, "_");
        }

        public final String getDefaultDir() {
            return this.a;
        }

        public final String getDir() {
            String str = this.b;
            return str == null ? this.a : str;
        }

        public final String getFilePrefix() {
            return this.c;
        }

        public final String getFileExtension() {
            return this.d;
        }

        public final boolean isLogSwitch() {
            return this.e;
        }

        public final boolean isLog2ConsoleSwitch() {
            return this.f;
        }

        public final String getGlobalTag() {
            return b.o(this.g) ? "" : this.g;
        }

        public final boolean isLogHeadSwitch() {
            return this.i;
        }

        public final boolean isLog2FileSwitch() {
            return this.j;
        }

        public final boolean isLogBorderSwitch() {
            return this.k;
        }

        public final boolean isSingleTagSwitch() {
            return this.l;
        }

        public final char getConsoleFilter() {
            return LogUtils.a[this.m - 2];
        }

        public final char getFileFilter() {
            return LogUtils.a[this.n - 2];
        }

        public final int getStackDeep() {
            return this.o;
        }

        public final int getStackOffset() {
            return this.p;
        }

        public final int getSaveDays() {
            return this.q;
        }

        public final boolean haveSetOnConsoleOutputListener() {
            return this.t != null;
        }

        public final boolean haveSetOnFileOutputListener() {
            return this.u != null;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("process: ");
            sb.append(getProcessName());
            sb.append(LogUtils.c);
            sb.append("logSwitch: ");
            sb.append(isLogSwitch());
            sb.append(LogUtils.c);
            sb.append("consoleSwitch: ");
            sb.append(isLog2ConsoleSwitch());
            sb.append(LogUtils.c);
            sb.append("tag: ");
            sb.append(getGlobalTag().equals("") ? "null" : getGlobalTag());
            sb.append(LogUtils.c);
            sb.append("headSwitch: ");
            sb.append(isLogHeadSwitch());
            sb.append(LogUtils.c);
            sb.append("fileSwitch: ");
            sb.append(isLog2FileSwitch());
            sb.append(LogUtils.c);
            sb.append("dir: ");
            sb.append(getDir());
            sb.append(LogUtils.c);
            sb.append("filePrefix: ");
            sb.append(getFilePrefix());
            sb.append(LogUtils.c);
            sb.append("borderSwitch: ");
            sb.append(isLogBorderSwitch());
            sb.append(LogUtils.c);
            sb.append("singleTagSwitch: ");
            sb.append(isSingleTagSwitch());
            sb.append(LogUtils.c);
            sb.append("consoleFilter: ");
            sb.append(getConsoleFilter());
            sb.append(LogUtils.c);
            sb.append("fileFilter: ");
            sb.append(getFileFilter());
            sb.append(LogUtils.c);
            sb.append("stackDeep: ");
            sb.append(getStackDeep());
            sb.append(LogUtils.c);
            sb.append("stackOffset: ");
            sb.append(getStackOffset());
            sb.append(LogUtils.c);
            sb.append("saveDays: ");
            sb.append(getSaveDays());
            sb.append(LogUtils.c);
            sb.append("formatter: ");
            sb.append(LogUtils.g);
            sb.append(LogUtils.c);
            sb.append("fileWriter: ");
            sb.append(this.s);
            sb.append(LogUtils.c);
            sb.append("onConsoleOutputListener: ");
            sb.append(this.t);
            sb.append(LogUtils.c);
            sb.append("onFileOutputListener: ");
            sb.append(this.u);
            sb.append(LogUtils.c);
            sb.append("fileExtraHeader: ");
            sb.append(this.v.a());
            return sb.toString();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class b {
        String a;
        String[] b;
        String c;

        b(String str, String[] strArr, String str2) {
            this.a = str;
            this.b = strArr;
            this.c = str2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class a {
        static String a(Object obj) {
            return a(obj, -1);
        }

        static String a(Object obj, int i) {
            if (obj.getClass().isArray()) {
                return c(obj);
            }
            if (obj instanceof Throwable) {
                return b.a((Throwable) obj);
            }
            if (obj instanceof Bundle) {
                return a((Bundle) obj);
            }
            if (obj instanceof Intent) {
                return a((Intent) obj);
            }
            if (i == 32) {
                return b(obj);
            }
            if (i == 48) {
                return a(obj.toString());
            }
            return obj.toString();
        }

        private static String a(Bundle bundle) {
            Iterator<String> it = bundle.keySet().iterator();
            if (!it.hasNext()) {
                return "Bundle {}";
            }
            StringBuilder sb = new StringBuilder(128);
            sb.append("Bundle { ");
            while (true) {
                String next = it.next();
                Object obj = bundle.get(next);
                sb.append(next);
                sb.append('=');
                if (obj instanceof Bundle) {
                    sb.append(obj == bundle ? "(this Bundle)" : a((Bundle) obj));
                } else {
                    sb.append(LogUtils.b(obj));
                }
                if (!it.hasNext()) {
                    sb.append(" }");
                    return sb.toString();
                }
                sb.append(StringUtil.COMMA);
                sb.append(' ');
            }
        }

        private static String a(Intent intent) {
            boolean z;
            Intent selector;
            ClipData clipData;
            StringBuilder sb = new StringBuilder(128);
            sb.append("Intent { ");
            String action = intent.getAction();
            boolean z2 = true;
            if (action != null) {
                sb.append("act=");
                sb.append(action);
                z = false;
            } else {
                z = true;
            }
            Set<String> categories = intent.getCategories();
            if (categories != null) {
                if (!z) {
                    sb.append(' ');
                }
                sb.append("cat=[");
                for (String str : categories) {
                    if (!z2) {
                        sb.append(StringUtil.COMMA);
                    }
                    sb.append(str);
                    z2 = false;
                }
                sb.append("]");
                z = false;
            }
            Uri data = intent.getData();
            if (data != null) {
                if (!z) {
                    sb.append(' ');
                }
                sb.append("dat=");
                sb.append(data);
                z = false;
            }
            String type = intent.getType();
            if (type != null) {
                if (!z) {
                    sb.append(' ');
                }
                sb.append("typ=");
                sb.append(type);
                z = false;
            }
            int flags = intent.getFlags();
            if (flags != 0) {
                if (!z) {
                    sb.append(' ');
                }
                sb.append("flg=0x");
                sb.append(Integer.toHexString(flags));
                z = false;
            }
            String str2 = intent.getPackage();
            if (str2 != null) {
                if (!z) {
                    sb.append(' ');
                }
                sb.append("pkg=");
                sb.append(str2);
                z = false;
            }
            ComponentName component = intent.getComponent();
            if (component != null) {
                if (!z) {
                    sb.append(' ');
                }
                sb.append("cmp=");
                sb.append(component.flattenToShortString());
                z = false;
            }
            Rect sourceBounds = intent.getSourceBounds();
            if (sourceBounds != null) {
                if (!z) {
                    sb.append(' ');
                }
                sb.append("bnds=");
                sb.append(sourceBounds.toShortString());
                z = false;
            }
            if (Build.VERSION.SDK_INT >= 16 && (clipData = intent.getClipData()) != null) {
                if (!z) {
                    sb.append(' ');
                }
                a(clipData, sb);
                z = false;
            }
            Bundle extras = intent.getExtras();
            if (extras != null) {
                if (!z) {
                    sb.append(' ');
                }
                sb.append("extras={");
                sb.append(a(extras));
                sb.append('}');
                z = false;
            }
            if (Build.VERSION.SDK_INT >= 15 && (selector = intent.getSelector()) != null) {
                if (!z) {
                    sb.append(' ');
                }
                sb.append("sel={");
                sb.append(selector == intent ? "(this Intent)" : a(selector));
                sb.append("}");
            }
            sb.append(" }");
            return sb.toString();
        }

        @RequiresApi(api = 16)
        private static void a(ClipData clipData, StringBuilder sb) {
            ClipData.Item itemAt = clipData.getItemAt(0);
            if (itemAt == null) {
                sb.append("ClipData.Item {}");
                return;
            }
            sb.append("ClipData.Item { ");
            String htmlText = itemAt.getHtmlText();
            if (htmlText != null) {
                sb.append("H:");
                sb.append(htmlText);
                sb.append("}");
                return;
            }
            CharSequence text = itemAt.getText();
            if (text != null) {
                sb.append("T:");
                sb.append(text);
                sb.append("}");
                return;
            }
            Uri uri = itemAt.getUri();
            if (uri != null) {
                sb.append("U:");
                sb.append(uri);
                sb.append("}");
                return;
            }
            Intent intent = itemAt.getIntent();
            if (intent != null) {
                sb.append("I:");
                sb.append(a(intent));
                sb.append("}");
                return;
            }
            sb.append("NULL");
            sb.append("}");
        }

        private static String b(Object obj) {
            if (obj instanceof CharSequence) {
                return b.m(obj.toString());
            }
            try {
                return b.l().toJson(obj);
            } catch (Throwable unused) {
                return obj.toString();
            }
        }

        private static String a(String str) {
            try {
                StreamSource streamSource = new StreamSource(new StringReader(str));
                StreamResult streamResult = new StreamResult(new StringWriter());
                Transformer newTransformer = TransformerFactory.newInstance().newTransformer();
                newTransformer.setOutputProperty("indent", "yes");
                newTransformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
                newTransformer.transform(streamSource, streamResult);
                String obj = streamResult.getWriter().toString();
                return obj.replaceFirst(">", ">" + LogUtils.c);
            } catch (Exception e) {
                e.printStackTrace();
                return str;
            }
        }

        private static String c(Object obj) {
            if (obj instanceof Object[]) {
                return Arrays.deepToString((Object[]) obj);
            }
            if (obj instanceof boolean[]) {
                return Arrays.toString((boolean[]) obj);
            }
            if (obj instanceof byte[]) {
                return Arrays.toString((byte[]) obj);
            }
            if (obj instanceof char[]) {
                return Arrays.toString((char[]) obj);
            }
            if (obj instanceof double[]) {
                return Arrays.toString((double[]) obj);
            }
            if (obj instanceof float[]) {
                return Arrays.toString((float[]) obj);
            }
            if (obj instanceof int[]) {
                return Arrays.toString((int[]) obj);
            }
            if (obj instanceof long[]) {
                return Arrays.toString((long[]) obj);
            }
            if (obj instanceof short[]) {
                return Arrays.toString((short[]) obj);
            }
            throw new IllegalArgumentException("Array has incompatible type: " + obj.getClass());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <T> Class b(IFormatter<T> iFormatter) {
        Type type;
        Type[] genericInterfaces = iFormatter.getClass().getGenericInterfaces();
        if (genericInterfaces.length == 1) {
            type = genericInterfaces[0];
        } else {
            type = iFormatter.getClass().getGenericSuperclass();
        }
        Type type2 = ((ParameterizedType) type).getActualTypeArguments()[0];
        while (type2 instanceof ParameterizedType) {
            type2 = ((ParameterizedType) type2).getRawType();
        }
        String obj = type2.toString();
        if (obj.startsWith("class ")) {
            obj = obj.substring(6);
        } else if (obj.startsWith("interface ")) {
            obj = obj.substring(10);
        }
        try {
            return Class.forName(obj);
        } catch (ClassNotFoundException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    private static Class c(Object obj) {
        String str;
        Class<?> cls = obj.getClass();
        if (cls.isAnonymousClass() || cls.isSynthetic()) {
            Type[] genericInterfaces = cls.getGenericInterfaces();
            if (genericInterfaces.length == 1) {
                Type type = genericInterfaces[0];
                while (type instanceof ParameterizedType) {
                    type = ((ParameterizedType) type).getRawType();
                }
                str = type.toString();
            } else {
                Type genericSuperclass = cls.getGenericSuperclass();
                while (genericSuperclass instanceof ParameterizedType) {
                    genericSuperclass = ((ParameterizedType) genericSuperclass).getRawType();
                }
                str = genericSuperclass.toString();
            }
            if (str.startsWith("class ")) {
                str = str.substring(6);
            } else if (str.startsWith("interface ")) {
                str = str.substring(10);
            }
            try {
                return Class.forName(str);
            } catch (ClassNotFoundException e2) {
                e2.printStackTrace();
            }
        }
        return cls;
    }
}
