package it.polito.tdp.PremierLeague.model;

import java.util.PriorityQueue;

import it.polito.tdp.PremierLeague.model.Evento.EventType;

public class Simulatore {
	//Parametri
	private int NUMERO_EVENTI;
	private Match PARTITA;
	private int IDTEAM1;
	private int IDTEAM2;
	private Player migliore;
	
	//Coda di eventi
	private PriorityQueue<Evento> coda;
	
	//Stato del mondo
	private int tempo;
	private int numGiocatoriSq1;
	private int numGiocatoriSq2;

	//Output
	private StatisticheSimulazione st;

	public Simulatore(int nUMERO_EVENTI, Match pARTITA, Player migliore) {
		super();
		NUMERO_EVENTI = nUMERO_EVENTI;
		PARTITA = pARTITA;
		IDTEAM2 = PARTITA.getTeamAwayID();
		IDTEAM1 = PARTITA.getTeamHomeID();
		this.migliore=migliore;
	}
	
	public void init() {
		tempo = 0;
		numGiocatoriSq1 = 11;
		numGiocatoriSq2 = 11;
		coda = new PriorityQueue<>();
		st = new StatisticheSimulazione();
		
		for(int i=0; i<NUMERO_EVENTI; i++) {
			double prob = Math.random()*100;
			Evento e;
			
			if(prob>=50) {
				//goal
				e = new Evento(tempo++, EventType.GOAL);
			} else if(prob>=20 && prob<50) {
				//espulsione
				e = new Evento(tempo++, EventType.ESPULSIONE);
			} else {
				//infortunio
				e = new Evento(tempo++, EventType.INFORTUNIO);
			}
			
			coda.add(e);
		}
	}
	
	public void run() {
		while(!coda.isEmpty()) {
			Evento e = coda.poll();
			processaEvento(e);
		}
	}

	private void processaEvento(Evento e) {
		switch(e.getTipo()) {
		case GOAL:
			if(numGiocatoriSq1>numGiocatoriSq2) {
				//segna la squadra 1
				st.incrementaGolSqua1();
			} else if(numGiocatoriSq1<numGiocatoriSq2) {
				//segna la squadra 2
				st.incrementaGolSqu2();
			} else {
				//stesso numero di giocatori
				if(migliore.getTeamID()==IDTEAM1) {
					st.incrementaGolSqua1();
				} else {
					st.incrementaGolSqu2();
				}
			}
			break;
		case ESPULSIONE:
			double prob = Math.random()*100;
			
			if(migliore.getTeamID()==IDTEAM1) {
				if(prob>=40) {
					numGiocatoriSq1--;
					st.incrementaEspulsiSq1();
				} else {
					numGiocatoriSq2--;
					st.incrementaEspulsiSq2();
				}
			} else {
				if(prob>=40) {
					numGiocatoriSq2--;
					st.incrementaEspulsiSq2();
				} else {
					numGiocatoriSq1--;
					st.incrementaEspulsiSq1();
				}
			}

			break;
		case INFORTUNIO:
			double pr = Math.random()*100;
			int azioniInPiu=0;
			
			if(pr<33) {
				azioniInPiu=1;
			} else if(pr>=33 && pr<66) {
				azioniInPiu=2;
			} else {
				azioniInPiu=3;
			}
			
			for(int i=0; i<azioniInPiu; i++) {
				double proba = Math.random()*100;
				
				Evento nuovoEvento;
				
				if(proba>=50) {
					//goal
					nuovoEvento = new Evento(tempo++, EventType.GOAL);
				} else if(proba>=20 && proba<50) {
					//espulsione
					nuovoEvento = new Evento(tempo++, EventType.ESPULSIONE);
				} else {
					//infortunio
					nuovoEvento = new Evento(tempo++, EventType.INFORTUNIO);
				}
				
				coda.add(nuovoEvento);
			}
			
			break;
		}
	}

	public StatisticheSimulazione getStats() {
		return st;
	}
	
}
