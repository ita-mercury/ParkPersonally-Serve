pipeline {
  agent any
  stages {
    stage('test') {
      steps {
        sh 'chmod 777 ./gradlew'
        sh 'rm -f ./src/main/resources/application/*'
        sh 'cp /usr/local/bin/application.properties ./src/main/resources/application.properties'
        sh './gradlew test'
      }
    }
    stage('QA') {
      steps {
        sh './gradlew build'
        sh 'mv ./build/libs/parkpersonally-0.0.1-SNAPSHOT.jar ./ParkPersonally.jar'
        sh 'pid=$(jps | grep jar | cut -d \' \' -f 1)'
        sh '''if [ ! -n $pid ]; then
 kill -9 $pid
fi'''
        sh 'nohup java -jar ParkPersonally.jar > 1 &'
      }
    }
  }
}