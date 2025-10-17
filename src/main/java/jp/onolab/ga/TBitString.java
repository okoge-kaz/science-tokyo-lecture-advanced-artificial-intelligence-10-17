package jp.onolab.ga;

import jp.onolab.cloneableArrayList2019.ICCloneable;
import jp.onolab.random2013.ICRandom;
import jp.onolab.random2013.TCJava48BitLcg;

public class TBitString implements ICCloneable<TBitString> {
	
	private static final long serialVersionUID = 1L;

	private boolean[] fArray;
		
	public TBitString() {
		fArray = new boolean [0];
	}
	
	public TBitString(int n) {
		fArray = new boolean [n];
	}
	
	public TBitString(String str) {
		fArray = new boolean [str.length()];
		for (int i = 0; i < fArray.length; ++i) {
			if (str.charAt(i) == '0') {
				fArray[i] = false;
			} else if (str.charAt(i) == '1') {
				fArray[i] = true;
			} else {
				throw new RuntimeException();
			}
		}
	}
	
	public TBitString(TBitString src) {
		fArray = new boolean [src.fArray.length];
		for (int i = 0; i < fArray.length; ++i) {
			fArray[i] = src.fArray[i];
		}
	}
	
	@Override
	public TBitString clone() {
		return new TBitString(this);
	}
	
	public TBitString copyFrom(TBitString src) {
		if (fArray.length != src.fArray.length) {
			fArray = new boolean [src.fArray.length];
		}
		for (int i = 0; i < fArray.length; ++i) {
			fArray[i] = src.fArray[i];
		}
		return this;
	}
	
	@Override
	public String toString() {
		String str = "";
		for (int i = 0; i < fArray.length; ++i) {
			if (fArray[i]) {
				str += "1";
			} else {
				str += "0";
			}
		}
		return str;
	}
	
	public TBitString fromString(String str) {
		if (str.length() != fArray.length) {
			fArray = new boolean [str.length()];
		}
		for (int i = 0; i < fArray.length; ++i) {
			if (str.charAt(i) == '0') {
				fArray[i] = false;
			} else if (str.charAt(i) == '1') {
				fArray[i] = true;
			} else {
				throw new RuntimeException();
			}
		}
		return this;
	}
	
	@Override
	public boolean equals(Object o) {
		TBitString bs = (TBitString)o;
		if (fArray.length != bs.fArray.length) {
			return false;
		}
		for (int i = 0; i < fArray.length; ++i) {
			if (fArray[i] != bs.fArray[i]) {
				return false;
			}
		}
		return true;
	}
	
	public void set(int index, boolean bit) {
		fArray[index] = bit;
	}
	
	public boolean get(int index) {
		return fArray[index];
	}
	
	public TBitString random(ICRandom rand) {
		for (int i = 0; i < fArray.length; ++i) {
			fArray[i] = rand.nextBoolean();
		}
		return this;
	}
	
	public int getLength() {
		return fArray.length;
	}
	
	public TBitString setLength(int length) {
		if (fArray.length != length) {
			fArray = new boolean [length];
		}
		return this;
	}
	
	public TBitString fill(boolean b) {
		for (int i = 0; i < fArray.length; ++i) {
			fArray[i] = b;
		}
		return this;
	}
		
	public TBitString flip(int index) {
		fArray[index] = !fArray[index];
		return this;
	}
	
	public TBitString flipAll() {
		for (int i = 0; i < fArray.length; ++i) {
			fArray[i] = !fArray[i];
		}
		return this;
	}
	
	public TBitString and(TBitString bs) {
		assert fArray.length == bs.fArray.length;
		for (int i = 0; i < fArray.length; ++i) {
			fArray[i] &= bs.fArray[i];
		}
		return this;
	}
	
	public TBitString or(TBitString bs) {
		assert fArray.length == bs.fArray.length;
		for (int i = 0; i < fArray.length; ++i) {
			fArray[i] |= bs.fArray[i];
		}
		return this;		
	}
	
	public TBitString xor(TBitString bs) {
		assert fArray.length == bs.fArray.length;
		for (int i = 0; i < fArray.length; ++i) {
			fArray[i] ^= bs.fArray[i];
		}
		return this;				
	}
	
	public static void main(String[] args) throws Exception {
		ICRandom rand = new TCJava48BitLcg();
		int n = 5;
		TBitString bs1 = new TBitString(n).random(rand);
		System.out.println(bs1);
	}
	
}
