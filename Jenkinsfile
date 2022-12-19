
pipeline {
  agent any

  tools {
    maven 'Maven'
  }

  stages {
    stage('initialize') {
      steps {
        sh 'mvn -v'
        sh 'java -version'
        sh 'git --version'
        sh 'docker -v'
      }
    }

    stage('build') {

      steps {
        checkout([$class: 'GitSCM', branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[url: "${GIT_REPO_URL}"]]])
        sh 'mvn clean install'
      }
    }

    stage('build docker image') {

      steps {
        script{
          sh "docker build -t ${IMAGE_TAG} ."
        }
      }
    }

    stage('deploy') {

      steps {
        script {
          withCredentials([usernamePassword(credentialsId: 'docker-pwd', passwordVariable: 'PASSWORD', usernameVariable: 'USERNAME')]) {
            sh "docker login -u ${USERNAME} -p ${PASSWORD}"
          }

          sh "docker push ${CONTAINER_NAME}"
        }
      }
    }
  }
}
