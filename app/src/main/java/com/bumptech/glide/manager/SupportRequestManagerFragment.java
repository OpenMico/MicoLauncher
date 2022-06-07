package com.bumptech.glide.manager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/* loaded from: classes.dex */
public class SupportRequestManagerFragment extends Fragment {
    private final a a;
    private final RequestManagerTreeNode b;
    private final Set<SupportRequestManagerFragment> c;
    @Nullable
    private SupportRequestManagerFragment d;
    @Nullable
    private RequestManager e;
    @Nullable
    private Fragment f;

    public SupportRequestManagerFragment() {
        this(new a());
    }

    @SuppressLint({"ValidFragment"})
    @VisibleForTesting
    public SupportRequestManagerFragment(@NonNull a aVar) {
        this.b = new a();
        this.c = new HashSet();
        this.a = aVar;
    }

    public void setRequestManager(@Nullable RequestManager requestManager) {
        this.e = requestManager;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @NonNull
    public a a() {
        return this.a;
    }

    @Nullable
    public RequestManager getRequestManager() {
        return this.e;
    }

    @NonNull
    public RequestManagerTreeNode getRequestManagerTreeNode() {
        return this.b;
    }

    private void a(SupportRequestManagerFragment supportRequestManagerFragment) {
        this.c.add(supportRequestManagerFragment);
    }

    private void b(SupportRequestManagerFragment supportRequestManagerFragment) {
        this.c.remove(supportRequestManagerFragment);
    }

    @NonNull
    Set<SupportRequestManagerFragment> b() {
        SupportRequestManagerFragment supportRequestManagerFragment = this.d;
        if (supportRequestManagerFragment == null) {
            return Collections.emptySet();
        }
        if (equals(supportRequestManagerFragment)) {
            return Collections.unmodifiableSet(this.c);
        }
        HashSet hashSet = new HashSet();
        for (SupportRequestManagerFragment supportRequestManagerFragment2 : this.d.b()) {
            if (c(supportRequestManagerFragment2.c())) {
                hashSet.add(supportRequestManagerFragment2);
            }
        }
        return Collections.unmodifiableSet(hashSet);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(@Nullable Fragment fragment) {
        FragmentManager b;
        this.f = fragment;
        if (fragment != null && fragment.getContext() != null && (b = b(fragment)) != null) {
            a(fragment.getContext(), b);
        }
    }

    @Nullable
    private static FragmentManager b(@NonNull Fragment fragment) {
        while (fragment.getParentFragment() != null) {
            fragment = fragment.getParentFragment();
        }
        return fragment.getFragmentManager();
    }

    @Nullable
    private Fragment c() {
        Fragment parentFragment = getParentFragment();
        return parentFragment != null ? parentFragment : this.f;
    }

    private boolean c(@NonNull Fragment fragment) {
        Fragment c = c();
        while (true) {
            Fragment parentFragment = fragment.getParentFragment();
            if (parentFragment == null) {
                return false;
            }
            if (parentFragment.equals(c)) {
                return true;
            }
            fragment = fragment.getParentFragment();
        }
    }

    private void a(@NonNull Context context, @NonNull FragmentManager fragmentManager) {
        d();
        this.d = Glide.get(context).getRequestManagerRetriever().a(fragmentManager);
        if (!equals(this.d)) {
            this.d.a(this);
        }
    }

    private void d() {
        SupportRequestManagerFragment supportRequestManagerFragment = this.d;
        if (supportRequestManagerFragment != null) {
            supportRequestManagerFragment.b(this);
            this.d = null;
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        FragmentManager b = b((Fragment) this);
        if (b != null) {
            try {
                a(getContext(), b);
            } catch (IllegalStateException e) {
                if (Log.isLoggable("SupportRMFragment", 5)) {
                    Log.w("SupportRMFragment", "Unable to register fragment with root", e);
                }
            }
        } else if (Log.isLoggable("SupportRMFragment", 5)) {
            Log.w("SupportRMFragment", "Unable to register fragment with root, ancestor detached");
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onDetach() {
        super.onDetach();
        this.f = null;
        d();
    }

    @Override // androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
        this.a.a();
    }

    @Override // androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
        this.a.b();
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        this.a.c();
        d();
    }

    @Override // androidx.fragment.app.Fragment
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
            Set<SupportRequestManagerFragment> b = SupportRequestManagerFragment.this.b();
            HashSet hashSet = new HashSet(b.size());
            for (SupportRequestManagerFragment supportRequestManagerFragment : b) {
                if (supportRequestManagerFragment.getRequestManager() != null) {
                    hashSet.add(supportRequestManagerFragment.getRequestManager());
                }
            }
            return hashSet;
        }

        public String toString() {
            return super.toString() + "{fragment=" + SupportRequestManagerFragment.this + "}";
        }
    }
}
