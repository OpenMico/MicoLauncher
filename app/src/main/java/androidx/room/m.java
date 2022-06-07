package androidx.room;

import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.room.util.CopyLock;
import androidx.room.util.DBUtil;
import androidx.room.util.FileUtil;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory;
import io.netty.handler.codec.http.multipart.DiskFileUpload;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.concurrent.Callable;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: SQLiteCopyOpenHelper.java */
/* loaded from: classes.dex */
public class m implements d, SupportSQLiteOpenHelper {
    @NonNull
    private final Context a;
    @Nullable
    private final String b;
    @Nullable
    private final File c;
    @Nullable
    private final Callable<InputStream> d;
    private final int e;
    @NonNull
    private final SupportSQLiteOpenHelper f;
    @Nullable
    private DatabaseConfiguration g;
    private boolean h;

    public m(@NonNull Context context, @Nullable String str, @Nullable File file, @Nullable Callable<InputStream> callable, int i, @NonNull SupportSQLiteOpenHelper supportSQLiteOpenHelper) {
        this.a = context;
        this.b = str;
        this.c = file;
        this.d = callable;
        this.e = i;
        this.f = supportSQLiteOpenHelper;
    }

    @Override // androidx.sqlite.db.SupportSQLiteOpenHelper
    public String getDatabaseName() {
        return this.f.getDatabaseName();
    }

    @Override // androidx.sqlite.db.SupportSQLiteOpenHelper
    @RequiresApi(api = 16)
    public void setWriteAheadLoggingEnabled(boolean z) {
        this.f.setWriteAheadLoggingEnabled(z);
    }

    @Override // androidx.sqlite.db.SupportSQLiteOpenHelper
    public synchronized SupportSQLiteDatabase getWritableDatabase() {
        if (!this.h) {
            a(true);
            this.h = true;
        }
        return this.f.getWritableDatabase();
    }

    @Override // androidx.sqlite.db.SupportSQLiteOpenHelper
    public synchronized SupportSQLiteDatabase getReadableDatabase() {
        if (!this.h) {
            a(false);
            this.h = true;
        }
        return this.f.getReadableDatabase();
    }

    @Override // androidx.sqlite.db.SupportSQLiteOpenHelper, java.io.Closeable, java.lang.AutoCloseable
    public synchronized void close() {
        this.f.close();
        this.h = false;
    }

    @Override // androidx.room.d
    @NonNull
    public SupportSQLiteOpenHelper b() {
        return this.f;
    }

    public void a(@Nullable DatabaseConfiguration databaseConfiguration) {
        this.g = databaseConfiguration;
    }

    private void a(boolean z) {
        String databaseName = getDatabaseName();
        File databasePath = this.a.getDatabasePath(databaseName);
        DatabaseConfiguration databaseConfiguration = this.g;
        CopyLock copyLock = new CopyLock(databaseName, this.a.getFilesDir(), databaseConfiguration == null || databaseConfiguration.multiInstanceInvalidation);
        try {
            copyLock.lock();
            if (!databasePath.exists()) {
                try {
                    a(databasePath, z);
                    copyLock.unlock();
                    return;
                } catch (IOException e) {
                    throw new RuntimeException("Unable to copy database file.", e);
                }
            } else if (this.g == null) {
                copyLock.unlock();
                return;
            } else {
                try {
                    int readVersion = DBUtil.readVersion(databasePath);
                    if (readVersion == this.e) {
                        copyLock.unlock();
                        return;
                    } else if (this.g.isMigrationRequired(readVersion, this.e)) {
                        copyLock.unlock();
                        return;
                    } else {
                        if (this.a.deleteDatabase(databaseName)) {
                            try {
                                a(databasePath, z);
                            } catch (IOException e2) {
                                Log.w("ROOM", "Unable to copy database file.", e2);
                            }
                        } else {
                            Log.w("ROOM", "Failed to delete database file (" + databaseName + ") for a copy destructive migration.");
                        }
                        copyLock.unlock();
                        return;
                    }
                } catch (IOException e3) {
                    Log.w("ROOM", "Unable to read database version.", e3);
                    copyLock.unlock();
                    return;
                }
            }
        } catch (Throwable th) {
            copyLock.unlock();
            throw th;
        }
        copyLock.unlock();
        throw th;
    }

    private void a(File file, boolean z) throws IOException {
        ReadableByteChannel readableByteChannel;
        if (this.b != null) {
            readableByteChannel = Channels.newChannel(this.a.getAssets().open(this.b));
        } else {
            File file2 = this.c;
            if (file2 != null) {
                readableByteChannel = new FileInputStream(file2).getChannel();
            } else {
                Callable<InputStream> callable = this.d;
                if (callable != null) {
                    try {
                        readableByteChannel = Channels.newChannel(callable.call());
                    } catch (Exception e) {
                        throw new IOException("inputStreamCallable exception on call", e);
                    }
                } else {
                    throw new IllegalStateException("copyFromAssetPath, copyFromFile and copyFromInputStream are all null!");
                }
            }
        }
        File createTempFile = File.createTempFile("room-copy-helper", DiskFileUpload.postfix, this.a.getCacheDir());
        createTempFile.deleteOnExit();
        FileUtil.copy(readableByteChannel, new FileOutputStream(createTempFile).getChannel());
        File parentFile = file.getParentFile();
        if (parentFile == null || parentFile.exists() || parentFile.mkdirs()) {
            b(createTempFile, z);
            if (!createTempFile.renameTo(file)) {
                throw new IOException("Failed to move intermediate file (" + createTempFile.getAbsolutePath() + ") to destination (" + file.getAbsolutePath() + ").");
            }
            return;
        }
        throw new IOException("Failed to create directories for " + file.getAbsolutePath());
    }

    private void b(File file, boolean z) {
        SupportSQLiteDatabase supportSQLiteDatabase;
        DatabaseConfiguration databaseConfiguration = this.g;
        if (databaseConfiguration != null && databaseConfiguration.prepackagedDatabaseCallback != null) {
            SupportSQLiteOpenHelper a = a(file);
            try {
                if (z) {
                    supportSQLiteDatabase = a.getWritableDatabase();
                } else {
                    supportSQLiteDatabase = a.getReadableDatabase();
                }
                this.g.prepackagedDatabaseCallback.onOpenPrepackagedDatabase(supportSQLiteDatabase);
            } finally {
                a.close();
            }
        }
    }

    private SupportSQLiteOpenHelper a(File file) {
        try {
            return new FrameworkSQLiteOpenHelperFactory().create(SupportSQLiteOpenHelper.Configuration.builder(this.a).name(file.getName()).callback(new SupportSQLiteOpenHelper.Callback(DBUtil.readVersion(file)) { // from class: androidx.room.m.1
                @Override // androidx.sqlite.db.SupportSQLiteOpenHelper.Callback
                public void onCreate(@NonNull SupportSQLiteDatabase supportSQLiteDatabase) {
                }

                @Override // androidx.sqlite.db.SupportSQLiteOpenHelper.Callback
                public void onUpgrade(@NonNull SupportSQLiteDatabase supportSQLiteDatabase, int i, int i2) {
                }
            }).build());
        } catch (IOException e) {
            throw new RuntimeException("Malformed database file, unable to read version.", e);
        }
    }
}
