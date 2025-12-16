package compil.ast;

public class Methode extends AstNode {
    public Type typeRetour;
    public Ident nom;
    public AstList<Parametre> params;
    public AstList<Instruction> instructions;
    public Exp expRetour;

    public Methode(Type typeRetour, Ident nom,
                   AstList<Parametre> params,
                   AstList<Instruction> instructions,
                   Exp expRetour) {
        super(typeRetour, nom, params, instructions, expRetour);
        this.typeRetour = typeRetour;
        this.nom = nom;
        this.params = params;
        this.instructions = instructions;
        this.expRetour = expRetour;
    }

    @Override
    public void accept(AstVisitor v) {
        v.visit(this);
    }
}
