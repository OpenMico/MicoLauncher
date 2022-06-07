package androidx.core.util;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/* loaded from: classes.dex */
public class AtomicFile {
    private final File a;
    private final File b;
    private final File c;

    public AtomicFile(@NonNull File file) {
        this.a = file;
        this.b = new File(file.getPath() + ".new");
        this.c = new File(file.getPath() + ".bak");
    }

    @NonNull
    public File getBaseFile() {
        return this.a;
    }

    public void delete() {
        this.a.delete();
        this.b.delete();
        this.c.delete();
    }

    @NonNull
    public FileOutputStream startWrite() throws IOException {
        if (this.c.exists()) {
            a(this.c, this.a);
        }
        try {
            return new FileOutputStream(this.b);
        } catch (FileNotFoundException unused) {
            if (this.b.getParentFile().mkdirs()) {
                try {
                    return new FileOutputStream(this.b);
                } catch (FileNotFoundException e) {
                    throw new IOException("Failed to create new file " + this.b, e);
                }
            } else {
                throw new IOException("Failed to create directory for " + this.b);
            }
        }
    }

    public void finishWrite(@Nullable FileOutputStream fileOutputStream) {
        if (fileOutputStream != null) {
            if (!a(fileOutputStream)) {
                Log.e("AtomicFile", "Failed to sync file output stream");
            }
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                Log.e("AtomicFile", "Failed to close file output stream", e);
            }
            a(this.b, this.a);
        }
    }

    public void failWrite(@Nullable FileOutputStream fileOutputStream) {
        if (fileOutputStream != null) {
            if (!a(fileOutputStream)) {
                Log.e("AtomicFile", "Failed to sync file output stream");
            }
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                Log.e("AtomicFile", "Failed to close file output stream", e);
            }
            if (!this.b.delete()) {
                Log.e("AtomicFile", "Failed to delete new file " + this.b);
            }
        }
    }

    @NonNull
    public FileInputStream openRead() throws FileNotFoundException {
        if (this.c.exists()) {
            a(this.c, this.a);
        }
        if (this.b.exists() && this.a.exists() && !this.b.delete()) {
            Log.e("AtomicFile", "Failed to delete outdated new file " + this.b);
        }
        return new FileInputStream(this.a);
    }

    @NonNull
    public byte[] readFully() throws IOException {
        FileInputStream openRead = openRead();
        try {
            byte[] bArr = new byte[openRead.available()];
            int i = 0;
            while (true) {
                int read = openRead.read(bArr, i, bArr.length - i);
                if (read <= 0) {
                    return bArr;
                }
                i += read;
                int available = openRead.available();
                if (available > bArr.length - i) {
                    byte[] bArr2 = new byte[available + i];
                    System.arraycopy(bArr, 0, bArr2, 0, i);
                    bArr = bArr2;
                }
            }
        } finally {
            openRead.close();
        }
    }

    private static boolean a(@NonNull FileOutputStream fileOutputStream) {
        try {
            fileOutputStream.getFD().sync();
            return true;
        } catch (IOException unused) {
            return false;
        }
    }

    private static void a(@NonNull File file, @NonNull File file2) {
        if (file2.isDirectory() && !file2.delete()) {
            Log.e("AtomicFile", "Failed to delete file which is a directory " + file2);
        }
        if (!file.renameTo(file2)) {
            Log.e("AtomicFile", "Failed to rename " + file + " to " + file2);
        }
    }
}
