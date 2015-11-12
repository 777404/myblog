@echo off
setLocal EnableDelayedExpansion

set JAVA_OUT=generated\java

rmdir /S /Q %JAVA_OUT%
mkdir %JAVA_OUT%

set PROTOC_CMD=protoc-2.6.1-win32\protoc -I=protos --java_out=%JAVA_OUT%
for /D %%f in (protos/*) do (
  set PROTOC_CMD=!PROTOC_CMD! protos\%%f\*.proto
)

echo %PROTOC_CMD%
call %PROTOC_CMD%

pause