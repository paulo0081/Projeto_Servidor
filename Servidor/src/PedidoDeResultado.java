

import java.sql.Date;

@SuppressWarnings("serial")
	public class PedidoDeResultado extends Comunicado
	{
		private String 	nome;
		private String 	ip;
	    
	    public PedidoDeResultado (String nome, String ip)
	    {
	    	this.nome = nome;
	        this.ip = ip;
	    }
	    
	    
	    public String getNome ()
	    {
	        return this.nome;
	    }
	    
	    public String getIp ()
	    {
	        return this.ip;
	    }
	    
	    public String toString ()
	    {
	        return ("Nome: " + this.nome +"\nIp: " + this.ip);
	    }
	}
