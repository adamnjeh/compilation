package compil.ast;

/**
 * Constante entière.
 */
public class ExpEntier extends Exp {
	public Integer value;

	public ExpEntier(Integer value) {
	    super();
		this.value = value;
	}
	
	@Override
    public String toString() {
        return super.toString() + "(" + value + ")";
    }
}
