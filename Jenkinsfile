pipeline {
    agent {
        node {
            label 'docker-agent-alpine2'
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