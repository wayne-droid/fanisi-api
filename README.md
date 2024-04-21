This is the fanisi technical test api, done with springboot (java framework).
Below is a deamon service to run the application jar file and the configurations required:


Description=Manage Java service
[Service]
EnvironmentFile=/etc/environment
WorkingDirectory=/home/wayne/fanisi-api
ExecStart=/bin/java -XX:MaxRAMPercentage=10.0 -jar fanisi-api.jar
Environment="SERVER_PORT=8040"
Environment="APP_PORT=8040"
Environment="LOG_FILE_NAME=fanisi-api.log"
Environment="user.timezone=Africa/Nairobi"
User=wayne
Type=simple
Restart=on-failure
RestartSec=10
[Install]
WantedBy=multi-user.target

To build the jar file navigate to the root folder of the project and run the following command (mvn -e package -Dmaven.test.skip=true), once done upload the generated jar file (target/generated_file.jar) to the hosting server and refrence the actual name and location of the file in the service created above. 
Below are the environment variables:

FANISI_DB_PASS=db_password
FANISI_DB_URL=jdbc:postgresql://localhost:5432/db_name
FANISI_DB_USER=db_user
FANISI_JWTSECRET=btEsoNKya3tNs/QfHwhwC6y8n+BaARY8cZsadtrBrdpdC/Ne+hToTlbvVq7WxRfd



