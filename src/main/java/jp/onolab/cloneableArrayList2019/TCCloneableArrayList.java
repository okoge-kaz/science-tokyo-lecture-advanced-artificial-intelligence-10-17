package jp.onolab.cloneableArrayList2019;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 複製可能なArrayListクラス．
 * @author isao
 *
 * @param <X>
 */
public class TCCloneableArrayList<
	X extends ICCloneable<X>
> extends ArrayList<X> {

	/** For serialization */
	private static final long serialVersionUID = 1L;
	
	/** テンプレートオブジェクト */
	private X fTemplate;

	/**
	 * コンストラクタ
	 * @param template テンプレート
	 */
	public TCCloneableArrayList(X template) {
		fTemplate = template;
	}

	/**
	 * コピーコンストラクタ
	 * @param src コピー元
	 */
	public TCCloneableArrayList(TCCloneableArrayList<X> src) {
		fTemplate = src.fTemplate;
		addAllCopyOf(src);
	}
	
	/**
	 * 複製を返す．
	 */
	@Override
	public TCCloneableArrayList<X> clone() {
		return new TCCloneableArrayList<X>(this);
	}

	/**
	 * コピーする．
	 * @param src　コピー元
	 * @return　自分自身
	 */
	public TCCloneableArrayList<X> copyFrom(TCCloneableArrayList<X> src) {
		fTemplate = src.fTemplate;
		clear();
		for (X s : src) {
			add(s.clone());
		}
		return this;
	}

	/**
	 * 大きさを変更する．配列の要素はテンプレートの複製で初期化される．
	 * @param size 大きさ
	 */
	public TCCloneableArrayList<X> resize(int size) {
		assert size >= 0;
		int diff = size - size();
		if (diff == 0) {
			return this;
		}
		if (diff > 0) {	// the new size is bigger than the old
			do {
				add(fTemplate.clone());
				diff--;
			} while (diff > 0);
		} else {		// the new size is smaller than the old
			do {
				remove(size() - 1);
				diff++;
			} while (diff < 0);
		}
		assert size() == size;
		return this;
	}

	/**
	 * 引数のコピーを配列の末尾に追加する．
	 * @param src コピー元
	 * @return
	 */
	public boolean addCopyOf(X src) {
		return add(src.clone());
	}

	/**
	 * 引数の全要素のコピーを配列の末尾に追加する．
	 * @param src コピー元
	 * @return
	 */
	public boolean addAllCopyOf(Collection<? extends X> src) {
		for (X s : src) {
			addCopyOf(s);
		}
		return src.size() != 0;
	}

	/**
	 * 添字で指定された要素のコピーを返す．
	 * @param i 添字
	 * @return 要素のコピー
	 */
	public X getCopyOf(int i) {
		return (X)get(i).clone();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Size: " + size() + "\n");
		for (X s: this) {
			sb.append(s);
			sb.append("\n");
		}
		return sb.toString();
	}

	/**
	 * テンプレートオブジェクトを返す．
	 * @return テンプレートオブジェクト
	 */
	public X getTemplate() {
		return fTemplate;
	}

}
