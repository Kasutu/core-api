
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
    always {
      discordSend description: '', enableArtifactsList: true, footer: '', image: '', link: "https://kasutu-jenkins-dashboard.loca.lt/job/${env.JOB_NAME}", result: 'SUCCESS', scmWebUrl: 'https://github.com/Kasutu/core-api', showChangeset: true, thumbnail: 'https://imagepng.org/wp-content/uploads/2019/12/check-icone-2.png', title: 'env.JOB_NAME', webhookURL: 'https://discord.com/api/webhooks/1042083153721950208/Vqm_l65LLoa7CVpCIYGmLLpF4XduCeFWS3RQRF5wGiGDZRiK5TSdtUjJWAVlkgZK-d3b'
    }

    success {
      if (env.GITHUB_PR_STATE == "open") {
        setGitHubPullRequestStatus context: 'build sucess', message: '', state: 'SUCCESS'
      }
    }
  }
}
