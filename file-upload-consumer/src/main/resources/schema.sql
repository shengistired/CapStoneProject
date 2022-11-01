CREATE TABLE IF NOT EXISTS fileconsume(
  	id VARCHAR(255),
	fileName VARCHAR(255),
	fileType VARCHAR(255),
	fileSize BIGINT,
	uploadedBy VARCHAR(255),
	uploadTimeStamp TIMESTAMP,
	PRIMARY KEY (id)
);