
@echo off

set MIDP_HOME=C:\Users\MLIU22\_Jitao\software\libs\midp2.0fcs
set CLASSPATH=.;.\classes;

%MIDP_HOME%\bin\midp -classpath %CLASSPATH% org.migame.airfighter.midlet.AirFighterMIDlet

pause
