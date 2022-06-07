package org.fourthline.cling.support.model;

import org.w3c.dom.Element;

/* loaded from: classes5.dex */
public class PersonWithRole extends Person {
    private String role;

    public PersonWithRole(String str) {
        super(str);
    }

    public PersonWithRole(String str, String str2) {
        super(str);
        this.role = str2;
    }

    public String getRole() {
        return this.role;
    }

    public void setOnElement(Element element) {
        element.setTextContent(toString());
        if (getRole() != null) {
            element.setAttribute("role", getRole());
        }
    }
}
