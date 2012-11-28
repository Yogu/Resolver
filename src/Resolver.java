import java.util.HashSet;
import java.util.Set;


public class Resolver {
	private Formula formula;
	
	public Resolver(Formula formula) {
		this.formula = formula;
	}
	
	public Clause resolveEmptyClause() {
		// Resolve until no new clauses are resolved
		int count;
		do {
			count = formula.getClauses().size();
			Clause emptyClause = resolveNext();
			if (emptyClause != null)
				return emptyClause;
		} while (formula.getClauses().size() != count);
		return null;
	}
	
	/**
	 * 
	 * @return empty clause, if resolved
	 */
	private Clause resolveNext() {
		// cache access
		Set<Clause> clauses = formula.getClauses();
		Set<Clause> resolvents = new HashSet<Clause>();
		
		for (Clause leftClause : clauses) {
			for (Clause rightClause : clauses) {
				Clause resolvent = leftClause.resolveWith(rightClause);
				if (resolvent != null && !clauses.contains(resolvent)) {
					//System.out.println(String.format("Resolved %s (%s, %s)", resolvent, leftClause, rightClause));
					if (resolvent.equals(Clause.EMPTY)) {
						formula.addClauses(resolvents);
						return resolvent;
					}
					if (resolvents.add(resolvent))
						System.out.println(formula.getClauses().size() + resolvents.size());
				}
			}
		}
		formula.addClauses(resolvents);
		return null;
	}
}
