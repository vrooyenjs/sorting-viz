package za.co.janspies.sorters;

import org.apache.log4j.Logger;

import za.co.janspies.GraphJPanel;
import za.co.janspies.Sorter;
import za.co.janspies.SortingFactory;
import za.co.janspies.model.DataModel;

/**
 * We can use binary search to reduce the number of comparisons in normal
 * insertion sort. Binary Insertion Sort uses binary search to find the proper
 * location to insert the selected item at each iteration. In normal insertion
 * sort, it takes O(n^2) comparisons(at nth iteration) in worst case. We can
 * reduce it to O(log n) by using binary search.
 *
 * @author jvanrooyen
 *
 */
public class BinaryInsertionSort implements Sorter {
	private static final Logger LOG = Logger.getLogger(BinaryInsertionSort.class);

	private final GraphJPanel graphJPanel;
	private final DataModel dataModel;
	int count = 0;

	public BinaryInsertionSort(final GraphJPanel graphJPanel) {
		LOG.info("Starting up a BinaryInsertionSort...");
		this.graphJPanel = graphJPanel;
		this.dataModel = graphJPanel.getDataModel();
		this.dataModel.getStatistics().reset();
	}

	public void run() {
		this.sort();
	}

	/**
	 * The main function that sorts given array using flip operations
	 */
	public void sort() {
		for (int i = 1; i < this.dataModel.getLength(); i++) {
			final int x = this.dataModel.get(i);

			// Find location to insert using binary search
			final int j = Math.abs(this.dataModel.binarySearch(0, i, x) + 1);

			// Shifting array to one location right
			this.dataModel.arrayCopy(j, j + 1, i - j);

			// Placing element at its correct location
			this.dataModel.set(j, x);
			this.repaint(this.count++);
		}
	}

	public boolean stopNow() {
		return SortingFactory.RESET_PRESSED;
	}

	public void repaint(final int count) {
		this.graphJPanel.repaint();
		try {
			Thread.sleep(SortingFactory.THREAD_SPEED);
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static String getName() {
		return "Binary Insertion Sort";
	}
}
