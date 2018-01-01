# VideoSubtitles
Code for DB:
```sql
CREATE database VIDEO_SUBTITLES;

use VIDEO_SUBTITLES;

CREATE TABLE accounts(                          
  id SERIAL PRIMARY KEY AUTO_INCREMENT,                          
  username VARCHAR(100) UNIQUE NOT NULL,
  password VARCHAR(255) NOT NULL                                             
); 
```
