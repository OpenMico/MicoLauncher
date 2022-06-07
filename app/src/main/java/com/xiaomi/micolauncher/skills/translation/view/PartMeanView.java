package com.xiaomi.micolauncher.skills.translation.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.skills.translation.bean.PartMeaning;

/* loaded from: classes3.dex */
public class PartMeanView extends LinearLayout {
    TextView a;
    TextView b;

    public PartMeanView(Context context) {
        this(context, null);
    }

    public PartMeanView(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public PartMeanView(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        LayoutInflater.from(context).inflate(R.layout.meanings_item, this);
        this.a = (TextView) findViewById(R.id.speech_part);
        this.b = (TextView) findViewById(R.id.meaning);
    }

    public void setData(PartMeaning partMeaning) {
        this.a.setText(partMeaning.getSpeechPart());
        this.b.setText(partMeaning.getMeaning());
    }
}
