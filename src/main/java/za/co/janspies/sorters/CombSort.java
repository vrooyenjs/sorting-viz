package za.co.janspies.sorters;

import org.apache.log4j.Logger;

import za.co.janspies.GraphJPanel;
import za.co.janspies.Sorter;
import za.co.janspies.SortingFactory;
import za.co.janspies.model.DataModel;

public class CombSort implements Sorter {
	private static final Logger LOG = Logger.getLogger(CombSort.class);

	private final GraphJPanel graphJPanel;
	private final DataModel dataModel;
	int count = 0;

	public CombSort(final GraphJPanel graphJPanel) {
		LOG.info("Starting up a CombSort...");
		this.graphJPanel = graphJPanel;
		this.dataModel = graphJPanel.getDataModel();
		this.dataModel.getStatistics().reset();
	}

	public void run() {
		this.sort();
	}

	// To find gap between elements
	int getNextGap(int gap) {
		// Shrink gap by Shrink factor
		gap = (gap * 10) / 13;
		if (gap < 1) {
			return 1;
		}
		return gap;
	}

	/**
	 * Iterative merge sort function to sort arr[0...n-1]
	 */
	public void sort() {
		final int n = this.dataModel.getLength();

		// initialize gap
		int gap = n;

		// Initialize swapped as true to make sure that
		// loop runs
		boolean swapped = true;

		// Keep running while gap is more than 1 and last
		// iteration caused a swap
		while ((gap != 1) || (swapped == true)) {
			// Find next gap
			gap = this.getNextGap(gap);

			// Initialize swapped as false so that we can
			// check if swap happened or not
			swapped = false;

			// Compare all elements with current gap
			for (int i = 0; i < (n - gap); i++) {
				if (this.dataModel.get(i) > this.dataModel.get(i + gap)) {
					// Swap arr[i] and arr[i+gap]
					this.dataModel.swap(i, i + gap);
					this.repaint(this.count++);

					// Set swapped
					swapped = true;
				}
			}
		}
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
		return "Comb Sort";
	}

	public boolean stopNow() {
		return SortingFactory.RESET_PRESSED;
	}

}
