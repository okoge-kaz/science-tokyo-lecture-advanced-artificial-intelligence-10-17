package jp.onolab.discreteFunctions;

import java.io.Serializable;
import java.util.ArrayList;

import jp.onolab.boa.TCIntMatrix;


/**
 * 離散ブラックボックス最適化用ベンチマーク関数のインターフェース
 * @author shimazu
 */
public interface IDiscreteFunction extends Serializable {
    /**
     * ベクトルxでの評価値を返す
     * @param x ベクトル
     * @return 評価値
     */
    double evaluate(TCIntMatrix x);

    /**
     * ベクトルxが最適解かどうかを返す．
     * @param x ベクトル
     * @return 最適解かどうかのbool値
     */
    boolean isOptimum(TCIntMatrix x);

    int getDimension();

    /**
     * 最適解を返す
     * @return 最適解の集合
     */
    ArrayList<TCIntMatrix> getOptima();

    /**
     * 局所解を返す
     * @return 局所解の集合
     */
    ArrayList<TCIntMatrix> getLocalOptima();

    /**
     * 各次元の基数を返す
     * @return 各次元の基数
     */
    TCIntMatrix getCategory();
}
