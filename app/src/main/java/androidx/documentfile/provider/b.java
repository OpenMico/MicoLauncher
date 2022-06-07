package androidx.documentfile.provider;

import android.net.Uri;
import android.util.Log;
import android.webkit.MimeTypeMap;
import androidx.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/* compiled from: RawDocumentFile.java */
/* loaded from: classes.dex */
class b extends DocumentFile {
    private File a;

    @Override // androidx.documentfile.provider.DocumentFile
    public boolean isVirtual() {
        return false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public b(@Nullable DocumentFile documentFile, File file) {
        super(documentFile);
        this.a = file;
    }

    @Override // androidx.documentfile.provider.DocumentFile
    @Nullable
    public DocumentFile createFile(String str, String str2) {
        String extensionFromMimeType = MimeTypeMap.getSingleton().getExtensionFromMimeType(str);
        if (extensionFromMimeType != null) {
            str2 = str2 + "." + extensionFromMimeType;
        }
        File file = new File(this.a, str2);
        try {
            file.createNewFile();
            return new b(this, file);
        } catch (IOException e) {
            Log.w("DocumentFile", "Failed to createFile: " + e);
            return null;
        }
    }

    @Override // androidx.documentfile.provider.DocumentFile
    @Nullable
    public DocumentFile createDirectory(String str) {
        File file = new File(this.a, str);
        if (file.isDirectory() || file.mkdir()) {
            return new b(this, file);
        }
        return null;
    }

    @Override // androidx.documentfile.provider.DocumentFile
    public Uri getUri() {
        return Uri.fromFile(this.a);
    }

    @Override // androidx.documentfile.provider.DocumentFile
    public String getName() {
        return this.a.getName();
    }

    @Override // androidx.documentfile.provider.DocumentFile
    @Nullable
    public String getType() {
        if (this.a.isDirectory()) {
            return null;
        }
        return a(this.a.getName());
    }

    @Override // androidx.documentfile.provider.DocumentFile
    public boolean isDirectory() {
        return this.a.isDirectory();
    }

    @Override // androidx.documentfile.provider.DocumentFile
    public boolean isFile() {
        return this.a.isFile();
    }

    @Override // androidx.documentfile.provider.DocumentFile
    public long lastModified() {
        return this.a.lastModified();
    }

    @Override // androidx.documentfile.provider.DocumentFile
    public long length() {
        return this.a.length();
    }

    @Override // androidx.documentfile.provider.DocumentFile
    public boolean canRead() {
        return this.a.canRead();
    }

    @Override // androidx.documentfile.provider.DocumentFile
    public boolean canWrite() {
        return this.a.canWrite();
    }

    @Override // androidx.documentfile.provider.DocumentFile
    public boolean delete() {
        a(this.a);
        return this.a.delete();
    }

    @Override // androidx.documentfile.provider.DocumentFile
    public boolean exists() {
        return this.a.exists();
    }

    @Override // androidx.documentfile.provider.DocumentFile
    public DocumentFile[] listFiles() {
        ArrayList arrayList = new ArrayList();
        File[] listFiles = this.a.listFiles();
        if (listFiles != null) {
            for (File file : listFiles) {
                arrayList.add(new b(this, file));
            }
        }
        return (DocumentFile[]) arrayList.toArray(new DocumentFile[arrayList.size()]);
    }

    @Override // androidx.documentfile.provider.DocumentFile
    public boolean renameTo(String str) {
        File file = new File(this.a.getParentFile(), str);
        if (!this.a.renameTo(file)) {
            return false;
        }
        this.a = file;
        return true;
    }

    private static String a(String str) {
        String mimeTypeFromExtension;
        int lastIndexOf = str.lastIndexOf(46);
        return (lastIndexOf < 0 || (mimeTypeFromExtension = MimeTypeMap.getSingleton().getMimeTypeFromExtension(str.substring(lastIndexOf + 1).toLowerCase())) == null) ? "application/octet-stream" : mimeTypeFromExtension;
    }

    private static boolean a(File file) {
        File[] listFiles = file.listFiles();
        if (listFiles == null) {
            return true;
        }
        boolean z = true;
        for (File file2 : listFiles) {
            if (file2.isDirectory()) {
                z &= a(file2);
            }
            if (!file2.delete()) {
                Log.w("DocumentFile", "Failed to delete " + file2);
                z = false;
            }
        }
        return z;
    }
}
