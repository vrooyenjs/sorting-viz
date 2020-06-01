package za.co.janspies.model;

import java.util.concurrent.atomic.AtomicLong;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataModel {

    private int[] data;
    private int length;
    private Statistics statistics;

    public DataModel(final int length) {
        this.statistics = new Statistics();
        this.setLength(length);
        this.init();
    }

    /**
     *
     */
    private void init() {
        this.statistics.setGets(new AtomicLong(0l));
        this.statistics.setSets(new AtomicLong(0l));
        this.statistics.setSwaps(new AtomicLong(0l));
        this.data = new int[this.getLength()];
        for (int i = 0; i < this.getLength(); i++) {
            this.data[i] = i;
        }
    }

    /**
     *
     * @param fromIndex
     * @param toIndex
     */
    public synchronized void swap(final int fromIndex, final int toIndex) {
        this.getStatistics().getSwaps().getAndIncrement();
        final int tmp = this.get(fromIndex);
        this.set(fromIndex, this.get(toIndex));
        this.set(toIndex, tmp);
    }

    /**
     *
     * @param index
     * @return
     */
    public synchronized int get(final int index) {
        this.getStatistics().getGets().getAndIncrement();
        return this.data[index];
    }

    /**
     *
     * @param index
     * @param value
     */
    public synchronized void set(final int index, final int value) {
        this.getStatistics().getSets().getAndIncrement();
        this.data[index] = value;
    }
}
