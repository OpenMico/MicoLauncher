package miuix.animation.font;

import android.annotation.TargetApi;
import android.view.View;
import android.widget.TextView;
import java.lang.ref.WeakReference;
import java.util.Objects;
import miuix.animation.property.ISpecificProperty;
import miuix.animation.property.ViewProperty;

@TargetApi(26)
/* loaded from: classes5.dex */
public class FontWeightProperty extends ViewProperty implements ISpecificProperty {
    private WeakReference<TextView> a;
    private int b;
    private float d = Float.MAX_VALUE;

    public FontWeightProperty(TextView textView, int i) {
        super("fontweight");
        this.a = new WeakReference<>(textView);
        this.b = i;
    }

    public TextView getTextView() {
        return this.a.get();
    }

    public float getScaledTextSize() {
        TextView textView = this.a.get();
        if (textView != null) {
            return textView.getTextSize() / textView.getResources().getDisplayMetrics().scaledDensity;
        }
        return 0.0f;
    }

    public float getValue(View view) {
        return this.d;
    }

    public void setValue(View view, float f) {
        this.d = f;
        TextView textView = this.a.get();
        if (textView != null) {
            VarFontUtils.a(textView, (int) f);
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass() || !super.equals(obj)) {
            return false;
        }
        TextView textView = this.a.get();
        return textView != null && textView.equals(((FontWeightProperty) obj).a.get());
    }

    public int hashCode() {
        TextView textView = this.a.get();
        return textView != null ? Objects.hash(Integer.valueOf(super.hashCode()), textView) : Objects.hash(Integer.valueOf(super.hashCode()), this.a);
    }

    @Override // miuix.animation.property.ISpecificProperty
    public float getSpecificValue(float f) {
        TextView textView = this.a.get();
        if (f >= VarFontUtils.MIN_WGHT || textView == null) {
            return f;
        }
        return VarFontUtils.a((int) f, getScaledTextSize(), this.b, VarFontUtils.a(textView.getContext()));
    }
}
