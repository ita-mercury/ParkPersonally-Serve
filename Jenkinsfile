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
        sh './gradlew build jacocoTestReport'
        jacoco(execPattern: 'build/jacoco/*.exec', classPattern: 'build/classes', sourcePattern: 'src/main/java', exclusionPattern: 'src/test*,src/main/**/model/**,src/main/**/dto/**,src/main/**/exception/**')
      }
    }
    stage('QA') {
      steps {
        sh 'cp -f ./build/libs/parkpersonally-0.0.1-SNAPSHOT.jar /usr/local/bin/ParkPersonally.jar'
        sh '''pid=$(jps | grep jar | cut -d \' \' -f 1)
kill -9 $pid'''
        sh '''JENKINS_NODE_COOKIE=dontKillMe
nohup java -jar /usr/local/bin/ParkPersonally.jar --spring.profiles.active=test > /usr/local/bin/out.log & sleep 20s'''
      }
    }
  }
}