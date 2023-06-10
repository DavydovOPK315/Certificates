pipeline {
    agent any
    tools {
            maven 'Maven-3.6.3'
        }
    triggers {
            pollSCM '*/5 * * * *'
        }
    stages {
         stage('Git Checkout') {
               steps {
                   git url: 'https://github.com/DavydovOPK315/Certificates.git'
   		           echo "Code Checked-out Successfully!!";
               }
           }

         stage ('Initialize') {
                steps {
                    sh '''
                        echo "java version"
                        java -version
                        echo "PATH = ${PATH}"
                        echo "M2_HOME = ${M2_HOME}"
                    '''
                }
            }

         stage('Package') {
                steps {
                    sh 'mvn clean package'
	     	        echo "Maven Package Goal Executed Successfully!";
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
                    withSonarQubeEnv('SonarQube') {
                        sh 'mvn verify org.sonarsource.scanner.maven:sonar-maven-plugin:sonar -Dsonar.projectKey=davydovopk315_certificates'
                    }
                }
         }

         stage("Quality gate") {
                steps {
                    waitForQualityGate abortPipeline: true
                }
              }
         stage("Deploy") {
                steps {


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