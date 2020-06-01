package za.co.janspies.sorters;

import java.util.Arrays;

import org.apache.log4j.Logger;

import za.co.janspies.GraphJPanel;
import za.co.janspies.Sorter;
import za.co.janspies.SortingFactory;
import za.co.janspies.model.DataModel;

public class PigeonholeSort implements Sorter {
	private static final Logger LOG = Logger.getLogger(PigeonholeSort.class);

	private final GraphJPanel graphJPanel;
	private final DataModel dataModel;
	int count = 0;

	public PigeonholeSort(final GraphJPanel graphJPanel) {
		LOG.info("Starting up a PigeonholeSort...");
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
		int min = this.dataModel.get(0);
		int max = this.dataModel.get(0);
		int range, i, j, index;

		for (int a = 0; a < this.dataModel.getLength(); a++) {
			if (this.dataModel.get(a) > max) {
				max = this.dataModel.get(a);
			}
			if (this.dataModel.get(a) < min) {
				min = this.dataModel.get(a);
			}
		}

		range = (max - min) + 1;
		final int[] phole = new int[range];
		Arrays.fill(phole, 0);

		for (i = 0; i < this.dataModel.getLength(); i++) {
			phole[this.dataModel.get(i) - min]++;

		}

		index = 0;

		for (j = 0; j < range; j++) {
			while (phole[j]-- > 0) {
				this.dataModel.set(index++, j + min);
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
		return "Pigeonhole Sort";
	}

	public boolean stopNow() {
		return SortingFactory.RESET_PRESSED;
	}

}
