CREATE TABLE city (
                      id BIGINT  PRIMARY KEY,
                      cityName VARCHAR(255) NOT NULL UNIQUE,
                      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE district (
                          id BIGINT  PRIMARY KEY,
                          districtName VARCHAR(255) NOT NULL,
                          city_id BIGINT NOT NULL,
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          FOREIGN KEY (city_id) REFERENCES city(id) ON DELETE CASCADE
);

CREATE TABLE advertisement (
                               id BIGINT PRIMARY KEY,
                               title VARCHAR(255) NOT NULL,
                               description TEXT NOT NULL,
                               district_id BIGINT NOT NULL,
                               user_id BIGINT NOT NULL,
                               created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                               FOREIGN KEY (district_id) REFERENCES district(id) ON DELETE CASCADE
);

