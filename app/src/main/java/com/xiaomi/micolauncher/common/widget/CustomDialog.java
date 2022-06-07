package com.xiaomi.micolauncher.common.widget;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.application.MicoApplication;
import com.xiaomi.micolauncher.common.WeakRefHandler;
import com.xiaomi.micolauncher.module.video.childmode.ChildModeManager;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class CustomDialog extends AlertDialog {
    private static final long CLOSE_DURATION = TimeUnit.MINUTES.toMillis(1);
    private static final int MSG_CLOSE = 1;
    private TextView btnCancel;
    private TextView btnSure;
    private final Builder builder;
    private TextView messageView;
    private TextView titleView;
    private WeakRefHandler weakRefHandler;
    private long closeDuration = -1;
    private final Handler.Callback closeCallback = new Handler.Callback() { // from class: com.xiaomi.micolauncher.common.widget.CustomDialog.1
        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            if (message.what != 1) {
                return false;
            }
            CustomDialog.this.removeCloseScheduler();
            CustomDialog.this.dismiss();
            if (CustomDialog.this.builder == null || CustomDialog.this.builder.negativeOnClickListener == null) {
                return false;
            }
            CustomDialog.this.builder.negativeOnClickListener.onClick(CustomDialog.this.btnCancel);
            return false;
        }
    };

    /* loaded from: classes3.dex */
    public enum DialogStyle {
        ONLY_TITLE,
        COMMON
    }

    public CustomDialog(Builder builder) {
        super(MicoApplication.getGlobalContext(), R.style.CustomDialog);
        this.builder = builder;
        setOnCancelListener(builder.onCancelListener);
    }

    @Override // android.app.Dialog
    protected void onStart() {
        super.onStart();
    }

    @Override // android.app.AlertDialog, android.app.Dialog
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        int i = ChildModeManager.getManager().isChildMode() ? R.layout.dialog_common_layout_child_mode : R.layout.dialog_common_layout;
        Builder builder = this.builder;
        if (builder != null && builder.layoutResId > 0) {
            i = this.builder.layoutResId;
        }
        setContentView(i);
        this.titleView = (TextView) findViewById(R.id.title);
        this.messageView = (TextView) findViewById(R.id.message);
        this.btnSure = (TextView) findViewById(R.id.btn_sure);
        this.btnCancel = (TextView) findViewById(R.id.btn_cancel);
        initView();
        initTitle();
        initMessage();
        initNegative();
        initPositive();
        this.weakRefHandler = new WeakRefHandler(this.closeCallback);
        scheduleToClose(CLOSE_DURATION);
        setDialogWindowType();
    }

    @Override // android.app.Dialog, android.content.DialogInterface
    public void dismiss() {
        super.dismiss();
        removeCloseScheduler();
    }

    @Override // android.app.Dialog, android.view.Window.Callback
    public boolean dispatchTouchEvent(@NonNull MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case 0:
                removeCloseScheduler();
                break;
            case 1:
                scheduleToClose(this.closeDuration);
                break;
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    private void initView() {
        Builder builder = this.builder;
        if (builder != null) {
            setCanceledOnTouchOutside(builder.canceledOnTouchOutside);
        }
    }

    private void initTitle() {
        if (this.titleView != null) {
            Builder builder = this.builder;
            if (!(builder == null || builder.title == null)) {
                this.titleView.setText(this.builder.title);
            }
            Builder builder2 = this.builder;
            if (builder2 != null && builder2.titleResId > 0) {
                this.titleView.setText(this.builder.titleResId);
            }
        }
    }

    private void initMessage() {
        if (this.messageView != null) {
            Builder builder = this.builder;
            if (!(builder == null || builder.message == null)) {
                this.messageView.setText(this.builder.message);
            }
            Builder builder2 = this.builder;
            if (builder2 != null && builder2.messageResId > 0) {
                this.messageView.setText(this.builder.messageResId);
            }
        }
    }

    private void initNegative() {
        if (this.btnCancel != null) {
            Builder builder = this.builder;
            if (!(builder == null || builder.negative == null)) {
                this.btnCancel.setText(this.builder.negative);
            }
            Builder builder2 = this.builder;
            if (builder2 != null && builder2.negativeResId > 0) {
                this.btnCancel.setText(this.builder.negativeResId);
            }
            this.btnCancel.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.common.widget.-$$Lambda$CustomDialog$3NDTjaZNUDiR7_HoHzYg1OnUrQE
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    CustomDialog.lambda$initNegative$0(CustomDialog.this, view);
                }
            });
            this.btnCancel.setVisibility(this.builder.hasNegativeButton ? 0 : 8);
        }
    }

    public static /* synthetic */ void lambda$initNegative$0(CustomDialog customDialog, View view) {
        customDialog.dismiss();
        Builder builder = customDialog.builder;
        if (builder != null && builder.negativeOnClickListener != null) {
            customDialog.builder.negativeOnClickListener.onClick(view);
        }
    }

    private void initPositive() {
        if (this.btnSure != null) {
            Builder builder = this.builder;
            if (!(builder == null || builder.positive == null)) {
                this.btnSure.setText(this.builder.positive);
            }
            Builder builder2 = this.builder;
            if (builder2 != null && builder2.positiveResId > 0) {
                this.btnSure.setText(this.builder.positiveResId);
            }
            this.btnSure.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.common.widget.-$$Lambda$CustomDialog$RahYSFAq5vBra2yC5Q_YEw2Yu18
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    CustomDialog.lambda$initPositive$1(CustomDialog.this, view);
                }
            });
            this.btnSure.setVisibility(this.builder.hasPositiveButton ? 0 : 8);
        }
    }

    public static /* synthetic */ void lambda$initPositive$1(CustomDialog customDialog, View view) {
        customDialog.dismiss();
        Builder builder = customDialog.builder;
        if (builder != null && builder.positiveOnClickListener != null) {
            customDialog.builder.positiveOnClickListener.onClick(view);
        }
    }

    public TextView getPositiveButton() {
        return this.btnSure;
    }

    public TextView getNegativeButton() {
        return this.btnCancel;
    }

    @Override // android.app.AlertDialog
    public void setMessage(CharSequence charSequence) {
        super.setMessage(charSequence);
        TextView textView = this.messageView;
        if (textView != null) {
            textView.setText(charSequence);
        }
    }

    public void setMessage(int i) {
        TextView textView = this.messageView;
        if (textView != null) {
            textView.setText(i);
        }
    }

    public void removeCloseScheduler() {
        WeakRefHandler weakRefHandler = this.weakRefHandler;
        if (weakRefHandler != null) {
            weakRefHandler.removeCallbacksAndMessages(null);
        }
    }

    private void scheduleToClose(long j) {
        WeakRefHandler weakRefHandler;
        this.closeDuration = j;
        long j2 = this.closeDuration;
        if (j2 > 0 && (weakRefHandler = this.weakRefHandler) != null) {
            weakRefHandler.sendEmptyMessageDelayed(1, j2);
        }
    }

    /* loaded from: classes3.dex */
    public static class Builder {
        private boolean canceledOnTouchOutside;
        private boolean hasNegativeButton = true;
        private boolean hasPositiveButton = true;
        private int layoutResId;
        private CharSequence message;
        private int messageResId;
        private String negative;
        private View.OnClickListener negativeOnClickListener;
        private int negativeResId;
        private DialogInterface.OnCancelListener onCancelListener;
        private String positive;
        private View.OnClickListener positiveOnClickListener;
        private int positiveResId;
        private DialogStyle style;
        private CharSequence title;
        private int titleResId;

        public Builder setTitle(CharSequence charSequence) {
            this.title = charSequence;
            return this;
        }

        public Builder setMessage(CharSequence charSequence) {
            this.message = charSequence;
            return this;
        }

        public Builder setPositive(String str) {
            this.positive = str;
            return this;
        }

        public Builder setNegative(String str) {
            this.negative = str;
            return this;
        }

        public Builder setStyle(DialogStyle dialogStyle) {
            this.style = dialogStyle;
            return this;
        }

        public Builder setLayoutResId(int i) {
            this.layoutResId = i;
            return this;
        }

        public Builder setTitleResId(int i) {
            this.titleResId = i;
            return this;
        }

        public Builder setMessageResId(int i) {
            this.messageResId = i;
            return this;
        }

        public Builder setPositiveResId(int i) {
            this.positiveResId = i;
            return this;
        }

        public Builder setNegativeResId(int i) {
            this.negativeResId = i;
            return this;
        }

        public Builder setHasNegativeButton(boolean z) {
            this.hasNegativeButton = z;
            return this;
        }

        public Builder setHasPositiveButton(boolean z) {
            this.hasPositiveButton = z;
            return this;
        }

        public Builder setPositiveOnClickListener(View.OnClickListener onClickListener) {
            this.positiveOnClickListener = onClickListener;
            return this;
        }

        public Builder setNegativeOnClickListener(View.OnClickListener onClickListener) {
            this.negativeOnClickListener = onClickListener;
            return this;
        }

        public Builder setOnCancelListener(DialogInterface.OnCancelListener onCancelListener) {
            this.onCancelListener = onCancelListener;
            return this;
        }

        public Builder setCanceledOnTouchOutside(boolean z) {
            this.canceledOnTouchOutside = z;
            return this;
        }

        public CustomDialog build() {
            return new CustomDialog(this);
        }
    }

    @Override // android.app.Dialog
    public void onBackPressed() {
        super.onBackPressed();
        Builder builder = this.builder;
        if (builder != null && builder.negativeOnClickListener != null) {
            this.builder.negativeOnClickListener.onClick(this.btnCancel);
        }
    }

    private void setDialogWindowType() {
        Window window = getWindow();
        if (window != null) {
            window.setType(2038);
            window.addFlags(2);
            window.addFlags(1024);
            window.setGravity(17);
        }
    }
}
