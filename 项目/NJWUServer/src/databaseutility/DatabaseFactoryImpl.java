package databaseutility;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JOptionPane;

import data.broadcastdata.BroadCastData;
import data.choosedata.SelectRecordData;
import data.framedata.ModuleData;
import data.framedata.TypeData;
import data.institutedata.InstituteData;
import data.lessondata.LessonUnData;
import data.lessonrecorddata.LessonRecordData;
import data.plandata.LessonAbData;
import data.systemdata.SystemData;
import data.userdata.StudentData;
import data.userdata.TeacherData;
import dataservice.DatabaseService;
import dataservice.Table;
/**
 * 
 * @author luck
 * @version 1.0
 * @date 13.10.15
 * 对抽象工厂的实现，生成各个Data的实例，负责数据库操作。
 */
public class DatabaseFactoryImpl extends UnicastRemoteObject implements DatabaseFactory{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	DatabaseService databaseService;
	public DatabaseFactoryImpl() throws RemoteException {
		super();
		getLink();
		try {
			databaseService = new BroadCastData(conn);
			Naming.rebind(Table.broadcast.toString(), databaseService);
			databaseService = new StudentData(conn);
			Naming.rebind(Table.student.toString(),databaseService);
			databaseService = new TeacherData(conn);
			Naming.rebind(Table.teacher.toString(),databaseService);
			databaseService = new LessonAbData(conn);
			Naming.rebind(Table.lesson_abstract.toString(),databaseService);
			databaseService = new LessonRecordData(conn);
			Naming.rebind(Table.lesson_record.toString(),databaseService);
			databaseService = new LessonUnData(conn);
			Naming.rebind(Table.Lesson_unique.toString(),databaseService);
			databaseService = new SelectRecordData(conn);
			Naming.rebind(Table.select_record.toString(),databaseService);
			databaseService = new ModuleData(conn);
			Naming.rebind(Table.module.toString(),databaseService);
			databaseService = new TypeData(conn);
			Naming.rebind(Table.type.toString(),databaseService);
			databaseService = new SystemData(conn);
			Naming.rebind(Table.system.toString(), databaseService);
			databaseService = new InstituteData(conn);
			Naming.rebind(Table.institute.toString(),databaseService);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
     * 驱动地址
     */
    public String driver = "com.mysql.jdbc.Driver";
    /**
     * 用户名
     */
    final String username = "excalibur";
    /**
     * 密码
     */
    final String password = "njuexcalibur";
    /**
     * 引擎
     */
    final String engin = "ENGINE=InnoDB";
    /**
     * 字符编码
     */
    final String charset = "DEFAULT CHARSET=gbk";
    /**
     * 定义数据库操作对象
     */
     Statement st;
     PreparedStatement PS;
     ResultSet RS;
     Connection conn = null;
     int mark=0;
	@Override
	public String getDataBase(Table table) throws RemoteException {
		return table.toString();
	}
	
	public void getLink(){
		try {
//            String url = "jdbc:mysql://db4free.net:3306/njwu?useUnicode=true&characterEncoding=gbk";
            String url = "jdbc:mysql://localhost:8889/njwu?useUnicode=true&characterEncoding=gbk";

            Class.forName(driver).newInstance();
//            conn = DriverManager.getConnection(url, username, password);
            
            conn = DriverManager.getConnection(url, Constant.userName, Constant.passWord);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "请确认您的数据库配置完全");
        }
	}
}
