pipeline {
  agent any

  stages {
    stage('Build App') {
      agent {
        docker {
          image 'maven:3-jdk-8'
          reuseNode true
          // TODO: add cache local dir for maven
          args '-v /tmp/.m2:/.m2'
        }
      }
      steps {
      	configFileProvider([configFile(fileId: 'defaultSettings', targetLocation: './settings.xml')]) {
          sh("mvn clean install -s ./settings.xml")
        }
        fingerprint 'target/*.jar'
        archiveArtifacts artifacts: 'target/*.jar', onlyIfSuccessful: true
      }
      post {
      	always {
      	  junit '**/target/*-reports/*.xml'
      	  step([
      	    $class: 'TasksPublisher',
      	    defaultEncoding: 'UTF-8',
      	    asRegexp: true,
      	    ignoreCase: true,
      	    usePreviousBuildAsReference: true,
      	    high: '^.*(FIXME(?:[0-9]*))(.*)$',
      	    normal: '^.*(TODO(?:[0-9]*))(.*)$',
      	    low: '',
      	    pattern: 'src/**/*, pom.xml, README.md',
      	    excludePattern: '',
      	    failedNewHigh: '5',
      	    failedNewNormal: '5',
      	    failedNewLow: '5',
      	    failedTotalAll: '30',
      	    failedTotalHigh: '6',
      	    failedTotalNormal: '15',
      	    failedTotalLow: '11',
      	    healthy: '10',
      	    thresholdLimit: 'normal',
      	    unHealthy: '15',
      	    unstableNewHigh: '3',
      	    unstableNewNormal: '3',
      	    unstableNewLow: '4',
      	    unstableTotalAll: '20',
      	    unstableTotalHigh: '5',
      	    unstableTotalNormal: '5',
      	    unstableTotalLow: '10',
      	  ])
      	}
      }
    }
  }
}
