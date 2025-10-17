package jp.onolab.discreteFunctions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.onolab.boa.TCIntMatrix;


/**
 * One-Max関数
 * @author shimazu
 */
public class TOneMaxFunction extends TAbstractDiscreteFunction {
    /** シリアライズ用のid */
    private static final long serialVersionUID = 1L;

    /**
     * コンストラクタ
     * @param dim 次元数
     */
    public TOneMaxFunction(int dim) {
        super(dim);
        TCIntMatrix optimum = new TCIntMatrix(dim).fill(1);
        fOptima = new ArrayList<>(Arrays.asList(optimum));
    }

    @Override
    public double evaluate(TCIntMatrix x) {
        double eval = 0.0;
        for(int i = 0; i < fDimension; ++i) {
            eval += x.getValue(i);
        }
        return -eval;
    }

    @Override
    public boolean isOptimum(TCIntMatrix x) {
        double eval = evaluate(x);
        if(fDimension + eval < 1e-8) {
            return true;
        }
        return false;
    }

    // 簡単なテスト
    public static void main(String[] args) {
        int dim = 5;
        IDiscreteFunction obj = new TOneMaxFunction(dim);
        List<TCIntMatrix> optimum = obj.getOptima();
        System.out.print("input: ");
        for(int i = 0; i < dim; ++i) {
            System.out.print(optimum.get(0).getValue(i));
        }
        System.out.print("\teval: " + obj.evaluate(optimum.get(0)));
        System.out.println("\tisOptimum: " + obj.isOptimum(optimum.get(0)));
        TCIntMatrix x = new TCIntMatrix(dim);
        System.out.print("input: ");
        for(int i = 0; i < dim; ++i) {
            System.out.print(x.getValue(i));
        }
        System.out.print("\teval: " + obj.evaluate(x));
        System.out.println("\tisOptimum: " + obj.isOptimum(x));
        x.setValue(1, 1);
        x.setValue(3, 1);
        System.out.print("input: ");
        for(int i = 0; i < dim; ++i) {
            System.out.print(x.getValue(i));
        }
        System.out.print("\teval: " + obj.evaluate(x));
        System.out.println("\tisOptimum: " + obj.isOptimum(x));
    }
}
