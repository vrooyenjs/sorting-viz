package za.co.janspies.sorters;

import org.apache.log4j.Logger;

import za.co.janspies.GraphJPanel;
import za.co.janspies.Sorter;
import za.co.janspies.SortingFactory;
import za.co.janspies.model.DataModel;

public class PancakeSort implements Sorter {
	private static final Logger LOG = Logger.getLogger(HeapSort.class);

	private final GraphJPanel graphJPanel;
	private final DataModel dataModel;
	int count = 0;

	public PancakeSort(final GraphJPanel graphJPanel) {
		LOG.info("Starting up a PancakeSort...");
		this.graphJPanel = graphJPanel;
		this.dataModel = graphJPanel.getDataModel();
		this.dataModel.getStatistics().reset();
	}

	public void run() {
		sort();
	}

	/**
	 * The main function that sorts given array using flip operations
	 */
	public void sort() {
		// Start from the complete
		// array and one by one
		// reduce current size by one
		for (int curr_size = dataModel.getLength(); curr_size > 1; --curr_size) {

			// Find index of the
			// maximum element in
			// arr[0..curr_size-1]
			int mi = findMax(curr_size);

			// Move the maximum element
			// to end of current array
			// if it's not already at
			// the end
			if (mi != curr_size - 1) {
				// To move at the end,
				// first move maximum
				// number to beginning
				flip(mi);

				// Now move the maximum
				// number to end by
				// reversing current array
				flip(curr_size - 1);
			}
		}

	}

	/**
	 * Reverses arr[0..i]
	 * 
	 * @param mi
	 */
	private void flip(int i) {
		int start = 0;
		while (start < i) {
			dataModel.swap(start, i);
			repaint(count++);
			start++;
			i--;
		}
	}

	/**
	 * Returns index of the maximum element in arr[0..n-1]
	 * 
	 * @param curr_size
	 * @return
	 */
	private int findMax(int n) {
		int mi, i;
		for (mi = 0, i = 0; i < n; ++i)
			if (dataModel.get(i) > dataModel.get(mi))
				mi = i;
		return mi;
	}

	public boolean stopNow() {
		return SortingFactory.RESET_PRESSED;
	}

	public void repaint(final int count) {
		this.graphJPanel.repaint();
		try {
			if ((count % 10) == 0) {
				Thread.sleep(SortingFactory.THREAD_SPEED);
			}
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static String getName() {
		return "Pancake Sort";
	}

}
