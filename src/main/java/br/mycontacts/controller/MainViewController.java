package br.mycontacts.controller;

import br.mycontacts.database.Conection;
import br.mycontacts.models.Contato;
import br.mycontacts.service.AgendaService;
import br.mycontacts.service.AgendaSingleton;
import br.mycontacts.utils.Alerts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {

    private final AgendaService agendaService = AgendaSingleton.agenda;

    @FXML
    private Button adicionarContato;
    @FXML
    private Button atualizarLista;

    @FXML
    private Button limparPainelLateral;

    @FXML private Button apagarContato;


    @FXML private TableView<Contato> tabelaContatos;
    //@FXML private TableColumn<Contato, Long> colId;
    @FXML private TableColumn<Contato, String> colNome;
    @FXML private TableColumn<Contato, String> colTelefone;
    @FXML private TableColumn<Contato, String> colEmail;
    @FXML private TableColumn<Contato, String> colEmpresa;
    @FXML private TableColumn<Contato, String> colData;

    @FXML private Label labelNome;
    @FXML private Label labelTelefone;
    @FXML private Label labelEmail;
    @FXML private Label labelOrganizacao;

    @FXML private TextField txtPesquisa;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // 1. Vincula as colunas com as Properties do JavaFX
        //colId.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        colNome.setCellValueFactory(cellData -> cellData.getValue().nomeProperty());
        colTelefone.setCellValueFactory(cellData -> cellData.getValue().telefoneProperty());
        colEmail.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        colData.setCellValueFactory(cellData -> cellData.getValue().dataCriacaoProperty());

        // Tratamento seguro para a coluna Empresa
//        colEmpresa.setCellValueFactory(cellData -> {
//            if (cellData.getValue() instanceof br.mycontacts.models.ContatoComercial) {
//                return ((br.mycontacts.models.ContatoComercial) cellData.getValue()).empresaProperty();
//            }
//            return new javafx.beans.property.SimpleStringProperty(""); // Fica vazio se for comum
//        });

        tabelaContatos.getSelectionModel().selectedItemProperty().addListener((observable, contatoAntigo, contatoSelecionado) -> {
            if (contatoSelecionado != null) {
                preencherPainelLateral(contatoSelecionado);
            } else {
                limparPainelLateral();
            }
        });

        txtPesquisa.textProperty().addListener((observable, textoAntigo, textoNovo) -> {
            filtrarContatosEmTempoReal(textoNovo);
        });

        // 2. Manda carregar as linhas vindas do MySQL
        carregarDadosTabela();
    }

    public void carregarDadosTabela() {
        // Busca do Service (Singleton) e joga na TableView
        tabelaContatos.setItems(javafx.collections.FXCollections.observableArrayList(
                br.mycontacts.service.AgendaSingleton.agenda.listarContatos()
        ));
    }

    @FXML
    public void atualizarLista(ActionEvent event) {
        carregarDadosTabela();
    }

    @FXML
    void adicionarContato(ActionEvent event) {
        try {
            // 1. Carrega o FXML da tela de adição
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/view/AdicionarContatoView.fxml"));
            javafx.scene.Parent root = loader.load();

            // 2. Cria e abre a nova janela (Stage)
            Stage stage = new Stage();
            //stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Adicionar Novo Contato");
            stage.setScene(new javafx.scene.Scene(root));

            // Bloqueia a janela de trás enquanto esta estiver aberta (Modality)
            stage.initModality(javafx.stage.Modality.WINDOW_MODAL);
            stage.initOwner(((Button) event.getSource()).getScene().getWindow());

            // 3. ABRE A TELA E ESPERA: O código para aqui até o usuário salvar ou cancelar
            stage.showAndWait();

            // 4. QUANDO A TELA FECHAR: Executa automaticamente a atualização da tabela!
            carregarDadosTabela();

        } catch (Exception e) {
            e.printStackTrace();
            br.mycontacts.utils.Alerts.showAlerts("Erro", null, "Não foi possível abrir a tela de cadastro.", Alert.AlertType.ERROR);
        }
    }

    private void preencherPainelLateral(Contato contato) {
        labelNome.setText(contato.getNome());
        labelTelefone.setText(contato.getTelefone());
        labelEmail.setText(contato.getEmail());

        // Verifica se o contato selecionado é Comercial para exibir a empresa
        if (contato instanceof br.mycontacts.models.ContatoComercial) {
            String empresa = ((br.mycontacts.models.ContatoComercial) contato).getEmpresa();
            labelOrganizacao.setText(empresa);
        } else {
            labelOrganizacao.setText("Nenhuma (Contato Comum)"); // Fica limpo se for comum
        }
    }

    public void limparPainelLateral(ActionEvent event) {
        labelNome.setText("");
        labelTelefone.setText("");
        labelEmail.setText("");
        labelOrganizacao.setText("");
    }

    void limparPainelLateral() {
        tabelaContatos.getSelectionModel().clearSelection();
        labelNome.setText("");
        labelTelefone.setText("");
        labelEmail.setText("");
        labelOrganizacao.setText("");
    }

    @FXML
    public void apagarContato(ActionEvent event) {
        Contato contatoSelecionado = tabelaContatos.getSelectionModel().getSelectedItem();

        if (contatoSelecionado == null) {
            Alerts.showAlerts("Aviso", null, "Selecione um contato na tabela primeiro!", Alert.AlertType.WARNING);
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar Exclusão");
        alert.setHeaderText(null);
        alert.setContentText("Tem certeza que deseja apagar o contato \"" + contatoSelecionado.getNome() + "\"?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                // Remove fisicamente do MySQL via Service -> DAO
                agendaService.removerContato(contatoSelecionado.getId());

                Alerts.showAlerts("Sucesso", null, "Contato excluído com sucesso!", Alert.AlertType.INFORMATION);
                limparPainelLateral();
                carregarDadosTabela();

            } catch (Exception e) {
                Alerts.showAlerts("Erro", null, "Não foi possível excluir o contato.", Alert.AlertType.ERROR);
            }
        }
    }

    private void filtrarContatosEmTempoReal(String termoBusca) {
        // 1. Se o usuário apagou tudo o que digitou, recarrega a tabela original com todos do banco
        if (termoBusca == null || termoBusca.isBlank()) {
            carregarDadosTabela();
            return;
        }

        // 2. Busca os contatos filtrados da Service (que usa a normalização de acentos)
        java.util.List<Contato> resultados = agendaService.buscarContatos(termoBusca);

        // 3. Atualiza a TableView instantaneamente com o resultado
        tabelaContatos.setItems(javafx.collections.FXCollections.observableArrayList(resultados));
    }

    @FXML
    void editarContato(ActionEvent event) {
        Contato contatoSelecionado = tabelaContatos.getSelectionModel().getSelectedItem();

        if (contatoSelecionado == null) {
            Alerts.showAlerts("Aviso", null, "Selecione um contato para editar!", Alert.AlertType.WARNING);
            return;
        }

        try {
            // 1. Carrega o FXML da tela de edição
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/view/EditarContatoView.fxml"));
            javafx.scene.Parent root = loader.load();

            // 2. Pega o Controller da tela de edição que acabou de ser carregada
            EditarContatoController controllerEdicao = loader.getController();

            // 3. PASSA o contato selecionado da tabela para o formulário de edição!
            controllerEdicao.setContatoParaEditar(contatoSelecionado);

            // 4. Abre a nova janela (Stage) em modo Modal (bloqueia a de trás até fechar)
            Stage stage = new Stage();
            stage.setTitle("Editar Contato");
            stage.setScene(new javafx.scene.Scene(root));
            stage.initModality(javafx.stage.Modality.WINDOW_MODAL);
            stage.initOwner(((Button) event.getSource()).getScene().getWindow());

            // Exibe a janela e espera ela fechar
            stage.showAndWait();

            // 5. Quando o usuário fechar a tela de edição (após salvar), atualiza a tabela automaticamente
            carregarDadosTabela();
            limparPainelLateral();

        } catch (Exception e) {
            e.printStackTrace();
            Alerts.showAlerts("Erro", null, "Não foi possível abrir a tela de edição.", Alert.AlertType.ERROR);
        }
    }
}