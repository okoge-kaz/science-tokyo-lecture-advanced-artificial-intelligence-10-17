package jp.onolab.boa.probabilisticModel;

import java.util.ArrayList;
import java.util.List;

import jp.onolab.boa.TCIntMatrix;
import jp.onolab.boa.TIndividual;
import jp.onolab.random2013.ICRandom;


/**
 * 抽象確率モデルのクラス
 * @author shimazu
 */
public abstract class TAbstractProbabilisticModel implements IProbabilisticModel {
    /** シリアライズ用のid **/
    private static final long serialVersionUID = 1L;

    /** 集団サイズ **/
    protected int fPopulationSize;

    /** 次元数 **/
    protected int fDimension;

    /** 各次元の基数 **/
    protected TCIntMatrix fCategory;

    /** 最大基数 **/
    protected int fCMax;

    /** 乱数生成器 **/
    protected ICRandom fRandom;

    /**
     * コンストラクタ
     * @param category 各次元の基数
     * @param populationSize 集団サイズ
     * @param random 乱数生成器
     */
    public TAbstractProbabilisticModel(TCIntMatrix category, int populationSize, ICRandom random) {
        assert(category.getRowDimension() == 1);
        fDimension = category.getDimension();
        fCategory = category;
        fCMax = category.max(new ArrayList<>());
        fPopulationSize = populationSize;
        fRandom = random;
    }

    public TAbstractProbabilisticModel(TAbstractProbabilisticModel other) {
        fDimension = other.fDimension;
        fCategory = other.fCategory.clone();
        fCMax = other.fCMax;
        fPopulationSize = other.fPopulationSize;
        fRandom = other.fRandom;
    }

    @Override
    public List<TIndividual> initialize() {
        List<TIndividual> population = new ArrayList<>(fPopulationSize);
        for(int i = 0; i < fPopulationSize; ++i) {
            TCIntMatrix vector = new TCIntMatrix(fDimension);
            for(int j = 0; j < fDimension; ++j) {
                vector.setValue(j, fRandom.nextInt(0, fCategory.getValue(j) - 1));
            }
            TIndividual indiv = new TIndividual(fDimension);
            indiv.setX(vector);
            population.add(indiv);
        }
        return population;
    }

    @Override
    public IProbabilisticModel clone() {
        return null;
    }
}
