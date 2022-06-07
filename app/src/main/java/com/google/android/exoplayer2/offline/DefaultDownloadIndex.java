package com.google.android.exoplayer2.offline;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.text.TextUtils;
import androidx.annotation.GuardedBy;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import com.google.android.exoplayer2.database.DatabaseIOException;
import com.google.android.exoplayer2.database.DatabaseProvider;
import com.google.android.exoplayer2.database.VersionTable;
import com.google.android.exoplayer2.offline.DownloadRequest;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import com.xiaomi.account.openauth.XiaomiOAuthConstants;
import com.xiaomi.mi_soundbox_command_sdk.MiSoundBoxCommandExtras;
import com.xiaomi.mipush.sdk.Constants;
import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes2.dex */
public final class DefaultDownloadIndex implements WritableDownloadIndex {
    private static final String a = a(3, 4);
    private static final String[] b = {"id", "mime_type", MiSoundBoxCommandExtras.URI, "stream_keys", "custom_cache_key", "data", XiaomiOAuthConstants.EXTRA_STATE_2, "start_time_ms", "update_time_ms", "content_length", DownloadService.KEY_STOP_REASON, "failure_reason", "percent_downloaded", "bytes_downloaded", "key_set_id"};
    private final String c;
    private final String d;
    private final DatabaseProvider e;
    private final Object f;
    @GuardedBy("initializationLock")
    private boolean g;

    public DefaultDownloadIndex(DatabaseProvider databaseProvider) {
        this(databaseProvider, "");
    }

    public DefaultDownloadIndex(DatabaseProvider databaseProvider, String str) {
        this.c = str;
        this.e = databaseProvider;
        String valueOf = String.valueOf("ExoPlayerDownloads");
        String valueOf2 = String.valueOf(str);
        this.d = valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf);
        this.f = new Object();
    }

    @Override // com.google.android.exoplayer2.offline.DownloadIndex
    @Nullable
    public Download getDownload(String str) throws DatabaseIOException {
        a();
        try {
            Cursor a2 = a("id = ?", new String[]{str});
            if (a2.getCount() == 0) {
                if (a2 != null) {
                    a2.close();
                }
                return null;
            }
            a2.moveToNext();
            Download b2 = b(a2);
            if (a2 != null) {
                a2.close();
            }
            return b2;
        } catch (SQLiteException e) {
            throw new DatabaseIOException(e);
        }
    }

    @Override // com.google.android.exoplayer2.offline.DownloadIndex
    public DownloadCursor getDownloads(int... iArr) throws DatabaseIOException {
        a();
        return new a(a(a(iArr), (String[]) null));
    }

    @Override // com.google.android.exoplayer2.offline.WritableDownloadIndex
    public void putDownload(Download download) throws DatabaseIOException {
        a();
        try {
            a(download, this.e.getWritableDatabase());
        } catch (SQLiteException e) {
            throw new DatabaseIOException(e);
        }
    }

    @Override // com.google.android.exoplayer2.offline.WritableDownloadIndex
    public void removeDownload(String str) throws DatabaseIOException {
        a();
        try {
            this.e.getWritableDatabase().delete(this.d, "id = ?", new String[]{str});
        } catch (SQLiteException e) {
            throw new DatabaseIOException(e);
        }
    }

    @Override // com.google.android.exoplayer2.offline.WritableDownloadIndex
    public void setDownloadingStatesToQueued() throws DatabaseIOException {
        a();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(XiaomiOAuthConstants.EXTRA_STATE_2, (Integer) 0);
            this.e.getWritableDatabase().update(this.d, contentValues, "state = 2", null);
        } catch (SQLException e) {
            throw new DatabaseIOException(e);
        }
    }

    @Override // com.google.android.exoplayer2.offline.WritableDownloadIndex
    public void setStatesToRemoving() throws DatabaseIOException {
        a();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(XiaomiOAuthConstants.EXTRA_STATE_2, (Integer) 5);
            contentValues.put("failure_reason", (Integer) 0);
            this.e.getWritableDatabase().update(this.d, contentValues, null, null);
        } catch (SQLException e) {
            throw new DatabaseIOException(e);
        }
    }

    @Override // com.google.android.exoplayer2.offline.WritableDownloadIndex
    public void setStopReason(int i) throws DatabaseIOException {
        a();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DownloadService.KEY_STOP_REASON, Integer.valueOf(i));
            this.e.getWritableDatabase().update(this.d, contentValues, a, null);
        } catch (SQLException e) {
            throw new DatabaseIOException(e);
        }
    }

    @Override // com.google.android.exoplayer2.offline.WritableDownloadIndex
    public void setStopReason(String str, int i) throws DatabaseIOException {
        a();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DownloadService.KEY_STOP_REASON, Integer.valueOf(i));
            SQLiteDatabase writableDatabase = this.e.getWritableDatabase();
            String str2 = this.d;
            String str3 = a;
            StringBuilder sb = new StringBuilder(String.valueOf(str3).length() + 11);
            sb.append(str3);
            sb.append(" AND ");
            sb.append("id = ?");
            writableDatabase.update(str2, contentValues, sb.toString(), new String[]{str});
        } catch (SQLException e) {
            throw new DatabaseIOException(e);
        }
    }

    /* JADX WARN: Finally extract failed */
    private void a() throws DatabaseIOException {
        synchronized (this.f) {
            if (!this.g) {
                try {
                    int version = VersionTable.getVersion(this.e.getReadableDatabase(), 0, this.c);
                    if (version != 3) {
                        SQLiteDatabase writableDatabase = this.e.getWritableDatabase();
                        writableDatabase.beginTransactionNonExclusive();
                        try {
                            VersionTable.setVersion(writableDatabase, 0, this.c, 3);
                            List<Download> a2 = version == 2 ? a(writableDatabase) : new ArrayList<>();
                            String valueOf = String.valueOf(this.d);
                            writableDatabase.execSQL(valueOf.length() != 0 ? "DROP TABLE IF EXISTS ".concat(valueOf) : new String("DROP TABLE IF EXISTS "));
                            String str = this.d;
                            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 415);
                            sb.append("CREATE TABLE ");
                            sb.append(str);
                            sb.append(StringUtils.SPACE);
                            sb.append("(id TEXT PRIMARY KEY NOT NULL,mime_type TEXT,uri TEXT NOT NULL,stream_keys TEXT NOT NULL,custom_cache_key TEXT,data BLOB NOT NULL,state INTEGER NOT NULL,start_time_ms INTEGER NOT NULL,update_time_ms INTEGER NOT NULL,content_length INTEGER NOT NULL,stop_reason INTEGER NOT NULL,failure_reason INTEGER NOT NULL,percent_downloaded REAL NOT NULL,bytes_downloaded INTEGER NOT NULL,key_set_id BLOB NOT NULL)");
                            writableDatabase.execSQL(sb.toString());
                            for (Download download : a2) {
                                a(download, writableDatabase);
                            }
                            writableDatabase.setTransactionSuccessful();
                            writableDatabase.endTransaction();
                        } catch (Throwable th) {
                            writableDatabase.endTransaction();
                            throw th;
                        }
                    }
                    this.g = true;
                } catch (SQLException e) {
                    throw new DatabaseIOException(e);
                }
            }
        }
    }

    private void a(Download download, SQLiteDatabase sQLiteDatabase) {
        byte[] bArr = download.request.keySetId == null ? Util.EMPTY_BYTE_ARRAY : download.request.keySetId;
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", download.request.id);
        contentValues.put("mime_type", download.request.mimeType);
        contentValues.put(MiSoundBoxCommandExtras.URI, download.request.uri.toString());
        contentValues.put("stream_keys", a(download.request.streamKeys));
        contentValues.put("custom_cache_key", download.request.customCacheKey);
        contentValues.put("data", download.request.data);
        contentValues.put(XiaomiOAuthConstants.EXTRA_STATE_2, Integer.valueOf(download.state));
        contentValues.put("start_time_ms", Long.valueOf(download.startTimeMs));
        contentValues.put("update_time_ms", Long.valueOf(download.updateTimeMs));
        contentValues.put("content_length", Long.valueOf(download.contentLength));
        contentValues.put(DownloadService.KEY_STOP_REASON, Integer.valueOf(download.stopReason));
        contentValues.put("failure_reason", Integer.valueOf(download.failureReason));
        contentValues.put("percent_downloaded", Float.valueOf(download.getPercentDownloaded()));
        contentValues.put("bytes_downloaded", Long.valueOf(download.getBytesDownloaded()));
        contentValues.put("key_set_id", bArr);
        sQLiteDatabase.replaceOrThrow(this.d, null, contentValues);
    }

    private List<Download> a(SQLiteDatabase sQLiteDatabase) {
        ArrayList arrayList = new ArrayList();
        if (!Util.tableExists(sQLiteDatabase, this.d)) {
            return arrayList;
        }
        Cursor query = sQLiteDatabase.query(this.d, new String[]{"id", "title", MiSoundBoxCommandExtras.URI, "stream_keys", "custom_cache_key", "data", XiaomiOAuthConstants.EXTRA_STATE_2, "start_time_ms", "update_time_ms", "content_length", DownloadService.KEY_STOP_REASON, "failure_reason", "percent_downloaded", "bytes_downloaded"}, null, null, null, null, null);
        while (query.moveToNext()) {
            try {
                arrayList.add(c(query));
            } catch (Throwable th) {
                if (query != null) {
                    try {
                        query.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }
        if (query != null) {
            query.close();
        }
        return arrayList;
    }

    private static String a(@Nullable String str) {
        return "dash".equals(str) ? MimeTypes.APPLICATION_MPD : "hls".equals(str) ? MimeTypes.APPLICATION_M3U8 : "ss".equals(str) ? MimeTypes.APPLICATION_SS : MimeTypes.VIDEO_UNKNOWN;
    }

    private Cursor a(String str, @Nullable String[] strArr) throws DatabaseIOException {
        try {
            return this.e.getReadableDatabase().query(this.d, b, str, strArr, null, null, "start_time_ms ASC");
        } catch (SQLiteException e) {
            throw new DatabaseIOException(e);
        }
    }

    @VisibleForTesting
    static String a(List<StreamKey> list) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            StreamKey streamKey = list.get(i);
            sb.append(streamKey.periodIndex);
            sb.append('.');
            sb.append(streamKey.groupIndex);
            sb.append('.');
            sb.append(streamKey.streamIndex);
            sb.append(StringUtil.COMMA);
        }
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1);
        }
        return sb.toString();
    }

    private static String a(int... iArr) {
        if (iArr.length == 0) {
            return "1";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(XiaomiOAuthConstants.EXTRA_STATE_2);
        sb.append(" IN (");
        for (int i = 0; i < iArr.length; i++) {
            if (i > 0) {
                sb.append(StringUtil.COMMA);
            }
            sb.append(iArr[i]);
        }
        sb.append(')');
        return sb.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Download b(Cursor cursor) {
        byte[] blob = cursor.getBlob(14);
        DownloadRequest.Builder streamKeys = new DownloadRequest.Builder((String) Assertions.checkNotNull(cursor.getString(0)), Uri.parse((String) Assertions.checkNotNull(cursor.getString(2)))).setMimeType(cursor.getString(1)).setStreamKeys(b(cursor.getString(3)));
        if (blob.length <= 0) {
            blob = null;
        }
        DownloadRequest build = streamKeys.setKeySetId(blob).setCustomCacheKey(cursor.getString(4)).setData(cursor.getBlob(5)).build();
        DownloadProgress downloadProgress = new DownloadProgress();
        downloadProgress.bytesDownloaded = cursor.getLong(13);
        downloadProgress.percentDownloaded = cursor.getFloat(12);
        int i = cursor.getInt(6);
        return new Download(build, i, cursor.getLong(7), cursor.getLong(8), cursor.getLong(9), cursor.getInt(10), i == 4 ? cursor.getInt(11) : 0, downloadProgress);
    }

    private static Download c(Cursor cursor) {
        int i = 0;
        DownloadRequest build = new DownloadRequest.Builder((String) Assertions.checkNotNull(cursor.getString(0)), Uri.parse((String) Assertions.checkNotNull(cursor.getString(2)))).setMimeType(a(cursor.getString(1))).setStreamKeys(b(cursor.getString(3))).setCustomCacheKey(cursor.getString(4)).setData(cursor.getBlob(5)).build();
        DownloadProgress downloadProgress = new DownloadProgress();
        downloadProgress.bytesDownloaded = cursor.getLong(13);
        downloadProgress.percentDownloaded = cursor.getFloat(12);
        int i2 = cursor.getInt(6);
        if (i2 == 4) {
            i = cursor.getInt(11);
        }
        return new Download(build, i2, cursor.getLong(7), cursor.getLong(8), cursor.getLong(9), cursor.getInt(10), i, downloadProgress);
    }

    private static List<StreamKey> b(@Nullable String str) {
        ArrayList arrayList = new ArrayList();
        if (TextUtils.isEmpty(str)) {
            return arrayList;
        }
        for (String str2 : Util.split(str, Constants.ACCEPT_TIME_SEPARATOR_SP)) {
            String[] split = Util.split(str2, "\\.");
            Assertions.checkState(split.length == 3);
            arrayList.add(new StreamKey(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2])));
        }
        return arrayList;
    }

    /* loaded from: classes2.dex */
    private static final class a implements DownloadCursor {
        private final Cursor a;

        private a(Cursor cursor) {
            this.a = cursor;
        }

        @Override // com.google.android.exoplayer2.offline.DownloadCursor
        public Download getDownload() {
            return DefaultDownloadIndex.b(this.a);
        }

        @Override // com.google.android.exoplayer2.offline.DownloadCursor
        public int getCount() {
            return this.a.getCount();
        }

        @Override // com.google.android.exoplayer2.offline.DownloadCursor
        public int getPosition() {
            return this.a.getPosition();
        }

        @Override // com.google.android.exoplayer2.offline.DownloadCursor
        public boolean moveToPosition(int i) {
            return this.a.moveToPosition(i);
        }

        @Override // com.google.android.exoplayer2.offline.DownloadCursor, java.io.Closeable, java.lang.AutoCloseable
        public void close() {
            this.a.close();
        }

        @Override // com.google.android.exoplayer2.offline.DownloadCursor
        public boolean isClosed() {
            return this.a.isClosed();
        }
    }
}
