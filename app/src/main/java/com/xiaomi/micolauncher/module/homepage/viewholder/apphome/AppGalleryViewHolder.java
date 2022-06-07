package com.xiaomi.micolauncher.module.homepage.viewholder.apphome;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import com.blankj.utilcode.util.SizeUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.xiaomi.mico.appstore.bean.AppInfo;
import com.xiaomi.mico.base.ui.RxViewHelp;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.GlideUtils;
import com.xiaomi.micolauncher.common.ubus.storage.LocalAlbumStorage;
import com.xiaomi.micolauncher.common.ui.MicoAnimConfigurator4EntertainmentAndApps;
import com.xiaomi.micolauncher.common.view.AnimLifecycleManager;
import com.xiaomi.micolauncher.module.homepage.record.AppRecordHelper;
import io.reactivex.functions.Consumer;
import java.io.File;

/* loaded from: classes3.dex */
public class AppGalleryViewHolder extends BaseAppHolder {
    private final ImageView a;
    private String b;
    private Drawable c;

    @SuppressLint({"CheckResult"})
    public AppGalleryViewHolder(@NonNull View view) {
        super(view, false);
        this.a = (ImageView) view.findViewById(R.id.ivPic);
    }

    @Override // com.xiaomi.micolauncher.common.view.BaseViewHolder
    @SuppressLint({"CheckResult"})
    public void initInMain() {
        super.initInMain();
        AnimLifecycleManager.repeatOnAttach(this.itemView, MicoAnimConfigurator4EntertainmentAndApps.get());
        RxViewHelp.debounceClicksWithOneSeconds(this.itemView).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.homepage.viewholder.apphome.-$$Lambda$AppGalleryViewHolder$OZqGFa88osXlq2hq1MGf8YSdM5U
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                AppGalleryViewHolder.this.a(obj);
            }
        });
    }

    public /* synthetic */ void a(Object obj) throws Exception {
        executeAction();
        AppRecordHelper.recordAppClickByMi(this.tabName, this.appInfo.getAppName());
    }

    @Override // com.xiaomi.micolauncher.module.homepage.viewholder.apphome.BaseAppHolder
    public void bindAppInfo(AppInfo appInfo, String str) {
        super.bindAppInfo(appInfo, str);
        refresh();
    }

    public void refresh() {
        if (!LocalAlbumStorage.getInstance().getDownloadedFiles().isEmpty()) {
            File file = LocalAlbumStorage.getInstance().getDownloadedFiles().get(0);
            if (!TextUtils.equals(this.b, file.getPath())) {
                Glide.with(this.a).load(file).format(DecodeFormat.PREFER_ARGB_8888).apply((BaseRequestOptions<?>) new RequestOptions().transforms(new CenterCrop(), new RoundedCorners(SizeUtils.dp2px(30.0f)))).placeholder(R.drawable.ic_icon_gallery_skill_empty).error(R.drawable.ic_icon_gallery_skill_empty).into(this.a);
                this.b = file.getPath();
            }
        } else if (!TextUtils.equals("empty", this.b)) {
            if (this.c == null) {
                this.c = ContextCompat.getDrawable(this.context, R.drawable.ic_icon_gallery_skill_empty);
            }
            GlideUtils.bindImageView(this.a, this.c, SizeUtils.dp2px(30.0f));
            this.b = "empty";
        }
    }
}
