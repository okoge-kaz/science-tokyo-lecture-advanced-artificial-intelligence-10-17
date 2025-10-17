package jp.onolab.cloneableArrayList2019;

import java.io.Serializable;

public interface ICCloneable<T> extends Cloneable, Serializable {
	
	T clone();

}
