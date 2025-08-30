package com.timetoast.load_test.giftToast;

import com.timetoast.load_test.giftToast.dto.GiftToastFriendDto;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Order(2)
@Component
public class GiftToastDataLoader implements CommandLineRunner {
    private final JdbcTemplate jdbc;

    public GiftToastDataLoader(final JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private Number insertGiftToast(long iconId, Long teamId, boolean isOpened, String title, String type, String description) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO gift_toast(icon_id, team_id, memorized_date, opened_date, is_opened, " +
                            "title, gift_toast_type, description, created_at, last_modified_at) " +
                            "VALUES (?, ?, ?,?,?,?,?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS
            );

            ps.setLong(1, iconId);
            ps.setObject(2, teamId, java.sql.Types.BIGINT);
            ps.setDate(3, java.sql.Date.valueOf(LocalDate.now()));
            ps.setDate(4, java.sql.Date.valueOf(LocalDate.now()));
            ps.setBoolean(5, isOpened);
            ps.setString(6, title);
            ps.setString(7,  type);
            ps.setString(8, description);
            ps.setDate(9, java.sql.Date.valueOf(LocalDate.now()));
            ps.setDate(10, java.sql.Date.valueOf(LocalDate.now()));
            return ps;
        }, keyHolder);

        return keyHolder.getKey();
    }

    private void insertGiftToastOwner(long memberId, long giftToastId){
        jdbc.update("INSERT INTO gift_toast_owner(member_id, gift_toast_id, created_at, last_modified_at) " +
                "VALUES (?, ?, ?,?)", memberId, giftToastId, LocalDate.now(), LocalDate.now());
    }

    @Override
    public void run(String... args) throws Exception {
        List<Long> iconIds = jdbc.queryForList("select icon_id from icon", Long.class);

        Random random = new Random();

        List<Long> memberIds = jdbc.queryForList("select member_id from member", Long.class);

        int giftToastMineCnt = random.nextInt(memberIds.size()) + 3;
        giftToastMineCnt = Math.min(giftToastMineCnt, memberIds.size());

        Collections.shuffle(memberIds);
        List<Long> selectedMembers = memberIds.subList(0, giftToastMineCnt);
        for(long memberId : selectedMembers) {

            Number key = insertGiftToast(iconIds.get(random.nextInt(iconIds.size())), null, false,
                    "title"+random.nextInt(), "MINE", "description");
            if(key==null) continue;
            insertGiftToastOwner(memberId, key.longValue());
        }


        List<GiftToastFriendDto> follows = jdbc.query("select follower_id, following_id from follow",
                (rs, rowNum) ->
                        new GiftToastFriendDto(
                                rs.getLong("follower_id"),
                                rs.getLong("following_id")));

        int giftToastFriendCnt = random.nextInt(follows.size())+3;
        giftToastFriendCnt = Math.min(giftToastFriendCnt, follows.size());

        Collections.shuffle(follows);
        List<GiftToastFriendDto> selectedFollows = follows.subList(0, giftToastFriendCnt);

        for(GiftToastFriendDto follow : selectedFollows) {
            Number key = insertGiftToast(iconIds.get(random.nextInt(iconIds.size())), null, false,
                    "title"+random.nextInt(), "FRIEND", "description");
            if(key==null) continue;
            insertGiftToastOwner(follow.followerId(), key.longValue());
            insertGiftToastOwner(follow.followingId(), key.longValue());
        }


        List<Long> teams = jdbc.queryForList("select team_id from team", Long.class);

        int teamCnt = random.nextInt(teams.size())+3;
        teamCnt = Math.min(teamCnt, teams.size());

        Collections.shuffle(teams);

        List<Long> selectedTeams = teams.subList(0, teamCnt);

        for(long teamId : selectedTeams) {
            List<Long> teamMembers = jdbc.queryForList("SELECT member_id FROM team_member WHERE team_id = ?",
                    Long.class, teamId);

            Number key = insertGiftToast(iconIds.get(random.nextInt(iconIds.size())), teamId, false,
                    "title"+random.nextInt(), "TEAM", "description");
            if(key==null) continue;

            for(long memberId : teamMembers) {
                insertGiftToastOwner(memberId, teamId);
            }
        }
    }
}
