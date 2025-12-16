package compil.ast;

public class ExpNull extends Exp {
    public ExpNull() { super(); }

    @Override
    public void accept(AstVisitor v) {
        v.visit(this);
    }
}
