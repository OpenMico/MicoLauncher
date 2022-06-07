package com.xiaomi.idm.transfile;

import java.io.IOException;
import java.util.List;

/* loaded from: classes3.dex */
public class IDMTransFile {
    public static final String IS_DIR = "IS_DIR";
    public static final String IS_FILE = "IS_FILE";
    private int a = 1;
    private long b = 0;
    private FileNode c = new FileNode();

    /* loaded from: classes3.dex */
    public interface IDMTransFileCallback {
        void onDataRec(byte[] bArr, long j, int i) throws IOException;

        void onDataSendProgress(String str, long j, int i);

        void onEndDataRec(String str, String str2, int i);

        void onEndDataSend(String str, int i);

        void onEndRecEvent(int i);

        void onEndSendEvent(int i);

        void onStartDataRec(String str, long j, int i);

        void onStartDataSend(String str, long j, int i);

        void onStartRecEvent(String str, List<FileNode> list, long j, int i);

        void onStartSendEvent(String str, List<FileNode> list, long j, int i);

        void onStatusCallback(int i, String str);
    }

    public native int IDMTransFileClientCreate(String str, int i);

    public native boolean IDMTransFileRelease();

    public native int IDMTransFileServerCreate(String str, int i);

    public native boolean getIDMTransFileReadyFlag();

    public native int registerInterface(IDMTransFileCallback iDMTransFileCallback, FileNode fileNode);

    public native int sendFile(String str);

    public native int stopDataTrans(int i);

    static {
        System.loadLibrary("idmtransfile");
    }

    /* loaded from: classes3.dex */
    public class FileNode {
        public String name;
        public long size;
        public String type;

        public FileNode() {
        }
    }

    public boolean setCallback(IDMTransFileCallback iDMTransFileCallback) {
        return registerInterface(iDMTransFileCallback, this.c) != -1;
    }
}
