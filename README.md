# VideoSubtitles
Code for DB:
```sql
CREATE database VIDEO_SUBTITLES;

use VIDEO_SUBTITLES;

CREATE TABLE accounts(                          
  id SERIAL PRIMARY KEY,                          
  username NVARCHAR(100) UNIQUE NOT NULL,
  password NVARCHAR(255) NOT NULL,
  salt NVARCHAR(255) NOT NULL
); 

CREATE TABLE USER_FILES(
  username NVARCHAR(100) NOT NULL,
  file_location NVARCHAR(255) NOT NULL,
  type NVARCHAR(10)
);
```
