pipeline {
    agent any
    tools {
            maven 'Maven-3.6.3'
        }
    stages {

    stage ('Initialize') {
                steps {
                    sh '''
                        echo "java -version"
                        echo "PATH = ${PATH}"
                        echo "M2_HOME = ${M2_HOME}"
                    '''
                }
            }

        stage('Git Checkout') {
            steps {
                git url: 'https://github.com/DavydovOPK315/Certificates.git'
		            echo "Code Checked-out Successfully!!";
            }
        }

        stage('Package') {
            steps {
                sh 'mvn package'
		        echo "Maven Package Goal Executed Successfully!";
            }
        }

        stage('JUNit Reports') {
            steps {
                junit 'target/surefire-reports/*.xml'
		        echo "Publishing JUnit reports"
            }
        }

        stage('Jacoco Reports') {
            steps {
                  jacoco()
                  echo "Publishing Jacoco Code Coverage Reports";
            }
        }

	stage('SonarQube analysis') {
            steps {
		// Change this as per your Jenkins Configuration
                withSonarQubeEnv('SonarQube') {
                    sh 'mvn package sonar:sonar'
                }
            }
        }

    }
    post {

        success {
            echo 'This will run only if successful'
        }
        failure {
            echo 'This will run only if failed'
        }

    }
}