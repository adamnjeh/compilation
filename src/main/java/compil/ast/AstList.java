package compil.ast;

/**
 * Nœud de l'AST avec enfants homogènes. <br>
 * Construction itérative avec la méthode {@link #add(R)}
 * 
 * @param <R> le type des enfants.
 */
public class AstList<R extends AstNode> extends AstNode {
	/**
	 * Construit une liste vide.
	 */
	public AstList() {
		/* liste vide */ }

	/**
	 * Construit la liste des enfants par itération.
	 * 
	 * @param node le nœud ajouté en fin de liste
	 */
	public void add(final R node) {
		this.addEnfant(node);
	}

	public void accept(final AstVisitor v) {
		v.visit(this);
	}
}
