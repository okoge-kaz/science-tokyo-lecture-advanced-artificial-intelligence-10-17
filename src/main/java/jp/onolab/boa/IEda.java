package jp.onolab.boa;

import java.io.Serializable;
import java.util.List;

import jp.onolab.boa.probabilisticModel.IProbabilisticModel;


/**
 * 分布推定アルゴリズム(Estimation of Distribution Algorithms; EDAs)のインターフェース．
 * @author shimazu
 */
public interface IEda extends Serializable {
    /**
     * 最適化手法の初期化．基本的には初期集団の生成を行う．
     * @return 初期集団
     */
    List<TIndividual> initialize();

    /**
     * 確率分布の更新
     * @param 集団
     */
    void update(List<TIndividual> pop);

    /**
     * 個体のサンプリング．
     * @param lam 生成する個体数
     * @return 集団
     */
    List<TIndividual> samplePopulation(int lam);

    /**
     * 確率分布の収束の程度を計算．
     * @return 収束具合
     */
    double getConvergence();

    /**
     * 確率分布が収束したかの判定．
     * @return 収束したかどうか
     */
    boolean isConvergence();

    /**
     * 最良個体を返す．
     * @return 最良個体
     */
    TIndividual getBestIndividual();

    /**
     * 評価回数を返す．
     * @return 評価回数
     */
    int getNumOfEvaluations();

    /**
     * 最適化手法内で保持している集団を返す．
     * @return 集団
     */
    List<TIndividual> getPopulation();

    /**
     * 確率モデルを返す．
     * 確率モデルを外部で変更させないため，確率モデルをcloneして返す．
     * @return 確率モデル
     */
    IProbabilisticModel getProbabilisticModel();
}
