@echo off
REM Script d'exécution des tests - CrudEtudiant
REM Usage: run-tests.bat [classe_test] [/coverage] [/verbose]

setlocal enabledelayedexpansion

echo.
echo ╔════════════════════════════════════════════════════════╗
echo ║     CrudEtudiant - Suite de Tests Unitaires          ║
echo ╚════════════════════════════════════════════════════════╝
echo.

REM Vérifier si Maven est installé
mvn -version >nul 2>&1
if errorlevel 1 (
    echo ❌ ERREUR : Maven n'est pas installé ou n'est pas dans le PATH
    echo.
    echo Pour installer Maven :
    echo   1. Téléchargez : https://maven.apache.org/download.cgi
    echo   2. Extrayez dans C:\tools\maven
    echo   3. Ajoutez C:\tools\maven\bin au PATH
    echo   4. Redémarrez CMD/PowerShell
    echo.
    echo Ou installez avec Chocolatey :
    echo   choco install maven
    echo.
    exit /b 1
)

echo ✓ Maven détecté
echo.

REM Déterminer la classe de test à exécuter
set TEST_CLASS=all
if not "%1"=="" (
    set TEST_CLASS=%1
)

REM Vérifier les options
set COVERAGE=0
set VERBOSE=0

if "%2"=="/coverage" set COVERAGE=1
if "%3"=="/coverage" set COVERAGE=1
if "%2"=="/verbose" set VERBOSE=1
if "%3"=="/verbose" set VERBOSE=1

REM Afficher les paramètres
if not "%TEST_CLASS%"=="all" (
    echo Tests à exécuter : %TEST_CLASS%
) else (
    echo Tests à exécuter : TOUS (67 tests)
)

if %COVERAGE%==1 echo Rapport de couverture : ACTIVÉ
if %VERBOSE%==1 echo Mode verbeux : ACTIVÉ

echo.
echo ─────────────────────────────────────────────────────────
echo Exécution en cours...
echo ─────────────────────────────────────────────────────────
echo.

REM Construire la commande Maven
set CMD=mvn clean

if "%TEST_CLASS%"=="all" (
    set CMD=!CMD! test
) else (
    set CMD=!CMD! test -Dtest=%TEST_CLASS%
)

if %VERBOSE%==1 (
    set CMD=!CMD! -X
) else (
    set CMD=!CMD! -q
)

if %COVERAGE%==1 (
    set CMD=!CMD! jacoco:report
)

REM Exécuter la commande
%CMD%
set EXIT_CODE=%errorlevel%

echo.
echo ─────────────────────────────────────────────────────────

if %EXIT_CODE%==0 (
    echo ✓ SUCCÈS : Tous les tests ont passé !
    echo.
    if %COVERAGE%==1 (
        echo Rapport de couverture généré :
        echo   target\site\jacoco\index.html
        echo.
    )
    echo Résultats disponibles dans : target\
) else (
    echo ❌ ERREUR : Des tests ont échoué
    echo Code de sortie : %EXIT_CODE%
)

echo.
exit /b %EXIT_CODE%

