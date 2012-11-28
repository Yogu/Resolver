
public class Atom implements Comparable<Atom> {
	public final String name;
	
	public Atom(String name) {
		this.name = name;
	}
	
	public boolean equals(Atom other) {
		return name.equals(other.name);
	}
	
	@Override
	public int hashCode() {
		return name.hashCode();
	}
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof Atom)
			return equals((Atom)other);
		return false;
	}
	
	@Override
	public String toString() {
		return name;
	}

	@Override
	public int compareTo(Atom o) {
		return name.compareTo(o.name);
	}
}
