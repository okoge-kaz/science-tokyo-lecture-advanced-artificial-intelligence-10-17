package jp.onolab.boa;


/**
 * 離散ブラックボックス関数最適化問題における個体クラス．
 * @author shimazu
 */
public class TIndividual implements Comparable<TIndividual> {
    /** 評価値 **/
    private double fEvaluationValue;

    /** 個体のベクトル表現 **/
    private TCIntMatrix fX;

    /** 実行可能解かどうか **/
    private boolean fFeasible;

    /**
     * コンストラクタ
     * @param dim 問題の次元
     * @param eval 評価値
     * @param feas 実行可能解かどうか
     */
    public TIndividual(int dim, double eval, boolean feas) {
        fX = new TCIntMatrix(dim);
        fEvaluationValue = eval;
        fFeasible = feas;
    }

    public TIndividual() {
        this(0, Double.MAX_VALUE, true);
    }

    public TIndividual(int dim) {
        this(dim, Double.MAX_VALUE, true);
    }

    public TIndividual(int dim, double eval) {
        this(dim, eval, true);
    }

    public TIndividual(TIndividual other) {
        fX = new TCIntMatrix(other.fX);
        fEvaluationValue = other.fEvaluationValue;
        fFeasible = other.fFeasible;
    }

    public TCIntMatrix getX() {
        return fX;
    }

    public void setX(TCIntMatrix x) {
        fX.copyFrom(x);
    }

    public double getEvaluationValue() {
        return fEvaluationValue;
    }

    public void setEvaluationValue(double value) {
        fEvaluationValue = value;
    }

    public boolean getFeasible() {
        return fFeasible;
    }

    public void setFeasible(boolean feas) {
        fFeasible = feas;
    }

    public TIndividual copyFrom(TIndividual src) {
        fX.copyFrom(src.fX);
        fEvaluationValue = src.fEvaluationValue;
        fFeasible = src.fFeasible;
        return this;
    }

    @Override
    public TIndividual clone() {
        return new TIndividual(this);
    }

    /**
     * 比較メソッドの実装．
     * 片方のみが実行可能解の場合，それが優れる．
     * 両方実行可能解の場合，評価値の小さいほうが優れる．
     * @param other
     * @return
     */
    @Override
    public int compareTo(TIndividual other) {
        if(fFeasible && other.fFeasible) {
            if(fEvaluationValue - other.fEvaluationValue < 0) {
                return -1;
            }
            else if(fEvaluationValue - other.fEvaluationValue > 0) {
                return 1;
            }
            else {
                return 0;
            }
        }
        else if(!fFeasible && !other.fFeasible) {
            return 0;
        }
        else if(fFeasible) {
            return -1;
        }
        else {
            return 1;
        }
    }
}
