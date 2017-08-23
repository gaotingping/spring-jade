package net.paoding.rose.jade.dataaccess.strategy;

import net.paoding.rose.jade.dataaccess.DataSourceFactory;
import net.paoding.rose.jade.dataaccess.DataSourceHolder;
import net.paoding.rose.jade.dataaccess.ms.ListDataSourceFactory;

/**
 * 多数据源访问负载策略，目前只考虑mysql多从库的应用场景
 * 需要考虑其它人扩展的需要
 * 
 * @author gaotingping@cyberzone.cn
 */
public interface LoadBalance {
	
	public void init(DataSourceFactory master,ListDataSourceFactory slaves);

	public DataSourceHolder load();

	public void refresh(DataSourceFactory master, ListDataSourceFactory slaves);
}
