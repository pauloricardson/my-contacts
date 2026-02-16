package br.mycontacts.controller;

import br.mycontacts.controller.opcoes.AdicionarContatoOption;
import br.mycontacts.controller.opcoes.MenuOption;
import br.mycontacts.service.AgendaService;
import br.mycontacts.utils.ConsoleUI;

import java.util.ArrayList;
import java.util.List;

public class MenuPrincipal {

    private List<MenuOption> opcoes = new ArrayList<>();

    public MenuPrincipal() {
        AgendaService agendaService = new AgendaService();
        opcoes.add(new AdicionarContatoOption(agendaService));
    }

    public void inciar() {
        opcoes.get(0).executar();
    }
}
