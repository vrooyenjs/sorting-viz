package za.co.janspies;

/**
 *
 * @author jvanrooyen
 *
 */
public interface Sorter extends Runnable {
	boolean stopNow();

	void sort();

	void repaint(int count);
}
