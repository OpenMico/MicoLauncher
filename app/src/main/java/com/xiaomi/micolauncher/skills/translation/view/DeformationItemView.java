package com.xiaomi.micolauncher.skills.translation.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.skills.translation.bean.WordDeformation;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes3.dex */
public class DeformationItemView extends LinearLayout {
    TextView a;
    TextView b;

    public DeformationItemView(Context context) {
        this(context, null);
    }

    public DeformationItemView(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public DeformationItemView(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        LayoutInflater.from(context).inflate(R.layout.deformation_item_layout, this);
        this.a = (TextView) findViewById(R.id.word_key);
        this.b = (TextView) findViewById(R.id.word_content);
    }

    public void setData(String str, List<WordDeformation> list) {
        this.a.setText(str);
        StringBuilder sb = new StringBuilder();
        Context context = getContext();
        for (int i = 0; i < list.size(); i++) {
            WordDeformation wordDeformation = list.get(i);
            sb.append(context.getString(R.string.word_deformation, wordDeformation.getPartName(context), wordDeformation.getWords().get(0)));
            if (Hardware.isBigScreen()) {
                sb.append(StringUtils.SPACE);
                if (i < list.size() - 1 && (i + 1) % 2 == 0) {
                    sb.append("\n");
                }
            } else {
                sb.append("\n");
            }
        }
        this.b.setText(sb.toString());
    }
}
