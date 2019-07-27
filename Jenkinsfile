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
        sh 'cp -f ./build/libs/parkpersonally-0.0.1-SNAPSHOT.jar ./ParkPersonally.jar'
        sh 'pid=$(jps | grep jar | cut -d \' \' -f 1)'
        sh '''if [ ! -n $pid ]; then
 kill -9 $pid
fi'''
        sh '''JENKINS_NODE_COOKIE=dontKillMe
nohup java -jar ParkPersonally.jar > out.log & sleep 20s'''
      }
    }
    stage('Delpoy') {
      steps {
        sh 'chmod 777 ./ParkPersonally.jar'
        sh 'scp -i /root/.ssh/ooclserver_rsa ./ParkPersonally.jar root@39.98.219.194:/usr/local/bin/ParkPersonally.jar'
        sh 'ssh -i /root/.ssh/ooclserver_rsa root@39.98.219.194 "pid=\\$(jps | grep jar | cut -d \' \' -f 1)"'
        sh '''if [ ! -n $pid ]; then
 kill -9 $pid
fi'''
        sh 'ssh -i /root/.ssh/ooclserver_rsa root@39.98.219.194 "rm -f /usr/local/bin/application.log"'
        sh 'ssh -i /root/.ssh/ooclserver_rsa root@39.98.219.194 "cd /usr/local/bin;nohup java -jar ParkPersonally.jar > application.log &"'
      }
    }
  }
}