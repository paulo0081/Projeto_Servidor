package bd.dbos;

import java.sql.*;

public class LabirintoBD implements Cloneable
{
    private String 	ip;
    private String 	nome;
    private Date  	created;
    private Date	updated;
    private String	lab;
 
    public void setIp (String ip) throws Exception
    {
        if (ip == null)
            throw new Exception ("Ip invalido");

        this.ip = ip;
    }   

    public void setNome (String nome) throws Exception
    {
        if (nome==null || nome.equals(""))
            throw new Exception ("Nome nao fornecido");

        this.nome = nome;
    }

    public void setCreated (Date created) throws Exception
    {
        if (!(created instanceof Date))
            throw new Exception ("Data invalida");

        this.created = (Date) created;
    }
    
    public void setUpdated (Date updated) throws Exception
    {
        if (!(updated instanceof Date))
            throw new Exception ("Data invalida");

        this.updated = (Date) updated;
    }
    
    public void setLab (String lab) throws Exception
    {
        if (lab == null)
            throw new Exception ("Labirinto vazio");

        this.lab = lab;
    }
    
    public String getIp ()
    {
        return this.ip;
    }

    public String getNome ()
    {
        return this.nome;
    }

    public Date getCreated ()
    {
        return this.created;
    }
    
    public Date getUpdated ()
    {
        return this.updated;
    }
    
    public String getLab ()
    {
        return this.lab;
    }

    public LabirintoBD (String ip, String nome, Date created, Date updated, String lab) throws Exception
    {
        this.setIp 		(ip);
        this.setNome   	(nome);
        this.setCreated (created);
        this.setUpdated	(updated);
        this.setLab		(lab);
    }

    public String toString ()
    {
        String ret="";

        ret+="Ip: "			+this.ip+"\n";
        ret+="Nome: "		+this.nome+"\n";
        ret+="Created_at: "	+this.created+"\n";
        ret+="Updated_at: "	+this.updated+"\n\n";
        ret+="Lab: "		+this.lab;

        return ret;
    }

    public boolean equals (Object obj)
    {
        if (this==obj)
            return true;

        if (obj==null)
            return false;

        if (!(obj instanceof LabirintoBD))
            return false;

        LabirintoBD lab = (LabirintoBD) obj;

        if (this.ip!= lab.ip)
            return false;

        if (this.nome.equals(lab.nome))
            return false;

        if (this.created!=lab.created) //em caso de dar merda, usar o compareTo
            return false;
        
        if (this.updated!=lab.updated) //em caso de dar merda, usar o compareTo
            return false;

        if (this.lab!=lab.lab)
        	return false;
        
        return true;
    }

    public int hashCode ()
    {
        int ret=666;

        ret = 3*ret + new Integer(this.ip).hashCode();
        ret = 13*ret + this.nome.hashCode();
        ret = 5*ret + new String(this.lab).hashCode();

        return ret;
    }


    public LabirintoBD (LabirintoBD modelo) throws Exception
    {
        this.ip 		= modelo.ip; // nao clono, pq nao eh objeto
        this.nome   	= modelo.nome;   // nao clono, pq nao eh clonavel
        this.created  	= modelo.created;  // nao clono, pq nao eh objeto
        this.updated	= modelo.updated;
        this.lab		= modelo.lab;
    }

    public Object clone ()
    {
        LabirintoBD ret=null;

        try
        {
            ret = new LabirintoBD (this);
        }
        catch (Exception erro)
        {} // nao trato, pq this nunca é null e construtor de
           // copia da excecao qdo seu parametro for null

        return ret;
    }
}