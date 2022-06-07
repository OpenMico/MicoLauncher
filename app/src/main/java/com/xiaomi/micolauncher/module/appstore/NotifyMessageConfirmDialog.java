package com.xiaomi.micolauncher.module.appstore;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.base.BaseActivity;

/* loaded from: classes3.dex */
public class NotifyMessageConfirmDialog extends Dialog {
    private View a;
    private Handler b = new Handler(Looper.getMainLooper());

    public NotifyMessageConfirmDialog(Context context) {
        super(context, R.style.DialogAlbum);
        this.a = LayoutInflater.from(context).inflate(R.layout.dialog_notify_message_confirm, (ViewGroup) null);
        setContentView(this.a);
        ((TextView) this.a.findViewById(R.id.close_btn)).setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.appstore.-$$Lambda$NotifyMessageConfirmDialog$uSRqsRSS0xMn4DdsAkbg3wA1NwI
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                NotifyMessageConfirmDialog.this.b(view);
            }
        });
        this.b.postDelayed(new Runnable() { // from class: com.xiaomi.micolauncher.module.appstore.-$$Lambda$NotifyMessageConfirmDialog$OCR5cwp3YbuWeZDID0JaIW5nZzc
            @Override // java.lang.Runnable
            public final void run() {
                NotifyMessageConfirmDialog.this.b();
            }
        }, BaseActivity.DEFAULT_CLOSE_DURATION);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(View view) {
        if (isShowing()) {
            dismiss();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b() {
        if (isShowing()) {
            try {
                Context baseContext = ((ContextWrapper) getContext()).getBaseContext();
                if (!(baseContext instanceof Activity)) {
                    dismiss();
                } else if (!((Activity) baseContext).isFinishing() && !((Activity) baseContext).isDestroyed()) {
                    dismiss();
                }
            } catch (IllegalArgumentException e) {
                L.storage.e("appstore.NotifyMessageConfirmDialog", e);
            } catch (Exception e2) {
                L.storage.e("appstore.NotifyMessageConfirmDialog", e2);
            }
        }
    }

    public NotifyMessageConfirmDialog(Context context, int i) {
        super(context, R.style.DialogAlbum);
        Window window = getWindow();
        if (window != null) {
            window.setType(2038);
        }
        this.a = LayoutInflater.from(context).inflate(i, (ViewGroup) null);
        setContentView(this.a);
        ((TextView) this.a.findViewById(R.id.close_btn)).setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.appstore.-$$Lambda$NotifyMessageConfirmDialog$eny825rDfDzNyqDno_TnDJEHMJo
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                NotifyMessageConfirmDialog.this.a(view);
            }
        });
        this.b.postDelayed(new Runnable() { // from class: com.xiaomi.micolauncher.module.appstore.-$$Lambda$NotifyMessageConfirmDialog$FsOQ9fOKuQrR1clMmE4R-k9pmec
            @Override // java.lang.Runnable
            public final void run() {
                NotifyMessageConfirmDialog.this.a();
            }
        }, BaseActivity.DEFAULT_CLOSE_DURATION);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(View view) {
        dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a() {
        if (isShowing()) {
            try {
                Context baseContext = ((ContextWrapper) getContext()).getBaseContext();
                if (!(baseContext instanceof Activity)) {
                    dismiss();
                } else if (!((Activity) baseContext).isFinishing() && !((Activity) baseContext).isDestroyed()) {
                    dismiss();
                }
            } catch (IllegalArgumentException e) {
                L.storage.e("appstore.NotifyMessageConfirmDialog", e);
            } catch (Exception e2) {
                L.storage.e("appstore.NotifyMessageConfirmDialog", e2);
            }
        }
    }

    public NotifyMessageConfirmDialog setMessage(int i) {
        View view = this.a;
        if (view != null) {
            ((TextView) view.findViewById(R.id.custom_dialog_message)).setText(i);
        }
        return this;
    }

    public NotifyMessageConfirmDialog setMessage(String str) {
        View view = this.a;
        if (view != null) {
            ((TextView) view.findViewById(R.id.custom_dialog_message)).setText(str);
        }
        return this;
    }

    public NotifyMessageConfirmDialog setButtonMessage(int i) {
        View view = this.a;
        if (view != null) {
            ((TextView) view.findViewById(R.id.close_btn)).setText(i);
        }
        return this;
    }

    public NotifyMessageConfirmDialog setButtonMessage(String str) {
        View view = this.a;
        if (view != null) {
            ((TextView) view.findViewById(R.id.close_btn)).setText(str);
        }
        return this;
    }

    public NotifyMessageConfirmDialog setWidth(int i) {
        Window window = getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = i;
        window.setAttributes(attributes);
        return this;
    }

    public NotifyMessageConfirmDialog setButtonClickListener(View.OnClickListener onClickListener) {
        ((TextView) this.a.findViewById(R.id.close_btn)).setOnClickListener(onClickListener);
        return this;
    }
}
