package jp.onolab.ga;

import jp.onolab.cloneableArrayList2019.ICCloneable;

public class TIndividual  implements ICCloneable<TIndividual> {
	
	private static final long serialVersionUID = 1L;

	/** 実行可能性 */
	private boolean fFeasible;

	/** 評価値 */
	private double fEvaluationValue;

	/** ビットストリング */
	private TBitString fBitString;

	public TIndividual() {
		fFeasible = false;
		fEvaluationValue = Double.NaN;
		fBitString = new TBitString();
	}
	
	public TIndividual(TIndividual src) {
		fFeasible = src.fFeasible;
		fEvaluationValue = src.fEvaluationValue;
		fBitString = new TBitString(src.fBitString);
	}
	
	@Override
	public TIndividual clone() {
		return new TIndividual(this);
	}
	
	public TIndividual copyFrom(TIndividual src) {
		fFeasible = src.fFeasible;
		fEvaluationValue = src.fEvaluationValue;
		fBitString.copyFrom(src.fBitString);
		return this;
	}
	
	@Override
	public String toString() {
		String str = fFeasible + " " + fEvaluationValue + "\n";
		str += fBitString.toString();
		return str;
	}

	public boolean isFeasible() {
		return fFeasible;
	}

	public void setFeasible(boolean feasible) {
		fFeasible = feasible;
	}

	public double getEvaluationValue() {
		return fEvaluationValue;
	}

	public void setEvaluationValue(double evaluationValue) {
		fEvaluationValue = evaluationValue;
	}

	public TBitString getBitString() {
		return fBitString;
	}
	
}
