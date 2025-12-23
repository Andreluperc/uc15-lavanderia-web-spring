package br.senac.pi.lavanderia.util;

public final class CpfUtils {
    private CpfUtils() {}

    public static String normalizar(String cpf) {
        if (cpf == null) return null;
        return cpf.replaceAll("\\D", "");
    }

    public static boolean isValidoBasico(String cpf) {
        String n = normalizar(cpf);
        if (n == null) return false;
        if (n.length() != 11) return false;
        return !n.matches("([0-9])\\1{10}");
    }
}
