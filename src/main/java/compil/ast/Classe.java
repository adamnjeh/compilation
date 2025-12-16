package compil.ast;

/**
 * Une classe = nom + parent + attributs + méthodes.
 */
public class Classe extends AstNode {
    public Ident nom;
    public Ident parent; // "Object" si pas de extends
    public AstList<Attribut> attributs;
    public AstList<Methode> methodes;

    public Classe(Ident nom, Ident parent, AstList<Attribut> attributs, AstList<Methode> methodes) {
        super(nom, parent, attributs, methodes);
        this.nom = nom;
        this.parent = parent;
        this.attributs = attributs;
        this.methodes = methodes;
    }

    @Override
    public void accept(AstVisitor v) {
        v.visit(this);
    }
}
