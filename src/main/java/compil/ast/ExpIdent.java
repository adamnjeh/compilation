package compil.ast;

/**
 * Une expression avec une variable, qui possède un identificateur.
 */
public class ExpIdent extends Exp {
    public Ident ident;

    public ExpIdent(Ident ident) {
        super(ident);
        this.ident = ident;
    }

    @Override
    public String toString() {
        return super.toString() + "(" + ident.nom + ")";
    }

}
