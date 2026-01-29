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
                echo '========== ÉTAPE SONARQUBE =========='
                withSonarQubeEnv('SonarQube') {
                    sh 'mvn sonar:sonar'
                }
                echo '✓ Analyse SonarQube complétée'
            }
        }        
    }

    post {
        always {
            echo '========== RÉSUMÉ DU BUILD =========='
            echo "Build Number: ${BUILD_NUMBER}"
            echo "Build Status: ${currentBuild.result}"
            echo ""

            // Publier les résultats des tests (IMPORTANT)
            junit testResults: 'target/surefire-reports/*.xml', allowEmptyResults: true, keepLongStdio: true

            // Publier le rapport de couverture si disponible
            script {
                if (fileExists('target/site/jacoco/index.html')) {
                    publishHTML(
                        reportDir: 'target/site/jacoco',
                        reportFiles: 'index.html',
                        reportName: 'Rapport de Couverture JaCoCo',
                        keepAll: true,
                        alwaysLinkToLastBuild: true
                    )
                    echo '✓ Rapport JaCoCo publié'
                }
            }

            // Afficher le résumé des tests
            sh '''
                echo ""
                echo "========== RÉSUMÉ DES TESTS =========="
                if [ -d "target/surefire-reports" ]; then
                    TEST_COUNT=$(find target/surefire-reports -name "TEST-*.xml" | wc -l)
                    echo "Fichiers de test générés: $TEST_COUNT"

                    # Compter les tests réussis
                    if [ -f "target/surefire-reports/TEST-*.xml" ]; then
                        grep -h "tests=" target/surefire-reports/TEST-*.xml 2>/dev/null | head -1 || echo "Tests exécutés"
                    fi
                else
                    echo "⚠️ Aucun rapport de test disponible"
                fi
            '''

            echo '========== FIN DU BUILD =========='
        }

        success {
            echo '✓ BUILD RÉUSSI - Tous les tests sont passés'
        }

        failure {
            echo '❌ BUILD ÉCHOUÉ - Vérifiez les logs ci-dessus'
            echo 'Consultez target/surefire-reports/ pour les détails'
        }

        unstable {
            echo '⚠️ BUILD INSTABLE - Certains tests ont échoué'
        }

        cleanup {
            echo '🧹 Nettoyage des ressources...'
            // Ne pas supprimer le répertoire - garder les rapports
            sh 'rm -rf target/surefire-reports/*.xml || true'
        }
    }
}
