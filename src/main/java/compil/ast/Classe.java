package compil.ast;

/**
 * TODO: ne pas oublier de compléter cette classe.
 */
public class Classe extends AstNode {
    public Classe() {
        super();
    }

    @Override
    public void accept(AstVisitor v) {
        v.visit(this);
   }
}
