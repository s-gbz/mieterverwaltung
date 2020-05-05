pipeline {
    agent any

    tools {
        maven '3.6.3'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build Artifacts') {
            steps {
                sh 'mvn clean package'
            }
        }
    }
}