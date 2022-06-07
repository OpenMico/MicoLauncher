package com.xiaomi.passport.ui.internal;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

/* loaded from: classes4.dex */
public abstract class AlertController {
    public abstract Button getButton(int i);

    public abstract ListView getListView();

    public abstract void installContent();

    public abstract boolean onKeyDown(int i, KeyEvent keyEvent);

    public abstract boolean onKeyUp(int i, KeyEvent keyEvent);

    public abstract void setButton(int i, CharSequence charSequence, DialogInterface.OnClickListener onClickListener, Message message);

    public abstract void setCustomTitle(View view);

    public abstract void setIcon(int i);

    public abstract void setInverseBackgroundForced(boolean z);

    public abstract void setMessage(CharSequence charSequence);

    public abstract void setTitle(CharSequence charSequence);

    public abstract void setView(View view);

    public AlertController(Context context, DialogInterface dialogInterface, Window window) {
    }

    /* loaded from: classes4.dex */
    public static abstract class AlertParams {
        public ListAdapter mAdapter;
        public boolean mCancelable;
        public boolean[] mCheckedItems;
        public Context mContext;
        public Cursor mCursor;
        public View mCustomTitleView;
        public LayoutInflater mInflater;
        public String mIsCheckedColumn;
        public boolean mIsMultiChoice;
        public boolean mIsSingleChoice;
        public CharSequence[] mItems;
        public String mLabelColumn;
        public CharSequence mMessage;
        public DialogInterface.OnClickListener mNegativeButtonListener;
        public CharSequence mNegativeButtonText;
        public DialogInterface.OnClickListener mNeutralButtonListener;
        public CharSequence mNeutralButtonText;
        public DialogInterface.OnCancelListener mOnCancelListener;
        public DialogInterface.OnMultiChoiceClickListener mOnCheckboxClickListener;
        public DialogInterface.OnClickListener mOnClickListener;
        public AdapterView.OnItemSelectedListener mOnItemSelectedListener;
        public DialogInterface.OnKeyListener mOnKeyListener;
        public DialogInterface.OnClickListener mPositiveButtonListener;
        public CharSequence mPositiveButtonText;
        public CharSequence mTitle;
        public View mView;
        public int mIconId = 0;
        public int mCheckedItem = -1;

        public abstract void apply(AlertController alertController);

        public AlertParams(Context context) {
            this.mContext = context;
        }
    }
}
