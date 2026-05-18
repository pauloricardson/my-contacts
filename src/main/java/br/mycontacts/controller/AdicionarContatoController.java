package br.mycontacts.controller;

import br.mycontacts.database.ContatoDAO;
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

public class AdicionarContatoController implements Initializable {

    @FXML private CheckBox chkComercial;
    @FXML private VBox boxEmpresa;
    @FXML private TextField txtEmpresa;

    @FXML private TextField txtNome;
    @FXML private TextField txtTelefone;
    @FXML private TextField txtEmail;

    private final ContatoDAO contatoDAO = new ContatoDAO();

    private final AgendaService agendaService = AgendaSingleton.agenda;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        boxEmpresa.visibleProperty().bind(chkComercial.selectedProperty());
        boxEmpresa.managedProperty().bind(chkComercial.selectedProperty());
    }

    @FXML
    public void cancelar(ActionEvent event) {
        Button botaoCancelar = (Button) event.getSource();
        Stage stage = (Stage) botaoCancelar.getScene().getWindow();
        stage.close();
    }

    @FXML
    void adicionar(ActionEvent event) {
        try {
            String nome = txtNome.getText();
            String telefone = txtTelefone.getText();
            String email = txtEmail.getText();

            Contato novoContato;

            if (chkComercial.isSelected()) {
                String empresa = txtEmpresa.getText();
                novoContato = new ContatoComercial(nome, telefone, email, empresa);
            } else {
                novoContato = new Contato(nome, telefone, email);
            }

            agendaService.adicionarContato(novoContato);

            Alerts.showAlerts("Sucesso", null, "Contato salvo com sucesso!", Alert.AlertType.INFORMATION);

            Button btn = (Button) event.getSource();
            Stage stage = (Stage) btn.getScene().getWindow();
            stage.close();

        } catch (IllegalArgumentException e) {
            Alerts.showAlerts("Erro de Validação", null, (e.getMessage()), Alert.AlertType.ERROR);
        } catch (Exception e) {
            Alerts.showAlerts("Erro", null, "Ocorreu um erro inesperado", Alert.AlertType.ERROR);
        }
    }
}
