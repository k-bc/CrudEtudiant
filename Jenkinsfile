pipeline {
    agent any

    options {
        // Garder les 10 derniers builds
        buildDiscarder(logRotator(numToKeepStr: '10'))
        // Timeout après 30 minutes
        timeout(time: 30, unit: 'MINUTES')
        // Timestamps dans les logs
        timestamps()
    }

    stages {
        stage('GIT') {
            steps {
                echo '========== ÉTAPE GIT =========='
                echo 'Getting project from github'
                checkout scm
                echo '✓ Projet cloné avec succès'
            }
        }

        stage('CLEAN') {
            steps {
                echo '========== ÉTAPE CLEAN =========='
                sh 'mvn clean'
                echo '✓ Nettoyage effectué'
            }
        }

        stage('COMPILE') {
            steps {
                echo '========== ÉTAPE COMPILE =========='
                sh 'mvn compile'
                echo '✓ Compilation réussie'
            }
        }

        stage('TEST') {
            steps {
                echo '========== ÉTAPE TEST UNITAIRES =========='
                echo 'Exécution de 67 tests unitaires...'
                sh 'mvn test -q'
                echo '✓ Tests exécutés avec succès'
            }
        }

        stage('RAPPORT DE COUVERTURE') {
            steps {
                echo '========== ÉTAPE RAPPORT DE COUVERTURE =========='
                sh 'mvn jacoco:report'
                echo '✓ Rapport JaCoCo généré'

                // Archiver les résultats
                archiveArtifacts artifacts: 'target/site/jacoco/**', allowEmptyArchive: true
                echo '✓ Résultats archivés'
            }
        }
        
        stage('SONARQUBE') {
            steps {
		    withSonarQubeEnv('SonarQube') {
		    sh 'mvn sonar:sonar'
		}
            }
        }        
    }
}
