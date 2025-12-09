package compil;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import compil.util.Debug;

/**
 * Test de l'analyse lexicale et de l'analyse syntaxique avec fichier en entrée.
 * 
 * @author Denis Conan
 */
final class TestFileCompiler {

    @BeforeEach
    void setUp() {
        // mettre en commentaire la ligne suivante pour moins d'affichage
        Debug.isLoggingON();
    }

    @Test
    @DisplayName("test sur fichier")
    void test1() {
        Compiler.fileCompiler(
                System.getProperty("user.dir") + "/src/test/resources/dm1.txt", true,
                false);
        System.out.println(
                "Nombre de problèmes (intéressant si on fait de la gestion d'erreurs) : " + Compiler.getFailures());
        Assertions.assertEquals(0, Compiler.getFailures());
    }
}
