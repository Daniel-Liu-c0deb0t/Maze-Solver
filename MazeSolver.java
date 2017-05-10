import java.util.*;

public class MazeSolver {
	public static final char wallChar = '#';
	public static final char startChar = 's';
	public static final char endChar = 'e';
	public static final char emptyChar = '.';
	public static final char verticalPathChar = '|';
	public static final char horizontalPathChar = '-';
	public static final char intersectionPathChar = '+';
	
	private static int[] dC = {1, 0, -1, 0};
	private static int[] dR = {0, 1, 0, -1};
	
	public static ArrayList<Point> wallFollowSolve(char[][] maze, Point start, Point end){
		for(int i = 3; i >= 0; i--){
			Point direction = start.c > end.c ? new Point(dR[i], dC[i]) : new Point(dR[i], dC[i]).getLeft();
			Point position = start.add(direction);
			
			if(position.pointOutside(maze[0].length, maze.length) || maze[position.r][position.c] == wallChar){
				ArrayList<Point> result = wallFollowSolveHelper(maze, start, end, direction, new ArrayList<Point>(), new HashMap<Point, Point>());
				
				if(result != null)
					return result;
			}
		}
		
		return null;
	}
	
	private static ArrayList<Point> wallFollowSolveHelper(char[][] maze, Point curr, Point end, Point direction, ArrayList<Point> currPath, HashMap<Point, Point> visited){
		if(visited.containsKey(curr) && visited.get(curr).equals(direction))
			return null;
		
		currPath.add(curr);
		visited.put(curr, direction);
		
		if(curr.equals(end)){
			return currPath;
		}
		
		Point rightPos = curr.add(direction.getRight());
		Point leftPos = curr.add(direction.getLeft());
		Point nextPos = curr.add(direction);
		ArrayList<Point> result = null;
		
		if(!rightPos.pointOutside(maze[0].length, maze.length) && maze[rightPos.r][rightPos.c] != wallChar){
			result = wallFollowSolveHelper(maze, rightPos, end, direction.getRight(), currPath, visited);
			if(result != null)
				return result;
		}
		
		if(!nextPos.pointOutside(maze[0].length, maze.length) && maze[nextPos.r][nextPos.c] != wallChar){
			result = wallFollowSolveHelper(maze, nextPos, end, direction, currPath, visited);
			if(result != null)
				return result;
		}
		
		if(!leftPos.pointOutside(maze[0].length, maze.length) && maze[leftPos.r][leftPos.c] != wallChar){
			result = wallFollowSolveHelper(maze, leftPos, end, direction.getLeft(), currPath, visited);
		}
		
		return result;
	}
	
	public static ArrayList<Point> dfsSolve(char[][] maze, Point start, Point end){
		return dfsSolveHelper(maze, start, end, new HashSet<Point>(), new ArrayList<Point>());
	}
	
	private static ArrayList<Point> dfsSolveHelper(char[][] maze, Point curr, Point end, HashSet<Point> visited, ArrayList<Point> currPath){
		if(curr.pointOutside(maze[0].length, maze.length) || maze[curr.r][curr.c] == wallChar || visited.contains(curr)){
			return null;
		}
		
		currPath.add(curr);
		visited.add(curr);
		
		if(curr.equals(end)){
			return currPath;
		}
		
		for(int i = 0; i < 4; i++){
			ArrayList<Point> result = dfsSolveHelper(maze, new Point(curr.r + dR[i], curr.c + dC[i]), end, visited, currPath);
			if(result != null){
				return result;
			}
		}
		
		currPath.remove(currPath.size() - 1);
		
		return null;
	}
	
	public static ArrayList<Point> bfsSolve(char[][] maze, Point start, Point end){
		LinkedList<Point> queue = new LinkedList<Point>();
		HashMap<Point, Point> prev = new HashMap<Point, Point>();
		
		queue.add(start);
		prev.put(start, null);
		
		while(!queue.isEmpty()){
			Point p = queue.poll();
			
			if(p.equals(end)){
				ArrayList<Point> result = new ArrayList<Point>();
				
				Point curr = p;
				while(curr != null){
					result.add(curr);
					curr = prev.get(curr);
				}
				
				Collections.reverse(result);
				
				return result;
			}
			
			for(int i = 0; i < 4; i++){
				Point next = new Point(p.r + dR[i], p.c + dC[i]);
				if(!next.pointOutside(maze[0].length, maze.length) && maze[p.r + dR[i]][p.c + dC[i]] != wallChar && !prev.containsKey(next)){
					prev.put(next, p);
					queue.offer(next);
				}
			}
		}
		
		return null;
	}
	
	public static ArrayList<Point> bestFirstBFSSolve(char[][] maze, Point start, Point end){
		PriorityQueue<Point> queue = new PriorityQueue<Point>(new Comparator<Point>(){
			@Override
			public int compare(Point p1, Point p2){
				return p1.distTo(end) - p2.distTo(end);
			}
		});
		HashMap<Point, Point> prev = new HashMap<Point, Point>();
		
		queue.add(start);
		prev.put(start, null);
		
		while(!queue.isEmpty()){
			Point p = queue.poll();
			
			if(p.equals(end)){
				ArrayList<Point> result = new ArrayList<Point>();
				
				Point curr = p;
				while(curr != null){
					result.add(curr);
					curr = prev.get(curr);
				}
				
				Collections.reverse(result);
				
				return result;
			}
			
			for(int i = 0; i < 4; i++){
				Point next = new Point(p.r + dR[i], p.c + dC[i]);
				if(!next.pointOutside(maze[0].length, maze.length) && maze[p.r + dR[i]][p.c + dC[i]] != wallChar && !prev.containsKey(next)){
					prev.put(next, p);
					queue.offer(next);
				}
			}
		}
		
		return null;
	}
	
	public static ArrayList<Point> aStarSolve(char[][] maze, Point start, Point end){
		HashSet<Point> closedSet = new HashSet<Point>();
		
		HashMap<Point, Integer> gScore = new HashMap<Point, Integer>();
		gScore.put(start, 0);
		
		HashMap<Point, Integer> fScore = new HashMap<Point, Integer>();
		fScore.put(start, start.distTo(end));
		
		HashMap<Point, Point> prev = new HashMap<Point, Point>();
		prev.put(start, null);
		
		PriorityQueue<Point> openSet = new PriorityQueue<Point>(new Comparator<Point>(){
			@Override
			public int compare(Point p1, Point p2){
				if(!fScore.containsKey(p1))
					fScore.put(p1, Integer.MAX_VALUE);
				
				if(!fScore.containsKey(p2))
					fScore.put(p2, Integer.MAX_VALUE);
				
				return fScore.get(p1) - fScore.get(p2);
			}
		});
		openSet.add(start);
		
		while(!openSet.isEmpty()){
			Point p = openSet.poll();
			
			if(p.equals(end)){
				ArrayList<Point> result = new ArrayList<Point>();
				
				result.add(p);
				
				Point curr = p;
				while(curr != start){
					curr = prev.get(curr);
					result.add(curr);
				}
				
				Collections.reverse(result);
				
				return result;
			}
			
			closedSet.add(p);
			
			for(int i = 0; i < 4; i++){
				Point next = new Point(p.r + dR[i], p.c + dC[i]);
				
				if(closedSet.contains(next) || next.pointOutside(maze[0].length, maze.length) || maze[next.r][next.c] == wallChar)
					continue;
				
				if(!gScore.containsKey(p))
					gScore.put(p, Integer.MAX_VALUE);
				
				int newGScore = Math.min(gScore.get(p) + 1, Integer.MAX_VALUE);
				
				if(!prev.containsKey(next))
					openSet.add(next);
				else if(newGScore >= gScore.get(next))
					continue;
				
				prev.put(next, p);
				gScore.put(next, newGScore);
				fScore.put(next, gScore.get(next) + next.distTo(end));
			}
		}
		
		return null;
	}
	
	public static ArrayList<Point> dijkstraSolve(char[][] maze, Point start, Point end){
		HashMap<Point, Integer> dist = new HashMap<Point, Integer>();
		dist.put(start, 0);
		
		HashMap<Point, Point> prev = new HashMap<Point, Point>();
		prev.put(start, null);
		
		PriorityQueue<Point> queue = new PriorityQueue<Point>(new Comparator<Point>(){
			@Override
			public int compare(Point p1, Point p2){
				if(!dist.containsKey(p1))
					dist.put(p1, Integer.MAX_VALUE);
				
				if(!dist.containsKey(p2))
					dist.put(p2, Integer.MAX_VALUE);
				
				return dist.get(p1) - dist.get(p2);
			}
		});
		queue.offer(start);
		
		while(!queue.isEmpty()){
			Point p = queue.poll();
			
			if(dist.get(p) == Integer.MAX_VALUE)
				continue;
			
			for(int i = 0; i < 4; i++){
				Point next = new Point(p.r + dR[i], p.c + dC[i]);
				
				if(!dist.containsKey(next))
					dist.put(next, Integer.MAX_VALUE);
				
				if(next.pointOutside(maze[0].length, maze.length) || maze[next.r][next.c] == wallChar)
					continue;
				
				if(dist.get(p) + p.distTo(next) < dist.get(next)){
					dist.put(next, dist.get(p) + p.distTo(next));
					
					if(!prev.containsKey(next))
						queue.offer(next);
					
					prev.put(next, p);
				}
			}
		}
		
		ArrayList<Point> result = new ArrayList<Point>();
		Point curr = end;
		while(curr != null){
			result.add(curr);
			curr = prev.get(curr);
		}
		
		Collections.reverse(result);
		
		return result.size() < 2 ? null : result;
	}
	
	public static ArrayList<Point> bellmanFordSolve(char[][] maze, Point start, Point end){
		HashMap<Point, Integer> dist = new HashMap<Point, Integer>();
		HashMap<Point, Point> prev = new HashMap<Point, Point>();
		
		dist.put(start, 0);
		prev.put(start, null);
		
		for(int i = 0; i < maze.length; i++){
			for(int j = 0; j < maze[0].length; j++){
				if(maze[i][j] == wallChar)
					continue;
				
				for(int k = 0; k < maze.length; k++){
					for(int l = 0; l < maze[0].length; l++){
						if(maze[k][l] == wallChar)
							continue;
						
						Point u = new Point(k, l);
						
						if(!dist.containsKey(u))
							dist.put(u, Integer.MAX_VALUE);
						
						if(dist.get(u) == Integer.MAX_VALUE)
							continue;
						
						for(int m = 0; m < 4; m++){
							Point v = new Point(k + dR[m], l + dC[m]);
							
							if(v.pointOutside(maze[0].length, maze.length) || maze[v.r][v.c] == wallChar)
								continue;
							
							if(!dist.containsKey(v))
								dist.put(v, Integer.MAX_VALUE);
							
							if(dist.get(u) + u.distTo(v) < dist.get(v)){
								dist.put(v, dist.get(u) + u.distTo(v));
								prev.put(v, u);
							}
						}
					}
				}
			}
		}
		
		ArrayList<Point> result = new ArrayList<Point>();
		Point curr = end;
		while(curr != null){
			result.add(curr);
			curr = prev.get(curr);
		}
		
		Collections.reverse(result);
		
		return result.size() < 2 ? null : result;
	}
	
	public static ArrayList<Point> floydWarshallSolve(char[][] maze, Point start, Point end){
		int[][][][] dist = new int[maze.length][maze[0].length][maze.length][maze[0].length];
		Point[][][][] next = new Point[maze.length][maze[0].length][maze.length][maze[0].length];
		
		for(int i = 0; i < maze.length; i++){
			for(int j = 0; j < maze[0].length; j++){
				Point p1 = new Point(i, j);
				
				for(int k = 0; k < maze.length; k++){
					for(int l = 0; l < maze[0].length; l++){
						Point p2 = new Point(k, l);
						
						dist[i][j][k][l] = (maze[k][l] != wallChar && p1.distTo(p2) == 1) ? 1 : Integer.MAX_VALUE;
						next[i][j][k][l] = (p1.distTo(p2) > 1 || maze[k][l] == wallChar) ? null : p2;
					}
				}
			}
		}
		
		for(int i = 0; i < maze.length; i++){
			for(int j = 0; j < maze[0].length; j++){
				for(int k = 0; k < maze.length; k++){
					for(int l = 0; l < maze[0].length; l++){
						if(dist[k][l][i][j] == Integer.MAX_VALUE)
							continue;
						
						for(int m = 0; m < maze.length; m++){
							for(int n = 0; n < maze[0].length; n++){
								if(dist[i][j][m][n] == Integer.MAX_VALUE)
									continue;
								
								if(dist[k][l][m][n] > dist[k][l][i][j] + dist[i][j][m][n]){
									dist[k][l][m][n] = dist[k][l][i][j] + dist[i][j][m][n];
									next[k][l][m][n] = next[k][l][i][j];
								}
							}
						}
					}
				}
			}
		}
		
		if(next[start.r][start.c][end.r][end.c] == null)
			return null;
		
		ArrayList<Point> result = new ArrayList<Point>();
		Point curr = start;
		
		result.add(curr);
		
		while(!curr.equals(end)){
			curr = next[curr.r][curr.c][end.r][end.c];
			result.add(curr);
		}
		
		return result;
	}
}
