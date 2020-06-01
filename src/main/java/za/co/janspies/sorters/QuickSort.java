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
public class QuickSort implements Sorter {
	private static final Logger LOG = Logger.getLogger(QuickSort.class);

	private final GraphJPanel graphJPanel;
	private final DataModel dataModel;
	int count = 0;

	public QuickSort(final GraphJPanel graphJPanel) {
		LOG.info("Starting up a QuickSort...");
		this.graphJPanel = graphJPanel;
		this.dataModel = graphJPanel.getDataModel();
		this.dataModel.getStatistics().reset();
	}

	public void run() {
		this.sort();
	}

	public void sort() {
		this.sort(0, this.dataModel.getLength() - 1);
	}

	/**
	 * The main function that implements QuickSort() arr[] --> Array to be sorted,
	 * low --> Starting index, high --> Ending index
	 */
	private void sort(final int low, final int high) {
		if (low < high) {
			final int partition = this.partition(low, high);

			// Recursively sort elements before
			// partition and after partition
			this.sort(low, partition - 1);
			this.sort(partition + 1, high);
		}
	}

	/**
	 * This function takes last element as pivot, places the pivot element at its
	 * correct position in sorted array, and places all smaller (smaller than pivot)
	 * to left of pivot and all greater elements to right of pivot
	 *
	 * @param lot
	 * @param high
	 * @return
	 */
	private int partition(final int low, final int high) {
		final int pivot = this.dataModel.get(high);
		int i = (low - 1); // index of smaller element
		for (int j = low; j < high; j++) {
			// If current element is smaller than the pivot
			if (this.dataModel.get(j) < pivot) {
				i++;

				// swap arr[i] and arr[j]
				this.dataModel.swap(i, j);
				this.repaint(this.count++);

			}
		}

		// swap arr[i+1] and arr[high] (or pivot)
		this.dataModel.swap(i + 1, high);
		this.repaint(this.count++);

		return i + 1;
	}

	public boolean stopNow() {
		return SortingFactory.RESET_PRESSED;
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
		return "Quick Sort";
	}
}
