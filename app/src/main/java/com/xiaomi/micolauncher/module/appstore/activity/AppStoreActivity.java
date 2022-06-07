package com.xiaomi.micolauncher.module.appstore.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.mico.appstore.bean.AppInfo;
import com.xiaomi.mico.base.ui.MicoAnimationTouchListener;
import com.xiaomi.mico.base.ui.RxViewHelp;
import com.xiaomi.mico.base.utils.CommonUtils;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.GlideUtils;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.base.BaseActivity;
import com.xiaomi.micolauncher.common.event.AppStoreListChange;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.common.schema.SchemaManager;
import com.xiaomi.micolauncher.module.appstore.activity.AppStoreActivity;
import com.xiaomi.micolauncher.module.appstore.manager.AppStoreApi;
import com.xiaomi.micolauncher.module.appstore.manager.AppStoreManager;
import com.xiaomi.micolauncher.skills.video.model.ThirdPartyAppProxy;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes3.dex */
public class AppStoreActivity extends BaseActivity {
    private RecyclerView a;
    private a b;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_app_store);
        this.a = (RecyclerView) findViewById(R.id.app_list);
        this.b = new a(this);
        this.a.setLayoutManager(new GridLayoutManager((Context) this, 3, 0, false));
        this.a.setAdapter(this.b);
        a();
        setDefaultScheduleDuration();
    }

    private void a() {
        AppStoreManager.getManager().getAppInfoList().subscribeOn(MicoSchedulers.io()).compose(bindToLifecycle()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.appstore.activity.-$$Lambda$AppStoreActivity$NkN_B0ojl-m_NiSBsYYmU9X99JY
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                AppStoreActivity.this.a((List) obj);
            }
        }, $$Lambda$AppStoreActivity$An11NlpbW6p2lKdFVX9BO86pRCc.INSTANCE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(List list) throws Exception {
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            AppInfo appInfo = (AppInfo) it.next();
            if (!appInfo.isHidden()) {
                if (appInfo.isOnline()) {
                    arrayList.add(appInfo);
                } else if (CommonUtils.isPackageInstalled(this, appInfo.getPackageName())) {
                    arrayList.add(appInfo);
                }
            }
        }
        this.b.a(arrayList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Throwable th) throws Exception {
        L.storage.e("AppStoreActivity loadData", th);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public static class a extends RecyclerView.Adapter<b> {
        private List<AppInfo> a;
        private Context b;

        public a(Context context) {
            this.b = context;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(List<AppInfo> list) {
            this.a = list;
            notifyDataSetChanged();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            List<AppInfo> list = this.a;
            if (list == null) {
                return 0;
            }
            return list.size();
        }

        @NonNull
        /* renamed from: a */
        public b onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new b(LayoutInflater.from(this.b).inflate(R.layout.activity_app_store_item, viewGroup, false));
        }

        /* renamed from: a */
        public void onBindViewHolder(@NonNull b bVar, int i) {
            final AppInfo appInfo = this.a.get(i);
            GlideUtils.bindImageViewWithDefault(this.b, appInfo.getIconUrl(), bVar.a, R.drawable.appcenter_placeholder);
            bVar.b.setText(appInfo.getAppName());
            RxViewHelp.debounceClicksWithOneSeconds(bVar.a).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.appstore.activity.-$$Lambda$AppStoreActivity$a$46h6-8MHQsI2j9PjFIyOP-880SE
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    AppStoreActivity.a.this.a(appInfo, obj);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void a(AppInfo appInfo, Object obj) throws Exception {
            if (TextUtils.equals(AppInfo.TYPE_THIRD_PARTY, appInfo.getAppType())) {
                final String packageName = appInfo.getPackageName();
                AppStoreApi.handleAppWithPkgName(this.b, packageName, new Runnable() { // from class: com.xiaomi.micolauncher.module.appstore.activity.-$$Lambda$AppStoreActivity$a$ddBSH5nPVVu8YRWDPVmeCCW6KUg
                    @Override // java.lang.Runnable
                    public final void run() {
                        AppStoreActivity.a.this.a(packageName);
                    }
                });
                return;
            }
            SchemaManager.handleSchema(this.b, appInfo.getMicoAction());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void a(String str) {
            ThirdPartyAppProxy.getInstance().startApp(this.b, str);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public static class b extends RecyclerView.ViewHolder {
        public ImageView a;
        public TextView b;

        public b(@NonNull View view) {
            super(view);
            this.a = (ImageView) view.findViewById(R.id.icon_iv);
            this.b = (TextView) view.findViewById(R.id.name_tv);
            this.a.setOnTouchListener(MicoAnimationTouchListener.getInstance());
        }
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseActivity
    @NonNull
    public BaseActivity.EventBusRegisterMode getEventBusRegisterMode() {
        return BaseActivity.EventBusRegisterMode.WHOLE_LIFE;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAppStoreListChanged(AppStoreListChange appStoreListChange) {
        a();
    }
}
