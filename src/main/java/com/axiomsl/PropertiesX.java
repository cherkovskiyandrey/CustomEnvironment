package com.axiomsl;

public enum PropertiesX {
    INSTANCE;

    private final PropX<Integer> axiomApplicationDir = new PropX<>("axiom.archival.dir", Integer.class);

    public PropX<Integer> getAxiomApplicationDir() {
        return axiomApplicationDir;
    }
}
