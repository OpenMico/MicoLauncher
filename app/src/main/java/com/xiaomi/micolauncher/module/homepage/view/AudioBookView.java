package com.xiaomi.micolauncher.module.homepage.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.xiaomi.micolauncher.R;

/* loaded from: classes3.dex */
public class AudioBookView extends ConstraintLayout {
    ImageView a;
    TextView b;
    TextView c;

    public AudioBookView(Context context) {
        this(context, null);
    }

    public AudioBookView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public AudioBookView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        View inflate = LayoutInflater.from(context).inflate(R.layout.audiobook_big_item, this);
        this.a = (ImageView) inflate.findViewById(R.id.audiobook_iv);
        this.b = (TextView) inflate.findViewById(R.id.audiobook_name);
        this.c = (TextView) inflate.findViewById(R.id.audiobook_title);
    }
}
