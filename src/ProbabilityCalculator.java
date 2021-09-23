import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.BigInteger;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.JRadioButton;
import javax.swing.JSplitPane;
import javax.swing.JTextField;


public class ProbabilityCalculator implements ActionListener{

	private int n=0;
	private int x=0;
	private double p=0;
	private int N=0;
	private int M=0;
	private double lambda=0;
	private double result=0;
	
	private int calcType;
	private final int LESS_THAN=0, EXACTLY=1, AT_LEAST=2;
	private static Distribution dist;
	
	
	
	
	private JFrame frame;
	private JSplitPane outerJsp;
	private JSplitPane innerJsp;

	
	
	private JButton calc;
	private JLabel resultOut;
	
	private JLabel trialsLabel;
	private JLabel successesLabel;
	private JLabel probabilityLabel;
	private JLabel successElementsLabel;
	private JLabel totalElementsLabel;
	private JLabel lambdaLabel;
	
	private JTextField trials;
	private JTextField successes;
	private JTextField probability;
	private JTextField successElements;
	private JTextField totalElements;
	private JTextField lambdaField;
	
	
	private ButtonGroup calcTypeGroup;
	private JRadioButton lt;
	private JRadioButton ex;
	private JRadioButton gte;
	
	
	private JPanel titlePanel;
	private JLabel title;
	
	private JComboBox<String> formulaType;
	private JPanel binomialDistPanel = new JPanel();
	private JPanel negBinomialDistPanel = new JPanel();
	private JPanel hypergeoDistPanel = new JPanel();
	private JPanel multiHyperDistPanel = new JPanel();
	private JPanel poissonDistPanel = new JPanel();
	public final int BINOM=0, NEG_BINOM=1, HYP_GEO=2, MULTI_HYP_GEO=3, POISSON=4;
	private final String DIST_TYPES[] = new String[] {"Binomial Distribution", "Negative Binomial Distribution", "Hypergeometric Distribution", "Multivar. Hypergeometric Dist.",
			"Poisson Distribution"};
	private final JPanel DIST_PANELS[] = new JPanel[] {binomialDistPanel, negBinomialDistPanel, hypergeoDistPanel, multiHyperDistPanel, poissonDistPanel};
	private JPanel currPanel;
	public double probabilities[];
	
	
	private JPanel dropdownPanel = new JPanel();
	
	
	public ProbabilityCalculator() {
		
		
		
		//JLabels
		formulaType = new JComboBox(DIST_TYPES);
		formulaType.addActionListener(new FormulaTypeDropdown());
		
		//Button group
		lt = new JRadioButton("Less than");
		ex = new JRadioButton("Exactly");
		gte = new JRadioButton("At least");
		gte.setSelected(true);
		calcTypeGroup = new ButtonGroup();
		calcTypeGroup.add(lt);
		calcTypeGroup.add(ex);
		calcTypeGroup.add(gte);
		

		
		
		frame = new JFrame();
		calc = new JButton("Update");
		calc.addActionListener(this);
		
		trials = new JTextField();
		successes = new JTextField();
		probability = new JTextField();
		totalElements = new JTextField();
		successElements = new JTextField();
		lambdaField = new JTextField();
		resultOut = new JLabel(""+0);
		

		

		
		
		//----------------------------------------------------------------------------------------
		//by default, BINOM is the currPanel
		initBinomialDistJPanel();
		currPanel=DIST_PANELS[BINOM];
		title = new JLabel(DIST_TYPES[BINOM]);
		titlePanel = new JPanel();
		titlePanel.add(title);
		
		
		
		//----------------------------------------------------------------------------------------
		//adding stuff to dropdownPanel
		dropdownPanel.add(formulaType);
		
		

		
		
		//----------------------------------------------------------------------------------------
		//adding stuff to frame
		innerJsp=new JSplitPane(JSplitPane.VERTICAL_SPLIT, titlePanel, currPanel);
		outerJsp=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, dropdownPanel, innerJsp);
		frame.add(outerJsp, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Probability Calculator");
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);			//prevents resizability
		frame.setLocationRelativeTo(null);	//opens frame in the center of the monitor
		
		
	}
	
	

	     
	

	






	@Override
	//This is the UPDATE button
	public void actionPerformed(ActionEvent e) {
		
		//Gets text box values
		try {
		n=Integer.parseInt(trials.getText());
		}
		catch (NumberFormatException er){
			n=0;
		}
		try {
		x=Integer.parseInt(successes.getText());
		}
		catch (NumberFormatException er) {
			x=0;
		}
		try {
		p=Double.parseDouble(probability.getText());
		}
		catch (NumberFormatException er) {
			p=0;
		}
		try {
			N=Integer.parseInt(totalElements.getText());
		}
		catch (NumberFormatException er) {
			N=0;
		}
		try {
			M=Integer.parseInt(successElements.getText());
		}
		catch (NumberFormatException er) {
			M=0;
		}
		try {
			lambda=Double.parseDouble(lambdaField.getText());
		}
		catch(NumberFormatException er)
		{
			lambda=0;
		}
		if(lt.isSelected())
			calcType=LESS_THAN;
		else if(gte.isSelected())
			calcType=AT_LEAST;
		else
			calcType=EXACTLY;
		
		if (currPanel.equals(DIST_PANELS[BINOM]))
			dist = new BinomialDistribution(x,n,p);
		else if(currPanel.equals(DIST_PANELS[NEG_BINOM]))
			dist = new NegativeBinomialDistribution(x,n,p);
		else if(currPanel.equals(DIST_PANELS[HYP_GEO]))
			dist = new HypergeometricDistribution(x,n,N,M);
		else if (currPanel.equals(DIST_PANELS[MULTI_HYP_GEO]))
			dist = new MultivariateHypergeometricDistribution();
		else if (currPanel.equals(DIST_PANELS[POISSON]));
			dist = new PoissonDistribution(x, lambda);

		result=dist.calculate(calcType);
		//System.out.println("Result: "+result);
		
		resultOut.setText(""+Math.round(result*1000000)/10000.0+"%");	
	}
		

	
	
	
		
	private void initBinomialDistJPanel()
	{
		trialsLabel = new JLabel("Trials:");
		successesLabel = new JLabel("Succesess:                    ");
		probabilityLabel = new JLabel("p:");
		binomialDistPanel.setBorder(BorderFactory.createEmptyBorder(50,50,50,50));
		binomialDistPanel.setLayout(new GridLayout(7,3));
		binomialDistPanel.add(successesLabel);
		binomialDistPanel.add(successes);
		binomialDistPanel.add(trialsLabel);
		binomialDistPanel.add(trials);
		binomialDistPanel.add(probabilityLabel);
		binomialDistPanel.add(probability);
		binomialDistPanel.add(lt);
		binomialDistPanel.add(ex);
		binomialDistPanel.add(gte);
		binomialDistPanel.add(new JLabel());
		binomialDistPanel.add(new JLabel());
		binomialDistPanel.add(calc);
		binomialDistPanel.add(new JLabel("Probabilty:  ", SwingConstants.RIGHT));
		binomialDistPanel.add(resultOut);
	}
	
	private void initNegBinomialDistJPanel() {
		negBinomialDistPanel.add(new JLabel("Not in service."));	
		 /*
		trialsLabel = new JLabel("Trials:");
		successesLabel = new JLabel("Succesess:                    ");
		probabilityLabel = new JLabel("p:");
		negBinomialDistPanel.setBorder(BorderFactory.createEmptyBorder(50,50,50,50));
		negBinomialDistPanel.setLayout(new GridLayout(7,3));
		negBinomialDistPanel.add(successesLabel);
		negBinomialDistPanel.add(successes);
		negBinomialDistPanel.add(trialsLabel);
		negBinomialDistPanel.add(trials);
		negBinomialDistPanel.add(probabilityLabel);
		negBinomialDistPanel.add(probability);
		negBinomialDistPanel.add(lt);
		negBinomialDistPanel.add(ex);
		negBinomialDistPanel.add(gte);
		negBinomialDistPanel.add(new JLabel());
		negBinomialDistPanel.add(new JLabel());
		negBinomialDistPanel.add(calc);
		negBinomialDistPanel.add(new JLabel("Probabilty:  ", SwingConstants.RIGHT));
		negBinomialDistPanel.add(resultOut);
		*/
		
		
	}
	private void initHypGeoDistJPanel() {
		trialsLabel = new JLabel("Trials: (n)");
		successesLabel = new JLabel("Successes (x):");
		totalElementsLabel = new JLabel("Total Elements (N):");
		successElementsLabel = new JLabel("Success Elmnts (M):");
		hypergeoDistPanel.setBorder(BorderFactory.createEmptyBorder(50,50,50,50));
		hypergeoDistPanel.setLayout(new GridLayout(8,3));
		hypergeoDistPanel.add(successesLabel);
		hypergeoDistPanel.add(successes);
		hypergeoDistPanel.add(trialsLabel);
		hypergeoDistPanel.add(trials);
		hypergeoDistPanel.add(totalElementsLabel);
		hypergeoDistPanel.add(totalElements);
		hypergeoDistPanel.add(successElementsLabel);
		hypergeoDistPanel.add(successElements);
		hypergeoDistPanel.add(lt);
		hypergeoDistPanel.add(ex);
		hypergeoDistPanel.add(gte);
		hypergeoDistPanel.add(new JLabel());
		hypergeoDistPanel.add(new JLabel());
		hypergeoDistPanel.add(calc);
		hypergeoDistPanel.add(new JLabel("Probabilty:  ", SwingConstants.RIGHT));
		hypergeoDistPanel.add(resultOut);
		
		
	}
	private void initMultiHypGeoDistJPanel() {
		// TODO Auto-generated method stub
		multiHyperDistPanel.add(new JLabel("Not in service."));		
	}
	private void initPoissonDistJPanel(){
		successesLabel = new JLabel("Successes (x):");
		lambdaLabel = new JLabel("Lambda :");
		poissonDistPanel.setBorder(BorderFactory.createEmptyBorder(50,50,50,50));
		poissonDistPanel.setLayout(new GridLayout(7,3));
		poissonDistPanel.add(successesLabel);
		poissonDistPanel.add(successes);
		poissonDistPanel.add(lambdaLabel);
		poissonDistPanel.add(lambdaField);
		poissonDistPanel.add(new JLabel());
		poissonDistPanel.add(new JLabel());
		//poissonDistPanel.add(lt);
		//poissonDistPanel.add(ex);
		//poissonDistPanel.add(gte);
		poissonDistPanel.add(new JLabel());
		poissonDistPanel.add(new JLabel());
		//temporary
		ex.setSelected(true);
		poissonDistPanel.add(new JLabel());
		
		poissonDistPanel.add(calc);
		poissonDistPanel.add(new JLabel("Probabilty:  ", SwingConstants.RIGHT));
		poissonDistPanel.add(resultOut);
		
	}
	
	
	private void setTitle(int type){
		title.setText(DIST_TYPES[type]);
	}
	

	public static void main(String []args){  
		new ProbabilityCalculator();
	}
	
	
	
	
	
	
	
	


		
		
		
		
	
	
	
	
	//--------------------------------------------------------------------------------------
	//FormulaTypeDropdown for choosing what panel to show
	private class FormulaTypeDropdown implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			JComboBox<String> cb = (JComboBox<String>)e.getSource();
			String distType = (String)cb.getSelectedItem();
			
			
			
			
			currPanel.removeAll();
			//switch(distType)
			if(distType.equals(DIST_TYPES[BINOM]))
			{
				initBinomialDistJPanel();
				innerJsp.setBottomComponent(DIST_PANELS[BINOM]);
				frame.remove(currPanel);
				currPanel=DIST_PANELS[BINOM];
				setTitle(BINOM);
			}
			else if(distType.equals(DIST_TYPES[NEG_BINOM]))
			{
				initNegBinomialDistJPanel();
				innerJsp.setBottomComponent(DIST_PANELS[NEG_BINOM]);
				frame.remove(currPanel);
				currPanel=DIST_PANELS[NEG_BINOM];
				setTitle(NEG_BINOM);
			}
			else if(distType.equals(DIST_TYPES[HYP_GEO]))
			{
				initHypGeoDistJPanel();
				innerJsp.setBottomComponent(DIST_PANELS[HYP_GEO]);
				frame.remove(currPanel);
				currPanel=DIST_PANELS[HYP_GEO];
				setTitle(HYP_GEO);
			}
			else if(distType.equals(DIST_TYPES[MULTI_HYP_GEO]))
			{
				initMultiHypGeoDistJPanel();
				innerJsp.setBottomComponent(DIST_PANELS[MULTI_HYP_GEO]);
				frame.remove(currPanel);
				currPanel=DIST_PANELS[MULTI_HYP_GEO];
				setTitle(MULTI_HYP_GEO);
			}
			else if(distType.equals(DIST_TYPES[POISSON]))
			{
				initPoissonDistJPanel();
				innerJsp.setBottomComponent(DIST_PANELS[POISSON]);
				frame.remove(currPanel);
				currPanel=DIST_PANELS[POISSON];
				setTitle(POISSON);
			}
			
			
		}
		
	}
	
	
	
	
	
	
	
}
