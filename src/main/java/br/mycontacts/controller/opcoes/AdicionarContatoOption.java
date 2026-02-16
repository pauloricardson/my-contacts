package br.mycontacts.controller.opcoes;

import br.mycontacts.models.Contato;
import br.mycontacts.models.ContatoComercial;
import br.mycontacts.service.AgendaService;
import br.mycontacts.utils.ConsoleUI;

public class AdicionarContatoOption implements MenuOption {

    private AgendaService agendaService;

    public AdicionarContatoOption(AgendaService agendaService) {
        this.agendaService = agendaService;
    }


    @Override
    public String nomeFuncao() {
        return "Adicionar Contato";
    }

    @Override
    public void executar() {

        ConsoleUI.traco();
        System.out.println(nomeFuncao());
        ConsoleUI.traco();

        String nome = ConsoleUI.lerString("Nome:");
        String telefone = ConsoleUI.lerString("Telefone:");
        String email = ConsoleUI.lerString("E-mail:");

        ConsoleUI.traco();

        System.out.print("""
                Tipo de contato:
                1. Pessoal
                2. Comercial
                """);
        int opcao;

        while (true) {
            opcao = ConsoleUI.lerInt("Opção:");
            if (opcao == 1 || opcao == 2) {
                break;
            }
            System.out.println("Opção inválida, tente novamente.");
        }

        String comercial = "\0";

        if (opcao == 2) {
            comercial = ConsoleUI.lerString("Empresa:");
        }

        if (!ConsoleUI.confirmar("Confirmar (s/n)?")) {
            System.out.println("Opreção cancelada.");
            return;
        }

        try {
            if (opcao == 1) {
                Contato contato = new Contato(nome, telefone, email);
                agendaService.adicionarContato(contato);
            } else {
                Contato contato = new ContatoComercial(nome, telefone, email, comercial);
                agendaService.adicionarContato(contato);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Contato adicionado com sucesso!");

    }
}
