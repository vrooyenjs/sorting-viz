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
		final int n = this.dataModel.getLength();

		// Build heap (rearrange array)
		for (int i = (n / 2) - 1; i >= 0; i--) {
			this.heapify(n, i);
		}

		// One by one extract an element from heap
		for (int i = n - 1; i > 0; i--) {
			// Move current root to end
			this.dataModel.swap(0, i);
			this.repaint(this.count++);

			// call max heapify on the reduced heap
			this.heapify(i, 0);
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
	void heapify(final int n, final int i) {
		int largest = i; // Initialize largest as root
		final int l = (2 * i) + 1; // left = 2*i + 1
		final int r = (2 * i) + 2; // right = 2*i + 2

		// If left child is larger than root
		if ((l < n) && (this.dataModel.get(l) > this.dataModel.get(largest))) {
			largest = l;
		}

		// If right child is larger than largest so far
		if ((r < n) && (this.dataModel.get(r) > this.dataModel.get(largest))) {
			largest = r;
		}

		// If largest is not root
		if (largest != i) {
			this.dataModel.swap(i, largest);
			this.repaint(this.count++);

			// Recursively heapify the affected sub-tree
			this.heapify(n, largest);
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
