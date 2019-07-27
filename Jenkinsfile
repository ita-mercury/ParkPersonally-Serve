pipeline {
  agent any
  stages {
    stage('Gradle Test') {
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
        sh 'cp -f ./build/libs/parkpersonally-0.0.1-SNAPSHOT.jar /usr/local/bin/ParkPersonally.jar'
        sh 'pid=$(jps | grep jar | cut -d \' \' -f 1)'
        sh '''if [ ! -n $pid ]; then
 kill -9 $pid
fi'''
        sh 'cd /usr/local/bin'
        sh '''JENKINS_NODE_COOKIE=dontKillMe
nohup java -jar ParkPersonally.jar > out.log & sleep 20s'''
      }
    }
  }
}