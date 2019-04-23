package dijkstraapp;

public class DijkstraApp {

    public static void main(String[] args) {

        int[][] v1 = { // вершины
            {1, 2, 3, 4},
            {5},
            {3, 7},
            {4, 6, 7, 10},
            {5, 6},
            {9},
            {8, 9},
            {8, 10},
            {10},
            {11},};

        int[][] w1 = { // веса ребер
            {3, 3, 5, 1},
            {2},
            {1, 4},
            {2, 3, 2, 2},
            {1, 2},
            {3},
            {5, 8},
            {2, 7},
            {4},
            {7},};

        graph4dijkstra G = new graph4dijkstra(v1, w1); // инициализация двумя массивами - вершин и весов

//        G.displayGraph(); // вывод введенного графа
        Edge[] result = G.dijkstra(10); // запуск Дэйкстры

        System.out.println("Dijkstra :");
        for (int i = 0; i < result.length; i++) {
            System.out.println("i:" + i + "  " + result[i].V1 + " " + result[i].V2); // + хранится вес вершины - result[i].W
        }

//
        Edge[] out = G.getPath(1); // получаем путь до произвольной вершины
        System.out.println("вывод пути");
        for (int i = 0; i < out.length; i++) {
            System.out.print("v1-" + out[i].V1 + " v2-" + out[i].V2); // + хранится вес вершины ребра - result[i].W
            System.out.println("");
        }

        System.out.println("-------------------");

    }

}
