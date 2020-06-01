package za.co.janspies.sorters;

import org.apache.log4j.Logger;

import za.co.janspies.GraphJPanel;
import za.co.janspies.Sorter;
import za.co.janspies.SortingFactory;
import za.co.janspies.model.DataModel;

public class BinaryInsertionSort implements Sorter {
	private static final Logger LOG = Logger.getLogger(HeapSort.class);

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
		sort();
	}

	/**
	 * The main function that sorts given array using flip operations
	 */
	public void sort() {
		for (int i = 1; i < dataModel.getLength(); i++) {
			int x = dataModel.get(i);

			// Find location to insert using binary search
			int j = Math.abs(dataModel.binarySearch(0, i, x) + 1);

			// Shifting array to one location right
			dataModel.arrayCopy(j, j + 1, i - j);

			// Placing element at its correct location
			dataModel.set(j, x);
			repaint(count++);
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
