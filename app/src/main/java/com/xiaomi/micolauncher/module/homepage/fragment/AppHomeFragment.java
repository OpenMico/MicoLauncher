package com.xiaomi.micolauncher.module.homepage.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.mico.appstore.bean.AppInfo;
import com.xiaomi.mico.base.ui.RxViewHelp;
import com.xiaomi.mico.base.utils.ToastUtil;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.settingslib.core.BigSettings;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.mico.utils.Threads;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.event.AppStoreUninstallEvent;
import com.xiaomi.micolauncher.common.event.LocalAlbumFilesUpdated;
import com.xiaomi.micolauncher.common.ui.MicoAnimConfigurator4SmallButton;
import com.xiaomi.micolauncher.common.utils.UiUtils;
import com.xiaomi.micolauncher.common.view.AnimLifecycleManager;
import com.xiaomi.micolauncher.common.widget.HorizontalRecyclerView;
import com.xiaomi.micolauncher.module.homepage.ItemTouchCallBack;
import com.xiaomi.micolauncher.module.homepage.adapter.AllAppAdapter;
import com.xiaomi.micolauncher.module.homepage.adapter.AppAdapter;
import com.xiaomi.micolauncher.module.homepage.adapter.BaseAdapter;
import com.xiaomi.micolauncher.module.homepage.adapter.CategoryTabAdapter;
import com.xiaomi.micolauncher.module.homepage.adapter.ChildAllAppAdapter;
import com.xiaomi.micolauncher.module.homepage.bean.AppPageData;
import com.xiaomi.micolauncher.module.homepage.bean.CategoryTab;
import com.xiaomi.micolauncher.module.homepage.bean.RecommendCard;
import com.xiaomi.micolauncher.module.homepage.event.AppInstalledEvent;
import com.xiaomi.micolauncher.module.homepage.event.AppsDataLoadSuccess;
import com.xiaomi.micolauncher.module.homepage.event.DeleteAppEvent;
import com.xiaomi.micolauncher.module.homepage.event.ResetScrollViewPositionEvent;
import com.xiaomi.micolauncher.module.homepage.record.AppRecordHelper;
import com.xiaomi.micolauncher.module.homepage.view.AppSkillHolderCacheManager;
import com.xiaomi.micolauncher.module.homepage.viewholder.CategoryViewHolder;
import com.xiaomi.micolauncher.module.recommend.RecommendEvent;
import com.xiaomi.micolauncher.module.setting.SettingOpenManager;
import com.xiaomi.micolauncher.module.skill.manager.SkillDataManager;
import com.xiaomi.micolauncher.module.video.childmode.ChildModeManager;
import com.xiaomi.micolauncher.skills.alarm.controller.uievent.AlarmCreationPromptEvent;
import com.xiaomi.micolauncher.skills.alarm.controller.uievent.AlarmModifyEvent;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes3.dex */
public class AppHomeFragment extends BaseHomeFragment implements CategoryViewHolder.TabClickListener {
    public static final String TAG = "AppHomeFragment";
    private HorizontalRecyclerView a;
    private TextView b;
    private ImageView c;
    private ConstraintLayout d;
    private boolean e;
    private AppAdapter f;
    private AllAppAdapter g;
    private ChildAllAppAdapter h;
    private AppPageData i;
    private ItemTouchHelper j;
    private CategoryTab k;
    private int l;
    private int m;
    private int n;
    private CategoryTabAdapter<CategoryTab> o;
    private boolean p;
    private boolean q;
    private final Runnable r = new Runnable() { // from class: com.xiaomi.micolauncher.module.homepage.fragment.-$$Lambda$AppHomeFragment$s9xs_uNhhsre4zEXSWDH44CWAlQ
        @Override // java.lang.Runnable
        public final void run() {
            AppHomeFragment.this.g();
        }
    };

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    protected BaseAdapter<? extends RecyclerView.ViewHolder> createAdapter() {
        return null;
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    protected void getArgumentsData() {
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    protected int itemViewCacheSize() {
        return 4;
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    protected void recordShow() {
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    public void init(Context context) {
        super.init(context);
        registerToEventBusIfNot();
        this.l = context.getResources().getDimensionPixelSize(R.dimen.app_item_margin);
        this.m = context.getResources().getDimensionPixelSize(R.dimen.rec_app_item_margin);
        this.n = context.getResources().getDimensionPixelSize(R.dimen.app_card_weather_margin_top);
        this.p = ChildModeManager.getManager().isChildMode();
        SkillDataManager.getManager().loadAppsDataFromCache(context);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void deleteApp(DeleteAppEvent deleteAppEvent) {
        int i = deleteAppEvent.position;
        L.skillpage.i("delete app position : %d", Integer.valueOf(i));
        if (a()) {
            int itemCount = this.f.getItemCount() - i;
            if (this.f.removeAppData(i)) {
                this.f.notifyItemRangeChanged(i, itemCount);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void uninstallApp(AppStoreUninstallEvent appStoreUninstallEvent) {
        if (a()) {
            int i = appStoreUninstallEvent.position;
            int itemCount = this.f.getItemCount() - i;
            if (this.f.removeAppData(i)) {
                this.f.notifyItemRangeChanged(i, itemCount);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void installedApp(AppInstalledEvent appInstalledEvent) {
        AppInfo appInfo = appInstalledEvent.appInfo;
        if (a() && appInfo != null) {
            this.f.addAppData(appInfo);
            AppAdapter appAdapter = this.f;
            appAdapter.notifyItemRangeChanged(appAdapter.getItemCount() - 1, 1);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void resetRecyclerViewPosition(ResetScrollViewPositionEvent resetScrollViewPositionEvent) {
        if (!this.p && resetScrollViewPositionEvent.getEventType() == 1 && this.recyclerView != null) {
            this.recyclerView.scrollToPosition(0);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void loadAppsDataSuccess(AppsDataLoadSuccess appsDataLoadSuccess) {
        ConstraintLayout constraintLayout;
        if (isAdded() && appsDataLoadSuccess != null) {
            this.i = SkillDataManager.getManager().getAppPageData();
            AppPageData appPageData = this.i;
            if (appPageData != null && !ContainerUtil.isEmpty(appPageData.getTabList()) && (constraintLayout = this.d) != null) {
                constraintLayout.setVisibility(8);
                this.recyclerView.setFocusable(true);
                this.k = this.i.getTabList().get(0);
                this.o.updateTabList(this.i.getTabList());
                a(this.k);
                Observable.create(new ObservableOnSubscribe() { // from class: com.xiaomi.micolauncher.module.homepage.fragment.-$$Lambda$AppHomeFragment$s40Vcbix3H_yt41mDQRE3z8Ye8g
                    @Override // io.reactivex.ObservableOnSubscribe
                    public final void subscribe(ObservableEmitter observableEmitter) {
                        AppHomeFragment.this.a(observableEmitter);
                    }
                }).subscribeOn(Schedulers.io()).subscribe();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(ObservableEmitter observableEmitter) throws Exception {
        AppRecordHelper.recordAppShowByMi(this.k.getAppTabName());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAlbumChanged(LocalAlbumFilesUpdated localAlbumFilesUpdated) {
        if (a()) {
            this.f.notifyItemRangeChanged(1, 1);
        }
    }

    private boolean a() {
        CategoryTab categoryTab;
        if (!isAdded() || (categoryTab = this.k) == null || this.f == null) {
            return false;
        }
        return SkillDataManager.TAB_KEY_MINE.equals(categoryTab.getAppTabKey());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAlarmCreation(AlarmCreationPromptEvent alarmCreationPromptEvent) {
        if (a()) {
            this.f.notifyItemRangeChanged(2, 1);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAlarmModify(AlarmModifyEvent alarmModifyEvent) {
        if (a()) {
            this.f.notifyItemRangeChanged(2, 1);
        }
    }

    private void a(CategoryTab categoryTab) {
        if (categoryTab.isConfigurable()) {
            d(categoryTab);
        } else {
            e(categoryTab);
        }
    }

    private void b(CategoryTab categoryTab) {
        this.h.setDataList(SkillDataManager.getManager().getRecommendCards(categoryTab.getAppTabKey()), this.k.getAppTabName());
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this.context, 0, false) { // from class: com.xiaomi.micolauncher.module.homepage.fragment.AppHomeFragment.1
            @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
            public boolean supportsPredictiveItemAnimations() {
                return false;
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // androidx.recyclerview.widget.LinearLayoutManager
            public int getExtraLayoutSpace(RecyclerView.State state) {
                return super.getExtraLayoutSpace(state);
            }

            @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
            public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
                try {
                    super.onLayoutChildren(recycler, state);
                } catch (IndexOutOfBoundsException e) {
                    L.homepage.e("setChildCardUI error happen %s", e);
                }
            }
        });
        this.recyclerView.setPadding(UiUtils.getSize(this.context, R.dimen.card_padding_start), 0, 0, 0);
        this.recyclerView.setAdapter(this.h);
    }

    private void c(CategoryTab categoryTab) {
        this.g.setDataList(SkillDataManager.getManager().getRecommendCards(categoryTab.getAppTabKey()), this.k.getAppTabName());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.context, 3, 0, false) { // from class: com.xiaomi.micolauncher.module.homepage.fragment.AppHomeFragment.2
            @Override // androidx.recyclerview.widget.GridLayoutManager, androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
            public boolean supportsPredictiveItemAnimations() {
                return false;
            }

            @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
            public boolean canScrollHorizontally() {
                return AppHomeFragment.this.recyclerView.isFocusable();
            }

            @Override // androidx.recyclerview.widget.GridLayoutManager, androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
            public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
                try {
                    super.onLayoutChildren(recycler, state);
                } catch (IndexOutOfBoundsException e) {
                    L.homepage.e("setCarUI error happen %s", e);
                }
            }
        };
        this.recyclerView.setLayoutManager(gridLayoutManager);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() { // from class: com.xiaomi.micolauncher.module.homepage.fragment.AppHomeFragment.3
            @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
            public int getSpanSize(int i) {
                List<RecommendCard> recommendCards = AppHomeFragment.this.g.getRecommendCards();
                if (!ContainerUtil.hasData(recommendCards) || recommendCards.size() <= i) {
                    return 1;
                }
                switch (recommendCards.get(i).getCardType()) {
                    case 1:
                    case 2:
                    case 3:
                        return 3;
                    default:
                        return 1;
                }
            }
        });
        this.recyclerView.setPadding(UiUtils.getSize(this.context, R.dimen.app_home_padding_start), 0, 0, UiUtils.getSize(this.context, R.dimen.tab_fragment_content_bottom_padding));
        this.recyclerView.setAdapter(this.g);
    }

    private void d(CategoryTab categoryTab) {
        if (this.p) {
            b(categoryTab);
        } else {
            c(categoryTab);
        }
    }

    private void e(final CategoryTab categoryTab) {
        this.e = false;
        this.f.setManaged(this.e);
        if (SkillDataManager.TAB_KEY_MINE.equals(categoryTab.getAppTabKey())) {
            a(0);
            e();
        } else {
            a(8);
        }
        String tag = categoryTab.getTag();
        if (ContainerUtil.isEmpty(tag)) {
            tag = categoryTab.getAppTabKey();
        }
        List<AppInfo> appInfosByTabKey = SkillDataManager.getManager().getAppInfosByTabKey(tag);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.context, 3, 0, false) { // from class: com.xiaomi.micolauncher.module.homepage.fragment.AppHomeFragment.4
            @Override // androidx.recyclerview.widget.GridLayoutManager, androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
            public boolean supportsPredictiveItemAnimations() {
                return false;
            }

            @Override // androidx.recyclerview.widget.GridLayoutManager, androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
            public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
                try {
                    super.onLayoutChildren(recycler, state);
                } catch (IndexOutOfBoundsException e) {
                    L.homepage.e("showListUi error happen %s,tab %s", e, categoryTab);
                }
            }
        };
        if (!this.p && SkillDataManager.TAB_KEY_MINE.equals(categoryTab.getAppTabKey())) {
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() { // from class: com.xiaomi.micolauncher.module.homepage.fragment.AppHomeFragment.5
                @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
                public int getSpanSize(int i) {
                    List<AppInfo> appList = AppHomeFragment.this.f.getAppList();
                    if (ContainerUtil.hasData(appList)) {
                        AppInfo appInfo = appList.get(i);
                        if (SkillDataManager.APP_WEATHER.equals(appInfo.getAppName())) {
                            return 3;
                        }
                        if (SkillDataManager.APP_GALLERY.equals(appInfo.getAppName())) {
                            return 2;
                        }
                        if (SkillDataManager.APP_ALARM.equals(appInfo.getAppName())) {
                            return 1;
                        }
                    }
                    return 1;
                }
            });
        }
        this.f.setDataList(appInfosByTabKey, this.k);
        if (this.p) {
            this.recyclerView.setPadding(UiUtils.getSize(this.context, R.dimen.child_list_ui_padding_start), UiUtils.getSize(this.context, R.dimen.child_list_ui_padding_top), 0, 0);
        } else {
            this.recyclerView.setPadding(UiUtils.getSize(this.context, R.dimen.list_ui_padding_start), 0, 0, UiUtils.getSize(this.context, R.dimen.tab_fragment_content_bottom_padding));
        }
        this.recyclerView.setLayoutManager(gridLayoutManager);
        this.recyclerView.setAdapter(this.f);
    }

    @Override // androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public void onLowMemory() {
        super.onLowMemory();
        L.homepage.i("onLowMemory appHomeFragment isHidden: %b,isOnResume: %b", Boolean.valueOf(isHidden()), Boolean.valueOf(isOnResume()));
        if (isHidden() || !isOnResume()) {
            this.q = true;
            return;
        }
        this.q = false;
        b();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onAllApiServiceInitFinished(RecommendEvent.ApiInited apiInited) {
        SkillDataManager.getManager().loadAppsData(this.context);
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    protected void loadData() {
        if (SkillDataManager.getManager().getAppPageData() != null) {
            Threads.postDelayedInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.module.homepage.fragment.-$$Lambda$AppHomeFragment$VNBwOIIJVemyhWtpC_UKO4HCGeg
                @Override // java.lang.Runnable
                public final void run() {
                    AppHomeFragment.this.h();
                }
            }, 300L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void h() {
        loadAppsDataSuccess(new AppsDataLoadSuccess());
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    protected int layoutId() {
        return this.p ? R.layout.child_app_center_layout : R.layout.app_center_layout;
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onHiddenChanged(boolean z) {
        super.onHiddenChanged(z);
        if (!z && this.i == null) {
            loadData();
        }
        CategoryTab categoryTab = this.k;
        if (categoryTab != null) {
            if (!z && SkillDataManager.TAB_KEY_MINE.equals(categoryTab.getAppTabKey())) {
                a(0);
                e();
            }
            if (this.q && !z) {
                b();
            }
        }
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        if (!isHidden()) {
            AppAdapter appAdapter = this.f;
            if (!(appAdapter == null || this.i == null)) {
                appAdapter.notifyItemRangeChanged(0, 3);
            }
            if (this.q) {
                b();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void g() {
        if (isDetached()) {
            this.q = false;
        } else if (isHidden() || !isOnResume()) {
            this.q = true;
        } else {
            this.q = false;
            if (this.i != null && this.recyclerView != null && this.recyclerView.getAdapter() != null) {
                this.recyclerView.getAdapter().notifyDataSetChanged();
            }
        }
    }

    private void b() {
        ThreadUtil.removeCallbacksInMainThread(this.r);
        ThreadUtil.postDelayedInMainThread(this.r, 1500L);
    }

    public void backOutEditStatus() {
        if (this.e) {
            this.e = false;
            this.f.setManaged(false);
            this.f.notifyDataSetChanged();
            e();
            d();
        }
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    @SuppressLint({"CheckResult", "ClickableViewAccessibility"})
    protected void initViews(View view) {
        this.a = (HorizontalRecyclerView) view.findViewById(R.id.category_recycler);
        this.b = (TextView) view.findViewById(R.id.app_manage_complete);
        AnimLifecycleManager.repeatOnAttach(this.b, MicoAnimConfigurator4SmallButton.get());
        RxViewHelp.debounceClicksWithOneSeconds(this.b).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.homepage.fragment.-$$Lambda$AppHomeFragment$0GIJ65_Mg9oipxM2pGhbBj-hOAU
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                AppHomeFragment.this.b(obj);
            }
        });
        this.d = (ConstraintLayout) view.findViewById(R.id.loading_layout);
        this.f = new AppAdapter(this.context);
        this.g = new AllAppAdapter(this.context);
        this.h = new ChildAllAppAdapter(this.context);
        if (!this.p) {
            this.c = (ImageView) view.findViewById(R.id.common_set);
            AnimLifecycleManager.repeatOnAttach(this.c, MicoAnimConfigurator4SmallButton.get());
            RxViewHelp.debounceClicksWithOneSeconds(this.c).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.homepage.fragment.-$$Lambda$AppHomeFragment$teMidaLS4PZfCRYe47Vg7iXEcjI
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    AppHomeFragment.this.a(obj);
                }
            });
            this.recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() { // from class: com.xiaomi.micolauncher.module.homepage.fragment.AppHomeFragment.6
                @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
                public void getItemOffsets(@NonNull Rect rect, @NonNull View view2, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.State state) {
                    super.getItemOffsets(rect, view2, recyclerView, state);
                    if (AppHomeFragment.this.k != null) {
                        if (SkillDataManager.TAB_KEY_REC.equals(AppHomeFragment.this.k.getAppTabKey())) {
                            rect.right = AppHomeFragment.this.m;
                            rect.top = AppHomeFragment.this.n;
                            return;
                        }
                        rect.right = AppHomeFragment.this.l;
                        rect.top = 0;
                    }
                }
            });
        }
        this.o = new CategoryTabAdapter<>(this.context, null, this);
        this.a.setAdapter(this.o);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(Object obj) throws Exception {
        clickManageTv();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Object obj) throws Exception {
        SettingOpenManager.openSetting(this.context, BigSettings.DEFAULT);
    }

    public void clickManageTv() {
        this.e = !this.e;
        if (this.e) {
            ToastUtil.showToast((int) R.string.app_manage_remind);
            f();
            c();
        } else {
            e();
            d();
        }
        this.f.setManaged(this.e);
        this.f.notifyDataSetChanged();
    }

    private void c() {
        if (this.j == null) {
            ItemTouchCallBack itemTouchCallBack = new ItemTouchCallBack();
            itemTouchCallBack.setOnItemTouchListener(this.f);
            this.j = new ItemTouchHelper(itemTouchCallBack);
            this.j.attachToRecyclerView(this.recyclerView);
        }
    }

    private void d() {
        if (this.j != null) {
            try {
                Method declaredMethod = ItemTouchHelper.class.getDeclaredMethod("e", new Class[0]);
                declaredMethod.setAccessible(true);
                declaredMethod.invoke(this.j, new Object[0]);
                this.j = null;
                L.skillpage.i("removeTouchHelper ");
            } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                L.skillpage.i("removeTouchHelper : ", e);
            }
        }
    }

    private void e() {
        if (this.p) {
            this.b.setBackgroundResource(R.drawable.app_manage_child_bg);
            this.b.setTextColor(this.context.getColor(R.color.white));
        } else {
            this.b.setBackgroundResource(R.drawable.app_button_normal_bg);
        }
        this.b.setText(R.string.app_manage);
    }

    private void f() {
        if (this.p) {
            this.b.setBackgroundResource(R.drawable.app_complete_child_bg);
            this.b.setTextColor(this.context.getColor(R.color.color_182582));
        } else {
            this.b.setBackgroundResource(R.drawable.app_button_blue_bg);
        }
        this.b.setText(R.string.app_complete);
    }

    private void a(int i) {
        TextView textView = this.b;
        if (textView != null && textView.getVisibility() != i) {
            this.b.clearAnimation();
            this.b.setVisibility(i);
        }
    }

    @Override // com.xiaomi.micolauncher.module.homepage.viewholder.CategoryViewHolder.TabClickListener
    public void onTabClick(int i, Object obj) {
        if (this.o.setHighlightPosition(i)) {
            this.k = (CategoryTab) obj;
            backOutEditStatus();
            a(this.k);
            a(SkillDataManager.TAB_KEY_MINE.equals(this.k.getAppTabKey()) ? 0 : 8);
            AppRecordHelper.recordAppShowByMi(this.k.getAppTabName());
        }
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        AppSkillHolderCacheManager.getManager().clearAllAppViewHolder();
    }
}
