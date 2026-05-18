package br.mycontacts.controller;

import br.mycontacts.models.Contato;
import br.mycontacts.models.ContatoComercial;
import br.mycontacts.service.AgendaService;
import br.mycontacts.service.AgendaSingleton;
import br.mycontacts.utils.Alerts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class EditarContatoController implements Initializable {

    @FXML private CheckBox chkComercial;
    @FXML private VBox boxEmpresa;
    @FXML private TextField txtEmpresa;

    @FXML private TextField txtNome;
    @FXML private TextField txtTelefone;
    @FXML private TextField txtEmail;

    private final AgendaService agendaService = AgendaSingleton.agenda;

    // Guarda o ID do contato que está sendo editado para sabermos quem alterar no MySQL
    private long idContatoAtual;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        boxEmpresa.visibleProperty().bind(chkComercial.selectedProperty());
        boxEmpresa.managedProperty().bind(chkComercial.selectedProperty());
    }

    // MÉTODO CHAVE: O MainViewController vai chamar esse método para injetar o contato selecionado aqui
    public void setContatoParaEditar(Contato contato) {
        this.idContatoAtual = contato.getId();
        this.txtNome.setText(contato.getNome());
        this.txtTelefone.setText(contato.getTelefone());
        this.txtEmail.setText(contato.getEmail());

        // Se for comercial, marca o checkbox e preenche a empresa
        if (contato instanceof ContatoComercial) {
            chkComercial.setSelected(true);
            txtEmpresa.setText(((ContatoComercial) contato).getEmpresa());
        } else {
            chkComercial.setSelected(false);
            txtEmpresa.setText("");
        }
    }

    @FXML
    void salvarAlteracoes(ActionEvent event) {
        try {
            String nome = txtNome.getText();
            String telefone = txtTelefone.getText();
            String email = txtEmail.getText();

            Contato contatoAtualizado;

            // Remonta o objeto usando o construtor completo (mantendo o ID original do banco)
            if (chkComercial.isSelected()) {
                String empresa = txtEmpresa.getText();
                contatoAtualizado = new ContatoComercial(idContatoAtual, nome, telefone, email, empresa, null);
            } else {
                contatoAtualizado = new Contato(idContatoAtual, nome, telefone, email, null);
            }

            // Manda para o Service atualizar no banco
            agendaService.atualizarContato(contatoAtualizado);

            Alerts.showAlerts("Sucesso", null, "Contato atualizado com sucesso!", Alert.AlertType.INFORMATION);

            // Fecha a janela
            Button btn = (Button) event.getSource();
            Stage stage = (Stage) btn.getScene().getWindow();
            stage.close();

        } catch (IllegalArgumentException e) {
            Alerts.showAlerts("Erro de Validação", null, e.getMessage(), Alert.AlertType.ERROR);
        } catch (Exception e) {
            Alerts.showAlerts("Erro", null, "Ocorreu um erro inesperado ao atualizar.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void cancelar(ActionEvent event) {
        Button botaoCancelar = (Button) event.getSource();
        Stage stage = (Stage) botaoCancelar.getScene().getWindow();
        stage.close();
    }
}
