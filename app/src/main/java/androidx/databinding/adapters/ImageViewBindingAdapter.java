package androidx.databinding.adapters;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;
import androidx.annotation.RestrictTo;
import androidx.databinding.BindingAdapter;
import androidx.databinding.BindingMethod;
import androidx.databinding.BindingMethods;

@BindingMethods({@BindingMethod(attribute = "android:tint", method = "setImageTintList", type = ImageView.class), @BindingMethod(attribute = "android:tintMode", method = "setImageTintMode", type = ImageView.class)})
@RestrictTo({RestrictTo.Scope.LIBRARY})
/* loaded from: classes.dex */
public class ImageViewBindingAdapter {
    @BindingAdapter({"android:src"})
    public static void setImageUri(ImageView imageView, String str) {
        if (str == null) {
            imageView.setImageURI(null);
        } else {
            imageView.setImageURI(Uri.parse(str));
        }
    }

    @BindingAdapter({"android:src"})
    public static void setImageUri(ImageView imageView, Uri uri) {
        imageView.setImageURI(uri);
    }

    @BindingAdapter({"android:src"})
    public static void setImageDrawable(ImageView imageView, Drawable drawable) {
        imageView.setImageDrawable(drawable);
    }
}
