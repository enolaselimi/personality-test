package test;

import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.awt.FlowLayout;

public class MBTI extends JFrame {
	private static final int NO_FUNC=8;  
	private ArrayList<String> questions; 
	private String[] funct;
	private int[] points; 
	private JPanel northPanel; 
	private JPanel centerPanel; 
	private JPanel southPanel; 
	private JLabel countLabel; 
	private JLabel question; 
	private JSlider answer; 
	private JButton button; //next button
	private JButton button1; //previous button
	private ActionListener listener;
	
	//variable to keep track of the number of current question.
	int count = 1;
	
	public MBTI() throws FileNotFoundException { 
		listener = new ClickListener();
		setStarterPoints();
		setFunctions();
		setQuestions();
		createFrame();
	}


	/**
	 *  Read the set of questions from a file 
	 *  
	 */
	private void setQuestions() throws FileNotFoundException {
		questions = new ArrayList<>();
		File readQ = new File("C:\\Users\\DELL\\eclipse-workspace\\personality-test\\src\\resources\\questions.txt");
		Scanner in = new Scanner(readQ);
		while(in.hasNextLine()) {
			questions.add(in.nextLine());
		}
		in.close();
	}

	/**
	 *  Create array with all cognitive functions 
	 */
	private void setFunctions() {
		funct = new String[NO_FUNC]; 
		funct[0] = "Ne";
		funct[1] = "Ni";
		funct[2] = "Se";
		funct[3] = "Si"; 
		funct[4] = "Te";
		funct[5] = "Ti";
		funct[6] = "Fe";
		funct[7] = "Fi";
	}

	/** 
	 * Sets starter points to 0 for all functions 
	 */
	private void setStarterPoints() {
		points = new int[NO_FUNC];
		for(int i = 0; i < NO_FUNC; i++) {
			points[i] = 0;
		}
	}


	/**
	 * Creates the GUI 
	 */
	private void createFrame() {
		setSize(780,400);
		northPanel = new JPanel();
		countLabel = new JLabel("Question no. "+count);
		countLabel.setFont(new Font("New Romantics", Font.BOLD, 20));
		northPanel.add(countLabel);
		northPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		add(northPanel,BorderLayout.NORTH);
		centerPanel = new JPanel();
		question = new JLabel(questions.get(count-1));
		question.setFont(new Font("Century Gothic", Font.BOLD, 16));
		centerPanel.add(question);
		centerPanel.setBorder(BorderFactory.createEmptyBorder(100, 220, 200, 220));
		answer = new JSlider(0,100,0);
		centerPanel.add(answer);
		add(centerPanel,BorderLayout.CENTER);
		button = new JButton("next");
		button.addActionListener(listener);
		button1 = new JButton("previous");
		button1.addActionListener(listener);
		southPanel = new JPanel();
		southPanel.add(button1);
		southPanel.add(button);
		add(southPanel,BorderLayout.SOUTH);
	}
	
	public class ClickListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == button ) {
				// if last question, the next button name changes to Results.
				if(count == 40) { 
					button.setText("Results");
				}
				// Otherwise show next question with slider set to medium value (5).
				if(count < 40) {
					addValue();
					count++;
					countLabel.setText("Question no. "+count);
					question.setText(questions.get(count-1));
					answer.setValue(0);
				}
			}
			//if previous is clicked, go back 1 question.
			if(e.getSource() == button1 && count > 1) {
				count--;
				countLabel.setText("Question no. "+count);
				question.setText(questions.get(count-1));
				removeValue();
				answer.setValue(5);
			}
			//Sort functions according to their points and print results for function with max points.
			if(e.getSource() == button && button.getText() == "Results") {
				String temp = "";
				int t = 0;
				for(int i = 0; i < NO_FUNC; i++) {
					for(int j = i+1; j < NO_FUNC; j++) {
						if (points[i] < points[j]) { 
							temp = funct[i];
							t = points[i];
							funct[i] = funct[j];
							points[i] = points[j];
							funct[j] = temp;
							points[j] = t;
						}
					}
				}
				printResults();
			}
		}

		/**
		 * Method to remove the calculated points, if the previous button is clicked.
		 */
		private void removeValue() {
			int value = answer.getValue();
			if(count <= 5) { 
				points[0] -= value;
			}
			if(count > 5 && count <= 10) { 
				points[1] -= value;
			}
			if(count > 10 && count <= 15) { 
				points[2] -= value; 
			}
			if(count > 15 && count <= 20) {
				points[3] -= value;
			}
			if(count > 20 && count <= 25) {
				points[4] -= value;
			}
			if(count > 25 && count <= 30) {
				points[5] -= value;
			}
			if(count > 30 && count <= 35) {
				points[6] -= value;
			}
			if(count >35 && count <= 40) {
				points[7] -= value;
			}
		}

		/**
		 * Method to add points value. 
		 * Each function has 5 questions( in a specific order). 
		 * According to count's value we add the slider's value to points array.
		 */
		private void addValue() {
			int value = answer.getValue();
			if(count <= 5) { 
				points[0] += value;
			}
			if(count > 5 && count <= 10) { 
				points[1] += value;
			}
			if(count > 10 && count <= 15) { 
				points[2] += value; 
			}
			if(count > 15 && count <= 20) {
				points[3] += value;
			}
			if(count > 20 && count <= 25) {
				points[4] += value;
			}
			if(count > 25 && count <= 30) {
				points[5] += value;
			}
			if(count > 30 && count <= 35) {
				points[6] += value;
			}
			if(count >35 && count <= 40) {
				points[7] += value;
			}
		}
	}


	private void printResults() {
		JTextArea result = new JTextArea(80,80);
		result.setText("Your dominant cognitive function is: ");
		result.setEditable(false);
		result.setFont(new Font("Serif", Font.ITALIC, 18));
		result.setLineWrap(true);
		result.setWrapStyleWord(true);
		remove(northPanel);
		remove(southPanel);
		remove(centerPanel);
		add(result,BorderLayout.CENTER);
		boolean correct = false;
		while(correct == false) {
			for(int i = 0; i < NO_FUNC-1; i++) {
				if(points[i] == points[i+1]) {
					continue;
				} else {
					correct = true;
					break;
				}
			}
			break;
		}
		if(correct == false) {
			result.append("Multiple personality types.\r\n Try again without lying this time XD");
		}
		else {
	
			if(funct[0].equals("Ne")) { 
				result.append("Ne(Extroverted Intuition)\r\n"
						+ "This means your type might be ENTP or ENFP \r\n"
						+"ENTP : Quick, ingenious, stimulating, alert, and outspoken. Resourceful in solving new and challenging "
						+ "problems. Adept at generating conceptual possibilities and then analyzing them strategically. "
						+ "Good at reading other people. Bored by routine, will seldom do the same thing the same way, apt "
						+ "to turn one new interest after another \n\n"
						+ "ENFP: Warmly enthusiastic and imaginative. See life as full of possibilities. Make connections between "
						+ "events and information very quickly, and confidently proceed based on the patterns they see. "
						+ "Want a lot of affirmation from others, and readily give appreciation and support. Spontaneous "
						+ "and flexible, often rely on their ability to improvise and their verbal fluency");
			}
			if(funct[0].equals("Ni")) {
				result.append("Ni(Introverted Intuition) \r\n" 
						+"This means your type might be INTJ or INFJ\r\n"
						+"INTJ: Have original minds and great drive for implementing their ideas and achieving their goals."
						+ "Quickly see patterns in external events and develop long-range explanatory perspectives. When "
						+ "committed, organize a job and carry it through. Skeptical and independent, have high standards "
						+ "of competence and performance – for themselves and others.\n\n"
						+ "INFJ: Seek meaning and connection with ideas, relationships, and material possessions. Want to "
						+ "understand what motivate people and are insightful about others. Conscientious and committed "
						+ "to their firm values. Develop a clear vision about how best to serve the common good. Organized "
						+ "and decisive in implementing their vision.");
				
			}
			if(funct[0].equals("Se")) {
				result.append("Se(Extroverted Sensing) \r\n"
						+ "This means your type might be ESTP or ESFP\r\n"
						+ "ESTP: Flexible and tolerant, they take a pragmatic approach focused on immediate results. Theories and "
						+ "conceptual explanations bore them – they want to act energetically to solve the problem. Focus "
						+ "on the here-and-now, spontaneous, enjoy each moment that they can be active with others. Enjoy "
						+ "material comforts and style. Learn best through doing.\n\n"
						+ "ESFP: Outgoing, friendly, and accepting. Exuberant lovers of life, people, and material comforts. Enjoy "
						+ "working with others to make things happen. Bring common sense and a realistic approach to "
						+ "their work, and make work fun. Flexible and spontaneous, adapt readily to new people and "
						+ "environments. Learn best by trying a new skill with other people.");
			}
			if(funct[0].equals("Si")) { 
				result.append("Si(Introverted Sensing) \r\n"
						+"This means your type might be ISTJ or ISFJ\r\n"
						+ "ISTJ: Quiet, serious, earn success by thoroughness and dependability. Practical, matter-of-fact, "
						+ "realistic, and responsible. Decide logically what should be done and work toward it steadily, "
						+ "regardless of distraction. Take pleasure in making everything orderly and organized – their work, "
						+ "their home, their life. Value traditions and loyalty.\n\n"
						+ "ISFJ: Quiet, friendly, responsible, and conscientious. Committed and steady in meeting their "
						+ "obligations. Thorough, painstaking, and accurate. Loyal, considerate, notice and remember "
						+ "specifics about people are important to them, concerned with how others feel. Strive to create an "
						+ "orderly and harmonious environment at work and at home.");
			}
			if(funct[0].equals("Te")) {
				result.append("Te(Extroverted Thinking) \r\n"
						+"This means your type might be ENTJ or ESTJ\r\n"
						+ "ESTJ: Practical, realistic, matter-of-fact. Decisive, quickly move to implement decisions. Organize "
						+ "project and people to get things done, focus on getting results in the most efficient way possible. "
						+ "Take care of routine details. Have a clear set of logical standards, systematically follow them and "
						+ "want others to also. Forceful in implementing their plans.\n\n"
						+ "ENTJ: Frank, decisive, assumes leadership readily. Quickly see illogical and inefficient procedures and "
						+ "policies, develop and implement comprehensive systems to solve organizational problems. Enjoy "
						+ "long-term planning and goal setting. Usually well-informed, well read, enjoy expanding their "
						+ "knowledge and passing it on to others. Forceful in presenting their ideas.");
			}
			if(funct[0].equals("Ti")) {
				result.append("Ti(Introverted Thinking) \r\n"
						+"This means your type might be INTP or ISTP\r\n"
						+ "INTP: Seek to develop logical explanations for everything that interests them. Theoretical and abstract, "
						+ "interested more in ideas than in social interaction. Quiet, contained, flexible, and adaptable. Have "
						+ "unusual ability to focus in depth to solve problems in their area of interest. Skeptical, sometimes "
						+ "critical, always analytical.\n\n"
						+ "ISTP: Tolerant and flexible, quiet observers until a problem appears, then act quickly to find workable "
						+ "solutions. Analyze what makes things work and readily get through large amounts of data to "
						+ "isolate the core of practical problems. Interested in cause and effect, organize facts using logical "
						+ "principles, value efficiency");
			}
			if(funct[0].equals("Fe")) {
				result.append("Fe(Extroverted Feeling) \r\n"
						+"This means your type might be ENFJ or ESFJ\r\n"
						+ "ESFJ: Warmhearted, conscientious, and cooperative. Want harmony in their environment; work with "
						+ "determination to establish it. Likes to work with others to complete tasks accurately and on time. "
						+ "Loyal, follow through even in small matters. Notice what others need in their day-by-day lives "
						+ "and try to provide it. Want to be appreciated for who they are and for what they contribute.\n\n"
						+ "ENFJ: Warm, empathetic, responsive, and responsible. Highly attuned to the emotions, needs, and "
						+ "motivations of others. Find potential in everyone, want to help others fulfill their potential. May "
						+ "act as catalysts for individual and group growth. Loyal, responsive to praise and criticism. "
						+ "Sociable, facilitate others in a group, and provide inspiring leadership");
			}
			if(funct[0].equals("Fi")) {
				result.append("Fi(Introverted Feeling) \r\n"
						+"This means your type might be INFP or ISFP\r\n"
						+ "ISFP: Quiet, friendly, sensitive, and kind. Enjoy the present moment, what’s going on around them. "
						+ "Like to have their own space and to work within their own time frame. Loyal and committed to "
						+ "their values and to people who are important to them. Dislike disagreements and conflicts, do not "
						+ "force their opinions or values on others.\n\n"
						+ "INFP: Idealistic, loyal to their values and to people who are important to them. Want an external life "
						+ "that is congruent with their values. Curious, quick to see possibilities, can be catalysts for "
						+ "implementing ideas. Seek to understand people and to help them fulfill their potential. "
						+ "Adaptable, flexible, and accepting unless a value is threatened.");
			}
		}
	}  
}
