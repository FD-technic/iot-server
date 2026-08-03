@echo off
setlocal EnableExtensions

REM ============================================================
REM FD-Deploy
REM Generic deployment script for Spring Boot applications
REM ============================================================

echo.
echo ============================================
echo              FD - DEPLOY
echo ============================================
echo.

REM ------------------------------------------------------------
REM Check configuration file
REM ------------------------------------------------------------

if not exist "deploy.conf" (
    echo [ERROR] Configuration file deploy.conf not found.
    pause
    exit /b 1
)

REM ------------------------------------------------------------
REM Load configuration
REM ------------------------------------------------------------

for /f "usebackq tokens=1,* delims==" %%A in ("deploy.conf") do (
    if not "%%A"=="" (
        set %%A=%%B
    )
)

REM ------------------------------------------------------------
REM Validate required variables
REM ------------------------------------------------------------

if "%USER%"=="" (
    echo [ERROR] USER is not defined.
    pause
    exit /b 1
)

if "%HOST%"=="" (
    echo [ERROR] HOST is not defined.
    pause
    exit /b 1
)

if "%LOCAL_FILE%"=="" (
    echo [ERROR] LOCAL_FILE is not defined.
    pause
    exit /b 1
)

if "%UPLOAD_DIR%"=="" (
    echo [ERROR] UPLOAD_DIR is not defined.
    pause
    exit /b 1
)

if "%DEPLOY_SCRIPT%"=="" (
    echo [ERROR] DEPLOY_SCRIPT is not defined.
    pause
    exit /b 1
)

if "%PORT%"=="" (
    set PORT=22
)

echo Configuration loaded.
echo.

REM ------------------------------------------------------------
REM Build
REM ------------------------------------------------------------

echo ============================================
echo Building project...
echo ============================================

if exist "mvnw.cmd" (
    call mvnw.cmd clean package
) else (
    call mvn clean package
)

if errorlevel 1 (
    echo.
    echo [ERROR] Build failed.
    pause
    exit /b 1
)

REM ------------------------------------------------------------
REM Check JAR
REM ------------------------------------------------------------

if not exist "%LOCAL_FILE%" (
    echo.
    echo [ERROR] JAR file not found:
    echo %LOCAL_FILE%
    pause
    exit /b 1
)

echo.
echo Build OK.

REM ------------------------------------------------------------
REM Upload
REM ------------------------------------------------------------

echo.
echo ============================================
echo Uploading application...
echo ============================================

scp -P %PORT% "%LOCAL_FILE%" %USER%@%HOST%:%UPLOAD_DIR%

if errorlevel 1 (
    echo.
    echo [ERROR] Upload failed.
    pause
    exit /b 1
)

echo Upload OK.

REM ------------------------------------------------------------
REM Remote deploy
REM ------------------------------------------------------------

echo.
echo ============================================
echo Running remote deployment...
echo ============================================

ssh -t -p %PORT% %USER%@%HOST% "sudo %DEPLOY_SCRIPT%"

if errorlevel 1 (
    echo.
    echo [ERROR] Remote deployment failed.
    pause
    exit /b 1
)

REM ------------------------------------------------------------
REM Finished
REM ------------------------------------------------------------

echo.
echo ============================================
echo Deployment completed successfully.
echo ============================================

pause
endlocal