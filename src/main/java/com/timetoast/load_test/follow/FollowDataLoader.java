package com.timetoast.load_test.follow;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Order(1)
@Component
public class FollowDataLoader implements CommandLineRunner {

    private final JdbcTemplate jdbc;

    public FollowDataLoader(final JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public void run(String... args) throws Exception {
        List<Long> memberIds = jdbc.queryForList("select member_id from member", Long.class);

        Random random = new Random();

        for(Long memberId: memberIds) {
            int randomInt = random.nextInt(memberIds.size());
            for(int i=0; i<randomInt; i++){
                long follower = memberIds.get(random.nextInt(memberIds.size()));
                if (follower != memberId) {
                    jdbc.update("INSERT INTO follow(follower_id, following_id, created_at, last_modified_at) " +
                            "VALUES (?, ?, ?,?)", follower, memberId, LocalDate.now(), LocalDate.now());

                }
            }
        }
    }
}
