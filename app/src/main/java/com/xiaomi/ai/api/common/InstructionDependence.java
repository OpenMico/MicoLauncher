package com.xiaomi.ai.api.common;

import com.xiaomi.common.Optional;

/* loaded from: classes3.dex */
public class InstructionDependence {
    private String id;
    private Optional<String> predicate;

    public InstructionDependence() {
    }

    public InstructionDependence(String str, Optional<String> optional) {
        this.id = str;
        this.predicate = optional;
    }

    public Optional<String> getPredicate() {
        Optional<String> optional = this.predicate;
        return Optional.ofNullable((optional == null || !optional.isPresent()) ? null : this.predicate.get());
    }

    public void setPredicate(String str) {
        this.predicate = Optional.ofNullable(str);
    }

    public String getId() {
        return this.id;
    }

    public void setId(String str) {
        this.id = str;
    }
}
