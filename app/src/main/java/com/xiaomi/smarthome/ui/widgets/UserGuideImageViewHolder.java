package com.xiaomi.smarthome.ui.widgets;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import androidx.annotation.DrawableRes;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.ObjectKey;
import com.xiaomi.micolauncher.common.view.BaseViewHolder;
import com.xiaomi.smarthome.R;

/* loaded from: classes4.dex */
public class UserGuideImageViewHolder extends BaseViewHolder {
    ImageView a;

    public UserGuideImageViewHolder(View view) {
        super(view);
        this.a = (ImageView) view.findViewById(R.id.userGuideImage);
    }

    public void bind(@DrawableRes int i) {
        Context context = this.a.getContext();
        Glide.with(context).load(Integer.valueOf(i)).diskCacheStrategy(DiskCacheStrategy.NONE).format(DecodeFormat.PREFER_RGB_565).signature(new ObjectKey(Integer.valueOf(context.getResources().getConfiguration().uiMode & 48))).priority(Priority.IMMEDIATE).into(this.a);
    }
}
