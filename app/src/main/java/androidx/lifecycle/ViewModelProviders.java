package androidx.lifecycle;

import android.app.Activity;
import android.app.Application;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

/* loaded from: classes.dex */
public class ViewModelProviders {
    private static Application checkApplication(Activity activity) {
        Application application = activity.getApplication();
        if (application != null) {
            return application;
        }
        throw new IllegalStateException("Your activity/fragment is not yet attached to Application. You can't request ViewModel before onCreate call.");
    }

    private static Activity checkActivity(Fragment fragment) {
        FragmentActivity activity = fragment.getActivity();
        if (activity != null) {
            return activity;
        }
        throw new IllegalStateException("Can't create ViewModelProvider for detached fragment");
    }

    @NonNull
    @MainThread
    public static ViewModelProvider of(@NonNull Fragment fragment) {
        return of(fragment, (ViewModelProvider.Factory) null);
    }

    @NonNull
    @MainThread
    public static ViewModelProvider of(@NonNull FragmentActivity fragmentActivity) {
        return of(fragmentActivity, (ViewModelProvider.Factory) null);
    }

    @NonNull
    @MainThread
    public static ViewModelProvider of(@NonNull Fragment fragment, @Nullable ViewModelProvider.Factory factory) {
        Application checkApplication = checkApplication(checkActivity(fragment));
        if (factory == null) {
            factory = ViewModelProvider.AndroidViewModelFactory.getInstance(checkApplication);
        }
        return new ViewModelProvider(fragment.getViewModelStore(), factory);
    }

    @NonNull
    @MainThread
    public static ViewModelProvider of(@NonNull FragmentActivity fragmentActivity, @Nullable ViewModelProvider.Factory factory) {
        Application checkApplication = checkApplication(fragmentActivity);
        if (factory == null) {
            factory = ViewModelProvider.AndroidViewModelFactory.getInstance(checkApplication);
        }
        return new ViewModelProvider(fragmentActivity.getViewModelStore(), factory);
    }

    @Deprecated
    /* loaded from: classes.dex */
    public static class DefaultFactory extends ViewModelProvider.AndroidViewModelFactory {
        @Deprecated
        public DefaultFactory(@NonNull Application application) {
            super(application);
        }
    }
}
