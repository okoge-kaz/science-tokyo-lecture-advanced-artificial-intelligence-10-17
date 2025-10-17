package jp.onolab.indexUtility2013;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 * 一次元配列のインデックス操作を行うためのユーティリティ．
 * 
 * @author fnob
 */
public class TCIndexUtility {

	/**
	 * 比較可能なクラスリストに対して最小値のインデックスを発見する．
	 * @param <T> 比較可能なクラス
	 * @param a <T>型オブジェクトを要素とするリスト
	 * @param index インデックス
	 */
	public static <T extends Comparable<? super T>> T max(final ArrayList<T> a, ArrayList<Integer> index){
		T max = a.get(0);
		index.clear();
		index.add(0);
		
		for(int i=1, n=a.size(); i<n; i++){
			if(max.compareTo(a.get(i)) <= 0){
				if(max.compareTo(a.get(i)) == 0){
					index.add(i);
				}else{
					index.clear();
					index.add(i);
					max = a.get(i);
				}
			}
		}
		return max;	
	}
	
	/**
	 * 比較可能なクラス配列に対して最大値のインデックスを発見する．
	 * @param <T> 比較可能なクラス
	 * @param a <T>型配列
	 * @param index インデックス
	 */
	public static <T extends Comparable<? super T>> T max(final T[] a, ArrayList<Integer> index){
		T max = a[0];
		index.clear();
		index.add(0);
		
		for(int i=1, n=a.length; i<n; i++){
			if(max.compareTo(a[i]) <= 0){
				if(max.compareTo(a[i]) == 0){
					index.add(i);
				}else{
					index.clear();
					index.add(i);
					max = a[i];
				}
			}
		}
		return max;	
	}

	/**
	 * byte型配列の最大値のインデックスを発見する．　
	 * @param a byte型配列
	 * @param index 最大要素のインデックス
	 * @return max 最大値
	 * @author fnob
	 */
	public static byte max(final byte[] a, ArrayList<Integer> index){
		byte max = a[0];
		index.clear();
		index.add(0);
		
		for(int i=1, n=a.length; i<n; i++){
			if(Byte.valueOf(max).compareTo(Byte.valueOf(a[i])) <= 0){
				if(Byte.valueOf(max).compareTo(Byte.valueOf(a[i])) == 0){
					index.add(i);
				}else{
					index.clear();
					index.add(i);
					max = a[i];
				}
			}
		}
		return max;
	}
	
	/**
	 * short型配列の最大値のインデックスを発見する．　
	 * @param a short型配列
	 * @param index 最大要素のインデックス
	 * @return max 最大値
	 * @author fnob
	 */
	public static short max(final short[] a, ArrayList<Integer> index){
		short max = a[0];
		index.clear();
		index.add(0);
		
		for(int i=1, n=a.length; i<n; i++){
			if(Short.valueOf(max).compareTo(Short.valueOf(a[i])) <= 0){
				if(Short.valueOf(max).compareTo(Short.valueOf(a[i])) == 0){
					index.add(i);
				}else{
					index.clear();
					index.add(i);
					max = a[i];
				}
			}
		}
		return max;
	}
	
	/**
	 * int型配列の最大値のインデックスを発見する．　
	 * @param a int型配列
	 * @param index 最大要素のインデックス
	 * @return max 最大値
	 * @author fnob
	 */
	public static int max(final int[] a, ArrayList<Integer> index){
		int max = a[0];
		index.clear();
		index.add(0);
		
		for(int i=1, n=a.length; i<n; i++){
			if(Integer.valueOf(max).compareTo(Integer.valueOf(a[i])) <= 0){
				if(Integer.valueOf(max).compareTo(Integer.valueOf(a[i])) == 0){
					index.add(i);
				}else{
					index.clear();
					index.add(i);
					max = a[i];
				}
			}
		}
		return max;
	}	
	
	/**
	 * long型配列の最大値のインデックスを発見する．　
	 * @param a long型配列
	 * @param index 最大要素のインデックス
	 * @return max 最大値
	 * @author fnob
	 */
	public static long max(final long[] a, ArrayList<Integer> index){
		long max = a[0];
		index.clear();
		index.add(0);

		for(int i=1, n=a.length; i<n; i++){
			if(Long.valueOf(max).compareTo(Long.valueOf(a[i])) <= 0){
				if(Long.valueOf(max).compareTo(Long.valueOf(a[i])) == 0){
					index.add(i);
				}else{
					index.clear();
					index.add(i);
					max = a[i];
				}
			}
		}
		return max;
	}
	
	/**
	 * float型配列の最大値のインデックスを発見する．　
	 * @param a float型配列
	 * @param index 最大要素のインデックス
	 * @return max 最大値
	 * @author fnob
	 */
	public static float max(final float[] a, ArrayList<Integer> index){
		float max = a[0];
		index.clear();
		index.add(0);

		for(int i=1, n=a.length; i<n; i++){
			if(Float.valueOf(max).compareTo(Float.valueOf(a[i])) <= 0){
				if(Float.valueOf(max).compareTo(Float.valueOf(a[i])) == 0){
					index.add(i);
				}else{
					index.clear();
					index.add(i);
					max = a[i];
				}
			}
		}
		return max;
	}
	
	/**
	 * double型配列の最大値のインデックスを発見する．　
	 * @param a double型配列
	 * @param index 最大要素のインデックス
	 * @return max 最大値
	 * @author fnob
	 */
	public static double max(final double[] a, ArrayList<Integer> index){
		double max = a[0];
		index.clear();
		index.add(0);

		for(int i=1, n=a.length; i<n; i++){
			if(Double.valueOf(max).compareTo(Double.valueOf(a[i])) <= 0){
				if(Double.valueOf(max).compareTo(Double.valueOf(a[i])) == 0){
					index.add(i);
				}else{
					index.clear();
					index.add(i);
					max = a[i];
				}
			}
		}
		return max;
	}
	
	/**
	 * char型配列の最大値のインデックスを発見する．　
	 * @param a double型配列
	 * @param index 最大要素のインデックス
	 * @return max 最大値
	 * @author fnob
	 */
	public static char max(final char[] a, ArrayList<Integer> index){
		char max = a[0];
		index.clear();
		index.add(0);

		for(int i=1, n=a.length; i<n; i++){
			if(Character.valueOf(max).compareTo(Character.valueOf(a[i])) <= 0){
				if(Character.valueOf(max).compareTo(Character.valueOf(a[i])) == 0){
					index.add(i);
				}else{
					index.clear();
					index.add(i);
					max = a[i];
				}
			}
		}
		return max;
	}
	
	/**
	 * boolean型配列の最大値のインデックスを発見する．　
	 * @param a boolean型配列
	 * @param index 最大要素のインデックス
	 * @return max 最大値
	 * @author fnob
	 */
	public static boolean max(final boolean[] a, ArrayList<Integer> index){
		boolean max = a[0];
		index.clear();
		index.add(0);

		for(int i=1, n=a.length; i<n; i++){
			if(Boolean.valueOf(max).compareTo(Boolean.valueOf(a[i])) <= 0){
				if(Boolean.valueOf(max).compareTo(Boolean.valueOf(a[i])) == 0){
					index.add(i);
				}else{
					index.clear();
					index.add(i);
					max = a[i];
				}
			}
		}
		return max;
	}
	
	/**
	 * 比較可能なクラス配列に対して最小値のインデックスを発見する．
	 * @param <T> 比較可能なクラス
	 * @param a <T>型配列
	 * @param index インデックス
	 */
	public static <T extends Comparable<? super T>> T min(final T[] a, ArrayList<Integer> index){
		T min = a[0];
		index.clear();
		index.add(0);
		
		for(int i=1, n=a.length; i<n; i++){
			if(min.compareTo(a[i]) >= 0){
				if(min.compareTo(a[i]) == 0){
					index.add(i);
				}else{
					index.clear();
					index.add(i);
					min = a[i];
				}
			}
		}
		return min;	
	}
	
	/**
	 * 比較可能なクラスリストに対して最小値のインデックスを発見する．
	 * @param <T> 比較可能なクラス
	 * @param a <T>型オブジェクトを要素とするリスト
	 * @param index インデックス
	 */
	public static <T extends Comparable<? super T>> T min(final ArrayList<T> a, ArrayList<Integer> index){
		T min = a.get(0);
		index.clear();
		index.add(0);
		
		for(int i=1, n=a.size(); i<n; i++){
			if(min.compareTo(a.get(i)) >= 0){
				if(min.compareTo(a.get(i)) == 0){
					index.add(i);
				}else{
					index.clear();
					index.add(i);
					min = a.get(i);
				}
			}
		}
		return min;	
	}

	/**
	 * byte型配列の最小値のインデックスを発見する．　
	 * @param a byte型配列
	 * @param index 最小要素のインデックス
	 * @return min 最小値
	 * @author fnob
	 */
	public static byte min(final byte[] a, ArrayList<Integer> index){
		byte min = a[0];
		index.clear();
		index.add(0);
		
		for(int i=1, n=a.length; i<n; i++){
			if(Byte.valueOf(min).compareTo(Byte.valueOf(a[i])) >= 0){
				if(Byte.valueOf(min).compareTo(Byte.valueOf(a[i])) == 0){
					index.add(i);
				}else{
					index.clear();
					index.add(i);
					min = a[i];
				}
			}
		}
		return min;
	}	
	
	/**
	 * short型配列の最小値のインデックスを発見する．　
	 * @param a short型配列
	 * @param index 最小要素のインデックス
	 * @return min 最小値
	 * @author fnob
	 */
	public static short min(final short[] a, ArrayList<Integer> index){
		short min = a[0];
		index.clear();
		index.add(0);
		
		for(int i=1, n=a.length; i<n; i++){
			if(Short.valueOf(min).compareTo(Short.valueOf(a[i])) >= 0){
				if(Short.valueOf(min).compareTo(Short.valueOf(a[i])) == 0){
					index.add(i);
				}else{
					index.clear();
					index.add(i);
					min = a[i];
				}
			}
		}
		return min;
	}	
	
	/**
	 * int型配列の最小値のインデックスを発見する．　
	 * @param a double型配列
	 * @param index 最小要素のインデックス
	 * @return min 最小値
	 * @author fnob
	 */
	public static int min(final int[] a, ArrayList<Integer> index){
		int min = a[0];
		index.clear();
		index.add(0);
		
		for(int i=1, n=a.length; i<n; i++){
			if(Integer.valueOf(min).compareTo(Integer.valueOf(a[i])) >= 0){
				if(Integer.valueOf(min).compareTo(Integer.valueOf(a[i])) == 0){
					index.add(i);
				}else{
					index.clear();
					index.add(i);
					min = a[i];
				}
			}
		}
		return min;
	}
	
	/**
	 * long型配列の最小値のインデックスを発見する．　
	 * @param a long型配列
	 * @param index 最小要素のインデックス
	 * @return min 最小値
	 * @author fnob
	 */
	public static long min(final long[] a, ArrayList<Integer> index){
		long min = a[0];
		index.clear();
		index.add(0);
		
		for(int i=1, n=a.length; i<n; i++){
			if(Long.valueOf(min).compareTo(Long.valueOf(a[i])) >= 0){
				if(Long.valueOf(min).compareTo(Long.valueOf(a[i])) == 0){
					index.add(i);
				}else{
					index.clear();
					index.add(i);
					min = a[i];
				}
			}
		}
		return min;
	}
	
	/**
	 * float型配列の最小値のインデックスを発見する．　
	 * @param a float型配列
	 * @param index 最小要素のインデックス
	 * @return min 最小値
	 * @author fnob
	 */
	public static float min(final float[] a, ArrayList<Integer> index){
		float min = a[0];
		index.clear();
		index.add(0);
		
		for(int i=1, n=a.length; i<n; i++){
			if(Float.valueOf(min).compareTo(Float.valueOf(a[i])) >= 0){
				if(Float.valueOf(min).compareTo(Float.valueOf(a[i])) == 0){
					index.add(i);
				}else{
					index.clear();
					index.add(i);
					min = a[i];
				}
			}
		}
		return min;
	}
	
	/**
	 * double型配列の最小値のインデックスを発見する．　
	 * @param a double型配列
	 * @param index 最小要素のインデックス
	 * @return min 最小値
	 * @author fnob
	 */
	public static double min(final double[] a, ArrayList<Integer> index){
		double min = a[0];
		index.clear();
		index.add(0);
		
		for(int i=1, n=a.length; i<n; i++){
			if(Double.valueOf(min).compareTo(Double.valueOf(a[i])) >= 0){
				if(Double.valueOf(min).compareTo(Double.valueOf(a[i])) == 0){
					index.add(i);
				}else{
					index.clear();
					index.add(i);
					min = a[i];
				}
			}
		}
		return min;
	}
	
	/**
	 * char型配列の最小値のインデックスを発見する．　
	 * @param a char型配列
	 * @param index 最小要素のインデックス
	 * @return min 最小値
	 * @author fnob
	 */
	public static char min(final char[] a, ArrayList<Integer> index){
		char min = a[0];
		index.clear();
		index.add(0);
		
		for(int i=1, n=a.length; i<n; i++){
			if(Character.valueOf(min).compareTo(Character.valueOf(a[i])) >= 0){
				if(Character.valueOf(min).compareTo(Character.valueOf(a[i])) == 0){
					index.add(i);
				}else{
					index.clear();
					index.add(i);
					min = a[i];
				}
			}
		}
		return min;
	}
	
	/**
	 * boolean型配列の最小値のインデックスを発見する．　
	 * @param a boolean型配列
	 * @param index 最小要素のインデックス
	 * @return min 最小値
	 * @author fnob
	 */
	public static boolean min(final boolean[] a, ArrayList<Integer> index){
		boolean min = a[0];
		index.clear();
		index.add(0);
		
		for(int i=1, n=a.length; i<n; i++){
			if(Boolean.valueOf(min).compareTo(Boolean.valueOf(a[i])) >= 0){
				if(Boolean.valueOf(min).compareTo(Boolean.valueOf(a[i])) == 0){
					index.add(i);
				}else{
					index.clear();
					index.add(i);
					min = a[i];
				}
			}
		}
		return min;
	}
		
	/**
	 * 比較可能なクラスリストに対してインデックスソートを行う．
	 * 引数で渡されるリストの中身は変更されないことに注意．
	 * @param <T> 比較可能なクラス
	 * @param a <T>型オブジェクトを要素とするリスト
	 * @param index インデックス
	 * @param mode 昇順，降順の指定
	 * 		mode == true : 降順
	 * 		mode == false : 昇順
	 */
	public static <T extends Comparable<T>> void indexSort(final ArrayList<T> a, Integer[] index, boolean mode){
		makeIndex(index, a.size());
		Arrays.sort(index, new Comparator<Integer>() {
			public int compare(Integer o1, Integer o2) {
				return a.get(o1).compareTo((T) a.get(o2));
			}
		});
		if(mode) reverseIndex(index);
	}

	/**
	 * 比較可能なクラス配列に対してインデックスソートを行う．
	 * 引数で渡されるリストの中身は変更されないことに注意．
	 * @param <T> 比較可能なクラス
	 * @param a <T>型配列
	 * @param index インデックス
	 * @param mode 昇順，降順の指定
	 * 		mode == true : 降順
	 * 		mode == false : 昇順
	 */
	public static <T extends Comparable<T>> void indexSort(final T[] a, Integer[] index, boolean mode){
		makeIndex(index, a.length);
		Arrays.sort(index, new Comparator<Integer>() {
			public int compare(Integer o1, Integer o2) {
				return a[o1].compareTo((T) a[o2]);
			}
		});
		if(mode) reverseIndex(index);
	}
	
	/**
	 * byte型配列に対してインデックスソートを行う．
	 * 引数として渡される配列の中身はソートされないことに注意．
	 * @param a byte型配列
	 * @param index インデックス
	 * @param mode 昇順，降順の指定
	 * 		mode == true : 降順
	 * 		mode == false : 昇順
	 */
	public static void indexSort(final byte[] a, Integer[] index, boolean mode){
		makeIndex(index, a.length);
		Arrays.sort(index, new Comparator<Integer>() {
			public int compare(Integer o1, Integer o2) {
				return Byte.valueOf(a[o1]).compareTo(Byte.valueOf(a[o2]));
			}
		});
		if(mode) reverseIndex(index);
	}
	
	/**
	 * short型配列に対してインデックスソートを行う．
	 * 引数として渡される配列の中身はソートされないことに注意．
	 * @param a short型配列
	 * @param index インデックス
	 * @param mode 昇順，降順の指定
	 * 		mode == true : 降順
	 * 		mode == false : 昇順
	 */
	public static void indexSort(final short[] a, Integer[] index, boolean mode){
		makeIndex(index, a.length);
		Arrays.sort(index, new Comparator<Integer>() {
			public int compare(Integer o1, Integer o2) {
				return Short.valueOf(a[o1]).compareTo(Short.valueOf(a[o2]));
			}
		});
		if(mode) reverseIndex(index);
	}
	
	/**
	 * int型配列に対してインデックスソートを行う．
	 * 引数として渡される配列の中身はソートされないことに注意．
	 * @param a int型配列
	 * @param index インデックス
	 * @param mode 昇順，降順の指定
	 * 		mode == true : 降順
	 * 		mode == false : 昇順
	 */
	public static void indexSort(final int[] a, Integer[] index, boolean mode){
		makeIndex(index, a.length);
		Arrays.sort(index, new Comparator<Integer>() {
			public int compare(Integer o1, Integer o2) {
				return Integer.valueOf(a[o1]).compareTo(Integer.valueOf(a[o2]));
			}
		});
		if(mode) reverseIndex(index);
	}
	
	/**
	 * long型配列に対してインデックスソートを行う．
	 * 引数として渡される配列の中身はソートされないことに注意．
	 * @param a long型配列
	 * @param index インデックス
	 * @param mode 昇順，降順の指定
	 * 		mode == true : 降順
	 * 		mode == false : 昇順
	 */
	public static void indexSort(final long[] a, Integer[] index, boolean mode){
		makeIndex(index, a.length);
		Arrays.sort(index, new Comparator<Integer>() {
			public int compare(Integer o1, Integer o2) {
				return Long.valueOf(a[o1]).compareTo(Long.valueOf(a[o2]));
			}
		});
		if(mode) reverseIndex(index);
	}
	
	/**
	 * float型配列に対してインデックスソートを行う．
	 * 引数として渡される配列の中身はソートされないことに注意．
	 * @param a float型配列
	 * @param index インデックス
	 * @param mode 昇順，降順の指定
	 * 		mode == true : 降順
	 * 		mode == false : 昇順
	 */
	public static void indexSort(final float[] a, Integer[] index, boolean mode){
		makeIndex(index, a.length);
		Arrays.sort(index, new Comparator<Integer>() {
			public int compare(Integer o1, Integer o2) {
				return Float.valueOf(a[o1]).compareTo(Float.valueOf(a[o2]));
			}
		});
		if(mode) reverseIndex(index);
	}
	
	/**
	 * double型配列に対してインデックスソートを行う．
	 * 引数として渡される配列の中身はソートされないことに注意．
	 * @param a double型配列
	 * @param index インデックス
	 * @param mode 昇順，降順の指定
	 * 		mode == true : 降順
	 * 		mode == false : 昇順
	 */
	public static void indexSort(final double[] a, Integer[] index, boolean mode){
		makeIndex(index, a.length);
		Arrays.sort(index, new Comparator<Integer>() {
			public int compare(Integer o1, Integer o2) {
				return Double.valueOf(a[o1]).compareTo(Double.valueOf(a[o2]));
			}
		});
		if(mode) reverseIndex(index);
	}
	
	/**
	 * char型配列に対してインデックスソートを行う．
	 * 引数として渡される配列の中身はソートされないことに注意．
	 * @param a char型配列
	 * @param index インデックス
	 * @param mode 昇順，降順の指定
	 * 		mode == true : 降順
	 * 		mode == false : 昇順
	 */
	public static void indexSort(final char[] a, Integer[] index, boolean mode){
		makeIndex(index, a.length);
		Arrays.sort(index, new Comparator<Integer>() {
			public int compare(Integer o1, Integer o2) {
				return Character.valueOf(a[o1]).compareTo(Character.valueOf(a[o2]));
			}
		});
		if(mode) reverseIndex(index);
	}

	/**
	 * boolean型配列に対してインデックスソートを行う．
	 * 引数として渡される配列の中身はソートされないことに注意．
	 * @param a boolean型配列
	 * @param index インデックス
	 * @param mode 昇順，降順の指定
	 * 		mode == true : 降順
	 * 		mode == false : 昇順
	 */
	public static void indexSort(final boolean[] a, Integer[] index, boolean mode){
		makeIndex(index, a.length);
		Arrays.sort(index, new Comparator<Integer>() {
			public int compare(Integer o1, Integer o2) {
				return Boolean.valueOf(a[o1]).compareTo(Boolean.valueOf(a[o2]));
			}
		});
		if(mode) reverseIndex(index);
	}
	
	/**
	 * 正順(0,1,...,n)のインデックスを作成する．
	 * @param n インデックスの長さ
	 */
	private static void makeIndex(Integer[] index, int n){
		if(index.length != n) {
			throw new RuntimeException("index.length != n");
		}
		for(int i=0; i<n; i++){
			index[i] = i;
		}
	}
	
	/**
	 * インデックスを逆順にする．
	 * @param index インデックス
	 */
	private static void reverseIndex(Integer[] index){
		int temp;
		
		for(int i=0; i<(index.length/2); i++){
			temp = index[i];
			index[i] = index[index.length - 1 - i];
			index[index.length - 1 - i] = temp;
		}
	}	
}
