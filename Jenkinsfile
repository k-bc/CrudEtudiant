pipeline {
    agent any

    environment {
            SONAR_TOKEN = credentials('squ_f89912bf7ffec7b07aa45d3b6731e3f299989ea9')
           }

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

       stage('SONARQUBE') {
            steps {
		    withSonarQubeEnv('SonarQube') {
		    sh 'mvn sonar:sonar'
		}
            }
        }        
    }
}
