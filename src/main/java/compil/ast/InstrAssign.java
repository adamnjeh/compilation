package compil.ast;

public class InstrAssign extends Instruction {
    public Ident ident;
    public Exp exp;

    public InstrAssign(Ident ident, Exp exp) {
        super(ident, exp);
        this.ident = ident;
        this.exp = exp;
    }

    @Override
    public void accept(AstVisitor v) {
        v.visit(this);
    }
}
