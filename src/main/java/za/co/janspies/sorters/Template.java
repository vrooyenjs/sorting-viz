package za.co.janspies.sorters;

import org.apache.log4j.Logger;

import za.co.janspies.GraphJPanel;
import za.co.janspies.Sorter;
import za.co.janspies.SortingFactory;
import za.co.janspies.model.DataModel;

public class Template implements Sorter {
	private static final Logger LOG = Logger.getLogger(Template.class);

	private final GraphJPanel graphJPanel;
	private final DataModel dataModel;
	int count = 0;

	public Template(final GraphJPanel graphJPanel) {
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
		return "Merge Sort";
	}

	public boolean stopNow() {
		return SortingFactory.RESET_PRESSED;
	}

}
