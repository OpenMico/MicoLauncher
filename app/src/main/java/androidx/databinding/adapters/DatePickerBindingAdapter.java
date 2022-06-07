package androidx.databinding.adapters;

import android.widget.DatePicker;
import androidx.annotation.RestrictTo;
import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingListener;
import androidx.databinding.InverseBindingMethod;
import androidx.databinding.InverseBindingMethods;
import androidx.databinding.library.baseAdapters.R;

@InverseBindingMethods({@InverseBindingMethod(attribute = "android:year", type = DatePicker.class), @InverseBindingMethod(attribute = "android:month", type = DatePicker.class), @InverseBindingMethod(attribute = "android:day", method = "getDayOfMonth", type = DatePicker.class)})
@RestrictTo({RestrictTo.Scope.LIBRARY})
/* loaded from: classes.dex */
public class DatePickerBindingAdapter {
    @BindingAdapter(requireAll = false, value = {"android:year", "android:month", "android:day", "android:onDateChanged", "android:yearAttrChanged", "android:monthAttrChanged", "android:dayAttrChanged"})
    public static void setListeners(DatePicker datePicker, int i, int i2, int i3, DatePicker.OnDateChangedListener onDateChangedListener, InverseBindingListener inverseBindingListener, InverseBindingListener inverseBindingListener2, InverseBindingListener inverseBindingListener3) {
        if (i == 0) {
            i = datePicker.getYear();
        }
        if (i3 == 0) {
            i3 = datePicker.getDayOfMonth();
        }
        if (inverseBindingListener == null && inverseBindingListener2 == null && inverseBindingListener3 == null) {
            datePicker.init(i, i2, i3, onDateChangedListener);
            return;
        }
        a aVar = (a) ListenerUtil.getListener(datePicker, R.id.onDateChanged);
        if (aVar == null) {
            aVar = new a();
            ListenerUtil.trackListener(datePicker, aVar, R.id.onDateChanged);
        }
        aVar.a(onDateChangedListener, inverseBindingListener, inverseBindingListener2, inverseBindingListener3);
        datePicker.init(i, i2, i3, aVar);
    }

    /* loaded from: classes.dex */
    private static class a implements DatePicker.OnDateChangedListener {
        DatePicker.OnDateChangedListener a;
        InverseBindingListener b;
        InverseBindingListener c;
        InverseBindingListener d;

        private a() {
        }

        public void a(DatePicker.OnDateChangedListener onDateChangedListener, InverseBindingListener inverseBindingListener, InverseBindingListener inverseBindingListener2, InverseBindingListener inverseBindingListener3) {
            this.a = onDateChangedListener;
            this.b = inverseBindingListener;
            this.c = inverseBindingListener2;
            this.d = inverseBindingListener3;
        }

        @Override // android.widget.DatePicker.OnDateChangedListener
        public void onDateChanged(DatePicker datePicker, int i, int i2, int i3) {
            DatePicker.OnDateChangedListener onDateChangedListener = this.a;
            if (onDateChangedListener != null) {
                onDateChangedListener.onDateChanged(datePicker, i, i2, i3);
            }
            InverseBindingListener inverseBindingListener = this.b;
            if (inverseBindingListener != null) {
                inverseBindingListener.onChange();
            }
            InverseBindingListener inverseBindingListener2 = this.c;
            if (inverseBindingListener2 != null) {
                inverseBindingListener2.onChange();
            }
            InverseBindingListener inverseBindingListener3 = this.d;
            if (inverseBindingListener3 != null) {
                inverseBindingListener3.onChange();
            }
        }
    }
}
