package net.paoding.rose.jade.dataaccess.datasource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.sql.DataSource;

import net.paoding.rose.jade.dataaccess.DataSourceFactory;
import net.paoding.rose.jade.dataaccess.DataSourceHolder;
import net.paoding.rose.jade.statement.StatementMetaData;

/**
 * 从预设的一系列的DataSource随机提供一个，在m/s或多数据源情况下使用
 * 
 */
public class RandomDataSourceFactory implements DataSourceFactory {

    private Random random = new Random();

    private List<DataSourceHolder> dataSources = new ArrayList<>(10);
    
    private List<DataSourceHolder> downSlaves = new ArrayList<>(10);

    public RandomDataSourceFactory() {
    	
    }

    public void addDataSource(DataSource dataSource) {
        if (this.dataSources.size() == 0) {
            this.dataSources = new ArrayList<DataSourceHolder>(dataSources);
        }
        this.dataSources.add(new DataSourceHolder(dataSource));
    }

    public RandomDataSourceFactory(List<DataSource> dataSources) {
        this.setDataSources(dataSources);
    }

    public void setDataSources(List<DataSource> dataSources) {
        this.dataSources = new ArrayList<DataSourceHolder>(dataSources.size());
        for (DataSource dataSource : dataSources) {
            this.dataSources.add(new DataSourceHolder(dataSource));
        }
    }

    @Override
    public DataSourceHolder getHolder(StatementMetaData metaData,
            Map<String, Object> runtimeProperties) {
    	
        if (dataSources.size() == 0) {
            return null;
        }
        int index = random.nextInt(dataSources.size()); // 0.. size
        
        return dataSources.get(index);
    }

    //以下方法自己折腾的
	public List<DataSourceHolder> getDataSources() {
		return dataSources;
	}

	public void addDataSourceHolder(int index) {
		 //FIXME 并发
		 dataSources.add(downSlaves.get(index));
		 downSlaves.remove(index);
	}

	public void removeDataSourceHolder(int index) {
		//FIXME 并发
		downSlaves.add(dataSources.get(index));
		dataSources.remove(index);
	}

	public List<DataSourceHolder> getDownSlaves() {
		return downSlaves;
	}
}
