package za.co.janspies.sorters;

import org.apache.log4j.Logger;

import za.co.janspies.GraphJPanel;
import za.co.janspies.Sorter;
import za.co.janspies.SortingFactory;
import za.co.janspies.model.DataModel;

/**
 * ShellSort is mainly a variation of Insertion Sort. In insertion sort, we move
 * elements only one position ahead. When an element has to be moved far ahead,
 * many movements are involved. The idea of shellSort is to allow exchange of
 * far items. In shellSort, we make the array h-sorted for a large value of h.
 * We keep reducing the value of h until it becomes 1. An array is said to be
 * h-sorted if all sublists of every h’th element is sorted.
 *
 * @author jvanrooyen
 *
 */
public class ShellSort implements Sorter {
	private static final Logger LOG = Logger.getLogger(ShellSort.class);

	private final GraphJPanel graphJPanel;
	private final DataModel dataModel;
	int count = 0;

	public ShellSort(final GraphJPanel graphJPanel) {
		LOG.info("Starting up a ShellSort...");
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
		// Start with a big gap, then reduce the gap
		for (int gap = this.dataModel.getLength() / 2; gap > 0; gap /= 2) {

			// Do a gapped insertion sort for this gap size.
			// The first gap elements a[0..gap-1] are already
			// in gapped order keep adding one more element
			// until the entire array is gap sorted
			for (int i = gap; i < this.dataModel.getLength(); i += 1) {

				// add a[i] to the elements that have been gap
				// sorted save a[i] in temp and make a hole at
				// position i
				final int temp = this.dataModel.get(i);

				// shift earlier gap-sorted elements up until
				// the correct location for a[i] is found
				int j;
				for (j = i; (j >= gap) && (this.dataModel.get(j - gap) > temp); j -= gap) {
					this.dataModel.set(j, this.dataModel.get(j - gap));
					this.repaint(this.count++);
				}

				// put temp (the original a[i]) in its correct
				// location
				this.dataModel.set(j, temp);
				this.repaint(this.count++);
			}
		}
	}

	public void repaint(final int count) {
		this.graphJPanel.repaint();
		try {
			if ((count % 2) == 0) {
				Thread.sleep(SortingFactory.THREAD_SPEED);
			}
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static String getName() {
		return "Shell Sort";
	}

	public boolean stopNow() {
		return SortingFactory.RESET_PRESSED;
	}

}
