package za.co.janspies.sorters;

import za.co.janspies.GraphJPanel;
import za.co.janspies.Sorter;
import za.co.janspies.SortingFactory;

public class Shuffler implements Sorter {

    private final GraphJPanel graphJPanel;
    int count = 0;

    public Shuffler(final GraphJPanel graphJPanel) {
        this.graphJPanel = graphJPanel;
        this.graphJPanel.getDataModel().getStatistics().reset();
    }

    public void run() {
        this.sort();
    }

    public void sort() {

        this.forwardShuffle();
        this.backwardsShuffle();

        SortingFactory.RESET_PRESSED = false;
    }

    private void forwardShuffle() {
        for (int i = 0; i < this.graphJPanel.getDataModel().getLength(); i++) {
            final double probability = Math.random();

            // 80% chance to shuffle at index i
            if (probability < (SortingFactory.SORTING_CHAOS / 100)) {
                final int toIndex = (int) Math.floor(Math.random() * (this.graphJPanel.getDataModel().getLength() - 1));
                this.graphJPanel.getDataModel().swap(i, toIndex);

                this.repaint(this.count++);
                if (this.stopNow()) {
                    break;
                }
            }
        }
    }

    private void backwardsShuffle() {
        for (int i = this.graphJPanel.getDataModel().getLength() - 1; i > 0; i--) {
            final double probability = Math.random();

            // 80% chance to shuffle at index i
            if (probability < (SortingFactory.SORTING_CHAOS / 100)) {
                final int toIndex = (int) Math.floor(Math.random() * (this.graphJPanel.getDataModel().getLength() - 1));
                this.graphJPanel.getDataModel().swap(i, toIndex);
                this.repaint(this.count++);
                if (this.stopNow()) {
                    break;
                }
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

    public boolean stopNow() {
        return SortingFactory.RESET_PRESSED;
    }

    public static String getName() {
        return "Shuffle";
    }
}
