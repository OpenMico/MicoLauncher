package androidx.databinding.adapters;

import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import androidx.databinding.BindingConversion;

/* loaded from: classes.dex */
public class Converters {
    @BindingConversion
    public static ColorDrawable convertColorToDrawable(int i) {
        return new ColorDrawable(i);
    }

    @BindingConversion
    public static ColorStateList convertColorToColorStateList(int i) {
        return ColorStateList.valueOf(i);
    }
}
