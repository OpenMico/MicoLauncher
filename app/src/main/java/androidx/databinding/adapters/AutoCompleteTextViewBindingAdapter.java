package androidx.databinding.adapters;

import android.widget.AutoCompleteTextView;
import androidx.annotation.RestrictTo;
import androidx.databinding.BindingAdapter;
import androidx.databinding.BindingMethod;
import androidx.databinding.BindingMethods;
import androidx.databinding.adapters.AdapterViewBindingAdapter;

@BindingMethods({@BindingMethod(attribute = "android:completionThreshold", method = "setThreshold", type = AutoCompleteTextView.class), @BindingMethod(attribute = "android:popupBackground", method = "setDropDownBackgroundDrawable", type = AutoCompleteTextView.class), @BindingMethod(attribute = "android:onDismiss", method = "setOnDismissListener", type = AutoCompleteTextView.class), @BindingMethod(attribute = "android:onItemClick", method = "setOnItemClickListener", type = AutoCompleteTextView.class)})
@RestrictTo({RestrictTo.Scope.LIBRARY})
/* loaded from: classes.dex */
public class AutoCompleteTextViewBindingAdapter {

    /* loaded from: classes.dex */
    public interface FixText {
        CharSequence fixText(CharSequence charSequence);
    }

    /* loaded from: classes.dex */
    public interface IsValid {
        boolean isValid(CharSequence charSequence);
    }

    @BindingAdapter(requireAll = false, value = {"android:fixText", "android:isValid"})
    public static void setValidator(AutoCompleteTextView autoCompleteTextView, final FixText fixText, final IsValid isValid) {
        if (fixText == null && isValid == null) {
            autoCompleteTextView.setValidator(null);
        } else {
            autoCompleteTextView.setValidator(new AutoCompleteTextView.Validator() { // from class: androidx.databinding.adapters.AutoCompleteTextViewBindingAdapter.1
                @Override // android.widget.AutoCompleteTextView.Validator
                public boolean isValid(CharSequence charSequence) {
                    IsValid isValid2 = IsValid.this;
                    if (isValid2 != null) {
                        return isValid2.isValid(charSequence);
                    }
                    return true;
                }

                @Override // android.widget.AutoCompleteTextView.Validator
                public CharSequence fixText(CharSequence charSequence) {
                    FixText fixText2 = fixText;
                    return fixText2 != null ? fixText2.fixText(charSequence) : charSequence;
                }
            });
        }
    }

    @BindingAdapter(requireAll = false, value = {"android:onItemSelected", "android:onNothingSelected"})
    public static void setOnItemSelectedListener(AutoCompleteTextView autoCompleteTextView, AdapterViewBindingAdapter.OnItemSelected onItemSelected, AdapterViewBindingAdapter.OnNothingSelected onNothingSelected) {
        if (onItemSelected == null && onNothingSelected == null) {
            autoCompleteTextView.setOnItemSelectedListener(null);
        } else {
            autoCompleteTextView.setOnItemSelectedListener(new AdapterViewBindingAdapter.OnItemSelectedComponentListener(onItemSelected, onNothingSelected, null));
        }
    }
}
