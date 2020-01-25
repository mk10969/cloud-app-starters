### On windows server
 - Install
   - java 8 32bit
   - JvLink
   - OpenSSH
 
 - environment
   - 受信port開放 8080
   - 送信port開放 27017
  
 - app service化
   - winsw[https://github.com/kohsuke/winsw]
   
 - OpenSSH service化
   - 管理者でPowerShell login
   - $ Start-Service sshd
   - $ Set-Service -Name sshd -StartupType 'Automatic'
 


 - ssh接続
```
ssh Administrator@192.168.56.104
```

 - 手動起動
```
java 
 -Xms1024M ^
 -Xmx1024M ^
 -XX:+UseG1GC ^
 -XX:InitiatingHeapOccupancyPercent=40 ^
 -Xloggc:./log/gc.log -XX:+PrintGCDetails ^
 -XX:+PrintGCTimeStamps -jar uma-feed-app.jar ^
 --spring.profiles.active=prd,setup
```

```
java ^
 -XX:+UseConcMarkSweepGC ^
 -XX:+UseParNewGC ^
 -XX:+CMSParallelRemarkEnabled ^
 -XX:+CMSClassUnloadingEnabled ^
 -XX:+UseCMSInitiatingOccupancyOnly ^
 -XX:CMSInitiatingOccupancyFraction=40 ^
 -Xms512M ^
 -Xmx512M ^
 -Xmn256M ^
 -XX:NewRatio=2 ^
 -XX:SurvivorRatio=8 ^
 -XX:MaxTenuringThreshold=15 ^
 -XX:TargetSurvivorRatio=90 ^
 -Xloggc:./log/gc.log ^
 -XX:+PrintGCDetails ^
 -XX:+PrintGCTimeStamps ^
 -jar uma-feed-app.jar ^
 -Dcom.jacob.debug=true ^
 --spring.profiles.active=prd,setup
```


要約すると、
・32bit Windowsだと搭載メモリの1/4が最大値の限度。
・64bit Windowsだとこの縛りはなく、搭載メモリに依存。

 
 - jstat
 ```
jstat -gcutil -t <PID> 1000
```

