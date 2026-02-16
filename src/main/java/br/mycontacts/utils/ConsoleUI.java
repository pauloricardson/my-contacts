package br.mycontacts.utils;

import java.util.Scanner;

public class ConsoleUI {
    public static void traco() {
        System.out.println("------------------------------------------------------------");
    }

    public static void limparConsole() {
        for (int i = 0; i < 40; i++) {
            System.out.println();
        }
    }

    public static void pausar(Scanner teclado) {
        System.out.println("Pressione ENTER para continuar...");
        teclado.nextLine();
    }

    public static String lerString(Scanner teclado, String mensagem) {
        String texto;

        while (true) {
            System.out.print(mensagem + " ");
            texto = teclado.nextLine().trim();

            if (!texto.isEmpty()) {
                return texto;
            }
            System.out.println("Entrada inválida, tente novamente.");
        }
    }

    public static int lerInt(Scanner teclado, String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem + " ");
                return Integer.parseInt(teclado.nextLine());
            } catch (Exception e) {
                System.out.println("Opção inválida, tente novamente.");
            }
        }
    }

    public static long lerLong(Scanner teclado, String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem + " ");
                return Long.parseLong(teclado.nextLine());
            } catch (Exception e) {
                System.out.println("Valor inválido, tente novamente.");
            }
        }
    }

    public static boolean confirmar(Scanner teclado, String mensagem) {
        while (true) {
            System.out.println(mensagem + " ");
            String entrada = teclado.nextLine().trim().toLowerCase();

            if (entrada.equals("s")) return true;
            if (entrada.equals("n")) return false;

            System.out.println("Valor inválido, tente novamente.");
        }
    }
}
