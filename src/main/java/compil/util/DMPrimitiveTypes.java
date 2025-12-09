package compil.util;

/**
 * Énumération des types primitifs avec nom textuel.
 * 
 * <p>
 * N.B. : le nom de la classe est préfixé avec « DM » (pour Devoir Maison).
 * </p>
 * 
 * @author Pascal Hennequin, Denis Conan
 */
public enum DMPrimitiveTypes implements DMType {
	/**
	 * Le type entier.
	 */
	INT("int"),
	/**
	 * Le type inconnu (inutile).
	 */
	UNDEF("undef");

	/**
	 * Le nom de type.
	 */
	private final String name;

	/**
	 * Constructeur privé pour nommage des types.
	 * 
	 * @param name le nom JAVA du type.
	 */
	DMPrimitiveTypes(final String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return this.name;
	}
}
