@echo off
echo Compiling Professional Banking System with JavaFX...
echo.

REM Set JavaFX path - Update this path to your JavaFX installation
set JAVAFX_PATH=C:\Program Files\Java\javafx-sdk-24.0.1\lib

REM Compile all Java files with JavaFX modules
javac --module-path "%JAVAFX_PATH%" --add-modules javafx.controls,javafx.fxml banking/*.java

if %errorlevel% neq 0 (
    echo Compilation failed!
    echo Please check your JavaFX installation path.
    echo Current JavaFX path: %JAVAFX_PATH%
    pause
    exit /b 1
)

echo Compilation successful!
echo.
echo Starting the JavaFX application...
echo.

REM Run the application with JavaFX modules
java --module-path "%JAVAFX_PATH%" --add-modules javafx.controls,javafx.fxml -cp . banking.Main

pause
