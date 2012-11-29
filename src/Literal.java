
public class Literal implements Comparable<Literal> {
	public final Atom atom;
	public final boolean isNegative;
	
	public Literal(Atom atom, boolean isNegative) {
		this.atom = atom;
		this.isNegative = isNegative;
	}
	
	public static Literal createPositive(Atom atom) {
		return new Literal(atom, false);
	}
	
	public static Literal createNegative(Atom atom) {
		return new Literal(atom, true);
	}
	
	public boolean isComplementTo(Literal other) {
		return other.atom.equals(this.atom) && other.isNegative != this.isNegative;
	}
	
	public boolean equals(Literal other) {
		return other.atom.equals(this.atom) && other.isNegative == this.isNegative;
	}
	
	public boolean equals(Object other) {
		if (other instanceof Literal)
			return equals((Literal)other);
		return false;
	}
	
	@Override
	public int hashCode() {
		return this.atom.hashCode() ^ (isNegative ? 1 : 0);
	}
	
	public Literal negate() {
		return new Literal(atom, !isNegative);
	}
	
	@Override
	public String toString() {
		return (isNegative ? "neg " : "") + atom.toString();
	}

	@Override
	public int compareTo(Literal o) {
		return atom.compareTo(o.atom);
	}
}
