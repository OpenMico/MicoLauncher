package com.xiaomi.passport.ui.settings;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Window;
import androidx.appcompat.app.AlertDialog;
import com.xiaomi.accountsdk.utils.AccountLog;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes4.dex */
public class SimpleDialogFragment extends DialogFragment {
    public static final String ARG_CANCELABLE = "cancelable";
    public static final String ARG_MESSAGE = "msg_res_id";
    public static final String ARG_TITLE = "title";
    public static final String ARG_TYPE = "type";
    private static final String TAG = "SimpleDialogFragment";
    public static final int TYPE_ALERT = 1;
    public static final int TYPE_PROGRESS = 2;
    private boolean mCancelable = true;
    private CharSequence mMessage;
    private DialogInterface.OnClickListener mNegativeButtonClickListener;
    private int mNegativeButtonTextRes;
    private DialogInterface.OnDismissListener mOnDismissListener;
    private DialogInterface.OnClickListener mPositiveButtonClickListener;
    private int mPositiveButtonTextRes;
    private String mTitle;
    private int mType;

    @Override // android.app.DialogFragment, android.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.mType = arguments.getInt("type");
            this.mMessage = arguments.getCharSequence("msg_res_id");
            this.mTitle = arguments.getString("title");
            this.mCancelable = arguments.getBoolean("cancelable", true);
            return;
        }
        throw new IllegalStateException("no argument");
    }

    @Override // android.app.DialogFragment
    public Dialog onCreateDialog(Bundle bundle) {
        switch (this.mType) {
            case 1:
                try {
                    if (isMiuiActivity()) {
                        Class<?> cls = Class.forName("miui.app.AlertDialog$Builder");
                        Object newInstance = cls.getConstructor(Context.class).newInstance(getActivity());
                        cls.getMethod("setTitle", CharSequence.class).invoke(newInstance, this.mTitle);
                        cls.getMethod("setMessage", CharSequence.class).invoke(newInstance, this.mMessage);
                        cls.getMethod("setCancelable", Boolean.TYPE).invoke(newInstance, Boolean.valueOf(this.mCancelable));
                        if (this.mPositiveButtonTextRes > 0) {
                            cls.getMethod("setPositiveButton", Integer.TYPE, DialogInterface.OnClickListener.class).invoke(newInstance, Integer.valueOf(this.mPositiveButtonTextRes), this.mPositiveButtonClickListener);
                        }
                        if (this.mNegativeButtonTextRes > 0) {
                            cls.getMethod("setNegativeButton", Integer.TYPE, DialogInterface.OnClickListener.class).invoke(newInstance, Integer.valueOf(this.mNegativeButtonTextRes), this.mNegativeButtonClickListener);
                        }
                        return (Dialog) cls.getMethod("create", new Class[0]).invoke(newInstance, new Object[0]);
                    }
                } catch (ClassNotFoundException e) {
                    AccountLog.e(TAG, "AlertDialog reflect exception: ", e);
                } catch (IllegalAccessException e2) {
                    AccountLog.e(TAG, "AlertDialog reflect exception: ", e2);
                } catch (IllegalArgumentException e3) {
                    AccountLog.e(TAG, "AlertDialog reflect exception: ", e3);
                } catch (InstantiationException e4) {
                    AccountLog.e(TAG, "AlertDialog reflect exception: ", e4);
                } catch (NoSuchMethodException e5) {
                    AccountLog.e(TAG, "AlertDialog reflect exception: ", e5);
                } catch (InvocationTargetException e6) {
                    AccountLog.e(TAG, "AlertDialog reflect exception: ", e6);
                }
                AlertDialog.Builder title = new AlertDialog.Builder(getActivity()).setMessage(this.mMessage).setCancelable(this.mCancelable).setTitle(this.mTitle);
                int i = this.mPositiveButtonTextRes;
                if (i > 0) {
                    title.setPositiveButton(i, this.mPositiveButtonClickListener);
                }
                int i2 = this.mNegativeButtonTextRes;
                if (i2 > 0) {
                    title.setNegativeButton(i2, this.mNegativeButtonClickListener);
                }
                return title.create();
            case 2:
                try {
                    if (isMiuiActivity()) {
                        Class<?> cls2 = Class.forName("miui.app.ProgressDialog");
                        Object newInstance2 = cls2.getConstructor(Context.class).newInstance(getActivity());
                        cls2.getMethod("setMessage", CharSequence.class).invoke(newInstance2, this.mMessage);
                        cls2.getMethod("setCancelable", Boolean.TYPE).invoke(newInstance2, Boolean.valueOf(this.mCancelable));
                        ((Window) cls2.getMethod("getWindow", new Class[0]).invoke(newInstance2, new Object[0])).setGravity(80);
                        return (Dialog) newInstance2;
                    }
                } catch (ClassNotFoundException e7) {
                    AccountLog.e(TAG, "ProgressDialog reflect exception: ", e7);
                } catch (IllegalAccessException e8) {
                    AccountLog.e(TAG, "ProgressDialog reflect exception: ", e8);
                } catch (IllegalArgumentException e9) {
                    AccountLog.e(TAG, "ProgressDialog reflect exception: ", e9);
                } catch (InstantiationException e10) {
                    AccountLog.e(TAG, "ProgressDialog reflect exception: ", e10);
                } catch (NoSuchMethodException e11) {
                    AccountLog.e(TAG, "ProgressDialog reflect exception: ", e11);
                } catch (InvocationTargetException e12) {
                    AccountLog.e(TAG, "ProgressDialog reflect exception: ", e12);
                }
                ProgressDialog progressDialog = new ProgressDialog(getActivity());
                progressDialog.setMessage(this.mMessage);
                progressDialog.setCancelable(this.mCancelable);
                progressDialog.getWindow().setGravity(80);
                return progressDialog;
            default:
                throw new IllegalStateException("unknown dialog type:" + this.mType);
        }
    }

    private boolean isMiuiActivity() {
        try {
            return Class.forName("miui.app.Activity").isInstance(getActivity());
        } catch (ClassNotFoundException unused) {
            return false;
        }
    }

    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        this.mOnDismissListener = onDismissListener;
    }

    @Override // android.app.DialogFragment, android.content.DialogInterface.OnDismissListener
    public void onDismiss(DialogInterface dialogInterface) {
        super.onDismiss(dialogInterface);
        DialogInterface.OnDismissListener onDismissListener = this.mOnDismissListener;
        if (onDismissListener != null) {
            onDismissListener.onDismiss(dialogInterface);
        }
    }

    public void setNegativeButton(int i, DialogInterface.OnClickListener onClickListener) {
        this.mNegativeButtonTextRes = i;
        this.mNegativeButtonClickListener = onClickListener;
    }

    public void setPositiveButton(int i, DialogInterface.OnClickListener onClickListener) {
        this.mPositiveButtonTextRes = i;
        this.mPositiveButtonClickListener = onClickListener;
    }

    /* loaded from: classes4.dex */
    public static final class AlertDialogFragmentBuilder {
        private boolean mCancelable = true;
        private boolean mCreated;
        private CharSequence mMessage;
        private String mTitle;
        private int mType;

        public AlertDialogFragmentBuilder(int i) {
            this.mType = i;
        }

        public AlertDialogFragmentBuilder setMessage(CharSequence charSequence) {
            this.mMessage = charSequence;
            return this;
        }

        public AlertDialogFragmentBuilder setCancelable(boolean z) {
            this.mCancelable = z;
            return this;
        }

        public AlertDialogFragmentBuilder setTitle(String str) {
            this.mTitle = str;
            return this;
        }

        public SimpleDialogFragment create() {
            if (!this.mCreated) {
                this.mCreated = true;
                SimpleDialogFragment simpleDialogFragment = new SimpleDialogFragment();
                Bundle bundle = new Bundle();
                bundle.putString("title", this.mTitle);
                bundle.putCharSequence("msg_res_id", this.mMessage);
                bundle.putBoolean("cancelable", this.mCancelable);
                bundle.putInt("type", this.mType);
                simpleDialogFragment.setArguments(bundle);
                return simpleDialogFragment;
            }
            throw new IllegalStateException("dialog has been created");
        }
    }

    @Override // android.app.DialogFragment
    @Deprecated
    public void dismiss() {
        super.dismiss();
    }

    public void showAllowingStateLoss(FragmentManager fragmentManager, String str) {
        if (fragmentManager == null) {
            AccountLog.w(TAG, "invalid parameter");
            return;
        }
        FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
        beginTransaction.add(this, str);
        beginTransaction.commitAllowingStateLoss();
    }
}
