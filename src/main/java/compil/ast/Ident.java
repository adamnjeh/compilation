package compil.ast;

/**
 * Un identificateur possède un nom.
 */
public class Ident extends AstNode {
    public String nom;

    public Ident(String nom) {
        super();
        this.nom = nom;
    }

    @Override
    public void accept(AstVisitor v) {
        v.visit(this);
    }
}
