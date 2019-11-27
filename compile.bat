
@echo off

set JAVA_HOME=C:\Users\jitao\_marlio\software\jdk\jdk1.4
set MIDP_HOME=C:\Users\jitao\_marlio\software\J2meDevKit\midp2.0fcs
set CLASSPATH=.;.\src;.\classes;.\resource\classpath;%MIDP_HOME%\classes

echo Compiling all the source code ... 

%JAVA_HOME%\bin\javac -classpath %CLASSPATH% -d .\classes src/org/migame/airfighter/kernal/common/AppletConstant.java
%JAVA_HOME%\bin\javac -classpath %CLASSPATH% -d .\classes src/org/migame/airfighter/kernal/util/DataStorageHelper.java
%JAVA_HOME%\bin\javac -classpath %CLASSPATH% -d .\classes src/org/migame/airfighter/kernal/model/map/GameBackgroundMap.java
%JAVA_HOME%\bin\javac -classpath %CLASSPATH% -d .\classes src/org/migame/airfighter/kernal/model/bullet/Bullet.java
%JAVA_HOME%\bin\javac -classpath %CLASSPATH% -d .\classes src/org/migame/airfighter/kernal/model/bullet/EnemyStraight01Bullet.java
%JAVA_HOME%\bin\javac -classpath %CLASSPATH% -d .\classes src/org/migame/airfighter/kernal/model/bullet/MachineGunFire.java
%JAVA_HOME%\bin\javac -classpath %CLASSPATH% -d .\classes src/org/migame/airfighter/kernal/model/bullet/PlayerBulletT0.java
%JAVA_HOME%\bin\javac -classpath %CLASSPATH% -d .\classes src/org/migame/airfighter/kernal/model/bullet/PlayerBulletT1.java
%JAVA_HOME%\bin\javac -classpath %CLASSPATH% -d .\classes src/org/migame/airfighter/kernal/model/bullet/PlayerTraceMissileBullet.java
%JAVA_HOME%\bin\javac -classpath %CLASSPATH% -d .\classes src/org/migame/airfighter/kernal/model/bullet/TraceMissile.java
%JAVA_HOME%\bin\javac -classpath %CLASSPATH% -d .\classes src/org/migame/airfighter/kernal/model/craft/AbstractEnemyAircraft.java
%JAVA_HOME%\bin\javac -classpath %CLASSPATH% -d .\classes src/org/migame/airfighter/kernal/model/craft/Aircraft.java
%JAVA_HOME%\bin\javac -classpath %CLASSPATH% -d .\classes src/org/migame/airfighter/kernal/model/craft/Enemy01Aircraft.java
%JAVA_HOME%\bin\javac -classpath %CLASSPATH% -d .\classes src/org/migame/airfighter/kernal/model/craft/EnemyBossAircraft.java
%JAVA_HOME%\bin\javac -classpath %CLASSPATH% -d .\classes src/org/migame/airfighter/kernal/model/craft/PlayerAircraft.java
%JAVA_HOME%\bin\javac -classpath %CLASSPATH% -d .\classes src/org/migame/airfighter/kernal/core/AirFighterEngine.java
%JAVA_HOME%\bin\javac -classpath %CLASSPATH% -d .\classes src/org/migame/airfighter/midlet/AirFighterMIDlet.java

echo Compilation completed!

echo Pre-verify the imported class liberary: MathFP ...
%MIDP_HOME%\bin\preverify -d .\classes -classpath %CLASSPATH% net/jscience/math/kvm/MathFP

echo Pre-verify all the necessary classes in project ... 
%MIDP_HOME%\bin\preverify -d .\classes -classpath %CLASSPATH% org/migame/airfighter/kernal/common/AppletConstant
%MIDP_HOME%\bin\preverify -d .\classes -classpath %CLASSPATH% org/migame/airfighter/kernal/util/DataStorageHelper
%MIDP_HOME%\bin\preverify -d .\classes -classpath %CLASSPATH% org/migame/airfighter/kernal/model/map/GameBackgroundMap
%MIDP_HOME%\bin\preverify -d .\classes -classpath %CLASSPATH% org/migame/airfighter/kernal/model/bullet/Bullet
%MIDP_HOME%\bin\preverify -d .\classes -classpath %CLASSPATH% org/migame/airfighter/kernal/model/bullet/EnemyStraight01Bullet
%MIDP_HOME%\bin\preverify -d .\classes -classpath %CLASSPATH% org/migame/airfighter/kernal/model/bullet/MachineGunFire
%MIDP_HOME%\bin\preverify -d .\classes -classpath %CLASSPATH% org/migame/airfighter/kernal/model/bullet/PlayerBulletT0
%MIDP_HOME%\bin\preverify -d .\classes -classpath %CLASSPATH% org/migame/airfighter/kernal/model/bullet/PlayerBulletT1
%MIDP_HOME%\bin\preverify -d .\classes -classpath %CLASSPATH% org/migame/airfighter/kernal/model/bullet/PlayerTraceMissileBullet
%MIDP_HOME%\bin\preverify -d .\classes -classpath %CLASSPATH% org/migame/airfighter/kernal/model/bullet/TraceMissile
%MIDP_HOME%\bin\preverify -d .\classes -classpath %CLASSPATH% org/migame/airfighter/kernal/model/craft/AbstractEnemyAircraft
%MIDP_HOME%\bin\preverify -d .\classes -classpath %CLASSPATH% org/migame/airfighter/kernal/model/craft/Aircraft
%MIDP_HOME%\bin\preverify -d .\classes -classpath %CLASSPATH% org/migame/airfighter/kernal/model/craft/Enemy01Aircraft
%MIDP_HOME%\bin\preverify -d .\classes -classpath %CLASSPATH% org/migame/airfighter/kernal/model/craft/EnemyBossAircraft
%MIDP_HOME%\bin\preverify -d .\classes -classpath %CLASSPATH% org/migame/airfighter/kernal/model/craft/PlayerAircraft
%MIDP_HOME%\bin\preverify -d .\classes -classpath %CLASSPATH% org/migame/airfighter/kernal/core/AirFighterEngine
%MIDP_HOME%\bin\preverify -d .\classes -classpath %CLASSPATH% org/migame/airfighter/midlet/AirFighterMIDlet

echo Preverification completed!

pause
