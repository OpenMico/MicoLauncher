package androidx.databinding.adapters;

import android.widget.VideoView;
import androidx.annotation.RestrictTo;
import androidx.databinding.BindingMethod;
import androidx.databinding.BindingMethods;

@BindingMethods({@BindingMethod(attribute = "android:onCompletion", method = "setOnCompletionListener", type = VideoView.class), @BindingMethod(attribute = "android:onError", method = "setOnErrorListener", type = VideoView.class), @BindingMethod(attribute = "android:onInfo", method = "setOnInfoListener", type = VideoView.class), @BindingMethod(attribute = "android:onPrepared", method = "setOnPreparedListener", type = VideoView.class)})
@RestrictTo({RestrictTo.Scope.LIBRARY})
/* loaded from: classes.dex */
public class VideoViewBindingAdapter {
}
