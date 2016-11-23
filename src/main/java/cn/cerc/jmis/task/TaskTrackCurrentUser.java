package cn.cerc.jmis.task;

import cn.cerc.jbean.other.SystemTable;
import cn.cerc.jdb.core.TDateTime;
import cn.cerc.jdb.mysql.SqlSession;

//清理在线用户记录表
public class TaskTrackCurrentUser extends AbstractTask {

	@SuppressWarnings("deprecation")
	@Override
	public void execute() {
		// 清理在线用户记录表
		SqlSession conn = (SqlSession) handle.getProperty(SqlSession.sessionId);
		// 删除超过100天的登录记录
		StringBuffer sql1 = new StringBuffer();
		sql1.append(String.format("DELETE FROM %s WHERE DATEDIFF(NOW(),LoginTime_)>100", SystemTable.getCurrentUser));
		conn.execute(sql1.toString());
		// 清除所有未正常登录的用户记录
		StringBuffer sql2 = new StringBuffer();
		sql2.append(String.format("UPDATE %s SET Viability_=-1 ", SystemTable.getCurrentUser));
		// 在线达24小时以上的用户
		sql2.append("WHERE (Viability_>0) and (");
		sql2.append("(HOUR(TIMEDIFF(NOW(),LoginTime_)) > 24 AND LogoutTime_ IS NULL)");
		// 在早上5点以后，清除昨天的用户
		if (TDateTime.Now().getData().getHours() > 5)
			sql2.append(" OR (DATEDIFF(NOW(),LoginTime_)=1)");
		// 已登出超过4小时的用户
		sql2.append(" OR (HOUR(TIMEDIFF(NOW(),LogoutTime_)))");
		sql2.append(")");
		conn.execute(sql2.toString());
	}
}
