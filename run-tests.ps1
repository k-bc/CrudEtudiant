#!/usr/bin/env pwsh
exit $ExitCode
Write-Host ""

}
    Write-Host "Code de sortie : $ExitCode" -ForegroundColor $Red
    Write-Host "❌ ERREUR : Des tests ont échoué" -ForegroundColor $Red
} else {
    Write-Host "Résultats disponibles dans : target/" -ForegroundColor $Cyan
    Write-Host ""

    }
        Write-Host "  target/site/jacoco/index.html" -ForegroundColor $Yellow
        Write-Host "Rapport de couverture généré :" -ForegroundColor $Cyan
    if ($Coverage) {

    Write-Host ""
    Write-Host "✓ SUCCÈS : Tous les tests ont passé !" -ForegroundColor $Green
if ($ExitCode -eq 0) {

Write-Host "─────────────────────────────────────────────────────────" -ForegroundColor $Cyan
Write-Host ""

$ExitCode = $LASTEXITCODE
Invoke-Expression $Command
# Exécuter la commande

Write-Host ""
Write-Host "─────────────────────────────────────────────────────────" -ForegroundColor $Cyan
Write-Host "Exécution en cours..." -ForegroundColor $Cyan
Write-Host "─────────────────────────────────────────────────────────" -ForegroundColor $Cyan
Write-Host ""

}
    }
        $Command += " -q"
    } else {
        Write-Host "Mode verbeux : ACTIVÉ" -ForegroundColor $Cyan
        $Command += " -X"
    if ($Verbose) {

    }
        Write-Host "Rapport de couverture : ACTIVÉ" -ForegroundColor $Cyan
        $Command += " jacoco:report"
    if ($Coverage) {

    }
        Write-Host "Tests à exécuter : TOUS (67 tests)" -ForegroundColor $Cyan
    } else {
        Write-Host "Tests à exécuter : $TestClass" -ForegroundColor $Cyan
        $Command += " -Dtest=$TestClass"
    if ($TestClass -ne "all") {

    $Command += " test"
} else {
    $Command += " install -DskipTests"
if ($SkipTests) {

$Command = "$MvnCmd clean"
# Construire la commande de base

}
    Write-Host "✓ Maven Wrapper détecté" -ForegroundColor $Green
    $MvnCmd = ".\mvnw.cmd"
if (Test-Path ".\mvnw.cmd") {
$MvnCmd = "mvn"
# Déterminer la commande Maven

Write-Host ""
Write-Host "✓ Maven détecté" -ForegroundColor $Green

}
    exit 1
    Write-Host ""
    Write-Host "  choco install maven"
    Write-Host "Ou installez avec Chocolatey :"
    Write-Host ""
    Write-Host "  4. Redémarrez PowerShell"
    Write-Host "  3. Ajoutez C:\tools\maven\bin au PATH"
    Write-Host "  2. Extrayez dans C:\tools\maven"
    Write-Host "  1. Téléchargez : https://maven.apache.org/download.cgi"
    Write-Host "Pour installer Maven :" -ForegroundColor $Yellow
    Write-Host ""
    Write-Host "❌ ERREUR : Maven n'est pas installé ou n'est pas dans le PATH" -ForegroundColor $Red
if (-not $MavenCheck) {

}
    $false
} catch {
    $true
    mvn -version 2>$null
$MavenCheck = try {
# Vérifier si Maven est installé

Write-Host ""
Write-Host "╚════════════════════════════════════════════════════════╝" -ForegroundColor $Cyan
Write-Host "║     CrudEtudiant - Suite de Tests Unitaires          ║" -ForegroundColor $Cyan
Write-Host "╔════════════════════════════════════════════════════════╗" -ForegroundColor $Cyan

$Cyan = [System.ConsoleColor]::Cyan
$Yellow = [System.ConsoleColor]::Yellow
$Red = [System.ConsoleColor]::Red
$Green = [System.ConsoleColor]::Green
# Couleurs pour l'output

)
    [switch]$SkipTests
    [switch]$Verbose,
    [switch]$Coverage,
    [string]$TestClass = "all",
    [Parameter(Mandatory=$false)]
param(

# Exécute tous les tests unitaires ou ceux spécifiés
# Script de test pour le projet CrudEtudiant

