#!groovy

node("maven") { // <1>
    // define commands
    def ocCmd = "oc --token=`cat /var/run/secrets/kubernetes.io/serviceaccount/token` --server=https://openshift.default.svc.cluster.local --certificate-authority=/run/secrets/kubernetes.io/serviceaccount/ca.crt"
    def mvnCmd = "mvn -s configuration/cicd-settings.xml"

    def strHello = 'Hello '

    echo "${strHello} ${env.PWD}"
    echo "Running ${env.BUILD_ID} on ${env.JENKINS_URL}"
    echo "test ..."

    stage('Build') { // <2>
        /* .. snip .. */
        echo 'Build'
        sh "${mvnCmd} -v"
        sh "${mvnCmd} clean package -Dmven.test.skip=true"
        
    }
    stage('Unit Test') {
        /* .. snip .. */
        if (currentBuild.result == 'SUCCESS') {
            echo 'Test'
            sh "${mvnCmd} test"
        }
    }
    stage('Integration Test') {
        /* .. snip .. */
        if (currentBuild.result == 'SUCCESS') {
            echo 'Test'
            sh "${mvnCmd} integration-test -P integration"
        }
    }
    stage('Deploy') {
        /* .. snip .. */
        if (currentBuild.result == 'SUCCESS') { // <1>
            echo 'Deploy'
        }
    }
}