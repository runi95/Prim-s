package minimum.spanning.forest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Minimum Spanning Forest
 * 
 * @author Runar Serigstad - 235165
 * @author Håkon Knudsen - 234259
 * 
 */

public class Main {
	static ArrayList<Double> wlist = new ArrayList<>();
	static ST<Double, Integer> wst = new ST<>();
	static EdgeWeightedGraph g;
	static Graph g2;
	static int v, e;

	public static void main(String[] args) {
		if (args.length != 2)
			throw new IllegalArgumentException(
					"Please input vertices.txt and weights.txt");
		readWeights(args[1]);
		read(args[0]);
		//	Done reading files

		// O(E*log(V))
		PrimMST mst = new PrimMST(g);
		g2 = new Graph(v);
		// O(E)
		for (Edge e : mst.edges()) {
			g2.addEdge(e.either(), e.other(e.either()));
		}

		CC cc = new CC(g2);

		int[] arr = new int[v];
		ST<Double, Integer>[] st = new ST[cc.count()];
		// O(V)
		for (int i = 0; i < v; i++) {
			if (st[cc.id(i)] == null)
				st[cc.id(i)] = new ST<>();
			st[cc.id(i)].put(wlist.get(i), i);
			arr[i] = cc.id(i);
		}
		
		System.out.println("Number of trees in forest " + cc.count());

		int min = wst.get(wst.min());

		System.out.println("Edges in MST's: ");
		System.out.println(g2);

		// O(V)
		for (int i = 0; i < st.length; i++) {
			if (min != st[i].get(st[i].min())) {
				g2.addEdge(min, st[i].get(st[i].min()));
			}
		}

		System.out.println("Edges after connecting MST's: ");
		System.out.println(g2);
	}

	public static void readWeights(String file) {
		File f = new File(file);

		BufferedReader r = null;
		try {
			r = new BufferedReader(new FileReader(f));
			String line;
			while ((line = r.readLine()) != null) {
				String[] split = line.split(" ");
				Integer vertex = Integer.parseInt(split[0]);
				Double weight = Double.parseDouble(split[1]);
				wlist.add(weight);
				wst.put(weight, vertex - 1);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				r.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void read(String file) {
		File f = new File(file);

		BufferedReader r = null;
		try {
			r = new BufferedReader(new FileReader(f));
			String line;
			int l = 0;
			while ((line = r.readLine()) != null) {
				if (l == 0) {
					v = Integer.parseInt(line);
					g = new EdgeWeightedGraph(v);
					g2 = new Graph(v);
				} else if (l == 1) {
					e = Integer.parseInt(line);
				} else {
					String[] split = line.split(" ");
					int e1 = Integer.parseInt(split[0]), e2 = Integer
							.parseInt(split[1]);
					g.addEdge(new Edge(e1 - 1, e2 - 1, (wlist.get(e1 - 1) + wlist
							.get(e2 - 1))));
				}
				l++;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				r.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
