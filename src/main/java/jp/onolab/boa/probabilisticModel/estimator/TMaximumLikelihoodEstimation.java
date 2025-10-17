package jp.onolab.boa.probabilisticModel.estimator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.onolab.boa.TCIntMatrix;
import jp.onolab.boa.TIndividual;
import jp.onolab.matrix2017.TCMatrix;
import jp.onolab.random2013.ICRandom;
import jp.onolab.random2013.TCJava48BitLcg;


/**
 * 最尤推定クラス
 * @author shimazu
 */
public class TMaximumLikelihoodEstimation extends TAbstractEstimator {
    /** シリアライズ用のid **/
    private static final long serialVersionUID = 1L;

    /**
     * コンストラクタ
     * @param category 各次元の基数
     */
    public TMaximumLikelihoodEstimation(TCIntMatrix category) {
        super(category);
    }

    public TMaximumLikelihoodEstimation(TMaximumLikelihoodEstimation other) {
        super(other);
    }

    @Override
    public TMaximumLikelihoodEstimation clone() {
        return new TMaximumLikelihoodEstimation(this);
    }

    @Override
    public TCMatrix estimate(int node, List<Integer> parents, List<TIndividual> data) {
        // 各値の組み合わせの出現頻度を計算
        TCMatrix cpt = new TCMatrix((int)Math.pow(fCMax, parents.size() + 1));
        int val;
        for(TIndividual indiv : data) {
            boolean isValid = true;
            val = indiv.getX().getValue(node);
            if(val < 0) {
                continue;
            }
            int index = val;
            for(int i = 0; i < parents.size(); ++i) {
                int parent = parents.get(i);
                val = indiv.getX().getValue(parent);
                if(val < 0) {
                    isValid = false;
                    break;
                }
                index += val * (int)Math.pow(fCMax, i + 1);
            }
            if(isValid) {
                cpt.setValue(index, cpt.getValue(index) + 1);
            }
        }
        // 各ノードの値に対して正規化することで条件付き確率に変換
        for(int i = 0; i < cpt.getDimension(); i+=fCMax) {
            int sum = 0;
            for(int j = i; j < i + fCategory.getValue(node); ++j) {
                sum += cpt.getValue(j);
            }
            for(int j = i; j < i + fCategory.getValue(node); ++j) {
                if(sum == 0) {
                    cpt.setValue(j, 1.0 / fCategory.getValue(node));
                }
                else {
                    cpt.setValue(j, cpt.getValue(j) / sum);

                }
            }
        }
        return cpt;
    }

    public static void main(String[] args) {
        ICRandom random = new TCJava48BitLcg(0);
        int lam = 5;
        int dim = 3;
        TCIntMatrix category = new TCIntMatrix(dim).fill(2);
        category.setValue(2, 3);
        List<TIndividual> pop = new ArrayList<>();
        for(int i = 0 ; i < lam; ++i) {
            TCIntMatrix vec = new TCIntMatrix(dim);
            for(int d = 0; d < dim; ++d) {
                vec.setValue(d, random.nextInt(0, category.getValue(d) - 1));
                System.out.print(vec.getValue(d));
            }
            System.out.println();
            TIndividual indiv = new TIndividual(dim);
            indiv.setX(vec);
            pop.add(indiv);
        }
        IEstimator mle = new TMaximumLikelihoodEstimation(category);
        // 各ノードが独立な場合
        for(int d = 0; d < dim; ++d) {
            System.out.println(mle.estimate(d, new ArrayList<>(), pop));
        }
        // 親ノードを1つ持つ場合
        for(int d = 0; d < dim; ++d) {
            System.out.println(mle.estimate(d, new ArrayList<>(Arrays.asList((d+1) % dim)), pop));
        }
        // 親ノードを2つ持つ場合
        for(int d = 0; d < dim; ++d) {
            System.out.println(mle.estimate(d, new ArrayList<>(Arrays.asList((d+1) % dim, (d+2) % dim)), pop));
        }
    }
}
