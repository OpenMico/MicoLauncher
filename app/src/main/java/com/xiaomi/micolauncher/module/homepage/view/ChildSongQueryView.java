package com.xiaomi.micolauncher.module.homepage.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.module.homepage.bean.ChildSongQueryFirst;
import com.xiaomi.micolauncher.module.homepage.bean.ChildSongQueryInterface;
import com.xiaomi.micolauncher.module.homepage.bean.ChildSongQuerySecond;

/* loaded from: classes3.dex */
public class ChildSongQueryView extends ConstraintLayout {
    ChildSongQueryItemView[] a;

    public ChildSongQueryView(Context context) {
        this(context, null);
    }

    public ChildSongQueryView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ChildSongQueryView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.a = new ChildSongQueryItemView[6];
        a();
    }

    private void a() {
        View.inflate(getContext(), R.layout.child_song_query_first_view, this);
        this.a[0] = (ChildSongQueryItemView) findViewById(R.id.queryYao);
        this.a[1] = (ChildSongQueryItemView) findViewById(R.id.tai);
        this.a[2] = (ChildSongQueryItemView) findViewById(R.id.liuXing);
        this.a[3] = (ChildSongQueryItemView) findViewById(R.id.anquan);
        this.a[4] = (ChildSongQueryItemView) findViewById(R.id.shenghuo);
        this.a[5] = (ChildSongQueryItemView) findViewById(R.id.dongHua);
    }

    public void updateItems(int i) {
        ChildSongQueryInterface[] enumValues = i == 2 ? ChildSongQueryFirst.getEnumValues() : ChildSongQuerySecond.getEnumValues();
        int i2 = 0;
        while (true) {
            ChildSongQueryItemView[] childSongQueryItemViewArr = this.a;
            if (i2 < childSongQueryItemViewArr.length) {
                childSongQueryItemViewArr[i2].setIconResId(enumValues[i2].getIconResId());
                this.a[i2].setTitleResId(enumValues[i2].getTitleResId());
                i2++;
            } else {
                return;
            }
        }
    }
}
