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
public class SelectionSort implements Sorter {
    private static final Logger LOG = Logger.getLogger(SortingFactory.class);

    private final GraphJPanel graphJPanel;
    private final DataModel dataModel;

    public SelectionSort(final GraphJPanel graphJPanel) {
        LOG.info("Starting up SelectionSort");
        this.graphJPanel = graphJPanel;
        this.dataModel = graphJPanel.getDataModel();

        this.dataModel.getStatistics().reset();
    }

    public void run() {
        this.sort();
    }

    public void sort() {
        int count = 0;
        for (int i = 0; i < (this.dataModel.getLength() - 1); i++) {

            int minIndex = i;
            for (int j = i + 1; j < this.dataModel.getLength(); j++) {
                count++;
                if (this.dataModel.get(j) < this.dataModel.get(minIndex)) {
                    minIndex = j;
                }
            }

            this.dataModel.swap(i, minIndex);
            this.repaint(count);
            if (this.stopNow()) {
                break;
            }
        }
        SortingFactory.RESET_PRESSED = false;
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

    public boolean stopNow() {
        return SortingFactory.RESET_PRESSED;
    }

    public static String getName() {
        return "Selection Sort";
    }
}
