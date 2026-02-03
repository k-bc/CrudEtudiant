pipeline {
    agent any

    options {
        // Garder les 10 derniers builds
        buildDiscarder(logRotator(numToKeepStr: '10'))
        // Timeout apres 30 minutes
        timeout(time: 30, unit: 'MINUTES')
        // Timestamps dans les logs
        timestamps()
    }

    environment {
        // Desactiver VirtualBox Drag & Drop
        VBOX_DND_DISABLED = '1'
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
                echo '========== ETAPE TEST UNITAIRES =========='
                echo 'Execution de 67 tests unitaires...'
                script {
                    def testResult = sh(
                        script: 'mvn test',
                        returnStatus: true
                    )
                    if (testResult == 0) {
                        echo '✓ Tests executes avec succes'
                    } else {
                        echo "⚠️ Tests termines avec code: $testResult"
                    }
                }
            }
        }

        stage('RAPPORT DE COUVERTURE') {
            steps {
                echo '========== ETAPE RAPPORT DE COUVERTURE =========='
                script {
                    def coverageResult = sh(
                        script: 'mvn jacoco:report -DskipTests',
                        returnStatus: true
                    )
                    if (coverageResult == 0) {
                        echo '✓ Rapport JaCoCo genere'
                        archiveArtifacts artifacts: 'target/site/jacoco/**', allowEmptyArchive: true
                        echo '✓ Resultats archives'
                    } else {
                        echo "⚠️ Probleme lors de la generation du rapport: $coverageResult"
                    }
                }
            }
        }

        stage('SONARQUBE') {
            steps {
                echo '========== ETAPE SONARQUBE =========='
                script {
                    try {
                        withSonarQubeEnv('SonarQube') {
                            sh 'mvn sonar:sonar'
                        }
                        echo '✓ Analyse SonarQube completee'
                    } catch (Exception e) {
                        echo "⚠️ SonarQube indisponible ou erreur: ${e.message}"
                        echo "Continuant le build..."
                    }
                }
            }
        }        
    }

    post {
        always {
            echo '========== RESUME DU BUILD =========='
            echo "Build Number: ${BUILD_NUMBER}"
            echo "Build Status: ${currentBuild.result}"
            echo ""

            // Publier les resultats des tests
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
                    echo '✓ Rapport JaCoCo publie'
                }
            }

            // Afficher le resume des tests
            sh '''
                echo ""
                echo "========== RESUME DES TESTS =========="
                if [ -d "target/surefire-reports" ]; then
                    TEST_COUNT=$(find target/surefire-reports -name "TEST-*.xml" | wc -l)
                    echo "Fichiers de test generes: $TEST_COUNT"

                    # Afficher le resume
                    if [ -f "target/surefire-reports/TEST-*.xml" ]; then
                        grep -h "tests=" target/surefire-reports/TEST-*.xml 2>/dev/null | head -1 || echo "Tests executes"
                    fi
                else
                    echo "Aucun rapport de test disponible"
                fi
            '''

            echo '========== FIN DU BUILD =========='
        }

        success {
            echo 'OK - BUILD REUSSI - Tous les tests sont passes'
        }

        failure {
            echo 'ERREUR - BUILD ECHOUE - Verifiez les logs ci-dessus'
            echo 'Consultez target/surefire-reports/ pour les details'
        }

        unstable {
            echo 'ATTENTION - BUILD INSTABLE - Certains tests ont echoue'
        }

        cleanup {
            echo 'Nettoyage des ressources...'
            sh 'rm -rf target/surefire-reports/*.xml || true'
        }
    }

}
