package compil.util;

/**
 * Interface pour les types.
 * 
 * <p>
 * N.B. : le nom de la classe est préfixé avec « DM » (pour Devoir Maison) afin
 * d'éviter les collisions avec l'interface {@link java.lang.reflect.Type}.
 * </p>
 * 
 * @author Denis Conan
 */
public interface DMType {
	/**
	 * le nom du type.
	 * 
	 * @return le nom du type.
	 */
	String name();
}
