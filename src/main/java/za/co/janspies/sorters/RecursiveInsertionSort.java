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
public class RecursiveInsertionSort implements Sorter {
	private static final Logger LOG = Logger.getLogger(RecursiveInsertionSort.class);

	private final GraphJPanel graphJPanel;
	private final DataModel dataModel;
	int count = 0;

	public RecursiveInsertionSort(final GraphJPanel graphJPanel) {
		LOG.info("Starting up a RecursiveInsertionSort...");
		this.graphJPanel = graphJPanel;
		this.dataModel = graphJPanel.getDataModel();
		this.dataModel.getStatistics().reset();
	}

	public void run() {
		this.sort();

	}

	public void sort() {
		this.sort(this.dataModel.getLength());
	}

	public void sort(final int n) {
		// Base case
		if (n <= 1) {
			return;
		}

		// Sort first n-1 elements
		this.sort(n - 1);

		// Insert last element at its correct position
		// in sorted array.
		final int last = this.dataModel.get(n - 1);
		int j = n - 2;

		/*
		 * Move elements of arr[0..i-1], that are greater than key, to one position
		 * ahead of their current position
		 */
		while ((j >= 0) && (this.dataModel.get(j) > last)) {
			this.dataModel.set(j + 1, this.dataModel.get(j));
			j--;
		}
		this.dataModel.set(j + 1, last);
		this.repaint(this.count);
	}

	public boolean stopNow() {
		return SortingFactory.RESET_PRESSED;
	}

	public void repaint(final int count) {
		this.graphJPanel.repaint();
		try {
			if ((count % 500) == 0) {
				Thread.sleep(SortingFactory.THREAD_SPEED);
			}
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static String getName() {
		return "Recursive Insertion Sort";
	}
}
