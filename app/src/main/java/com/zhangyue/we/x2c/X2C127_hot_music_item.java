package com.zhangyue.we.x2c;

import android.content.Context;
import android.view.View;
import com.zhangyue.we.x2c.layouts.X2C127_Hot_Music_Item;

/* loaded from: classes4.dex */
public class X2C127_hot_music_item implements IViewCreator {
    @Override // com.zhangyue.we.x2c.IViewCreator
    public View createView(Context context) {
        return new X2C127_Hot_Music_Item().createView(context);
    }
}
