package jp.onolab.boa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import jp.onolab.random2013.ICRandom;


/**
 * int型の行列演算をサポートするクラス．
 * 基本的にはTCIntMatrix型と同じメソッド名・使い方だが，int型でサポートされない行列演算に関しては削除している．
 * @author shimazu
 */
public class TCIntMatrix implements Serializable, Cloneable {
	/** シリアライズ用のid */
	private static final long serialVersionUID = 1L;

	/** 行列の要素を横一列に並べた配列 **/
	private final int[] fElements;

	/** 行数(縦の長さ) **/
	private final int fNoOfRow;

	/** 列数(横の長さ) **/
	private final int fNoOfColumn;

	/**
	 * noOfRow x noOfColumnの行列を生成する。
	 * 行列の各要素は0.0で初期化される
	 * @param noOfColumn 行列の高さ
	 * @param noOfRow 行列の幅
	 */
	public TCIntMatrix( final int noOfRow, final int noOfColumn ) {
		fElements = new int[ noOfColumn * noOfRow ];
		fNoOfRow = noOfRow;
		fNoOfColumn = noOfColumn;
	}

	/**
	 * 高さheightの縦ベクトルを生成する。
	 * 行列の各要素は0.0で初期化される
	 * @param column
	 */
	public TCIntMatrix( final int column ) {
		this( column, 1 );
	}

	/**
	 * コピーコンストラクタ
	 * @param mat コピー元の行列
	 * @return 自分自身の行列
	 */
	public TCIntMatrix ( final TCIntMatrix mat ) {
		fElements = new int[ mat.fElements.length ];
		fNoOfRow = mat.getRowDimension();
		fNoOfColumn = mat.getColumnDimension();
		copyFrom( mat );
	}

	/**
	 * 1次元配列による初期化
	 * arrayの中身をdeep copyします
	 * @param array
	 */
	public TCIntMatrix( final int[] array ) {
		this( array.length, 1 );
		System.arraycopy( array, 0, fElements, 0, array.length );
	}

	/**
	 * 2次元配列による初期化
	 * arrayの中身をdeep copyします
	 * @param array
	 */
	public TCIntMatrix( final int[][] array ) {
		this( array.length, array[0].length );
		int iStepIndex = 0;
		for ( int i = 0; i < fNoOfRow; i++ ) {
			System.arraycopy( array[i], 0, fElements, iStepIndex, fNoOfColumn );
			iStepIndex += fNoOfColumn;
		}
	}

	/**
	 * srcで指定した行列をこの行列へdeep copyします。
	 * @param src コピーする元の行列
	 * @return 自分自身の行列
	 */
	public TCIntMatrix copyFrom( final TCIntMatrix src ) {
		assert( fNoOfRow == src.fNoOfRow &&
				fNoOfColumn == src.fNoOfColumn );

		System.arraycopy( src.fElements, 0, fElements, 0, fElements.length );
		return this;
	}

	/**
	 * srcで指定した行列の転置をこの行列へディープコピーします。
	 * @param src コピーする元の行列
	 * @return 自分自身の行列
	 */
	public TCIntMatrix tcopyFrom( final TCIntMatrix src ) {
		assert( fNoOfRow == src.fNoOfColumn &&
				fNoOfColumn == src.fNoOfRow );

		for ( int i = 0; i < fNoOfColumn; i++ ) {
			for ( int j = 0; j < fNoOfRow; j++ ) {
				setValue( j, i, src.getValue( i, j ) );
			}
		}
		return this;
	}

	/**
	 * thisを転置した状態でcloneします。
	 * @param src
	 * @return
	 */
	public TCIntMatrix tclone() {
		return new TCIntMatrix( fNoOfColumn, fNoOfRow ).tcopyFrom( this );
	}

	/**
	 * srcの部分行列 [ srcFromColumn, stcToColumn, srcFromRow, srcToRow ] を、
	 * この行列の位置( srcToRow, srcToColumn )を左上としてコピーする。
	 * ここで、部分行列の先端、終端のindexは部分行列に含まれる。
	 * @param src
	 * @param srcFromRow srcの行列のコピーする領域の上端
	 * @param srcToRow src行列のコピーする領域の下端
	 * @param srcFromColumn srcの行列のコピーする領域の左端
	 * @param srcToColumn srcの行列のコピーする領域の右端
	 * @param dstFromRow 自分自身の行列のコピー先の左端
	 * @param dstFromColumn 自分自身の行列のコピー先の上端
	 * @return this
	 */
	public TCIntMatrix copySubmatrixFrom( final TCIntMatrix src, final int srcFromRow, final int srcToRow,
			final int srcFromColumn, final int srcToColumn, final int dstFromRow, final int dstFromColumn ) {

		final int submatrixColumnDimension = srcToColumn - srcFromColumn + 1;
		final int submatrixRowDimension = srcToRow - srcFromRow + 1;

		assert( 0 <= dstFromRow && dstFromRow + submatrixRowDimension <= fNoOfRow &&             // 部分行列の縦が呼び出し元の行列からはみ出ないか
				0 <= dstFromColumn && dstFromColumn + submatrixColumnDimension <= fNoOfColumn && // 部分行列の横が呼び出し元の行列からはみ出ないか
				0 <= srcFromRow && srcFromRow <= srcToRow && srcToRow < src.fNoOfRow &&          // 部分行列の縦がsrcの行列からはみ出ないか
				0 <= srcFromColumn && srcFromColumn <= srcToColumn && srcToColumn < src.fNoOfColumn ); // 部分行列の横がsrcの行列からはみ出ないか

		for ( int i = 0; i < submatrixRowDimension; i++ ) {
			for ( int j = 0; j < submatrixColumnDimension; j++ ) {
				setValue( i + dstFromRow, j + dstFromColumn, src.getValue( i + srcFromRow, j + srcFromColumn ) );
			}
		}
		return this;
	}

	/**
	 * thisの列数を返します( 列数 = 横の長さ )
	 * @return
	 */
	public int getColumnDimension() {
		return fNoOfColumn;
	}

	/**
	 * thisの行数を返します( 行数 = 縦の長さ )
	 * @return
	 */
	public int getRowDimension() {
		return fNoOfRow;
	}

	/**
	 * 行列そのものの次元数（＝要素数）を返します
	 * @return
	 */
	public int getDimension() {
		return fElements.length;
	}

	/**
	 * r行c列目の要素を返します
	 * @param r
	 * @param c
	 * @return
	 */
	public int getValue( final int r, final int c ) {
		assert( 0 <= r && r < fNoOfRow &&
				0 <= c && c < fNoOfColumn );

		return fElements[ r * fNoOfColumn + c ];
	}

	/**
	 * getValue( r / getRowDimension(), r % getRowDimension )と同じです
	 * @param r
	 * @return
	 */
	public int getValue( final int r ) {
		assert( 0 <= r && r < fElements.length );
		return fElements[ r ];
	}

	/**
	 * 1 x 1 の行列の場合のみ、getValue( 0, 0 )と同じです
	 * @return
	 */
	public int getValue() {
		assert( fElements.length == 1 );
		return fElements[ 0 ];
	}

	/**
	 * r行c列目の要素をvalにします
	 * @param r
	 * @param c
	 * @param val
	 * @return
	 */
	public TCIntMatrix setValue( final int r, final int c, final int val ){
		assert( 0 <= r && r < fNoOfRow &&
				0 <= c && c < fNoOfColumn );

		fElements[ r * fNoOfColumn + c ] = val;
		return this;
	}

	/**
	 * r行目の要素をvalにします
	 * @param r
	 * @param val
	 * @return
	 */
	public TCIntMatrix setValue( final int r, final int val ) {
		assert( 0 <= r && r < fElements.length );
		fElements[ r ] = val;
		return this;
	}

	/**
	 * 1 x 1 の行列の場合のみ、setValue( 0, 0 )と同じです
	 * @return
	 */
	public TCIntMatrix setValue( final int val ) {
		assert( fElements.length == 1 );
		fElements[ 0 ] = val;
		return this;
	}

	/**
	 * this = this + mを行う
	 * this, mの行列サイズが等しい必要がある
	 * @param m
	 * @return this
	 */
	public TCIntMatrix add( final TCIntMatrix m ) {
		assert( fNoOfRow == m.fNoOfRow &&
				fNoOfColumn == m.fNoOfColumn );
		for ( int i = 0; i < fElements.length; i++ ) {
			fElements[ i ] += m.fElements[ i ];
		}
		return this;
	}

	/**
	 * this = m1 + m2を行う．
	 * this, m1, m2の行列サイズが等しい必要がある．
	 * @param m1
	 * @param m2
	 * @return
	 */
	public TCIntMatrix add(final TCIntMatrix m1, final TCIntMatrix m2) {
		assert(fNoOfRow == m1.fNoOfRow && fNoOfRow == m2.fNoOfRow
				    && fNoOfColumn == m1.fNoOfColumn && fNoOfColumn == m2.fNoOfColumn);
		for ( int i = 0; i < fElements.length; i++ ) {
			fElements[i] = m1.fElements[i] + m2.fElements[i];
		}
		return this;
	}

	/**
	 * thisの各要素にvalを足す
	 * @param val
	 * @return this
	 */
	public TCIntMatrix add( final int val ) {
		for ( int i = 0; i < fElements.length; i++ ) {
			fElements[ i ] += val;
		}
		return this;
	}

	/**
	 * mの各要素にvalを足したものをthisに代入する．
	 * @param m
	 * @param val
	 * @return
	 */
	public TCIntMatrix add(final TCIntMatrix m, final int val) {
		assert(val != 0.0);
		assert(fNoOfRow == m.fNoOfRow && fNoOfColumn == m.fNoOfColumn);
		for ( int i = 0; i < fElements.length; i++ ) {
			fElements[i] = m.fElements[i] + val;
		}
		return this;
	}

	/**
	 * this = this - m を行う
	 * this, mの行列サイズ等しい必要がある
	 * @param m
	 * @return
	 */
	public TCIntMatrix sub( final TCIntMatrix m ) {
		assert( fNoOfRow == m.fNoOfRow &&
				fNoOfColumn == m.fNoOfColumn );

		for ( int i = 0; i < fElements.length; i++ ) {
			fElements[ i ] -= m.fElements[ i ];
		}
		return this;
	}

	/**
	 * this = m1 - m2を行う．
	 * this, m1, m2の行列サイズが等しい必要がある．
	 * @param m1
	 * @param m2
	 * @return
	 */
	public TCIntMatrix sub(final TCIntMatrix m1, final TCIntMatrix m2) {
		assert(fNoOfRow == m1.fNoOfRow && fNoOfRow == m2.fNoOfRow
				    && fNoOfColumn == m1.fNoOfColumn && fNoOfColumn == m2.fNoOfColumn);
		for ( int i = 0; i < fElements.length; i++ ) {
			fElements[i] = m1.fElements[i] - m2.fElements[i];
		}
		return this;
	}

	/**
	 * thisの各要素からvalを引く
	 * @param val
	 * @return this
	 */
	public TCIntMatrix sub( final int val ) {
		for ( int i = 0; i < fElements.length; i++ ) {
			fElements[ i ] -= val;
		}
		return this;
	}

	/**
	 * mの各要素からvalをひいたものをthisに代入する．
	 * @param m
	 * @param val
	 * @return
	 */
	public TCIntMatrix sub(final TCIntMatrix m, final int val) {
		assert(val != 0.0);
		assert(fNoOfRow == m.fNoOfRow && fNoOfColumn == m.fNoOfColumn);
		for ( int i = 0; i < fElements.length; i++ ) {
			fElements[i] = m.fElements[i] - val;
		}
		return this;
	}

	/**
	 * this = m2 * m3 を行い、thisを返す
	 * m2 * m3の結果とthisの大きさが同じの必要がある
	 * ３パターンで分類しています
	 * @param m
	 * @param m2
	 * @return this
	 */
	public TCIntMatrix times( final TCIntMatrix m, final TCIntMatrix m2 ) {
		assert( m.fNoOfColumn == m2.fNoOfRow &&
				fNoOfRow == m.fNoOfRow &&
				fNoOfColumn == m2.fNoOfColumn );

		if ( m2.fNoOfColumn > 1 ) {
			if( fElements.length > 600 ) { // 行列サイズが大きければキャッシュを重視してikjループした方が速い
				Arrays.fill( fElements, 0 );
				int mIStepIndex = 0;
				for ( int i = 0; i < fElements.length; i += fNoOfColumn ) {
					int m2KStepIndex = 0;
					for ( int k = 0; k < m.fNoOfColumn; k++ ) {
						for ( int j = 0; j < fNoOfColumn; j++ ) {
							fElements[ i + j ] += m.fElements[ mIStepIndex + k ] * m2.fElements[ m2KStepIndex + j ];
						}
						m2KStepIndex += fNoOfColumn;
					}
					mIStepIndex += m.fNoOfColumn;
				}
			} else { // 行列サイズが小さければvalで書き込む値をキャッシュした方が速い
				int mIStepIndex = 0;
				for ( int i = 0; i < fElements.length; i += fNoOfColumn ) {
					for ( int j = 0; j < fNoOfColumn; j++ ) {
						int m2KStepIndex = 0;
						int val = 0;
						for ( int k = 0; k < m.fNoOfColumn; k++ ) {
							val += m.fElements[ mIStepIndex + k ] * m2.fElements[ m2KStepIndex + j ];
							m2KStepIndex += fNoOfColumn;
						}
						fElements[ i + j ] = val;
					}
					mIStepIndex += m.fNoOfColumn;
				}
			}
		} else { // 行列 x ベクトルの場合にはforループが２重になるので速い
			Arrays.fill( fElements, 0 );
			int iStepIndex = 0;
			for ( int i = 0; i < fNoOfRow; i++ ) {
				for ( int j = 0; j < m.fNoOfColumn; j++ ) {
					fElements[ i ] += m.fElements[ iStepIndex + j ] * m2.fElements[ j ];
				}
				iStepIndex += m.fNoOfColumn;
			}
		}
		return this;
	}

	/**
	 * this = this * mを行う。結果は自身の行列に上書きされる
	 * this, nが共に大きさの同じ正方行列の必要がある
	 * そうでない場合は、m1.times( m2, m3 )を推奨
	 * @param m
	 * @return
	 */
	public TCIntMatrix times( final TCIntMatrix m ) {

		assert( fNoOfRow == fNoOfColumn &&
				fNoOfColumn == m.fNoOfRow &&
				m.fNoOfRow == m.fNoOfColumn );

		final int size = fNoOfRow;
		final int[] ithRow = new int[ size ];

		for ( int i = 0; i < fElements.length; i += size ) {
			System.arraycopy( fElements, i, ithRow, 0, size );
			Arrays.fill( fElements, i, i + size, 0 );
			int mKIndex = 0;
			for ( int k = 0; k < size; k++ ) {
				for ( int j = 0; j < size; j++ ) {
					fElements[ i + j ] += ithRow[ k ] * m.fElements[ mKIndex + j ];
				}
				mKIndex += size;
			}
		}
		return this;
	}

	/**
	 * thisの全ての要素にvalをかける
	 * @param val 各要素にかける値
	 * @return this
	 */
	public TCIntMatrix times(final int val) {
		for ( int i = 0; i < fElements.length; i++ ) {
			fElements[ i ] *= val;
		}
		return this;
	}

	/**
	 * mの各要素にvalをかけたものをthisに代入する．
	 * @param m
	 * @param val
	 * @return
	 */
	public TCIntMatrix times(final TCIntMatrix m, final int val) {
		assert(val != 0.0);
		assert(fNoOfRow == m.fNoOfRow && fNoOfColumn == m.fNoOfColumn);
		for ( int i = 0; i < fElements.length; i++ ) {
			fElements[i] = m.fElements[i] * val;
		}
		return this;
	}

	/**
	 * this = m * thisの結果をthisに書き込む
	 * this, mが大きさの同じ正方行列 n x n でなければ使えない
	 * 大きさnのint[]のnewが起こるので、少し遅いかもしれません
	 * 避けたければ、m1.times( m2, m3 )を推奨
	 * @param m 左からかける行列
	 * @return this
	 */
	public TCIntMatrix timesLeft( final TCIntMatrix m ) {
		assert( fNoOfRow == fNoOfColumn &&
				fNoOfColumn == m.fNoOfRow &&
				m.fNoOfRow == m.fNoOfColumn );

		final int size = fNoOfRow;
		final int[] jthColumn = new int[ size ];

		for ( int j = 0; j < size; j++ ) {
			int iStepIndex = 0;
			for ( int i = 0; i < size; i++ ) {
				jthColumn[ i ] = fElements[ iStepIndex + j ];
				fElements[ iStepIndex + j ] = 0;
				iStepIndex += size;
			}
			for ( int i = 0; i < fElements.length; i += size ) {
				for ( int k = 0; k < size; k++ ) {
					fElements[ i + j ] += m.fElements[ i + k ] * jthColumn[ k ];
				}
			}
		}
		return this;
	}

	/**
	 * this = this .* m (要素ごとの乗算)
	 * @param m
	 * @return
	 */
	public TCIntMatrix timesElement( final TCIntMatrix m ) {
		assert( fNoOfRow == m.fNoOfRow &&
				fNoOfColumn == m.fNoOfColumn );

		for ( int i = 0; i < fElements.length; i++ ) {
			fElements[ i ] *= m.fElements[ i ];
		}

		return this;
	}

	/**
	 * this = m1 .* m2 (要素ごとの乗算)
	 * @param m1
	 * @param m2
	 * @return
	 */
	public TCIntMatrix timesElement(final TCIntMatrix m1, final TCIntMatrix m2) {
		assert(fNoOfRow == m1.fNoOfRow && fNoOfRow == m2.fNoOfRow
				    && fNoOfColumn == m1.fNoOfColumn && fNoOfColumn == m2.fNoOfColumn);
		for ( int i = 0; i < fElements.length; i++ ) {
			fElements[i] = m1.fElements[i] * m2.fElements[i];
		}
		return this;
	}

	/**
	 * thisの各要素をvalで割る．小数値になった場合は切り捨てます．
	 * @param val
	 * @return
	 */
	public TCIntMatrix div(final double val) {
		assert( val != 0.0 );
		for ( int i = 0; i < fElements.length; i++ ) {
			fElements[ i ] = (int)(fElements[ i ] / val);
		}
		return this;
	}

	/**
	 * mの各要素をvalで割ったものをthisに代入する．小数値になった場合は切り捨てます．
	 * @param m
	 * @param val
	 * @return
	 */
	public TCIntMatrix div(final TCIntMatrix m, final double val) {
		assert(val != 0.0);
		assert(fNoOfRow == m.fNoOfRow && fNoOfColumn == m.fNoOfColumn);
		for ( int i = 0; i < fElements.length; i++ ) {
			fElements[i] = (int)(m.fElements[i] / val);
		}
		return this;
	}

	/**
	 * this = this ./ m (要素ごとの除算)．小数値になった場合は切り捨てます．
	 * @param m
	 * @return
	 */
	public TCIntMatrix divElement(TCIntMatrix m) {
		assert( fNoOfRow == m.fNoOfRow &&
				fNoOfColumn == m.fNoOfColumn );

		for ( int i = 0; i < fElements.length; i++ ) {
			fElements[i] = (int)(fElements[ i ] / m.fElements[ i ]);
		}
		return this;
	}

	/**
	 * this = m1 ./ m2 (要素ごとの除算)．小数値になった場合は切り捨てます．
	 * @param m1
	 * @param m2
	 * @return
	 */
	public TCIntMatrix divElement(final TCIntMatrix m1, final TCIntMatrix m2) {
		assert(fNoOfRow == m1.fNoOfRow && fNoOfRow == m2.fNoOfRow
				    && fNoOfColumn == m1.fNoOfColumn && fNoOfColumn == m2.fNoOfColumn);
		for ( int i = 0; i < fElements.length; i++ ) {
			fElements[i] = (int)(m1.fElements[i] / m2.fElements[i]);
		}
		return this;
	}

	public int trace() {
		final int length = Math.min( fNoOfColumn, fNoOfRow );
		int sum = 0;
		int iStepIndex = 0;
		for ( int i = 0; i < length; i++ ) {
			sum += fElements[ iStepIndex + i ];
			iStepIndex += fNoOfColumn;
		}
		return sum;
	}

	public double normF() {
		double sum = 0.0;
		for ( final double val : fElements ) {
			sum += val * val;
		}
		return Math.sqrt( sum );
	}

	/**
	 * thisのL2ノルムを計算する
	 * @return L2ノルム
	 */
	public double normL2() {
		assert( getColumnDimension() == 1 );

		double sum = 0.0;
		for ( final double val : fElements ) {
			sum += val * val;
		}
		return Math.sqrt( sum );
	}

	/**
	 * thisとvのユークリッド距離を計算する
	 * @param vector 距離計算するベクトル
	 * @return ユークリッド距離
	 */
	public double distanceL2( final TCIntMatrix vector ) {
		assert ( fNoOfColumn == 1 && vector.fNoOfColumn == 1 );

		double sum = 0.0;
		for ( int i = 0; i < fElements.length; i++ ) {
			final double diff = fElements[ i ] - vector.fElements[ i ];
			sum += diff * diff;
		}
		return Math.sqrt( sum );
	}

    /**
     * *thisとvectorのハミング距離を計算する
     * @param vector 距離計算するベクトル
     * @return ハミング距離
     */
    public int distanceHamming( final TCIntMatrix vector) {
		assert ( fNoOfColumn == 1 && vector.fNoOfColumn == 1 );
        int sum = 0;
        for ( int i = 0; i < fElements.length; i++ ) {
            if ( fElements[ i ] != vector.fElements[ i ] ) {
                sum++;
            }
        }
        return sum;
    }

	/**
	 * vectorとの内積を計算する
	 * this, vectorともに同じ次元数の縦ベクトルの必要がある
	 * @param vector
	 * @return <vector, this>
	 */
	public int innerProduct( final TCIntMatrix vector ) {
		assert( fNoOfColumn == 1 && vector.fNoOfColumn == 1 &&
				fNoOfRow == vector.fNoOfRow );
		int sum = 0;
		for ( int i = 0; i < fElements.length; i++ ) {
			sum += fElements[i] * vector.fElements[i];
		}
		return sum;
	}


	/**
	 * 絶対値を返す
	 * @return
	 */
	public TCIntMatrix abs() {
		for ( int i = 0; i < fElements.length; i++ ) {
			if (fElements[i] < 0) {
				fElements[i] = -fElements[i];
			}
		}
		return this;
	}

	 /**
	 * 現在の行列を全てvalで上書きする
	 * @param val
	 * @return this
	 */
	public TCIntMatrix fill( final int val ) {
		Arrays.fill( fElements, val );
		return this;
	}

	/**
	 * 現在の行列を、[min, max]内の一様乱数で初期化する
	 * @param random 乱数生成器
	 * @return this
	 */
	public TCIntMatrix rand( final ICRandom random, int min, int max ) {
		for ( int i = 0; i < getDimension(); i++ ) {
			fElements[ i ] = random.nextInt(min, max);
		}
		return this;
	}

	/**
	 * 現在の行列を単位行列で置き換える
	 * @return this
	 */
	public TCIntMatrix eye() {
		Arrays.fill( fElements, 0 );
		final int size = Math.min( fNoOfRow, fNoOfColumn );
		for ( int i = 0; i < size; i++ ) {
			fElements[ i * fNoOfColumn + i ] = 1;
		}
		return this;
	}

	/**
	 * 二つの行列の各要素を比較し，大きい方の要素で構成された行列返す．
	 *
	 * @param b matrix
	 * @return this
	 */
	public TCIntMatrix max( final TCIntMatrix m ) {
		assert( fNoOfRow == m.fNoOfRow &&
				fNoOfColumn == m.fNoOfColumn );

		for ( int i = 0; i < fElements.length; i++ ) {
			if ( fElements[ i ] < m.fElements[ i ] ) {
				fElements[ i ] = m.fElements[ i ];
			}
		}
		return this;
	}

	/**
	 * 行列の各要素a_(i,j)をmax(a_(i,j), d)で置き換える．
	 *
	 * @param d 比較対象
	 * @return this
	 */
	public TCIntMatrix max( final int val ) {
		for ( int i = 0; i < fElements.length; i++ ) {
			if( fElements[ i ] < val ) {
				fElements[ i ] = val;
			}
		}
		return this;
	}

	/**
	 * m-by-n行列の最大値とそれに対応するインデックスを発見する．
	 * 最大値に対応するインデックスが複数ある場合，それらのインデックスを全て取り出す．
	 *
	 * @param indexes 最大値の『インデックス』を格納するリスト.
	 * ただし，m-by-n行列における(i,j)要素の『インデックス』は，i*n + jで表現されることに注意．
	 *
	 * @return max 最大値
	 */
	public int max( final ArrayList<Integer> indices ){
		indices.clear();
		double max = Double.NEGATIVE_INFINITY;

		for ( int i = 0; i < fElements.length; i++ ) {
			if ( max < fElements[ i ]  ) {
				indices.clear();
				indices.add( i );
				max = fElements[ i ];
			} else if( max == fElements[ i ] ){
				indices.add( i );
			}
		}
		return (int)max;
	}

	/**
	 * 二つの行列の各要素を比較し，小さい方の要素で構成された行列返す．
	 *
	 * @param b matrix
	 * @return this
	 */
	public TCIntMatrix min( final TCIntMatrix m ) {
		assert( fNoOfRow == m.fNoOfRow &&
				fNoOfColumn == m.fNoOfColumn );

		for ( int i = 0; i < fElements.length; i++ ) {
			if ( fElements[ i ] > m.fElements[ i ] ) {
				fElements[ i ] = m.fElements[ i ];
			}
		}
		return this;
	}

	/**
	 * 行列の各要素a_(i,j)をmin(a_(i,j), d)で置き換える．
	 *
	 * @param d 比較対象
	 * @return this
	 */
	public TCIntMatrix min( final int val ) {
		for ( int i = 0; i < fElements.length; i++ ) {
			if( val < fElements[ i ] ){
				fElements[ i ] = val;
			}
		}
		return this;
	}

	/**
	 * m-by-n行列の最小値とそれに対応するインデックスを発見する．
	 * 最小値に対応するインデックスが複数ある場合，それらのインデックスを全て取り出す．
	 *
	 * @param indexes 最小値の『インデックス』を格納するリスト.
	 * ただし，m-by-n行列における(i,j)要素の『インデックス』は，i*n + jで表現されることに注意．
	 *
	 * @return min 最小値.
	 * @author fnob
	 */
	public int min( final ArrayList<Integer> indices ){

		indices.clear();
		double min = Double.MAX_VALUE;
		for ( int i = 0; i < fElements.length; i++ ) {
			if ( fElements[ i ] < min ) {
				indices.clear();
				indices.add( i );
				min = fElements[ i ];
			} else if ( fElements[ i ] == min ) {
				indices.add( i );
			}
		}
		return (int)min;
	}

	@Override
	public TCIntMatrix clone() {
		return new TCIntMatrix( this );
	}

	/**
	 * 自身の src_fromRow から src_toRow 行，および src_fromColumn から src_toColumn 列に対応する部分行列のクローンを作成して返す．
	 * ただし、全ての端は区間内に含まれる
	 * @param srcFromRow first row index
	 * @param srcToRow last row index
	 * @param srcFromColumn first column index
	 * @param srcToColumn last column index
	 * @return 部分行列のクローン
	 */
	public TCIntMatrix cloneSubmatrix( final int srcFromRow, final int srcToRow,
			final int srcFromColumn, final int srcToColumn ) {

		final int subRowDimension = srcToRow - srcFromRow + 1;
		final int subColumnDimension = srcToColumn - srcFromColumn + 1;
		final TCIntMatrix result = new TCIntMatrix( subColumnDimension, subRowDimension );
		result.copySubmatrixFrom( this, srcFromRow, srcToRow, srcFromColumn, srcToColumn, 0, 0 );
		return result;
	}

	/**
	 * 指定した行の行ベクトルのクローンを返す．
	 * @param rowIndex 行
	 * @return 行ベクトル
	 */
	public TCIntMatrix cloneRowVector( final int rowIndex ) {
		final TCIntMatrix result = new TCIntMatrix( 1, fNoOfColumn );
		final int columnIndex = rowIndex * fNoOfColumn;
		System.arraycopy( fElements, columnIndex, result.fElements, 0, fNoOfColumn );
		return result;
	}

	/**
	 * 指定した列の列ベクトルのクローンを返す．
	 * @param columnIndex 列
	 * @return 列ベクトル
	 */
	public TCIntMatrix cloneColumnVector( final int columnIndex ) {
		final TCIntMatrix result = new TCIntMatrix( fNoOfRow );
		int iStepIndex = 0;
		for ( int i = 0; i < fNoOfRow; ++i ) {
			result.setValue( i, fElements[ iStepIndex + columnIndex ] );
			iStepIndex += fNoOfColumn;
		}
		return result;
	}

	/**
	 * 行列の要素にNaNを含んでいないか調べる．
	 * @return true:含んでいる，false:含んでいない
	 */
	public boolean isNan() {
		for ( final int val : fElements ) {
			if ( Double.isNaN( val ) ) {
				return true;
			}
		}
		return false;
	}

	public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append( "[" );
		for ( int i = 0; i < fNoOfRow; i++ ) {
			sb.append( "[" );
			sb.append( getValue( i, 0 ) );
			for ( int j = 1; j < fNoOfColumn; j++ ) {
				sb.append( ", " + getValue( i, j ) );
			}
			if ( i == fNoOfRow - 1 ) {
				sb.append( "]" );
			} else {
				sb.append( "], \n" );
			}
		}
		sb.append( "]\n" );

		return sb.toString();
	}

	/**
	 * 指定された行に行列をコピーする．
	 * @param m コピーする行列．列数がコピー先の行列（この行列）と同じである必要がある．
	 * @param row コピーする行．(row+bの行数)がコピー先の行列（この行列）の行数内に収まっている必要がある．
	 * @return コピーされた後のこの行列
	 */
	public TCIntMatrix copyAtRow( final TCIntMatrix m, final int row ) {
		assert( fNoOfColumn == m.fNoOfColumn &&
				0 <= row && row + m.fNoOfRow <= fNoOfRow );

		final int additionalIndex = row * fNoOfColumn;
		for ( int i = 0; i < m.fElements.length; i += fNoOfColumn ) {
			for ( int j = 0; j < m.fNoOfColumn; ++j ) {
				fElements[ i + additionalIndex + j ] = m.fElements[ i + j ];
			}
		}
		return this;
	}

	/**
	 * 指定された列に行列をコピーする．
	 * @param m コピーする行列．行数がコピー先の行列（この行列）と同じである必要がある．
	 * @param column コピーする列．(column+bの列数)がコピー先の行列（この行列）の列数内に収まっている必要がある．
	 * @return コピーされた後のこの行列
	 */
	public TCIntMatrix copyAtColumn( final TCIntMatrix m, final int column ) {
		assert( fNoOfRow == m.fNoOfRow &&
				0 <= column && column + m.fNoOfColumn <= fNoOfColumn );

		int mIStepIndex = 0;
		for ( int i = 0; i < fElements.length; i += fNoOfColumn ) {
			for ( int j = 0; j < m.fNoOfColumn; j++ ) {
				fElements[ i + j + column ] = m.fElements[ mIStepIndex + j ];
			}
			mIStepIndex += m.fNoOfColumn;
		}
		return this;
	}

	/**
	 * 非対称行列を対称行列へ強制する．
	 * @param 非対称行列
	 * @return 対称行列
	 */
	public TCIntMatrix enforceSymmetry(){
		assert( fNoOfColumn == fNoOfRow );

		int iStepIndex = 0;
		for ( int i = 0; i < fNoOfRow; i++ ) {
			int jStepIndex = 0;
			for ( int j = 0; j < i; j++ ) {
				fElements[ jStepIndex + i ] = fElements[ iStepIndex + j ];
				jStepIndex += fNoOfColumn;
			}
			iStepIndex += fNoOfColumn;
		}
		return this;
	}

	@Override
	public boolean equals( final Object o ) {
		if ( o == this ) {
			return true;
		}
		if ( !( o instanceof TCIntMatrix ) ) {
			return false;
		}
		final TCIntMatrix o_ = ( TCIntMatrix ) o;
		return	getColumnDimension() == o_.getColumnDimension() &&
				getRowDimension() == o_.getRowDimension() &&
				Arrays.equals( fElements, o_.fElements );
	}

	@Override
	public int hashCode() {
		final int multiplier = 37;
		int result = 17;
		for ( int i = 0; i < getRowDimension(); i++ ) {
			for ( int j = 0; j < getColumnDimension(); j++ ) {
				final long l = Double.doubleToLongBits( getValue( i, j ) );
				result = multiplier * result + (int) (l ^ (l >>> 32));
			}
		}
		return result;
	}
}
