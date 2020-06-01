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
public class BubbleSort implements Sorter {
    private static final Logger LOG = Logger.getLogger(BubbleSort.class);

    private final GraphJPanel graphJPanel;
    private final DataModel dataModel;
    int count = 0;

    public BubbleSort(final GraphJPanel graphJPanel) {
        LOG.info("Starting up a BubbleSort...");
        this.graphJPanel = graphJPanel;
        this.dataModel = graphJPanel.getDataModel();
        this.dataModel.getStatistics().reset();
    }

    public boolean stopNow() {
        return SortingFactory.RESET_PRESSED;
    }

    public void run() {
        this.sort();
    }

    public void sort() {
        for (int i = 0; i < (this.dataModel.getLength() - 1); i++) {
            boolean swapped = false;
            for (int j = 0; j < (this.dataModel.getLength() - 1); j++) {
                if (this.dataModel.get(j) > this.dataModel.get(j + 1)) {
                    this.dataModel.swap(j, j + 1);
                    this.repaint(this.count++);

                    swapped = true;

                    if (this.stopNow()) {
                        break;
                    }
                }
            }
            if (this.stopNow()) {
                break;
            }
            if (!swapped) {
                break;
            }
        }
        SortingFactory.RESET_PRESSED = false;

    }

    public void repaint(final int count) {
        this.graphJPanel.repaint();
        try {
            if ((count % 10) == 0) {
                Thread.sleep(SortingFactory.THREAD_SPEED);
            }
        } catch (final InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static String getName() {
        return "Bubble Sort";
    }

}
