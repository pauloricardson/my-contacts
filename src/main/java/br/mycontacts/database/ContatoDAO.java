package br.mycontacts.database;

import br.mycontacts.models.Contato;
import br.mycontacts.models.ContatoComercial;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContatoDAO {
    public void salvar(Contato contato) {
        String sql = "INSERT INTO contatos (nome, telefone, email, organizacao) VALUES (?, ?, ?, ?)";

        try (Connection conn = Conection.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, contato.getNome());
            stmt.setString(2, contato.getTelefone());
            stmt.setString(3, contato.getEmail());

            // Pulo do gato usando Polimorfismo:
            // Verificamos se o objeto que chegou é uma instância da classe filha (ContatoComercial)
            if (contato instanceof ContatoComercial) {
                ContatoComercial cc = (ContatoComercial) contato; // Downcasting seguro
                stmt.setString(4, cc.getEmpresa());
            } else {
                stmt.setNull(4, java.sql.Types.VARCHAR); // Se for comum, salva como NULL no MySQL
            }

            stmt.executeUpdate();
            System.out.println("Contato salvo no banco de dados com sucesso!");

        } catch (SQLException e) {
            System.err.println("Erro ao salvar contato no MySQL: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Não foi possível salvar o contato.");
        }
    }

    public ObservableList<Contato> buscarTodos() {
        ObservableList<Contato> lista = FXCollections.observableArrayList();
        // Buscando todas as colunas necessárias do MySQL
        String sql = "SELECT id, nome, telefone, email, organizacao, data_criacao FROM contatos";

        try (Connection conn = Conection.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                long id = rs.getLong("id");
                String nome = rs.getString("nome");
                String telefone = rs.getString("telefone");
                String email = rs.getString("email");
                String empresa = rs.getString("organizacao");
                String dataCriacao = rs.getString("data_criacao"); // Puxa o timestamp do banco

                if (empresa != null && !empresa.isBlank()) {
                    // Usa o Construtor 2 da classe Comercial
                    lista.add(new ContatoComercial(id, nome, telefone, email, empresa, dataCriacao));
                } else {
                    // Usa o Construtor 2 da classe base Contato
                    lista.add(new Contato(id, nome, telefone, email, dataCriacao));
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar contatos no banco: " + e.getMessage());
            e.printStackTrace();
        }
        return lista;
    }

    public boolean deletar(long id) {
        String sql = "DELETE FROM contatos WHERE id = ?";

        try (Connection conn = Conection.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            // rowsAffected recebe a quantidade de linhas apagadas (deve ser 1 se achou o ID)
            int rowsAffected = stmt.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao deletar contato no MySQL: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Não foi possível excluir o contato do banco de dados.");
        }
    }

    public void atualizar(Contato contato) {
        String sql = "UPDATE contatos SET nome = ?, telefone = ?, email = ?, organizacao = ? WHERE id = ?";

        try (Connection conn = Conection.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, contato.getNome());
            stmt.setString(2, contato.getTelefone());
            stmt.setString(3, contato.getEmail());

            if (contato instanceof ContatoComercial) {
                stmt.setString(4, ((ContatoComercial) contato).getEmpresa());
            } else {
                stmt.setNull(4, java.sql.Types.VARCHAR);
            }

            stmt.setLong(5, contato.getId());

            stmt.executeUpdate();
            System.out.println("Contato atualizado no MySQL com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao atualizar o contato no banco.");
        }
    }
}
