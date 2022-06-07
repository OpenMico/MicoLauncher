package androidx.databinding.adapters;

import android.widget.ZoomControls;
import androidx.annotation.RestrictTo;
import androidx.databinding.BindingMethod;
import androidx.databinding.BindingMethods;

@BindingMethods({@BindingMethod(attribute = "android:onZoomIn", method = "setOnZoomInClickListener", type = ZoomControls.class), @BindingMethod(attribute = "android:onZoomOut", method = "setOnZoomOutClickListener", type = ZoomControls.class)})
@RestrictTo({RestrictTo.Scope.LIBRARY})
/* loaded from: classes.dex */
public class ZoomControlsBindingAdapter {
}
