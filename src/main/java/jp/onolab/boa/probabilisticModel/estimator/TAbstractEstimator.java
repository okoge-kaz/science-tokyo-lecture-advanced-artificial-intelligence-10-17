package jp.onolab.boa.probabilisticModel.estimator;

import java.util.ArrayList;

import jp.onolab.boa.TCIntMatrix;


/**
 * 抽象確率分布推定器クラス
 * @author shimazu
 */
public abstract class TAbstractEstimator implements IEstimator {
    /** シリアライズ用のid **/
    private static final long serialVersionUID = 1L;

    /** 各次元の基数 **/
    TCIntMatrix fCategory;

    /** 最大基数 **/
    int fCMax;

    /**
     * コンストラクタ
     * @param cateogry 各次元の基数
     */
    public TAbstractEstimator(TCIntMatrix category) {
        fCategory = category;
        fCMax = fCategory.max(new ArrayList<>());
    }

    public TAbstractEstimator(TAbstractEstimator other) {
        fCategory = other.fCategory.clone();
        fCMax = other.fCMax;
    }

    @Override
    public IEstimator clone() {
        return null;
    }
}
