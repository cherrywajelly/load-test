package com.timetoast.load_test.team;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Order(1)
@Component
public class TeamDataLoader implements CommandLineRunner {

    private final JdbcTemplate jdbc;

    public TeamDataLoader(final JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public void run(String... args) throws Exception {
        List<Long> memberIds = jdbc.queryForList("select member_id from member", Long.class);

        Random random = new Random();

        int teamCnt = 5+random.nextInt(memberIds.size());
        for(int i=0; i<teamCnt; i++){
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbc.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(
                        "INSERT INTO team(name, team_profile_url, created_at, last_modified_at) VALUES (?, ?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS
                );
                int t = random.nextInt();
                ps.setString(1, "team"+ t);
                ps.setString(2, "teamProfileUrl" + t);
                ps.setDate(3, java.sql.Date.valueOf(LocalDate.now()));
                ps.setDate(4, java.sql.Date.valueOf(LocalDate.now()));
                return ps;
            }, keyHolder);

            if(keyHolder.getKey()==null) continue;

            Long teamId = keyHolder.getKey().longValue();
            int membersCnt = 2 + random.nextInt(memberIds.size());
            membersCnt = Math.min(membersCnt, memberIds.size());

            List<Long> teamMembers = new ArrayList<>(memberIds);
            Collections.shuffle(teamMembers);
            List<Long> selectedMembers = teamMembers.subList(0, membersCnt);

            for (Long memberId : selectedMembers) {
                jdbc.update("INSERT INTO team_member(member_id, team_id, created_at, last_modified_at) " +
                        "VALUES (?, ?, ?, ?)", memberId, teamId, LocalDate.now(), LocalDate.now());
            }

        }
    }
}
