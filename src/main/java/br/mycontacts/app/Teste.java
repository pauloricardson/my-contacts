package br.mycontacts.app;

import br.mycontacts.models.Contato;
import br.mycontacts.utils.ConsoleUI;

import java.util.Scanner;

public class Teste {
    public static void main(String[] args) {

        Scanner teclado = new Scanner(System.in);

        ConsoleUI.lerString(teclado, "Nome: ");
    }
}
