import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


public class Main {
	public static void main(String[] args) {
		Atom a11 = new Atom("A11");
		Atom a12 = new Atom("A12");
		Atom a13 = new Atom("A13");
		Atom a21 = new Atom("A21");
		Atom a22 = new Atom("A22");
		Atom a23 = new Atom("A23");
		Literal p11 = Literal.createPositive(a11);
		Literal p12 = Literal.createPositive(a12);
		Literal p13 = Literal.createPositive(a13);
		Literal p21 = Literal.createPositive(a21);
		Literal p22 = Literal.createPositive(a22);
		Literal p23 = Literal.createPositive(a23);
		Literal n11 = Literal.createNegative(a11);
		Literal n12 = Literal.createNegative(a12);
		Literal n13 = Literal.createNegative(a13);
		Literal n21 = Literal.createNegative(a21);
		Literal n22 = Literal.createNegative(a22);
		Literal n23 = Literal.createNegative(a23);
		
		Formula formula = new Formula();
		/*formula.addClause(p11, n12, n13);
		formula.addClause(n11, p12, n13);
		formula.addClause(n11, n12, p13);
		formula.addClause(n11, n12, n13);
		formula.addClause(p21, n22, n23);
		formula.addClause(n21, p22, n23);
		formula.addClause(n21, n22, p23);
		formula.addClause(n21, n22, n23);*/
		
		formula.addClause(n11, n12);
		formula.addClause(n11, n13);
		formula.addClause(n12, n13);
		formula.addClause(n21, n22);
		formula.addClause(n21, n23);
		formula.addClause(n22, n23);
		
		formula.addClause(p11, p21);
		formula.addClause(n11, n21);
		formula.addClause(p12, p22);
		formula.addClause(n12, n22);
		formula.addClause(p13, p23);
		formula.addClause(n13, n23);
		Set<Clause> originalClauses = new HashSet<Clause>(formula.getClauses());
		
		System.out.println("Formula: " + formula);
		
		
		Resolver resolver = new Resolver(formula);
		Clause emptyClause = resolver.resolveEmptyClause();
		if (emptyClause != null) {
			System.out.println("");
			System.out.println("================================================================");
			System.out.println("                        UNSATISFIABLE");
			System.out.println("================================================================");
			System.out.println("");
			System.out.println("Clauses in formula:");
			
			for (Clause clause : originalClauses)
				System.out.println(clause);

			System.out.println("");
			System.out.println("Derivation:");
			printDerivation(emptyClause);
		} else {
			System.out.println("");
			System.out.println("================================================================");
			System.out.println("                         SATISFIABLE");
			System.out.println("================================================================");
			System.out.println("");
			System.out.println("Resolved clauses:");
			for (Clause clause : formula.getClauses())
				System.out.println(clause);
		}
	}
	
	public static void printDerivation(Clause clause) {
		if (clause.getLeftOrigin() != null && clause.getRightOrigin() != null) {
			printDerivation(clause.getLeftOrigin());
			printDerivation(clause.getRightOrigin());
			System.out.println(String.format("%s, %s => %s", clause.getLeftOrigin(), clause.getRightOrigin(), clause));
		}
	}
}
