package pe.edu.cibertec.patitas_backend.session;

public class UserActual {

    private static String tipoDocumento;
    private static String numeroDocumento;

    public static void setUserData(String tipoDoc, String numeroDoc) {
        tipoDocumento = tipoDoc;
        numeroDocumento = numeroDoc;
    }

    public static String getTipoDocumento() {
        return tipoDocumento;
    }

    public static String getNumeroDocumento() {
        return numeroDocumento;
    }

    public static void clear() {
        tipoDocumento = null;
        numeroDocumento = null;
    }

}

