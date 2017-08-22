package net.paoding.rose.jade.dataaccess.strategy;

/**
 * 检测到数据源失败后，通知
 * 
 * @author gaotingping@cyberzone.cn
 */
public interface FailureNotice {

	public void notice();
}
