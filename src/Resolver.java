import java.util.HashSet;
import java.util.Set;


public class Resolver {
	private Formula formula;
	
	public Resolver(Formula formula) {
		this.formula = formula;
	}
	
	public Clause resolveEmptyClause(boolean breakEarly) {
		// Resolve until no new clauses are resolved
		int count;
		do {
			count = formula.getClauses().size();
			Clause emptyClause = resolveNext(breakEarly);
			if (emptyClause != null)
				return emptyClause;
		} while (formula.getClauses().size() != count);
		
		// If we don't break on empty clauses, we now need to check whether the empty clause is contained by 
		if (!breakEarly)
		return null;
	}
	
	/**
	 * 
	 * @return empty clause, if resolved
	 */
	private Clause resolveNext(boolean breakEarly) {
		// cache access
		Set<Clause> clauses = formula.getClauses();
		Set<Clause> resolvents = new HashSet<Clause>();
		
		for (Clause leftClause : clauses) {
			for (Clause rightClause : clauses) {
				Clause resolvent = leftClause.resolveWith(rightClause);
				if (resolvent != null) {
					//System.out.println(String.format("Resolved %s (%s, %s)", resolvent, leftClause, rightClause));
					if (resolvent.equals(Clause.EMPTY) && breakEarly) {
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
