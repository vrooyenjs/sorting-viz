package za.co.janspies.sorters;

import org.apache.log4j.Logger;

import za.co.janspies.GraphJPanel;
import za.co.janspies.Sorter;
import za.co.janspies.SortingFactory;
import za.co.janspies.model.DataModel;

/**
 * Unlike a traditional sorting algorithm, which attempts to sort with the
 * fewest comparisons possible, the goal is to sort the sequence in as few
 * reversals as possible.
 *
 * @author jvanrooyen
 *
 */
public class PancakeSort implements Sorter {
	private static final Logger LOG = Logger.getLogger(PancakeSort.class);

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
		this.sort();
	}

	/**
	 * The main function that sorts given array using flip operations
	 */
	public void sort() {
		// Start from the complete
		// array and one by one
		// reduce current size by one
		for (int curr_size = this.dataModel.getLength(); curr_size > 1; --curr_size) {

			// Find index of the
			// maximum element in
			// arr[0..curr_size-1]
			final int mi = this.findMax(curr_size);

			// Move the maximum element
			// to end of current array
			// if it's not already at
			// the end
			if (mi != (curr_size - 1)) {
				// To move at the end,
				// first move maximum
				// number to beginning
				this.flip(mi);

				// Now move the maximum
				// number to end by
				// reversing current array
				this.flip(curr_size - 1);
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
			this.dataModel.swap(start, i);
			this.repaint(this.count++);
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
	private int findMax(final int n) {
		int mi, i;
		for (mi = 0, i = 0; i < n; ++i) {
			if (this.dataModel.get(i) > this.dataModel.get(mi)) {
				mi = i;
			}
		}
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
