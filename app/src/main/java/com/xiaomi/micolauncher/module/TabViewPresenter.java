package com.xiaomi.micolauncher.module;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.elvishew.xlog.Logger;
import com.xiaomi.mico.base.ui.MicoAnimationTouchListener;
import com.xiaomi.mico.token.TokenManager;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.TimeUtils;
import com.xiaomi.micolauncher.common.base.BaseActivity;
import com.xiaomi.micolauncher.common.utils.UiUtils;
import com.xiaomi.micolauncher.module.homelock.SmartHomeLockFragment;
import com.xiaomi.micolauncher.module.homepage.TabConfigParser;
import com.xiaomi.micolauncher.module.homepage.fragment.AppHomeFragment;
import com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment;
import com.xiaomi.micolauncher.module.homepage.fragment.LauncherOverlayFragment;
import com.xiaomi.micolauncher.module.homepage.fragment.PhoneOverlayFragment;
import com.xiaomi.micolauncher.module.skill.manager.SkillDataManager;
import com.xiaomi.micolauncher.module.video.childmode.ChildModeManager;
import com.xiaomi.smarthome.core.miot.MicoMiotDeviceManager;
import com.xiaomi.smarthome.ui.SmartHomeFragment;

/* loaded from: classes3.dex */
public class TabViewPresenter implements View.OnClickListener {
    public static final String LAUNCH_TAB_POSITION = "LAUNCH_TAB_POSITION";
    public static final int TAB_DEFALUT_VALUE = -1;
    public static final int TAB_POSITION_0 = 0;
    private View b;
    private final int c;
    private final int d;
    private final SparseArray<TabConfigParser.ConfigInfo> e;
    private SmartHomeLockFragment g;
    private final View h;
    private final View i;
    private Fragment j;
    private boolean l;
    private final BaseActivity m;
    private final SparseArray<Fragment> f = new SparseArray<>();
    private int k = 0;
    private final SparseArray<View> a = new SparseArray<>(5);

    /* loaded from: classes3.dex */
    public interface OnRefreshDataListener {
        void onRefresh();
    }

    public TabViewPresenter(BaseActivity baseActivity, View view, SparseArray<TabConfigParser.ConfigInfo> sparseArray) {
        this.m = baseActivity;
        this.h = view;
        this.i = view.findViewById(R.id.tabView);
        this.c = UiUtils.getSize(this.i.getContext(), R.dimen.layout_tab_height_adult);
        this.d = UiUtils.getSize(this.i.getContext(), R.dimen.layout_tab_height_child);
        this.e = sparseArray;
        a();
    }

    public void a() {
        SkillDataManager.getManager().setAppPageData(null);
        this.l = ChildModeManager.getManager().isChildMode();
        this.k = 0;
        c();
        a(this.i);
        b();
        L.homepage.i("currentPosition is %d", Integer.valueOf(this.k));
        switchSelectedTabByPosition(this.k);
    }

    private void a(View view) {
        int i;
        int i2;
        int i3;
        int i4;
        BaseActivity baseActivity;
        int i5;
        BaseActivity baseActivity2;
        int i6;
        BaseActivity baseActivity3;
        int i7;
        BaseActivity baseActivity4;
        int i8;
        this.a.clear();
        Resources resources = view.getResources();
        if (this.l) {
            i = resources.getDimensionPixelSize(R.dimen.child_tab_margin_right);
            i2 = 0;
        } else {
            i2 = resources.getDimensionPixelSize(R.dimen.mc20dp);
            i = 0;
        }
        ImageView imageView = (ImageView) view.findViewById(R.id.ivTab1);
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) imageView.getLayoutParams();
        marginLayoutParams.height = this.l ? this.d : this.c;
        if (this.l) {
            i3 = resources.getDimensionPixelSize(R.dimen.home_tab_last_icon_padding_top_child);
        } else {
            i3 = resources.getDimensionPixelSize(R.dimen.home_tab_last_icon_padding_top);
        }
        marginLayoutParams.topMargin = i3;
        marginLayoutParams.leftMargin = i2;
        marginLayoutParams.rightMargin = i;
        imageView.setLayoutParams(marginLayoutParams);
        imageView.setImageResource(this.e.get(0).getIconId());
        if (this.l) {
            baseActivity = this.m;
            i4 = R.string.child_home_tab_video;
        } else {
            baseActivity = this.m;
            i4 = R.string.home_tab_smart_home;
        }
        imageView.setContentDescription(baseActivity.getString(i4));
        imageView.setScaleType(this.l ? ImageView.ScaleType.FIT_CENTER : ImageView.ScaleType.CENTER);
        a(imageView, 0);
        ImageView imageView2 = (ImageView) view.findViewById(R.id.ivTab2);
        ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) imageView2.getLayoutParams();
        marginLayoutParams2.height = this.l ? this.d : this.c;
        marginLayoutParams2.leftMargin = i2;
        marginLayoutParams2.rightMargin = i;
        imageView2.setLayoutParams(marginLayoutParams2);
        imageView2.setImageResource(this.e.get(1).getIconId());
        if (this.l) {
            baseActivity2 = this.m;
            i5 = R.string.child_home_tab_music;
        } else {
            baseActivity2 = this.m;
            i5 = R.string.home_tab_communication;
        }
        imageView2.setContentDescription(baseActivity2.getString(i5));
        imageView2.setScaleType(this.l ? ImageView.ScaleType.FIT_CENTER : ImageView.ScaleType.CENTER);
        a(imageView2, 1);
        ImageView imageView3 = (ImageView) view.findViewById(R.id.ivTab3);
        ViewGroup.MarginLayoutParams marginLayoutParams3 = (ViewGroup.MarginLayoutParams) imageView3.getLayoutParams();
        marginLayoutParams3.height = this.l ? this.d : this.c;
        marginLayoutParams3.leftMargin = i2;
        marginLayoutParams3.rightMargin = i;
        imageView3.setLayoutParams(marginLayoutParams3);
        imageView3.setImageResource(this.e.get(2).getIconId());
        if (this.l) {
            baseActivity3 = this.m;
            i6 = R.string.child_home_tab_story;
        } else {
            baseActivity3 = this.m;
            i6 = R.string.home_tab_entertainment;
        }
        imageView3.setContentDescription(baseActivity3.getString(i6));
        imageView3.setScaleType(this.l ? ImageView.ScaleType.FIT_CENTER : ImageView.ScaleType.CENTER);
        a(imageView3, 2);
        ImageView imageView4 = (ImageView) view.findViewById(R.id.ivTab4);
        ViewGroup.MarginLayoutParams marginLayoutParams4 = (ViewGroup.MarginLayoutParams) imageView4.getLayoutParams();
        marginLayoutParams4.height = this.l ? this.d : this.c;
        marginLayoutParams4.leftMargin = i2;
        marginLayoutParams4.bottomMargin = this.l ? 0 : resources.getDimensionPixelSize(R.dimen.home_tab_last_icon_padding_bottom);
        marginLayoutParams4.rightMargin = i;
        imageView4.setLayoutParams(marginLayoutParams4);
        imageView4.setImageResource(this.e.get(3).getIconId());
        if (this.l) {
            baseActivity4 = this.m;
            i7 = R.string.child_home_tab_course;
        } else {
            baseActivity4 = this.m;
            i7 = R.string.home_tab_app;
        }
        imageView4.setContentDescription(baseActivity4.getString(i7));
        imageView4.setScaleType(this.l ? ImageView.ScaleType.FIT_CENTER : ImageView.ScaleType.CENTER);
        a(imageView4, 3);
        ImageView imageView5 = (ImageView) view.findViewById(R.id.ivTab5);
        ViewGroup.MarginLayoutParams marginLayoutParams5 = (ViewGroup.MarginLayoutParams) imageView5.getLayoutParams();
        if (this.l) {
            imageView5.setVisibility(0);
            if (this.l) {
                i8 = resources.getDimensionPixelSize(R.dimen.home_tab_last_icon_padding_bottom_child);
            } else {
                i8 = resources.getDimensionPixelSize(R.dimen.home_tab_last_icon_padding_bottom);
            }
            marginLayoutParams5.bottomMargin = i8;
            marginLayoutParams5.height = this.l ? this.d : this.c;
            marginLayoutParams5.leftMargin = i2;
            marginLayoutParams5.rightMargin = i;
            imageView5.setLayoutParams(marginLayoutParams5);
            imageView5.setImageResource(this.e.get(4).getIconId());
            a(imageView5, 4);
            return;
        }
        imageView5.setVisibility(8);
    }

    @SuppressLint({"ClickableViewAccessibility"})
    private void a(ImageView imageView, int i) {
        this.a.put(i, imageView);
        imageView.setOnClickListener(this);
        imageView.setOnTouchListener(MicoAnimationTouchListener.getInstance());
        imageView.setTag(Integer.valueOf(i));
    }

    private void b() {
        L.homepage.i("createFragments fragmentSparseArray size  %d", Integer.valueOf(this.e.size()));
        for (int i = 0; i < this.e.size(); i++) {
            Fragment fragment = this.f.get(i);
            String tag = this.e.get(i).getTag();
            if (fragment == null || ((fragment instanceof BaseHomeFragment) && !tag.equals(((BaseHomeFragment) fragment).identifier()))) {
                a(i, tag);
            }
        }
        FragmentTransaction beginTransaction = this.m.getSupportFragmentManager().beginTransaction();
        for (int i2 = 1; i2 < this.e.size(); i2++) {
            Fragment fragment2 = this.f.get(i2);
            if (fragment2 instanceof PhoneOverlayFragment) {
                beginTransaction.add(R.id.fragment_container, fragment2, ((PhoneOverlayFragment) fragment2).identifier());
                beginTransaction.hide(fragment2);
            }
        }
        beginTransaction.commitAllowingStateLoss();
    }

    private void a(int i, String str) {
        L.homepage.d("MainPageBigScreen createFragment : %d tag=%s", Integer.valueOf(i), str);
        Fragment instantiate = Fragment.instantiate(this.h.getContext(), this.e.get(i).getName());
        if (instantiate instanceof BaseHomeFragment) {
            ((BaseHomeFragment) instantiate).init(this.h.getContext());
        }
        this.f.put(i, instantiate);
        L.homepage.i("created fragment %s, pos %s,tag=%s", instantiate, Integer.valueOf(i), str);
    }

    private void a(int i) {
        L.homepage.d("MainPageBigScreen showFragment : %d", Integer.valueOf(i));
        FragmentManager supportFragmentManager = this.m.getSupportFragmentManager();
        FragmentTransaction beginTransaction = supportFragmentManager.beginTransaction();
        beginTransaction.setTransition(0);
        Fragment fragment = this.f.get(i);
        String tag = this.e.get(i).getTag();
        if (i != 0) {
            SmartHomeLockControl.getIns().setNeedShowLock(true);
        } else if (SmartHomeLockControl.getIns().isNeedShowLock()) {
            if (this.g == null) {
                this.g = new SmartHomeLockFragment();
            }
            fragment = this.g;
        }
        if (fragment == null || ((fragment instanceof BaseHomeFragment) && !tag.equals(((BaseHomeFragment) fragment).identifier()))) {
            L.homepage.i("position=%d fragment=%s tag=%s", Integer.valueOf(i), fragment, tag);
            a(i, tag);
            fragment = this.f.get(i);
        }
        if (fragment == this.j && fragment != null) {
            L.homepage.e("Error : repeated fragment %s %s, already added ? %s", Integer.valueOf(i), fragment, Boolean.valueOf(fragment.isAdded()));
        }
        if (this.j != null) {
            L.homepage.d("hide  currentFragment %s is isResumed", this.j);
            beginTransaction.hide(this.j);
        }
        if (fragment.isAdded()) {
            beginTransaction.show(fragment);
        } else {
            String str = "";
            if (fragment instanceof BaseHomeFragment) {
                str = ((BaseHomeFragment) fragment).identifier();
            } else if (fragment instanceof LauncherOverlayFragment) {
                str = ((LauncherOverlayFragment) fragment).identifier();
            } else if (fragment instanceof SmartHomeFragment) {
                str = SmartHomeFragment.TAG;
            } else if (fragment instanceof SmartHomeLockFragment) {
                str = SmartHomeLockFragment.TAG;
            }
            Fragment findFragmentByTag = supportFragmentManager.findFragmentByTag(str);
            if (findFragmentByTag != null) {
                beginTransaction.remove(findFragmentByTag);
            }
            beginTransaction.add(R.id.fragment_container, fragment, str);
        }
        this.j = fragment;
        if (!this.l && (this.j instanceof SmartHomeFragment)) {
            MicoMiotDeviceManager.getInstance().setUserId(TokenManager.getInstance().getUserId());
        }
        try {
            beginTransaction.commitNowAllowingStateLoss();
        } catch (IllegalStateException e) {
            e.printStackTrace();
            beginTransaction.commitAllowingStateLoss();
            L.homepage.e(e);
        }
    }

    private void c() {
        if (this.f.size() != 0) {
            FragmentTransaction beginTransaction = this.m.getSupportFragmentManager().beginTransaction();
            for (int i = 0; i < this.e.size(); i++) {
                Fragment fragment = this.f.get(i);
                if (fragment != null) {
                    unregisterFromEventBusIfNeed(fragment);
                    beginTransaction.remove(fragment);
                }
            }
            beginTransaction.commitNowAllowingStateLoss();
            this.f.clear();
        }
    }

    protected void unregisterFromEventBusIfNeed(Fragment fragment) {
        if (EventBusRegistry.getEventBus().isRegistered(fragment)) {
            EventBusRegistry.getEventBus().unregister(fragment);
        }
    }

    public void clearCache() {
        FragmentTransaction beginTransaction = this.m.getSupportFragmentManager().beginTransaction();
        for (int i = 0; i < this.e.size(); i++) {
            Fragment fragment = this.f.get(i);
            if (!(fragment == null || i == this.k)) {
                this.f.remove(i);
                unregisterFromEventBusIfNeed(fragment);
                if (fragment.isAdded() && fragment.isHidden()) {
                    beginTransaction.remove(fragment);
                }
            }
        }
        beginTransaction.commitNowAllowingStateLoss();
    }

    public void dispose() {
        for (int i = 0; i < this.f.size(); i++) {
            Fragment valueAt = this.f.valueAt(i);
            if (valueAt != null) {
                unregisterFromEventBusIfNeed(valueAt);
            }
        }
    }

    public void loadFragmentFromManager() {
        for (int i = 0; i < this.e.size(); i++) {
            Fragment findFragmentByTag = this.m.getSupportFragmentManager().findFragmentByTag(this.e.get(i).getTag());
            L.homepage.d("MainPageBigScreen onCreate savedInstanceState fragment is : %s, tag is : %s", findFragmentByTag, this.e.get(i).getTag());
            if (findFragmentByTag != null) {
                if (findFragmentByTag instanceof BaseHomeFragment) {
                    ((BaseHomeFragment) findFragmentByTag).init(this.m);
                }
                this.f.put(i, findFragmentByTag);
            }
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        b(view);
    }

    public void showTab(int i) {
        if (i >= 0 && i < this.a.size()) {
            b(this.a.get(i));
        }
    }

    private void b(View view) {
        this.k = ((Integer) view.getTag()).intValue();
        View view2 = this.b;
        if (view2 != null) {
            view2.setSelected(false);
        }
        this.b = view;
        view.setSelected(true);
        a(this.k);
    }

    public void switchSelectedTabByPosition(int i) {
        b((ImageView) this.a.get(i));
    }

    public void timeTick() {
        BaseHomeFragment baseHomeFragment;
        if (TimeUtils.isMidNight()) {
            for (int i = 0; i < this.f.size(); i++) {
                Fragment fragment = this.f.get(i);
                if ((fragment instanceof BaseHomeFragment) && (baseHomeFragment = (BaseHomeFragment) fragment) != null && baseHomeFragment.isAdded()) {
                    baseHomeFragment.onTimeTickUpdate();
                }
            }
        }
    }

    public void setSmartHomeVisibility(boolean z) {
        Fragment fragment = this.j;
        if (fragment instanceof SmartHomeFragment) {
            fragment.setUserVisibleHint(z);
        }
    }

    public void backOutEditStatus() {
        Fragment fragment = this.j;
        if (fragment instanceof AppHomeFragment) {
            ((AppHomeFragment) fragment).backOutEditStatus();
        }
    }

    public void saveCurrentPositionToBundle(Bundle bundle) {
        Logger logger = L.homepage;
        logger.d("MainPageBigScreen onSaveInstanceState : " + this + ",position : " + this.k);
        bundle.putInt("current_position", this.k);
    }

    public void loadCurrentPositionFromBundle(Bundle bundle) {
        this.k = bundle.getInt("current_position", 0);
        L.homepage.d("MainPageBigScreen onCreate savedInstanceState position is : %d", Integer.valueOf(this.k));
    }

    public void switchSelectedTabByIntent(Intent intent) {
        switchSelectedTabByPosition(intent.getIntExtra(LAUNCH_TAB_POSITION, 0));
    }

    public void onNewIntent(Intent intent) {
        Fragment fragment = this.j;
        if (fragment instanceof LauncherOverlayFragment) {
            ((LauncherOverlayFragment) fragment).onNewIntent(intent);
        }
    }

    public void onResume() {
        if (this.k != 0) {
            return;
        }
        if (SmartHomeLockControl.getIns().isNeedShowLock()) {
            showTab(0);
        } else if (this.j instanceof SmartHomeLockFragment) {
            showTab(0);
        }
    }
}
