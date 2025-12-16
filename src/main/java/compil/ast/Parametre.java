package compil.ast;

public class Parametre extends AstNode {
    public Type type;
    public Ident ident;

    public Parametre(Type type, Ident ident) {
        super(type, ident);
        this.type = type;
        this.ident = ident;
    }

    @Override
    public void accept(AstVisitor v) {
        v.visit(this);
    }
}
