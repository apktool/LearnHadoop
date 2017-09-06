
HiveServer2 (introduced in Hive 0.11) has its own CLI called Beeline.  HiveCLI is now deprecated in favor of Beeline, as it lacks the multi-user, security, and other capabilities of HiveServer2.  To run HiveServer2 and Beeline from shell:

# 使用derby metastore

## 启动hiveserver

```
> hive
> schematool -dbType derby -initSchema
>> hiveserver2
```

## 启动hiveclient

```
> beeline -u jdbc:hive2://localhost:10000/default -n hadoop -p hive
```

## hive-site.xml

```xml
<configuration>
	<property>
		<name>javax.jdo.option.ConnectionURL</name>
		<value>jdbc:derby:;databaseName=/home/hadoop/App/data/metastore_db;create=true</value>
		<description>JDBC connect string for a JDBC metastore</description>
	</property>
	<property>
		<name>javax.jdo.option.ConnectionUserName</name>
		<value>hadoop</value>
		<description>Username to use against metastore database</description>
	</property>
	<property>
		<name>javax.jdo.option.ConnectionPassword</name>
		<value>hive</value>
		<description>password to use against metastore database</description>
	</property>
	<property>
		<name>hive.server2.thrift.http.port</name>
		<value>10000</value>
		<description>Port number of HiveServer2 Thrift interface when hive.server2.transport.mode is 'http'.</description>
	</property>
</configuration>
```

# mysql metastore

## 启动hiveclient

```sbtshell
beeline -u jdbc:mysql://localhost:3306/metastore?useSSL=false -n hiveuser -p hivepassword
```
## hive-site.xml

```xml
<configuration>
<property>
		<name>javax.jdo.option.ConnectionURL</name>
		<value>jdbc:mysql://localhost/metastore?useSSL=false;createDatabaseIfNotExist=true</value>
		<description>metadata is stored in a MySQL server</description>
	</property>
	<property>
		<name>javax.jdo.option.ConnectionDriverName</name>
		<value>com.mysql.cj.jdbc.Driver</value>
		<description>MySQL JDBC driver class</description>
	</property>
	<property>
		<name>javax.jdo.option.ConnectionUserName</name>
		<value>hiveuser</value>
		<description>Username to use against metastore database</description>
	</property>
	<property>
		<name>javax.jdo.option.ConnectionPassword</name>
		<value>hivepassword</value>
		<description>password to use against metastore database</description>
	</property>
	<property>
		<name>hive.server2.thrift.http.port</name>
		<value>10000</value>
		<description>Port number of HiveServer2 Thrift interface when hive.server2.transport.mode is 'http'.</description>
	</property>
	<property>
		<name>datanucleus.transactionIsolation</name>
		<value>repeatable-read</value>
	</property>
</configuration>
```

---

# Reference

[Configuring the Hive Metastore](https://www.cloudera.com/documentation/enterprise/latest/topics/cdh_ig_hive_metastore_configure.html)

[How to Configure MySQL Metastore for Hive?](https://dzone.com/articles/how-configure-mysql-metastore)
