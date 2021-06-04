

import java.io.*;
import java.net.*;
import java.util.*;

import bd.daos.LabirintosBD;
import bd.dbos.LabirintoBD;

public class SupervisoraDeConexao extends Thread
{
    private String				labirinto;
    private Parceiro            usuario;
    private Socket              conexao;
    private ArrayList<Parceiro> usuarios;

    public SupervisoraDeConexao
    (Socket conexao, ArrayList<Parceiro> usuarios)
    throws Exception
    {
        if (conexao==null)
            throw new Exception ("Conexao ausente");

        if (usuarios==null)
            throw new Exception ("Usuarios ausentes");

        this.conexao  = conexao;
        this.usuarios = usuarios;
    }

    public void run ()
    {

        ObjectOutputStream transmissor;
        try
        {
            transmissor =
            new ObjectOutputStream(
            this.conexao.getOutputStream());
        }
        catch (Exception erro)
        {
            return;
        }
        
        ObjectInputStream receptor=null;
        try
        {
            receptor=
            new ObjectInputStream(
            this.conexao.getInputStream());
        }
        catch (Exception err0)
        {
            try
            {
                transmissor.close();
            }
            catch (Exception falha)
            {} // so tentando fechar antes de acabar a thread
            
            return;
        }

        try
        {
            this.usuario =
            new Parceiro (this.conexao,
                          receptor,
                          transmissor);
        }
        catch (Exception erro)
        {} // sei que passei os parametros corretos

        try
        {
            synchronized (this.usuarios)
            {
                this.usuarios.add (this.usuario);
            }


            for(;;)
            {
                Comunicado comunicado = this.usuario.envie ();

                if (comunicado==null)
                    return;
                else if (comunicado instanceof PedidoDeSalvar)
                {
					// pegar do comunicado o vetor cheio de desenhos
					// e mais o nome do desenho e mais identificacao 
					// cliente, e mandar pro BD usando o DAO e o DBO
					// que voce vai fazer
					// -----
					// desconectar o usuario
                	try 
                	{
                		PedidoDeSalvar pedidoDeSalvar = (PedidoDeSalvar) comunicado;
                		LabirintoBD lab = new LabirintoBD (pedidoDeSalvar.getIp(), pedidoDeSalvar.getNome(), pedidoDeSalvar.getData(), pedidoDeSalvar.getData(), pedidoDeSalvar.getLabirinto());
                		
                		LabirintosBD.incluir(lab);
                		System.out.println("Labirinto incluido");
                	}
                	catch(Exception erro)
                	{
                		System.err.println("Dados faltantes");
                	} 	
		        }
                else if (comunicado instanceof PedidoDeResultado)
                {
					// pegar do comunicado o nome do desenho e a identificacao
					// cliente, usar o DAO e DBO para recuperar do BD os dados,
					// preencher um objeto do tipo Desenho e vai enviar pro
					// cliente fazendo usuario.receba(desenho)
					// -----
					// desconecta o usuario
                	try
                	{
                	LabirintoBD lab;
                	
                	PedidoDeResultado pedidoDeResultado = (PedidoDeResultado) comunicado;
                	
                	lab = LabirintosBD.getLab(pedidoDeResultado.getIp(), pedidoDeResultado.getNome());
                	labirinto = lab.getLab();
                	
                	this.usuario.receba(new Resultado(this.labirinto));
                	System.out.println("Labirinto encontrado");
                	}
                	catch(Exception erro)
                	{
                		System.err.println("Nao foi possivel encontrar o labirinto");
                	}
                }
            }
        }
        catch (Exception erro)
        {
            try
            {
                transmissor.close ();
                receptor   .close ();
            }
            catch (Exception falha)
            {} // so tentando fechar antes de acabar a thread

            return;
        }
    }
}
