node {
    stage('Clone') {
        git 'https://github.com/abakarEgrey/offer-service.git'
    }
    
    stage('Build') {
        sh '/usr/share/maven/bin/mvn clean package'
    }
    
    stage('Run') {
        sh 'java -jar target/offer-service-0.0.1-SNAPSHOT.jar'
    }
}
