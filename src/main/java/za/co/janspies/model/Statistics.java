package za.co.janspies.model;

import java.util.concurrent.atomic.AtomicLong;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author jvanrooyen
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class Statistics {
	public void reset() {
		this.gets = new AtomicLong(0l);
		this.sets = new AtomicLong(0l);
		this.swaps = new AtomicLong(0l);
	}

	private AtomicLong gets = new AtomicLong(0l);
	private AtomicLong sets = new AtomicLong(0l);
	private AtomicLong swaps = new AtomicLong(0l);
}
