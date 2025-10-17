package jp.onolab.boa.probabilisticModel.estimator;

import java.io.Serializable;
import java.util.List;

import jp.onolab.boa.TIndividual;
import jp.onolab.matrix2017.TCMatrix;


/**
 * 確率分布の推定器のためのインターフェース
 * @author shimazu
 */
public interface IEstimator extends Serializable {
    /**
     * 確率分布を推定
     * @param node ノード番号
     * @param parents 親ノード番号の集合
     * @param data 推定に用いるデータセット
     * @return ノードの条件付き確率
     */
    TCMatrix estimate(int node, List<Integer> parents, List<TIndividual> data);

    IEstimator clone();
}
