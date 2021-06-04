package bd.daos;

import java.sql.*;
import bd.*;
import bd.core.*;
import bd.dbos.*;

public class LabirintosBD
{
    public static boolean cadastrado (String ip, String nome) throws Exception
    {
        boolean retorno = false;

        try
        {
            String sql;

            sql = "SELECT * " +
                  "FROM LABIRINTOS " +
                  "WHERE IP = ? AND NOME = ?";

            BDOracle.COMANDO.prepareStatement (sql);

            BDOracle.COMANDO.setString 	(1, ip);
            BDOracle.COMANDO.setString 	(2, nome);

            MeuResultSet resultado = (MeuResultSet)BDOracle.COMANDO.executeQuery ();

            retorno = resultado.first(); // pode-se usar resultado.last() ou resultado.next() ou resultado.previous() ou resultado.absotule(numeroDaLinha)

            /* // ou, se preferirmos,

            String sql;

            sql = "SELECT COUNT(*) AS QUANTOS " +
                  "FROM LIVROS " +
                  "WHERE CODIGO = ?";

            BDOracle.COMANDO.prepareStatement (sql);

            BDOracle.COMANDO.setInt (1, codigo);

            MeuResultSet resultado = (MeuResultSet)BDOracle.COMANDO.executeQuery ();

            resultado.first();

            retorno = resultado.getInt("QUANTOS") != 0;

            */
        }
        catch (SQLException erro)
        {
            throw new Exception ("Erro ao procurar labirinto");
        }

        return retorno;
    }

    public static void incluir (LabirintoBD lab) throws Exception
    {
        if (lab==null)
            throw new Exception ("Labirinto nao fornecido");

        try
        {
            String sql;
            if (!(cadastrado(lab.getIp(), lab.getNome())))
            {
            	sql = 	"INSERT INTO LABIRINTOS " +
                        "(IP, NOME, CREATED_AT, UPDATED_AT, LAB)" +
                        "VALUES " +
                        "(?,?,?,?,?)";

                  BDOracle.COMANDO.prepareStatement (sql);

                  BDOracle.COMANDO.setString 		(1, lab.getIp ());
                  BDOracle.COMANDO.setString  		(2, lab.getNome ());
                  BDOracle.COMANDO.setDate    		(3, lab.getCreated ());
                  BDOracle.COMANDO.setDate    		(4, lab.getUpdated ());
                  BDOracle.COMANDO.setString    	(5, lab.getLab ());

                  BDOracle.COMANDO.executeUpdate ();
                  BDOracle.COMANDO.commit        ();
                  
                  System.out.println("Labirinto incluido com sucesso");
            }
            else
            {
            	alterar(lab);
            }
        }
        catch (SQLException erro)
        {
			BDOracle.COMANDO.rollback();
            throw new Exception ("Erro ao inserir labirinto");
        }
    }

    public static void excluir (String ip, String nome) throws Exception
    {
        if (!cadastrado (ip, nome))
            throw new Exception ("Nao cadastrado");

        try
        {
            String sql;

            sql = "DELETE FROM LABIRINTOS " +
                  "WHERE IP = ? AND NOME = ?";

            BDOracle.COMANDO.prepareStatement (sql);

            BDOracle.COMANDO.setString	(1, ip);
            BDOracle.COMANDO.setString 	(2, nome);

            BDOracle.COMANDO.executeUpdate ();
            BDOracle.COMANDO.commit        ();      
            
            System.out.println("Labirinto excluido com sucesso");
            }
        catch (SQLException erro)
        {
			BDOracle.COMANDO.rollback();
            throw new Exception ("Erro ao excluir labirinto");
        }
    }

    public static void alterar (LabirintoBD lab) throws Exception
    {
        if (lab==null)
            throw new Exception ("Labirinto nao fornecido");

        if (!cadastrado (lab.getIp(), lab.getNome()))
            throw new Exception ("Nao cadastrado");

        try
        {
            String sql;

            sql = "UPDATE LABIRINTOS " +
                  "SET UPDATED_AT=?, LAB=? " +
                  "WHERE IP = ? AND NOME = ? ";

            BDOracle.COMANDO.prepareStatement (sql);

            BDOracle.COMANDO.setDate    	(1, lab.getUpdated ());
            BDOracle.COMANDO.setString    	(2, lab.getLab ());
            BDOracle.COMANDO.setString 		(3, lab.getIp ());
            BDOracle.COMANDO.setString  	(4, lab.getNome ());

            BDOracle.COMANDO.executeUpdate ();
            
            BDOracle.COMANDO.commit        ();
            
            System.out.println("Labirinto alterado com sucesso");
        }
        catch (SQLException erro)
        {
			BDOracle.COMANDO.rollback();
            throw new Exception ("Erro ao atualizar dados de labirinto");
        }
    }

    public static LabirintoBD getLab (String ip, String nome) throws Exception
    {
        LabirintoBD lab = null;

        try
        {
            String sql;

            sql = "SELECT * " +
                  "FROM Labirintos " +
                  "WHERE IP = ? AND NOME = ?";

            BDOracle.COMANDO.prepareStatement (sql);

            BDOracle.COMANDO.setString 	(1, ip);
            BDOracle.COMANDO.setString 	(2, nome);

            MeuResultSet resultado = (MeuResultSet)BDOracle.COMANDO.executeQuery ();

            if (!resultado.first())
                throw new Exception ("Nao cadastrado");

            lab = new LabirintoBD (	resultado.getString	("IP"),
                               		resultado.getString	("NOME"),
                               		resultado.getDate 	("CREATED_AT"),
                               		resultado.getDate 	("UPDATED_AT"),
                               		resultado.getString	("LAB"));
        }
        catch (SQLException erro)
        {
            throw new Exception ("Erro ao procurar labirinto");
        }

        return lab;
    }

    public static MeuResultSet getLabs () throws Exception
    {
        MeuResultSet resultado = null;

        try
        {
            String sql;

            sql = "SELECT * " +
                  "FROM Labirintos";

            BDOracle.COMANDO.prepareStatement (sql);

            resultado = (MeuResultSet)BDOracle.COMANDO.executeQuery ();
        }
        catch (SQLException erro)
        {
            throw new Exception ("Erro ao recuperar labirintos");
        }

        return resultado;
    }
}
