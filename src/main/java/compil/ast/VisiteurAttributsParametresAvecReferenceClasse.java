package compil.ast;

import compil.util.Debug;
import compil.util.IndentWriter;

import java.util.ArrayList;
import java.util.List;

/**
 * 2ème visiteur: affiche attributs et paramètres avec leur référence de classe
 * (si le type est une classe, i.e. type != int).
 */
public class VisiteurAttributsParametresAvecReferenceClasse extends AstVisitorDefault {

    private final IndentWriter out = new IndentWriter();

    private String currentClass = null;

    private List<String> classItems = new ArrayList<>();
    private List<MethInfo> classMethods = new ArrayList<>();

    private static class MethInfo {
        final String name;
        final List<String> params = new ArrayList<>();
        MethInfo(String name) { this.name = name; }
    }

    private MethInfo currentMethodInfo = null;

    public VisiteurAttributsParametresAvecReferenceClasse(Axiome axiome) {
        out.println("Affichage des identificateurs dans leur portée et leur référence de classe : ");
        axiome.accept(this);
        Debug.log(out);
    }

    /** AstList transparent */
    @Override
    public <T extends AstNode> void visit(AstList<T> n) {
        for (AstNode x : n) {
            if (x != null) x.accept(this);
        }
    }

    @Override
    public void visit(Classe n) {
        currentClass = n.nom.nom;
        classItems = new ArrayList<>();
        classMethods = new ArrayList<>();

        super.visit(n);

        out.println(currentClass + " (classe) {");
        out.indent();

        for (String item : classItems) {
            out.println(item + ", ");
        }

        for (MethInfo m : classMethods) {
            out.println(m.name + " (méthode) {");
            out.indent();
            for (String p : m.params) {
                out.println(p + ", ");
            }
            out.println("}");
        }

        out.println("}");
        currentClass = null;
    }

    @Override
    public void visit(Attribut n) {
        if (currentClass != null && currentMethodInfo == null) {
            classItems.add(formatWithRefIfClass(n.ident.nom, n.type) + " (attribut)");
        }
    }

    @Override
    public void visit(Methode n) {
        currentMethodInfo = new MethInfo(n.nom.nom);
        classMethods.add(currentMethodInfo);

        if (n.params != null) n.params.accept(this);

        currentMethodInfo = null;
    }

    @Override
    public void visit(Parametre n) {
        if (currentMethodInfo != null) {
            currentMethodInfo.params.add(formatWithRefIfClass(n.ident.nom, n.type) + " (paramètre)");
        }
    }

    private String formatWithRefIfClass(String name, Type t) {
        if (t == null || t.nom == null) return name;
        if ("int".equals(t.nom)) return name;
        return name + " (réf classe `" + t.nom + "')";
    }
}
