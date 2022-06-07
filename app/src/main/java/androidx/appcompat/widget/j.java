package androidx.appcompat.widget;

import android.view.textclassifier.TextClassificationManager;
import android.view.textclassifier.TextClassifier;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.util.Preconditions;

/* compiled from: AppCompatTextClassifierHelper.java */
/* loaded from: classes.dex */
final class j {
    @NonNull
    private TextView a;
    @Nullable
    private TextClassifier b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public j(@NonNull TextView textView) {
        this.a = (TextView) Preconditions.checkNotNull(textView);
    }

    @RequiresApi(api = 26)
    public void a(@Nullable TextClassifier textClassifier) {
        this.b = textClassifier;
    }

    @NonNull
    @RequiresApi(api = 26)
    public TextClassifier a() {
        TextClassifier textClassifier = this.b;
        if (textClassifier != null) {
            return textClassifier;
        }
        TextClassificationManager textClassificationManager = (TextClassificationManager) this.a.getContext().getSystemService(TextClassificationManager.class);
        if (textClassificationManager != null) {
            return textClassificationManager.getTextClassifier();
        }
        return TextClassifier.NO_OP;
    }
}
