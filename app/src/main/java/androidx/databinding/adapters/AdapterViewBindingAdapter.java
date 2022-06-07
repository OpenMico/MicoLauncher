package androidx.databinding.adapters;

import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import androidx.annotation.RestrictTo;
import androidx.databinding.BindingAdapter;
import androidx.databinding.BindingMethod;
import androidx.databinding.BindingMethods;
import androidx.databinding.InverseBindingListener;
import androidx.databinding.InverseBindingMethod;
import androidx.databinding.InverseBindingMethods;

@BindingMethods({@BindingMethod(attribute = "android:onItemClick", method = "setOnItemClickListener", type = AdapterView.class), @BindingMethod(attribute = "android:onItemLongClick", method = "setOnItemLongClickListener", type = AdapterView.class)})
@InverseBindingMethods({@InverseBindingMethod(attribute = "android:selectedItemPosition", type = AdapterView.class), @InverseBindingMethod(attribute = "android:selection", event = "android:selectedItemPositionAttrChanged", method = "getSelectedItemPosition", type = AdapterView.class)})
@RestrictTo({RestrictTo.Scope.LIBRARY})
/* loaded from: classes.dex */
public class AdapterViewBindingAdapter {

    /* loaded from: classes.dex */
    public interface OnItemSelected {
        void onItemSelected(AdapterView<?> adapterView, View view, int i, long j);
    }

    /* loaded from: classes.dex */
    public interface OnNothingSelected {
        void onNothingSelected(AdapterView<?> adapterView);
    }

    @BindingAdapter({"android:selectedItemPosition"})
    public static void setSelectedItemPosition(AdapterView adapterView, int i) {
        if (adapterView.getSelectedItemPosition() != i) {
            adapterView.setSelection(i);
        }
    }

    @BindingAdapter({"android:selection"})
    public static void setSelection(AdapterView adapterView, int i) {
        setSelectedItemPosition(adapterView, i);
    }

    @BindingAdapter({"android:selectedItemPosition", "android:adapter"})
    public static void setSelectedItemPosition(AdapterView adapterView, int i, Adapter adapter) {
        if (adapter != adapterView.getAdapter()) {
            adapterView.setAdapter(adapter);
            adapterView.setSelection(i);
        } else if (adapterView.getSelectedItemPosition() != i) {
            adapterView.setSelection(i);
        }
    }

    @BindingAdapter({"android:selection", "android:adapter"})
    public static void setSelection(AdapterView adapterView, int i, Adapter adapter) {
        setSelectedItemPosition(adapterView, i, adapter);
    }

    @BindingAdapter(requireAll = false, value = {"android:onItemSelected", "android:onNothingSelected", "android:selectedItemPositionAttrChanged"})
    public static void setOnItemSelectedListener(AdapterView adapterView, OnItemSelected onItemSelected, OnNothingSelected onNothingSelected, InverseBindingListener inverseBindingListener) {
        if (onItemSelected == null && onNothingSelected == null && inverseBindingListener == null) {
            adapterView.setOnItemSelectedListener(null);
        } else {
            adapterView.setOnItemSelectedListener(new OnItemSelectedComponentListener(onItemSelected, onNothingSelected, inverseBindingListener));
        }
    }

    /* loaded from: classes.dex */
    public static class OnItemSelectedComponentListener implements AdapterView.OnItemSelectedListener {
        private final OnItemSelected a;
        private final OnNothingSelected b;
        private final InverseBindingListener c;

        public OnItemSelectedComponentListener(OnItemSelected onItemSelected, OnNothingSelected onNothingSelected, InverseBindingListener inverseBindingListener) {
            this.a = onItemSelected;
            this.b = onNothingSelected;
            this.c = inverseBindingListener;
        }

        @Override // android.widget.AdapterView.OnItemSelectedListener
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
            OnItemSelected onItemSelected = this.a;
            if (onItemSelected != null) {
                onItemSelected.onItemSelected(adapterView, view, i, j);
            }
            InverseBindingListener inverseBindingListener = this.c;
            if (inverseBindingListener != null) {
                inverseBindingListener.onChange();
            }
        }

        @Override // android.widget.AdapterView.OnItemSelectedListener
        public void onNothingSelected(AdapterView<?> adapterView) {
            OnNothingSelected onNothingSelected = this.b;
            if (onNothingSelected != null) {
                onNothingSelected.onNothingSelected(adapterView);
            }
            InverseBindingListener inverseBindingListener = this.c;
            if (inverseBindingListener != null) {
                inverseBindingListener.onChange();
            }
        }
    }
}
