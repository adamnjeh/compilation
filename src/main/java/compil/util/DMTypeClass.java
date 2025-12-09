package compil.util;

import java.util.Objects;

/**
 * Classe pour les types classes.
 * 
 * <p>
 * N.B. : le nom de la classe est préfixé avec « DM » (pour Devoir Maison).
 * </p>
 * 
 * @author Denis Conan
 */
public class DMTypeClass implements DMType {
	/**
	 * le nom de la classe, qui est le nom du type.
	 */
	private String name;

	/**
	 * construit un type classe.
	 * 
	 * @param name le nom de la classe, qui devient le nom du type.
	 */
	public DMTypeClass(final String name) {
		this.name = name;
	}

	@Override
	public String name() {
		return name;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof DMTypeClass)) {
			return false;
		}
		DMTypeClass other = (DMTypeClass) obj;
		return Objects.equals(name, other.name);
	}

	@Override
	public String toString() {
		return this.name;
	}
}
