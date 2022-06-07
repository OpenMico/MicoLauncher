package com.xiaomi.micolauncher.module.music.base;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import com.xiaomi.micolauncher.module.music.bean.TitleType;

/* loaded from: classes3.dex */
public abstract class BaseSquareCardView extends RelativeLayout {
    /* JADX INFO: Access modifiers changed from: protected */
    public abstract void refreshPage(Object obj);

    /* JADX INFO: Access modifiers changed from: protected */
    public abstract void setDefaultResId(int i);

    /* JADX INFO: Access modifiers changed from: protected */
    public abstract void setRadius(float f);

    /* JADX INFO: Access modifiers changed from: protected */
    public abstract void setTitleName(String str);

    /* JADX INFO: Access modifiers changed from: protected */
    public abstract void setTitleType(TitleType titleType);

    /* JADX INFO: Access modifiers changed from: protected */
    public abstract void showTagView();

    /* JADX INFO: Access modifiers changed from: protected */
    public abstract void showTimeView();

    public BaseSquareCardView(Context context) {
        super(context);
    }

    public BaseSquareCardView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public BaseSquareCardView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }
}
