package jp.onolab.ga;

import java.util.Comparator;

/**
 * Compares solutions based on the objective function of the single-objective problem.
 * <h3>Properties:</h3>
 * No parameter required.
 *
 * @since 2
 * @author isao
 */
public class TEvaluationValueComparator implements Comparator<TIndividual> {

	/**
	 * コンストラクタ
	 */
	public TEvaluationValueComparator() {
	}

	@Override
	public int compare(TIndividual a, TIndividual b) {
		if (a.isFeasible() == true && b.isFeasible() == false) {
			return -1;
		} else if (a.isFeasible() == false && b.isFeasible() == true) {
			return 1;
		}
		if (a.getEvaluationValue() - b.getEvaluationValue() < 0.0) {
			return -1;
		} else if (a.getEvaluationValue() - b.getEvaluationValue() > 0.0) {
			return 1;
		} else {
			return 0;
		}
	} 

}
