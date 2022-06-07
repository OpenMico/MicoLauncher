package com.elvishew.xlog.printer;

import android.util.Log;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: MicoLauncher.apk:classes.jar:com/elvishew/xlog/printer/AndroidPrinter.class */
public class AndroidPrinter implements Printer {
    static final int DEFAULT_MAX_CHUNK_SIZE = 4000;
    private boolean autoSeparate;
    private int maxChunkSize;

    public AndroidPrinter() {
        this(false, 4000);
    }

    public AndroidPrinter(boolean autoSeparate) {
        this(autoSeparate, 4000);
    }

    public AndroidPrinter(int maxChunkSize) {
        this(false, maxChunkSize);
    }

    public AndroidPrinter(boolean autoSeparate, int maxChunkSize) {
        this.autoSeparate = autoSeparate;
        this.maxChunkSize = maxChunkSize;
    }

    @Override // com.elvishew.xlog.printer.Printer
    public void println(int logLevel, String tag, String msg) {
        int end;
        int msgLength = msg.length();
        int start = 0;
        while (start < msgLength) {
            if (msg.charAt(start) == '\n') {
                start++;
            } else {
                int end2 = Math.min(start + this.maxChunkSize, msgLength);
                if (this.autoSeparate) {
                    int newLine = msg.indexOf(10, start);
                    end = newLine != -1 ? newLine : end2;
                } else {
                    end = adjustEnd(msg, start, end2);
                }
                printChunk(logLevel, tag, msg.substring(start, end));
                start = end;
            }
        }
    }

    static int adjustEnd(String msg, int start, int originEnd) {
        if (originEnd == msg.length()) {
            return originEnd;
        }
        if (msg.charAt(originEnd) == '\n') {
            return originEnd;
        }
        for (int last = originEnd - 1; start < last; last--) {
            if (msg.charAt(last) == '\n') {
                return last;
            }
        }
        return originEnd;
    }

    void printChunk(int logLevel, String tag, String msg) {
        Log.println(logLevel, tag, msg);
    }
}
