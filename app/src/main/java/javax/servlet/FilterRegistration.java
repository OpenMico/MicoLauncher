package javax.servlet;

import java.util.Collection;
import java.util.EnumSet;
import javax.servlet.Registration;

/* loaded from: classes5.dex */
public interface FilterRegistration extends Registration {

    /* loaded from: classes5.dex */
    public interface Dynamic extends FilterRegistration, Registration.Dynamic {
    }

    void addMappingForServletNames(EnumSet<DispatcherType> enumSet, boolean z, String... strArr);

    void addMappingForUrlPatterns(EnumSet<DispatcherType> enumSet, boolean z, String... strArr);

    Collection<String> getServletNameMappings();

    Collection<String> getUrlPatternMappings();
}
