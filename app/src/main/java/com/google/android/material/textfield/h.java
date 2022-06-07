package com.google.android.material.textfield;

import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import com.google.android.material.R;
import com.google.android.material.internal.TextWatcherAdapter;
import com.google.android.material.textfield.TextInputLayout;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: PasswordToggleEndIconDelegate.java */
/* loaded from: classes2.dex */
public class h extends e {
    private final TextWatcher d = new TextWatcherAdapter() { // from class: com.google.android.material.textfield.h.1
        @Override // com.google.android.material.internal.TextWatcherAdapter, android.text.TextWatcher
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            h.this.c.setChecked(!h.this.c());
        }
    };
    private final TextInputLayout.OnEditTextAttachedListener e = new TextInputLayout.OnEditTextAttachedListener() { // from class: com.google.android.material.textfield.h.2
        @Override // com.google.android.material.textfield.TextInputLayout.OnEditTextAttachedListener
        public void onEditTextAttached(@NonNull TextInputLayout textInputLayout) {
            EditText editText = textInputLayout.getEditText();
            textInputLayout.setEndIconVisible(true);
            textInputLayout.setEndIconCheckable(true);
            h.this.c.setChecked(true ^ h.this.c());
            editText.removeTextChangedListener(h.this.d);
            editText.addTextChangedListener(h.this.d);
        }
    };
    private final TextInputLayout.OnEndIconChangedListener f = new TextInputLayout.OnEndIconChangedListener() { // from class: com.google.android.material.textfield.h.3
        @Override // com.google.android.material.textfield.TextInputLayout.OnEndIconChangedListener
        public void onEndIconChanged(@NonNull TextInputLayout textInputLayout, int i) {
            final EditText editText = textInputLayout.getEditText();
            if (editText != null && i == 1) {
                editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                editText.post(new Runnable() { // from class: com.google.android.material.textfield.h.3.1
                    @Override // java.lang.Runnable
                    public void run() {
                        editText.removeTextChangedListener(h.this.d);
                    }
                });
            }
        }
    };

    /* JADX INFO: Access modifiers changed from: package-private */
    public h(@NonNull TextInputLayout textInputLayout) {
        super(textInputLayout);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.android.material.textfield.e
    public void a() {
        this.a.setEndIconDrawable(AppCompatResources.getDrawable(this.b, R.drawable.design_password_eye));
        this.a.setEndIconContentDescription(this.a.getResources().getText(R.string.password_toggle_content_description));
        this.a.setEndIconOnClickListener(new View.OnClickListener() { // from class: com.google.android.material.textfield.h.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                EditText editText = h.this.a.getEditText();
                if (editText != null) {
                    int selectionEnd = editText.getSelectionEnd();
                    if (h.this.c()) {
                        editText.setTransformationMethod(null);
                    } else {
                        editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    }
                    if (selectionEnd >= 0) {
                        editText.setSelection(selectionEnd);
                    }
                    h.this.a.refreshEndIconDrawableState();
                }
            }
        });
        this.a.addOnEditTextAttachedListener(this.e);
        this.a.addOnEndIconChangedListener(this.f);
        EditText editText = this.a.getEditText();
        if (a(editText)) {
            editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean c() {
        EditText editText = this.a.getEditText();
        return editText != null && (editText.getTransformationMethod() instanceof PasswordTransformationMethod);
    }

    private static boolean a(EditText editText) {
        return editText != null && (editText.getInputType() == 16 || editText.getInputType() == 128 || editText.getInputType() == 144 || editText.getInputType() == 224);
    }
}
