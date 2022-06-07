package com.xiaomi.micolauncher.skills.translation.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.skills.translation.bean.WordDeformation;
import java.util.LinkedHashMap;
import java.util.List;

/* loaded from: classes3.dex */
public class WordDeformationView extends LinearLayout {
    TextView a;

    public WordDeformationView(Context context) {
        this(context, null);
    }

    public WordDeformationView(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public WordDeformationView(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        LayoutInflater.from(context).inflate(R.layout.word_deformation_layout, this);
        this.a = (TextView) findViewById(R.id.deformations);
        setOrientation(1);
    }

    public void setData(LinkedHashMap<String, List<WordDeformation>> linkedHashMap) {
        for (String str : linkedHashMap.keySet()) {
            DeformationItemView deformationItemView = new DeformationItemView(getContext());
            deformationItemView.setData(str, linkedHashMap.get(str));
            addView(deformationItemView);
        }
    }
}
