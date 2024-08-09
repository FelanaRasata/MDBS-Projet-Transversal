package itu.mbds.transversal.utils.enumeration;

public enum EntityValue {
    USER("Utilisateur"),
    ITEM("Objet"),
    ITEM_CATEGORY("Catégorie d'objet");

    private final String name;

    EntityValue(String s) {
        name = s;
    }

    public String toString() {
        return this.name;
    }
}

