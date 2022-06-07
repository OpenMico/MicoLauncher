package javax.servlet;

import java.util.Collection;
import java.util.Set;
import javax.servlet.Registration;

/* loaded from: classes5.dex */
public interface ServletRegistration extends Registration {

    /* loaded from: classes5.dex */
    public interface Dynamic extends Registration.Dynamic, ServletRegistration {
        void setLoadOnStartup(int i);

        void setMultipartConfig(MultipartConfigElement multipartConfigElement);

        void setRunAsRole(String str);

        Set<String> setServletSecurity(ServletSecurityElement servletSecurityElement);
    }

    Set<String> addMapping(String... strArr);

    Collection<String> getMappings();

    String getRunAsRole();
}
