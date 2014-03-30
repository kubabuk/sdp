package vision.gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.MouseInputAdapter;

import vision.ImageProcessor;
import vision.PitchConstants;
import vision.VisionRunner;
import world.World;

@SuppressWarnings("serial")
public class VisionGUI extends JFrame {
	private final int videoWidth;
	private final int videoHeight;

	// Pitch dimension selector variables
	private boolean selectionActive = false;
	private boolean justClick = false;
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
	
	private final PitchConstants pitchConstants;
	
	private final JPanel videoDisplay = new JPanel();
	private final JTabbedPane tabbedPane = new JTabbedPane();
	private final JPanel blueThresholder;
	private final JPanel yellowThresholder;
	private final JPanel dotsThresholder;
	private final JPanel ballThresholder;
	private final JPanel cameraSettings;
	private final JPanel plateThresholder;
	private final JPanel generalSettings;
	private final StatePanel statePanel;
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
		this.pitchConstants = new PitchConstants(0);
		this.videoWidth = videoWidth;
		this.videoHeight = videoHeight;
		this.world = world;
		this.statePanel = new StatePanel(this.world);
		this.imageProcessor = new ImageProcessor(this.world); 
		this.ballThresholder = new BallThresholder(imageProcessor, pitchConstants);
		this.yellowThresholder = new YellowThresholder(imageProcessor, pitchConstants);
		this.blueThresholder = new BlueThresholder(imageProcessor, pitchConstants);
		this.dotsThresholder = new DotsThresholder(imageProcessor, pitchConstants);
		this.plateThresholder = new PlateThresholder(imageProcessor, pitchConstants);
		this.cameraSettings = new CameraSettingsPanel(vStream, pitchConstants);
		this.generalSettings = new GeneralSettingsPanel(imageProcessor, world, pitchConstants, this);
		
		int pitchWidth = videoWidth - (pitchConstants.getLeftBuffer() + pitchConstants.getRightBuffer());
		world.setWidth(pitchWidth);
                int pitchHeight = videoHeight - (pitchConstants.getTopBuffer() + pitchConstants.getBottomBuffer());
                world.setHeight(pitchHeight);
		imageProcessor.setTop(pitchConstants.getTopBuffer());
		world.setPitchTop(pitchConstants.getTopBuffer());
		imageProcessor.setLeft(pitchConstants.getLeftBuffer());
		world.setPitchLeft(pitchConstants.getLeftBuffer());
		imageProcessor.setRight(videoWidth - pitchConstants.getRightBuffer());
		imageProcessor.setBottom(videoHeight - pitchConstants.getBottomBuffer());
		world.setFirstSectionBoundary((int) (pitchConstants.getLeftBuffer() + (0.21 * pitchWidth)));
		world.setSecondSectionBoundary((int) (pitchConstants.getLeftBuffer() + (0.5 * pitchWidth)));
		world.setThirdSectionBoundary((int) (pitchConstants.getLeftBuffer() + (0.79 * pitchWidth)));

		Container contentPane = this.getContentPane();

		Dimension videoSize = new Dimension(videoWidth, videoHeight);
		
		tabbedPane.setSize(300, this.videoHeight);
		tabbedPane.setLocation(videoWidth, 0);
		tabbedPane.addTab("General", generalSettings);
		tabbedPane.addTab("Ball", ballThresholder);
		tabbedPane.addTab("Dots", dotsThresholder);
		tabbedPane.addTab("Camera Settings", cameraSettings);
		tabbedPane.addTab("Green Plates", plateThresholder);
		tabbedPane.addTab("Blue i", blueThresholder);
		tabbedPane.addTab("Yellow i", yellowThresholder);
		tabbedPane.setFocusable(true);
		contentPane.add(tabbedPane);
		
		BufferedImage blankInitialiser = new BufferedImage(videoWidth,
				videoHeight, BufferedImage.TYPE_INT_RGB);
		getContentPane().setLayout(null);
		videoDisplay.setLocation(0, 0);
		this.videoDisplay.setMinimumSize(videoSize);
		this.videoDisplay.setSize(videoSize);
		contentPane.add(videoDisplay);
		this.setSize(videoWidth + 300, videoHeight + 300);
		this.setVisible(true);
		this.getGraphics().drawImage(blankInitialiser, 0, 0, null);
		
		statePanel.setSize(this.videoWidth, 300);
		statePanel.setLocation(0,videoHeight);
		contentPane.add(statePanel);

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

		MouseInputAdapter mouseSelector = new MouseInputAdapter() {
			Rectangle selection;

			public void mousePressed(MouseEvent e) {
				selectionActive = true;
				justClick = true;
				System.out.println("Initialised anchor");
				// Pitch dimension selector
				anchor = e.getPoint();
				System.out.println(anchor.x);
				System.out.println(anchor.y);
				selection = new Rectangle(anchor);
			}

			public void mouseDragged(MouseEvent e) {
				justClick = false;
				selection.setBounds((int) Math.min(anchor.x, e.getX()),
						(int) Math.min(anchor.y, e.getY()),
						(int) Math.abs(e.getX() - anchor.x),
						(int) Math.abs(e.getY() - anchor.y));
				a = (int) Math.min(anchor.x, e.getX());
				b = (int) Math.min(anchor.y, e.getY());
				c = (int) Math.abs(e.getX() - anchor.x);
				d = (int) Math.abs(e.getY() - anchor.y);
			}

			public void mouseReleased(MouseEvent e) {
				if (justClick){
					imageProcessor.setRobot(e.getPoint());
				}
				selectionActive = false;
				if (e.getPoint().distance(anchor) > 5) {
					try {
						int top = b;
						int bottom = videoHeight - d - b;
						int left = a;
						int right = videoWidth - c - a;
						if (top > 0 && bottom > 0 && left > 0
									&& right > 0) {
						// 	Update pitch constants
							pitchConstants.setTopBuffer(top);
							pitchConstants.setBottomBuffer(bottom);
							pitchConstants.setLeftBuffer(left);
							pitchConstants.setRightBuffer(right);
							
//							Update imageProcessor with new values.
							int pitchWidth = videoWidth - (pitchConstants.getLeftBuffer() + pitchConstants.getRightBuffer());
							world.setWidth(pitchWidth);
                                                        int pitchHeight = videoHeight - (pitchConstants.getTopBuffer() + pitchConstants.getBottomBuffer());
                                                        world.setHeight(pitchHeight);
							imageProcessor.setTop(pitchConstants.getTopBuffer());
							world.setPitchTop(pitchConstants.getTopBuffer());
							imageProcessor.setLeft(pitchConstants.getLeftBuffer());
							world.setPitchLeft(pitchConstants.getLeftBuffer());
							imageProcessor.setRight(videoWidth - pitchConstants.getRightBuffer());
							imageProcessor.setBottom(videoHeight - pitchConstants.getBottomBuffer());
							world.setFirstSectionBoundary((int) (pitchConstants.getLeftBuffer() + (0.21 * pitchWidth)));
							world.setSecondSectionBoundary((int) (pitchConstants.getLeftBuffer() + (0.5 * pitchWidth)));
							world.setThirdSectionBoundary((int) (pitchConstants.getLeftBuffer() + (0.79 * pitchWidth)));

						// 	Writing the new dimensions to file
							FileWriter writer = new FileWriter(
								new File("constants/pitch" + pitchConstants.getPitchNum() + "Dimensions"));
							writer.write("" + top + "\n");
							writer.write("" + bottom + "\n");
							writer.write("" + left + "\n");
							writer.write("" + right + "\n");
							writer.close();
							System.out.println("Wrote pitch const");
						} else {
							System.out.println("Pitch selection NOT succesful");
						}
						System.out.print("Top: " + top + " Bottom " + bottom);
						System.out.println(" Right " + right + " Left "	+ left);
					} catch (IOException e1) {
						System.out.println("Error writing pitch dimensions to file");
						e1.printStackTrace();
					}
					System.out.println("A: " + a + " B: " + b + " C: " + c + " D:" + d);
					repaint();
					}
			}
		};

		this.videoDisplay.addMouseListener(mouseSelector);
		this.videoDisplay.addMouseMotionListener(mouseSelector);

		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.addWindowListener(windowAdapter);
	}
	
	public void refreshThresholds(){
		((BallThresholder) ballThresholder).updateNewValues();
		((BlueThresholder) blueThresholder).updateNewValues();
		((CameraSettingsPanel) cameraSettings).updateNewValues();
		((DotsThresholder) dotsThresholder).updateNewValues();
		((PlateThresholder) plateThresholder).updateNewValues();
		((YellowThresholder) yellowThresholder).updateNewValues();
		imageProcessor.setTop(pitchConstants.getTopBuffer());
		imageProcessor.setLeft(pitchConstants.getLeftBuffer());
		imageProcessor.setRight(videoWidth - pitchConstants.getRightBuffer());
		imageProcessor.setBottom(videoHeight - pitchConstants.getBottomBuffer());
	}

	public void sendFrame(BufferedImage frame, int fps, int frameCounter) {
		this.frame = frame;
		this.fps = fps;
		this.frameCounter = frameCounter;
		Image img = imageProcessor.trackWorld(frame);
		this.videoDisplay.getGraphics().drawImage((BufferedImage) img, 0, 0, this.videoWidth, this.videoHeight, null);
		this.statePanel.updateState();
	}

	public void sendWorldState(World worldState) {
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		Graphics frameGraphics = frame.getGraphics();

		// Draw overlay on top of raw frame
		frameGraphics.drawImage(debugOverlay, 0, 0, null);

		// Draw frame info and worldstate on top of the result
		// Display the FPS that the vision system is running at
		frameGraphics.setColor(Color.white);
		frameGraphics.drawString("Frame: " + frameCounter, 15, 15);
		frameGraphics.drawString("FPS: " + fps, 15, 30);

		// Display Ball & Robot Positions
		frameGraphics.drawString("Ball:", 15, 45);
		frameGraphics.drawString("(" + worldState.getBall().getPos().getX() + ", "
				+ worldState.getBall().getPos().getY() + ")", 60, 45);

	}
}
