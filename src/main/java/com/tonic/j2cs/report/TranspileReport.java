package com.tonic.j2cs.report;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Collects everything the run wants to tell the user: discovered classes, methods that
 * degraded to NotSupportedException stubs, stubbed types, and known semantic divergences.
 */
public final class TranspileReport {

    private String input = "";
    private String entryClass = "";
    private final List<String> classes = new ArrayList<>();
    private final List<String> unsupportedMethods = new ArrayList<>();
    private final List<String> unsupportedReasons = new ArrayList<>();
    private int structuredBodies;
    private int classicBodies;
    private final List<String> classicFallbackReasons = new ArrayList<>();
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
        unsupportedReasons.add(reason);
    }

    /**
     * The stubbed-method reasons rolled up into a ranked worklist: each reason is split into a
     * category (text before the first ": ") and a detail (the remainder, e.g. the missing shim
     * member). Categories are ordered by descending method count, details within a category by
     * descending count. Points directly at which shims to add.
     */
    public List<String> unsupportedSummary() {
        Map<String, Integer> categoryTotals = new LinkedHashMap<>();
        Map<String, Map<String, Integer>> details = new LinkedHashMap<>();
        for (String reason : unsupportedReasons) {
            int split = reason.indexOf(": ");
            String category = split < 0 ? reason : reason.substring(0, split);
            categoryTotals.merge(category, 1, Integer::sum);
            details.computeIfAbsent(category, k -> new LinkedHashMap<>());
            if (split >= 0) {
                details.get(category).merge(reason.substring(split + 2), 1, Integer::sum);
            }
        }
        List<String> lines = new ArrayList<>();
        categoryTotals.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .forEach(category -> {
                    lines.add(category.getKey() + " (" + category.getValue() + ")");
                    details.get(category.getKey()).entrySet().stream()
                            .sorted(Map.Entry.<String, Integer>comparingByValue().reversed()
                                    .thenComparing(Map.Entry.comparingByKey()))
                            .forEach(detail -> lines.add("  " + detail.getKey()
                                    + (detail.getValue() > 1 ? " x" + detail.getValue() : "")));
                });
        return lines;
    }

    public void stubbedType(String internalName) {
        stubbedTypes.add(internalName);
    }

    public void divergence(String text) {
        divergences.add(text);
    }

    public void structuredBody() {
        structuredBodies++;
    }

    public void classicBody(String reason) {
        classicBodies++;
        classicFallbackReasons.add(reason);
    }

    public int getStructuredBodies() {
        return structuredBodies;
    }

    public int getClassicBodies() {
        return classicBodies;
    }

    public List<String> getClassicFallbackReasons() {
        return Collections.unmodifiableList(classicFallbackReasons);
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
