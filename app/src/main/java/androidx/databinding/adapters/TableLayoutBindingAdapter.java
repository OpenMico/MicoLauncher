package androidx.databinding.adapters;

import android.util.SparseBooleanArray;
import android.widget.TableLayout;
import androidx.annotation.RestrictTo;
import androidx.databinding.BindingAdapter;
import java.util.regex.Pattern;

@RestrictTo({RestrictTo.Scope.LIBRARY})
/* loaded from: classes.dex */
public class TableLayoutBindingAdapter {
    private static Pattern a = Pattern.compile("\\s*,\\s*");

    @BindingAdapter({"android:collapseColumns"})
    public static void setCollapseColumns(TableLayout tableLayout, CharSequence charSequence) {
        SparseBooleanArray a2 = a(charSequence);
        for (int i = 0; i < 20; i++) {
            boolean z = a2.get(i, false);
            if (z != tableLayout.isColumnCollapsed(i)) {
                tableLayout.setColumnCollapsed(i, z);
            }
        }
    }

    @BindingAdapter({"android:shrinkColumns"})
    public static void setShrinkColumns(TableLayout tableLayout, CharSequence charSequence) {
        if (charSequence == null || charSequence.length() <= 0 || charSequence.charAt(0) != '*') {
            tableLayout.setShrinkAllColumns(false);
            SparseBooleanArray a2 = a(charSequence);
            int size = a2.size();
            for (int i = 0; i < size; i++) {
                int keyAt = a2.keyAt(i);
                boolean valueAt = a2.valueAt(i);
                if (valueAt) {
                    tableLayout.setColumnShrinkable(keyAt, valueAt);
                }
            }
            return;
        }
        tableLayout.setShrinkAllColumns(true);
    }

    @BindingAdapter({"android:stretchColumns"})
    public static void setStretchColumns(TableLayout tableLayout, CharSequence charSequence) {
        if (charSequence == null || charSequence.length() <= 0 || charSequence.charAt(0) != '*') {
            tableLayout.setStretchAllColumns(false);
            SparseBooleanArray a2 = a(charSequence);
            int size = a2.size();
            for (int i = 0; i < size; i++) {
                int keyAt = a2.keyAt(i);
                boolean valueAt = a2.valueAt(i);
                if (valueAt) {
                    tableLayout.setColumnStretchable(keyAt, valueAt);
                }
            }
            return;
        }
        tableLayout.setStretchAllColumns(true);
    }

    private static SparseBooleanArray a(CharSequence charSequence) {
        SparseBooleanArray sparseBooleanArray = new SparseBooleanArray();
        if (charSequence == null) {
            return sparseBooleanArray;
        }
        for (String str : a.split(charSequence)) {
            try {
                int parseInt = Integer.parseInt(str);
                if (parseInt >= 0) {
                    sparseBooleanArray.put(parseInt, true);
                }
            } catch (NumberFormatException unused) {
            }
        }
        return sparseBooleanArray;
    }
}
