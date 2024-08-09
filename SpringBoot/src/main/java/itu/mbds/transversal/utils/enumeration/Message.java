package itu.mbds.transversal.utils.enumeration;

public enum Message {

    USERNAME_USED("Nom d'utilisateur déjà utilisé."),
    BAD_CREDENTIAL("Invalide nom d'utilisateur ou mot de passe."),
    ACCOUNT_DISABLED("Compte non activé."),

    NOT_FOUND(" est introuvable.");

    private final String name;

    Message(String s) {
        name = s;
    }

    public boolean equalsName(String otherName) {
        // (otherName == null) check is not needed because name.equals(null) returns false
        return name.equals(otherName);
    }

    public String toString() {
        return this.name;
    }

    public String byId(String entity, int id) {
        return entity + "avec l'id : " + id + this.name;
    }


}
