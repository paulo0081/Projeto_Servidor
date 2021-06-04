

import java.sql.Date;

@SuppressWarnings("serial")
public class PedidoDeSalvar extends Comunicado
{
    
	private String 	labirinto;
	private Date 	data;
	private String 	nome;
	private String 	ip;
    
    public PedidoDeSalvar (String nome, String ip, Date data, String labirinto)
    {
    	this.nome = nome;
        this.ip = ip;
        this.data = data;
        this.labirinto = labirinto;
        System.out.println("Passei aqui");
    }
    
    
    public String getNome ()
    {
        return this.nome;
    }
    
    public String getIp ()
    {
        return this.ip;
    }
    
    public Date getData ()
    {
        return this.data;
    }
    
    public String getLabirinto ()
    {
        return this.labirinto;
    }
    
    public String toString ()
    {
        return ("Nome: " + this.nome +"\nIp: " + this.ip + "\nLab: "+ this.labirinto);
    }
}
