package com.xiaomi.micolauncher.skills.translation.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.skills.translation.bean.Synonym;
import java.util.List;

/* loaded from: classes3.dex */
public class SynonymView extends LinearLayout {
    TextView a;

    public SynonymView(Context context) {
        this(context, null);
    }

    public SynonymView(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SynonymView(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        LayoutInflater.from(context).inflate(R.layout.synonym_layout, this);
        this.a = (TextView) findViewById(R.id.synonym_tv);
        setOrientation(1);
    }

    public void setData(int i, int i2, List<Synonym> list) {
        if (!ContainerUtil.isEmpty(list)) {
            this.a.setText(i2);
            for (Synonym synonym : list) {
                if (!TextUtils.isEmpty(synonym.getSpeechPart()) && !ContainerUtil.isEmpty(synonym.getDetails())) {
                    SynonymItemView synonymItemView = new SynonymItemView(getContext());
                    synonymItemView.setData(i, synonym);
                    addView(synonymItemView);
                }
            }
        }
    }
}
