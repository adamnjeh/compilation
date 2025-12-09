// package syntax;
package compil;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import compil.util.CompilerException;
import compil.util.Debug;
import java_cup.runtime.SymbolFactory;
import java_cup.runtime.lr_parser;

/**
 * Point d'entrée du compilateur. Dans ce projet, c'est uniquement l'analyseur
 * lexical plus l'analyseur syntaxique.
 * 
 * <br>
 * Cette classe encapsule les classes {@code main.Yylex} et {@code main.parser}
 * de CUP/JFlex.
 * 
 * <br>
 * La méthode {@link #main} existe pour l'analyse en ligne de commande.
 */
public class Compiler {
    /**
     * Le fichier source en entrée.
     */
    private String infile;
    /**
     * Le nombre de problèmes rencontrés : dans l'analyse lexicale et dans l'analyse
     * syntaxique. L'analyse syntaxique peut récupérer les problèmes. Mais, dans les
     * tests, on souhaite savoir s'il y a eu des problèmes, et combien.
     */
    private static int failures;

    /**
     * Construit l'analyseur syntaxique CUP/JFlex.
     * 
     * @param infile le fichier analysé, {@code "stdin"} pour l'entrée standard
     */
    private Compiler(final String infile) {
        this.infile = infile;
    }

    /**
     * Obtient le nombre de problèmes rencontrés.
     * 
     * @return le nombre de problèmes.
     */
    public static int getFailures() {
        return failures;
    }

    /**
     * Incrémente le nombre de problèmes rencontrés.
     */
    public static void incrementFailures() {
        Compiler.failures++;
    }

    /**
     * Exécution de l'analyse et validation de la valeur de retour.
     * 
     * @param debugToken {@code true} pour impression des tokens lus
     * @param debugParse {@code true} pour trace d’exécution de l'automate LR
     * @return la valeur du symbole axiome de la grammaire.
     * @throws FileNotFoundException pb avec le fichier à analyser.
     */
    public Object run(final boolean debugToken, final boolean debugParse) throws FileNotFoundException {
        Compiler.failures = 0;
        // redirection de l'entrée standard
        if ("stdin".equals(infile) || "-".equals(infile)) {
            if (Debug.isLoggingON()) {
                Debug.PW.println("Reading standard input");
            }
        } else {
            System.setIn(new FileInputStream(infile));
            if (Debug.isLoggingON()) {
                Debug.PW.println("Reading file " + infile);
            }
        }
        // création du lexer et du parser
        // sf pour la définition des Tokens partagés entre JFlex et CUP
        SymbolFactory sf = new java_cup.runtime.ComplexSymbolFactory();
        // lexer pour l'analyseur lexical JFlex
        java_cup.runtime.Scanner lexer = new Yylex(new java.io.InputStreamReader(System.in), sf);
        // scanBuffer pour la capture de Tokens échangés entre JFlex et CUP
        java_cup.runtime.ScannerBuffer scanBuffer = new java_cup.runtime.ScannerBuffer(lexer);
        // parser pour l'analyseur syntaxique CUP
        lr_parser parser = new parser(scanBuffer, sf);
        Object axiome = null;
        final java_cup.runtime.Symbol result;
        try {
            result = debugParse ? parser.debug_parse() : parser.parse();
            if (debugToken) {
                Debug.PW.println("= Token Debug");
                Debug.PW.println(scanBuffer.getBuffered().toString());
            }
            if (failures > 0) {
                Debug.PW.println("Analyse syntaxique en erreur sur le symbole qui suit : " + result);
            } else if (result == null || result.sym != 0) {
                Debug.PW.println("Analyse syntaxique avec problème, fin sur le symbole qui suit : " + result);
                Compiler.incrementFailures();
            } else {
                axiome = result.value;
// DÉBUT du code à « dé-commenter »
//                Debug.PW.println("Analyse syntaxique , Axiome = " + axiome);
//                if (axiome instanceof Axiome racineAST) {
//                    System.out.println(racineAST.toPrint());
//                    new VisiteurAttributsParametresVariablesAvecPorteee(racineAST);
//                } else {
//                    Debug.PW.println(
//                            "La racine devrait être un nœud Axiome (" + axiome.getClass().getCanonicalName() + ")");
//                    Compiler.incrementFailures();
//                }
// FIN du code à « dé-commenter »
            }
        } catch (RuntimeException e) {
            Compiler.incrementFailures();
            throw e;
        } catch (Exception e) {
            Compiler.incrementFailures();
            Debug.PW.println(e.getMessage());
        }
        return axiome;
    }

    /**
     * Exécution du compilateur avec une chaîne de caractères en entrée.
     * 
     * @param chaine     la chaîne de caractères à analyser.
     * @param debugToken {@code true} pour impression des tokens lus.
     * @param debugParse {@code true} pour trace d’exécution de l'automate LR.
     * @return la valeur du symbole axiome de la grammaire.
     */
    public static Object stringCompiler(final String chaine, final boolean debugToken, final boolean debugParse) {
        System.setIn(new java.io.ByteArrayInputStream(chaine.getBytes()));
        try {
            return new Compiler("stdin").run(debugToken, debugParse);
        } catch (FileNotFoundException e) {
            throw new CompilerException("problème avec la chaîne de caractères en entrée");
        }
    }

    /**
     * Exécution du compilateur avec un fichier en entrée.
     * 
     * @param fileName   le nom du fichier à analyser.
     * @param debugToken {@code true} pour impression des tokens lus.
     * @param debugParse {@code true} pour trace d’exécution de l'automate LR.
     * @return la valeur du symbole axiome de la grammaire.
     */
    public static Object fileCompiler(final String fileName, final boolean debugToken, final boolean debugParse) {
        try {
            return new Compiler(fileName).run(debugToken, debugParse);
        } catch (FileNotFoundException e) {
            throw new CompilerException("problème avec la chaîne de caractères en entrée");
        }
    }

    /**
     * Exécution en ligne de commande de l'analyseur syntaxique.
     * 
     * @param args [-help|-h] [-debug|-d] [-trace|-t] [files]
     * @throws FileNotFoundException pb avec le fichier à analyser.
     */
    public static void main(final String[] args) throws FileNotFoundException {
        final java.util.List<String> files = new java.util.ArrayList<>();
        boolean debug = false;
        boolean trace = false;
        String usage = "CupParse [-help|-h] [-debug|-d] [-trace|-t] [files]";
        usage += "\n\t -degug   dump tokens,";
        usage += "\n\t -trace   trace LR Automaton";
        usage += "\n\t files    stdin by default";
        for (String opt : args) {
            switch (opt) {
            case "-h", "-help":
                Debug.PW.println(usage);
                System.exit(0);
                break;
            case "-d", "-debug":
                debug = true;
                break;
            case "-t", "-trace":
                trace = true;
                break;
            default:
                files.add(opt);
            }
        }
        if (files.isEmpty()) {
            files.add("stdin");
        }
        for (String file : files) {
            new Compiler(file).run(debug, trace);
        }
    }
}
