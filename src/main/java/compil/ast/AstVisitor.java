package compil.ast;

/**
 * Interface Visiteur pour l'AST Minijava.
 */
public interface AstVisitor {
	/**
	 * Visite une liste de nœuds homogénes.
	 * 
	 * @param <T> le type commun des enfants.
	 * @param n   le nœud visité.
	 */
	<T extends AstNode> void visit(AstList<T> n);

	// AST
	void visit(Attribut n);
    
    void visit(Axiome n);
	
    void visit(Classe n);
   
    void visit(Exp n);
    
    void visit(ExpEntier n);
    
    void visit(ExpIdent n);
    
    void visit(Ident n);
    
    void visit(Type n);
    
    void visit(Parametre n);
    void visit(Methode n);

    void visit(Instruction n);
    void visit(InstrPrintln n);
    void visit(InstrAssign n);

    void visit(ExpNull n);
    void visit(ExpNew n);

}
