package net.paoding.rose.jade.dataaccess.ms;

import java.util.List;

import net.paoding.rose.jade.dataaccess.DataSourceFactory;
import net.paoding.rose.jade.dataaccess.DataSourceHolder;

/**
 * 主从数据源
 * 
 * @author gaotingping@cyberzone.cn
 */
public interface MsSourceFactory extends DataSourceFactory {
	
	public void init();
	
	public void destroy();
	
	/**
	 * 获得主库
	 */
	public DataSourceHolder getMaster();
	
	/**
	 * 获得从库
	 */
	public List<DataSourceHolder> getSlaves();

	/**
	 * 数据源挂了
	 */
	public void downSlave(int index);
	
	/**
	 * 数据源上线了
	 */
	public void onLineSlave(int index);

	/**
	 * 获得所有下线的从库
	 */
	public List<DataSourceHolder> getDownSlaves();
}
