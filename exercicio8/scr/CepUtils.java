public final class CepUtils {
    private CepUtils() {}

    /** Normaliza e valida CEP: exatamente 8 dígitos. Lança CepInvalidoException. */
    public static String validar(String cep) {
        if (cep == null) throw new CepInvalidoException("CEP não pode ser nulo.");
        String limpo = cep.replaceAll("\\D", "");
        if (!limpo.matches("\\d{8}")) {
            throw new CepInvalidoException("CEP inválido: use exatamente 8 dígitos.");
        }
        return limpo;
    }

    /** “Mesma região” (simplificada): 1º dígito igual. */
    public static boolean mesmaRegiao(String cepA, String cepB) {
        String a = validar(cepA);
        String b = validar(cepB);
        return a.charAt(0) == b.charAt(0);
    }
}

