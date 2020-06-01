package za.co.janspies.sorters;

import org.apache.log4j.Logger;

import za.co.janspies.GraphJPanel;
import za.co.janspies.Sorter;
import za.co.janspies.SortingFactory;
import za.co.janspies.model.DataModel;

/**
 *
 * @author jvanrooyen
 *
 */
public class BitonicSort implements Sorter {
	private static final Logger LOG = Logger.getLogger(BitonicSort.class);

	private final GraphJPanel graphJPanel;
	private final DataModel dataModel;
	int repaintCount = 0;
	private static final boolean UP = true;

	public BitonicSort(final GraphJPanel graphJPanel) {
		LOG.info("Starting up a BitonicSort...");
		this.graphJPanel = graphJPanel;
		this.dataModel = graphJPanel.getDataModel();
		this.dataModel.getStatistics().reset();
	}

	public void run() {
		this.sort();
	}

	public void sort() {
		this.sort(this.dataModel.getLength(), UP);
	}

	/**
	 *
	 * @param n
	 * @param dir
	 */
	public void sort(final int n, final boolean up) {
		this.bitonicSort(0, n, up);
	}

	/**
	 * This function first produces a bitonic sequence by recursively sorting its
	 * two halves in opposite sorting orders, and then calls bitonicMerge to make
	 * them in the same order
	 *
	 * @param low
	 * @param count
	 * @param dir
	 */
	private void bitonicSort(final int lo, final int n, final boolean dir) {
		if (n > 1) {
			final int m = n / 2;
			this.bitonicSort(lo, m, !dir);
			this.bitonicSort(lo + m, n - m, dir);
			this.bitonicMerge(lo, n, dir);
		}
	}

	/**
	 * It recursively sorts a bitonic sequence in ascending order, if dir = 1, and
	 * in descending order otherwise (means dir=0). The sequence to be sorted starts
	 * at index position low, the parameter cnt is the number of elements to be
	 * sorted.
	 *
	 * @param low
	 * @param count
	 * @param dir
	 */
	private void bitonicMerge(final int lo, final int n, final boolean dir) {
		if (n > 1) {
			final int m = this.greatestPowerOfTwoLessThan(n);
			for (int i = lo; i < ((lo + n) - m); i++) {
				this.compAndSwap(i, i + m, dir);
			}
			this.bitonicMerge(lo, m, dir);
			this.bitonicMerge(lo + m, n - m, dir);
		}
	}

	/**
	 *
	 * @param n
	 * @return
	 */
	private int greatestPowerOfTwoLessThan(final int n) {
		int k = 1;
		while ((k > 0) && (k < n)) {
			k = k << 1;
		}
		return k >>> 1;
	}

	/**
	 * The parameter dir indicates the sorting direction, ASCENDING or DESCENDING;
	 * if (a[i] > a[j]) agrees with the direction, then a[i] and a[j] are
	 * interchanged.
	 *
	 * @param i
	 * @param j
	 * @param dir
	 */
	private void compAndSwap(final int i, final int j, final boolean dir) {
		if (dir == this.dataModel.gt(i, j)) {
			this.dataModel.swap(i, j);
			this.repaint(this.repaintCount++);
		}
	}

	public void repaint(final int count) {
		this.graphJPanel.repaint();
		try {
			if ((count % 2) == 0) {
				Thread.sleep(SortingFactory.THREAD_SPEED);
			}
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static String getName() {
		return "Bitonic Sort";
	}

	public boolean stopNow() {
		return SortingFactory.RESET_PRESSED;
	}

}
