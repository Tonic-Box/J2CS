package com.tonic.j2cs.report;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Collects everything the run wants to tell the user: discovered classes, methods that
 * degraded to NotSupportedException stubs, stubbed types, and known semantic divergences.
 */
public final class TranspileReport {

    private String input = "";
    private String entryClass = "";
    private final List<String> classes = new ArrayList<>();
    private final List<String> unsupportedMethods = new ArrayList<>();
    private final List<String> stubbedTypes = new ArrayList<>();
    private final List<String> divergences = new ArrayList<>();

    public void setInput(String input) {
        this.input = input;
    }

    public void setEntryClass(String entryClass) {
        this.entryClass = entryClass;
    }

    public void classDiscovered(String internalName) {
        classes.add(internalName);
    }

    public void unsupportedMethod(String owner, String name, String desc, String reason) {
        unsupportedMethods.add(owner + "." + name + desc + ": " + reason);
    }

    public void stubbedType(String internalName) {
        stubbedTypes.add(internalName);
    }

    public void divergence(String text) {
        divergences.add(text);
    }

    public String getInput() {
        return input;
    }

    public String getEntryClass() {
        return entryClass;
    }

    public List<String> getClasses() {
        return Collections.unmodifiableList(classes);
    }

    public List<String> getUnsupportedMethods() {
        return Collections.unmodifiableList(unsupportedMethods);
    }

    public List<String> getStubbedTypes() {
        return Collections.unmodifiableList(stubbedTypes);
    }

    public List<String> getDivergences() {
        return Collections.unmodifiableList(divergences);
    }
}
