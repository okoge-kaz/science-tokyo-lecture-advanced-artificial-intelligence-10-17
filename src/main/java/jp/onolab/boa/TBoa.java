package jp.onolab.boa;

import java.util.Collections;
import java.util.List;

import jp.onolab.boa.probabilisticModel.TBayesianNetwork;
import jp.onolab.boa.probabilisticModel.estimator.TMaximumLikelihoodEstimation;
import jp.onolab.boa.probabilisticModel.metric.IMetric;
import jp.onolab.boa.probabilisticModel.metric.TBic;
import jp.onolab.boa.probabilisticModel.networkSearch.TK2AlgorithmSymmetric;
import jp.onolab.boa.replacement.TTruncation;
import jp.onolab.boa.selection.TTop;
import jp.onolab.random2013.ICRandom;


/**
 * Bayesian Optimization Algorithm (BOA)のクラス．
 * @author shimazu
 */
public class TBoa extends TAbstractEda {

    /** シリアライズ用のid */
    private static final long serialVersionUID = 1L;

    /**
     * コンストラクタ
     * @param category 各次元の基数
     * @param populationSize 集団サイズ
     * @param random 乱数生成器
     */
    public TBoa(TCIntMatrix category, int populationSize, ICRandom random) {
        this(category, populationSize, 0.5, new TBic(category), category.getDimension(), random);
    }

    /**
     * コンストラクタ
     * @param category 各次元の基数
     * @param populationSize 集団サイズ
     * @param selectionRate 選択割合
     * @param metric ネットワークの評価指標
     * @param k 各ノードの親ノードの数の最大数
     * @param random 乱数生成器
     */
    public TBoa(TCIntMatrix category, int populationSize, double selectionRate,
                IMetric metric, int k,  ICRandom random) {
        super(new TBayesianNetwork(category, populationSize, metric,
                                   new TMaximumLikelihoodEstimation(category),
                                   new TK2AlgorithmSymmetric(k), random),
              new TTop(selectionRate),
              new TTruncation());
    }

    @Override
    public void update(List<TIndividual> pop) {
        // 評価回数と最良個体の更新
        fNumOfEval += pop.size();
        Collections.sort(pop);
        if(pop.get(0).getEvaluationValue() < fBestIndiv.getEvaluationValue()) {
            fBestIndiv = pop.get(0);
        }
        // 置換の実行．初期集団の場合のみそのまま代入．
        if(fPopulation.size() == 0) {
            fPopulation = pop;
        }
        else {
            fPopulation = fReplacement.replace(fPopulation, pop);
        }
        // 選択の実行
        List<TIndividual> selectedPop = fSelection.select(fPopulation);
        // 確率モデルの構築
        fModel.update(selectedPop);
    }
}
