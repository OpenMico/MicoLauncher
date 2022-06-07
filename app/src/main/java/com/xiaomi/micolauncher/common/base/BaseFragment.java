package com.xiaomi.micolauncher.common.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.CallSuper;
import androidx.annotation.CheckResult;
import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.elvishew.xlog.Logger;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.RxLifecycle;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.xiaomi.micolauncher.application.MicoApplication;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.BehaviorSubject;
import java.util.List;

@Keep
/* loaded from: classes3.dex */
public class BaseFragment extends Fragment {
    private static final Logger logger = L.lifecycle;
    private EventBusFragmentMode eventBusRegisterMode;
    protected boolean mActivated;
    private boolean visible;
    protected String simpleName = getClass().getSimpleName();
    private CompositeDisposable mDisposableBag = new CompositeDisposable();
    protected final BehaviorSubject<FragmentEvent> lifecycleSubject = BehaviorSubject.create();

    /* loaded from: classes3.dex */
    public enum EventBusFragmentMode {
        WHOLE_LIFE,
        ON_VISIBLE,
        ON_ACTIVATED,
        NO_AUTO_REGISTER
    }

    @Override // androidx.fragment.app.Fragment
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        this.lifecycleSubject.onNext(FragmentEvent.CREATE);
        logger.i("Fragment onCreate %s", this);
        if (getEventBusRegisterModeCached() == EventBusFragmentMode.WHOLE_LIFE) {
            registerToEventBusIfNot();
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        this.lifecycleSubject.onNext(FragmentEvent.ATTACH);
        logger.d("Fragment onAttach %s", this);
    }

    @Override // androidx.fragment.app.Fragment
    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View onCreateView = super.onCreateView(layoutInflater, viewGroup, bundle);
        this.lifecycleSubject.onNext(FragmentEvent.CREATE_VIEW);
        logger.d("Fragment onCreateView %s", this.simpleName);
        return onCreateView;
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityCreated(@Nullable Bundle bundle) {
        super.onActivityCreated(bundle);
        logger.d("Fragment onActivityCreated %s", this.simpleName);
    }

    @Override // androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
        this.lifecycleSubject.onNext(FragmentEvent.START);
        logger.d("Fragment onStart %s", this.simpleName);
    }

    @Override // androidx.fragment.app.Fragment
    public void setUserVisibleHint(boolean z) {
        super.setUserVisibleHint(z);
        logger.d("Fragment setUserVisibleHint %s, %s", this.simpleName, Boolean.valueOf(z));
        this.visible = z;
        if (isAdded()) {
            dispatchUserVisibleChange(z);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onHiddenChanged(boolean z) {
        super.onHiddenChanged(z);
        dispatchUserVisibleChange(!z);
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        this.lifecycleSubject.onNext(FragmentEvent.RESUME);
        if (!isHidden() && !this.mActivated && this.visible) {
            onActivate();
        }
        if (getEventBusRegisterModeCached() == EventBusFragmentMode.ON_VISIBLE) {
            registerToEventBusIfNot();
        }
        logger.d("Fragment onResume %s", this.simpleName);
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        this.lifecycleSubject.onNext(FragmentEvent.PAUSE);
        super.onPause();
        if (!isHidden() && this.mActivated) {
            onDeactivate();
        }
        if (getEventBusRegisterModeCached() == EventBusFragmentMode.ON_VISIBLE) {
            unregisterToEventBusIfNeed();
        }
        logger.d("Fragment onPause %s", this.simpleName);
    }

    public void addToDisposeBag(Disposable disposable) {
        this.mDisposableBag.add(disposable);
    }

    @Override // androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
        this.lifecycleSubject.onNext(FragmentEvent.STOP);
        logger.d("Fragment onStop %s", this.simpleName);
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        this.lifecycleSubject.onNext(FragmentEvent.DESTROY_VIEW);
        this.mDisposableBag.dispose();
        this.mDisposableBag = new CompositeDisposable();
        unregisterToEventBusIfNeed();
        super.onDestroyView();
        logger.i("Fragment onDestroyView %s", this.simpleName);
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        this.lifecycleSubject.onNext(FragmentEvent.DESTROY);
        unregisterToEventBusIfNeed();
        logger.i("Fragment onDestroy %s", this.simpleName);
    }

    @CallSuper
    public void onActivate() {
        this.mActivated = true;
        notifyChildFragment();
        logger.i("Fragment onActivate %s", this.simpleName);
        if (getEventBusRegisterModeCached() == EventBusFragmentMode.ON_ACTIVATED) {
            registerToEventBusIfNot();
        }
    }

    @CallSuper
    public void onDeactivate() {
        this.mActivated = false;
        notifyChildFragment();
        logger.i("Fragment onDeactivate %s", this.simpleName);
        if (getEventBusRegisterModeCached() == EventBusFragmentMode.ON_ACTIVATED) {
            unregisterToEventBusIfNeed();
        }
    }

    public boolean isActivated() {
        return isAdded() && !isHidden() && this.mActivated;
    }

    private void notifyChildFragment() {
        FragmentManager fragmentManager;
        List<Fragment> list = null;
        try {
            fragmentManager = getChildFragmentManager();
        } catch (IllegalStateException unused) {
            logger.e("get fragmentManager is null %s is");
            fragmentManager = null;
        }
        if (fragmentManager != null) {
            list = fragmentManager.getFragments();
        }
        if (!(list == null || list.isEmpty())) {
            for (Fragment fragment : list) {
                if (fragment != null && fragment.isVisible() && !fragment.isHidden() && fragment.getUserVisibleHint() && (fragment instanceof BaseFragment)) {
                    if (this.mActivated) {
                        ((BaseFragment) fragment).onActivate();
                    } else {
                        ((BaseFragment) fragment).onDeactivate();
                    }
                }
            }
        }
    }

    private void dispatchUserVisibleChange(boolean z) {
        if (z == this.mActivated) {
            return;
        }
        if (z) {
            onActivate();
        } else {
            onDeactivate();
        }
    }

    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindToLifecycle() {
        return RxLifecycle.bindUntilEvent(this.lifecycleSubject, FragmentEvent.DESTROY_VIEW);
    }

    protected boolean isOnResume() {
        return this.lifecycleSubject.getValue() == FragmentEvent.RESUME;
    }

    protected boolean isViewNotAvailable() {
        return isRemoving() || isDetached();
    }

    @NonNull
    public EventBusFragmentMode getEventBusRegisterMode() {
        return EventBusFragmentMode.NO_AUTO_REGISTER;
    }

    private EventBusFragmentMode getEventBusRegisterModeCached() {
        if (this.eventBusRegisterMode == null) {
            this.eventBusRegisterMode = getEventBusRegisterMode();
        }
        return this.eventBusRegisterMode;
    }

    protected void registerToEventBusIfNot() {
        if (!EventBusRegistry.getEventBus().isRegistered(this)) {
            EventBusRegistry.getEventBus().register(this);
        }
    }

    protected void unregisterToEventBusIfNeed() {
        if (EventBusRegistry.getEventBus().isRegistered(this)) {
            EventBusRegistry.getEventBus().unregister(this);
        }
    }

    @Override // androidx.fragment.app.Fragment
    @Nullable
    public Context getContext() {
        Context context = super.getContext();
        return context == null ? MicoApplication.getGlobalContext() : context;
    }
}
