
pipeline {
  agent any

  tools {
    maven 'Maven'
  }

  stages {
    stage('initialize') {
      when { environment name: 'INIT', value: 'true' }

      steps {
        sh 'mvn -v'
        sh 'java -version'
        sh 'git --version'
        sh 'docker -v'
      }
    }

    stage('build') {
      when { environment name: 'BUILD_MAVEN', value: 'true' }

      steps {
        checkout([$class: 'GitSCM', branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[url: "${GIT_REPO_URL}"]]])
        sh 'mvn clean install'
      }
    }

    stage('build docker image') {
      when { environment name: 'BUILD_DOCKER', value: 'true' }

      steps {
        script{
          sh "docker build -t ${IMAGE_TAG} ."
        }
      }
    }

    stage('deploy') {
      when { environment name: 'DEPLOY_DOCKER', value: 'true' }

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
