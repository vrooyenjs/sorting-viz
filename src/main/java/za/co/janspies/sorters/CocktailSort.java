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
public class CocktailSort implements Sorter {
	private static final Logger LOG = Logger.getLogger(CocktailSort.class);

	private final GraphJPanel graphJPanel;
	private final DataModel dataModel;
	int count = 0;

	public CocktailSort(final GraphJPanel graphJPanel) {
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
		boolean swapped = true;
		int start = 0;
		int end = this.dataModel.getLength();

		while (swapped == true) {
			// reset the swapped flag on entering the
			// loop, because it might be true from a
			// previous iteration.
			swapped = false;

			// loop from bottom to top same as
			// the bubble sort
			for (int i = start; i < (end - 1); ++i) {
				if (this.dataModel.gt(i, i + 1)) {
					this.dataModel.swap(i, i + 1);
					swapped = true;
					this.repaint(this.count++);
				}
			}

			// if nothing moved, then array is sorted.
			if (swapped == false) {
				break;
			}

			// otherwise, reset the swapped flag so that it
			// can be used in the next stage
			swapped = false;

			// move the end point back by one, because
			// item at the end is in its rightful spot
			end = end - 1;

			// from top to bottom, doing the
			// same comparison as in the previous stage
			for (int i = end - 1; i >= start; i--) {
				if (this.dataModel.gt(i, i + 1)) {
					this.dataModel.swap(i, i + 1);
					swapped = true;
					this.repaint(this.count++);
				}
			}

			// increase the starting point, because
			// the last stage would have moved the next
			// smallest number to its rightful spot.
			start = start + 1;
		}
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
		return "Cocktail Sort";
	}

	public boolean stopNow() {
		return SortingFactory.RESET_PRESSED;
	}

}
