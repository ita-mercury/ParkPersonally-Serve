pipeline {
  agent any
  stages {
    stage('Gradle Test') {
      steps {
        sh 'cp /usr/local/bin/build.gradle ./build.gradle'
        sh 'chmod 777 ./gradlew'
        sh 'cp /usr/local/bin/application.properties ./src/main/resources/application.properties'
        sh 'cp /usr/local/bin/application-test.properties ./src/main/resources/application-test.properties'
        sh 'cp /usr/local/bin/application-prod.properties ./src/main/resources/application-prod.properties'
        sh './gradlew test'
      }
      post {
        always {
          junit "/usr/local/bin/test/*.xml"
        }
      }
    }
    stage('QA') {
      steps {
        sh './gradlew build'
        sh 'cp -f ./build/libs/parkpersonally-0.0.1-SNAPSHOT.jar /usr/local/bin/ParkPersonally.jar'
        sh '''pid=$(jps | grep jar | cut -d \' \' -f 1)
kill -9 $pid'''
        sh '''JENKINS_NODE_COOKIE=dontKillMe
nohup java -jar /usr/local/bin/ParkPersonally.jar --spring.profiles.active=test > /usr/local/bin/out.log & sleep 20s'''
      }
    }
  }
}