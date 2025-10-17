package jp.onolab.discreteFunctions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.onolab.boa.TCIntMatrix;


/**
 * Deceptive-k Trap関数
 * @author shimazu
 */
public class TDeceptiveTrapFunction extends TAbstractDiscreteFunction {
    /** シリアライズ用のid */
    private static final long serialVersionUID = 1L;

    /** 各変数が変数間依存を持つ変数の数 */
    private final int fK;

    /** 評価値を決めるユーザパラメータ **/
    private final double fD;

    /**
     * コンストラクタ
     * @param dim 次元数
     * @param k 各変数に変数間依存のある変数の数
     * @param d 評価値を決めるユーザパラメータ
     */
    public TDeceptiveTrapFunction(int dim, int k, double d) {
        super(dim);
        assert(dim % k == 0);
        assert((k - 1) * d < 1.0);
        fK = k;
        fD = d;
        fOptima = new ArrayList<>(Arrays.asList(new TCIntMatrix(dim).fill(1)));
        /** fLocalOptimaは2^(dim/k)個だけ存在し，高次元の時にメモリに乗り切らないので未実装 **/
    }

    public TDeceptiveTrapFunction(int dim, int k) {
        this(dim, k, 0.1);
    }

    @Override
    public double evaluate(TCIntMatrix x) {
        double eval = 0.0;
        for(int i = 0; i < fDimension; i+=fK) {
            int bitSum = 0;
            for(int j = 0; j < fK; ++j) {
                bitSum += x.getValue(i + j);
            }
            if(bitSum == fK) {
                eval += 1.0;
            }
            else if(bitSum < fK - 1) {
                eval += 1.0 - (bitSum + 1) * fD;
            }
        }
        return -eval;
    }

    @Override
    public boolean isOptimum(TCIntMatrix x) {
        double eval = evaluate(x);
        if(fDimension / fK + eval < 1e-8) {
            return true;
        }
        return false;
    }

    // 簡単なテスト
    public static void main(String[] args) {
        int dim = 9;
        int k = 3;
        IDiscreteFunction obj = new TDeceptiveTrapFunction(dim, k);
        List<TCIntMatrix> optimum = obj.getOptima();
        System.out.println("最適解 --> eval: " + obj.evaluate(optimum.get(0)) + "\tisOptimum: " + obj.isOptimum(optimum.get(0)));
        TCIntMatrix x = new TCIntMatrix(dim);
        System.out.print("input: ");
        for(int i = 0; i < dim; ++i) {
            System.out.print(x.getValue(i));
        }
        System.out.println("\teval: " + obj.evaluate(x) + "\tisOptimum: " + obj.isOptimum(x));
        x.setValue(0, 1);
        x.setValue(1, 1);
        x.setValue(2, 1);
        x.setValue(4, 1);
        x.setValue(5, 1);
        x.setValue(8, 1);
        System.out.print("input: ");
        for(int i = 0; i < dim; ++i) {
            System.out.print(x.getValue(i));
        }
        System.out.println("\teval: " + obj.evaluate(x) + "\tisOptimum: " + obj.isOptimum(x));
    }
}
