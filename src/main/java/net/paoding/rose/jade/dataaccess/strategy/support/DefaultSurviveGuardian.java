package net.paoding.rose.jade.dataaccess.strategy.support;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import net.paoding.rose.jade.dataaccess.DataSourceHolder;
import net.paoding.rose.jade.dataaccess.ms.MsSourceFactory;
import net.paoding.rose.jade.dataaccess.strategy.MsLoadConfig;
import net.paoding.rose.jade.dataaccess.strategy.SurviveGuardian;

/**
 * 只负责侦听，不处理错误
 */
public class DefaultSurviveGuardian implements SurviveGuardian {

	private ScheduledExecutorService onLineSchedule = null;

	private ScheduledExecutorService downSchedule = null;

	private MsSourceFactory msSourceFactory;

	private MsLoadConfig msLoadConfig;

	@Override
	public void init() {

		// 扫在线的
		onLineSchedule = Executors.newScheduledThreadPool(msLoadConfig.getPoolSize());

		onLineSchedule.scheduleAtFixedRate(new Runnable() {

			@Override
			public void run() {

				List<DataSourceHolder> dsList = msSourceFactory.getSlaves();

				if (dsList == null || dsList.isEmpty()) {
					return;
				}

				//一次就处理一个
				int index=-1;

				for (int i = 0; i < dsList.size(); i++) {
					DataSourceHolder ds = dsList.get(i);
					if (!verifyDataSource(ds)) {
						index=i;
					}
				}

				dsList = null;

				if (index >0) {
					msSourceFactory.downSlave(index);
				}
			}

		}, msLoadConfig.getDelay(), msLoadConfig.getPeriod(), TimeUnit.SECONDS);

		// 扫离线的
		downSchedule = Executors.newScheduledThreadPool(msLoadConfig.getPoolSize());

		downSchedule.scheduleAtFixedRate(new Runnable() {

			@Override
			public void run() {

				List<DataSourceHolder> dsList = msSourceFactory.getDownSlaves();
				if (dsList == null || dsList.isEmpty()) {
					return;
				}
				
				//一次就处理一个
				int index=-1;
				for (int i = 0; i < dsList.size(); i++) {
					DataSourceHolder ds = dsList.get(i);
					if (verifyDataSource(ds)) {
						index=i;
						break;
					}
				}
				
				if(index>0){
					msSourceFactory.onLineSlave(index);
				}
			}

		}, msLoadConfig.getDelay(), msLoadConfig.getPeriod(), TimeUnit.SECONDS);
	}

	private boolean verifyDataSource(DataSourceHolder ds) {
		Connection conn = null;
		try {

			conn = ds.getDataSource().getConnection();
			PreparedStatement pst = conn.prepareStatement(msLoadConfig.getVerifySql());
			pst.execute();

			return true;

		} catch (SQLException e) {
			
			return false;

		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void destroy() {
		// 立即关闭
		if (onLineSchedule != null) {
			onLineSchedule.shutdownNow();
		}

		if (downSchedule != null) {
			downSchedule.shutdownNow();
		}
	}

	public MsSourceFactory getMsSourceFactory() {
		return msSourceFactory;
	}

	public void setMsSourceFactory(MsSourceFactory msSourceFactory) {
		this.msSourceFactory = msSourceFactory;
	}
}
