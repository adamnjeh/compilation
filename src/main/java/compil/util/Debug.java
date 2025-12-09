package compil.util;

import java.io.PrintWriter;

/**
 * Options d'exécution du compilateur. Dans ce projet, c'est uniquement
 * l'analyseur lexical.
 * 
 * @author Pascal Hennequin
 * @author Denis Conan
 */
public final class Debug {
	/**
	 * Si {@code true}, debug actif.
	 */
	private static boolean isLoggingOn = true;
	/**
	 * Le flot d'impression standard.
	 */
	public static final PrintWriter PW = new PrintWriter(System.out, true);
	/**
	 * Le Flot d'impression en erreur.
	 */
	public static final PrintWriter PWERR = new PrintWriter(System.err, true);

	/**
	 * Constructeur interdit.
	 * 
	 * @throws IllegalStateException constructeur interdit.
	 */
	private Debug() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Répond à la question est-ce que les <i>logs</i> sont activés.
	 * 
	 * @return {@code true} si <i>logging</i>.
	 */
	public static boolean isLoggingON() {
		return isLoggingOn;
	}

	/**
	 * Désactive le <i>log</i>.
	 */
	public static void noLogging() {
		isLoggingOn = false;
	}

	/**
	 * Imprime sur la sortie standard. Remplace {@code System.out.println()}.
	 * 
	 * @param o l'objet à imprimer.
	 */
	public static void log(final Object o) {
		if (isLoggingOn) {
			PW.println(o.toString());
		}
	}
}
