package za.co.janspies.sorters;

import org.apache.log4j.Logger;

import za.co.janspies.GraphJPanel;
import za.co.janspies.Sorter;
import za.co.janspies.SortingFactory;
import za.co.janspies.model.DataModel;

/**
 * Like QuickSort, Merge Sort is a Divide and Conquer algorithm. It divides
 * input array in two halves, calls itself for the two halves and then merges
 * the two sorted halves
 *
 * @author jvanrooyen
 *
 */
public class MergeSort implements Sorter {
	private static final Logger LOG = Logger.getLogger(MergeSort.class);

	private final GraphJPanel graphJPanel;
	private final DataModel dataModel;
	int count = 0;

	public MergeSort(final GraphJPanel graphJPanel) {
		LOG.info("Starting up a MergeSort...");
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
		this.sort(0, this.dataModel.getLength() - 1);
	}

	/**
	 *
	 * @param left
	 * @param right
	 */
	void sort(final int left, final int right) {
		if (left < right) {
			// Find the middle point
			final int m = (left + right) / 2;

			// Sort first and second halves
			this.sort(left, m);
			this.sort(m + 1, right);

			// Merge the sorted halves
			this.merge(left, m, right);
		}
	}

	/**
	 *
	 * @param left
	 * @param m
	 * @param right
	 */
	private void merge(final int left, final int m, final int right) {
		// Find sizes of two subarrays to be merged
		final int n1 = (m - left) + 1;
		final int n2 = right - m;

		/* Create temp arrays */
		final int L[] = new int[n1];
		final int R[] = new int[n2];

		/* Copy data to temp arrays */
		for (int i = 0; i < n1; ++i) {
			L[i] = this.dataModel.get(left + i);
		}
		for (int j = 0; j < n2; ++j) {
			R[j] = this.dataModel.get(m + 1 + j);
		}

		/* Merge the temp arrays */

		// Initial indexes of first and second subarrays
		int i = 0, j = 0;

		// Initial index of merged subarry array
		int k = left;
		while ((i < n1) && (j < n2)) {
			if (L[i] <= R[j]) {
				this.dataModel.set(k, L[i]);
				this.repaint(this.count++);
				i++;
			} else {
				this.dataModel.set(k, R[j]);
				this.repaint(this.count++);
				j++;
			}
			k++;
		}

		/* Copy remaining elements of L[] if any */
		while (i < n1) {
			this.dataModel.set(k, L[i]);
			this.repaint(this.count++);
			i++;
			k++;
		}

		/* Copy remaining elements of R[] if any */
		while (j < n2) {
			this.dataModel.set(k, R[j]);
			this.repaint(this.count++);
			j++;
			k++;
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
		return "Merge Sort";
	}

	public boolean stopNow() {
		return SortingFactory.RESET_PRESSED;
	}
}
