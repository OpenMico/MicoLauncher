package com.google.android.material.timepicker;

import android.text.Editable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.google.android.material.textfield.TextInputLayout;

/* compiled from: TimePickerTextInputKeyController.java */
/* loaded from: classes2.dex */
class g implements View.OnKeyListener, TextView.OnEditorActionListener {
    private final ChipTextInputComboView a;
    private final ChipTextInputComboView b;
    private final d c;
    private boolean d = false;

    /* JADX INFO: Access modifiers changed from: package-private */
    public g(ChipTextInputComboView chipTextInputComboView, ChipTextInputComboView chipTextInputComboView2, d dVar) {
        this.a = chipTextInputComboView;
        this.b = chipTextInputComboView2;
        this.c = dVar;
    }

    public void a() {
        TextInputLayout a = this.a.a();
        TextInputLayout a2 = this.b.a();
        EditText editText = a.getEditText();
        EditText editText2 = a2.getEditText();
        editText.setImeOptions(268435461);
        editText2.setImeOptions(268435462);
        editText.setOnEditorActionListener(this);
        editText.setOnKeyListener(this);
        editText2.setOnKeyListener(this);
    }

    private void a(int i) {
        boolean z = true;
        this.b.setChecked(i == 12);
        ChipTextInputComboView chipTextInputComboView = this.a;
        if (i != 10) {
            z = false;
        }
        chipTextInputComboView.setChecked(z);
        this.c.d = i;
    }

    @Override // android.widget.TextView.OnEditorActionListener
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        boolean z = i == 5;
        if (z) {
            a(12);
        }
        return z;
    }

    @Override // android.view.View.OnKeyListener
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        boolean z;
        if (this.d) {
            return false;
        }
        this.d = true;
        EditText editText = (EditText) view;
        if (this.c.d == 12) {
            z = a(i, keyEvent, editText);
        } else {
            z = b(i, keyEvent, editText);
        }
        this.d = false;
        return z;
    }

    private boolean a(int i, KeyEvent keyEvent, EditText editText) {
        if (!(i == 67 && keyEvent.getAction() == 0 && TextUtils.isEmpty(editText.getText()))) {
            return false;
        }
        a(10);
        return true;
    }

    private boolean b(int i, KeyEvent keyEvent, EditText editText) {
        Editable text = editText.getText();
        if (text == null) {
            return false;
        }
        if (!(i >= 7 && i <= 16 && keyEvent.getAction() == 1 && editText.getSelectionStart() == 2 && text.length() == 2)) {
            return false;
        }
        a(12);
        return true;
    }
}
