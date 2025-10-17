package jp.onolab.boa.replacement;

import java.io.Serializable;
import java.util.List;

import jp.onolab.boa.TIndividual;


/**
 * 置換方法のインターフェース
 * @author shimazu
 */
public interface IReplacement extends Serializable {
    /**
     * 置換の適用．
     * @param parent 親集団．
     * @param candidate 候補解集団．
     */
    List<TIndividual> replace(List<TIndividual> parent, List<TIndividual> candidate);
}
