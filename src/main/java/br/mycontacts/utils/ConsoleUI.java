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

    public static int lerInt(Scanner teclado) {
        while (true) {
            try {
                return Integer.parseInt(teclado.nextLine());
            } catch (Exception e) {
                System.out.println("Opção inválida, tente novamente > ");
            }
        }
    }

    public static long lerLong(Scanner teclado) {
        while (true) {
            try {
                return Long.parseLong(teclado.nextLine());
            } catch (Exception e) {
                System.out.println("Valor inválido, tente novamente > ");
            }
        }
    }

    public static boolean confirmar(Scanner teclado) {
        while (true) {
            String entrada = teclado.nextLine().trim().toLowerCase();

            if (entrada.equals("s")) return true;
            if (entrada.equals("n")) return false;

            System.out.println("Digite apenas (s/n): ");
        }
    }
}
