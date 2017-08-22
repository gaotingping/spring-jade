package net.paoding.rose.jade.dataaccess.strategy;

/**
 * 数据源幸存者守护者
 * 
 * @author gaotingping@cyberzone.cn
 */
public interface SurviveGuardian {

	public void init();
	
	public void destroy();
}
