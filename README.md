# Maze-Solver
### 2D grid-based maze solver that is written in Java. Uses Swing for GUI and contains 8 different methods for solving mazes.


#### How to Use:


1. Choose a method for solving the maze. There are eight: BFS, DFS, Best-First BFS, Dijkstra's, Bellman-Ford, Floyd-Warshall, A\*, and simulate hand on right wall.
2. Input a maze that is properly formatted.
3. Click solve to solve the maze.


's' marks the start on the maze

'e' marks the end on the maze

'#' (hash or number sign) is a wall

'.' (period or dot) is an empty space that can be traversed


Note 1: '+' (plus sign), '-' (minus sign or dash), and '|' (pipe) marks the path that is found.

Note 2: the program supports irregularly shaped mazes (see 2nd sample maze) and will automatically place walls to fill in the empty spots.


#### About the Code:


MazeSolverGui.java contains the GUI code. It calls functions in MazeSolver.java that does the actual solving. Point.java is a wrapper for 2 integers (row and column). SampleMazes.txt contains 2 sample mazes.


#### 2 Sample Mazes:


```
......#...........
.#....#s.#........
.#.....##..######.
..##.......#......
....#.....#...#..#
.######...#....#..
......#...#...#.#.
...e..#...#...#...

...#...#...#
.#.#.#.#.#.#
.#.#.#.#.#..#
.#.#.#.#.##.##
.#.#.#.#.#....#
.#.#.#.#.####.#
.#.#.#.#.#e#..#
s#...#...#...##
```
