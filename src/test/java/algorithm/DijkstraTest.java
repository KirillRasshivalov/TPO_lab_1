package algorithm;

import algo.algorithm.Dijkstra;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DijkstraTest {

    private Dijkstra dijkstra;

    @BeforeEach
    public void setUp() {
        dijkstra = new Dijkstra();
    }

    @Test
    @DisplayName("Простой линейный граф")
    void testLinearGraph() {
        int[][] graph = {
                {0, 5, -1},
                {5, 0, 3},
                {-1, 3, 0}
        };
        List<String> expectedTrace = Arrays.asList(
                "Т1",
                "Т2(0→1)", "Т3(1→0)",
                "Т2(1→2)", "Т3(2→1)",
                "Т4",
                "Т5(ver=0,cost=0)",
                "Т6(edge 0→1)", "Т7(dist[1]=5)",
                "Т5(ver=1,cost=5)",
                "Т6(edge 1→0)",
                "Т6(edge 1→2)", "Т7(dist[2]=8)",
                "Т5(ver=2,cost=8)",
                "Т6(edge 2→1)",
                "Т8(return 8)"
        );
        int result = dijkstra.findShortestPath(graph, 0, 2, 3);
        List<String> actualTrace = dijkstra.getTracePoints();
        assertEquals(8, result);
        assertEquals(expectedTrace, actualTrace);
    }

    @Test
    @DisplayName("Граф с несколькими путями")
    void testMultiplePathsGraph() {
        int[][] graph = {
                {0, 4, 2, -1},
                {4, 0, 1, 5},
                {2, 1, 0, 8},
                {-1, 5, 8, 0}
        };
        List<String> expectedTrace = Arrays.asList(
                "Т1",
                "Т2(0→1)", "Т3(1→0)",
                "Т2(0→2)", "Т3(2→0)",
                "Т2(1→2)", "Т3(2→1)",
                "Т2(1→3)", "Т3(3→1)",
                "Т2(2→3)", "Т3(3→2)",
                "Т4",
                "Т5(ver=0,cost=0)",
                "Т6(edge 0→1)", "Т7(dist[1]=4)",
                "Т6(edge 0→2)", "Т7(dist[2]=2)",
                "Т5(ver=2,cost=2)",
                "Т6(edge 2→0)",
                "Т6(edge 2→1)", "Т7(dist[1]=3)",
                "Т6(edge 2→3)", "Т7(dist[3]=10)",
                "Т5(ver=1,cost=3)",
                "Т6(edge 1→0)",
                "Т6(edge 1→2)",
                "Т6(edge 1→3)", "Т7(dist[3]=8)",
                "Т5(ver=1,cost=4)",
                "Т6(edge 1→0)",
                "Т6(edge 1→2)",
                "Т6(edge 1→3)",
                "Т5(ver=3,cost=8)",
                "Т6(edge 3→1)",
                "Т6(edge 3→2)",
                "Т5(ver=3,cost=10)",
                "Т6(edge 3→1)",
                "Т6(edge 3→2)",
                "Т8(return 8)"
        );
        int result = dijkstra.findShortestPath(graph, 0, 3, 4);
        List<String> actualTrace = dijkstra.getTracePoints();
        assertEquals(8, result);
        assertEquals(expectedTrace, actualTrace);
    }

    @Test
    @DisplayName("Граф с недостижимой вершиной")
    void testUnreachableVertex() {
        int[][] graph = {
                {0, 2, -1, -1},
                {2, 0, 3, -1},
                {-1, 3, 0, -1},
                {-1, -1, -1, 0}
        };
        List<String> expectedTrace = Arrays.asList(
                "Т1",
                "Т2(0→1)", "Т3(1→0)",
                "Т2(1→2)", "Т3(2→1)",
                "Т4",
                "Т5(ver=0,cost=0)",
                "Т6(edge 0→1)", "Т7(dist[1]=2)",
                "Т5(ver=1,cost=2)",
                "Т6(edge 1→0)",
                "Т6(edge 1→2)", "Т7(dist[2]=5)",
                "Т5(ver=2,cost=5)",
                "Т6(edge 2→1)",
                "Т8(return -1)"
        );
        int result = dijkstra.findShortestPath(graph, 0, 3, 4);
        List<String> actualTrace = dijkstra.getTracePoints();
        assertEquals(-1, result);
        assertEquals(expectedTrace, actualTrace);
    }

    @Test
    @DisplayName("Граф с нулевыми весами")
    void testZeroWeights() {
        int[][] graph = {
                {0, 0, 5},
                {0, 0, 0},
                {5, 0, 0}
        };
        List<String> expectedTrace = Arrays.asList(
                "Т1",
                "Т2(0→1)", "Т3(1→0)",
                "Т2(0→2)", "Т3(2→0)",
                "Т2(1→2)", "Т3(2→1)",
                "Т4",
                "Т5(ver=0,cost=0)",
                "Т6(edge 0→1)", "Т7(dist[1]=0)",
                "Т6(edge 0→2)", "Т7(dist[2]=5)",
                "Т5(ver=1,cost=0)",
                "Т6(edge 1→0)",
                "Т6(edge 1→2)", "Т7(dist[2]=0)",
                "Т5(ver=2,cost=0)",
                "Т6(edge 2→0)",
                "Т6(edge 2→1)",
                "Т5(ver=2,cost=5)",
                "Т6(edge 2→0)",
                "Т6(edge 2→1)",
                "Т8(return 0)"
        );
        int result = dijkstra.findShortestPath(graph, 0, 1, 3);
        List<String> actualTrace = dijkstra.getTracePoints();
        assertEquals(0, result);
        assertEquals(expectedTrace, actualTrace);
    }

    @Test
    @DisplayName("Путь в себя")
    void testPathToSelf() {
        int[][] graph = {
                {0, 2, 3},
                {2, 0, 1},
                {3, 1, 0}
        };
        List<String> expectedTrace = Arrays.asList(
                "Т1",
                "Т2(0→1)", "Т3(1→0)",
                "Т2(0→2)", "Т3(2→0)",
                "Т2(1→2)", "Т3(2→1)",
                "Т4",
                "Т5(ver=0,cost=0)",
                "Т6(edge 0→1)", "Т7(dist[1]=2)",
                "Т6(edge 0→2)", "Т7(dist[2]=3)",
                "Т5(ver=1,cost=2)",
                "Т6(edge 1→0)",
                "Т6(edge 1→2)",
                "Т5(ver=2,cost=3)",
                "Т6(edge 2→0)",
                "Т6(edge 2→1)",
                "Т8(return 0)"
        );
        int result = dijkstra.findShortestPath(graph, 0, 0, 3);
        List<String> actualTrace = dijkstra.getTracePoints();
        assertEquals(0, result);
        assertEquals(expectedTrace, actualTrace);
    }

    @Test
    @DisplayName("Граф с изолированной вершиной")
    void testIsolatedVertex() {
        int[][] graph = {
                {0, 2, -1},
                {2, 0, -1},
                {-1, -1, 0}
        };
        List<String> expectedTrace = Arrays.asList(
                "Т1",
                "Т2(0→1)", "Т3(1→0)",
                "Т4",
                "Т5(ver=0,cost=0)",
                "Т6(edge 0→1)", "Т7(dist[1]=2)",
                "Т5(ver=1,cost=2)",
                "Т6(edge 1→0)",
                "Т8(return -1)"
        );
        int result = dijkstra.findShortestPath(graph, 0, 2, 3);
        List<String> actualTrace = dijkstra.getTracePoints();
        assertEquals(-1, result);
        assertEquals(expectedTrace, actualTrace);
    }

    @Test
    @DisplayName("Полносвязный граф")
    void testCompleteGraph() {
        int[][] graph = {
                {0, 1, 2},
                {1, 0, 3},
                {2, 3, 0}
        };
        List<String> expectedTrace = Arrays.asList(
                "Т1",
                "Т2(0→1)", "Т3(1→0)",
                "Т2(0→2)", "Т3(2→0)",
                "Т2(1→2)", "Т3(2→1)",
                "Т4",
                "Т5(ver=0,cost=0)",
                "Т6(edge 0→1)", "Т7(dist[1]=1)",
                "Т6(edge 0→2)", "Т7(dist[2]=2)",
                "Т5(ver=1,cost=1)",
                "Т6(edge 1→0)",
                "Т6(edge 1→2)",
                "Т5(ver=2,cost=2)",
                "Т6(edge 2→0)",
                "Т6(edge 2→1)",
                "Т8(return 2)"
        );
        int result = dijkstra.findShortestPath(graph, 0, 2, 3);
        List<String> actualTrace = dijkstra.getTracePoints();
        assertEquals(2, result);
        assertEquals(expectedTrace, actualTrace);
    }
}
