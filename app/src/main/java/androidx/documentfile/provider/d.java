package androidx.documentfile.provider;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import java.util.ArrayList;

/* compiled from: TreeDocumentFile.java */
@RequiresApi(21)
/* loaded from: classes.dex */
class d extends DocumentFile {
    private Context a;
    private Uri b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public d(@Nullable DocumentFile documentFile, Context context, Uri uri) {
        super(documentFile);
        this.a = context;
        this.b = uri;
    }

    @Override // androidx.documentfile.provider.DocumentFile
    @Nullable
    public DocumentFile createFile(String str, String str2) {
        Uri a = a(this.a, this.b, str, str2);
        if (a != null) {
            return new d(this, this.a, a);
        }
        return null;
    }

    @Nullable
    private static Uri a(Context context, Uri uri, String str, String str2) {
        try {
            return DocumentsContract.createDocument(context.getContentResolver(), uri, str, str2);
        } catch (Exception unused) {
            return null;
        }
    }

    @Override // androidx.documentfile.provider.DocumentFile
    @Nullable
    public DocumentFile createDirectory(String str) {
        Uri a = a(this.a, this.b, "vnd.android.document/directory", str);
        if (a != null) {
            return new d(this, this.a, a);
        }
        return null;
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
        ContentResolver contentResolver = this.a.getContentResolver();
        Uri uri = this.b;
        Uri buildChildDocumentsUriUsingTree = DocumentsContract.buildChildDocumentsUriUsingTree(uri, DocumentsContract.getDocumentId(uri));
        ArrayList arrayList = new ArrayList();
        Cursor cursor = null;
        try {
            try {
                cursor = contentResolver.query(buildChildDocumentsUriUsingTree, new String[]{"document_id"}, null, null, null);
                while (cursor.moveToNext()) {
                    arrayList.add(DocumentsContract.buildDocumentUriUsingTree(this.b, cursor.getString(0)));
                }
            } catch (Exception e) {
                Log.w("DocumentFile", "Failed query: " + e);
            }
            Uri[] uriArr = (Uri[]) arrayList.toArray(new Uri[arrayList.size()]);
            DocumentFile[] documentFileArr = new DocumentFile[uriArr.length];
            for (int i = 0; i < uriArr.length; i++) {
                documentFileArr[i] = new d(this, this.a, uriArr[i]);
            }
            return documentFileArr;
        } finally {
            a(cursor);
        }
    }

    private static void a(@Nullable AutoCloseable autoCloseable) {
        if (autoCloseable != null) {
            try {
                autoCloseable.close();
            } catch (RuntimeException e) {
                throw e;
            } catch (Exception unused) {
            }
        }
    }

    @Override // androidx.documentfile.provider.DocumentFile
    public boolean renameTo(String str) {
        try {
            Uri renameDocument = DocumentsContract.renameDocument(this.a.getContentResolver(), this.b, str);
            if (renameDocument == null) {
                return false;
            }
            this.b = renameDocument;
            return true;
        } catch (Exception unused) {
            return false;
        }
    }
}
