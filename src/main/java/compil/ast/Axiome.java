package compil.ast;

/**
 * C'est l'axiome, qui est une liste de Classe.
 */
public class Axiome extends AstNode {

    public Axiome(AstList<Classe> classes) {
        super();
        classes.forEach(this::addEnfant);
    }

    @Override
    public void accept(AstVisitor v) {
        v.visit(this);
    }
}
