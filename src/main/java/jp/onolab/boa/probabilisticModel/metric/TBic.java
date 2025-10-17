package jp.onolab.boa.probabilisticModel.metric;

import java.util.List;

import jp.onolab.boa.TCIntMatrix;
import jp.onolab.boa.TIndividual;


/**
 * ベイズ情報量規準(Bayesian Information Criterion; BIC)のクラス．
 * @author shimazu
 */
public class TBic extends TAbstractMetric {
    /** シリアライズ用のid */
    private static final long serialVersionUID = 1L;

    public TBic(List<TIndividual> data, TCIntMatrix base) {
        super(data, base);
    }

    public TBic(TCIntMatrix base) {
        super(base);
    }

    public TBic(TBic other) {
        super(other);
    }

    public TBic clone() {
        return new TBic(this);
    }

    @Override
    public double localScore(int node, List<Integer> parents) {
        double conditional_entropy = getConditionalEntropy(node, parents, Math.E) * fDatasize;
        double regularization = fBase.getValue(node) - 1;
        for(int parent : parents) {
            regularization *= fBase.getValue(parent);
        }
        regularization *= Math.log(fDatasize) / 2.0;
        return -conditional_entropy - regularization;
    }
}
