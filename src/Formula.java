import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


public class Formula {
	private Set<Clause> clauses;
	
	public Formula() {
		this.clauses = new HashSet<Clause>();
	}
	
	public void addClause(Clause clause) {
		clauses.add(clause);
	}
	
	public void addClause(Literal... literals) {
		clauses.add(new Clause(literals));
	}
	
	public void addClauses(Collection<Clause> clauses) {
		this.clauses.addAll(clauses);
	}
	
	public Set<Clause> getClauses() {
		return clauses;
	}
	
	public boolean equals(Formula other) {
		return other.getClauses().equals(clauses);
	}
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof Formula)
			return equals((Formula)other);
		return false;
	}
	
	@Override
	public int hashCode() {
		return clauses.hashCode();
	}
	
	@Override
	public String toString() {
		String s = null;
		for (Clause clause : clauses) {
			if (s == null)
				s = "";
			else
				s += ", ";
			s += clause.toString();
		}
		return "{" + s + "}";
	}
}
