package compil.ast;

public class ExpNew extends Exp {
    public Ident classe;

    public ExpNew(Ident classe) {
        super(classe);
        this.classe = classe;
    }
}
