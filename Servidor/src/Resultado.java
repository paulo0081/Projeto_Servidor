

@SuppressWarnings("serial")
public class Resultado extends Comunicado
{
	private String labirinto;

	public Resultado (String lab)
    {
    	this.labirinto = lab;
    }

    public String getLabirinto ()
    {
    	return this.labirinto;
    }
    
    public String toString ()
    {
    	return (""+this.labirinto);
	}

}
