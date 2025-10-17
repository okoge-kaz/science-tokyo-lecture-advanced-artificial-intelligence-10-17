package jp.onolab.discreteFunctions;

import java.util.ArrayList;

import jp.onolab.boa.TCIntMatrix;


/**
 * 抽象離散ブラックボックスベンチマーク関数クラス
 * @author shimazu
 */
public abstract class TAbstractDiscreteFunction implements IDiscreteFunction {
    /** シリアライズ用のid */
    private static final long serialVersionUID = 1L;

    /**  次元数 **/
    protected int fDimension;

    /** 各次元の基数 **/
    protected TCIntMatrix fCategory;

    /** 最適解の集合 **/
    protected ArrayList<TCIntMatrix> fOptima;

    /** 局所解の集合 **/
    protected ArrayList<TCIntMatrix> fLocalOptima;

    /** コンストラクタ
     * @param dimension 次元数
     */
    public TAbstractDiscreteFunction(int dimension) {
        fDimension = dimension;
        fOptima = new ArrayList<>();
        fLocalOptima = new ArrayList<>();
        fCategory = new TCIntMatrix(dimension).fill(2); // 基本的に問題はビット列を想定．
    }

    @Override
    public int getDimension() {
        return fDimension;
    }

    @Override
    public ArrayList<TCIntMatrix> getOptima() {
        return fOptima;
    }

    @Override
    public ArrayList<TCIntMatrix> getLocalOptima() {
        return fLocalOptima;
    }

    @Override
    public TCIntMatrix getCategory() {
        return fCategory;
    }
}
