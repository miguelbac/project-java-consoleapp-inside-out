package org.factoriaf5.project_inside_out.models;

public enum Emotion {
    ALEGRIA("Alegría"),
    TRISTEZA("Tristeza"),
    IRA("Ira"),
    ASCO("Asco"),
    MIEDO("Miedo"),
    ANSIEDAD("Ansiedad"),
    ENVIDIA("Envidia"),
    VERGUENZA("Vergüenza"),
    ABURRIMIENTO("Aburrimiento"),
    NOSTALGIA("Nostalgia");

    private final String displayName;

    private Emotion(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}