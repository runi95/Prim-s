package maze;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * MAZE
 * 
 * @author Runar Serigstad - 235165
 * @author Håkon Knudsen - 234259
 * 
 */

public class Main {
	private static ArrayList<String> lines = new ArrayList<>();
	private static ArrayList<Integer> edges = new ArrayList<>();
	private static HashMap<String, Integer> map = new HashMap<>();
	private static Graph g;

	public static void main(String[] args) {
//		args = new String[1];
//		args[0] = "src/data/simple_maze_20x30.txt";
		readMaze(args[0]);
		g = new Graph(nodes());
		for (int i = 0; i < lines.size(); i++) {
			for (int j = 0; j < lines.get(i).length(); j++) {
				if (lines.get(i).substring(j, j + 1).equals("1"))
					addNeighbours(i, j);
			}
		}
		
		for (int a = 0; a < edges.size(); a++) {
			int s = edges.get(a);
			BreadthFirstPaths bfs = new BreadthFirstPaths(g, s);
			for (int b = a; b < edges.size(); b++) {
				int v = edges.get(b);
				if (s != v)
					if (bfs.hasPathTo(v)) {
						System.out.printf("%d to %d (%d):  ", s, v,
								bfs.distTo(v));
						for (int x : bfs.pathTo(v)) {
							if (x == s)
								System.out.print(x);
							else
								System.out.print("-" + x);
						}
						System.out.println();
					}

					else {
						System.out.printf("%d to %d (-):  not connected\n", s,
								v);
					}

			}
		}

//		System.out.println(g);

	} // Main

	public static void addNeighbours(int i, int j) {
		if (lines.size() > i + 1 && lines.get(i + 1).length() > j)
			if (lines.get(i + 1).substring(j, j + 1).equals("1")) {
				g.addEdge(map.get((i + 1) + "," + j), map.get(i + "," + j));
			}
		if (lines.get(i).length() > j + 1)
			if (lines.get(i).substring(j + 1, j + 2).equals("1")) {
				g.addEdge(map.get(i + "," + (j + 1)), map.get(i + "," + j));
			}
	}

	public static int nodes() {
		int counter = 0;
		for (int i = 0; i < lines.size(); i++)
			for (int j = 0; j < lines.get(i).length(); j++)
				if (lines.get(i).substring(j, j + 1).equals("1")) {
					map.put(i + "," + j, counter++);
					if (i == 0 || j == 0 || i == (lines.size() - 1)
							|| j == (lines.get(i).length() - 1)) {
						edges.add(map.get(i + "," + j));
					}
				}
		// System.out.println(map);
		return counter;
	}

	public static void readMaze(String file) {
		File f = new File(file);
		BufferedReader r = null;
		try {
			r = new BufferedReader(new FileReader(f));
			String l;
			while ((l = r.readLine()) != null) {
				if(!l.equals(""))
				lines.add(l);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				r.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

} // Class
