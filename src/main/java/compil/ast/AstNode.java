package compil.ast;

import java.util.ArrayList;
import java.util.List;

import java_cup.runtime.ComplexSymbolFactory.Location;

/**
 * La classe abstraite ancêtre pour l'AST.
 * <ul>
 * <li>Construction VarArgs
 * <li>Itérable : {@code for (AstNode enfants : pere) }
 * <li>Impression de l'arbre : {@link #toPrint}
 * <li>Gestion des positions dans le source : {@link #addPosition}
 * </ul>
 */
public abstract class AstNode implements Iterable<AstNode> {
    /**
     * Un nom pour le debugging. Par défaut le nom de la classe instanciante
     */
    private final String label;

    /**
     * La liste des nœuds enfants.
     */
    private final List<AstNode> enfants;

    /**
     * La localisation dans le fichier source: Debut.
     */
    private Location start;

    /**
     * La localisation dans le fichier source: Fin.
     */
    private Location stop;

    /**
     * Construit le nœud avec ses enfants en VarArgs.
     * 
     * @param enfants une liste quelconque d'enfants.
     */
    protected AstNode(final AstNode... enfants) {
        this.label = getClass().getSimpleName();
        this.enfants = new ArrayList<>();
        for (AstNode f : enfants)
            this.enfants.add(f);
    }

    /**
     * Construit les enfants par itération, uniquement pour {@link AstList}.
     * 
     * @param f le nœud enfant à ajouter.
     */
    protected void addEnfant(final AstNode f) {
        this.enfants.add(f);
    }

    /**
     * Réalise le patron de conception "Visitor".
     * 
     * @param v le visiteur
     */
    public abstract void accept(AstVisitor v);

    /**
     * Itération sur les enfants. <br>
     * {@code AstNode p; for (AstNode f : p) ...}
     */
    public java.util.Iterator<AstNode> iterator() {
        return enfants.iterator();
    }

    /**
     * Donne la taille.
     * 
     * @return le nombre d'enfants.
     */
    public int size() {
        return this.enfants.size();
    }

    /**
     * Gestion optionnelle des positions des symboles dans le source. <br>
     * Exemple dans CUP :
     * 
     * <pre>
     * x ::= A:a .. Z:z {: RESULT = new Node(A,..,Z);
     *                    RESULT.addPosition(axleft,zxright); :}
     * </pre>
     * 
     * @param left  la position de début du symbole
     * @param right la position de fin du symbole
     */
    public void addPosition(final Location left, final Location right) {
        this.start = left;
        this.stop = right;
    }

    /** Imprime un noeud. */
    public String toString() {
        if (start == null && stop == null)
            return label;
        return label + "[" + locStr(start) + "-" + locStr(stop) + "]";
    }

    /**
     * "Redéfinition" de {@code Location.toString()}.
     * 
     * @param l la Location à imprimer
     * @return la chaîne imprimable
     */
    private String locStr(final Location l) {
        if (l == null)
            return "";
        return l.getLine() + "/" + l.getColumn() + "(" + l.getOffset() + ")";
    }

    /**
     * Imprime l'Arbre de Syntaxe Abstraite.
     * 
     * @return la représentation textuelle de L'AST
     */
    public String toPrint() {
        final StringBuilder sb = new StringBuilder();
        Print.astPrint(this, "", sb);
        return sb.toString();
    }

    /** Impression récursive de l'AST. */
    private static final class Print {
        /**
         * Les jeux de caractères pour impression d'arbre.
         * <ul>
         * <li>ASCII : "|-", "| ", "\\-", " "
         * <li>Unicode: "\u251c\u2500","\u2502\u0020","\u2514\u2500","\u0020\u0020"
         * <li>Unicode Compact: "\u251c", "\u2502", "\u2514", "\u0020"
         * </ul>
         */
        private static final String[][] CSS = { { "|-", "| ", "\\-", "  " }, // ASCII
                { "├─", "│ ", "└─", "  " }, // Unicode
                { "├", "│", "└", " " } // Unicode Compact
        };
        /** Le jeu de caractères pour impression d'arbre. */
        private static final String[] CS = CSS[0]; // Unicode

        /** Constructeur pour la forme. */
        private Print() {
        }

        /**
         * Imprime récursivement un arbre à la ASCII art.
         * 
         * @param indent la chaîne d'indentation courante
         */
        private static void astPrint(final AstNode n, final String indent, final StringBuilder sb) {
            sb.append(n + System.lineSeparator());
            for (java.util.Iterator<AstNode> it = n.iterator(); it.hasNext();) {
                final AstNode f = it.next();
                final int indice = it.hasNext() ? 0 : 2; /* last or not? */
                sb.append(indent + CS[indice]);
                astPrint(f, indent + CS[indice + 1], sb);
            }
        }
    }
}
