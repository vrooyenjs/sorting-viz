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
public class IterativeMergeSort implements Sorter {
	private static final Logger LOG = Logger.getLogger(IterativeMergeSort.class);

	private final GraphJPanel graphJPanel;
	private final DataModel dataModel;
	int count = 0;

	public IterativeMergeSort(final GraphJPanel graphJPanel) {
		LOG.info("Starting up a IterativeMergeSorting...");
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
		// For current size of subarrays to
		// be merged curr_size varies from
		// 1 to n/2
		int curr_size;

		// For picking starting index of
		// left subarray to be merged
		int left_start;

		// Merge subarrays in bottom up
		// manner. First merge subarrays
		// of size 1 to create sorted
		// subarrays of size 2, then merge
		// subarrays of size 2 to create
		// sorted subarrays of size 4, and
		// so on.
		for (curr_size = 1; curr_size <= (this.dataModel.getLength() - 1); curr_size = 2 * curr_size) {

			// Pick starting point of different
			// subarrays of current size
			for (left_start = 0; left_start < (this.dataModel.getLength() - 1); left_start += 2 * curr_size) {
				// Find ending point of left
				// subarray. mid+1 is starting
				// point of right
				final int mid = Math.min((left_start + curr_size) - 1, this.dataModel.getLength() - 1);

				final int right_end = Math.min((left_start + (2 * curr_size)) - 1, this.dataModel.getLength() - 1);

				// Merge Subarrays arr[left_start...mid]
				// & arr[mid+1...right_end]
				this.merge(left_start, mid, right_end);
			}
		}
	}

	/**
	 *
	 * @param left_start
	 * @param mid
	 * @param right_end
	 */
	private void merge(final int l, final int m, final int r) {
		int i, j, k;
		final int n1 = (m - l) + 1;
		final int n2 = r - m;

		/* create temp arrays */
		final int L[] = new int[n1];
		final int R[] = new int[n2];

		/*
		 * Copy data to temp arrays L[] and R[]
		 */
		for (i = 0; i < n1; i++) {
			L[i] = this.dataModel.get(l + i);
			this.repaint(this.count++);
		}
		for (j = 0; j < n2; j++) {
			R[j] = this.dataModel.get(m + 1 + j);
			this.repaint(this.count++);
		}

		/*
		 * Merge the temp arrays back into arr[l..r]
		 */
		i = 0;
		j = 0;
		k = l;
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

		/*
		 * Copy the remaining elements of L[], if there are any
		 */
		while (i < n1) {
			this.dataModel.set(k, L[i]);
			this.repaint(this.count++);
			i++;
			k++;
		}

		/*
		 * Copy the remaining elements of R[], if there are any
		 */
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
			if ((count % 5) == 0) {
				Thread.sleep(SortingFactory.THREAD_SPEED);
			}
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static String getName() {
		return "Iterative Merge Sort";
	}

	public boolean stopNow() {
		return SortingFactory.RESET_PRESSED;
	}
}
