package za.co.janspies.sorters;

import org.apache.log4j.Logger;

import za.co.janspies.GraphJPanel;
import za.co.janspies.Sorter;
import za.co.janspies.SortingFactory;
import za.co.janspies.model.DataModel;

public class RecursiveBubbleSort implements Sorter {
    private static final Logger LOG = Logger.getLogger(QuickSort.class);

    private final GraphJPanel graphJPanel;
    private final DataModel dataModel;
    int count = 0;

    public RecursiveBubbleSort(final GraphJPanel graphJPanel) {
        LOG.info("Starting up a RecursiveBubbleSort...");
        this.graphJPanel = graphJPanel;
        this.dataModel = graphJPanel.getDataModel();
        this.dataModel.getStatistics().reset();
    }

    public void run() {
        this.sort();

    }

    public void sort() {
        this.sort(this.dataModel.getLength());
    }

    public void sort(final int n) {

        // Base case
        if (n == 1) {
            return;
        }

        // One pass of bubble sort. After
        // this pass, the largest element
        // is moved (or bubbled) to end.
        for (int i = 0; i < (n - 1); i++) {
            if (this.dataModel.get(i) > this.dataModel.get(i + 1)) {
                this.dataModel.swap(i, i + 1);
                this.repaint(this.count++);
            }
        }

        // Largest element is fixed,
        // recur for remaining array
        this.sort(n - 1);

    }

    public boolean stopNow() {
        return SortingFactory.RESET_PRESSED;
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
        return "Recursive Bubble Sort";
    }
}
