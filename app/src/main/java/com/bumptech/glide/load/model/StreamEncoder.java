package com.bumptech.glide.load.model;

import android.util.Log;
import androidx.annotation.NonNull;
import com.bumptech.glide.load.Encoder;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.engine.bitmap_recycle.ArrayPool;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* loaded from: classes.dex */
public class StreamEncoder implements Encoder<InputStream> {
    private final ArrayPool a;

    public StreamEncoder(ArrayPool arrayPool) {
        this.a = arrayPool;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r6v2 */
    /* JADX WARN: Type inference failed for: r6v3 */
    /* JADX WARN: Type inference failed for: r6v6, types: [byte[]] */
    public boolean encode(@NonNull InputStream inputStream, @NonNull File file, @NonNull Options options) {
        Throwable th;
        boolean z;
        IOException e;
        try {
            try {
                options = (byte[]) this.a.get(65536, byte[].class);
                z = false;
                OutputStream outputStream = null;
                try {
                    OutputStream fileOutputStream = new FileOutputStream(file);
                    while (true) {
                        try {
                            int read = inputStream.read(options);
                            if (read == -1) {
                                break;
                            }
                            fileOutputStream.write(options, 0, read);
                        } catch (IOException e2) {
                            e = e2;
                            outputStream = fileOutputStream;
                            if (Log.isLoggable("StreamEncoder", 3)) {
                                Log.d("StreamEncoder", "Failed to encode data onto the OutputStream", e);
                            }
                            if (outputStream != null) {
                                outputStream.close();
                            }
                            this.a.put(options == true ? 1 : 0);
                            return z;
                        } catch (Throwable th2) {
                            th = th2;
                            if (fileOutputStream != 0) {
                                try {
                                    fileOutputStream.close();
                                } catch (IOException unused) {
                                }
                            }
                            this.a.put(options == true ? 1 : 0);
                            throw th;
                        }
                    }
                    fileOutputStream.close();
                    z = true;
                    fileOutputStream.close();
                } catch (IOException e3) {
                    e = e3;
                }
            } catch (IOException unused2) {
            }
            this.a.put(options == true ? 1 : 0);
            return z;
        } catch (Throwable th3) {
            th = th3;
        }
    }
}
