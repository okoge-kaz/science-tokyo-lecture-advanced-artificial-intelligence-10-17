package jp.onolab.boa.selection;

import java.io.Serializable;
import java.util.List;

import jp.onolab.boa.TIndividual;


/**
 * 選択手法のインターフェース
 * @author shimazu
 */
public interface ISelection extends Serializable {
    /**
     * 与えられた集団に対して選択を適用．
     * @param population 集団
     * @return 選択後の集団
     */
    List<TIndividual> select(List<TIndividual> population);
}
