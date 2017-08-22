package net.paoding.rose.jade.dataaccess.strategy;

public class MsLoadConfig {

	private long delay = 5;

	private long period = 1;
	
	private int poolSize = 1;
	
	private String verifySql = "select 1";
	
	private boolean masterRead = true;

	public long getDelay() {
		return delay;
	}

	public void setDelay(long delay) {
		this.delay = delay;
	}

	public long getPeriod() {
		return period;
	}

	public void setPeriod(long period) {
		this.period = period;
	}

	public int getPoolSize() {
		return poolSize;
	}

	public void setPoolSize(int poolSize) {
		this.poolSize = poolSize;
	}

	public String getVerifySql() {
		return verifySql;
	}

	public void setVerifySql(String verifySql) {
		this.verifySql = verifySql;
	}

	public boolean isMasterRead() {
		return masterRead;
	}

	public void setMasterRead(boolean masterRead) {
		this.masterRead = masterRead;
	}
}
