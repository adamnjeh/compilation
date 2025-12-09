package compil.ast;

/**
 * Expressions, classe abstraite pour Exp*.
 */
public abstract class Exp extends AstNode {
	Exp(AstNode... enfants) {
		super(enfants);
	}
}
