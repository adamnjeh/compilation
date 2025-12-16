package compil.ast;

/**
 * Expressions, classe abstraite pour Exp*.
 */
public abstract class Exp extends AstNode {
	Exp(AstNode... enfants) {
		super(enfants);
	}
	
    @Override
    public void accept(AstVisitor v) {
        v.visit(this);
    }
}
