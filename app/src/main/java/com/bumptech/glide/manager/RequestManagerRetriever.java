package com.bumptech.glide.manager;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.collection.ArrayMap;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.GlideExperiments;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.bitmap.HardwareConfigState;
import com.bumptech.glide.util.Preconditions;
import com.bumptech.glide.util.Util;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public class RequestManagerRetriever implements Handler.Callback {
    private static final RequestManagerFactory j = new RequestManagerFactory() { // from class: com.bumptech.glide.manager.RequestManagerRetriever.1
        @Override // com.bumptech.glide.manager.RequestManagerRetriever.RequestManagerFactory
        @NonNull
        public RequestManager build(@NonNull Glide glide, @NonNull Lifecycle lifecycle, @NonNull RequestManagerTreeNode requestManagerTreeNode, @NonNull Context context) {
            return new RequestManager(glide, lifecycle, requestManagerTreeNode, context);
        }
    };
    private volatile RequestManager c;
    private final Handler d;
    private final RequestManagerFactory e;
    private final h i;
    @VisibleForTesting
    final Map<FragmentManager, RequestManagerFragment> a = new HashMap();
    @VisibleForTesting
    final Map<androidx.fragment.app.FragmentManager, SupportRequestManagerFragment> b = new HashMap();
    private final ArrayMap<View, Fragment> f = new ArrayMap<>();
    private final ArrayMap<View, android.app.Fragment> g = new ArrayMap<>();
    private final Bundle h = new Bundle();

    /* loaded from: classes.dex */
    public interface RequestManagerFactory {
        @NonNull
        RequestManager build(@NonNull Glide glide, @NonNull Lifecycle lifecycle, @NonNull RequestManagerTreeNode requestManagerTreeNode, @NonNull Context context);
    }

    public RequestManagerRetriever(@Nullable RequestManagerFactory requestManagerFactory, GlideExperiments glideExperiments) {
        this.e = requestManagerFactory == null ? j : requestManagerFactory;
        this.d = new Handler(Looper.getMainLooper(), this);
        this.i = a(glideExperiments);
    }

    private static h a(GlideExperiments glideExperiments) {
        if (!HardwareConfigState.HARDWARE_BITMAPS_SUPPORTED || !HardwareConfigState.BLOCK_HARDWARE_BITMAPS_WHEN_GL_CONTEXT_MIGHT_NOT_BE_INITIALIZED) {
            return new d();
        }
        if (glideExperiments.isEnabled(GlideBuilder.WaitForFramesAfterTrimMemory.class)) {
            return new f();
        }
        return new g();
    }

    @NonNull
    private RequestManager a(@NonNull Context context) {
        if (this.c == null) {
            synchronized (this) {
                if (this.c == null) {
                    this.c = this.e.build(Glide.get(context.getApplicationContext()), new b(), new e(), context.getApplicationContext());
                }
            }
        }
        return this.c;
    }

    @NonNull
    public RequestManager get(@NonNull Context context) {
        if (context != null) {
            if (Util.isOnMainThread() && !(context instanceof Application)) {
                if (context instanceof FragmentActivity) {
                    return get((FragmentActivity) context);
                }
                if (context instanceof Activity) {
                    return get((Activity) context);
                }
                if (context instanceof ContextWrapper) {
                    ContextWrapper contextWrapper = (ContextWrapper) context;
                    if (contextWrapper.getBaseContext().getApplicationContext() != null) {
                        return get(contextWrapper.getBaseContext());
                    }
                }
            }
            return a(context);
        }
        throw new IllegalArgumentException("You cannot start a load on a null Context");
    }

    @NonNull
    public RequestManager get(@NonNull FragmentActivity fragmentActivity) {
        if (Util.isOnBackgroundThread()) {
            return get(fragmentActivity.getApplicationContext());
        }
        b((Activity) fragmentActivity);
        this.i.a(fragmentActivity);
        return a(fragmentActivity, fragmentActivity.getSupportFragmentManager(), (Fragment) null, c(fragmentActivity));
    }

    @NonNull
    public RequestManager get(@NonNull Fragment fragment) {
        Preconditions.checkNotNull(fragment.getContext(), "You cannot start a load on a fragment before it is attached or after it is destroyed");
        if (Util.isOnBackgroundThread()) {
            return get(fragment.getContext().getApplicationContext());
        }
        if (fragment.getActivity() != null) {
            this.i.a(fragment.getActivity());
        }
        return a(fragment.getContext(), fragment.getChildFragmentManager(), fragment, fragment.isVisible());
    }

    @NonNull
    public RequestManager get(@NonNull Activity activity) {
        if (Util.isOnBackgroundThread()) {
            return get(activity.getApplicationContext());
        }
        if (activity instanceof FragmentActivity) {
            return get((FragmentActivity) activity);
        }
        b(activity);
        this.i.a(activity);
        return a(activity, activity.getFragmentManager(), (android.app.Fragment) null, c(activity));
    }

    @NonNull
    public RequestManager get(@NonNull View view) {
        if (Util.isOnBackgroundThread()) {
            return get(view.getContext().getApplicationContext());
        }
        Preconditions.checkNotNull(view);
        Preconditions.checkNotNull(view.getContext(), "Unable to obtain a request manager for a view without a Context");
        Activity b = b(view.getContext());
        if (b == null) {
            return get(view.getContext().getApplicationContext());
        }
        if (b instanceof FragmentActivity) {
            FragmentActivity fragmentActivity = (FragmentActivity) b;
            Fragment a = a(view, fragmentActivity);
            return a != null ? get(a) : get(fragmentActivity);
        }
        android.app.Fragment a2 = a(view, b);
        if (a2 == null) {
            return get(b);
        }
        return get(a2);
    }

    private static void a(@Nullable Collection<Fragment> collection, @NonNull Map<View, Fragment> map) {
        if (collection != null) {
            for (Fragment fragment : collection) {
                if (!(fragment == null || fragment.getView() == null)) {
                    map.put(fragment.getView(), fragment);
                    a(fragment.getChildFragmentManager().getFragments(), map);
                }
            }
        }
    }

    @Nullable
    private Fragment a(@NonNull View view, @NonNull FragmentActivity fragmentActivity) {
        this.f.clear();
        a(fragmentActivity.getSupportFragmentManager().getFragments(), this.f);
        View findViewById = fragmentActivity.findViewById(16908290);
        Fragment fragment = null;
        while (!view.equals(findViewById) && (fragment = this.f.get(view)) == null && (view.getParent() instanceof View)) {
            view = (View) view.getParent();
        }
        this.f.clear();
        return fragment;
    }

    @Nullable
    @Deprecated
    private android.app.Fragment a(@NonNull View view, @NonNull Activity activity) {
        this.g.clear();
        a(activity.getFragmentManager(), this.g);
        View findViewById = activity.findViewById(16908290);
        android.app.Fragment fragment = null;
        while (!view.equals(findViewById) && (fragment = this.g.get(view)) == null && (view.getParent() instanceof View)) {
            view = (View) view.getParent();
        }
        this.g.clear();
        return fragment;
    }

    @TargetApi(26)
    @Deprecated
    private void a(@NonNull FragmentManager fragmentManager, @NonNull ArrayMap<View, android.app.Fragment> arrayMap) {
        if (Build.VERSION.SDK_INT >= 26) {
            for (android.app.Fragment fragment : fragmentManager.getFragments()) {
                if (fragment.getView() != null) {
                    arrayMap.put(fragment.getView(), fragment);
                    a(fragment.getChildFragmentManager(), arrayMap);
                }
            }
            return;
        }
        b(fragmentManager, arrayMap);
    }

    @Deprecated
    private void b(@NonNull FragmentManager fragmentManager, @NonNull ArrayMap<View, android.app.Fragment> arrayMap) {
        int i = 0;
        while (true) {
            i++;
            this.h.putInt("key", i);
            android.app.Fragment fragment = null;
            try {
                fragment = fragmentManager.getFragment(this.h, "key");
            } catch (Exception unused) {
            }
            if (fragment != null) {
                if (fragment.getView() != null) {
                    arrayMap.put(fragment.getView(), fragment);
                    if (Build.VERSION.SDK_INT >= 17) {
                        a(fragment.getChildFragmentManager(), arrayMap);
                    }
                }
            } else {
                return;
            }
        }
    }

    @Nullable
    private static Activity b(@NonNull Context context) {
        if (context instanceof Activity) {
            return (Activity) context;
        }
        if (context instanceof ContextWrapper) {
            return b(((ContextWrapper) context).getBaseContext());
        }
        return null;
    }

    @TargetApi(17)
    private static void b(@NonNull Activity activity) {
        if (Build.VERSION.SDK_INT >= 17 && activity.isDestroyed()) {
            throw new IllegalArgumentException("You cannot start a load for a destroyed activity");
        }
    }

    @NonNull
    @TargetApi(17)
    @Deprecated
    public RequestManager get(@NonNull android.app.Fragment fragment) {
        if (fragment.getActivity() == null) {
            throw new IllegalArgumentException("You cannot start a load on a fragment before it is attached");
        } else if (Util.isOnBackgroundThread() || Build.VERSION.SDK_INT < 17) {
            return get(fragment.getActivity().getApplicationContext());
        } else {
            if (fragment.getActivity() != null) {
                this.i.a(fragment.getActivity());
            }
            return a(fragment.getActivity(), fragment.getChildFragmentManager(), fragment, fragment.isVisible());
        }
    }

    @NonNull
    @Deprecated
    public RequestManagerFragment a(Activity activity) {
        return a(activity.getFragmentManager(), (android.app.Fragment) null);
    }

    @NonNull
    private RequestManagerFragment a(@NonNull FragmentManager fragmentManager, @Nullable android.app.Fragment fragment) {
        RequestManagerFragment requestManagerFragment = this.a.get(fragmentManager);
        if (requestManagerFragment != null) {
            return requestManagerFragment;
        }
        RequestManagerFragment requestManagerFragment2 = (RequestManagerFragment) fragmentManager.findFragmentByTag("com.bumptech.glide.manager");
        if (requestManagerFragment2 != null) {
            return requestManagerFragment2;
        }
        RequestManagerFragment requestManagerFragment3 = new RequestManagerFragment();
        requestManagerFragment3.a(fragment);
        this.a.put(fragmentManager, requestManagerFragment3);
        fragmentManager.beginTransaction().add(requestManagerFragment3, "com.bumptech.glide.manager").commitAllowingStateLoss();
        this.d.obtainMessage(1, fragmentManager).sendToTarget();
        return requestManagerFragment3;
    }

    @NonNull
    @Deprecated
    private RequestManager a(@NonNull Context context, @NonNull FragmentManager fragmentManager, @Nullable android.app.Fragment fragment, boolean z) {
        RequestManagerFragment a = a(fragmentManager, fragment);
        RequestManager requestManager = a.getRequestManager();
        if (requestManager == null) {
            requestManager = this.e.build(Glide.get(context), a.a(), a.getRequestManagerTreeNode(), context);
            if (z) {
                requestManager.onStart();
            }
            a.setRequestManager(requestManager);
        }
        return requestManager;
    }

    @NonNull
    public SupportRequestManagerFragment a(androidx.fragment.app.FragmentManager fragmentManager) {
        return a(fragmentManager, (Fragment) null);
    }

    private static boolean c(Context context) {
        Activity b = b(context);
        return b == null || !b.isFinishing();
    }

    @NonNull
    private SupportRequestManagerFragment a(@NonNull androidx.fragment.app.FragmentManager fragmentManager, @Nullable Fragment fragment) {
        SupportRequestManagerFragment supportRequestManagerFragment = this.b.get(fragmentManager);
        if (supportRequestManagerFragment != null) {
            return supportRequestManagerFragment;
        }
        SupportRequestManagerFragment supportRequestManagerFragment2 = (SupportRequestManagerFragment) fragmentManager.findFragmentByTag("com.bumptech.glide.manager");
        if (supportRequestManagerFragment2 != null) {
            return supportRequestManagerFragment2;
        }
        SupportRequestManagerFragment supportRequestManagerFragment3 = new SupportRequestManagerFragment();
        supportRequestManagerFragment3.a(fragment);
        this.b.put(fragmentManager, supportRequestManagerFragment3);
        fragmentManager.beginTransaction().add(supportRequestManagerFragment3, "com.bumptech.glide.manager").commitAllowingStateLoss();
        this.d.obtainMessage(2, fragmentManager).sendToTarget();
        return supportRequestManagerFragment3;
    }

    @NonNull
    private RequestManager a(@NonNull Context context, @NonNull androidx.fragment.app.FragmentManager fragmentManager, @Nullable Fragment fragment, boolean z) {
        SupportRequestManagerFragment a = a(fragmentManager, fragment);
        RequestManager requestManager = a.getRequestManager();
        if (requestManager == null) {
            requestManager = this.e.build(Glide.get(context), a.a(), a.getRequestManagerTreeNode(), context);
            if (z) {
                requestManager.onStart();
            }
            a.setRequestManager(requestManager);
        }
        return requestManager;
    }

    private boolean a(FragmentManager fragmentManager, boolean z) {
        RequestManagerFragment requestManagerFragment = this.a.get(fragmentManager);
        RequestManagerFragment requestManagerFragment2 = (RequestManagerFragment) fragmentManager.findFragmentByTag("com.bumptech.glide.manager");
        if (requestManagerFragment2 == requestManagerFragment) {
            return true;
        }
        if (requestManagerFragment2 != null && requestManagerFragment2.getRequestManager() != null) {
            throw new IllegalStateException("We've added two fragments with requests! Old: " + requestManagerFragment2 + " New: " + requestManagerFragment);
        } else if (z || fragmentManager.isDestroyed()) {
            if (Log.isLoggable("RMRetriever", 5)) {
                if (fragmentManager.isDestroyed()) {
                    Log.w("RMRetriever", "Parent was destroyed before our Fragment could be added");
                } else {
                    Log.w("RMRetriever", "Tried adding Fragment twice and failed twice, giving up!");
                }
            }
            requestManagerFragment.a().c();
            return true;
        } else {
            FragmentTransaction add = fragmentManager.beginTransaction().add(requestManagerFragment, "com.bumptech.glide.manager");
            if (requestManagerFragment2 != null) {
                add.remove(requestManagerFragment2);
            }
            add.commitAllowingStateLoss();
            this.d.obtainMessage(1, 1, 0, fragmentManager).sendToTarget();
            if (Log.isLoggable("RMRetriever", 3)) {
                Log.d("RMRetriever", "We failed to add our Fragment the first time around, trying again...");
            }
            return false;
        }
    }

    private boolean a(androidx.fragment.app.FragmentManager fragmentManager, boolean z) {
        SupportRequestManagerFragment supportRequestManagerFragment = this.b.get(fragmentManager);
        SupportRequestManagerFragment supportRequestManagerFragment2 = (SupportRequestManagerFragment) fragmentManager.findFragmentByTag("com.bumptech.glide.manager");
        if (supportRequestManagerFragment2 == supportRequestManagerFragment) {
            return true;
        }
        if (supportRequestManagerFragment2 != null && supportRequestManagerFragment2.getRequestManager() != null) {
            throw new IllegalStateException("We've added two fragments with requests! Old: " + supportRequestManagerFragment2 + " New: " + supportRequestManagerFragment);
        } else if (z || fragmentManager.isDestroyed()) {
            if (fragmentManager.isDestroyed()) {
                if (Log.isLoggable("RMRetriever", 5)) {
                    Log.w("RMRetriever", "Parent was destroyed before our Fragment could be added, all requests for the destroyed parent are cancelled");
                }
            } else if (Log.isLoggable("RMRetriever", 6)) {
                Log.e("RMRetriever", "ERROR: Tried adding Fragment twice and failed twice, giving up and cancelling all associated requests! This probably means you're starting loads in a unit test with an Activity that you haven't created and never create. If you're using Robolectric, create the Activity as part of your test setup");
            }
            supportRequestManagerFragment.a().c();
            return true;
        } else {
            androidx.fragment.app.FragmentTransaction add = fragmentManager.beginTransaction().add(supportRequestManagerFragment, "com.bumptech.glide.manager");
            if (supportRequestManagerFragment2 != null) {
                add.remove(supportRequestManagerFragment2);
            }
            add.commitNowAllowingStateLoss();
            this.d.obtainMessage(2, 1, 0, fragmentManager).sendToTarget();
            if (Log.isLoggable("RMRetriever", 3)) {
                Log.d("RMRetriever", "We failed to add our Fragment the first time around, trying again...");
            }
            return false;
        }
    }

    @Override // android.os.Handler.Callback
    public boolean handleMessage(Message message) {
        FragmentManager fragmentManager;
        boolean z = false;
        boolean z2 = true;
        boolean z3 = message.arg1 == 1;
        Object obj = null;
        switch (message.what) {
            case 1:
                FragmentManager fragmentManager2 = (FragmentManager) message.obj;
                if (a(fragmentManager2, z3)) {
                    obj = this.a.remove(fragmentManager2);
                    z = true;
                    fragmentManager = fragmentManager2;
                    break;
                }
                fragmentManager = null;
                break;
            case 2:
                androidx.fragment.app.FragmentManager fragmentManager3 = (androidx.fragment.app.FragmentManager) message.obj;
                if (a(fragmentManager3, z3)) {
                    obj = this.b.remove(fragmentManager3);
                    z = true;
                    fragmentManager = fragmentManager3;
                    break;
                }
                fragmentManager = null;
                break;
            default:
                z2 = false;
                fragmentManager = null;
                break;
        }
        if (Log.isLoggable("RMRetriever", 5) && z && obj == null) {
            Log.w("RMRetriever", "Failed to remove expected request manager fragment, manager: " + fragmentManager);
        }
        return z2;
    }
}
