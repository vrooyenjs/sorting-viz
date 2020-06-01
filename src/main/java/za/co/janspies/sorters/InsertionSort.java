package za.co.janspies.sorters;

import org.apache.log4j.Logger;

import za.co.janspies.GraphJPanel;
import za.co.janspies.Sorter;
import za.co.janspies.SortingFactory;
import za.co.janspies.model.DataModel;

public class InsertionSort implements Sorter {
    private static final Logger LOG = Logger.getLogger(SortingFactory.class);

    private final GraphJPanel graphJPanel;
    private final DataModel dataModel;

    int count = 0;

    public InsertionSort(final GraphJPanel graphJPanel) {
        LOG.info("Starting up InsertionSort");
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
        for (int i = 1; i < this.dataModel.getLength(); ++i) {
            final int key = this.dataModel.get(i);
            int j = i - 1;

            /*
             * Move elements of arr[0..i-1], that are greater than key, to one position
             * ahead of their current position
             */
            while ((j >= 0) && (this.dataModel.get(j) > key)) {
                this.dataModel.set(j + 1, this.dataModel.get(j));
                this.repaint(this.count++);
                j -= 1;
            }
            this.dataModel.set(j + 1, key);
            this.repaint(this.count++);
        }
    }

    public void repaint(final int count) {
        this.graphJPanel.repaint();
        try {
            if ((count % 40) == 0) {
                Thread.sleep(SortingFactory.THREAD_SPEED);
            }
        } catch (final InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static String getName() {
        return "Insertion Sort";
    }
}
