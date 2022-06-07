package com.efs.sdk.base.newsharedpreferences;

import android.content.SharedPreferences;
import android.os.FileObserver;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.SystemClock;
import android.util.Log;
import android.util.Pair;
import com.google.android.exoplayer2.SimpleExoPlayer;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes.dex */
public final class SharedPreferencesNewImpl implements SharedPreferences {
    private static final String BACKUP_FILE_SUFFIX = ".bak";
    private static final int CONTENT_LENGTH_LOST = 1;
    private static final int CONTENT_OVER_SIZE = 7;
    private static final int DATA_TYPE_ERROR = 8;
    private static final int DATA_TYPE_INVALID = 9;
    private static final long DELAY_TIME_TO_SAVE = 1000;
    private static final byte FINISH_MARK = 18;
    private static final int FINISH_MARK_LENGTH = 1;
    private static final int ID_LENGTH = 4;
    private static final int INIT_EXCEPTION = 10;
    private static final int LOAD_BAK_FILE = 12;
    private static final int MAPPED_BUFFER_ERROR = 4;
    private static final int MAX_HANDLERTHREAD = 3;
    private static final long MAX_LOCK_FILE_TIME = 10000;
    private static final int MAX_NUM = Integer.MAX_VALUE;
    private static final int MAX_TRY_TIME = 6;
    private static final int MIN_INCREASE_LENGTH = 1024;
    private static final int MODIFY_ID_LOST = 2;
    private static final int OTHER_EXCEPTION = 11;
    private static final String TAG = "SharedPreferencesNew";
    private static final long TRY_RELOAD_INTERVAL = 60;
    private static final int TRY_SAVE_TIME_DELAY = 2000;
    private static final int TYPE_CAST_EXCEPTION = 13;
    private static final int VALUE_LOST = 3;
    private static final Object mFileMonitorSyncObj = new Object();
    private static HandlerThread[] mHandlerThreadPool = new HandlerThread[3];
    private static final int mSaveMessageID = 21310;
    private static ExecutorService sCachedThreadPool;
    private String mBackupFilePath;
    private int mCurTryTime;
    private Vector<SharedPreferences.Editor> mEditorList;
    private OnSharedPreferenceErrorListener mErrorListener;
    private File mFile;
    private FileChannel mFileChannel;
    private FileMonitor mFileMonitor;
    private Handler mHandler;
    private boolean mIsSaving;
    private final ArrayList<SharedPreferences.OnSharedPreferenceChangeListener> mListeners;
    private boolean mLoaded;
    private final LinkedHashMap<String, Object> mMap;
    private MappedByteBuffer mMappedByteBuffer;
    private int mModifyErrorCnt;
    private int mModifyID;
    private RunnableEx mSaveRunnable;
    private final Object mSyncObj;
    private final Object mSyncSaveObj;
    private final Runnable mTryReloadRunnable;
    private long mTryReloadStartTime;

    /* loaded from: classes.dex */
    public interface OnSharedPreferenceErrorListener {
        void onError(String str, int i, long j);
    }

    final boolean checkTypeValid(byte b) {
        return b == 4 || b == 2 || b == 1 || b == 3 || b == 5;
    }

    static {
        for (int i = 0; i < 3; i++) {
            mHandlerThreadPool[i] = new HandlerThread("newsp".concat(String.valueOf(i)));
            mHandlerThreadPool[i].start();
        }
        sCachedThreadPool = Executors.newCachedThreadPool();
    }

    /* loaded from: classes.dex */
    class SUPPORTED_TYPE {
        static final byte TYPE_BOOLEAN = 4;
        static final byte TYPE_FLOAT = 2;
        static final byte TYPE_INT = 1;
        static final byte TYPE_LONG = 3;
        static final byte TYPE_STRING = 5;

        private SUPPORTED_TYPE() {
        }
    }

    public SharedPreferencesNewImpl(File file) {
        this(file, 0, null, false);
    }

    public SharedPreferencesNewImpl(File file, boolean z) {
        this(file, 0, null, z);
    }

    public SharedPreferencesNewImpl(File file, OnSharedPreferenceErrorListener onSharedPreferenceErrorListener) {
        this(file, 0, onSharedPreferenceErrorListener, false);
    }

    public SharedPreferencesNewImpl(File file, int i, OnSharedPreferenceErrorListener onSharedPreferenceErrorListener) {
        this(file, i, onSharedPreferenceErrorListener, false);
    }

    public SharedPreferencesNewImpl(File file, int i, OnSharedPreferenceErrorListener onSharedPreferenceErrorListener, boolean z) {
        this.mMap = new LinkedHashMap<>();
        this.mListeners = new ArrayList<>();
        this.mLoaded = true;
        this.mSyncObj = new Object();
        this.mSyncSaveObj = new Object();
        this.mEditorList = new Vector<>();
        this.mIsSaving = false;
        this.mTryReloadRunnable = new Runnable() { // from class: com.efs.sdk.base.newsharedpreferences.SharedPreferencesNewImpl.2
            @Override // java.lang.Runnable
            public void run() {
                int modifyID = SharedPreferencesNewImpl.this.getModifyID();
                if (modifyID > 0 && modifyID != SharedPreferencesNewImpl.this.mModifyID) {
                    SharedPreferencesNewImpl.this.saveInner(false);
                }
            }
        };
        this.mSaveRunnable = new RunnableEx() { // from class: com.efs.sdk.base.newsharedpreferences.SharedPreferencesNewImpl.4
            @Override // java.lang.Runnable
            public void run() {
                SharedPreferencesNewImpl.this.saveInner(((Boolean) getArg()).booleanValue());
            }
        };
        this.mErrorListener = onSharedPreferenceErrorListener;
        this.mHandler = new Handler(getHandlerThread().getLooper());
        this.mFile = file;
        this.mBackupFilePath = file.getAbsolutePath() + BACKUP_FILE_SUFFIX;
        if (initBuffer()) {
            startLoadFromDisk(z);
        }
        this.mHandler.post(new Runnable() { // from class: com.efs.sdk.base.newsharedpreferences.SharedPreferencesNewImpl.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    File file2 = new File(SharedPreferencesNewImpl.this.mBackupFilePath);
                    if (!file2.exists()) {
                        file2.createNewFile();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override // android.content.SharedPreferences
    public final Map<String, ?> getAll() {
        HashMap hashMap;
        awaitLoadedLocked();
        synchronized (this.mMap) {
            hashMap = new HashMap(this.mMap);
        }
        return hashMap;
    }

    @Override // android.content.SharedPreferences
    public final String getString(String str, String str2) {
        awaitLoadedLocked();
        try {
            synchronized (this.mMap) {
                try {
                    String str3 = (String) this.mMap.get(str);
                    if (str3 != null) {
                        str2 = str3;
                    }
                } catch (ClassCastException e) {
                    if (this.mErrorListener != null) {
                        OnSharedPreferenceErrorListener onSharedPreferenceErrorListener = this.mErrorListener;
                        StringBuilder sb = new StringBuilder();
                        sb.append(this.mFile != null ? this.mFile.getAbsolutePath() : null);
                        sb.append("#");
                        sb.append(str);
                        sb.append(e);
                        onSharedPreferenceErrorListener.onError(sb.toString(), 13, this.mFile != null ? this.mFile.length() : 0L);
                    }
                    return str2;
                }
            }
            return str2;
        } catch (Throwable th) {
            throw th;
        }
    }

    @Override // android.content.SharedPreferences
    public final Set<String> getStringSet(String str, Set<String> set) {
        throw new RuntimeException("putStringSet is not supported!");
    }

    @Override // android.content.SharedPreferences
    public final int getInt(String str, int i) {
        awaitLoadedLocked();
        try {
            synchronized (this.mMap) {
                try {
                    Integer num = (Integer) this.mMap.get(str);
                    if (num != null) {
                        i = num.intValue();
                    }
                } catch (ClassCastException e) {
                    if (this.mErrorListener != null) {
                        OnSharedPreferenceErrorListener onSharedPreferenceErrorListener = this.mErrorListener;
                        StringBuilder sb = new StringBuilder();
                        sb.append(this.mFile != null ? this.mFile.getAbsolutePath() : null);
                        sb.append("#");
                        sb.append(str);
                        sb.append(e);
                        onSharedPreferenceErrorListener.onError(sb.toString(), 13, this.mFile != null ? this.mFile.length() : 0L);
                    }
                    return i;
                }
            }
            return i;
        } catch (Throwable th) {
            throw th;
        }
    }

    @Override // android.content.SharedPreferences
    public final long getLong(String str, long j) {
        awaitLoadedLocked();
        try {
            synchronized (this.mMap) {
                try {
                    Long l = (Long) this.mMap.get(str);
                    if (l != null) {
                        j = l.longValue();
                    }
                } catch (ClassCastException e) {
                    if (this.mErrorListener != null) {
                        OnSharedPreferenceErrorListener onSharedPreferenceErrorListener = this.mErrorListener;
                        StringBuilder sb = new StringBuilder();
                        sb.append(this.mFile != null ? this.mFile.getAbsolutePath() : null);
                        sb.append("#");
                        sb.append(str);
                        sb.append(e);
                        onSharedPreferenceErrorListener.onError(sb.toString(), 13, this.mFile != null ? this.mFile.length() : 0L);
                    }
                    return j;
                }
            }
            return j;
        } catch (Throwable th) {
            throw th;
        }
    }

    @Override // android.content.SharedPreferences
    public final float getFloat(String str, float f) {
        awaitLoadedLocked();
        try {
            synchronized (this.mMap) {
                try {
                    Float f2 = (Float) this.mMap.get(str);
                    if (f2 != null) {
                        f = f2.floatValue();
                    }
                } catch (ClassCastException e) {
                    if (this.mErrorListener != null) {
                        OnSharedPreferenceErrorListener onSharedPreferenceErrorListener = this.mErrorListener;
                        StringBuilder sb = new StringBuilder();
                        sb.append(this.mFile != null ? this.mFile.getAbsolutePath() : null);
                        sb.append("#");
                        sb.append(str);
                        sb.append(e);
                        onSharedPreferenceErrorListener.onError(sb.toString(), 13, this.mFile != null ? this.mFile.length() : 0L);
                    }
                    return f;
                }
            }
            return f;
        } catch (Throwable th) {
            throw th;
        }
    }

    @Override // android.content.SharedPreferences
    public final boolean getBoolean(String str, boolean z) {
        awaitLoadedLocked();
        try {
            synchronized (this.mMap) {
                try {
                    Boolean bool = (Boolean) this.mMap.get(str);
                    if (bool != null) {
                        z = bool.booleanValue();
                    }
                } catch (ClassCastException e) {
                    if (this.mErrorListener != null) {
                        OnSharedPreferenceErrorListener onSharedPreferenceErrorListener = this.mErrorListener;
                        StringBuilder sb = new StringBuilder();
                        sb.append(this.mFile != null ? this.mFile.getAbsolutePath() : null);
                        sb.append("#");
                        sb.append(str);
                        sb.append(e);
                        onSharedPreferenceErrorListener.onError(sb.toString(), 13, this.mFile != null ? this.mFile.length() : 0L);
                    }
                    return z;
                }
            }
            return z;
        } catch (Throwable th) {
            throw th;
        }
    }

    @Override // android.content.SharedPreferences
    public final boolean contains(String str) {
        boolean containsKey;
        awaitLoadedLocked();
        synchronized (this.mMap) {
            containsKey = this.mMap.containsKey(str);
        }
        return containsKey;
    }

    @Override // android.content.SharedPreferences
    public final SharedPreferences.Editor edit() {
        awaitLoadedLocked();
        return new EditorImpl();
    }

    @Override // android.content.SharedPreferences
    public final void registerOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener) {
        if (onSharedPreferenceChangeListener != null) {
            synchronized (this.mListeners) {
                this.mListeners.add(onSharedPreferenceChangeListener);
                if (this.mFileMonitor == null) {
                    try {
                        File file = new File(this.mBackupFilePath);
                        if (!file.exists()) {
                            file.createNewFile();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    this.mFileMonitor = new FileMonitor(this.mBackupFilePath, 2);
                }
            }
            synchronized (mFileMonitorSyncObj) {
                this.mFileMonitor.startWatching();
            }
        }
    }

    @Override // android.content.SharedPreferences
    public final void unregisterOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener) {
        if (onSharedPreferenceChangeListener != null) {
            synchronized (this.mListeners) {
                this.mListeners.remove(onSharedPreferenceChangeListener);
                if (this.mFileMonitor != null && this.mListeners.size() <= 0) {
                    this.mFileMonitor.stopWatching();
                }
            }
        }
    }

    /* loaded from: classes.dex */
    public final class EditorImpl implements SharedPreferences.Editor {
        private boolean mClear;
        private HashMap<String, Object> mModified = new HashMap<>();

        public EditorImpl() {
        }

        @Override // android.content.SharedPreferences.Editor
        public final SharedPreferences.Editor putString(String str, String str2) {
            synchronized (this) {
                this.mModified.put(str, str2);
            }
            return this;
        }

        @Override // android.content.SharedPreferences.Editor
        public final SharedPreferences.Editor putStringSet(String str, Set<String> set) {
            throw new RuntimeException("putStringSet is not supported!");
        }

        @Override // android.content.SharedPreferences.Editor
        public final SharedPreferences.Editor putInt(String str, int i) {
            synchronized (this) {
                this.mModified.put(str, Integer.valueOf(i));
            }
            return this;
        }

        @Override // android.content.SharedPreferences.Editor
        public final SharedPreferences.Editor putLong(String str, long j) {
            synchronized (this) {
                this.mModified.put(str, Long.valueOf(j));
            }
            return this;
        }

        @Override // android.content.SharedPreferences.Editor
        public final SharedPreferences.Editor putFloat(String str, float f) {
            synchronized (this) {
                this.mModified.put(str, Float.valueOf(f));
            }
            return this;
        }

        @Override // android.content.SharedPreferences.Editor
        public final SharedPreferences.Editor putBoolean(String str, boolean z) {
            synchronized (this) {
                this.mModified.put(str, Boolean.valueOf(z));
            }
            return this;
        }

        @Override // android.content.SharedPreferences.Editor
        public final SharedPreferences.Editor remove(String str) {
            synchronized (this) {
                this.mModified.put(str, null);
            }
            return this;
        }

        @Override // android.content.SharedPreferences.Editor
        public final SharedPreferences.Editor clear() {
            synchronized (this) {
                this.mClear = true;
            }
            return this;
        }

        @Override // android.content.SharedPreferences.Editor
        public final boolean commit() {
            SharedPreferencesNewImpl.this.save(this, false, true, false);
            return true;
        }

        @Override // android.content.SharedPreferences.Editor
        public final void apply() {
            SharedPreferencesNewImpl.this.save(this, false, false, true);
        }

        final boolean doClear() {
            boolean z;
            synchronized (this) {
                z = this.mClear;
                this.mClear = false;
            }
            return z;
        }

        final HashMap<String, Object> getAll() {
            HashMap<String, Object> hashMap;
            synchronized (this) {
                hashMap = this.mModified;
            }
            return hashMap;
        }
    }

    private boolean merge(SharedPreferences.Editor editor, Map map, boolean z) {
        if (editor == null) {
            return false;
        }
        EditorImpl editorImpl = (EditorImpl) editor;
        boolean doClear = editorImpl.doClear();
        if (doClear) {
            map.clear();
            this.mEditorList.clear();
        }
        HashMap<String, Object> all = editorImpl.getAll();
        if (all.size() == 0) {
            return doClear;
        }
        synchronized (editor) {
            for (Map.Entry<String, Object> entry : all.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if (value == null) {
                    map.remove(key);
                } else {
                    if (map.containsKey(key)) {
                        map.remove(key);
                    }
                    map.put(key, value);
                }
                if (!z) {
                    notifyDataChanged(key);
                }
            }
        }
        return true;
    }

    private void notifyDataChanged(String str) {
        if (this.mListeners.size() > 0) {
            for (int i = 0; i < this.mListeners.size(); i++) {
                SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener = this.mListeners.get(i);
                if (onSharedPreferenceChangeListener != null) {
                    onSharedPreferenceChangeListener.onSharedPreferenceChanged(this, str);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void tryReload() {
        if (SystemClock.uptimeMillis() - this.mTryReloadStartTime > 60) {
            this.mTryReloadStartTime = SystemClock.uptimeMillis();
            this.mHandler.removeCallbacks(this.mTryReloadRunnable);
            this.mHandler.post(this.mTryReloadRunnable);
        }
    }

    private boolean tryReloadWhenSave() {
        int modifyID = getModifyID();
        if (modifyID <= 0 || modifyID == this.mModifyID) {
            return false;
        }
        load(true);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveInner(final boolean z) {
        synchronized (this.mSyncSaveObj) {
            FileLock lockFile = lockFile(false);
            if (lockFile != null) {
                this.mIsSaving = true;
                if (tryReloadWhenSave()) {
                    mergeWhenReload();
                    notifyDataChanged(null);
                }
                synchronized (this.mMap) {
                    if (this.mEditorList.size() <= 0) {
                        try {
                            lockFile.release();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        this.mIsSaving = false;
                        return;
                    }
                    saveToMappedBuffer(obtainTotalBytes(), z);
                    backup();
                    try {
                        lockFile.release();
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                    this.mIsSaving = false;
                }
            }
            int i = this.mCurTryTime;
            this.mCurTryTime = i + 1;
            if (i < 6) {
                this.mHandler.postDelayed(new Runnable() { // from class: com.efs.sdk.base.newsharedpreferences.SharedPreferencesNewImpl.3
                    @Override // java.lang.Runnable
                    public void run() {
                        SharedPreferencesNewImpl.this.saveInner(z);
                    }
                }, SimpleExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x001f, code lost:
        r4.mEditorList.add(r5);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void save(android.content.SharedPreferences.Editor r5, boolean r6, boolean r7, boolean r8) {
        /*
            r4 = this;
            if (r5 != 0) goto L_0x0003
            return
        L_0x0003:
            java.util.LinkedHashMap<java.lang.String, java.lang.Object> r0 = r4.mMap
            monitor-enter(r0)
            r1 = 0
            r4.mCurTryTime = r1     // Catch: all -> 0x004d
            r2 = 1
            java.util.LinkedHashMap<java.lang.String, java.lang.Object> r3 = r4.mMap     // Catch: all -> 0x004d
            boolean r3 = r4.merge(r5, r3, r1)     // Catch: all -> 0x004d
            if (r3 != 0) goto L_0x001c
            java.util.Vector<android.content.SharedPreferences$Editor> r2 = r4.mEditorList     // Catch: all -> 0x004d
            int r2 = r2.size()     // Catch: all -> 0x004d
            if (r2 != 0) goto L_0x001d
            monitor-exit(r0)     // Catch: all -> 0x004d
            return
        L_0x001c:
            r1 = r2
        L_0x001d:
            if (r1 == 0) goto L_0x0024
            java.util.Vector<android.content.SharedPreferences$Editor> r1 = r4.mEditorList     // Catch: all -> 0x004d
            r1.add(r5)     // Catch: all -> 0x004d
        L_0x0024:
            monitor-exit(r0)     // Catch: all -> 0x004d
            if (r7 == 0) goto L_0x002b
            r4.saveInner(r6)
            return
        L_0x002b:
            if (r8 == 0) goto L_0x0030
            r7 = 1000(0x3e8, double:4.94E-321)
            goto L_0x0032
        L_0x0030:
            r7 = 0
        L_0x0032:
            com.efs.sdk.base.newsharedpreferences.SharedPreferencesNewImpl$RunnableEx r5 = r4.mSaveRunnable
            java.lang.Boolean r6 = java.lang.Boolean.valueOf(r6)
            r5.setArg(r6)
            android.os.Handler r5 = r4.mHandler
            com.efs.sdk.base.newsharedpreferences.SharedPreferencesNewImpl$RunnableEx r6 = r4.mSaveRunnable
            android.os.Message r5 = android.os.Message.obtain(r5, r6)
            r6 = 21310(0x533e, float:2.9862E-41)
            r5.what = r6
            android.os.Handler r6 = r4.mHandler
            r6.sendMessageDelayed(r5, r7)
            return
        L_0x004d:
            r5 = move-exception
            monitor-exit(r0)     // Catch: all -> 0x004d
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.efs.sdk.base.newsharedpreferences.SharedPreferencesNewImpl.save(android.content.SharedPreferences$Editor, boolean, boolean, boolean):void");
    }

    private Pair<Integer, byte[][]> getDataBytes() {
        byte[][] bArr;
        ArrayList arrayList;
        synchronized (this.mMap) {
            bArr = new byte[this.mMap.size() * 5];
            arrayList = new ArrayList(this.mMap.entrySet());
            this.mEditorList.clear();
        }
        int i = 0;
        int i2 = 0;
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            Map.Entry entry = (Map.Entry) arrayList.get(size);
            String str = (String) entry.getKey();
            Object value = entry.getValue();
            if (!(str == null || str.trim().length() <= 0 || value == null)) {
                byte[] bytes = str.getBytes();
                byte[] intToBytes = ByteIntUtils.intToBytes(bytes.length);
                bArr[i2] = intToBytes;
                bArr[i2 + 1] = bytes;
                int length = i + intToBytes.length + bytes.length;
                byte[] bytes2 = getBytes(value);
                byte[] intToBytes2 = ByteIntUtils.intToBytes(bytes2.length);
                bArr[i2 + 2] = intToBytes2;
                bArr[i2 + 3] = bytes2;
                byte[] bArr2 = new byte[1];
                bArr2[0] = (byte) getObjectType(value);
                bArr[i2 + 4] = bArr2;
                i = length + intToBytes2.length + bytes2.length + 1;
                i2 += 5;
            }
        }
        return new Pair<>(Integer.valueOf(i), bArr);
    }

    private void saveToMappedBuffer(byte[] bArr, boolean z) {
        synchronized (this.mSyncObj) {
            this.mMappedByteBuffer.position(0);
            safeBufferPut(this.mMappedByteBuffer, bArr);
            if (z) {
                this.mMappedByteBuffer.force();
            }
        }
    }

    private int increaseModifyID() {
        this.mModifyID = (this.mModifyID + 1) % Integer.MAX_VALUE;
        return this.mModifyID;
    }

    private int getContentLength() {
        if (this.mMappedByteBuffer == null || this.mFileChannel == null) {
            return -1;
        }
        synchronized (this.mSyncObj) {
            this.mMappedByteBuffer.position(0);
            byte[] bArr = new byte[4];
            safeBufferGet(this.mMappedByteBuffer, bArr);
            int bytesToInt = ByteIntUtils.bytesToInt(bArr);
            this.mMappedByteBuffer.position(4);
            byte b = this.mMappedByteBuffer.get();
            if ((b == 18 || b == getMaskByte(bArr)) && bytesToInt >= 0) {
                int i = Integer.MAX_VALUE;
                if (bytesToInt <= Integer.MAX_VALUE) {
                    i = bytesToInt;
                }
                return i;
            }
            if (this.mErrorListener != null) {
                this.mErrorListener.onError(this.mFile != null ? this.mFile.getAbsolutePath() : null, 1, this.mFile != null ? this.mFile.length() : 0L);
            }
            return -1;
        }
    }

    private void reallocBuffer() {
        if (this.mMappedByteBuffer != null) {
            synchronized (this.mSyncObj) {
                try {
                    int contentLength = getContentLength();
                    if (contentLength > this.mMappedByteBuffer.capacity()) {
                        allocBuffer(contentLength + 1024);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void load(boolean z) {
        byte[] bArr = null;
        FileLock lockFile = z ? null : lockFile(true);
        if (lockFile != null || z) {
            boolean z2 = false;
            try {
                reallocBuffer();
                if (!(this.mMappedByteBuffer == null || this.mMappedByteBuffer.capacity() == 0)) {
                    long contentLength = getContentLength();
                    if (contentLength <= 10) {
                        try {
                            z2 = parseBytesIntoMap(null, true);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (!z2 || this.mModifyID < 0) {
                            loadFromBakFile();
                        }
                        if (lockFile != null) {
                            try {
                                lockFile.release();
                                return;
                            } catch (Exception e2) {
                                e2.printStackTrace();
                                return;
                            }
                        } else {
                            return;
                        }
                    } else {
                        this.mModifyID = getModifyID();
                        if (this.mModifyID > 0) {
                            synchronized (this.mSyncObj) {
                                this.mMappedByteBuffer.position(10);
                                bArr = new byte[((int) contentLength) - 10];
                                safeBufferGet(this.mMappedByteBuffer, bArr);
                            }
                        }
                        try {
                            z2 = parseBytesIntoMap(bArr, true);
                        } catch (Exception e3) {
                            e3.printStackTrace();
                        }
                        if (!z2 || (bArr == null && this.mModifyID < 0)) {
                            loadFromBakFile();
                        }
                        if (lockFile != null) {
                            try {
                                lockFile.release();
                                return;
                            } catch (Exception e4) {
                                e4.printStackTrace();
                                return;
                            }
                        } else {
                            return;
                        }
                    }
                }
                try {
                    z2 = parseBytesIntoMap(null, true);
                } catch (Exception e5) {
                    e5.printStackTrace();
                }
                if (!z2 || this.mModifyID < 0) {
                    loadFromBakFile();
                }
                if (lockFile != null) {
                    try {
                        lockFile.release();
                    } catch (Exception e6) {
                        e6.printStackTrace();
                    }
                }
            } catch (Throwable th) {
                try {
                    z2 = parseBytesIntoMap(bArr, true);
                } catch (Exception e7) {
                    e7.printStackTrace();
                }
                if (!z2 || (bArr == null && this.mModifyID < 0)) {
                    loadFromBakFile();
                }
                if (lockFile != null) {
                    try {
                        lockFile.release();
                    } catch (Exception e8) {
                        e8.printStackTrace();
                    }
                }
                throw th;
            }
        } else if (!z) {
            loadFromBakFile();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final int getModifyID() {
        if (this.mMappedByteBuffer == null) {
            return -1;
        }
        synchronized (this.mSyncObj) {
            this.mMappedByteBuffer.position(5);
            byte[] bArr = new byte[4];
            safeBufferGet(this.mMappedByteBuffer, bArr);
            int bytesToInt = ByteIntUtils.bytesToInt(bArr);
            this.mMappedByteBuffer.position(9);
            byte b = this.mMappedByteBuffer.get();
            if ((b == 18 || b == getMaskByte(bArr)) && bytesToInt >= 0) {
                return bytesToInt;
            }
            this.mModifyErrorCnt++;
            if (this.mModifyErrorCnt < 3 && this.mErrorListener != null) {
                this.mErrorListener.onError(this.mFile != null ? this.mFile.getAbsolutePath() : null, 2, this.mFile != null ? this.mFile.length() : 0L);
            }
            return -1;
        }
    }

    private void startLoadFromDisk(boolean z) {
        synchronized (this) {
            this.mLoaded = false;
        }
        Runnable runnable = new Runnable() { // from class: com.efs.sdk.base.newsharedpreferences.SharedPreferencesNewImpl.5
            @Override // java.lang.Runnable
            public void run() {
                synchronized (SharedPreferencesNewImpl.this) {
                    SharedPreferencesNewImpl.this.loadFromDiskLocked();
                }
            }
        };
        if (z) {
            runnable.run();
        } else {
            sCachedThreadPool.execute(runnable);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loadFromDiskLocked() {
        if (!this.mLoaded) {
            load(false);
            this.mLoaded = true;
            notifyAll();
        }
    }

    private void awaitLoadedLocked() {
        if (!this.mLoaded) {
            synchronized (this) {
                while (!this.mLoaded) {
                    wait();
                }
            }
        }
        tryReload();
    }

    private boolean safeBufferGet(MappedByteBuffer mappedByteBuffer, byte[] bArr) {
        if (mappedByteBuffer == null || bArr == null || bArr.length == 0) {
            return false;
        }
        Arrays.fill(bArr, (byte) 0);
        int position = mappedByteBuffer.position();
        if (position + bArr.length > mappedByteBuffer.capacity()) {
            return false;
        }
        mappedByteBuffer.get(bArr);
        return true;
    }

    private MappedByteBuffer allocBuffer(int i) {
        MappedByteBuffer mappedByteBuffer = this.mMappedByteBuffer;
        int position = mappedByteBuffer != null ? mappedByteBuffer.position() : 0;
        try {
            this.mMappedByteBuffer = this.mFileChannel.map(FileChannel.MapMode.READ_WRITE, 0L, i);
        } catch (Exception e) {
            e.printStackTrace();
        }
        MappedByteBuffer mappedByteBuffer2 = this.mMappedByteBuffer;
        if (mappedByteBuffer2 != null) {
            mappedByteBuffer2.position(position);
        }
        return this.mMappedByteBuffer;
    }

    private boolean initBuffer() {
        boolean z = true;
        if (this.mMappedByteBuffer != null) {
            return true;
        }
        try {
            if (!this.mFile.exists()) {
                this.mFile.getParentFile().mkdirs();
                this.mFile.createNewFile();
                z = new File(this.mBackupFilePath).exists();
            } else if (this.mFile.length() == 0) {
                if (this.mErrorListener != null) {
                    this.mErrorListener.onError(this.mFile.getAbsolutePath(), 4, this.mFile.length());
                }
                z = false;
            }
            this.mFileChannel = new RandomAccessFile(this.mFile, "rw").getChannel();
            allocBuffer(10);
            if (z) {
                return z;
            }
            initFileHeader();
            return z;
        } catch (Exception e) {
            e.printStackTrace();
            OnSharedPreferenceErrorListener onSharedPreferenceErrorListener = this.mErrorListener;
            if (onSharedPreferenceErrorListener != null) {
                onSharedPreferenceErrorListener.onError(this.mFile.getAbsolutePath() + StringUtils.SPACE + e.getCause(), 10, -1L);
            }
            return false;
        }
    }

    private FileLock lockFile(boolean z) {
        FileChannel fileChannel = this.mFileChannel;
        FileLock fileLock = null;
        if (fileChannel == null) {
            return null;
        }
        if (z) {
            long uptimeMillis = SystemClock.uptimeMillis();
            while (fileLock == null) {
                try {
                    fileLock = this.mFileChannel.tryLock();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (fileLock == null) {
                    try {
                        Thread.sleep(100L);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
                if (SystemClock.uptimeMillis() - uptimeMillis > 10000) {
                    return fileLock;
                }
            }
            return fileLock;
        }
        try {
            return fileChannel.tryLock();
        } catch (Exception e3) {
            e3.printStackTrace();
            return null;
        }
    }

    public final void setSharedPreferenceErrorListener(OnSharedPreferenceErrorListener onSharedPreferenceErrorListener) {
        this.mErrorListener = onSharedPreferenceErrorListener;
    }

    private void safeBufferPut(MappedByteBuffer mappedByteBuffer, byte[] bArr) {
        if (mappedByteBuffer != null && bArr != null && bArr.length != 0) {
            if (mappedByteBuffer.position() + bArr.length >= mappedByteBuffer.capacity()) {
                mappedByteBuffer = allocBuffer(mappedByteBuffer.position() + bArr.length + 1024);
            }
            mappedByteBuffer.put(bArr);
        }
    }

    private void mergeWhenReload() {
        synchronized (this.mMap) {
            if (this.mEditorList.size() > 0) {
                Iterator<SharedPreferences.Editor> it = this.mEditorList.iterator();
                while (it.hasNext()) {
                    merge(it.next(), this.mMap, true);
                }
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:27:0x005b, code lost:
        if (r12.mErrorListener == null) goto L_0x00fa;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x005d, code lost:
        r3 = r12.mErrorListener;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0061, code lost:
        if (r12.mFile == null) goto L_0x006a;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0063, code lost:
        r5 = r12.mFile.getAbsolutePath();
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x006a, code lost:
        r5 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x006b, code lost:
        r3.onError(r5, 8, r13.length);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean parseBytesIntoMap(byte[] r13, boolean r14) {
        /*
            Method dump skipped, instructions count: 265
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.efs.sdk.base.newsharedpreferences.SharedPreferencesNewImpl.parseBytesIntoMap(byte[], boolean):boolean");
    }

    private Pair<byte[], Integer> getOneString(byte[] bArr, int i) {
        int i2;
        byte[] bArr2 = new byte[4];
        System.arraycopy(bArr, i, bArr2, 0, 4);
        int i3 = i + 4;
        if (bArr[i3] == 18 || bArr[i3] == getMaskByte(bArr2)) {
            int i4 = i3 + 1;
            int bytesToInt = ByteIntUtils.bytesToInt(bArr2);
            if (bytesToInt < 0 || (i2 = i4 + bytesToInt) >= bArr.length || bytesToInt > Integer.MAX_VALUE) {
                throw new Exception("length string is invalid");
            }
            byte[] bArr3 = null;
            if (bytesToInt != 0) {
                bArr3 = new byte[bytesToInt];
                System.arraycopy(bArr, i4, bArr3, 0, bytesToInt);
                if (bArr[i2] == 18 || bArr[i2] == getMaskByte(bArr3)) {
                    i4 = i2;
                } else {
                    throw new Exception("Stored bytes' finish mark missing");
                }
            }
            return new Pair<>(bArr3, Integer.valueOf(i4 + 1));
        }
        throw new Exception("length string's finish mark missing");
    }

    private byte[] obtainTotalBytes() {
        Pair<Integer, byte[][]> dataBytes = getDataBytes();
        int intValue = ((Integer) dataBytes.first).intValue() + 10 + (((byte[][]) dataBytes.second).length * 1);
        if (intValue > Integer.MAX_VALUE) {
            intValue = Integer.MAX_VALUE;
        }
        byte[] bArr = new byte[intValue];
        byte[] intToBytes = ByteIntUtils.intToBytes(intValue);
        System.arraycopy(intToBytes, 0, bArr, 0, intToBytes.length);
        int length = intToBytes.length + 0;
        bArr[length] = getMaskByte(intToBytes);
        int i = length + 1;
        byte[] intToBytes2 = ByteIntUtils.intToBytes(increaseModifyID());
        System.arraycopy(intToBytes2, 0, bArr, i, intToBytes2.length);
        int length2 = i + intToBytes2.length;
        bArr[length2] = getMaskByte(intToBytes2);
        int i2 = length2 + 1;
        byte[][] bArr2 = (byte[][]) dataBytes.second;
        int length3 = bArr2.length;
        int i3 = 0;
        while (true) {
            if (i3 >= length3) {
                break;
            }
            byte[] bArr3 = bArr2[i3];
            if (bArr3 != null) {
                if (bArr3.length + i2 + 1 <= Integer.MAX_VALUE) {
                    System.arraycopy(bArr3, 0, bArr, i2, bArr3.length);
                    int length4 = i2 + bArr3.length;
                    bArr[length4] = getMaskByte(bArr3);
                    i2 = length4 + 1;
                } else {
                    StringBuilder sb = new StringBuilder("Write too much data in ");
                    File file = this.mFile;
                    String str = null;
                    sb.append(file != null ? file.getAbsolutePath() : null);
                    Log.e(TAG, sb.toString());
                    OnSharedPreferenceErrorListener onSharedPreferenceErrorListener = this.mErrorListener;
                    if (onSharedPreferenceErrorListener != null) {
                        File file2 = this.mFile;
                        if (file2 != null) {
                            str = file2.getAbsolutePath();
                        }
                        onSharedPreferenceErrorListener.onError(str, 7, -1L);
                    }
                }
            }
            i3++;
        }
        return bArr;
    }

    private void initFileHeader() {
        if (this.mMappedByteBuffer != null) {
            byte[] bArr = new byte[10];
            byte[] intToBytes = ByteIntUtils.intToBytes(0);
            System.arraycopy(intToBytes, 0, bArr, 0, 4);
            bArr[4] = getMaskByte(intToBytes);
            byte[] intToBytes2 = ByteIntUtils.intToBytes(0);
            System.arraycopy(intToBytes2, 0, bArr, 5, 4);
            bArr[9] = getMaskByte(intToBytes2);
            this.mMappedByteBuffer.position(0);
            this.mMappedByteBuffer.put(bArr);
        }
    }

    private int getObjectType(Object obj) {
        if (obj instanceof String) {
            return 5;
        }
        if (obj instanceof Boolean) {
            return 4;
        }
        if (obj instanceof Float) {
            return 2;
        }
        if (obj instanceof Integer) {
            return 1;
        }
        return obj instanceof Long ? 3 : 0;
    }

    private Object getObjectByType(byte[] bArr, int i) {
        if (bArr == null || bArr.length <= 0) {
            return null;
        }
        try {
            if (i == 5) {
                return new String(bArr);
            }
            if (i == 4) {
                boolean z = false;
                if (bArr[0] == 1) {
                    z = true;
                }
                return Boolean.valueOf(z);
            } else if (i == 2) {
                return Float.valueOf(ByteFloatUtils.bytesToFloat(bArr));
            } else {
                if (i == 1) {
                    return Integer.valueOf(ByteIntUtils.bytesToInt(bArr));
                }
                if (i == 3) {
                    return Long.valueOf(ByteLongUtils.bytesToLong(bArr));
                }
                return null;
            }
        } catch (Throwable th) {
            th.printStackTrace();
            return null;
        }
    }

    private byte[] getBytes(Object obj) {
        if (obj == null) {
            return null;
        }
        try {
            if (obj instanceof String) {
                return ((String) obj).getBytes();
            }
            if (obj instanceof Boolean) {
                int i = 1;
                byte[] bArr = new byte[1];
                if (!((Boolean) obj).booleanValue()) {
                    i = 0;
                }
                bArr[0] = (byte) i;
                return bArr;
            } else if (obj instanceof Float) {
                return ByteFloatUtils.floatToBytes(((Float) obj).floatValue());
            } else {
                if (obj instanceof Integer) {
                    return ByteIntUtils.intToBytes(((Integer) obj).intValue());
                }
                if (obj instanceof Long) {
                    return ByteLongUtils.longToBytes(((Long) obj).longValue());
                }
                return null;
            }
        } catch (Throwable th) {
            th.printStackTrace();
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class ByteLongUtils {
        private ByteLongUtils() {
        }

        public static byte[] longToBytes(long j) {
            return ByteBuffer.allocate(8).putLong(j).array();
        }

        public static long bytesToLong(byte[] bArr) {
            return ByteBuffer.wrap(bArr).getLong();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class ByteIntUtils {
        private ByteIntUtils() {
        }

        public static byte[] intToBytes(int i) {
            return ByteBuffer.allocate(4).putInt(i).array();
        }

        public static int bytesToInt(byte[] bArr) {
            return ByteBuffer.wrap(bArr).getInt();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class ByteFloatUtils {
        private ByteFloatUtils() {
        }

        public static byte[] floatToBytes(float f) {
            return ByteBuffer.allocate(4).putFloat(f).array();
        }

        public static float bytesToFloat(byte[] bArr) {
            return ByteBuffer.wrap(bArr).getFloat();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void backup() {
        Throwable th;
        Closeable closeable = null;
        try {
            File file = new File(this.mBackupFilePath);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            try {
                closeable = fileOutputStream.getChannel();
                this.mFileChannel.transferTo(0L, this.mMappedByteBuffer.capacity(), closeable);
                safeClose(fileOutputStream);
                safeClose(closeable);
            } catch (Throwable th2) {
                th = th2;
                closeable = fileOutputStream;
                try {
                    th.printStackTrace();
                    safeClose(closeable);
                    safeClose(closeable);
                } catch (Throwable th3) {
                    th = th3;
                    safeClose(closeable);
                    safeClose(closeable);
                    throw th;
                }
            }
        } catch (Throwable th4) {
            th = th4;
            closeable = null;
        }
    }

    private void safeClose(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:63:0x00f7  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x00fa  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x0110  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean loadFromBakFile() {
        /*
            Method dump skipped, instructions count: 278
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.efs.sdk.base.newsharedpreferences.SharedPreferencesNewImpl.loadFromBakFile():boolean");
    }

    private byte getBCCCode(byte[] bArr) {
        byte b = 0;
        for (byte b2 : bArr) {
            b = (byte) (b ^ b2);
        }
        return b;
    }

    private byte getMaskByte(byte[] bArr) {
        return getBCCCode(bArr);
    }

    /* loaded from: classes.dex */
    final class FileMonitor extends FileObserver {
        public FileMonitor(String str, int i) {
            super(str, i);
        }

        @Override // android.os.FileObserver
        public final void onEvent(int i, String str) {
            if (SharedPreferencesNewImpl.this.mListeners.size() > 0) {
                SharedPreferencesNewImpl.this.tryReload();
            } else {
                stopWatching();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void onDestroy() {
        if (this.mIsSaving || this.mHandler.hasMessages(mSaveMessageID)) {
            saveInner(false);
        }
    }

    private HandlerThread getHandlerThread() {
        int nextInt = new Random().nextInt();
        if (nextInt < 0) {
            nextInt = -nextInt;
        }
        return mHandlerThreadPool[nextInt % 3];
    }

    /* loaded from: classes.dex */
    public static abstract class RunnableEx implements Runnable {
        private Object mArg;

        public void setArg(Object obj) {
            this.mArg = obj;
        }

        public Object getArg() {
            return this.mArg;
        }
    }
}
