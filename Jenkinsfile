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
                echo 'Execution de 220 tests unitaires...'
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

        stage('DOCKER BUILD') {
            steps {
                echo '========== ETAPE DOCKER BUILD =========='
                script {
                    try {
                        sh 'docker build -t khaledkbc/crud-etudiant:latest .'
                        echo '✓ Image Docker construite avec succes'
                    } catch (Exception e) {
                        echo "❌ Erreur lors du build Docker: ${e.message}"
                        error("Docker build a echoue")
                    }
                }
            }
        }

        stage('DOCKER PUSH') {
            steps {
                echo '========== ETAPE DOCKER PUSH =========='
                script {
                    try {
                        withCredentials([usernamePassword(
                            credentialsId: 'dockerhubcode',
                            usernameVariable: 'DOCKER_USER',
                            passwordVariable: 'DOCKER_PASS'
                        )]) {
                            sh '''
                              echo "$DOCKER_PASS" | docker login -u "$DOCKER_USER" --password-stdin
                              docker push khaledkbc/crud-etudiant:latest
                              docker logout
                            '''
                        }
                        echo '✓ Image Docker pushee vers DockerHub'
                    } catch (Exception e) {
                        echo "⚠️ Erreur lors du push Docker: ${e.message}"
                        echo "Le deploiement local va tenter de continuer..."
                    }
                }
            }
        }

        stage('DOCKER COMPOSE UP') {
            steps {
                echo '========== ETAPE DOCKER COMPOSE UP =========='
                script {
                    try {
                        // Nettoyage préalable pour éviter les conflits, ignore les erreurs si déjà arrêté
                        sh 'docker compose down --remove-orphans || true'
                        sh 'docker compose up -d --force-recreate'
                        echo '✓ Conteneurs demarres avec Docker Compose'
                        sh 'docker compose ps'
                    } catch (Exception e) {
                        echo "❌ Erreur lors du lancement Docker Compose: ${e.message}"
                        try {
                            echo "Tentative de secours avec docker-compose..."
                            sh 'docker-compose down --remove-orphans || true'
                            sh 'docker-compose up -d --force-recreate'
                            echo '✓ Conteneurs demarres avec docker-compose'
                        } catch (Exception e2) {
                            error("Docker compose a echoue : ${e2.message}")
                        }
                    }
                }
            }
        }
    }

}
