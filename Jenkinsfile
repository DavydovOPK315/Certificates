pipeline {
    agent any
    tools {
            maven 'Maven-3.6.3'
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

//         stage('JUNit Reports') {
//             steps {
//                 junit 'target/surefire-reports/*.xml'
// 		        echo "Publishing JUnit reports"
//             }
//         }

//         stage('Jacoco Reports') {
//             steps {
//                   jacoco()
//                   echo "Publishing Jacoco Code Coverage Reports";
//             }
//         }

	stage('SonarQube analysis') {
            steps {
		// Change this as per your Jenkins Configuration
                withSonarQubeEnv('SonarQube') {
//                     sh 'mvn package sonar:sonar'
//                     sh 'mvn clean verify sonar:sonar -Dsonar.projectKey=Certificates -Dsonar.host.url=http://localhost:9000 -Dsonar.login=sqa_aa066c9eb7fdfcdeb06c1de0d71b628413a71c98'
              sh 'mvn verify org.sonarsource.scanner.maven:sonar-maven-plugin:sonar -Dsonar.projectKey=davydovopk315_certificates'
                }
//                 	echo 'jar start'
//                 	java -jar target/Certificates-0.0.1-SNAPSHOT.jar

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