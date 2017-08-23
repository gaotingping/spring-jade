package net.paoding.rose.jade.dataaccess.ms;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.sql.DataSource;

import net.paoding.rose.jade.dataaccess.DataSourceFactory;
import net.paoding.rose.jade.dataaccess.DataSourceHolder;
import net.paoding.rose.jade.statement.StatementMetaData;

/**
 * 从预设的一系列的DataSource随机提供一个，在m/s或多数据源情况下使用
 * 
 */
public class ListDataSourceFactory implements DataSourceFactory {

	private Random random = new Random();

	private List<DataSourceHolder> dataSources = new CopyOnWriteArrayList<>();

	private List<DataSourceHolder> downSlaves = new CopyOnWriteArrayList<>();

	public void addDataSource(DataSource dataSource) {
		if (this.dataSources.size() == 0) {
			this.dataSources = new ArrayList<DataSourceHolder>(dataSources);
		}
		this.dataSources.add(new DataSourceHolder(dataSource));
	}

	public ListDataSourceFactory() {

	}

	public ListDataSourceFactory(List<DataSource> dataSources) {
		this.setDataSources(dataSources);
	}

	public void setDataSources(List<DataSource> dataSources) {
		this.dataSources = new ArrayList<DataSourceHolder>(dataSources.size());
		for (DataSource dataSource : dataSources) {
			this.dataSources.add(new DataSourceHolder(dataSource));
		}
	}

	@Override
	public DataSourceHolder getHolder(StatementMetaData metaData, Map<String, Object> runtimeProperties) {

		if (dataSources.size() == 0) {
			return null;
		}
		int index = random.nextInt(dataSources.size()); // 0.. size

		return dataSources.get(index);
	}

	// 以下方法自己折腾的
	public List<DataSourceHolder> getDataSources() {
		return dataSources;
	}

	public void addDataSourceHolder(int index) {
		dataSources.add(downSlaves.get(index));
		downSlaves.remove(index);
	}

	public void removeDataSourceHolder(int index) {
		downSlaves.add(dataSources.get(index));
		dataSources.remove(index);
	}

	public List<DataSourceHolder> getDownSlaves() {
		return downSlaves;
	}
}
