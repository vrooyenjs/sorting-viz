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
	int count = 0;

	public BitonicSort(final GraphJPanel graphJPanel) {
		LOG.info("Starting up a BitonicSort...");
		this.graphJPanel = graphJPanel;
		this.dataModel = graphJPanel.getDataModel();
		this.dataModel.getStatistics().reset();
	}

	public void run() {
		this.sort();
	}

	/**
	 * Iterative merge sort function to sort arr[0...n-1]
	 */
	public void sort() {
		this.sort(1);
	}

	/**
	 *
	 * @param up
	 */
	void sort(final int up) {
		this.bitonicSort(0, this.dataModel.getLength(), up);
	}

	/**
	 * This function first produces a bitonic sequence by recursively sorting its
	 * two halves in opposite sorting orders, and then calls bitonicMerge to make
	 * them in the same order
	 *
	 * @param low
	 * @param cnt
	 * @param dir
	 */
	private void bitonicSort(final int low, final int cnt, final int dir) {
		if (cnt > 1) {
			final int k = cnt / 2;

			// sort in ascending order since dir here is 1
			this.bitonicSort(low, k, 1);

			// sort in descending order since dir here is 0
			this.bitonicSort(low + k, k, 0);

			// Will merge whole sequence in ascending order
			// since dir=1.
			this.bitonicMerge(low, cnt, dir);
		}
	}

	/**
	 * It recursively sorts a bitonic sequence in ascending order, if dir = 1, and
	 * in descending order otherwise (means dir=0). The sequence to be sorted starts
	 * at index position low, the parameter cnt is the number of elements to be
	 * sorted.
	 *
	 * @param low
	 * @param cnt
	 * @param dir
	 */
	private void bitonicMerge(final int low, final int cnt, final int dir) {
		if (cnt > 1) {
			final int k = cnt / 2;
			for (int i = low; i < (low + k); i++) {
				this.compAndSwap(i, i + k, dir);
			}
			this.bitonicMerge(low, k, dir);
			this.bitonicMerge(low + k, k, dir);
		}

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
	private void compAndSwap(final int i, final int j, final int dir) {
		if ((this.dataModel.gt(i, j) && (dir == 1)) || (this.dataModel.lt(i, j) && (dir == 0))) {
			// Swapping elements
			this.dataModel.swap(i, j);
			this.repaint(this.count++);
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
