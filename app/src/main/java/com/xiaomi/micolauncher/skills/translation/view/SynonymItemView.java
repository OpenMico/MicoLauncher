package com.xiaomi.micolauncher.skills.translation.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.skills.translation.bean.Synonym;
import com.xiaomi.micolauncher.skills.translation.bean.SynonymDetail;

/* loaded from: classes3.dex */
public class SynonymItemView extends LinearLayout {
    TextView a;
    LinearLayout b;

    public SynonymItemView(@NonNull Context context) {
        this(context, null);
    }

    public SynonymItemView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SynonymItemView(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        LayoutInflater.from(context).inflate(R.layout.synonym_item, this);
        this.a = (TextView) findViewById(R.id.speech_part);
        this.b = (LinearLayout) findViewById(R.id.synonym_layout);
    }

    public void setData(int i, Synonym synonym) {
        this.a.setText(synonym.getSpeechPart());
        for (SynonymDetail synonymDetail : synonym.getDetails()) {
            SynonymDetailItemView synonymDetailItemView = new SynonymDetailItemView(getContext());
            synonymDetailItemView.setData(i, synonymDetail);
            this.b.addView(synonymDetailItemView);
        }
    }
}
