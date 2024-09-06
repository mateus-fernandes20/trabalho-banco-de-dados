package trabalhobdd;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class insert {

    private Connection connect() {
        String url = "jdbc:sqlite:trabalho.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void inserir(Object[][] data) {
    	// inserção de alunos com base numa tabela de dados pré montada
        String sql = "INSERT INTO Aluno(matricula, academia, nome, endereco) VALUES(?, ?, ?, ?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            for (Object[] row : data) {
                String matricula = (String) row[0];
                String academia = (String) row[1];
                String nome = (String) row[2];
                String endereco = (String) row[3];
                
                pstmt.setString(1, matricula);
                pstmt.setString(2, academia);
                pstmt.setString(3, nome);
                pstmt.setString(4, endereco);

                pstmt.addBatch();
            }

            int[] comando = pstmt.executeBatch();
            System.out.println(comando.length + " Sucesso");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deletar(String matricula) {
    	// deletar aluno com base em sua matricula
        String sql = "DELETE FROM Aluno WHERE matricula = ?";

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql))
        	{
            pstmt.setString(1, matricula);
            
            int comando = pstmt.executeUpdate();
            
            if (comando > 0)
            {
                System.out.println("Deletado com sucesso.");
            } else
            {
                System.out.println("Falha");
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    public void atualizar(String matricula, String nome, String academia, String endereco) {
    	//comando para atualizar dados dos alunos
        String sql = "UPDATE aluno SET academia = ?, nome = ?, endereco = ? WHERE matricula = ?";

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            pstmt.setString(1, nome);
            pstmt.setString(2, academia);
            pstmt.setString(3, matricula);
            pstmt.setString(4, endereco);

            int comando = pstmt.executeUpdate();
            if (comando > 0)
            {
                System.out.println("Atualizado com sucesso.");
            } else
            {
                System.out.println("Falha.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void busca(String nome)
    {
    	//comando para buscar alunos com base em seu nome
        String sql = "SELECT matricula, academia, nome, endereco FROM Aluno WHERE name LIKE ?";

        try (Connection conn = this.connect();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            
        	statement.setString(1, "%" + nome + "%");

            ResultSet rs = statement.executeQuery();

            while (rs.next())
            {
                System.out.println("Matricula: " + rs.getInt("matricula"));
                System.out.println("Nome: " + rs.getString("nome"));
                System.out.println("Academia: " + rs.getInt("academia"));
                System.out.println("Endereco: " + rs.getInt("endereco"));
            }

        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }


    
    public static void main(String[] args)
    {
    	insert aluno = new insert();
    	Object[][] data = {
                {"128937", "Smart Fit", "Carlos", "Parquelandia"},
                {"535463", "Green Life", "Andre", "Pici"},
                {"846567", "Selfit", "Bia", "Aldeota"},
                {"345467", "Gavioes", "Pedro", "Bom Jardim"}
            };
    	aluno.inserir(data);
    	aluno.inserir(data);
    	aluno.atualizar("128937", "Joao", "Green Life", "Bela Vista");
    	aluno.deletar("128937");
    	aluno.busca("Carlos");
    }
}
