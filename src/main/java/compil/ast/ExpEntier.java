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
	public void accept(AstVisitor v) {
        v.visit(this);
	}
}
