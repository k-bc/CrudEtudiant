pipeline {
    agent any

    stages {
        stage('Checkout Code') {
            steps {
                // Récupérer le code depuis GitHub
                git branch: 'main', 
                    url: 'https://github.com/k-bc/CrudEtudiant'
            }
            
        }
        stage('Build') {
            steps {
              sh 'mvn compile'
            }
            
        }

       steps {
                withSonarQubeEnv("${SONARQUBE_ENV}") {
                    sh '''
                    mvn sonar:sonar \
                      -Dsonar.projectKey=CrudEtudiant \
                      -Dsonar.projectName=CrudEtudiant \
                      -Dsonar.host.url=http://localhost:9000 \
                      -Dsonar.login=${sonar_token}
                    '''
                }
            }
    }
}

