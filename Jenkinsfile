pipeline {
    agent {
        node {
            label 'docker-agent-alpine'
            }
      }
    stages {
        stage('Compile') {
            steps {
                sh 'gradle clean compile'
                echo "compile..."
            }
        }
        stage('Test') {
             steps {
                sh 'gradle test'
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
                sh 'gradle clean jar'
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