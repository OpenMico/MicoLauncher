package androidx.exifinterface.media;

import android.media.MediaDataSource;
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.system.ErrnoException;
import android.system.Os;
import android.util.Log;
import androidx.annotation.DoNotInline;
import androidx.annotation.RequiresApi;
import java.io.Closeable;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* compiled from: ExifInterfaceUtils.java */
/* loaded from: classes.dex */
class a {
    /* JADX INFO: Access modifiers changed from: package-private */
    public static int a(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] bArr = new byte[8192];
        int i = 0;
        while (true) {
            int read = inputStream.read(bArr);
            if (read == -1) {
                return i;
            }
            i += read;
            outputStream.write(bArr, 0, read);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void a(InputStream inputStream, OutputStream outputStream, int i) throws IOException {
        byte[] bArr = new byte[8192];
        while (i > 0) {
            int min = Math.min(i, 8192);
            int read = inputStream.read(bArr, 0, min);
            if (read == min) {
                i -= read;
                outputStream.write(bArr, 0, read);
            } else {
                throw new IOException("Failed to copy the given amount of bytes from the inputstream to the output stream.");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static long[] a(Object obj) {
        if (obj instanceof int[]) {
            int[] iArr = (int[]) obj;
            long[] jArr = new long[iArr.length];
            for (int i = 0; i < iArr.length; i++) {
                jArr[i] = iArr[i];
            }
            return jArr;
        } else if (obj instanceof long[]) {
            return (long[]) obj;
        } else {
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean a(byte[] bArr, byte[] bArr2) {
        if (bArr == null || bArr2 == null || bArr.length < bArr2.length) {
            return false;
        }
        for (int i = 0; i < bArr2.length; i++) {
            if (bArr[i] != bArr2[i]) {
                return false;
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static String a(byte[] bArr) {
        StringBuilder sb = new StringBuilder(bArr.length * 2);
        for (int i = 0; i < bArr.length; i++) {
            sb.append(String.format("%02x", Byte.valueOf(bArr[i])));
        }
        return sb.toString();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static long a(String str) {
        try {
            int min = Math.min(str.length(), 3);
            long parseLong = Long.parseLong(str.substring(0, min));
            while (min < 3) {
                parseLong *= 10;
                min++;
            }
            return parseLong;
        } catch (NumberFormatException unused) {
            return 0L;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void a(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (RuntimeException e) {
                throw e;
            } catch (Exception unused) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void a(FileDescriptor fileDescriptor) {
        if (Build.VERSION.SDK_INT >= 21) {
            try {
                C0016a.b(fileDescriptor);
            } catch (Exception unused) {
                Log.e("ExifInterfaceUtils", "Error closing fd.");
            }
        } else {
            Log.e("ExifInterfaceUtils", "closeFileDescriptor is called in API < 21, which must be wrong.");
        }
    }

    /* compiled from: ExifInterfaceUtils.java */
    @RequiresApi(21)
    /* renamed from: androidx.exifinterface.media.a$a  reason: collision with other inner class name */
    /* loaded from: classes.dex */
    static class C0016a {
        /* JADX INFO: Access modifiers changed from: package-private */
        @DoNotInline
        public static FileDescriptor a(FileDescriptor fileDescriptor) throws ErrnoException {
            return Os.dup(fileDescriptor);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @DoNotInline
        public static long a(FileDescriptor fileDescriptor, long j, int i) throws ErrnoException {
            return Os.lseek(fileDescriptor, j, i);
        }

        @DoNotInline
        static void b(FileDescriptor fileDescriptor) throws ErrnoException {
            Os.close(fileDescriptor);
        }
    }

    /* compiled from: ExifInterfaceUtils.java */
    @RequiresApi(23)
    /* loaded from: classes.dex */
    static class b {
        /* JADX INFO: Access modifiers changed from: package-private */
        @DoNotInline
        public static void a(MediaMetadataRetriever mediaMetadataRetriever, MediaDataSource mediaDataSource) {
            mediaMetadataRetriever.setDataSource(mediaDataSource);
        }
    }
}
