package it.polito.tdp.PremierLeague.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	
	private PremierLeagueDAO dao;
	private Graph<Player, DefaultWeightedEdge> grafo;
	private Map<Integer, Player> idMap;
	private double migliore;
	private Player migliorGiocatore;
	
	public Model() {
		dao = new PremierLeagueDAO();
		idMap = new HashMap<Integer, Player>();
	}
	
	
	public void creaGrafo(Match m) {
		grafo = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		
		dao.listAllPlayersInMatch(m, idMap);
		System.out.println(idMap.size());
		
		Graphs.addAllVertices(grafo, idMap.values());
		
		for(Stats s : dao.getStatisticheGiocatore(m, idMap)) {
			if(s.getPeso()>=0) {
				Graphs.addEdge(grafo, s.getP1(), s.getP2(), s.getPeso());
			} else {
				Graphs.addEdge(grafo, s.getP2(), s.getP1(), -s.getPeso());
			}
		}
		
		System.out.println("Numero vertici = "+grafo.vertexSet().size());
		System.out.println("Numero archi = "+grafo.edgeSet().size());

	}
	
	private Player getGiocatoreMigliorePriv() {
		int i=0;
		migliore = 0;
		migliorGiocatore = null;
		
		for(Player p : grafo.vertexSet()) {
			double effOut = 0;
			for(DefaultWeightedEdge e : grafo.outgoingEdgesOf(p)) {
				effOut += grafo.getEdgeWeight(e);
			}
			System.out.println("L'efficienza in uscita del giocatore "+p+" è: "+effOut);
			
			double effIn = 0;
			for(DefaultWeightedEdge e : grafo.incomingEdgesOf(p)) {
				effIn += grafo.getEdgeWeight(e);
			}
			System.out.println("L'efficienza in entrata del giocatore "+p+" è: "+effIn);
			
			double delta = effOut - effIn;
			if(i==0 || delta>migliore) {
				migliore = delta;
				migliorGiocatore = p;
				i++;
			}
		}
		
		return migliorGiocatore;
	}


	public double getMigliore() {
		return migliore;
	}

	
	public List<Match> getAllMatches(){
		return dao.listAllMatches();
	}
	
	public Player getGiocatoreMigliore() {
		if(migliorGiocatore==null) {
			migliorGiocatore = this.getGiocatoreMigliorePriv();
		}
		
		return migliorGiocatore;
	}
	
	public StatisticheSimulazione simula(int numeroEventi, Match m) {
		Simulatore s = new Simulatore(numeroEventi, m, migliorGiocatore);
		s.init();
		s.run();
		return s.getStats();
	}
}
