package itu.mbds.transversal.utils.enumeration;

public enum Message {

    USERNAME_USED("Nom d'utilisateur déjà utilisé."),
    BAD_CREDENTIAL("Invalide nom d'utilisateur ou mot de passe."),
    ACCOUNT_DISABLED("Compte non activé."),
    UPLOAD_FAILED("Le téléchargement de l'image est un échec."),
    UPLOAD_NO_PICTURE("L'image est invalide."),

    ACCESS_DENIED("Vous n'êtes pas autorisé."),
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

    public String byId(EntityValue entity, long id) {
        return entity.toString() + " avec l'id : " + id + " " + this.name;
    }

    public String byEntity(EntityValue entity) {
        return entity.toString() + " " + this.name;
    }


}
