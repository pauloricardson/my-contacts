package br.mycontacts.utils;

import java.util.regex.Pattern;

public class ValidadorTelefone {

    private static final Pattern TELEFONE_BR = Pattern.compile("^\\(?\\d{2}\\)?\\s?(9?\\d{4})-?\\d{4}$");

    public static boolean validarTelefone(String telefone) {
        if (telefone == null || telefone.isBlank()) {
            return false;
        }
        return TELEFONE_BR.matcher(telefone).matches();
    }

}
