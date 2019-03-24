package dijkstraapp;

public class graph4dijkstra {

    private final DArray<DArray<eWeight>> G; // граф
    private final DArray<Edge> AllEdges; // массив всех ребер
    private final DArray<Integer> VertexWeight; // массив веса всех вершин
    private final DArray<Boolean> VisitedVertex; // массив посещенных вершин при поиске кратчайшего пути
    private final DArray<Integer> ParentVertex; // массив посещенных вершин при поиске кратчайшего пути
    private int sizeEdges; // общее число ребер графа
    private int sizeVertex; // общее число вершин графа

    graph4dijkstra() {
        G = new DArray<>();
        AllEdges = new DArray<>();
        VertexWeight = new DArray<>();
        VisitedVertex = new DArray<>();
        ParentVertex = new DArray<>();
        sizeEdges = 0;
        sizeVertex = 0;
    }

    public void set(int g_i, int el_i, eWeight ew) { // установка для матрицы вектора смежности (g_i - вершина, el_i индекс массива вершин куда уходят ребра, ew - объект - вершина на которую можно уйти с g_i с весом ребра)
        DArray<eWeight> tmp;
        if (el_i == 0) {
            tmp = new DArray<>();
        } else {
            tmp = G.get(g_i);
        }
        tmp.add(el_i, ew);
        G.add(g_i, tmp);
    }

    private eWeight get(int g_i, int el_i) { // получение вершины с весом из матрицы вектора смежности (g_i - вершина с которой идет связь на нашу вершину, el_i индекс массива вершин для вершины g_i)
        DArray<eWeight> tmp = G.get(g_i);
        return tmp.get(el_i);
    }

    public void displayGraph() { // вывод графа
        System.out.println("Graph");
        for (int i = 0; i < sizeV(); i++) {
            System.out.print("i-" + i);
            for (int j = 0; j < sizeS(i); j++) {
                if (get(i, j) != null) {
                    System.out.print(" > " + get(i, j).vertex);
                }
            }
            System.out.println("");
        }
    }

    private int sizeV() { // размер матрицы вектора смежности
        return G.size();
    }

    private int sizeS(int v) { // число вершин, на которые есть связь с вершины v
        DArray<eWeight> tmp = G.get(v);
        return tmp.size();
    }

    private void addEdge(int v1, eWeight v2) { // добавление ребра в массив всех ребер
        int v_1 = v1;
        int v_2 = v2.vertex;
        int w = v2.weight;
        AllEdges.add(sizeEdges, new Edge(v_1, v_2, w));
        sizeEdges++;
        AllEdges.add(sizeEdges, new Edge(v_2, v_1, w)); // для неориентированного графа делаем симметричный массив ребер - AllEdges (a,b) = AllEdges (b,a)
        sizeEdges++;
    }

    private int getWeightEdge(int v1, int v2) { // вывод веса ребра по значениям вершин
        int w = -1; // нет ребра между вершинами
        for (int i = 0; i < sizeEdges; i++) {
            if (AllEdges.get(i).V1 == v1 && AllEdges.get(i).V2 == v2) {
                w = AllEdges.get(i).W;
                break;
            }
        }
        return w;
    }

    private Edge getEdge(int v1, int v2) { // вывод ребра по значениям вершин
        Edge e = new Edge(v1, v2, 0);
        for (int i = 0; i < sizeEdges; i++) {
            if (AllEdges.get(i).V1 == v1 && AllEdges.get(i).V2 == v2) {
                e = AllEdges.get(i);
                break;
            }
        }
        return e;
    }

    private void createArrayEdges() { // заполнение массива всех ребер
        for (int i = 0; i < sizeV(); i++) {
            for (int j = 0; j < sizeS(i); j++) {
                addEdge(i, get(i, j));
            }
        }
    }

    public void displayArrayEdges() { // вывод массива всех ребер
        System.out.println("ArrayEdges");
        for (int i = 0; i < sizeEdges; i++) {
            System.out.println(i + "- " + AllEdges.get(i).V1 + " " + AllEdges.get(i).V2 + " " + AllEdges.get(i).W);

        }
    }

    private void sizeVertex() { // число вершин графа - ищем через максимальное значение из V1 или V2
        for (int i = 0; i < sizeEdges; i++) {
            if (sizeVertex < AllEdges.get(i).V1) {
                sizeVertex = AllEdges.get(i).V1;
            }
            if (sizeVertex < AllEdges.get(i).V2) {
                sizeVertex = AllEdges.get(i).V2;
            }
        }
        sizeVertex++; // +1, т.к. считаем от нуля
    }

    private void initVertex() { // вначале каждая вершина с весом бесконечность и не просмотрена
        sizeVertex();
        for (int i = 0; i < sizeVertex; i++) {
            VertexWeight.add(i, -1); // -1 - обозначаем бесконечность у всех вершин
            VisitedVertex.add(i, Boolean.FALSE); // вершины не просмотрены
            ParentVertex.add(i, -1); // -1 - обозначаем отсутствие родителя у всех вершин
        }
    }

    private DArray<Integer> searchNextArrV(int v) { // поиск еще не зафиксированных соседей узла v
        DArray<Integer> nextV = new DArray<>();
        int step = 0;
        for (int i = 0; i < sizeEdges; i++) {
            if (AllEdges.get(i).V1 == v) {
                nextV.add(step, AllEdges.get(i).V2);
                step++;
            }
        }
        return nextV;
    }

    private int searchNextMinV() { // поиск незафиксированной вершины с минимальным весом
        int res = -1;
        for (int i = 0; i < sizeVertex; i++) { // пока первая незафиксированная но уже посещенная вершина;
            if (!VisitedVertex.get(i) && VertexWeight.get(i) != -1) {
                res = i;
                break;
            }
        }

        for (int i = 0; i < sizeVertex; i++) { // поиск по всем вершинам
            if (!VisitedVertex.get(i) && VertexWeight.get(i) != -1 && VertexWeight.get(i) < VertexWeight.get(res)) {
                res = i;
            }
        }
        return res;
    }

    public void resDijkstra() { // вывод результата просчета поиска путей
        for (int i = 0; i < sizeVertex; i++) {
            System.out.println("vertex-" + i + " weight-" + VertexWeight.get(i) + " added-" + VisitedVertex.get(i) + " parent-" + ParentVertex.get(i));
        }
    }

    public void dijkstra(int start) { // поиск кратчайших путей из вершины start

        DArray<Integer> nextV; // массив вершин, связяанных с рассматриваемой
        int minV; // следующая вершина с min весом

        createArrayEdges(); // создаем массив ребер

        initVertex(); // начальная инициализация VertexWeight, VisitedVertex, ParentVertex

        VertexWeight.set(start, 0);
        minV = start;

        while (minV != -1) {

            nextV = searchNextArrV(minV);

            VisitedVertex.set(minV, Boolean.TRUE);

            for (int i = 0; i < nextV.size(); i++) { // пересмотр всех соседних незафиксированных вершин и пересчет весов и перестановка родителей

                if (VertexWeight.get(nextV.get(i)) == -1
                        || // если бесконечность
                        (VertexWeight.get(nextV.get(i)) != -1 && VertexWeight.get(nextV.get(i)) > getWeightEdge(minV, nextV.get(i)) + VertexWeight.get(minV))) { // или не бесконечность и больше веса текущего узла
                    VertexWeight.set(nextV.get(i), getWeightEdge(minV, nextV.get(i)) + VertexWeight.get(minV)); // обновляем вес от начала до этой вершины
                    ParentVertex.set(nextV.get(i), minV);
                }
            }

//        resDijkstra(); // вывод результата просчета поиска путей пошагово

            minV = searchNextMinV();

        }
    }

    public DArray<Edge> getPath(int endVertex) { // вывод пути в виде массива ребер
        DArray<Edge> res = new DArray<>();
        int startV = ParentVertex.get(endVertex);
        int endV = endVertex;
        int sizeRes = 0;
        while (startV != -1) {
            res.add(sizeRes, getEdge(endV, startV));
            endV = startV;
            startV = ParentVertex.get(endV);
            sizeRes++;
        }
        return res;
    }
    
}
