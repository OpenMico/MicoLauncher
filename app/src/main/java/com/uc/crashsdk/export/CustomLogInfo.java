package com.uc.crashsdk.export;

import java.util.ArrayList;

/* compiled from: ProGuard */
/* loaded from: classes2.dex */
public class CustomLogInfo {
    public boolean mAddBuildId;
    public boolean mAddFooter;
    public boolean mAddHeader;
    public boolean mAddLogcat;
    public boolean mAddThreadsDump;
    public ArrayList<String> mCachedInfos;
    public ArrayList<String> mCallbacks;
    public StringBuffer mData;
    public ArrayList<String> mDumpFiles;
    public ArrayList<Integer> mDumpTids;
    public String mLogType;
    public boolean mUploadNow;

    public CustomLogInfo(StringBuffer stringBuffer, String str) {
        this.mAddHeader = true;
        this.mAddFooter = true;
        this.mAddLogcat = false;
        this.mUploadNow = false;
        this.mAddThreadsDump = false;
        this.mAddBuildId = false;
        this.mDumpFiles = null;
        this.mCallbacks = null;
        this.mCachedInfos = null;
        this.mDumpTids = null;
        this.mData = stringBuffer;
        this.mLogType = str;
    }

    public CustomLogInfo(CustomLogInfo customLogInfo) {
        this.mAddHeader = true;
        this.mAddFooter = true;
        this.mAddLogcat = false;
        this.mUploadNow = false;
        this.mAddThreadsDump = false;
        this.mAddBuildId = false;
        this.mDumpFiles = null;
        this.mCallbacks = null;
        this.mCachedInfos = null;
        this.mDumpTids = null;
        this.mData = customLogInfo.mData;
        this.mLogType = customLogInfo.mLogType;
        this.mAddHeader = customLogInfo.mAddHeader;
        this.mAddFooter = customLogInfo.mAddFooter;
        this.mAddLogcat = customLogInfo.mAddLogcat;
        this.mUploadNow = customLogInfo.mUploadNow;
        this.mAddThreadsDump = customLogInfo.mAddThreadsDump;
        this.mAddBuildId = customLogInfo.mAddBuildId;
        ArrayList<String> arrayList = customLogInfo.mDumpFiles;
        if (arrayList != null) {
            this.mDumpFiles = new ArrayList<>(arrayList);
        }
        ArrayList<String> arrayList2 = customLogInfo.mCallbacks;
        if (arrayList2 != null) {
            this.mCallbacks = new ArrayList<>(arrayList2);
        }
        ArrayList<String> arrayList3 = customLogInfo.mCachedInfos;
        if (arrayList3 != null) {
            this.mCachedInfos = new ArrayList<>(arrayList3);
        }
        ArrayList<Integer> arrayList4 = customLogInfo.mDumpTids;
        if (arrayList4 != null) {
            this.mDumpTids = new ArrayList<>(arrayList4);
        }
    }
}
