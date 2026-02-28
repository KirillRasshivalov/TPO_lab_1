package algo.algorithm;

import lombok.Getter;

import java.util.*;

public class Dijkstra {

    private static final int INT_MAX = Integer.MAX_VALUE;

    @Getter
    private List<String> tracePoints;

    private class Edge {
        int from;
        int to;
        int weight;

        public Edge(int from, int to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }
    }

    private class Pair implements Comparable<Pair> {
        int cost;
        int ver;

        public Pair(int cost, int ver) {
            this.cost = cost;
            this.ver = ver;
        }

        @Override
        public int compareTo(Pair other) {
            if (this.cost != other.cost) {
                return Integer.compare(this.cost, other.cost);
            }
            return Integer.compare(this.ver, other.ver);
        }
    }

    public int findShortestPath(int[][] graph, int from, int to, int numOfTops) {
        tracePoints = new ArrayList<>();
        tracePoints.add("Т1");
        List<List<Edge>> edges = new ArrayList<>();
        for (int i = 0; i < numOfTops; i++) {
            edges.add(new ArrayList<>());
        }
        for (int i = 0; i < graph.length; i++) {
            for (int j = i + 1; j < graph[i].length; j++) {
                if (graph[i][j] >= 0) {
                    tracePoints.add("Т2(" + i + "→" + j + ")");
                    edges.get(i).add(new Edge(i, j, graph[i][j]));
                    tracePoints.add("Т3(" + j + "→" + i + ")");
                    edges.get(j).add(new Edge(j, i, graph[i][j]));
                }
            }
        }
        int[] dists = new int[numOfTops];
        Arrays.fill(dists, INT_MAX);
        tracePoints.add("Т4");
        dists[from] = 0;
        PriorityQueue<Pair> pq = new PriorityQueue<>();
        pq.offer(new Pair(dists[from], from));
        while (!pq.isEmpty()) {
            Pair pair = pq.poll();
            int ver = pair.ver;
            int cost = pair.cost;
            tracePoints.add("Т5(ver=" + ver + ",cost=" + cost + ")");
            for (Edge edge : edges.get(ver)) {
                int v = edge.to, w = edge.weight;
                int totWeight = w + cost;
                tracePoints.add("Т6(edge " + ver + "→" + v + ")");
                if (totWeight < dists[v]) {
                    tracePoints.add("Т7(dist[" + v + "]=" + totWeight + ")");
                    dists[v] = totWeight;
                    pq.offer(new Pair(totWeight, v));
                }
            }
        }
        int res = dists[to] != INT_MAX ? dists[to] : -1;
        tracePoints.add("Т8(return " + res + ")");
        return res;
    }
}
