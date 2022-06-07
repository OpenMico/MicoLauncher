package com.xiaomi.passport.ui.settings.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.xiaomi.passport.ui.R;
import com.xiaomi.passport.ui.internal.util.SysHelper;

/* loaded from: classes4.dex */
public class PasswordView extends FrameLayout {
    private boolean mCheckPasswordRule;
    private EditText mPasswordView;
    private ImageView mPwdImageView;
    private boolean mShowClearPassword;

    public PasswordView(@NonNull Context context) {
        super(context);
        initView(context, false, null);
    }

    public PasswordView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.PasswordView);
        boolean z = obtainStyledAttributes.getBoolean(R.styleable.PasswordView_provision, false);
        String string = obtainStyledAttributes.getString(R.styleable.PasswordView_passwordHint);
        this.mCheckPasswordRule = obtainStyledAttributes.getBoolean(R.styleable.PasswordView_checkPasswordRule, false);
        initView(context, z, string);
        obtainStyledAttributes.recycle();
    }

    private void initView(Context context, boolean z, String str) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.passport_password, this);
        this.mPasswordView = (EditText) inflate.findViewById(R.id.input_pwd_view);
        if (!TextUtils.isEmpty(str)) {
            this.mPasswordView.setHint(str);
        }
        this.mPasswordView.setOnFocusChangeListener(new View.OnFocusChangeListener() { // from class: com.xiaomi.passport.ui.settings.widget.PasswordView.1
            @Override // android.view.View.OnFocusChangeListener
            public void onFocusChange(View view, boolean z2) {
                PasswordView.this.mPasswordView.setError(null);
            }
        });
        this.mPwdImageView = (ImageView) inflate.findViewById(R.id.show_pwd_img);
        this.mPwdImageView.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.passport.ui.settings.widget.PasswordView.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PasswordView.this.updateShowPasswordState();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateShowPasswordState() {
        this.mShowClearPassword = !this.mShowClearPassword;
        EditText editText = this.mPasswordView;
        if (editText != null && this.mPwdImageView != null) {
            editText.setInputType(SysHelper.getEditViewInputType(this.mShowClearPassword));
            this.mPasswordView.setTypeface(Typeface.DEFAULT);
            EditText editText2 = this.mPasswordView;
            editText2.setSelection(editText2.getText().length());
            this.mPwdImageView.setImageResource(this.mShowClearPassword ? R.drawable.passport_password_show : R.drawable.passport_password_not_show);
        }
    }

    public String getPassword() {
        EditText editText = this.mPasswordView;
        if (editText == null) {
            return null;
        }
        String obj = editText.getText().toString();
        if (TextUtils.isEmpty(obj)) {
            this.mPasswordView.requestFocus();
            this.mPasswordView.setError(getContext().getString(R.string.passport_error_empty_pwd));
            return null;
        } else if (!this.mCheckPasswordRule || SysHelper.checkPasswordPattern(obj)) {
            return obj;
        } else {
            this.mPasswordView.requestFocus();
            this.mPasswordView.setError(getContext().getString(R.string.passport_error_illegal_pwd));
            return null;
        }
    }

    public void setPassword(CharSequence charSequence) {
        if (this.mPasswordView != null && !TextUtils.isEmpty(charSequence)) {
            this.mPasswordView.setText(charSequence);
            this.mPasswordView.setSelection(charSequence.length());
        }
    }

    public void setError(CharSequence charSequence) {
        EditText editText = this.mPasswordView;
        if (editText != null) {
            editText.setError(charSequence);
        }
    }

    public void addTextChangedListener(TextWatcher textWatcher) {
        EditText editText = this.mPasswordView;
        if (editText != null) {
            editText.addTextChangedListener(textWatcher);
        }
    }
}
