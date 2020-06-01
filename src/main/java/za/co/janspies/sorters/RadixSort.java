package za.co.janspies.sorters;

import java.util.Arrays;

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
public class RadixSort implements Sorter {
	private static final Logger LOG = Logger.getLogger(RadixSort.class);

	private final GraphJPanel graphJPanel;
	private final DataModel dataModel;
	int counting = 0;

	public RadixSort(final GraphJPanel graphJPanel) {
		LOG.info("Starting up a RadixSort...");
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
		// Find the maximum number to know number of digits
		final int m = this.dataModel.getMax();

		// Do counting sort for every digit. Note that instead
		// of passing digit number, exp is passed. exp is 10^i
		// where i is current digit number
		for (int exp = 1; (m / exp) > 0; exp *= 10) {
			this.countSort(exp);
		}
	}

	// A function to do counting sort of arr[] according to
	// the digit represented by exp.
	private void countSort(final int exp) {
		final int output[] = new int[this.dataModel.getLength()]; // output array
		int i;
		final int count[] = new int[10];
		Arrays.fill(count, 0);

		// Store count of occurrences in count[]
		for (i = 0; i < this.dataModel.getLength(); i++) {
			count[(this.dataModel.get(i) / exp) % 10]++;
		}

		// Change count[i] so that count[i] now contains
		// actual position of this digit in output[]
		for (i = 1; i < 10; i++) {
			count[i] += count[i - 1];
		}

		// Build the output array
		for (i = this.dataModel.getLength() - 1; i >= 0; i--) {
			output[count[(this.dataModel.get(i) / exp) % 10] - 1] = this.dataModel.get(i);
			count[(this.dataModel.get(i) / exp) % 10]--;
		}

		// Copy the output array to arr[], so that arr[] now
		// contains sorted numbers according to curent digit
		for (i = 0; i < this.dataModel.getLength(); i++) {
			this.dataModel.set(i, output[i]);
			this.repaint(this.counting++);
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
		return "Radix Sort";
	}

	public boolean stopNow() {
		return SortingFactory.RESET_PRESSED;
	}

}
