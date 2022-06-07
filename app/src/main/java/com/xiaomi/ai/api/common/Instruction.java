package com.xiaomi.ai.api.common;

import com.xiaomi.common.Optional;
import java.util.List;

/* loaded from: classes3.dex */
public class Instruction<T> extends Message<InstructionHeader, T> {
    public Instruction() {
    }

    public Instruction(InstructionHeader instructionHeader, T t) {
        super(instructionHeader, t);
    }

    public String getId() {
        return getHeader().getId();
    }

    public Optional<String> getDialogId() {
        return getHeader().getDialogId();
    }

    public Instruction<T> setDialogId(String str) {
        getHeader().setDialogId(str);
        return this;
    }

    public Instruction<T> setDependence(InstructionDependence instructionDependence) {
        getHeader().setDependence(instructionDependence);
        return this;
    }

    public Optional<String> getDependenceId() {
        Optional<InstructionDependence> dependence = getHeader().getDependence();
        if (dependence == null || !dependence.isPresent()) {
            return Optional.empty();
        }
        return Optional.ofNullable(getHeader().getDependence().get().getId());
    }

    public Optional<String> getDependencePredicate() {
        Optional<InstructionDependence> dependence = getHeader().getDependence();
        if (dependence == null || !dependence.isPresent()) {
            return Optional.empty();
        }
        Optional<String> predicate = getHeader().getDependence().get().getPredicate();
        return predicate == null ? Optional.empty() : predicate;
    }

    public boolean checkDependence(String str, String str2) {
        if (str2 == null) {
            str2 = "true";
        }
        Optional<InstructionDependence> dependence = getHeader().getDependence();
        if (!dependence.isPresent()) {
            return false;
        }
        InstructionDependence instructionDependence = dependence.get();
        return instructionDependence.getId().equals(str) && (instructionDependence.getPredicate().isPresent() ? instructionDependence.getPredicate().get() : "true").equals(str2);
    }

    public void setDependenceSuccess(List<Instruction> list) {
        setDependenceImpl(list, "true");
    }

    public void setDependenceFailure(List<Instruction> list) {
        setDependenceImpl(list, "false");
    }

    private void setDependenceImpl(List<Instruction> list, String str) {
        if (list == null || list.size() == 0) {
            throw new IllegalArgumentException("dependence instruction list is empty.");
        }
        for (Instruction instruction : list) {
            instruction.setDependence(new InstructionDependence(getId(), Optional.of(str)));
            if (getDialogId() != null && getDialogId().isPresent()) {
                instruction.setDialogId(getDialogId().get());
            }
        }
    }
}
