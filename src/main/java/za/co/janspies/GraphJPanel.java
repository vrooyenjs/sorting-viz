package za.co.janspies;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import org.apache.log4j.Logger;

import lombok.Getter;
import lombok.Setter;
import za.co.janspies.model.DataModel;

/**
 *
 * @author jvanrooyen
 *
 */
@Getter
@Setter
public class GraphJPanel extends JPanel {
	private static final Logger LOG = Logger.getLogger(GraphJPanel.class);

	public static final double PANEL_WIDTH = 1695.0;
	public static final double PANEL_HEIGHT = 440.0;
	private static final double BASE_HEIGHT_UNIT = (440.0 / 1695.0);
	private DataModel dataModel;

	/**
	 *
	 */
	private static final long serialVersionUID = 2208266558865886687L;

	/**
	 * Create the panel.
	 *
	 */
	public GraphJPanel() {
		this.dataModel = new DataModel((int) PANEL_WIDTH);
		LOG.info("WIDTH: " + this.dataModel.getLength());
		this.setBackground(Color.WHITE);
	}

	@Override
	protected void paintComponent(final Graphics g) {
		super.paintComponent(g);

		int x = 10;

		for (int i = 0; i < this.dataModel.getLength(); i++) {
			final double hieght = BASE_HEIGHT_UNIT * this.dataModel.get(i);

			g.setColor(new Color(200, 200, (int) Math.floor(((255.0 / 1695.0)) * this.dataModel.get(i))));
			g.drawLine(x, (int) PANEL_HEIGHT, x, (int) (PANEL_HEIGHT - hieght));
			x += 1;
		}
	}

	void reset() {
		this.dataModel = new DataModel((int) PANEL_WIDTH);
		this.repaint();
	}
}
