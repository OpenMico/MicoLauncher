package androidx.documentfile.provider;

import android.content.Context;
import android.net.Uri;
import android.provider.DocumentsContract;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

/* compiled from: SingleDocumentFile.java */
@RequiresApi(19)
/* loaded from: classes.dex */
class c extends DocumentFile {
    private Context a;
    private Uri b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public c(@Nullable DocumentFile documentFile, Context context, Uri uri) {
        super(documentFile);
        this.a = context;
        this.b = uri;
    }

    @Override // androidx.documentfile.provider.DocumentFile
    public DocumentFile createFile(String str, String str2) {
        throw new UnsupportedOperationException();
    }

    @Override // androidx.documentfile.provider.DocumentFile
    public DocumentFile createDirectory(String str) {
        throw new UnsupportedOperationException();
    }

    @Override // androidx.documentfile.provider.DocumentFile
    public Uri getUri() {
        return this.b;
    }

    @Override // androidx.documentfile.provider.DocumentFile
    @Nullable
    public String getName() {
        return a.b(this.a, this.b);
    }

    @Override // androidx.documentfile.provider.DocumentFile
    @Nullable
    public String getType() {
        return a.c(this.a, this.b);
    }

    @Override // androidx.documentfile.provider.DocumentFile
    public boolean isDirectory() {
        return a.e(this.a, this.b);
    }

    @Override // androidx.documentfile.provider.DocumentFile
    public boolean isFile() {
        return a.f(this.a, this.b);
    }

    @Override // androidx.documentfile.provider.DocumentFile
    public boolean isVirtual() {
        return a.a(this.a, this.b);
    }

    @Override // androidx.documentfile.provider.DocumentFile
    public long lastModified() {
        return a.g(this.a, this.b);
    }

    @Override // androidx.documentfile.provider.DocumentFile
    public long length() {
        return a.h(this.a, this.b);
    }

    @Override // androidx.documentfile.provider.DocumentFile
    public boolean canRead() {
        return a.i(this.a, this.b);
    }

    @Override // androidx.documentfile.provider.DocumentFile
    public boolean canWrite() {
        return a.j(this.a, this.b);
    }

    @Override // androidx.documentfile.provider.DocumentFile
    public boolean delete() {
        try {
            return DocumentsContract.deleteDocument(this.a.getContentResolver(), this.b);
        } catch (Exception unused) {
            return false;
        }
    }

    @Override // androidx.documentfile.provider.DocumentFile
    public boolean exists() {
        return a.k(this.a, this.b);
    }

    @Override // androidx.documentfile.provider.DocumentFile
    public DocumentFile[] listFiles() {
        throw new UnsupportedOperationException();
    }

    @Override // androidx.documentfile.provider.DocumentFile
    public boolean renameTo(String str) {
        throw new UnsupportedOperationException();
    }
}
