package za.co.janspies;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import za.co.janspies.sorters.BinaryInsertionSort;
import za.co.janspies.sorters.BubbleSort;
import za.co.janspies.sorters.CycleSort;
import za.co.janspies.sorters.HeapSort;
import za.co.janspies.sorters.InsertionSort;
import za.co.janspies.sorters.IterativeMergeSort;
import za.co.janspies.sorters.PancakeSort;
import za.co.janspies.sorters.QuickSort;
import za.co.janspies.sorters.RecursiveBubbleSort;
import za.co.janspies.sorters.RecursiveInsertionSort;
import za.co.janspies.sorters.SelectionSort;
import za.co.janspies.sorters.Shuffler;

public class SortingFactory {
	private static final Logger LOG = Logger.getLogger(SortingFactory.class);

	public static boolean RESET_PRESSED = false;

	public static int THREAD_SPEED = 1;
	public static double SORTING_CHAOS = 60;
	public static final int TYPE_SHUFFLER = 0;
	public static final int TYPE_BUBBLE_SORT = 1;

	private static final Class<?>[] SORTING_CLASSES = new Class[] { BinaryInsertionSort.class, BubbleSort.class,
			CycleSort.class, HeapSort.class, InsertionSort.class, IterativeMergeSort.class, PancakeSort.class,
			QuickSort.class, RecursiveBubbleSort.class, RecursiveInsertionSort.class, SelectionSort.class,
			Shuffler.class };

	private SortingFactory() {
	}

	public static Sorter getSorter(final String type, final GraphJPanel graphJPanel) {
		LOG.info("Processing type: " + type);

		try {
			return getNewSorter(type, graphJPanel);
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return new Shuffler(graphJPanel);
	}

	/**
	 *
	 * @param type
	 * @param graphJPanel
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws InstantiationException
	 */
	private static Sorter getNewSorter(final String type, final GraphJPanel graphJPanel)
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, InstantiationException {

		for (final Class<?> clazz : SORTING_CLASSES) {
			final Method method = clazz.getMethod("getName", null);
			if (StringUtils.equalsIgnoreCase(String.valueOf(method.invoke(null)), type)) {
				final Constructor<?> cons = clazz.getConstructor(GraphJPanel.class);
				final Object sorter = cons.newInstance(graphJPanel);
				return (Sorter) sorter;
			}
		}
		return new Shuffler(graphJPanel);
	}

	public static String[] getSortingList() {
		final String[] arr = new String[SORTING_CLASSES.length];
		try {
			int i = 0;
			for (final Class<?> clazz : SORTING_CLASSES) {
				final Method method = clazz.getMethod("getName", null);
				arr[i++] = String.valueOf(method.invoke(null));
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return arr;
	}
}
