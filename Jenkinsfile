pipeline {
    agent any

    tools {
        maven 'Maven3'
    }

    environment {
        PATH = "C:\\Program Files\\Docker\\Docker\\resources\\bin;${env.PATH}"
        JAVA_HOME = 'C:\\Program Files\\Java\\jdk-21'
        SONARQUBE_SERVER = 'SonarQubeServer'
        DOCKERHUB_CREDENTIALS_ID = 'Docker_Hub'
        DOCKERHUB_REPO = 'YOUR_DOCKERHUB_USER/bmi_test_3013'
        DOCKER_IMAGE_TAG = 'latest'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'master',
                    url: 'https://github.com/Bayram-Erdogan/individual_task_for_week_5.git'
            }
        }

        stage('Build and Test') {
            steps {
                bat 'mvn clean verify'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withCredentials([string(credentialsId: 'SONAR_TOKEN', variable: 'SONAR_TOKEN')]) {
                    withSonarQubeEnv("${env.SONARQUBE_SERVER}") {
                        bat '''
                            sonar-scanner ^
                            -Dsonar.projectKey=BMI_Cal ^
                            -Dsonar.projectName=BMI_Cal ^
                            -Dsonar.sources=src/main/java,src/main/resources ^
                            -Dsonar.tests=src/test/java ^
                            -Dsonar.java.binaries=target/classes ^
                            -Dsonar.java.test.binaries=target/test-classes ^
                            -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml ^
                            -Dsonar.token=%SONAR_TOKEN%
                        '''
                    }
                }
            }
        }

        stage('Quality Gate') {
            steps {
                timeout(time: 5, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    def app = docker.build("${DOCKERHUB_REPO}:${DOCKER_IMAGE_TAG}")
                    env.BUILT_IMAGE = app.id  // Optional: store image ID for reuse
                }
            }
        }

        stage('Push Docker Image to Docker Hub') {
            steps {
                script {
                    docker.withRegistry('https://index.docker.io/v1/', DOCKERHUB_CREDENTIALS_ID) {
                       docker.image("${DOCKERHUB_REPO}:${DOCKER_IMAGE_TAG}").push()
                    }
                }
            }
        }
    }


}
