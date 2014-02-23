package vision.gui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.MouseInputAdapter;

import vision.ImageProcessor;
import vision.VisionRunner;
import world.World;

@SuppressWarnings("serial")
public class VisionGUI extends JFrame {
	private final int videoWidth;
	private final int videoHeight;

	// Pitch dimension selector variables
	private boolean selectionActive = false;
	private Point anchor;
	private int a;
	private int b;
	private int c;
	private int d;

	// Stored to only have rendering happen in one place
	private BufferedImage frame;
	private int fps;
	private int frameCounter;
	private BufferedImage debugOverlay;

	// Mouse listener variables
	boolean letterAdjustment = false;
	boolean yellowPlateAdjustment = false;
	boolean bluePlateAdjustment = false;
	boolean greyCircleAdjustment = false;
	boolean targetAdjustment = false;
	int mouseX;
	int mouseY;
	String adjust = "";
	File currentFile;
	File imgLetterT = new File("icons/Tletter2.png");
	File imgYellowPlate = new File("icons/YellowPlateSelector.png");
	File imgBluePlate = new File("icons/BluePlateSelector.png");
	File imgGreyCircle = new File("icons/GreyCircleSelector.png");
	BufferedImage selectorImage = null;
	BufferedImage letterTSelectorImage = null;
	BufferedImage yellowPlateSelectorImage = null;
	BufferedImage bluePlateSelectorImage = null;
	BufferedImage greyCircleSelectorImage = null;
	ArrayList<?>[] extractedColourSettings;
	double imageCenterX;
	double imageCenterY;
	int rotation = 0;
	ArrayList<Integer> xList = new ArrayList<Integer>();
	ArrayList<Integer> yList = new ArrayList<Integer>();

	private World world;
	private ImageProcessor imageProcessor;
	
	private final JPanel videoDisplay = new JPanel();
	private final JTabbedPane tabbedPane = new JTabbedPane();
	private final JPanel blueThresholder;
	private final JPanel yellowThresholder;
	private final JPanel dotsThresholder;
	private final JPanel ballThresholder;
	private final JPanel cameraSettings;
	private final JPanel plateThresholder;
	private final WindowAdapter windowAdapter = new WindowAdapter() {
		@Override
		public void windowClosing(WindowEvent e) {
			dispose();

			System.exit(0);
		}
	};

	public VisionGUI(final int videoWidth, final int videoHeight,
			final VisionRunner vStream, final World world) {

		super("Vision");
		this.videoWidth = videoWidth;
		this.videoHeight = videoHeight;
		this.world = world;
		this.imageProcessor = new ImageProcessor(this.world); 
		this.ballThresholder = new BallThresholder(imageProcessor);
		this.yellowThresholder = new YellowThresholder(imageProcessor);
		this.blueThresholder = new BlueThresholder(imageProcessor);
		this.dotsThresholder = new DotsThresholder(imageProcessor);
		this.plateThresholder = new PlateThresholder(imageProcessor);
		this.cameraSettings = new CameraSettingsPanel(vStream);

		Container contentPane = this.getContentPane();

		Dimension videoSize = new Dimension(videoWidth, videoHeight);
		
		tabbedPane.setSize(300, this.videoHeight);
		tabbedPane.setLocation(videoWidth, 0);
		tabbedPane.addTab("Ball", ballThresholder);
		tabbedPane.addTab("Dots", dotsThresholder);
		tabbedPane.addTab("Camera Settings", cameraSettings);
		tabbedPane.addTab("Robots", plateThresholder);
		tabbedPane.setFocusable(true);
		contentPane.add(tabbedPane);
		BufferedImage blankInitialiser = new BufferedImage(videoWidth,
				videoHeight, BufferedImage.TYPE_INT_RGB);
		getContentPane().setLayout(null);
		videoDisplay.setLocation(0, 0);
		this.videoDisplay.setMinimumSize(videoSize);
		this.videoDisplay.setSize(videoSize);
		contentPane.add(videoDisplay);
		this.setSize(videoWidth + 300, videoHeight);
		this.setVisible(true);
		this.getGraphics().drawImage(blankInitialiser, 0, 0, null);

		this.setResizable(false);
		videoDisplay.setFocusable(true);
		videoDisplay.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent ke) {
			}

			public void keyReleased(KeyEvent ke) {
				adjust = KeyEvent.getKeyText(ke.getKeyCode());
			}

			public void keyTyped(KeyEvent e) {
			}
		});

		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.addWindowListener(windowAdapter);
	}

	public void sendFrame(BufferedImage frame, int fps, int frameCounter) {
		this.frame = frame;
		this.fps = fps;
		this.frameCounter = frameCounter;
		if (frameCounter % 1000 < 10) {
			imageProcessor.trackBoundaries(frame);
		}
		Image img = imageProcessor.trackWorld(frame);
		this.videoDisplay.getGraphics().drawImage((BufferedImage) img, 0, 0, this.videoWidth, this.videoHeight, null);
	}
}
