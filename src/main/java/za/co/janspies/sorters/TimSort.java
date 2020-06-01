package za.co.janspies.sorters;

import org.apache.log4j.Logger;

import za.co.janspies.GraphJPanel;
import za.co.janspies.Sorter;
import za.co.janspies.SortingFactory;
import za.co.janspies.model.DataModel;

public class TimSort implements Sorter {
	private static final Logger LOG = Logger.getLogger(TimSort.class);

	private final GraphJPanel graphJPanel;
	private final DataModel dataModel;
	int count = 0;
	private static final int RUN = 32;

	public TimSort(final GraphJPanel graphJPanel) {
		LOG.info("Starting up a TimSort...");
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
		final int n = this.dataModel.getLength();
		LOG.info("Starting insertion sorting...");
		// Sort individual subarrays of size RUN
		for (int i = 0; i < this.dataModel.getLength(); i += RUN) {
			this.insertionSort(i, Math.min((i + 31), (n - 1)));
		}

		LOG.info("Starting merge sorting...");
		// start merging from size RUN (or 32). It will merge
		// to form size 64, then 128, 256 and so on ....
		for (int size = RUN; size < n; size = 2 * size) {

			// pick starting point of left sub array. We
			// are going to merge arr[left..left+size-1]
			// and arr[left+size, left+2*size-1]
			// After every merge, we increase left by 2*size
			for (int left = 0; left < n; left += 2 * size) {

				// find ending point of left sub array
				// mid+1 is starting point of right sub array
				final int mid = (left + size) - 1;
				final int right = Math.min(((left + (2 * size)) - 1), (n - 1));

				// merge sub array arr[left.....mid] &
				// arr[mid+1....right]
				this.merge(left, mid, right);
			}
		}

	}

	// this function sorts array from left index to
	// to right index which is of size atmost RUN
	public void insertionSort(final int left, final int right) {
		for (int i = left + 1; i <= right; i++) {
			final int temp = this.dataModel.get(i);
			int j = i - 1;
			while ((j >= left) && (this.dataModel.get(j) > temp)) {
				this.dataModel.set(j + 1, this.dataModel.get(j));
				this.repaint(this.count++);
				j--;
			}
			this.dataModel.set(j + 1, temp);
			this.repaint(this.count++);
		}
	}

	/**
	 * merge function merges the sorted runs
	 *
	 * @param l
	 * @param m
	 * @param r
	 */
	private void merge(final int l, final int m, final int r) {
		// original array is broken in two parts
		// left and right array
		final int len1 = (m - l) + 1;
		final int len2 = r - m;
		final int[] left = new int[len1];
		final int[] right = new int[len2];

		for (int x = 0; x < len1; x++) {
			left[x] = this.dataModel.get(l + x);
		}

		for (int x = 0; x < len2; x++) {
			right[x] = this.dataModel.get(m + 1 + x);
		}

		int i = 0;
		int j = 0;
		int k = l;

		// after comparing, we merge those two array
		// in larger sub array
		while ((i < len1) && (j < len2)) {
			if (left[i] <= right[j]) {
				this.dataModel.set(k, left[i]);
				this.repaint(this.count++);
				i++;
			} else {
				this.dataModel.set(k, right[j]);
				this.repaint(this.count++);
				j++;
			}
			k++;
		}

		// copy remaining elements of left, if any
		while (i < len1) {
			this.dataModel.set(k, left[i]);
			this.repaint(this.count++);
			k++;
			i++;
		}

		// copy remaining element of right, if any
		while (j < len2) {
			this.dataModel.set(k, right[j]);
			this.repaint(this.count++);
			k++;
			j++;
		}
	}

	public void repaint(final int count) {
		this.graphJPanel.repaint();
		try {
			if ((count % 8) == 0) {
				Thread.sleep(SortingFactory.THREAD_SPEED);
			}
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static String getName() {
		return "Tim Sort";
	}

	public boolean stopNow() {
		return SortingFactory.RESET_PRESSED;
	}

}
