package jp.onolab.boa.replacement;

import java.util.Collections;
import java.util.List;

import jp.onolab.boa.TIndividual;


/**
 * 下位置換のクラス
 * @author shimazu
 */
public class TTruncation extends TAbstractReplacement {
    /** シリアライズ用のid */
    private static final long serialVersionUID = 1L;

    @Override
    public List<TIndividual> replace(List<TIndividual> parent, List<TIndividual> candidate) {
        Collections.sort(parent);
        for(int i = 0; i < candidate.size(); ++i) {
            parent.get(parent.size() - i - 1).copyFrom(candidate.get(i));
        }
        return parent;
    }
}
