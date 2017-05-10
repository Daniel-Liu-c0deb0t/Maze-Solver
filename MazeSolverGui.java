import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.border.*;

public class MazeSolverGui implements ActionListener{
	private String solveMode = "wallFollow";
	private JTextArea mazeText;
	private HashMap<String, String> descriptions = new HashMap<String, String>();
	private JLabel descriptionLabel;
	private JLabel pathLength;
	
	public MazeSolverGui() throws Exception{
		descriptions.put("wallFollow", "The algorithm follows the wall to the right of the current moving direction, simulating a person walking with their right hand on the right wall. This is very commonly used in real life path finding, since it is easy to do. It is not guaranteed to find the shortest path.");
		descriptions.put("dfs", "Depth-first search is also known as DFS. It is a recursive algorithm that traverses graphs and 2D arrays. It is not guaranteed to find the shortest path.");
		descriptions.put("bfs", "Breadth-first search is also known as BFS. It is implemented using a queue, and it traverses graphs and 2D arrays. It is guaranteed to find the shortest path.");
		descriptions.put("bestFirstBFS", "This best-first search algorithm is implemented using BFS, but instead of using a queue, it uses a priority queue that sorts the nearby nodes according to a heuristic. In this implementation, the heuristic is the distance to the end. It is not guaranteed to find the shortest path.");
		descriptions.put("aStar", "A* is a path finding algorithm that uses heuristics. It is very commonly used in video games and grid based applications. The heuristic is the distance to the end. It is guaranteed to find the shortest path.");
		descriptions.put("dijkstra", "Dijkstra's shortest path algorithm is a commonly used shortest path algorithm for graphs. It does not support graphs with negative weights. It is guaranteed to find the shortest path.");
		descriptions.put("bellmanFord", "Bellman-Ford is a shortest path algorithm that can support negative edge weights with no negative cycles. It is implemented using dynamic programming. It is guaranteed to find the shortest path.");
		descriptions.put("floydWarshall", "Floyd-Warshall is an all-pair shortest path algorithm that finds the shortest path between every single pair of nodes. It is implemented using dynamic programming and is guaranteed to find the shortest paths.");
		
		JFrame frame = new JFrame("Maze Solver");
		
		JPanel upperPanel = new JPanel();
		upperPanel.setLayout(new FlowLayout());
		
		JPanel modePanel = new JPanel();
		modePanel.setLayout(new BoxLayout(modePanel, BoxLayout.Y_AXIS));
		
		ButtonGroup modeGroup = new ButtonGroup();
		
		JRadioButton wallFollowButton = new JRadioButton("Follow Right Wall");
		wallFollowButton.setActionCommand("wallFollow");
		wallFollowButton.addActionListener(this);
		wallFollowButton.setSelected(true);
		wallFollowButton.setFont(wallFollowButton.getFont().deriveFont(25f));
		modeGroup.add(wallFollowButton);
		modePanel.add(wallFollowButton);
		
		JRadioButton dfsButton = new JRadioButton("Depth First Search");
		dfsButton.setActionCommand("dfs");
		dfsButton.addActionListener(this);
		dfsButton.setFont(dfsButton.getFont().deriveFont(25f));
		modeGroup.add(dfsButton);
		modePanel.add(dfsButton);
		
		JRadioButton bfsButton = new JRadioButton("Breadth First Search");
		bfsButton.setActionCommand("bfs");
		bfsButton.addActionListener(this);
		bfsButton.setFont(bfsButton.getFont().deriveFont(25f));
		modeGroup.add(bfsButton);
		modePanel.add(bfsButton);
		
		JRadioButton bestFirstBFSButton = new JRadioButton("Best First Search (BFS)");
		bestFirstBFSButton.setActionCommand("bestFirstBFS");
		bestFirstBFSButton.addActionListener(this);
		bestFirstBFSButton.setFont(bestFirstBFSButton.getFont().deriveFont(25f));
		modeGroup.add(bestFirstBFSButton);
		modePanel.add(bestFirstBFSButton);
		
		JRadioButton aStarButton = new JRadioButton("A*");
		aStarButton.setActionCommand("aStar");
		aStarButton.addActionListener(this);
		aStarButton.setFont(aStarButton.getFont().deriveFont(25f));
		modeGroup.add(aStarButton);
		modePanel.add(aStarButton);
		
		JRadioButton dijkstraButton = new JRadioButton("Dijkstra's");
		dijkstraButton.setActionCommand("dijkstra");
		dijkstraButton.addActionListener(this);
		dijkstraButton.setFont(dijkstraButton.getFont().deriveFont(25f));
		modeGroup.add(dijkstraButton);
		modePanel.add(dijkstraButton);
		
		JRadioButton bellmanFordButton = new JRadioButton("Bellman-Ford");
		bellmanFordButton.setActionCommand("bellmanFord");
		bellmanFordButton.addActionListener(this);
		bellmanFordButton.setFont(bellmanFordButton.getFont().deriveFont(25f));
		modeGroup.add(bellmanFordButton);
		modePanel.add(bellmanFordButton);
		
		JRadioButton floydWarshallButton = new JRadioButton("Floyd-Warshall");
		floydWarshallButton.setActionCommand("floydWarshall");
		floydWarshallButton.addActionListener(this);
		floydWarshallButton.setFont(floydWarshallButton.getFont().deriveFont(25f));
		modeGroup.add(floydWarshallButton);
		modePanel.add(floydWarshallButton);
		
		TitledBorder modeBorder = BorderFactory.createTitledBorder("Solver Mode");
		modeBorder.setTitleFont(modeBorder.getTitleFont().deriveFont(30f));
		modePanel.setBorder(modeBorder);
		upperPanel.add(modePanel);
		
		descriptionLabel = new JLabel("<html><div width=500>" + descriptions.get(solveMode) + "</div></html>");
		descriptionLabel.setFont(descriptionLabel.getFont().deriveFont(25f));
		TitledBorder descriptionBorder = BorderFactory.createTitledBorder("Description");
		descriptionBorder.setTitleFont(descriptionBorder.getTitleFont().deriveFont(30f));
		descriptionLabel.setBorder(descriptionBorder);
		upperPanel.add(descriptionLabel);
		
		frame.getContentPane().add(upperPanel, BorderLayout.NORTH);
		
		StringBuilder defaultText = new StringBuilder();
		
		for(int i = 0; i < 10; i++){
			for(int j = 0; j < 10; j++){
				if(i == 0 && j == 0)
					defaultText.append(MazeSolver.startChar);
				else if(i == 9 && j == 9)
					defaultText.append(MazeSolver.endChar);
				else
					defaultText.append(MazeSolver.emptyChar);
			}
			defaultText.append('\n');
		}
		
		mazeText = new JTextArea(defaultText.toString());
		mazeText.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 35));
		frame.getContentPane().add(mazeText, BorderLayout.CENTER);
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new FlowLayout());
		
		JButton clearButton = new JButton("Clear");
		clearButton.setFont(clearButton.getFont().deriveFont(30f));
		clearButton.setPreferredSize(new Dimension(200, 50));
		clearButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				mazeText.setText("");
			}
		});
		bottomPanel.add(clearButton);
		
		JButton solveButton = new JButton("Solve!");
		solveButton.setFont(solveButton.getFont().deriveFont(40f));
		solveButton.setPreferredSize(new Dimension(300, 75));
		solveButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				if(mazeText.getText().isEmpty()){
					return;
				}
				
				String[] mazeStrings = mazeText.getText().split("\n");
				ArrayList<Point> result = null;
				Point start = null;
				Point end = null;
				int maxWidth = 0;
				
				for(int i = 0; i < mazeStrings.length; i++)
					maxWidth = Math.max(maxWidth, mazeStrings[i].length());
				
				char[][] mazeArray = new char[mazeStrings.length][maxWidth];
				
				for(int i = 0; i < mazeStrings.length; i++){
					for(int j = 0; j < maxWidth; j++){
						if(j >= mazeStrings[i].length())
							mazeArray[i][j] = MazeSolver.wallChar;
						else
							mazeArray[i][j] = mazeStrings[i].charAt(j);
						
						if(mazeArray[i][j] == MazeSolver.startChar)
							start = new Point(i, j);
						
						if(mazeArray[i][j] == MazeSolver.endChar)
							end = new Point(i, j);
						
						if(mazeArray[i][j] == MazeSolver.horizontalPathChar || mazeArray[i][j] == MazeSolver.verticalPathChar || mazeArray[i][j] == MazeSolver.intersectionPathChar)
							mazeArray[i][j] = MazeSolver.emptyChar;
					}
				}
				
				switch(solveMode){
					case "wallFollow":
						result = MazeSolver.wallFollowSolve(mazeArray, start, end);
						break;
					case "dfs":
						result = MazeSolver.dfsSolve(mazeArray, start, end);
						break;
					case "bfs":
						result = MazeSolver.bfsSolve(mazeArray, start, end);
						break;
					case "bestFirstBFS":
						result = MazeSolver.bestFirstBFSSolve(mazeArray, start, end);
						break;
					case "aStar":
						result = MazeSolver.aStarSolve(mazeArray, start, end);
						break;
					case "dijkstra":
						result = MazeSolver.dijkstraSolve(mazeArray, start, end);
						break;
					case "bellmanFord":
						result = MazeSolver.bellmanFordSolve(mazeArray, start, end);
						break;
					case "floydWarshall":
						result = MazeSolver.floydWarshallSolve(mazeArray, start, end);
						break;
					default:
						break;
				}
				
				if(result == null){
					JOptionPane.showMessageDialog(null, "No valid path to the end!", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}else{
					pathLength.setText("Path length: " + result.size());
					
					for(int i = 1; i < result.size() - 1; i++){
						if(result.get(i - 1).r != result.get(i + 1).r && result.get(i - 1).c != result.get(i + 1).c)
							mazeArray[result.get(i).r][result.get(i).c] = MazeSolver.intersectionPathChar;
						else if(result.get(i - 1).r == result.get(i).r)
							mazeArray[result.get(i).r][result.get(i).c] = MazeSolver.horizontalPathChar;
						else if(result.get(i - 1).c == result.get(i).c)
							mazeArray[result.get(i).r][result.get(i).c] = MazeSolver.verticalPathChar;
					}
					
					StringBuilder sb = new StringBuilder();
					for(int i = 0; i < mazeArray.length; i++){
						sb.append(mazeArray[i]);
						sb.append('\n');
					}
					
					mazeText.setText(sb.toString());
				}
			}
		});
		
		bottomPanel.add(solveButton);
		
		pathLength = new JLabel("Path length: ");
		pathLength.setFont(pathLength.getFont().deriveFont(25f));
		bottomPanel.add(pathLength);
		
		frame.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1200, 1500);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public static void main(String[] args) throws Exception{
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		new MazeSolverGui();
	}
	
	@Override
	public void actionPerformed(ActionEvent e){
		solveMode = e.getActionCommand();
		descriptionLabel.setText("<html><div width=500>" + descriptions.get(solveMode) + "</div></html>");
	}
}
