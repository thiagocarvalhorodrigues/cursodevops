pipeline {
    agent any

    environment {
		registry = "he220652/app-dcw5" // ALTERAR
        registryCredential = "dockerhub_id"
        dockerImage = ''
    }

    stages {
    	stage('Clone Repository') {
    		steps {
                git branch: "master", url: 'https://gitlab.com/thiagocarvalhorodrigues/app-dcw5.git' // ALTERAR
			}
    	}
    	stage('Build Docker Image') {
            steps{
                script {
                    dockerImage = docker.build registry + ":develop"
                }
            }
        }
    	stage('Send image to Docker Hub') {
            steps{
                script {
                    docker.withRegistry( '', registryCredential) {
                        dockerImage.push()
                    }
                }
            }
        }
    	stage('Deploy') {
		    steps{
                step([$class: 'AWSCodeDeployPublisher',
                    applicationName: 'teste-dcw',
                    awsAccessKey: "AKIAT5O7H6SILQKKTGPH", // ALTERAR
                    awsSecretKey: "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", // ALTERAR
                    credentials: 'awsAccessKey',
                    deploymentGroupAppspec: false,
                    deploymentGroupName: 'testegroup', // ALTERAR
                    deploymentMethod: 'deploy',
                    excludes: '',
                    iamRoleArn: '',
                    includes: '**',
                    pollingFreqSec: 15,
                    pollingTimeoutSec: 600,
                    proxyHost: '',
                    proxyPort: 0,
                    region: 'us-east-1', // CHECAR
                    s3bucket: 's3bucketthiago', // ALTERAR
                    s3prefix: '',
                    subdirectory: '',
                    versionFileName: '',
                    waitForCompletion: true])
            }
        }
    	stage('Cleaning up') {
        	steps {
            	sh "docker rmi $registry:develop"
        	}
		}
    }
}