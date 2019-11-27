
@echo off

set MIDP_HOME=C:\Users\jitao\_marlio\software\J2meDevKit\midp2.0fcs
set CLASSPATH=.;.\classes;

%MIDP_HOME%\bin\midp -classpath %CLASSPATH% org.migame.airfighter.midlet.AirFighterMIDlet

pause
