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
                sh "chmod +x gradlew"
                sh './gradlew build --scan'
                echo "compile end..."
            }
        }
        stage('Test') {
             steps {
                echo "test start..."
                sh './gradlew test'
                echo "test end..."
             }
        }
        stage('Jacoco sends') {
             steps {
                echo "Jacoco sends..."
             }
        }
//         stage('Build') {
//              steps {
//                 sh './gradlew clean jar'
//                 echo "build..."
//              }
//         }
        stage('Deploy') {
             steps {
                echo "Deploy..."
            }
        }
    }
}