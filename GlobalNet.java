import java.util.ArrayList;

public class GlobalNet
{
    //creates a global network
    //O : the original graph
    //regions: the regional graphs
    public static Graph run(Graph O, Graph[] regions)
    {
        Graph newGraph = new Graph(O.V());
        newGraph.setCodes(O.getCodes());
//
//        ArrayList<Edge> HAICAPTAIN = new ArrayList<Edge>();
//
//        for(int i = 0; i < regions.length; i++) {
//             HAICAPTAIN = regions[i].edges();
//        }
//        for(int j = 0; j < HAICAPTAIN.size(); j++) {
//            newGraph.addEdge(HAICAPTAIN.get(j));
//        }// HEREEEEEEEEE


        int room = 0;

        {
            int i = 0;
            while (i < regions.length) {
                for (Edge e: regions[i].edges()) {
                    newGraph.addEdge(e);
                }
                i++;
            }
        }



        while (room < regions.length) {
            Graph G = O.subgraph(O.getCodes());

            ArrayList<Edge> edges = regions[room].edges();
            {
                int i = 0, edgesSize = edges.size();
                while (i < edgesSize) {
                    Edge e = edges.get(i);
                    G.removeEdge(e);
                    G = G.connGraph();
                    G.setCodes(O.getCodes());
                    G.addEdge(e.u, e.v, 0);
                    i++;
                }
            }
            int sikdir = G.index(regions[room].getCode(0));

            int[][] xiyar = dik(G, sikdir);

            int[] DISTANT = xiyar[0];
            int[] PRAVIOUS = xiyar[1];

            int i = 0;
            while (i < regions.length) {
                int sd = G.index(regions[i].getCode(0));
                for (int j = regions[i].V() - 1; j >= 0; j--) {
                    if (DISTANT[sd] > DISTANT[G.index(regions[i].getCode(j))])
                        sd = G.index(regions[i].getCode(j));
                }

                if (PRAVIOUS[sd] != -1) {
                    do {
                        Edge pop = O.getEdge(sd, PRAVIOUS[sd]);
                        newGraph.addEdge(pop);
                        sd = PRAVIOUS[sd];
                    } while (PRAVIOUS[sd] != -1);
                }

                i++;
            }
            room++;
        }

        //TODO
        return newGraph.connGraph();
    }

    public static int[][] dik(Graph grapphyy, int SAIK) {

        DistQueue Q = new DistQueue(grapphyy.V());
        int[] DISTANT = new int[grapphyy.V()];
        int[] PRAVOUS = new int[grapphyy.V()];
        int ghk;

        DISTANT[SAIK] = 0;
        for (int i = 0; i < grapphyy.V(); i++) {
            if (i != SAIK)
                DISTANT[i] = 100000000; //approximate infinity
            PRAVOUS[i] = -1;
            Q.insert(i, DISTANT[i]);
        }

        while (!Q.isEmpty()) {
            ghk = Q.delMin();
            ArrayList<Integer> adj = grapphyy.adj(ghk);
            for (int i = 0; i < adj.size(); i++) {
                int w = adj.get(i);
                if (Q.inQueue(w)) {
                    int d = DISTANT[ghk] + grapphyy.getEdgeWeight(ghk, w);
                    if (d < DISTANT[w]) {
                        DISTANT[w] = d;
                        PRAVOUS[w] = ghk;
                        Q.set(w, d);
                    }
                }
            }
        }

        int[][] resultat = new int[2][grapphyy.V()];

        resultat[0] = DISTANT;
        resultat[1] = PRAVOUS;

        return resultat;
    }
//    public static void sasageyo(Graph Graph[] regions) {
//
//    }
//
//

}


    
    