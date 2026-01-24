pipeline {
    agent any

    stages {
        stage('GIT') {
            steps {
                echo 'Getting project from github'
                checkout scm
            }
        }

        stage('CLEAN') {
            steps {
                sh 'mvn clean'
            }
        }

        stage('COMPILE') {
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
