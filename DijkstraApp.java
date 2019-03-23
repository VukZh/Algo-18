package dijkstraapp;

public class DijkstraApp {

    public static void main(String[] args) {

        graph4dijkstra G = new graph4dijkstra();

        G.set(0, 0, new eWeight(1, 3)); // ввод графа
        G.set(0, 1, new eWeight(2, 3));
        G.set(0, 2, new eWeight(3, 5));
        G.set(0, 3, new eWeight(4, 1));
        G.set(1, 0, new eWeight(5, 2));
        G.set(2, 0, new eWeight(3, 1));
        G.set(2, 1, new eWeight(7, 4));
        G.set(3, 0, new eWeight(4, 2));
        G.set(3, 1, new eWeight(6, 3));
        G.set(3, 2, new eWeight(7, 2));
        G.set(3, 3, new eWeight(10, 2));
        G.set(4, 0, new eWeight(5, 1)); // 4- вершина; 0 - индекс вектора смежности; 5- соединенная вершина; 1 - вес
        G.set(4, 1, new eWeight(6, 2));
        G.set(5, 0, new eWeight(9, 3));
        G.set(6, 0, new eWeight(8, 5));
        G.set(6, 1, new eWeight(9, 8));
        G.set(7, 0, new eWeight(8, 2));
        G.set(7, 1, new eWeight(10, 7));
        G.set(8, 0, new eWeight(10, 4));
        G.set(9, 0, new eWeight(11, 7));

//        G.displayGraph(); // вывод введенного графа

        G.dijkstra(10); // запуск Дэйкстры
        System.out.println("-------------------");
        G.resDijkstra(); // вывод значений у вершин после расчета
        System.out.println("-------------------");        

        DArray<Edge> out = G.getPath(11); // получаем путь до произвольной вершины
        System.out.println("вывод пути");
        for (int i = 0; i < out.size(); i++) {
            System.out.print("v1-" + out.get(i).V1 + " v2-" + out.get(i).V2 + " w-" + out.get(i).W);
            System.out.println("");
        }

        System.out.println("-------------------");

    }

}
