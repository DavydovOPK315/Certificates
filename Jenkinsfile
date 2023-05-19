pipeline {
    agent {
        node {
            label 'docker-agent-alpine2'
            }
      }
    triggers {
        pollSCM '* * * * *'
    }
    stages {
        stage('Compile') {
            steps {
                echo "compile start..."
                sh './gradlew build --scan'
                echo "compile end..."
            }
        }
    }
}