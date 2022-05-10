import java.util.ArrayList;

public class RegNet
{
    //creates a regional network
    //G: the original graph
    //max: the budget
    public static Graph run(Graph G, int max) 
    {
        Graph newGraph = new Graph(G.V());
        newGraph.setCodes(G.getCodes());

        UnionFind uf = new UnionFind(G.V());


        ArrayList<Edge> sortedOne= new ArrayList<Edge>();
        sortedOne.addAll(G.sortedEdges()); //how bout copy() ?


        ArrayList<Edge> sortedOG= new ArrayList<Edge>();
        sortedOG.addAll(G.sortedEdges()); //how bout copy() ?
        //[6, 2, 5, 3, 9]
        //[2, 3, 5, 6, 9] <- have em

        //from slides
        int T = 0;
        int ind = 0;

        while(T < G.V() - 1) {


            if (uf.find(sortedOne.get(ind).ui()) != uf.find(G.edges().get(ind).vi())) {

//                resultSub.add(sortedOne.get(ind));
                newGraph.addEdge(sortedOne.get(ind));
                T++;
                uf.union(sortedOne.get(ind).ui(), sortedOne.get(ind).vi());

            }
            ind++;
        }//while

        sortedOne = newGraph.sortedEdges();
        int count = sortedOne.size() - 1;

        while(count >= 0) {
                if(newGraph.totalWeight() > max) { //here B| A C
                    if(newGraph.deg(sortedOne.get(count).u) == 1 || newGraph.deg(sortedOne.get(count).v) == 1) {
                        newGraph.removeEdge(sortedOne.get(count));
//                        sortedOne.remove(count);
                        count = sortedOne.size();
                    }
//                    if(newGraph.totalWeight() - (int) resultSub.get(resultSub.size() - 1) {}

//                    resultSub.remove(g);
                }//checker if its more than the budget
            count--;
            }//while loop

        //must connect
        newGraph = newGraph.connGraph();

        if(newGraph.totalWeight() < max) {//step 3

            ArrayList<Pair> DepthFirstArray = Pair.df(newGraph);

            while(true) {
                Pair p = Pair.mx(DepthFirstArray, G);

                if(p.stopC == -1) {
                    break;
                }
                if(newGraph.totalWeight() + G.getEdgeWeight(G.index(newGraph.getCode(p.start)), G.index(newGraph.getCode(p.end))) <= max) {
                    newGraph.addEdge(G.getEdge(G.index(newGraph.getCode(p.start)), G.index(newGraph.getCode(p.end) ) ) );
                }


            }//while loop

        }//if < max

	    //TODO

        Graph newNewGraph = newGraph.connGraph();
        return newNewGraph;
    }

    //SAME AS PART B, DK
    public static int OHYEEE(Graph graphhyyy, int START, int ENDDESU) {  //int value of u //from B(vertex) to D(dest)

        int[] PREVIOUS = new int[graphhyyy.V()];

        DistQueue queVertices = new DistQueue(graphhyyy.V());

        int number;
        int[] DISTANT = new int[graphhyyy.V()];
        DISTANT[START] = 0;


        int i = 0;
        if (i < graphhyyy.V()) {
            do {

                if (i != START) {
                    DISTANT[i] = Integer.MAX_VALUE; //approximate infinity
                }
                PREVIOUS[i] = -1;

                queVertices.insert(i, DISTANT[i]);
                i++;
            } while (i < graphhyyy.V());
        }

        int counter = 0;

        //isEmpty do while loop
        if (!queVertices.isEmpty()) {
            do {

                number = queVertices.delMin();

                for (int w : graphhyyy.adj(number)) {

                    if (queVertices.inQueue(w)) {

                        int track = DISTANT[number] + graphhyyy.getEdgeWeight(number, w);

                        if (track < DISTANT[w]) {
                            DISTANT[w] = track;

                            PREVIOUS[w] = number;

                            queVertices.set(w, track);
                        }
                    }//inqueue
                }//w:
            } while (!queVertices.isEmpty());
            if (PREVIOUS[ENDDESU] != -1) {
                do {
                    counter++;
                    ENDDESU = PREVIOUS[ENDDESU];
                } while (PREVIOUS[ENDDESU] != -1);
            }
        } else {
            if (PREVIOUS[ENDDESU] != -1) {
                do {
                    counter++;
                    ENDDESU = PREVIOUS[ENDDESU];
                } while (PREVIOUS[ENDDESU] != -1);
            }
        }

        //while loop for stop track

        return counter;
    }//OHYEEE

}//class




class Pair {
    public int start;
    public int end;
    public int stopC;

    public Pair(int start, int end, int Count) {
        this.start = start;
        this.end = end;
        this.stopC = Count;
    }

    public static ArrayList<Pair> df(Graph graphhhyy) {

        ArrayList<Pair> para = new ArrayList<>();

        int i = 0;
        while (i < graphhhyy.V()) {

            for (int j = 0; j < graphhhyy.V(); j++) {

                boolean nodes[] = new boolean[graphhhyy.V()];

                int HOYAA = RegNet.OHYEEE(graphhhyy, i, j);// AM I SURE ?

                Pair s = new Pair(i, j, HOYAA);//   BECAREFUL MYSELF!

                s.start = i;
                s.end = j;
                s.stopC = HOYAA - 1;
                para.add(s);
            }//for loop j
            i++;
        }//while loop

        return para;
    }

    public static Pair mx(ArrayList<Pair> mxArray, Graph grapphyy) {

        Pair maximum = mxArray.get(0);

        int i = 0;
        if (i < mxArray.size()) {
            do {

                if (mxArray.get(i).stopC > maximum.stopC || (mxArray.get(i).stopC == maximum.stopC && grapphyy.getEdgeWeight(mxArray.get(i).start, mxArray.get(i).end) < grapphyy.getEdgeWeight(maximum.start, maximum.end))) {
                    maximum = mxArray.get(i);
                }
                i++;
            } while (i < mxArray.size());
        }
        mxArray.remove(maximum);
        return maximum;
    }

}