package za.co.janspies;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.log4j.Logger;

import lombok.ToString;
import za.co.janspies.model.DataModel;
import za.co.janspies.sorters.Shuffler;

public class GraphApplication {

    private JFrame frame;
    private JTextField getsTextField;
    private JTextField setTextField;
    private JTextField swapsTextField;

    /**
     * Launch the application.
     */
    public static void main(final String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    final GraphApplication window = new GraphApplication();
                    window.frame.setVisible(true);
                } catch (final Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public GraphApplication() {
        this.initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        this.frame = new JFrame();
        this.frame.getContentPane().setFont(new Font("DejaVu Sans", Font.PLAIN, 12));
        this.frame.setBounds(100, 100, 1744, 731);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.getContentPane().setLayout(null);

        final GraphJPanel graphPanel = new GraphJPanel();
        graphPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        graphPanel.setBounds(12, 12, 1712, 448);
        this.frame.getContentPane().add(graphPanel);

        final JPanel algorythmPanel = new JPanel();
        algorythmPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        algorythmPanel.setBounds(12, 472, 558, 214);
        this.frame.getContentPane().add(algorythmPanel);
        algorythmPanel.setLayout(null);

        final JLabel lblSortingAlgorythm = new JLabel("Sorting Algorythm");
        lblSortingAlgorythm.setBounds(12, 12, 147, 23);
        algorythmPanel.add(lblSortingAlgorythm);

        final JComboBox comboBox = new JComboBox();
        comboBox.setModel(new DefaultComboBoxModel(SortingFactory.getSortingList()));
        comboBox.setBounds(156, 11, 390, 24);
        algorythmPanel.add(comboBox);

        final JButton shuffleButton = new JButton("Shuffle");
        shuffleButton.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent arg0) {
                new Thread(SortingFactory.getSorter(Shuffler.getName(), graphPanel)).start();

            }
        });
        shuffleButton.setBounds(12, 177, 117, 25);
        algorythmPanel.add(shuffleButton);

        final JButton btnReset = new JButton("Reset");
        btnReset.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent arg0) {
                SortingFactory.RESET_PRESSED = true;
                graphPanel.reset();
            }
        });
        btnReset.setBounds(141, 177, 117, 25);
        algorythmPanel.add(btnReset);

        final JLabel lblSortingSpeed = new JLabel("Sorting Speed (01)");
        lblSortingSpeed.setBounds(12, 99, 167, 23);
        algorythmPanel.add(lblSortingSpeed);

        final JSlider sortingSpeedSlider = new JSlider();
        sortingSpeedSlider.setValue(1);
        sortingSpeedSlider.setMaximum(20);
        sortingSpeedSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(final ChangeEvent arg0) {
                lblSortingSpeed.setText("Sorting Speed (" + sortingSpeedSlider.getValue() + ")");
                SortingFactory.THREAD_SPEED = sortingSpeedSlider.getValue();
            }
        });
        sortingSpeedSlider.setMinorTickSpacing(5);
        sortingSpeedSlider.setBounds(12, 123, 534, 16);
        algorythmPanel.add(sortingSpeedSlider);

        final JLabel lblShuffleChaos = new JLabel("Shuffle Chaos(60)");
        lblShuffleChaos.setBounds(12, 47, 177, 23);
        algorythmPanel.add(lblShuffleChaos);

        final JSlider shuffleChaosSlider = new JSlider();
        shuffleChaosSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(final ChangeEvent arg0) {
                lblShuffleChaos.setText("Shuffle Chaos (" + shuffleChaosSlider.getValue() + ")");
                SortingFactory.SORTING_CHAOS = shuffleChaosSlider.getValue();
            }
        });
        shuffleChaosSlider.setValue(60);
        shuffleChaosSlider.setMinorTickSpacing(5);
        shuffleChaosSlider.setMinimum(10);
        shuffleChaosSlider.setBounds(12, 71, 534, 16);
        algorythmPanel.add(shuffleChaosSlider);

        final JButton startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {

                new Thread(SortingFactory.getSorter(comboBox.getSelectedItem().toString(), graphPanel)).start();
            }
        });
        startButton.setBounds(270, 177, 117, 25);
        algorythmPanel.add(startButton);

        final JPanel panel = new JPanel();
        panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        panel.setBounds(582, 472, 1142, 214);
        this.frame.getContentPane().add(panel);
        panel.setLayout(null);

        final JLabel lblDataModelStatistics = new JLabel("Data Model Statistics");
        lblDataModelStatistics.setBounds(31, 22, 173, 15);
        panel.add(lblDataModelStatistics);

        final JLabel lblGets = new JLabel("Gets: ");
        lblGets.setBounds(31, 49, 60, 15);
        panel.add(lblGets);

        final JLabel lblSets = new JLabel("Sets:");
        lblSets.setBounds(31, 76, 47, 15);
        panel.add(lblSets);

        final JLabel lblSwaps = new JLabel("Swaps:");
        lblSwaps.setBounds(31, 103, 65, 15);
        panel.add(lblSwaps);

        this.getsTextField = new JTextField();
        this.getsTextField.setEditable(false);
        this.getsTextField.setBounds(109, 49, 239, 19);
        panel.add(this.getsTextField);
        this.getsTextField.setColumns(10);

        this.setTextField = new JTextField();
        this.setTextField.setEditable(false);
        this.setTextField.setBounds(109, 74, 239, 19);
        panel.add(this.setTextField);
        this.setTextField.setColumns(10);

        this.swapsTextField = new JTextField();
        this.swapsTextField.setEditable(false);
        this.swapsTextField.setBounds(109, 101, 239, 19);
        panel.add(this.swapsTextField);
        this.swapsTextField.setColumns(10);

        new Thread(new StatisticsThread(graphPanel.getDataModel(), this.setTextField, this.getsTextField,
                this.swapsTextField)).start();
    }
}

@ToString
class StatisticsThread implements Runnable {
    private static final Logger LOG = Logger.getLogger(StatisticsThread.class);

    private final JTextField set;
    private final JTextField get;
    private final JTextField swap;
    private final DataModel dataModel;

    public StatisticsThread(final DataModel dataModel, final JTextField set, final JTextField get,
            final JTextField swap) {
        this.dataModel = dataModel;
        this.set = set;
        this.get = get;
        this.swap = swap;
    }

    public void run() {
        LOG.info(this.toString());

        while (true) {
            this.set.setText(String.valueOf(this.dataModel.getStatistics().getSets().get()));
            this.get.setText(String.valueOf(this.dataModel.getStatistics().getGets().get()));
            this.swap.setText(String.valueOf(this.dataModel.getStatistics().getSwaps().get()));

            try {
                Thread.sleep(1);
            } catch (final InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
