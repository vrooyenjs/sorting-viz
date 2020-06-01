package za.co.janspies;

public interface Sorter extends Runnable {
    boolean stopNow();

    void sort();

    void repaint(int count);
}
