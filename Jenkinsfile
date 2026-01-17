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
    }
}

