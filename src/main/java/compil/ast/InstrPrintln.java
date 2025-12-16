package compil.ast;

public class InstrPrintln extends Instruction {
    public Exp exp;

    public InstrPrintln(Exp exp) {
        super(exp);
        this.exp = exp;
    }

    @Override
    public void accept(AstVisitor v) {
        v.visit(this);
    }
}
