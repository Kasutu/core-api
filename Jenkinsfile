
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
        checkout([$class: 'GitSCM', branches: [[name: '*/dev']], extensions: [], userRemoteConfigs: [[url: "${GIT_REPO_URL}"]]])
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

  post {
    success {
      discordSend description: "${env.GIT_BRANCH} branch build PASSED ✅", footer: "${env.BUILD_ID} - ${env.GIT_COMMITTER_NAME}", image: '', link: "https://kasutu-jenkins-dashboard.loca.lt/job/${env.JOB_NAME}", result: 'SUCCESS', scmWebUrl: 'https://github.com/Kasutu/core-api', showChangeset: true, thumbnail: 'https://imagepng.org/wp-content/uploads/2019/12/check-icone-2.png', title: "${env.JOB_NAME}", webhookURL: 'https://discord.com/api/webhooks/1042083153721950208/Vqm_l65LLoa7CVpCIYGmLLpF4XduCeFWS3RQRF5wGiGDZRiK5TSdtUjJWAVlkgZK-d3b'
    }

    failure {
      discordSend description: "${env.GIT_BRANCH} branch build FAILED ❌", footer: "${env.BUILD_ID} - ${env.GIT_COMMITTER_NAME}", image: '', link: "https://kasutu-jenkins-dashboard.loca.lt/job/${env.JOB_NAME}", result: 'FAILURE', scmWebUrl: 'https://github.com/Kasutu/core-api', showChangeset: true, thumbnail: 'https://external-content.duckduckgo.com/iu/?u=http%3A%2F%2Fvignette3.wikia.nocookie.net%2Frating-system%2Fimages%2F3%2F3f%2F450px-White_X_in_red_background.svg.png%2Frevision%2Flatest%3Fcb%3D20130326132551&f=1&nofb=1&ipt=1da84387ac09cf6684f8b3d4c2c9fe25fd43792eaabaeb38e817e84b7af0df7d&ipo=images', title: "${env.JOB_NAME}", webhookURL: 'https://discord.com/api/webhooks/1042083153721950208/Vqm_l65LLoa7CVpCIYGmLLpF4XduCeFWS3RQRF5wGiGDZRiK5TSdtUjJWAVlkgZK-d3b'
    }
  }
}
