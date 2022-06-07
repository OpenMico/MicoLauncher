package com.bumptech.glide.manager;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.os.Build;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Deprecated
/* loaded from: classes.dex */
public class RequestManagerFragment extends Fragment {
    private final a a;
    private final RequestManagerTreeNode b;
    private final Set<RequestManagerFragment> c;
    @Nullable
    private RequestManager d;
    @Nullable
    private RequestManagerFragment e;
    @Nullable
    private Fragment f;

    public RequestManagerFragment() {
        this(new a());
    }

    @SuppressLint({"ValidFragment"})
    @VisibleForTesting
    RequestManagerFragment(@NonNull a aVar) {
        this.b = new a();
        this.c = new HashSet();
        this.a = aVar;
    }

    public void setRequestManager(@Nullable RequestManager requestManager) {
        this.d = requestManager;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @NonNull
    public a a() {
        return this.a;
    }

    @Nullable
    public RequestManager getRequestManager() {
        return this.d;
    }

    @NonNull
    public RequestManagerTreeNode getRequestManagerTreeNode() {
        return this.b;
    }

    private void a(RequestManagerFragment requestManagerFragment) {
        this.c.add(requestManagerFragment);
    }

    private void b(RequestManagerFragment requestManagerFragment) {
        this.c.remove(requestManagerFragment);
    }

    @NonNull
    @TargetApi(17)
    Set<RequestManagerFragment> b() {
        if (equals(this.e)) {
            return Collections.unmodifiableSet(this.c);
        }
        if (this.e == null || Build.VERSION.SDK_INT < 17) {
            return Collections.emptySet();
        }
        HashSet hashSet = new HashSet();
        for (RequestManagerFragment requestManagerFragment : this.e.b()) {
            if (b(requestManagerFragment.getParentFragment())) {
                hashSet.add(requestManagerFragment);
            }
        }
        return Collections.unmodifiableSet(hashSet);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(@Nullable Fragment fragment) {
        this.f = fragment;
        if (fragment != null && fragment.getActivity() != null) {
            a(fragment.getActivity());
        }
    }

    @Nullable
    @TargetApi(17)
    private Fragment c() {
        Fragment parentFragment = Build.VERSION.SDK_INT >= 17 ? getParentFragment() : null;
        return parentFragment != null ? parentFragment : this.f;
    }

    @TargetApi(17)
    private boolean b(@NonNull Fragment fragment) {
        Fragment parentFragment = getParentFragment();
        while (true) {
            Fragment parentFragment2 = fragment.getParentFragment();
            if (parentFragment2 == null) {
                return false;
            }
            if (parentFragment2.equals(parentFragment)) {
                return true;
            }
            fragment = fragment.getParentFragment();
        }
    }

    private void a(@NonNull Activity activity) {
        d();
        this.e = Glide.get(activity).getRequestManagerRetriever().a(activity);
        if (!equals(this.e)) {
            this.e.a(this);
        }
    }

    private void d() {
        RequestManagerFragment requestManagerFragment = this.e;
        if (requestManagerFragment != null) {
            requestManagerFragment.b(this);
            this.e = null;
        }
    }

    @Override // android.app.Fragment
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            a(activity);
        } catch (IllegalStateException e) {
            if (Log.isLoggable("RMFragment", 5)) {
                Log.w("RMFragment", "Unable to register fragment with root", e);
            }
        }
    }

    @Override // android.app.Fragment
    public void onDetach() {
        super.onDetach();
        d();
    }

    @Override // android.app.Fragment
    public void onStart() {
        super.onStart();
        this.a.a();
    }

    @Override // android.app.Fragment
    public void onStop() {
        super.onStop();
        this.a.b();
    }

    @Override // android.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        this.a.c();
        d();
    }

    @Override // android.app.Fragment
    public String toString() {
        return super.toString() + "{parent=" + c() + "}";
    }

    /* loaded from: classes.dex */
    private class a implements RequestManagerTreeNode {
        a() {
        }

        @Override // com.bumptech.glide.manager.RequestManagerTreeNode
        @NonNull
        public Set<RequestManager> getDescendants() {
            Set<RequestManagerFragment> b = RequestManagerFragment.this.b();
            HashSet hashSet = new HashSet(b.size());
            for (RequestManagerFragment requestManagerFragment : b) {
                if (requestManagerFragment.getRequestManager() != null) {
                    hashSet.add(requestManagerFragment.getRequestManager());
                }
            }
            return hashSet;
        }

        public String toString() {
            return super.toString() + "{fragment=" + RequestManagerFragment.this + "}";
        }
    }
}
