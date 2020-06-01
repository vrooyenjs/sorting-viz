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
public class OddEvenSort implements Sorter {
	private static final Logger LOG = Logger.getLogger(OddEvenSort.class);

	private final GraphJPanel graphJPanel;
	private final DataModel dataModel;
	int count = 0;

	public OddEvenSort(final GraphJPanel graphJPanel) {
		LOG.info("Starting up a OddEvenSort...");
		this.graphJPanel = graphJPanel;
		this.dataModel = graphJPanel.getDataModel();
		this.dataModel.getStatistics().reset();
	}

	public void run() {
		this.sort();
	}

	public void sort() {
		boolean isSorted = false; // Initially array is unsorted

		while (!isSorted) {
			isSorted = true;

			// Perform Bubble sort on odd indexed element
			for (int i = 1; i <= (this.dataModel.getLength() - 2); i = i + 2) {
				if (this.dataModel.gt(i, i + 1)) {
					this.dataModel.swap(i, i + 1);
					this.repaint(this.count++);
					isSorted = false;
				}
			}

			// Perform Bubble sort on even indexed element
			for (int i = 0; i <= (this.dataModel.getLength() - 2); i = i + 2) {
				if (this.dataModel.gt(i, i + 1)) {
					this.dataModel.swap(i, i + 1);
					this.repaint(this.count++);
					isSorted = false;
				}
			}
		}
	}

	public void repaint(final int count) {
		this.graphJPanel.repaint();
		try {
			if ((count % 20) == 0) {
				Thread.sleep(SortingFactory.THREAD_SPEED);
			}
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static String getName() {
		return "Odd/Even Sort";
	}

	public boolean stopNow() {
		return SortingFactory.RESET_PRESSED;
	}

}
