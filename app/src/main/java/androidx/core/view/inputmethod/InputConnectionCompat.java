package androidx.core.view.inputmethod;

import android.content.ClipDescription;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.text.TextUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;
import android.view.inputmethod.InputContentInfo;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/* loaded from: classes.dex */
public final class InputConnectionCompat {
    public static final int INPUT_CONTENT_GRANT_READ_URI_PERMISSION = 1;

    /* loaded from: classes.dex */
    public interface OnCommitContentListener {
        boolean onCommitContent(InputContentInfoCompat inputContentInfoCompat, int i, Bundle bundle);
    }

    static boolean a(@Nullable String str, @NonNull Bundle bundle, @NonNull OnCommitContentListener onCommitContentListener) {
        boolean z;
        Throwable th;
        ResultReceiver resultReceiver;
        boolean z2;
        int i = 0;
        if (bundle == null) {
            return false;
        }
        if (TextUtils.equals("androidx.core.view.inputmethod.InputConnectionCompat.COMMIT_CONTENT", str)) {
            z = false;
        } else if (!TextUtils.equals("android.support.v13.view.inputmethod.InputConnectionCompat.COMMIT_CONTENT", str)) {
            return false;
        } else {
            z = true;
        }
        try {
            resultReceiver = (ResultReceiver) bundle.getParcelable(z ? "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_RESULT_RECEIVER" : "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_RESULT_RECEIVER");
            try {
                Uri uri = (Uri) bundle.getParcelable(z ? "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_URI" : "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_URI");
                ClipDescription clipDescription = (ClipDescription) bundle.getParcelable(z ? "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_DESCRIPTION" : "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_DESCRIPTION");
                Uri uri2 = (Uri) bundle.getParcelable(z ? "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_LINK_URI" : "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_LINK_URI");
                int i2 = bundle.getInt(z ? "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_FLAGS" : "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_FLAGS");
                Bundle bundle2 = (Bundle) bundle.getParcelable(z ? "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_OPTS" : "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_OPTS");
                if (uri == null || clipDescription == null) {
                    z2 = false;
                } else {
                    z2 = onCommitContentListener.onCommitContent(new InputContentInfoCompat(uri, clipDescription, uri2), i2, bundle2);
                }
                if (resultReceiver != null) {
                    if (z2) {
                        i = 1;
                    }
                    resultReceiver.send(i, null);
                }
                return z2;
            } catch (Throwable th2) {
                th = th2;
                if (resultReceiver != null) {
                    resultReceiver.send(0, null);
                }
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            resultReceiver = null;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static boolean commitContent(@NonNull InputConnection inputConnection, @NonNull EditorInfo editorInfo, @NonNull InputContentInfoCompat inputContentInfoCompat, int i, @Nullable Bundle bundle) {
        boolean z;
        ClipDescription description = inputContentInfoCompat.getDescription();
        String[] contentMimeTypes = EditorInfoCompat.getContentMimeTypes(editorInfo);
        int length = contentMimeTypes.length;
        boolean z2 = false;
        int i2 = 0;
        while (true) {
            if (i2 >= length) {
                z = false;
                break;
            } else if (description.hasMimeType(contentMimeTypes[i2])) {
                z = true;
                break;
            } else {
                i2++;
            }
        }
        if (!z) {
            return false;
        }
        if (Build.VERSION.SDK_INT >= 25) {
            return inputConnection.commitContent((InputContentInfo) inputContentInfoCompat.unwrap(), i, bundle);
        }
        switch (EditorInfoCompat.a(editorInfo)) {
            case 2:
                z2 = true;
                break;
            case 3:
            case 4:
                break;
            default:
                return false;
        }
        Bundle bundle2 = new Bundle();
        bundle2.putParcelable(z2 ? "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_URI" : "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_URI", inputContentInfoCompat.getContentUri());
        bundle2.putParcelable(z2 ? "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_DESCRIPTION" : "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_DESCRIPTION", inputContentInfoCompat.getDescription());
        bundle2.putParcelable(z2 ? "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_LINK_URI" : "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_LINK_URI", inputContentInfoCompat.getLinkUri());
        bundle2.putInt(z2 ? "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_FLAGS" : "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_FLAGS", i);
        bundle2.putParcelable(z2 ? "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_OPTS" : "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_OPTS", bundle);
        return inputConnection.performPrivateCommand(z2 ? "android.support.v13.view.inputmethod.InputConnectionCompat.COMMIT_CONTENT" : "androidx.core.view.inputmethod.InputConnectionCompat.COMMIT_CONTENT", bundle2);
    }

    @NonNull
    public static InputConnection createWrapper(@NonNull InputConnection inputConnection, @NonNull EditorInfo editorInfo, @NonNull final OnCommitContentListener onCommitContentListener) {
        if (inputConnection == null) {
            throw new IllegalArgumentException("inputConnection must be non-null");
        } else if (editorInfo == null) {
            throw new IllegalArgumentException("editorInfo must be non-null");
        } else if (onCommitContentListener == null) {
            throw new IllegalArgumentException("onCommitContentListener must be non-null");
        } else if (Build.VERSION.SDK_INT >= 25) {
            return new InputConnectionWrapper(inputConnection, false) { // from class: androidx.core.view.inputmethod.InputConnectionCompat.1
                @Override // android.view.inputmethod.InputConnectionWrapper, android.view.inputmethod.InputConnection
                public boolean commitContent(InputContentInfo inputContentInfo, int i, Bundle bundle) {
                    if (onCommitContentListener.onCommitContent(InputContentInfoCompat.wrap(inputContentInfo), i, bundle)) {
                        return true;
                    }
                    return super.commitContent(inputContentInfo, i, bundle);
                }
            };
        } else {
            return EditorInfoCompat.getContentMimeTypes(editorInfo).length == 0 ? inputConnection : new InputConnectionWrapper(inputConnection, false) { // from class: androidx.core.view.inputmethod.InputConnectionCompat.2
                @Override // android.view.inputmethod.InputConnectionWrapper, android.view.inputmethod.InputConnection
                public boolean performPrivateCommand(String str, Bundle bundle) {
                    if (InputConnectionCompat.a(str, bundle, onCommitContentListener)) {
                        return true;
                    }
                    return super.performPrivateCommand(str, bundle);
                }
            };
        }
    }
}
