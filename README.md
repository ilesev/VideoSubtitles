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
```
