pipeline {
  agent any

  stages {
    stage('Build App') {
      agent {
        docker {
          image 'maven:3-jdk-8'
          reuseNode true
        }
      }
      steps {
        sh("mvn clean install")
        fingerprint 'target/*.jar'
        archiveArtifacts artifacts: 'target/*.jar', onlyIfSuccessful: true
      }
      post {
      	always {
      	  junit '**/target/*-reports/*.xml'
      	}
      }
    }
  }
}
