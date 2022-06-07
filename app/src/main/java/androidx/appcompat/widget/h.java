package androidx.appcompat.widget;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;
import android.os.Bundle;
import android.text.Selection;
import android.text.Spannable;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.inputmethod.InputContentInfo;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.view.ContentInfoCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.inputmethod.InputConnectionCompat;
import androidx.core.view.inputmethod.InputContentInfoCompat;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: AppCompatReceiveContentHelper.java */
/* loaded from: classes.dex */
public final class h {
    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean a(@NonNull TextView textView, int i) {
        int i2 = 0;
        if ((i != 16908322 && i != 16908337) || ViewCompat.getOnReceiveContentMimeTypes(textView) == null) {
            return false;
        }
        ClipboardManager clipboardManager = (ClipboardManager) textView.getContext().getSystemService("clipboard");
        ClipData primaryClip = clipboardManager == null ? null : clipboardManager.getPrimaryClip();
        if (primaryClip != null && primaryClip.getItemCount() > 0) {
            ContentInfoCompat.Builder builder = new ContentInfoCompat.Builder(primaryClip, 1);
            if (i != 16908322) {
                i2 = 1;
            }
            ViewCompat.performReceiveContent(textView, builder.setFlags(i2).build());
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean a(@NonNull View view, @NonNull DragEvent dragEvent) {
        if (Build.VERSION.SDK_INT < 24 || dragEvent.getLocalState() != null || ViewCompat.getOnReceiveContentMimeTypes(view) == null) {
            return false;
        }
        Activity a2 = a(view);
        if (a2 == null) {
            Log.i("ReceiveContent", "Can't handle drop: no activity: view=" + view);
            return false;
        } else if (dragEvent.getAction() == 1) {
            return !(view instanceof TextView);
        } else {
            if (dragEvent.getAction() != 3) {
                return false;
            }
            if (view instanceof TextView) {
                return a.a(dragEvent, (TextView) view, a2);
            }
            return a.a(dragEvent, view, a2);
        }
    }

    /* compiled from: AppCompatReceiveContentHelper.java */
    @RequiresApi(24)
    /* loaded from: classes.dex */
    private static final class a {
        /* JADX WARN: Finally extract failed */
        static boolean a(@NonNull DragEvent dragEvent, @NonNull TextView textView, @NonNull Activity activity) {
            activity.requestDragAndDropPermissions(dragEvent);
            int offsetForPosition = textView.getOffsetForPosition(dragEvent.getX(), dragEvent.getY());
            textView.beginBatchEdit();
            try {
                Selection.setSelection((Spannable) textView.getText(), offsetForPosition);
                ViewCompat.performReceiveContent(textView, new ContentInfoCompat.Builder(dragEvent.getClipData(), 3).build());
                textView.endBatchEdit();
                return true;
            } catch (Throwable th) {
                textView.endBatchEdit();
                throw th;
            }
        }

        static boolean a(@NonNull DragEvent dragEvent, @NonNull View view, @NonNull Activity activity) {
            activity.requestDragAndDropPermissions(dragEvent);
            ViewCompat.performReceiveContent(view, new ContentInfoCompat.Builder(dragEvent.getClipData(), 3).build());
            return true;
        }
    }

    @Nullable
    static Activity a(@NonNull View view) {
        for (Context context = view.getContext(); context instanceof ContextWrapper; context = ((ContextWrapper) context).getBaseContext()) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @NonNull
    public static InputConnectionCompat.OnCommitContentListener b(@NonNull final View view) {
        return new InputConnectionCompat.OnCommitContentListener() { // from class: androidx.appcompat.widget.h.1
            @Override // androidx.core.view.inputmethod.InputConnectionCompat.OnCommitContentListener
            public boolean onCommitContent(InputContentInfoCompat inputContentInfoCompat, int i, Bundle bundle) {
                if (Build.VERSION.SDK_INT >= 25 && (i & 1) != 0) {
                    try {
                        inputContentInfoCompat.requestPermission();
                        InputContentInfo inputContentInfo = (InputContentInfo) inputContentInfoCompat.unwrap();
                        bundle = bundle == null ? new Bundle() : new Bundle(bundle);
                        bundle.putParcelable("androidx.core.view.extra.INPUT_CONTENT_INFO", inputContentInfo);
                    } catch (Exception e) {
                        Log.w("ReceiveContent", "Can't insert content from IME; requestPermission() failed", e);
                        return false;
                    }
                }
                return ViewCompat.performReceiveContent(view, new ContentInfoCompat.Builder(new ClipData(inputContentInfoCompat.getDescription(), new ClipData.Item(inputContentInfoCompat.getContentUri())), 2).setLinkUri(inputContentInfoCompat.getLinkUri()).setExtras(bundle).build()) == null;
            }
        };
    }
}
