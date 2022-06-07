package com.google.android.exoplayer2.offline;

import android.net.Uri;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.offline.DownloadRequest;
import com.google.android.exoplayer2.util.AtomicFile;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/* compiled from: ActionFile.java */
@Deprecated
/* loaded from: classes2.dex */
final class a {
    private final AtomicFile a;

    public a(File file) {
        this.a = new AtomicFile(file);
    }

    public boolean a() {
        return this.a.exists();
    }

    public void b() {
        this.a.delete();
    }

    public DownloadRequest[] c() throws IOException {
        if (!a()) {
            return new DownloadRequest[0];
        }
        InputStream inputStream = null;
        try {
            inputStream = this.a.openRead();
            DataInputStream dataInputStream = new DataInputStream(inputStream);
            int readInt = dataInputStream.readInt();
            if (readInt <= 0) {
                int readInt2 = dataInputStream.readInt();
                ArrayList arrayList = new ArrayList();
                for (int i = 0; i < readInt2; i++) {
                    try {
                        arrayList.add(a(dataInputStream));
                    } catch (DownloadRequest.UnsupportedRequestException unused) {
                    }
                }
                return (DownloadRequest[]) arrayList.toArray(new DownloadRequest[0]);
            }
            StringBuilder sb = new StringBuilder(44);
            sb.append("Unsupported action file version: ");
            sb.append(readInt);
            throw new IOException(sb.toString());
        } finally {
            Util.closeQuietly(inputStream);
        }
    }

    private static DownloadRequest a(DataInputStream dataInputStream) throws IOException {
        byte[] bArr;
        String readUTF = dataInputStream.readUTF();
        int readInt = dataInputStream.readInt();
        Uri parse = Uri.parse(dataInputStream.readUTF());
        boolean readBoolean = dataInputStream.readBoolean();
        int readInt2 = dataInputStream.readInt();
        String str = null;
        if (readInt2 != 0) {
            bArr = new byte[readInt2];
            dataInputStream.readFully(bArr);
        } else {
            bArr = null;
        }
        boolean z = true;
        boolean z2 = readInt == 0 && "progressive".equals(readUTF);
        ArrayList arrayList = new ArrayList();
        if (!z2) {
            int readInt3 = dataInputStream.readInt();
            for (int i = 0; i < readInt3; i++) {
                arrayList.add(a(readUTF, readInt, dataInputStream));
            }
        }
        if (readInt >= 2 || (!"dash".equals(readUTF) && !"hls".equals(readUTF) && !"ss".equals(readUTF))) {
            z = false;
        }
        if (!z && dataInputStream.readBoolean()) {
            str = dataInputStream.readUTF();
        }
        String a = readInt < 3 ? a(parse, str) : dataInputStream.readUTF();
        if (!readBoolean) {
            return new DownloadRequest.Builder(a, parse).setMimeType(a(readUTF)).setStreamKeys(arrayList).setCustomCacheKey(str).setData(bArr).build();
        }
        throw new DownloadRequest.UnsupportedRequestException();
    }

    private static StreamKey a(String str, int i, DataInputStream dataInputStream) throws IOException {
        int i2;
        int i3;
        int i4;
        if (("hls".equals(str) || "ss".equals(str)) && i == 0) {
            i4 = 0;
            i3 = dataInputStream.readInt();
            i2 = dataInputStream.readInt();
        } else {
            i4 = dataInputStream.readInt();
            i3 = dataInputStream.readInt();
            i2 = dataInputStream.readInt();
        }
        return new StreamKey(i4, i3, i2);
    }

    private static String a(String str) {
        char c;
        int hashCode = str.hashCode();
        if (hashCode == 3680) {
            if (str.equals("ss")) {
                c = 2;
            }
            c = 65535;
        } else if (hashCode == 103407) {
            if (str.equals("hls")) {
                c = 1;
            }
            c = 65535;
        } else if (hashCode != 3075986) {
            if (hashCode == 1131547531 && str.equals("progressive")) {
                c = 3;
            }
            c = 65535;
        } else {
            if (str.equals("dash")) {
                c = 0;
            }
            c = 65535;
        }
        switch (c) {
            case 0:
                return MimeTypes.APPLICATION_MPD;
            case 1:
                return MimeTypes.APPLICATION_M3U8;
            case 2:
                return MimeTypes.APPLICATION_SS;
            default:
                return MimeTypes.VIDEO_UNKNOWN;
        }
    }

    private static String a(Uri uri, @Nullable String str) {
        return str != null ? str : uri.toString();
    }
}
