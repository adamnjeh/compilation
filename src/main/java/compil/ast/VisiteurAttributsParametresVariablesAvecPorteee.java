package compil.ast;

import compil.util.Debug;
import compil.util.IndentWriter;

import java.util.ArrayList;
import java.util.List;

/**
 * Visiteur: affiche attributs et paramètres dans leur portée
 * au format demandé.
 */
public class VisiteurAttributsParametresVariablesAvecPorteee extends AstVisitorDefault {

    private final IndentWriter out = new IndentWriter();

    private String currentClass = null;

    private List<String> classAttributs = new ArrayList<>();
    private List<MethInfo> classMethods = new ArrayList<>();

    private static class MethInfo {
        final String name;
        final List<String> params = new ArrayList<>();
        MethInfo(String name) { this.name = name; }
    }

    private MethInfo currentMethodInfo = null;

    public VisiteurAttributsParametresVariablesAvecPorteee(Axiome axiome) {
        out.println("Affichage des identificateurs dans leur portée : ");
        axiome.accept(this);
        Debug.log(out);
    }

    /** transparent AstList */
    @Override
    public <T extends AstNode> void visit(AstList<T> n) {
        for (AstNode x : n) {
            if (x != null) x.accept(this);
        }
    }

    @Override
    public void visit(Classe n) {
        // reset collecte de la classe
        currentClass = n.nom.nom;
        classAttributs = new ArrayList<>();
        classMethods = new ArrayList<>();

        // collecter via parcours
        super.visit(n);

        // afficher la classe au format demandé
        out.println(currentClass + " (classe) {");
        out.indent();

        // attributs (avec virgule)
        for (String a : classAttributs) {
            out.println(a + " (attribut), ");
        }

        // méthodes
        for (MethInfo m : classMethods) {
            out.println(m.name + " (méthode) {");
            out.indent();
            for (String p : m.params) {
                out.println(p + " (paramètre), ");
            }
            // pas de unindent()
            out.println("}");
        }

        // pas de unindent()
        out.println("}");

        currentClass = null;
    }

    @Override
    public void visit(Attribut n) {
        // collecter seulement si on est dans une classe (pas dans une méthode)
        if (currentClass != null && currentMethodInfo == null) {
            classAttributs.add(n.ident.nom);
        }
    }

    @Override
    public void visit(Methode n) {
        // créer la méthode et collecter ses paramètres
        currentMethodInfo = new MethInfo(n.nom.nom);
        classMethods.add(currentMethodInfo);

        // visiter params (et seulement params)
        if (n.params != null) n.params.accept(this);

        currentMethodInfo = null;
    }

    @Override
    public void visit(Parametre n) {
        if (currentMethodInfo != null) {
            currentMethodInfo.params.add(n.ident.nom);
        }
    }
}
