package compil.ast;

public class ExpNew extends Exp {
    public Ident classe;

    public ExpNew(Ident classe) {
        super(classe);
        this.classe = classe;
    }

    @Override
    public void accept(AstVisitor v) {
        v.visit(this);
    }
}
