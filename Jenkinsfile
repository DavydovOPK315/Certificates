pipeline {
    agent {
        node {
            label 'docker-agent-alpine'
            }
      }
    stages {
        stage('Compile') {
            steps {
                sh './gradlew clean build'
                echo "compile..."
            }
        }
    }
}