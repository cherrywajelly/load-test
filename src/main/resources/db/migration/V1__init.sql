CREATE TABLE member (
                        member_id BIGINT AUTO_INCREMENT,
                        premium_id BIGINT,
                        email VARCHAR(255),
                        member_profile_url VARCHAR(255),
                        nickname VARCHAR(255),
                        login_type VARCHAR(20),
                        member_role VARCHAR(20),
                        bank VARCHAR(20),
                        account_number VARCHAR(255) ,
                        fcm_token VARCHAR(350),
                        created_at datetime,
                        last_modified_at datetime,
                        PRIMARY KEY (member_id)
);



CREATE TABLE premium (
                               premium_id BIGINT AUTO_INCREMENT,
                               premium_type VARCHAR(20),
                               price INT,
                               count INT,
                               description VARCHAR(255),
                               created_at DATETIME,
                               last_modified_at DATETIME,
                               PRIMARY KEY (premium_id)
);

CREATE TABLE team (
                      team_id BIGINT AUTO_INCREMENT,
                      name VARCHAR(255),
                      team_profile_url VARCHAR(255),
                      created_at datetime,
                      last_modified_at datetime,
                      PRIMARY KEY (team_id)
);

CREATE TABLE team_member (
                             team_member_id BIGINT AUTO_INCREMENT,
                             member_id BIGINT,
                             team_id BIGINT,
                             created_at datetime,
                             last_modified_at datetime,
                             PRIMARY KEY (team_member_id)
);

CREATE TABLE follow (
                        follow_id BIGINT AUTO_INCREMENT,
                        follower_id BIGINT,
                        following_id BIGINT,
                        created_at datetime,
                        last_modified_at datetime,
                        PRIMARY KEY (follow_id)
);


CREATE TABLE icon_group (
                            icon_group_id BIGINT AUTO_INCREMENT,
                            member_id BIGINT,
                            icon_type VARCHAR(20),
                            icon_builtin VARCHAR(20),
                            name VARCHAR(255) NOT NULL,
                            price int NOT NULL,
                            icon_state VARCHAR(20),
                            description VARCHAR(200),
                            thumbnail_image_url VARCHAR(255),
                            created_at datetime,
                            last_modified_at datetime,
                            PRIMARY KEY (icon_group_id)
);

CREATE TABLE icon (
                      icon_id BIGINT AUTO_INCREMENT,
                      icon_group_id BIGINT,
                      icon_image_url VARCHAR(255),
                      created_at datetime,
                      last_modified_at datetime,
                      PRIMARY KEY (icon_id)
);

CREATE TABLE icon_member (
                             icon_member_id BIGINT AUTO_INCREMENT,
                             member_id BIGINT,
                             icon_group_id BIGINT,
                             created_at datetime,
                             last_modified_at datetime,
                             PRIMARY KEY (icon_member_id)
);

CREATE TABLE gift_toast (
                            gift_toast_id BIGINT AUTO_INCREMENT,
                            icon_id BIGINT,
                            team_id BIGINT,
                            memorized_date DATE,
                            opened_date DATE,
                            is_opened BOOLEAN,
                            title VARCHAR(255),
                            gift_toast_type VARCHAR(20),
                            description VARCHAR(255),
                            created_at datetime,
                            last_modified_at datetime,
                            PRIMARY KEY (gift_toast_id)
);

CREATE TABLE gift_toast_owner (
                                  gift_toast_owner_id BIGINT AUTO_INCREMENT,
                                  member_id BIGINT,
                                  gift_toast_id BIGINT,
                                  created_at datetime,
                                  last_modified_at datetime,
                                  PRIMARY KEY (gift_toast_owner_id)
);

CREATE TABLE toast_piece (
                             toast_piece_id BIGINT AUTO_INCREMENT,
                             member_id BIGINT,
                             gift_toast_id BIGINT,
                             icon_id BIGINT,
                             title VARCHAR(255),
                             contents_url VARCHAR(255),
                             created_at datetime,
                             last_modified_at datetime,
                             PRIMARY KEY (toast_piece_id)
);

CREATE TABLE toast_piece_image (
                                   toast_piece_image_id BIGINT AUTO_INCREMENT,
                                   toast_piece_id BIGINT,
                                   image_url VARCHAR(255),
                                   created_at datetime,
                                   last_modified_at datetime,
                                   PRIMARY KEY (toast_piece_image_id)
);

CREATE TABLE event_toast (
                             event_toast_id BIGINT AUTO_INCREMENT,
                             member_id BIGINT,
                             icon_id BIGINT,
                             title VARCHAR(255),
                             opened_date DATE,
                             is_opened BOOLEAN,
                             description VARCHAR(255),
                             created_at DATETIME,
                             last_modified_at DATETIME,
                             PRIMARY KEY (event_toast_id)
);


CREATE TABLE jam (
                     jam_id BIGINT AUTO_INCREMENT,
                     member_id BIGINT,
                     event_toast_id BIGINT,
                     icon_id BIGINT,
                     title VARCHAR(255),
                     contents_url VARCHAR(255),
                     image_url VARCHAR(255),
                     created_at DATETIME,
                     last_modified_at DATETIME,
                     PRIMARY KEY (jam_id)
);

CREATE TABLE showcase (
                          showcase_id BIGINT AUTO_INCREMENT,
                          member_id BIGINT,
                          event_toast_id BIGINT,
                          created_at DATETIME,
                          last_modified_at DATETIME,
                          PRIMARY KEY (showcase_id)
);

CREATE TABLE fcm (
                     fcm_id BIGINT AUTO_INCREMENT,
                     member_id BIGINT,
                     fcm_constant VARCHAR(50),
                     sender_id BIGINT,
                     toast_name VARCHAR(255),
                     is_opened BOOLEAN,
                     param BIGINT,
                     image_url VARCHAR(255),
                     created_at DATETIME,
                     last_modified_at DATETIME,
                     PRIMARY KEY (fcm_id)
);

CREATE TABLE template (
                          template_id BIGINT AUTO_INCREMENT,
                          member_id BIGINT,
                          event_toast_id BIGINT,
                          template_text VARCHAR(200),
                          created_at DATETIME,
                          last_modified_at DATETIME,
                          PRIMARY KEY (template_id)
);

CREATE TABLE inquiry (
                         inquiry_id BIGINT AUTO_INCREMENT,
                         title VARCHAR(100),
                         contentsUrl VARCHAR(255),
                         email VARCHAR(100),
                         inquiry_state VARCHAR(40),
                         created_at DATETIME,
                         last_modified_at DATETIME,
                         PRIMARY KEY (inquiry_id)
);


CREATE TABLE payment (
                         payment_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         member_id BIGINT NOT NULL,
                         order_id VARCHAR(255) UNIQUE,
                         item_type VARCHAR(255),
                         payment_state VARCHAR(255),
                         amount INT,
                         item_id BIGINT,
                         expired_date DATE,
                         created_at DATETIME,
                         last_modified_at DATETIME

);

CREATE TABLE settlement (
                            settlement_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            member_id BIGINT NOT NULL,
                            icon_group_id BIGINT NOT NULL,
                            years_month DATE NOT NULL,
                            sales_count INT NOT NULL,
                            revenue BIGINT,
                            settlements BIGINT,
                            settlement_state VARCHAR(255),
                            settlement_date DATE,
                            created_at DATETIME,
                            last_modified_at DATETIME
);