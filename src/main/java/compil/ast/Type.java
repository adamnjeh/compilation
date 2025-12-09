package compil.ast;

/**
 * Un identificateur possède un nom.
 */
public class Type extends AstNode {
    public String nom;

    public Type(String nom) {
        super();
        this.nom = nom;
    }

    @Override
    public void accept(AstVisitor v) {
        v.visit(this);
    }
}
