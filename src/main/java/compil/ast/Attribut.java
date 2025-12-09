package compil.ast;

/**
 * Un attribut possède un type et un identificateur.
 */
public class Attribut extends AstNode {
    public Type type;
    public Ident ident;

    public Attribut(Type type, Ident ident) {
        super(type, ident);
        this.type = type;
        this.ident = ident;
    }

    @Override
    public void accept(AstVisitor v) {
        v.visit(this);
    }
}
