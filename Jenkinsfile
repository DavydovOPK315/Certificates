pipeline {
    agent {
        node {
            label 'docker-agent-python'
            }
      }
    triggers {
        pollSCM '* * * * *'
    }
    stages {
        stage('Compile') {
            steps {
                sh './gradlew clean compile'
                echo "compile..."
            }
        }
        stage('Test') {
             steps {
                sh './gradlew test'
                echo "test..."
             }
        }
        stage('Jacoco sends') {
             steps {
                echo "Jacoco sends..."
             }
        }
        stage('Build') {
             steps {
                sh './gradlew clean jar'
                echo "build..."
             }
        }
        stage('S3') {
             steps {
                echo "build..."
             }
        }
    }
}