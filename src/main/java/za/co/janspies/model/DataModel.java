package za.co.janspies.model;

import java.util.concurrent.atomic.AtomicLong;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author jvanrooyen
 *
 */
@Getter
@Setter
public class DataModel {

	private int[] data;
	private int length;
	private Statistics statistics;

	public DataModel(final int length) {
		this.statistics = new Statistics();
		this.setLength(length);
		this.init();
	}

	/**
	 *
	 */
	private void init() {
		this.statistics.setGets(new AtomicLong(0l));
		this.statistics.setSets(new AtomicLong(0l));
		this.statistics.setSwaps(new AtomicLong(0l));
		this.data = new int[this.getLength()];
		for (int i = 0; i < this.getLength(); i++) {
			this.data[i] = i;
		}
	}

	/**
	 *
	 * @param fromIndex
	 * @param toIndex
	 */
	public void swap(final int fromIndex, final int toIndex) {
		this.getStatistics().getSwaps().getAndIncrement();
		final int tmp = this.get(fromIndex);
		this.set(fromIndex, this.get(toIndex));
		this.set(toIndex, tmp);
	}

	/**
	 *
	 * @param index1
	 * @param index2
	 * @return
	 */
	public boolean lt(final int index1, final int index2) {
		return (this.get(index1) < this.get(index2));
	}

	/**
	 *
	 * @param index1
	 * @param index2
	 * @return
	 */
	public boolean ge(final int index1, final int index2) {
		return (this.get(index1) >= this.get(index2));
	}

	/**
	 *
	 * @param index1
	 * @param index2
	 * @return
	 */
	public boolean gt(final int index1, final int index2) {
		return (this.get(index1) > this.get(index2));
	}

	/**
	 *
	 * @param index1
	 * @param index2
	 * @return
	 */
	public boolean le(final int index1, final int index2) {
		return (this.get(index1) <= this.get(index2));
	}

	/**
	 *
	 * @param i
	 * @param x
	 * @return
	 */
	public int binarySearch(final int fromIndex, final int toIndex, final int key) {

		int low = fromIndex;
		int high = toIndex - 1;
		while (low <= high) {
			final int mid = (low + high) >>> 1;
			final long midVal = this.get(mid);
			if (midVal < key) {
				low = mid + 1;
			} else if (midVal > key) {
				high = mid - 1;
			} else {
				return mid; // key found
			}
		}
		return -(low + 1); // key not found.
	}

	/**
	 *
	 * @return
	 */
	public int getMax() {
		int mx = this.data[0];
		for (int i = 1; i < this.getLength(); i++) {
			if (this.data[i] > mx) {
				mx = this.data[i];
			}
		}
		return mx;
	}

	/**
	 *
	 * @param srcPos
	 * @param destPos
	 * @param length
	 */
	public void arrayCopy(final int srcPos, final int destPos, final int length) {
		System.arraycopy(this.data, srcPos, this.data, destPos, length);
	}

	/**
	 *
	 * @param index
	 * @return
	 */
	public synchronized int get(final int index) {
		this.getStatistics().getGets().getAndIncrement();
		return this.data[index];
	}

	/**
	 *
	 * @param index
	 * @param value
	 */
	public synchronized void set(final int index, final int value) {
		this.getStatistics().getSets().getAndIncrement();
		this.data[index] = value;
	}
}
