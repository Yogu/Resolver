import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class Formula {
	private Map<Clause, Integer> clauses;
	private Set<Clause> unmodifiableClauseSet;
	
	public Formula() {
		this.clauses = new HashMap<Clause, Integer>();
		this.unmodifiableClauseSet = Collections.unmodifiableSet(clauses.keySet());
	}
	
	/**
	 * Adds the specified clause to the clause set
	 * 
	 * If there is already an equivalent clause, and the given clause is less complex than the existing one,
	 * the existing clause is replaced by the given one.
	 * 
	 * @param clause the clause to add
	 */
	public void addClause(Clause clause) {
		int newClauseComplexity = clause.getResolvationComplexity();
		Integer existingClauseComplexity = clauses.get(clause);
		if (existingClauseComplexity == null || newClauseComplexity < existingClauseComplexity) {
			clauses.put(clause, newClauseComplexity);
		}
	}
	
	public void addClause(Literal... literals) {
		addClause(new Clause(literals));
	}
	
	public void addClauses(Collection<Clause> clauses) {
		for (Clause clause : clauses)
			addClause(clause);
	}
	
	public Set<Clause> getClauses() {
		return unmodifiableClauseSet;
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
		for (Clause clause : clauses.keySet()) {
			if (s == null)
				s = "";
			else
				s += ", ";
			s += clause.toString();
		}
		return "{" + s + "}";
	}
}
