import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Clause {
	private final Set<Literal> literals;
	private Clause leftOrigin;
	private Clause rightOrigin;
	
	public static final Clause EMPTY = new Clause();
	
	public Clause(Set<Literal> literals, Clause leftOrigin, Clause rightOrigin) {
		this.literals = Collections.unmodifiableSet(literals);
		this.leftOrigin = leftOrigin;
		this.rightOrigin = rightOrigin;
	}
	
	public Clause(Set<Literal> literals) {
		this(literals, null, null);
	}
	
	public Clause(Literal... literals) {
		this(new HashSet<Literal>(Arrays.asList(literals)));
	}
	
	public Set<Literal> getLiterals() {
		return literals;
	}
	
	public boolean equals(Clause other) {
		return other.getLiterals().equals(literals);
	}
	
	public Clause getLeftOrigin() {
		return leftOrigin;
	}
	
	public Clause getRightOrigin() {
		return rightOrigin;
	}
	
	/**
	 * Gets the nubmer of clauses that needed to be resolved before this clause could be resolved
	 * 
	 * @return a measurement for the complexity to resolve this clause
	 */
	public int getResolvationComplexity() {
		if (leftOrigin != null && rightOrigin != null)
			return leftOrigin.getResolvationComplexity() + rightOrigin.getResolvationComplexity() + 1;
		else
			return 0;
	}
	
	@Override
	public int hashCode() {
		return literals.hashCode();
	}
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof Clause)
			return equals((Clause)other);
		return false;
	}
	
	@Override
	public String toString() {
		List<Literal> sortedList = new ArrayList<Literal>(literals);
		Collections.sort(sortedList);
		String s = null;
		for (Literal literal : sortedList) {
			if (s == null)
				s = "";
			else
				s += ", ";
			s += literal.toString();
		}
		if (s == null)
			return "EMPTY";
		return "\\{" + s + "\\}";//[" + getResolvationComplexity() + "]";
	}
	
	public Clause resolveWith(Clause other) {
		// Resolving with oneself is not permitted
		if (equals(other))
			return null;
		
		for (Literal literal : literals) {
			Literal negation = literal.negate();
			if (other.getLiterals().contains(negation)) {
				// this.literals without literal
				Set<Literal> newLeftLiterals = new HashSet<Literal>();
				newLeftLiterals.addAll(literals);
				newLeftLiterals.remove(literal);
				
				// other.literals without negation
				Set<Literal> newRightLiterals = new HashSet<Literal>();
				newRightLiterals.addAll(other.getLiterals());
				newRightLiterals.remove(negation);
				
				// use newLeftLiterals as newLiterals
				Set<Literal> newLiterals = newLeftLiterals;
				newLiterals.addAll(newRightLiterals);
				return new Clause(newLiterals, this, other);
			}
		}
		return null;
	}
}
