package com.google.android.exoplayer2;

import android.os.Build;
import java.util.HashSet;

/* loaded from: classes.dex */
public final class ExoPlayerLibraryInfo {
    public static final boolean ASSERTIONS_ENABLED = true;
    @Deprecated
    public static final String DEFAULT_USER_AGENT;
    public static final boolean GL_ASSERTIONS_ENABLED = false;
    public static final String TAG = "ExoPlayer";
    public static final boolean TRACE_ENABLED = true;
    public static final String VERSION = "2.15.1";
    public static final int VERSION_INT = 2015001;
    public static final String VERSION_SLASHY = "ExoPlayerLib/2.15.1";
    private static final HashSet<String> a = new HashSet<>();
    private static String b = "goog.exo.core";

    static {
        String str = Build.VERSION.RELEASE;
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 57);
        sb.append("ExoPlayerLib/2.15.1 (Linux; Android ");
        sb.append(str);
        sb.append(") ");
        sb.append(VERSION_SLASHY);
        DEFAULT_USER_AGENT = sb.toString();
    }

    private ExoPlayerLibraryInfo() {
    }

    public static synchronized String registeredModules() {
        String str;
        synchronized (ExoPlayerLibraryInfo.class) {
            str = b;
        }
        return str;
    }

    public static synchronized void registerModule(String str) {
        synchronized (ExoPlayerLibraryInfo.class) {
            if (a.add(str)) {
                String str2 = b;
                StringBuilder sb = new StringBuilder(String.valueOf(str2).length() + 2 + String.valueOf(str).length());
                sb.append(str2);
                sb.append(", ");
                sb.append(str);
                b = sb.toString();
            }
        }
    }
}
