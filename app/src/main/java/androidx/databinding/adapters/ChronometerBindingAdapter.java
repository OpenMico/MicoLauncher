package androidx.databinding.adapters;

import android.widget.Chronometer;
import androidx.annotation.RestrictTo;
import androidx.databinding.BindingMethod;
import androidx.databinding.BindingMethods;

@BindingMethods({@BindingMethod(attribute = "android:onChronometerTick", method = "setOnChronometerTickListener", type = Chronometer.class)})
@RestrictTo({RestrictTo.Scope.LIBRARY})
/* loaded from: classes.dex */
public class ChronometerBindingAdapter {
}
