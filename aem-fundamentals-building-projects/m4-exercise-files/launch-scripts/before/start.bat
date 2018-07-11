@echo off
:: This script configures the start information for this server.
::
:: The following variables may be used to override the defaults.
:: For one-time overrides the variable can be set as part of the command-line; e.g.,
::
::     SET CQ_PORT=1234 & ./start.bat
::
setlocal

::* TCP port used for stop and status scripts
if not defined CQ_PORT set CQ_PORT=4502

::* hostname of the interface that this server should listen to
:: if not defined CQ_HOST set CQ_HOST=

::* runmode(s)
::* will not be used if repository is already present
if not defined CQ_RUNMODE set CQ_RUNMODE=author

::* name of the jarfile
:: if not defined CQ_JARFILE set CQ_JARFILE=

::* default JVM options
if not defined CQ_JVM_OPTS set CQ_JVM_OPTS=-Xmx1024m -XX:MaxPermSize=256M -Djava.awt.headless=true

::* ------------------------------------------------------------------------------
::* authentication
::* ------------------------------------------------------------------------------
::* when using oak (crx3) authentication must be configured using the
::* Apache Felix JAAS Configuration Factory service via the Web Console
::* see http://jackrabbit.apache.org/oak/docs/security/authentication/externalloginmodule.html

::* use jaas.config (legacy: only used for crx2 persistence)
:: if not defined CQ_USE_JAAS set CQ_USE_JAAS=true

::* config for jaas (legacy: only used for crx2 persistence)
if not defined CQ_JAAS_CONFIG set CQ_JAAS_CONFIG=etc\jaas.config

::* ------------------------------------------------------------------------------
::* persistence mode
::* ------------------------------------------------------------------------------
::* the persistence mode can not be switched for an existing repository
set CQ_RUNMODE=%CQ_RUNMODE%,crx3,crx3tar
:: set CQ_RUNMODE=%CQ_RUNMODE%,crx3,crx3mongo

::* settings for mongo db
:: if not defined CQ_MONGO_HOST set CQ_MONGO_HOST=127.0.0.1
:: if not defined CQ_MONGO_PORT set CQ_MONGO_PORT=27017
:: if not defined CQ_MONGO_DB   set CQ_MONGO_DB=aem6

::* ------------------------------------------------------------------------------
::* do not configure below this point
::* ------------------------------------------------------------------------------

chdir /D %~dp0
cd ..
        del conf\controlport
if not defined CQ_JARFILE     for %%X in (app\*.jar) do set CQ_JARFILE=%%X
for %%* in (.) do set CurrDirName=%%~n*
cd ..

set START_OPTS=start -c %CurrDirName% -i launchpad
if defined CQ_PORT            set START_OPTS=%START_OPTS% -p %CQ_PORT%
if defined CQ_RUNMODE         set CQ_JVM_OPTS=%CQ_JVM_OPTS% -Dsling.run.modes=%CQ_RUNMODE%
if defined CQ_HOST            set CQ_JVM_OPTS=%CQ_JVM_OPTS% -Dorg.apache.felix.http.host=%CQ_HOST%
if defined CQ_HOST            set START_OPTS=%START_OPTS% -a %CQ_HOST%
if defined CQ_MONGO_HOST      set START_OPTS=%START_OPTS% -Doak.mongo.host=%CQ_MONGO_HOST%
if defined CQ_MONGO_PORT      set START_OPTS=%START_OPTS% -Doak.mongo.port=%CQ_MONGO_PORT%
if defined CQ_MONGO_DB        set START_OPTS=%START_OPTS% -Doak.mongo.db=%CQ_MONGO_DB%
if defined CQ_USE_JAAS        set CQ_JVM_OPTS=%CQ_JVM_OPTS% -Djava.security.auth.login.config=%CQ_JAAS_CONFIG%
set START_OPTS=%START_OPTS% -Dsling.properties=conf/sling.properties

tasklist > oldTaskList.txt
start "CQ" cmd.exe /K java %CQ_JVM_OPTS% -jar %CurrDirName%\%CQ_JARFILE% %START_OPTS%
tasklist > newTaskList.txt
java -cp %~dp0 GetProcessID oldTaskList.txt newTaskList.txt java.exe > %CurrDirName%\conf\cq.pid
del newTaskList.txt
del oldTaskList.txt