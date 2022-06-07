package com.xiaomi.passport.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.xiaomi.passport.ui.internal.AlertController;
import com.xiaomi.passport.ui.internal.AlertControllerImpl;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public class AlertControllerWrapper extends AlertController {
    AlertControllerImpl mAlertControllerImpl;

    @Override // com.xiaomi.passport.ui.internal.AlertController
    public ListView getListView() {
        return null;
    }

    @Override // com.xiaomi.passport.ui.internal.AlertController
    public void setIcon(int i) {
    }

    @Override // com.xiaomi.passport.ui.internal.AlertController
    public void setInverseBackgroundForced(boolean z) {
    }

    public AlertControllerWrapper(Context context, DialogInterface dialogInterface, Window window) {
        super(context, dialogInterface, window);
        this.mAlertControllerImpl = new AlertControllerImpl(context, dialogInterface, window);
    }

    public AlertControllerImpl getImpl() {
        return this.mAlertControllerImpl;
    }

    @Override // com.xiaomi.passport.ui.internal.AlertController
    public void installContent() {
        this.mAlertControllerImpl.installContent();
    }

    @Override // com.xiaomi.passport.ui.internal.AlertController
    public void setTitle(CharSequence charSequence) {
        this.mAlertControllerImpl.setTitle(charSequence);
    }

    @Override // com.xiaomi.passport.ui.internal.AlertController
    public void setCustomTitle(View view) {
        this.mAlertControllerImpl.setCustomTitle(view);
    }

    @Override // com.xiaomi.passport.ui.internal.AlertController
    public void setMessage(CharSequence charSequence) {
        this.mAlertControllerImpl.setMessage(charSequence);
    }

    @Override // com.xiaomi.passport.ui.internal.AlertController
    public void setView(View view) {
        this.mAlertControllerImpl.setView(view);
    }

    @Override // com.xiaomi.passport.ui.internal.AlertController
    public void setButton(int i, CharSequence charSequence, DialogInterface.OnClickListener onClickListener, Message message) {
        this.mAlertControllerImpl.setButton(i, charSequence, onClickListener, message);
    }

    @Override // com.xiaomi.passport.ui.internal.AlertController
    public Button getButton(int i) {
        return this.mAlertControllerImpl.getButton(i);
    }

    @Override // com.xiaomi.passport.ui.internal.AlertController
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        return this.mAlertControllerImpl.onKeyDown(i, keyEvent);
    }

    @Override // com.xiaomi.passport.ui.internal.AlertController
    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        return this.mAlertControllerImpl.onKeyUp(i, keyEvent);
    }

    public boolean[] getCheckedItems() {
        return this.mAlertControllerImpl.getCheckedItems();
    }

    /* loaded from: classes4.dex */
    public static class AlertParams extends AlertController.AlertParams {
        public ArrayList<ActionItem> mActionItems;
        public boolean mEditMode;
        public DialogInterface.OnClickListener mOnActionItemClickListener;
        public DialogInterface.OnDismissListener mOnDismissListener;
        public DialogInterface.OnShowListener mOnShowListener;

        /* loaded from: classes4.dex */
        public static class ActionItem {
            public int icon;
            public int id;
            public CharSequence label;

            public ActionItem(CharSequence charSequence, int i, int i2) {
                this.label = charSequence;
                this.icon = i;
                this.id = i2;
            }
        }

        public AlertParams(Context context) {
            super(context);
        }

        @Override // com.xiaomi.passport.ui.internal.AlertController.AlertParams
        public void apply(AlertController alertController) {
            if (this.mCustomTitleView != null) {
                alertController.setCustomTitle(this.mCustomTitleView);
            } else {
                if (this.mTitle != null) {
                    alertController.setTitle(this.mTitle);
                }
                if (this.mIconId >= 0) {
                    alertController.setIcon(this.mIconId);
                }
            }
            if (this.mMessage != null) {
                alertController.setMessage(this.mMessage);
            }
            if (this.mPositiveButtonText != null) {
                alertController.setButton(-1, this.mPositiveButtonText, this.mPositiveButtonListener, null);
            }
            if (this.mNegativeButtonText != null) {
                alertController.setButton(-2, this.mNegativeButtonText, this.mNegativeButtonListener, null);
            }
            if (this.mNeutralButtonText != null) {
                alertController.setButton(-3, this.mNeutralButtonText, this.mNeutralButtonListener, null);
            }
            if (this.mItems == null && this.mCursor == null) {
                ListAdapter listAdapter = this.mAdapter;
            }
            if (this.mView != null) {
                alertController.setView(this.mView);
            }
            if (this.mActionItems != null) {
                ((AlertControllerWrapper) alertController).getImpl().setActionItems(this.mActionItems, this.mOnActionItemClickListener);
            }
        }
    }
}
