package com.xiaomi.smarthome.ui.widgets.security;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.alibaba.fastjson.asm.Opcodes;
import com.jakewharton.rxbinding4.view.RxView;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.xiaomi.mico.utils.Threads;
import com.xiaomi.micolauncher.common.ui.MicoAnimConfigurator4BigButton;
import com.xiaomi.micolauncher.common.ui.MicoAnimConfigurator4TabAndSwitch;
import com.xiaomi.micolauncher.common.ui.MicoAnimationManager;
import com.xiaomi.micolauncher.common.view.AnimLifecycleManager;
import com.xiaomi.onetrack.OneTrack;
import com.xiaomi.smarthome.R;
import com.xiaomi.smarthome.core.entity.DeviceInfoBean;
import com.xiaomi.smarthome.core.entity.DeviceTypeDic;
import com.xiaomi.smarthome.core.entity.IDevice;
import com.xiaomi.smarthome.core.utils.MiotDeviceUtils;
import com.xiaomi.smarthome.core.utils.OperationUtils;
import com.xiaomi.smarthome.core.utils.UiUtils;
import com.xiaomi.smarthome.databinding.SecurityCameraBinding;
import com.xiaomi.smarthome.databinding.SecurityCameraInDeviceListBinding;
import com.xiaomi.smarthome.ui.SmartHomeModeCategoryFragment;
import com.xiaomi.smarthome.ui.adapter.PageLocation;
import com.xiaomi.smarthome.ui.model.SmartHomeStatHelper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Consumer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* loaded from: classes4.dex */
public class SecurityView extends FrameLayout {
    private final ViewStub a;
    private final ViewStub b;
    private final Map<ViewStub, Boolean> c;
    private final Map<ViewStub, ViewDataBinding> d;
    private final Map<String, List<ViewStub>> e;
    private DeviceInfoBean f;
    private SecurityCameraBinding g;
    private SecurityCameraInDeviceListBinding h;
    private boolean i;
    private List<IDevice> j;
    private PageLocation k;
    private boolean l;
    private String m;
    private ItemTouchHelper n;
    private RecyclerView.ViewHolder o;

    public SecurityView(@NonNull Context context) {
        this(context, null);
    }

    public SecurityView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SecurityView(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public SecurityView(@NonNull Context context, @Nullable AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.c = new HashMap();
        this.d = new HashMap();
        this.e = new HashMap();
        this.i = false;
        this.k = PageLocation.PAGE_FIRST;
        this.l = true;
        LayoutInflater.from(context).inflate(R.layout.view_security, (ViewGroup) this, true);
        this.a = (ViewStub) findViewById(R.id.securityCamera);
        this.b = (ViewStub) findViewById(R.id.securityCamera2);
        ViewStub.OnInflateListener onInflateListener = new ViewStub.OnInflateListener() { // from class: com.xiaomi.smarthome.ui.widgets.security.-$$Lambda$SecurityView$dXs-BFCQ4jmbKAVZR8w6bf_zm-E
            @Override // android.view.ViewStub.OnInflateListener
            public final void onInflate(ViewStub viewStub, View view) {
                SecurityView.this.a(viewStub, view);
            }
        };
        this.a.setOnInflateListener(onInflateListener);
        this.b.setOnInflateListener(onInflateListener);
        ArrayList arrayList = new ArrayList();
        arrayList.add(this.a);
        arrayList.add(this.b);
        this.e.put(DeviceTypeDic.DT_SECURITY_CAMERA.getDeviceType(), arrayList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public /* synthetic */ void a(ViewStub viewStub, View view) {
        SecurityCameraInDeviceListBinding securityCameraInDeviceListBinding;
        if (this.a == viewStub) {
            this.g = SecurityCameraBinding.bind(view);
            SecurityCameraBinding securityCameraBinding = this.g;
            securityCameraBinding.tvLocation.setPaintFlags(Opcodes.INSTANCEOF);
            this.g.tvDeviceType.setPaintFlags(Opcodes.INSTANCEOF);
            AnimLifecycleManager.repeatOnAttach(this.g.ivStatus, MicoAnimConfigurator4TabAndSwitch.get());
            RxView.clicks(this.g.ivStatus).throttleFirst(1L, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.smarthome.ui.widgets.security.-$$Lambda$SecurityView$x5d24E-bVlhgw1acvRbnEviO0dQ
                @Override // io.reactivex.rxjava3.functions.Consumer
                public final void accept(Object obj) {
                    SecurityView.this.b((Unit) obj);
                }
            });
            securityCameraInDeviceListBinding = securityCameraBinding;
        } else if (this.b == viewStub) {
            this.h = SecurityCameraInDeviceListBinding.bind(view);
            SecurityCameraInDeviceListBinding securityCameraInDeviceListBinding2 = this.h;
            securityCameraInDeviceListBinding2.tvLocation.setPaintFlags(Opcodes.INSTANCEOF);
            this.h.tvDeviceType.setPaintFlags(Opcodes.INSTANCEOF);
            AnimLifecycleManager.repeatOnAttach(this.h.ivStatus, MicoAnimConfigurator4TabAndSwitch.get());
            RxView.clicks(this.h.ivStatus).throttleFirst(1L, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.smarthome.ui.widgets.security.-$$Lambda$SecurityView$4_HAhc6ISw0_sUWJoIqEYlXB2zM
                @Override // io.reactivex.rxjava3.functions.Consumer
                public final void accept(Object obj) {
                    SecurityView.this.a((Unit) obj);
                }
            });
            securityCameraInDeviceListBinding = securityCameraInDeviceListBinding2;
        } else {
            securityCameraInDeviceListBinding = null;
        }
        if (securityCameraInDeviceListBinding != null) {
            this.d.put(viewStub, securityCameraInDeviceListBinding);
            a(this.f, securityCameraInDeviceListBinding, null, this.k);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(Unit unit) throws Throwable {
        OperationUtils.toggleStatus(this.f.getDeviceInfoWrapper().getDeviceInfo(), new Function1() { // from class: com.xiaomi.smarthome.ui.widgets.security.-$$Lambda$SecurityView$x9Vk2Z159Cfpa8kof51In5XU5Bc
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit b;
                b = SecurityView.this.b((Boolean) obj);
                return b;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Unit b(Boolean bool) {
        SecurityCameraBinding securityCameraBinding = this.g;
        if (securityCameraBinding == null) {
            return null;
        }
        a(this.f, securityCameraBinding, bool, this.k);
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Unit unit) throws Throwable {
        OperationUtils.toggleStatus(this.f.getDeviceInfoWrapper().getDeviceInfo(), new Function1() { // from class: com.xiaomi.smarthome.ui.widgets.security.-$$Lambda$SecurityView$6TjILY03srQGy4mw0Wv1nLDxtVU
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit a;
                a = SecurityView.this.a((Boolean) obj);
                return a;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Unit a(Boolean bool) {
        SecurityCameraInDeviceListBinding securityCameraInDeviceListBinding = this.h;
        if (securityCameraInDeviceListBinding == null) {
            return null;
        }
        a(this.f, securityCameraInDeviceListBinding, bool, this.k);
        return null;
    }

    public void bindData(@Nullable List<IDevice> list, DeviceInfoBean deviceInfoBean, boolean z, PageLocation pageLocation, String str, ItemTouchHelper itemTouchHelper, RecyclerView.ViewHolder viewHolder) {
        ViewStub viewStub;
        this.f = deviceInfoBean;
        this.i = z;
        this.k = pageLocation;
        this.l = pageLocation == PageLocation.PAGE_SECOND;
        this.j = list;
        this.m = str;
        this.n = itemTouchHelper;
        this.o = viewHolder;
        String deviceType = this.f.getDeviceInfoWrapper().getModelInfo().getDeviceType();
        if (!DeviceTypeDic.DT_SECURITY_CAMERA.getDeviceType().equals(deviceType)) {
            viewStub = null;
        } else if (pageLocation == PageLocation.PAGE_FIRST) {
            viewStub = this.e.get(deviceType).get(0);
        } else {
            viewStub = this.e.get(deviceType).get(1);
        }
        if (viewStub != null && !a(viewStub)) {
            viewStub.inflate();
            this.c.put(viewStub, true);
        }
        ViewDataBinding viewDataBinding = this.d.get(viewStub);
        if (viewDataBinding != null) {
            a(deviceInfoBean, viewDataBinding, null, pageLocation);
        }
        b(viewStub);
    }

    private void a(final DeviceInfoBean deviceInfoBean, final ViewDataBinding viewDataBinding, final Boolean bool, final PageLocation pageLocation) {
        if (!Threads.isMainThread()) {
            new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.xiaomi.smarthome.ui.widgets.security.-$$Lambda$SecurityView$nG5IyO9yPTIdnZQ7erCyHIjYk-c
                @Override // java.lang.Runnable
                public final void run() {
                    SecurityView.this.c(deviceInfoBean, viewDataBinding, bool, pageLocation);
                }
            });
        } else {
            c(deviceInfoBean, viewDataBinding, bool, pageLocation);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: b */
    public void c(DeviceInfoBean deviceInfoBean, final ViewDataBinding viewDataBinding, Boolean bool, PageLocation pageLocation) {
        if (bool != null) {
            deviceInfoBean.getDeviceInfoWrapper().getDeviceInfo().currentStatus = bool.booleanValue();
        }
        if (!viewDataBinding.getRoot().hasOnClickListeners()) {
            viewDataBinding.getRoot().setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.smarthome.ui.widgets.security.-$$Lambda$SecurityView$3CcmasZ7JWgfAPU7D1NWGmDP5wY
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    SecurityView.this.a(viewDataBinding, view);
                }
            });
        }
        ImageView imageView = null;
        if (viewDataBinding instanceof SecurityCameraBinding) {
            SecurityCameraBinding securityCameraBinding = (SecurityCameraBinding) viewDataBinding;
            ImageView imageView2 = securityCameraBinding.ivDragHandle;
            securityCameraBinding.setItem(deviceInfoBean.getDeviceInfoWrapper());
            securityCameraBinding.setIsLocationDeviceNameExchange(deviceInfoBean.isLocationDeviceNameExchange());
            securityCameraBinding.setEditMode(this.i);
            securityCameraBinding.container.setBackground(ContextCompat.getDrawable(getContext(), MiotDeviceUtils.getItemBackgroundRes(this.j, deviceInfoBean, this.m)));
            AnimLifecycleManager.repeatOnAttach(securityCameraBinding.container, MicoAnimConfigurator4BigButton.get());
            if (this.i) {
                MicoAnimationManager.getManager().startScaleSmallAnims(securityCameraBinding.container);
                MicoAnimationManager.getManager().startShowAnims(securityCameraBinding.ivDragHandle);
                MicoAnimationManager.getManager().startHideAnims(securityCameraBinding.ivStatus);
            } else {
                a(viewDataBinding);
                MicoAnimationManager.getManager().startHideAnims(Boolean.valueOf(this.l), securityCameraBinding.ivDragHandle);
                MicoAnimationManager.getManager().startShowAnims(Boolean.valueOf(this.l), securityCameraBinding.ivStatus);
            }
            imageView = imageView2;
        }
        if (viewDataBinding instanceof SecurityCameraInDeviceListBinding) {
            SecurityCameraInDeviceListBinding securityCameraInDeviceListBinding = (SecurityCameraInDeviceListBinding) viewDataBinding;
            ImageView imageView3 = securityCameraInDeviceListBinding.ivDragHandle;
            securityCameraInDeviceListBinding.setItem(deviceInfoBean.getDeviceInfoWrapper());
            securityCameraInDeviceListBinding.setIsLocationDeviceNameExchange(deviceInfoBean.isLocationDeviceNameExchange());
            securityCameraInDeviceListBinding.setEditMode(this.i);
            securityCameraInDeviceListBinding.container.setBackground(ContextCompat.getDrawable(getContext(), MiotDeviceUtils.getItemBackgroundRes(this.j, deviceInfoBean, this.m)));
            AnimLifecycleManager.repeatOnAttach(securityCameraInDeviceListBinding.container, MicoAnimConfigurator4BigButton.get());
            if (this.i) {
                MicoAnimationManager.getManager().startScaleSmallAnims(securityCameraInDeviceListBinding.container);
                MicoAnimationManager.getManager().startShowAnims(securityCameraInDeviceListBinding.ivDragHandle);
                MicoAnimationManager.getManager().startHideAnims(securityCameraInDeviceListBinding.ivStatus);
            } else {
                a(viewDataBinding);
                MicoAnimationManager.getManager().startShowAnims(Boolean.valueOf(this.l), securityCameraInDeviceListBinding.ivStatus);
                MicoAnimationManager.getManager().startHideAnims(Boolean.valueOf(this.l), securityCameraInDeviceListBinding.ivDragHandle);
            }
            imageView = imageView3;
        }
        if (imageView != null && this.i) {
            imageView.setOnTouchListener(new View.OnTouchListener() { // from class: com.xiaomi.smarthome.ui.widgets.security.-$$Lambda$SecurityView$TXss1iQETh4DhfI4p0Mna8hsIt0
                @Override // android.view.View.OnTouchListener
                public final boolean onTouch(View view, MotionEvent motionEvent) {
                    boolean a;
                    a = SecurityView.this.a(view, motionEvent);
                    return a;
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(ViewDataBinding viewDataBinding, View view) {
        if (!UiUtils.isFastClick() && !this.i) {
            HashMap hashMap = new HashMap();
            hashMap.put("item", this.f.getDeviceInfoWrapper());
            hashMap.put(OneTrack.Event.VIEW, viewDataBinding.getRoot());
            LiveEventBus.get(SmartHomeModeCategoryFragment.EVENT_OPEN_CARD).post(hashMap);
            if (this.f.getDeviceInfoWrapper().getDeviceInfo().isOnline) {
                SmartHomeStatHelper.recordSmartTabControlModeClick(SmartHomeStatHelper.PARAM_VALUE_EQUIPMENT_MORE);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean a(View view, MotionEvent motionEvent) {
        this.n.startDrag(this.o);
        return false;
    }

    private void a(ViewDataBinding viewDataBinding) {
        if (this.k == PageLocation.PAGE_SECOND) {
            MicoAnimationManager.getManager().startScaleLargeAnims(viewDataBinding.getRoot());
        }
    }

    private boolean a(ViewStub viewStub) {
        Boolean bool = this.c.get(viewStub);
        return bool != null && bool.booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(ViewStub viewStub, ViewStub viewStub2, ViewDataBinding viewDataBinding) {
        viewDataBinding.getRoot().setVisibility(viewStub == viewStub2 ? 0 : 8);
    }

    private void b(final ViewStub viewStub) {
        if (viewStub != null) {
            this.d.forEach(new BiConsumer() { // from class: com.xiaomi.smarthome.ui.widgets.security.-$$Lambda$SecurityView$dBj2sFn32BspIKW3jA-i_CHvR8U
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    SecurityView.a(viewStub, (ViewStub) obj, (ViewDataBinding) obj2);
                }
            });
        }
    }
}
