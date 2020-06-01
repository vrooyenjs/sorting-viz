package za.co.janspies.sorters;

import org.apache.log4j.Logger;

import za.co.janspies.GraphJPanel;
import za.co.janspies.Sorter;
import za.co.janspies.SortingFactory;
import za.co.janspies.model.DataModel;

public class HeapSort implements Sorter {
	private static final Logger LOG = Logger.getLogger(HeapSort.class);

	private final GraphJPanel graphJPanel;
	private final DataModel dataModel;
	int count = 0;

	public HeapSort(final GraphJPanel graphJPanel) {
		LOG.info("Starting up a HeapSort...");
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

	public void sort() {
		int n = dataModel.getLength();

		// Build heap (rearrange array)
		for (int i = n / 2 - 1; i >= 0; i--)
			heapify(n, i);

		// One by one extract an element from heap
		for (int i = n - 1; i > 0; i--) {
			// Move current root to end
			dataModel.swap(0, i);
			repaint(count++);

			// call max heapify on the reduced heap
			heapify(i, 0);
		}
	}

	/**
	 * To heapify a subtree rooted with node i which is an index in arr[]. n is size
	 * of heap
	 * 
	 * @param arr
	 * @param n
	 * @param i
	 */
	void heapify(int n, int i) {
		int largest = i; // Initialize largest as root
		int l = 2 * i + 1; // left = 2*i + 1
		int r = 2 * i + 2; // right = 2*i + 2

		// If left child is larger than root
		if (l < n && dataModel.get(l) > dataModel.get(largest))
			largest = l;

		// If right child is larger than largest so far
		if (r < n && dataModel.get(r) > dataModel.get(largest))
			largest = r;

		// If largest is not root
		if (largest != i) {
			dataModel.swap(i, largest);
			repaint(count++);

			// Recursively heapify the affected sub-tree
			heapify(n, largest);
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
		return "Heap Sort";
	}

}
