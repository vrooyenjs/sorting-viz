package za.co.janspies.sorters;

import org.apache.log4j.Logger;

import za.co.janspies.GraphJPanel;
import za.co.janspies.Sorter;
import za.co.janspies.SortingFactory;
import za.co.janspies.model.DataModel;

/**
 * Cycle sort is an in-place sorting Algorithm, unstable sorting algorithm, a
 * comparison sort that is theoretically optimal in terms of the total number of
 * writes to the original array.
 *
 * @author jvanrooyen
 *
 */
public class CycleSort implements Sorter {
	private static final Logger LOG = Logger.getLogger(CycleSort.class);

	private final GraphJPanel graphJPanel;
	private final DataModel dataModel;
	int count = 0;

	public CycleSort(final GraphJPanel graphJPanel) {
		LOG.info("Starting up a CycleSort...");
		this.graphJPanel = graphJPanel;
		this.dataModel = graphJPanel.getDataModel();
		this.dataModel.getStatistics().reset();
	}

	public void run() {
		this.sort();
	}

	public boolean stopNow() {
		return SortingFactory.RESET_PRESSED;
	}

	/**
	 * Function sort the array using Cycle sort
	 */
	public void sort() {

		// traverse array elements and put it to on
		// the right place
		for (int cycle_start = 0; cycle_start <= (this.dataModel.getLength() - 2); cycle_start++) {

			// initialize item as starting point
			int item = this.dataModel.get(cycle_start);

			// Find position where we put the item. We basically
			// count all smaller elements on right side of item.
			int pos = cycle_start;
			for (int i = cycle_start + 1; i < this.dataModel.getLength(); i++) {
				if (this.dataModel.get(i) < item) {
					pos++;
				}
			}

			// If item is already in correct position
			if (pos == cycle_start) {
				continue;
			}

			// ignore all duplicate elements
			while (item == this.dataModel.get(pos)) {
				pos += 1;
			}

			// put the item to it's right position
			if (pos != cycle_start) {
				final int temp = item;
				item = this.dataModel.get(pos);
				this.dataModel.set(pos, temp);
				this.repaint(this.count++);
			}

			// Rotate rest of the cycle
			while (pos != cycle_start) {
				pos = cycle_start;

				// Find position where we put the element
				for (int i = cycle_start + 1; i < this.dataModel.getLength(); i++) {
					if (this.dataModel.get(i) < item) {
						pos += 1;
					}
				}

				// ignore all duplicate elements
				while (item == this.dataModel.get(pos)) {
					pos += 1;
				}

				// put the item to it's right position
				if (item != this.dataModel.get(pos)) {
					final int temp = item;
					item = this.dataModel.get(pos);
					this.dataModel.set(pos, temp);
					this.repaint(this.count++);
				}
			}

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
		return "Cycle Sort";
	}
}
