package net.paoding.rose.jade.dataaccess.strategy.support;

import net.paoding.rose.jade.dataaccess.DataSourceFactory;
import net.paoding.rose.jade.dataaccess.DataSourceHolder;
import net.paoding.rose.jade.dataaccess.ms.ListDataSourceFactory;
import net.paoding.rose.jade.dataaccess.strategy.LoadBalance;

//hash 哈希
public abstract class AbstractBalance implements LoadBalance{
	
	public void init(DataSourceFactory master,ListDataSourceFactory slaves){
		
	}

	public DataSourceHolder load(){
		return null;
	}
	
	@Override
	public void refresh(DataSourceFactory master, ListDataSourceFactory slaves) {
		
	}
}
