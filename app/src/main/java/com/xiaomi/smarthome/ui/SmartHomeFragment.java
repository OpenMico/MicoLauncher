package com.xiaomi.smarthome.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.blankj.utilcode.util.DebouncingUtils;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.umeng.analytics.pro.c;
import com.xiaomi.mico.base.ui.RxViewHelp;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.mico.settingslib.core.MicoSettings;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.ui.MicoAnimConfigurator4SmallButton;
import com.xiaomi.micolauncher.common.view.AnimLifecycleManager;
import com.xiaomi.micolauncher.module.homepage.event.MainPageGotoSetEvent;
import com.xiaomi.smarthome.R;
import com.xiaomi.smarthome.base.BaseFragment;
import com.xiaomi.smarthome.core.miot.MicoMiotDeviceManager;
import com.xiaomi.smarthome.ui.model.MicoMode;
import com.xiaomi.smarthome.ui.model.SmartHomeStatHelper;
import io.reactivex.functions.Consumer;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: SmartHomeFragment.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u0000 \u001e2\u00020\u0001:\u0001\u001eB\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J$\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u0017J\b\u0010\u001a\u001a\u00020\u000fH\u0016J\b\u0010\u001b\u001a\u00020\u000fH\u0016J\b\u0010\u001c\u001a\u00020\u000fH\u0016J\b\u0010\u001d\u001a\u00020\u000fH\u0002R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u001e\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u0006@BX\u0082\u000e¢\u0006\b\n\u0000\"\u0004\b\b\u0010\tR\u000e\u0010\n\u001a\u00020\u000bX\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001f"}, d2 = {"Lcom/xiaomi/smarthome/ui/SmartHomeFragment;", "Lcom/xiaomi/smarthome/base/BaseFragment;", "()V", "categoryFragment", "Lcom/xiaomi/smarthome/ui/SmartHomeModeCategoryFragment;", com.xiaomi.onetrack.api.b.p, "Lcom/xiaomi/smarthome/ui/model/MicoMode;", "micoMode", "setMicoMode", "(Lcom/xiaomi/smarthome/ui/model/MicoMode;)V", "modeSwitcher", "Landroid/widget/TextView;", "roomFragment", "Lcom/xiaomi/smarthome/ui/SmartHomeModeRoomFragment;", "onAttach", "", c.R, "Landroid/content/Context;", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onDestroyView", "onDetach", "onResume", "toggleMode", "Companion", "smarthome_release"}, k = 1, mv = {1, 4, 2})
/* loaded from: classes4.dex */
public final class SmartHomeFragment extends BaseFragment {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    public static final String TAG = "SmartHomeFragment";
    private TextView a;
    private MicoMode b = MicoMode.CATEGORY;
    private SmartHomeModeCategoryFragment c;
    private SmartHomeModeRoomFragment d;

    @JvmStatic
    @NotNull
    public static final SmartHomeFragment newInstance() {
        return Companion.newInstance();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void a(MicoMode micoMode) {
        if (this.b != micoMode) {
            this.b = micoMode;
            a();
        }
    }

    /* compiled from: SmartHomeFragment.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0005\u001a\u00020\u0006H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0007"}, d2 = {"Lcom/xiaomi/smarthome/ui/SmartHomeFragment$Companion;", "", "()V", "TAG", "", "newInstance", "Lcom/xiaomi/smarthome/ui/SmartHomeFragment;", "smarthome_release"}, k = 1, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @JvmStatic
        @NotNull
        public final SmartHomeFragment newInstance() {
            return new SmartHomeFragment();
        }
    }

    @Override // com.xiaomi.smarthome.base.BaseFragment, androidx.fragment.app.Fragment
    @SuppressLint({"ClickableViewAccessibility"})
    @NotNull
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        int i = 0;
        View view = inflater.inflate(R.layout.fragment_smart_home1, viewGroup, false);
        View findViewById = view.findViewById(R.id.tvModeSwitcher);
        Intrinsics.checkNotNullExpressionValue(findViewById, "view.findViewById(R.id.tvModeSwitcher)");
        this.a = (TextView) findViewById;
        TextView textView = this.a;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("modeSwitcher");
        }
        textView.setVisibility(Hardware.isX6A() ? 8 : 0);
        TextView textView2 = this.a;
        if (textView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("modeSwitcher");
        }
        AnimLifecycleManager.repeatOnAttach(textView2, MicoAnimConfigurator4SmallButton.get());
        TextView textView3 = this.a;
        if (textView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("modeSwitcher");
        }
        textView3.setOnClickListener(new a());
        View findViewById2 = view.findViewById(R.id.common_set);
        Intrinsics.checkNotNullExpressionValue(findViewById2, "view.findViewById(R.id.common_set)");
        ImageView imageView = (ImageView) findViewById2;
        if (Hardware.isX6A()) {
            i = 8;
        }
        imageView.setVisibility(i);
        ImageView imageView2 = imageView;
        AnimLifecycleManager.repeatOnAttach(imageView2, MicoAnimConfigurator4SmallButton.get());
        RxViewHelp.debounceClicksWithOneSeconds(imageView2).subscribe(b.a);
        a();
        Intrinsics.checkNotNullExpressionValue(view, "view");
        return view;
    }

    /* compiled from: SmartHomeFragment.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0004*\u0004\u0018\u00010\u00030\u0003H\n¢\u0006\u0002\b\u0005"}, d2 = {"<anonymous>", "", "it", "Landroid/view/View;", "kotlin.jvm.PlatformType", "onClick"}, k = 3, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    static final class a implements View.OnClickListener {
        a() {
        }

        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            MicoMode micoMode;
            if (DebouncingUtils.isValid(view, 500L)) {
                SmartHomeFragment smartHomeFragment = SmartHomeFragment.this;
                if (smartHomeFragment.b == MicoMode.CATEGORY) {
                    SmartHomeStatHelper.recordSmartTabControlModeClick("mode");
                    micoMode = MicoMode.ROOM;
                } else {
                    SmartHomeStatHelper.recordSmartTabRoomModeClick("mode");
                    micoMode = MicoMode.CATEGORY;
                }
                smartHomeFragment.a(micoMode);
                LiveEventBus.get(SmartHomeModeCategoryFragment.EVENT_MODE_CHANGED).post(1);
            }
        }
    }

    /* compiled from: SmartHomeFragment.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\u0010\u0000\u001a\u00020\u00012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003H\n¢\u0006\u0002\b\u0004"}, d2 = {"<anonymous>", "", "o", "", "accept"}, k = 3, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    static final class b<T> implements Consumer<Object> {
        public static final b a = new b();

        b() {
        }

        @Override // io.reactivex.functions.Consumer
        public final void accept(@Nullable Object obj) {
            EventBusRegistry.getEventBus().post(new MainPageGotoSetEvent());
        }
    }

    @Override // com.xiaomi.smarthome.base.BaseFragment, androidx.fragment.app.Fragment
    public void onAttach(@NotNull Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        super.onAttach(context);
        MicoMiotDeviceManager.getInstance().init(context);
        SmartHomeModeRoomFragment smartHomeModeRoomFragment = this.d;
        if (smartHomeModeRoomFragment != null) {
            smartHomeModeRoomFragment.init(context);
        }
    }

    @Override // com.xiaomi.smarthome.base.BaseFragment, androidx.fragment.app.Fragment
    public void onResume() {
        MicoMode micoMode;
        super.onResume();
        if (Hardware.isX6A()) {
            if (1 == MicoSettings.getMicoSmartHomeMode(getContext())) {
                SmartHomeStatHelper.recordSmartTabControlModeClick("mode");
                micoMode = MicoMode.ROOM;
            } else {
                SmartHomeStatHelper.recordSmartTabRoomModeClick("mode");
                micoMode = MicoMode.CATEGORY;
            }
            a(micoMode);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onDetach() {
        super.onDetach();
        MicoMiotDeviceManager.getInstance().unInit();
    }

    @Override // com.xiaomi.smarthome.base.BaseFragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        FragmentTransaction beginTransaction = getChildFragmentManager().beginTransaction();
        SmartHomeModeCategoryFragment smartHomeModeCategoryFragment = this.c;
        Intrinsics.checkNotNull(smartHomeModeCategoryFragment);
        FragmentTransaction remove = beginTransaction.remove(smartHomeModeCategoryFragment);
        SmartHomeModeRoomFragment smartHomeModeRoomFragment = this.d;
        Intrinsics.checkNotNull(smartHomeModeRoomFragment);
        remove.remove(smartHomeModeRoomFragment).commitNowAllowingStateLoss();
        this.c = null;
        this.d = null;
    }

    private final void a() {
        if (this.d == null) {
            this.d = SmartHomeModeRoomFragment.Companion.newInstance$smarthome_release();
        }
        if (this.c == null) {
            this.c = SmartHomeModeCategoryFragment.Companion.newInstance$smarthome_release();
        }
        FragmentTransaction beginTransaction = getChildFragmentManager().beginTransaction();
        Intrinsics.checkNotNullExpressionValue(beginTransaction, "childFragmentManager.beginTransaction()");
        beginTransaction.setCustomAnimations(R.anim.fragment_show_anim, R.anim.fragment_hide_anim);
        SmartHomeModeCategoryFragment smartHomeModeCategoryFragment = this.c;
        Intrinsics.checkNotNull(smartHomeModeCategoryFragment);
        if (!smartHomeModeCategoryFragment.isAdded()) {
            Fragment findFragmentByTag = getChildFragmentManager().findFragmentByTag(SmartHomeModeCategoryFragment.TAG);
            if (findFragmentByTag != null) {
                beginTransaction.remove(findFragmentByTag);
            }
            int i = R.id.fragmentContainer;
            SmartHomeModeCategoryFragment smartHomeModeCategoryFragment2 = this.c;
            Intrinsics.checkNotNull(smartHomeModeCategoryFragment2);
            beginTransaction.add(i, smartHomeModeCategoryFragment2, SmartHomeModeCategoryFragment.TAG);
        }
        SmartHomeModeRoomFragment smartHomeModeRoomFragment = this.d;
        Intrinsics.checkNotNull(smartHomeModeRoomFragment);
        if (!smartHomeModeRoomFragment.isAdded()) {
            Fragment findFragmentByTag2 = getChildFragmentManager().findFragmentByTag(SmartHomeModeRoomFragment.TAG);
            if (findFragmentByTag2 != null) {
                beginTransaction.remove(findFragmentByTag2);
            }
            int i2 = R.id.fragmentContainer;
            SmartHomeModeRoomFragment smartHomeModeRoomFragment2 = this.d;
            Intrinsics.checkNotNull(smartHomeModeRoomFragment2);
            beginTransaction.add(i2, smartHomeModeRoomFragment2, SmartHomeModeRoomFragment.TAG);
        }
        switch (this.b) {
            case ROOM:
                TextView textView = this.a;
                if (textView == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("modeSwitcher");
                }
                textView.setText(getString(R.string.switcher_mode_category));
                SmartHomeModeCategoryFragment smartHomeModeCategoryFragment3 = this.c;
                Intrinsics.checkNotNull(smartHomeModeCategoryFragment3);
                FragmentTransaction hide = beginTransaction.hide(smartHomeModeCategoryFragment3);
                SmartHomeModeRoomFragment smartHomeModeRoomFragment3 = this.d;
                Intrinsics.checkNotNull(smartHomeModeRoomFragment3);
                hide.show(smartHomeModeRoomFragment3).commitNowAllowingStateLoss();
                SmartHomeStatHelper.recordSmartTabRoomModeShow();
                return;
            case CATEGORY:
                TextView textView2 = this.a;
                if (textView2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("modeSwitcher");
                }
                textView2.setText(getString(R.string.switcher_text_room));
                SmartHomeModeRoomFragment smartHomeModeRoomFragment4 = this.d;
                Intrinsics.checkNotNull(smartHomeModeRoomFragment4);
                FragmentTransaction hide2 = beginTransaction.hide(smartHomeModeRoomFragment4);
                SmartHomeModeCategoryFragment smartHomeModeCategoryFragment4 = this.c;
                Intrinsics.checkNotNull(smartHomeModeCategoryFragment4);
                hide2.show(smartHomeModeCategoryFragment4).commitNowAllowingStateLoss();
                SmartHomeStatHelper.recordSmartTabControlModeShow();
                return;
            default:
                return;
        }
    }
}
