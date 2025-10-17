package jp.onolab.boa.probabilisticModel;

import java.io.Serializable;
import java.util.List;

import jp.onolab.boa.TIndividual;


/**
 * 確率モデルのインターフェース
 * @author shimazu
 */
public interface IProbabilisticModel extends Serializable {
    /**
     * 初期集団の生成．
     * @return 初期集団
     */
    List<TIndividual> initialize();

    /**
     * 確率モデルの更新
     * @param 集団
     */
    void update(List<TIndividual> pop);

    /**
     * 個体のサンプリング．
     * @param populationSize 個体数
     * @return 集団
     */
    List<TIndividual> sampling(int populationSize);

    /**
     * 確率分布の収束の程度を計算．
     * [0, 1]の範囲を取り，1に近いほど確率分布が収束していることを示す．
     * @return 収束具合
     */
    double getConvergence();

    /**
     * 確率分布が収束したかの判定．
     * @return 収束したかどうか
     */
    boolean isConvergence();

    IProbabilisticModel clone();
}
