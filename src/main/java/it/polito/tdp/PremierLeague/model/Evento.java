package it.polito.tdp.PremierLeague.model;

public class Evento implements Comparable<Evento>{
	private int tempo;
	private EventType tipo;
	
	public enum EventType {
		INFORTUNIO,
		GOAL,
		ESPULSIONE
	}

	public Evento(int tempo, EventType tipo) {
		super();
		this.tempo = tempo;
		this.tipo = tipo;
	}

	public int getTempo() {
		return tempo;
	}

	public EventType getTipo() {
		return tipo;
	}

	@Override
	public int compareTo(Evento o) {
		// TODO Auto-generated method stub
		return tempo - o.getTempo();
	}
	
	
}
