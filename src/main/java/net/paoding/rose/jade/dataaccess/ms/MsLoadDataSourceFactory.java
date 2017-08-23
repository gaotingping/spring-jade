package net.paoding.rose.jade.dataaccess.ms;

import java.util.List;
import java.util.Map;

import net.paoding.rose.jade.annotation.SQLType;
import net.paoding.rose.jade.annotation.UseMaster;
import net.paoding.rose.jade.dataaccess.DataSourceFactory;
import net.paoding.rose.jade.dataaccess.DataSourceHolder;
import net.paoding.rose.jade.dataaccess.datasource.SimpleDataSourceFactory;
import net.paoding.rose.jade.dataaccess.strategy.FailureNotice;
import net.paoding.rose.jade.dataaccess.strategy.LoadBalance;
import net.paoding.rose.jade.statement.StatementMetaData;

/**
 * 支持负载均衡的数据源
 * 
 * @author gaotingping@cyberzone.cn
 */
public class MsLoadDataSourceFactory implements MsSourceFactory {

    private DataSourceFactory master = new SimpleDataSourceFactory();

    private ListDataSourceFactory slaves = new ListDataSourceFactory();
    
    private LoadBalance loadBalance;
    
    private FailureNotice failureNotice;

    public void setMaster(DataSourceFactory master) {
        this.master = master;
    }

    public void setSlaves(ListDataSourceFactory slaves) {
        this.slaves = slaves;
    }

    @Override
    public DataSourceHolder getHolder(StatementMetaData metaData,
            Map<String, Object> runtimeProperties) {
    	
    	UseMaster useMaster = metaData.getMethod().getAnnotation(UseMaster.class);
    	if(useMaster!=null){
    		
    		return master.getHolder(metaData, runtimeProperties);
    		
    	}else if (metaData.getSQLType() != SQLType.READ) {
    		
            return master.getHolder(metaData, runtimeProperties);
            
        } else {
        	
        	/*从库走负载*/
        	return loadBalance.load();
        }
}

	@Override
	public DataSourceHolder getMaster() {
		return master.getHolder(null,null);
	}

	@Override
	public List<DataSourceHolder> getSlaves() {
		return slaves.getDataSources();
	}
	
	@Override
	public List<DataSourceHolder> getDownSlaves() {
		return slaves.getDownSlaves();
	}

	@Override
	public void downSlave(int index){
		slaves.removeDataSourceHolder(index);
		failureNotice.notice();
		loadBalance.refresh(master, slaves);
	}
	
	@Override
	public void onLineSlave(int index) {
		slaves.addDataSourceHolder(index);
		loadBalance.refresh(master, slaves);
	}

	@Override
	public void init() {
		loadBalance.init(master, slaves);
	}

	@Override
	public void destroy() {
		//do ...
	}
}
