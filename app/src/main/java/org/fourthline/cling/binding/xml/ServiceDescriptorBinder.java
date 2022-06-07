package org.fourthline.cling.binding.xml;

import org.fourthline.cling.model.ValidationException;
import org.fourthline.cling.model.meta.Service;
import org.w3c.dom.Document;

/* loaded from: classes5.dex */
public interface ServiceDescriptorBinder {
    Document buildDOM(Service service) throws DescriptorBindingException;

    <T extends Service> T describe(T t, String str) throws DescriptorBindingException, ValidationException;

    <T extends Service> T describe(T t, Document document) throws DescriptorBindingException, ValidationException;

    String generate(Service service) throws DescriptorBindingException;
}
