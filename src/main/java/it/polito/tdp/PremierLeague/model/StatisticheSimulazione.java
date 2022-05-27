package it.polito.tdp.PremierLeague.model;

public class StatisticheSimulazione {
	private int golSqua1;
	private int golSqu2;
	private int espulsiSq1;
	private int espulsiSq2;
	public StatisticheSimulazione() {
		super();
		this.golSqua1 = 0;
		this.golSqu2 = 0;
		this.espulsiSq1 = 0;
		this.espulsiSq2 = 0;
	}
	public int getGolSqua1() {
		return golSqua1;
	}
	public void incrementaGolSqua1() {
		this.golSqua1++;
	}
	public int getGolSqu2() {
		return golSqu2;
	}
	public void incrementaGolSqu2() {
		this.golSqu2++;
	}
	public int getEspulsiSq1() {
		return espulsiSq1;
	}
	public void incrementaEspulsiSq1() {
		this.espulsiSq1++;
	}
	public int getEspulsiSq2() {
		return espulsiSq2;
	}
	public void incrementaEspulsiSq2() {
		this.espulsiSq2++;
	}
	@Override
	public String toString() {
		return "StatisticheSimulazione [golSqua1=" + golSqua1 + ", golSqu2=" + golSqu2 + ", espulsiSq1=" + espulsiSq1
				+ ", espulsiSq2=" + espulsiSq2 + "]";
	}
	
	
	
	
}
