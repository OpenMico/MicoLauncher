package androidx.fragment.app;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.R;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.r;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelStoreOwner;
import com.xiaomi.mesh.provision.MiotMeshDeviceItem;
import org.apache.commons.lang3.StringUtils;

/* compiled from: FragmentStateManager.java */
/* loaded from: classes.dex */
public class l {
    private final g a;
    private final m b;
    @NonNull
    private final Fragment c;
    private boolean d = false;
    private int e = -1;

    public l(@NonNull g gVar, @NonNull m mVar, @NonNull Fragment fragment) {
        this.a = gVar;
        this.b = mVar;
        this.c = fragment;
    }

    public l(@NonNull g gVar, @NonNull m mVar, @NonNull ClassLoader classLoader, @NonNull FragmentFactory fragmentFactory, @NonNull k kVar) {
        this.a = gVar;
        this.b = mVar;
        this.c = fragmentFactory.instantiate(classLoader, kVar.a);
        if (kVar.j != null) {
            kVar.j.setClassLoader(classLoader);
        }
        this.c.setArguments(kVar.j);
        this.c.mWho = kVar.b;
        this.c.mFromLayout = kVar.c;
        Fragment fragment = this.c;
        fragment.mRestored = true;
        fragment.mFragmentId = kVar.d;
        this.c.mContainerId = kVar.e;
        this.c.mTag = kVar.f;
        this.c.mRetainInstance = kVar.g;
        this.c.mRemoving = kVar.h;
        this.c.mDetached = kVar.i;
        this.c.mHidden = kVar.k;
        this.c.mMaxState = Lifecycle.State.values()[kVar.l];
        if (kVar.m != null) {
            this.c.mSavedFragmentState = kVar.m;
        } else {
            this.c.mSavedFragmentState = new Bundle();
        }
        if (FragmentManager.a(2)) {
            Log.v("FragmentManager", "Instantiated fragment " + this.c);
        }
    }

    public l(@NonNull g gVar, @NonNull m mVar, @NonNull Fragment fragment, @NonNull k kVar) {
        this.a = gVar;
        this.b = mVar;
        this.c = fragment;
        Fragment fragment2 = this.c;
        fragment2.mSavedViewState = null;
        fragment2.mSavedViewRegistryState = null;
        fragment2.mBackStackNesting = 0;
        fragment2.mInLayout = false;
        fragment2.mAdded = false;
        fragment2.mTargetWho = fragment2.mTarget != null ? this.c.mTarget.mWho : null;
        this.c.mTarget = null;
        if (kVar.m != null) {
            this.c.mSavedFragmentState = kVar.m;
        } else {
            this.c.mSavedFragmentState = new Bundle();
        }
    }

    @NonNull
    public Fragment a() {
        return this.c;
    }

    public void a(int i) {
        this.e = i;
    }

    public int b() {
        if (this.c.mFragmentManager == null) {
            return this.c.mState;
        }
        int i = this.e;
        switch (this.c.mMaxState) {
            case RESUMED:
                break;
            case STARTED:
                i = Math.min(i, 5);
                break;
            case CREATED:
                i = Math.min(i, 1);
                break;
            case INITIALIZED:
                i = Math.min(i, 0);
                break;
            default:
                i = Math.min(i, -1);
                break;
        }
        if (this.c.mFromLayout) {
            if (this.c.mInLayout) {
                i = Math.max(this.e, 2);
                if (this.c.mView != null && this.c.mView.getParent() == null) {
                    i = Math.min(i, 2);
                }
            } else {
                i = this.e < 4 ? Math.min(i, this.c.mState) : Math.min(i, 1);
            }
        }
        if (!this.c.mAdded) {
            i = Math.min(i, 1);
        }
        r.b.a aVar = null;
        if (FragmentManager.a && this.c.mContainer != null) {
            aVar = r.a(this.c.mContainer, this.c.getParentFragmentManager()).a(this);
        }
        if (aVar == r.b.a.ADDING) {
            i = Math.min(i, 6);
        } else if (aVar == r.b.a.REMOVING) {
            i = Math.max(i, 3);
        } else if (this.c.mRemoving) {
            if (this.c.isInBackStack()) {
                i = Math.min(i, 1);
            } else {
                i = Math.min(i, -1);
            }
        }
        if (this.c.mDeferStart && this.c.mState < 5) {
            i = Math.min(i, 4);
        }
        if (FragmentManager.a(2)) {
            Log.v("FragmentManager", "computeExpectedState() of " + i + " for " + this.c);
        }
        return i;
    }

    public void c() {
        if (!this.d) {
            try {
                this.d = true;
                while (true) {
                    int b = b();
                    if (b == this.c.mState) {
                        if (FragmentManager.a && this.c.mHiddenChanged) {
                            if (!(this.c.mView == null || this.c.mContainer == null)) {
                                r a = r.a(this.c.mContainer, this.c.getParentFragmentManager());
                                if (this.c.mHidden) {
                                    a.c(this);
                                } else {
                                    a.b(this);
                                }
                            }
                            if (this.c.mFragmentManager != null) {
                                this.c.mFragmentManager.q(this.c);
                            }
                            this.c.mHiddenChanged = false;
                            this.c.onHiddenChanged(this.c.mHidden);
                        }
                        return;
                    } else if (b > this.c.mState) {
                        switch (this.c.mState + 1) {
                            case 0:
                                e();
                                continue;
                            case 1:
                                f();
                                continue;
                            case 2:
                                d();
                                g();
                                continue;
                            case 3:
                                h();
                                continue;
                            case 4:
                                if (!(this.c.mView == null || this.c.mContainer == null)) {
                                    r.a(this.c.mContainer, this.c.getParentFragmentManager()).a(r.b.EnumC0018b.a(this.c.mView.getVisibility()), this);
                                }
                                this.c.mState = 4;
                                continue;
                            case 5:
                                i();
                                continue;
                            case 6:
                                this.c.mState = 6;
                                continue;
                            case 7:
                                j();
                                continue;
                            default:
                                continue;
                        }
                    } else {
                        switch (this.c.mState - 1) {
                            case -1:
                                r();
                                continue;
                            case 0:
                                q();
                                continue;
                            case 1:
                                p();
                                this.c.mState = 1;
                                continue;
                            case 2:
                                this.c.mInLayout = false;
                                this.c.mState = 2;
                                continue;
                            case 3:
                                if (FragmentManager.a(3)) {
                                    Log.d("FragmentManager", "movefrom ACTIVITY_CREATED: " + this.c);
                                }
                                if (this.c.mView != null && this.c.mSavedViewState == null) {
                                    o();
                                }
                                if (!(this.c.mView == null || this.c.mContainer == null)) {
                                    r.a(this.c.mContainer, this.c.getParentFragmentManager()).d(this);
                                }
                                this.c.mState = 3;
                                continue;
                            case 4:
                                l();
                                continue;
                            case 5:
                                this.c.mState = 5;
                                continue;
                            case 6:
                                k();
                                continue;
                            default:
                                continue;
                        }
                    }
                }
            } finally {
                this.d = false;
            }
        } else if (FragmentManager.a(2)) {
            Log.v("FragmentManager", "Ignoring re-entrant call to moveToExpectedState() for " + a());
        }
    }

    public void d() {
        if (this.c.mFromLayout && this.c.mInLayout && !this.c.mPerformedCreateView) {
            if (FragmentManager.a(3)) {
                Log.d("FragmentManager", "moveto CREATE_VIEW: " + this.c);
            }
            Fragment fragment = this.c;
            fragment.performCreateView(fragment.performGetLayoutInflater(fragment.mSavedFragmentState), null, this.c.mSavedFragmentState);
            if (this.c.mView != null) {
                this.c.mView.setSaveFromParentEnabled(false);
                this.c.mView.setTag(R.id.fragment_container_view_tag, this.c);
                if (this.c.mHidden) {
                    this.c.mView.setVisibility(8);
                }
                this.c.performViewCreated();
                g gVar = this.a;
                Fragment fragment2 = this.c;
                gVar.a(fragment2, fragment2.mView, this.c.mSavedFragmentState, false);
                this.c.mState = 2;
            }
        }
    }

    public void a(@NonNull ClassLoader classLoader) {
        if (this.c.mSavedFragmentState != null) {
            this.c.mSavedFragmentState.setClassLoader(classLoader);
            Fragment fragment = this.c;
            fragment.mSavedViewState = fragment.mSavedFragmentState.getSparseParcelableArray("android:view_state");
            Fragment fragment2 = this.c;
            fragment2.mSavedViewRegistryState = fragment2.mSavedFragmentState.getBundle("android:view_registry_state");
            Fragment fragment3 = this.c;
            fragment3.mTargetWho = fragment3.mSavedFragmentState.getString("android:target_state");
            if (this.c.mTargetWho != null) {
                Fragment fragment4 = this.c;
                fragment4.mTargetRequestCode = fragment4.mSavedFragmentState.getInt("android:target_req_state", 0);
            }
            if (this.c.mSavedUserVisibleHint != null) {
                Fragment fragment5 = this.c;
                fragment5.mUserVisibleHint = fragment5.mSavedUserVisibleHint.booleanValue();
                this.c.mSavedUserVisibleHint = null;
            } else {
                Fragment fragment6 = this.c;
                fragment6.mUserVisibleHint = fragment6.mSavedFragmentState.getBoolean("android:user_visible_hint", true);
            }
            if (!this.c.mUserVisibleHint) {
                this.c.mDeferStart = true;
            }
        }
    }

    public void e() {
        if (FragmentManager.a(3)) {
            Log.d("FragmentManager", "moveto ATTACHED: " + this.c);
        }
        l lVar = null;
        if (this.c.mTarget != null) {
            l c = this.b.c(this.c.mTarget.mWho);
            if (c != null) {
                Fragment fragment = this.c;
                fragment.mTargetWho = fragment.mTarget.mWho;
                this.c.mTarget = null;
                lVar = c;
            } else {
                throw new IllegalStateException("Fragment " + this.c + " declared target fragment " + this.c.mTarget + " that does not belong to this FragmentManager!");
            }
        } else if (this.c.mTargetWho != null && (lVar = this.b.c(this.c.mTargetWho)) == null) {
            throw new IllegalStateException("Fragment " + this.c + " declared target fragment " + this.c.mTargetWho + " that does not belong to this FragmentManager!");
        }
        if (lVar != null && (FragmentManager.a || lVar.a().mState < 1)) {
            lVar.c();
        }
        Fragment fragment2 = this.c;
        fragment2.mHost = fragment2.mFragmentManager.h();
        Fragment fragment3 = this.c;
        fragment3.mParentFragment = fragment3.mFragmentManager.i();
        this.a.a(this.c, false);
        this.c.performAttach();
        this.a.b(this.c, false);
    }

    public void f() {
        if (FragmentManager.a(3)) {
            Log.d("FragmentManager", "moveto CREATED: " + this.c);
        }
        if (!this.c.mIsCreated) {
            g gVar = this.a;
            Fragment fragment = this.c;
            gVar.a(fragment, fragment.mSavedFragmentState, false);
            Fragment fragment2 = this.c;
            fragment2.performCreate(fragment2.mSavedFragmentState);
            g gVar2 = this.a;
            Fragment fragment3 = this.c;
            gVar2.b(fragment3, fragment3.mSavedFragmentState, false);
            return;
        }
        Fragment fragment4 = this.c;
        fragment4.restoreChildFragmentState(fragment4.mSavedFragmentState);
        this.c.mState = 1;
    }

    public void g() {
        String str;
        if (!this.c.mFromLayout) {
            if (FragmentManager.a(3)) {
                Log.d("FragmentManager", "moveto CREATE_VIEW: " + this.c);
            }
            Fragment fragment = this.c;
            LayoutInflater performGetLayoutInflater = fragment.performGetLayoutInflater(fragment.mSavedFragmentState);
            ViewGroup viewGroup = null;
            if (this.c.mContainer != null) {
                viewGroup = this.c.mContainer;
            } else if (this.c.mContainerId != 0) {
                if (this.c.mContainerId != -1) {
                    viewGroup = (ViewGroup) this.c.mFragmentManager.j().onFindViewById(this.c.mContainerId);
                    if (viewGroup == null && !this.c.mRestored) {
                        try {
                            str = this.c.getResources().getResourceName(this.c.mContainerId);
                        } catch (Resources.NotFoundException unused) {
                            str = "unknown";
                        }
                        throw new IllegalArgumentException("No view found for id 0x" + Integer.toHexString(this.c.mContainerId) + " (" + str + ") for fragment " + this.c);
                    }
                } else {
                    throw new IllegalArgumentException("Cannot create fragment " + this.c + " for a container view with no id");
                }
            }
            Fragment fragment2 = this.c;
            fragment2.mContainer = viewGroup;
            fragment2.performCreateView(performGetLayoutInflater, viewGroup, fragment2.mSavedFragmentState);
            if (this.c.mView != null) {
                boolean z = false;
                this.c.mView.setSaveFromParentEnabled(false);
                this.c.mView.setTag(R.id.fragment_container_view_tag, this.c);
                if (viewGroup != null) {
                    s();
                }
                if (this.c.mHidden) {
                    this.c.mView.setVisibility(8);
                }
                if (ViewCompat.isAttachedToWindow(this.c.mView)) {
                    ViewCompat.requestApplyInsets(this.c.mView);
                } else {
                    final View view = this.c.mView;
                    view.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: androidx.fragment.app.l.1
                        @Override // android.view.View.OnAttachStateChangeListener
                        public void onViewDetachedFromWindow(View view2) {
                        }

                        @Override // android.view.View.OnAttachStateChangeListener
                        public void onViewAttachedToWindow(View view2) {
                            view.removeOnAttachStateChangeListener(this);
                            ViewCompat.requestApplyInsets(view);
                        }
                    });
                }
                this.c.performViewCreated();
                g gVar = this.a;
                Fragment fragment3 = this.c;
                gVar.a(fragment3, fragment3.mView, this.c.mSavedFragmentState, false);
                int visibility = this.c.mView.getVisibility();
                float alpha = this.c.mView.getAlpha();
                if (FragmentManager.a) {
                    this.c.setPostOnViewCreatedAlpha(alpha);
                    if (this.c.mContainer != null && visibility == 0) {
                        View findFocus = this.c.mView.findFocus();
                        if (findFocus != null) {
                            this.c.setFocusedView(findFocus);
                            if (FragmentManager.a(2)) {
                                Log.v("FragmentManager", "requestFocus: Saved focused view " + findFocus + " for Fragment " + this.c);
                            }
                        }
                        this.c.mView.setAlpha(0.0f);
                    }
                } else {
                    Fragment fragment4 = this.c;
                    if (visibility == 0 && fragment4.mContainer != null) {
                        z = true;
                    }
                    fragment4.mIsNewlyAdded = z;
                }
            }
            this.c.mState = 2;
        }
    }

    public void h() {
        if (FragmentManager.a(3)) {
            Log.d("FragmentManager", "moveto ACTIVITY_CREATED: " + this.c);
        }
        Fragment fragment = this.c;
        fragment.performActivityCreated(fragment.mSavedFragmentState);
        g gVar = this.a;
        Fragment fragment2 = this.c;
        gVar.c(fragment2, fragment2.mSavedFragmentState, false);
    }

    public void i() {
        if (FragmentManager.a(3)) {
            Log.d("FragmentManager", "moveto STARTED: " + this.c);
        }
        this.c.performStart();
        this.a.c(this.c, false);
    }

    public void j() {
        if (FragmentManager.a(3)) {
            Log.d("FragmentManager", "moveto RESUMED: " + this.c);
        }
        View focusedView = this.c.getFocusedView();
        if (focusedView != null && a(focusedView)) {
            boolean requestFocus = focusedView.requestFocus();
            if (FragmentManager.a(2)) {
                StringBuilder sb = new StringBuilder();
                sb.append("requestFocus: Restoring focused view ");
                sb.append(focusedView);
                sb.append(StringUtils.SPACE);
                sb.append(requestFocus ? MiotMeshDeviceItem.CONNECT_STATE_SUCCEEDED : MiotMeshDeviceItem.CONNECT_STATE_FAILED);
                sb.append(" on Fragment ");
                sb.append(this.c);
                sb.append(" resulting in focused view ");
                sb.append(this.c.mView.findFocus());
                Log.v("FragmentManager", sb.toString());
            }
        }
        this.c.setFocusedView(null);
        this.c.performResume();
        this.a.d(this.c, false);
        Fragment fragment = this.c;
        fragment.mSavedFragmentState = null;
        fragment.mSavedViewState = null;
        fragment.mSavedViewRegistryState = null;
    }

    private boolean a(@NonNull View view) {
        if (view == this.c.mView) {
            return true;
        }
        for (ViewParent parent = view.getParent(); parent != null; parent = parent.getParent()) {
            if (parent == this.c.mView) {
                return true;
            }
        }
        return false;
    }

    public void k() {
        if (FragmentManager.a(3)) {
            Log.d("FragmentManager", "movefrom RESUMED: " + this.c);
        }
        this.c.performPause();
        this.a.e(this.c, false);
    }

    public void l() {
        if (FragmentManager.a(3)) {
            Log.d("FragmentManager", "movefrom STARTED: " + this.c);
        }
        this.c.performStop();
        this.a.f(this.c, false);
    }

    @NonNull
    public k m() {
        k kVar = new k(this.c);
        if (this.c.mState <= -1 || kVar.m != null) {
            kVar.m = this.c.mSavedFragmentState;
        } else {
            kVar.m = t();
            if (this.c.mTargetWho != null) {
                if (kVar.m == null) {
                    kVar.m = new Bundle();
                }
                kVar.m.putString("android:target_state", this.c.mTargetWho);
                if (this.c.mTargetRequestCode != 0) {
                    kVar.m.putInt("android:target_req_state", this.c.mTargetRequestCode);
                }
            }
        }
        return kVar;
    }

    @Nullable
    public Fragment.SavedState n() {
        Bundle t;
        if (this.c.mState <= -1 || (t = t()) == null) {
            return null;
        }
        return new Fragment.SavedState(t);
    }

    private Bundle t() {
        Bundle bundle = new Bundle();
        this.c.performSaveInstanceState(bundle);
        this.a.d(this.c, bundle, false);
        if (bundle.isEmpty()) {
            bundle = null;
        }
        if (this.c.mView != null) {
            o();
        }
        if (this.c.mSavedViewState != null) {
            if (bundle == null) {
                bundle = new Bundle();
            }
            bundle.putSparseParcelableArray("android:view_state", this.c.mSavedViewState);
        }
        if (this.c.mSavedViewRegistryState != null) {
            if (bundle == null) {
                bundle = new Bundle();
            }
            bundle.putBundle("android:view_registry_state", this.c.mSavedViewRegistryState);
        }
        if (!this.c.mUserVisibleHint) {
            if (bundle == null) {
                bundle = new Bundle();
            }
            bundle.putBoolean("android:user_visible_hint", this.c.mUserVisibleHint);
        }
        return bundle;
    }

    public void o() {
        if (this.c.mView != null) {
            SparseArray<Parcelable> sparseArray = new SparseArray<>();
            this.c.mView.saveHierarchyState(sparseArray);
            if (sparseArray.size() > 0) {
                this.c.mSavedViewState = sparseArray;
            }
            Bundle bundle = new Bundle();
            this.c.mViewLifecycleOwner.b(bundle);
            if (!bundle.isEmpty()) {
                this.c.mSavedViewRegistryState = bundle;
            }
        }
    }

    public void p() {
        if (FragmentManager.a(3)) {
            Log.d("FragmentManager", "movefrom CREATE_VIEW: " + this.c);
        }
        if (!(this.c.mContainer == null || this.c.mView == null)) {
            this.c.mContainer.removeView(this.c.mView);
        }
        this.c.performDestroyView();
        this.a.g(this.c, false);
        Fragment fragment = this.c;
        fragment.mContainer = null;
        fragment.mView = null;
        fragment.mViewLifecycleOwner = null;
        fragment.mViewLifecycleOwnerLiveData.setValue(null);
        this.c.mInLayout = false;
    }

    public void q() {
        Fragment e;
        if (FragmentManager.a(3)) {
            Log.d("FragmentManager", "movefrom CREATED: " + this.c);
        }
        boolean z = true;
        boolean z2 = this.c.mRemoving && !this.c.isInBackStack();
        if (z2 || this.b.a().b(this.c)) {
            FragmentHostCallback<?> fragmentHostCallback = this.c.mHost;
            if (fragmentHostCallback instanceof ViewModelStoreOwner) {
                z = this.b.a().a();
            } else if (fragmentHostCallback.c() instanceof Activity) {
                z = true ^ ((Activity) fragmentHostCallback.c()).isChangingConfigurations();
            }
            if (z2 || z) {
                this.b.a().f(this.c);
            }
            this.c.performDestroy();
            this.a.h(this.c, false);
            for (l lVar : this.b.g()) {
                if (lVar != null) {
                    Fragment a = lVar.a();
                    if (this.c.mWho.equals(a.mTargetWho)) {
                        a.mTarget = this.c;
                        a.mTargetWho = null;
                    }
                }
            }
            if (this.c.mTargetWho != null) {
                Fragment fragment = this.c;
                fragment.mTarget = this.b.e(fragment.mTargetWho);
            }
            this.b.b(this);
            return;
        }
        if (!(this.c.mTargetWho == null || (e = this.b.e(this.c.mTargetWho)) == null || !e.mRetainInstance)) {
            this.c.mTarget = e;
        }
        this.c.mState = 0;
    }

    public void r() {
        if (FragmentManager.a(3)) {
            Log.d("FragmentManager", "movefrom ATTACHED: " + this.c);
        }
        this.c.performDetach();
        boolean z = false;
        this.a.i(this.c, false);
        Fragment fragment = this.c;
        fragment.mState = -1;
        fragment.mHost = null;
        fragment.mParentFragment = null;
        fragment.mFragmentManager = null;
        if (fragment.mRemoving && !this.c.isInBackStack()) {
            z = true;
        }
        if (z || this.b.a().b(this.c)) {
            if (FragmentManager.a(3)) {
                Log.d("FragmentManager", "initState called for fragment: " + this.c);
            }
            this.c.initState();
        }
    }

    public void s() {
        this.c.mContainer.addView(this.c.mView, this.b.c(this.c));
    }
}
