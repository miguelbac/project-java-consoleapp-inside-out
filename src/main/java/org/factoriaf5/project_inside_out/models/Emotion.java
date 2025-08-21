package org.factoriaf5.project_inside_out.models;

public enum Emotion {
    ALEGRIA(1, "Alegría"),
    TRISTEZA(2, "Tristeza"),
    IRA(3, "Ira"),
    ASCO(4, "Asco"),
    MIEDO(5, "Miedo"),
    ANSIEDAD(6, "Ansiedad"),
    ENVIDIA(7, "Envidia"),
    VERGUENZA(8, "Vergüenza"),
    ABURRIMIENTO(9, "Aburrimiento"),
    NOSTALGIA(10, "Nostalgia");

    private final int option;
    private final String displayName;

    Emotion(int option, String displayName) {
        this.option = option;
        this.displayName = displayName;
    }

    public int getOption() {
        return option;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static Emotion fromOption(int option) {
        for (Emotion e : values()) {
            if (e.option == option) return e;
        }
        throw new IllegalArgumentException("Opción inválida: " + option);
    }
}
