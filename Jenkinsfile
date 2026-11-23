
pipeline {
    agent any

    tools {
        maven 'Maven3'
    }

    environment {
        SONARQUBE_SERVER = 'SonarQubeServer'
        DOCKERHUB_CREDENTIALS_ID = 'dockerhub-credentials'
        DOCKERHUB_REPO = 'bayramerdogan/bmi_test_3013'
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
                sh 'mvn clean verify'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                script {
                    def scannerHome = tool 'SonarScanner'

                    withSonarQubeEnv("${SONARQUBE_SERVER}") {
                        sh """
                            ${scannerHome}/bin/sonar-scanner \
                            -Dsonar.projectKey=BMI-Test-Project \
                            -Dsonar.projectName='BMI Test Project' \
                            -Dsonar.sources=src/main/java,src/main/resources \
                            -Dsonar.tests=src/test/java \
                            -Dsonar.java.binaries=target/classes \
                            -Dsonar.java.test.binaries=target/test-classes \
                            -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml
                        """
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
                sh 'docker build -t ${DOCKERHUB_REPO}:${DOCKER_IMAGE_TAG} .'
            }
        }

        stage('Push Docker Image to Docker Hub') {
            steps {
                withCredentials([
                    usernamePassword(
                        credentialsId: "${DOCKERHUB_CREDENTIALS_ID}",
                        usernameVariable: 'DOCKERHUB_USER',
                        passwordVariable: 'DOCKERHUB_TOKEN'
                    )
                ]) {
                    sh '''
                        echo "$DOCKERHUB_TOKEN" | docker login \
                            -u "$DOCKERHUB_USER" \
                            --password-stdin
                        docker push "$DOCKERHUB_REPO:$DOCKER_IMAGE_TAG"
                        docker logout
                    '''
                }
            }
        }
    }
}
