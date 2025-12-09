package compil.ast;

/**
 * Visiteur générique de l'AST avec parcours en profondeur.
 */
public class AstVisitorDefault implements AstVisitor {
	/**
	 * Parcours récursif en profondeur de l'arbre de syntaxe.
	 * 
	 * @param node le nœud à visiter
	 */
	public void defaultVisit(final AstNode node) {
		for (AstNode f : node)
			f.accept(this);
	}

	// Liste homogène
	public <T extends AstNode> void visit(final AstList<T> n) {
		defaultVisit(n);
	}

    // AST

    @Override
    public void visit(Attribut n) {
        defaultVisit(n);
    }

    @Override
    public void visit(Classe n) {
        defaultVisit(n);
    }

    @Override
    public void visit(Axiome n) {
        defaultVisit(n);
    }

    @Override
    public void visit(Exp n) {
        defaultVisit(n);
    }

    @Override
    public void visit(ExpEntier n) {
        defaultVisit(n);
    }

    @Override
    public void visit(ExpIdent n) {
        defaultVisit(n);
    }

    @Override
    public void visit(Ident n) {
        defaultVisit(n);
    }

    @Override
    public void visit(Type n) {
        defaultVisit(n);
    }
}
