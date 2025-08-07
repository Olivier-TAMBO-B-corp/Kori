@echo off
set VERSION=%1

if "%VERSION%"=="" (
    echo Usage: maven_install.bat [version]
    exit /b 1
)

mvn install:install-file ^
 -Dfile=artifacts\com\ttt\api\TModeler-android\%VERSION%\TModeler-android-%VERSION%.aar ^
 -DgroupId=com.ttt.api ^
 -DartifactId=TModeler-android ^
 -Dversion=%VERSION% ^
 -Dpackaging=aar ^
 -DpomFile=artifacts\com\ttt\api\TModeler-android\%VERSION%\TModeler-android-%VERSION%.pom