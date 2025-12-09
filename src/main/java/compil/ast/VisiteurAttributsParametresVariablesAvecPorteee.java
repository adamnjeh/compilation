package compil.ast;

import compil.util.Debug;
import compil.util.IndentWriter;

/**
 * Visiteur qui détecte les attributs des classes et la variables locales des
 * méthodes pour les afficher avec leur portée.
 */
public class VisiteurAttributsParametresVariablesAvecPorteee extends AstVisitorDefault {
    /**
     * Le Writer pour impression.
     */
    private final IndentWriter out = new IndentWriter();

    /**
     * Constructeur.
     * 
     * @param axiome La liste des classes.
     */
    public VisiteurAttributsParametresVariablesAvecPorteee(Axiome axiome) {
        out.println("Affichage des identificateurs dans leur portée : ");
        axiome.accept(this);
        Debug.log(out);
    }

    /////////////////// Visit ////////////////////
}
